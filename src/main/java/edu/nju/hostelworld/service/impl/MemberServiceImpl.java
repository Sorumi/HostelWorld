package edu.nju.hostelworld.service.impl;

import edu.nju.hostelworld.bean.MemberInfoBean;
import edu.nju.hostelworld.dao.AccountDao;
import edu.nju.hostelworld.dao.MemberDao;
import edu.nju.hostelworld.dao.OrderDao;
import edu.nju.hostelworld.model.Account;
import edu.nju.hostelworld.model.BookOrder;
import edu.nju.hostelworld.model.Level;
import edu.nju.hostelworld.model.Member;
import edu.nju.hostelworld.service.*;
import edu.nju.hostelworld.util.MemberState;
import edu.nju.hostelworld.util.OrderState;
import edu.nju.hostelworld.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Created by Sorumi on 17/2/2.
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private LevelService levelService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AppService appService;

    @Autowired
    private FinanceRecordService financeRecordService;

    @Override
    public MemberInfoBean convertToMemberInfoBean(String memberID) {
        MemberInfoBean memberInfoBean = new MemberInfoBean();
        Member member = findMemberByID(memberID);
        memberInfoBean.setMember(member);
        if (member.getAccount() != null) {
            memberInfoBean.setAccount(member.getAccount());
            Account account = accountService.findAccount(member.getAccount());
            if (account != null) {
                memberInfoBean.setAccountMoney(account.getMoney());
            }
        }
        double purchasedAmount = 0;
        List<BookOrder> orders = orderDao.findMemberOrders(memberID, OrderState.CheckOut);
        List<BookOrder> orders1 = orderDao.findMemberOrders(memberID, OrderState.CheckIn);
        orders.addAll(orders1);
        for (BookOrder order : orders) {
            purchasedAmount += order.getTotalPrice();
        }
        memberInfoBean.setPurchasedAmount(purchasedAmount);
        memberInfoBean.setLevel(findLevelByMemberID(memberID).getID());
        return memberInfoBean;
    }

    public ResultMessage addMember(Member member) {
        member.setID(generateMemberID());
        member.setState(MemberState.Inactive);
        return memberDao.addMember(member);
    }

    public ResultMessage updateMember(Member member) {
        return memberDao.updateMember(member);
    }

    public Member findMemberByID(String ID) {
        return memberDao.findMemberByID(ID);
    }

    public Member findMemberByUsername(String username) {
        return memberDao.findMemberByUsername(username);
    }

    public ResultMessage checkMember(String username, String password) {
        Member checkMember = findMemberByUsername(username);

        if (checkMember == null) {
            return ResultMessage.NOT_EXIST;
        }
        if (password.equals(checkMember.getPassword())) {
            return ResultMessage.SUCCESS;
        }
        return ResultMessage.FAILED;
    }

    public List<Member> findAllMembers() {
        return memberDao.findAllMembers();
    }

    @Override
    public Level findLevelByMemberID(String memberID) {
        double purchasedAmount = 0;
        List<BookOrder> orders = orderDao.findMemberOrders(memberID, OrderState.CheckOut);
        for (BookOrder order : orders) {
            purchasedAmount += order.getTotalPrice();
        }

        Level level = levelService.findLevelByPoints((int) purchasedAmount);
        return level;
    }

    public ResultMessage updateMoney(String ID, double money) {
        Member member = memberDao.findMemberByID(ID);
        if (member == null) {
            return ResultMessage.NOT_EXIST;
        } else if (member.getState() != MemberState.Normal && member.getState() != MemberState.Pause) {
            return ResultMessage.FAILED;
        }
        double oldMoney = member.getMoney();
        if (oldMoney + money < 0) {
            return ResultMessage.INSUFFICIENT;
        }
        member.setMoney(oldMoney + money);
        return memberDao.updateMember(member);
    }

    public ResultMessage addPoint(String ID, int point) {
        Member member = memberDao.findMemberByID(ID);
        if (member == null) {
            return ResultMessage.NOT_EXIST;
        } else if (member.getState() != MemberState.Normal) {
            return ResultMessage.FAILED;
        }
        member.setPoint(member.getPoint() + point);
        return memberDao.updateMember(member);
    }

    public ResultMessage activate(String ID) {
        Member member = memberDao.findMemberByID(ID);
        if (member == null) {
            return ResultMessage.NOT_EXIST;
        } else if (member.getState() != MemberState.Inactive || member.getAccount() == null) {
            return ResultMessage.FAILED;
        }

        ResultMessage resultMessage = accountService.deposit(member.getAccount(), 1000);

        if (resultMessage != ResultMessage.SUCCESS) {
            return resultMessage;
        }
        member.setStartDate(LocalDate.now().toString());
        member.setState(MemberState.Normal);
        member.setMoney(member.getMoney() + 1000);
        resultMessage = memberDao.updateMember(member);
        if (resultMessage != ResultMessage.SUCCESS) {
            return resultMessage;
        }
        return financeRecordService.addDepositFinanceRecord(member.getID(), 1000);
    }

    public ResultMessage pause(String ID) {
        Member member = memberDao.findMemberByID(ID);
        if (member == null) {
            return ResultMessage.NOT_EXIST;
        } else if (member.getState() != MemberState.Normal) {
            return ResultMessage.FAILED;
        }

        LocalDate startDate = LocalDate.parse(member.getStartDate());
        LocalDate todayDate = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(startDate, todayDate);
        if (daysBetween < 365) {
            return ResultMessage.FAILED;
        }
        member.setPauseDate(todayDate.toString());
        member.setState(MemberState.Pause);
        return memberDao.updateMember(member);
    }

    public ResultMessage resume(String ID) {
        Member member = memberDao.findMemberByID(ID);
        if (member == null) {
            return ResultMessage.NOT_EXIST;
        } else if (member.getState() != MemberState.Pause) {
            return ResultMessage.FAILED;
        }

        member.setStartDate(LocalDate.now().toString());
        member.setState(MemberState.Normal);
        return memberDao.updateMember(member);
    }

    public ResultMessage stop(String ID) {
        Member member = memberDao.findMemberByID(ID);
        if (member == null) {
            return ResultMessage.NOT_EXIST;
        } else if (member.getState() != MemberState.Normal && member.getState() != MemberState.Pause) {
            return ResultMessage.FAILED;
        }
        member.setState(MemberState.Stop);
        return memberDao.updateMember(member);
    }

    public ResultMessage deposit(String ID, double money) {
        Member member = memberDao.findMemberByID(ID);
        if (member == null) {
            return ResultMessage.NOT_EXIST;
        } else if (member.getState() != MemberState.Normal || member.getAccount() == null) {
            return ResultMessage.FAILED;
        }

        member.setMoney(member.getMoney() + money);
        ResultMessage resultMessage = memberDao.updateMember(member);

        if (resultMessage == ResultMessage.FAILED) {
            return resultMessage;
        }

        financeRecordService.addDepositFinanceRecord(member.getID(), money);
        return accountService.deposit(member.getAccount(), money);
    }

    public ResultMessage exchangeMoney(String ID, int point) {
        Member member = memberDao.findMemberByID(ID);
        if (member == null) {
            return ResultMessage.NOT_EXIST;
        } else if (member.getState() != MemberState.Normal) {
            return ResultMessage.FAILED;
        }

        int oldPoint = member.getPoint();
        if (oldPoint < point) {
            return ResultMessage.INSUFFICIENT;
        }
        member.setPoint(oldPoint - point);
        double money = (double) point * 0.01;
        member.setMoney(member.getMoney() + money);
        ResultMessage resultMessage = memberDao.updateMember(member);
        if (resultMessage != ResultMessage.SUCCESS) {
            return resultMessage;
        }
        financeRecordService.addExchangeFinanceRecord(member.getID(), money);
        return appService.updateMoney(-money);
    }

    public ResultMessage pauseMembers() {
        ResultMessage resultMessage = ResultMessage.SUCCESS;
        List<Member> list = memberDao.findMembersByStateAndMaxDate(MemberState.Normal, "startDate", LocalDate.now().minusYears(1).toString());
        for (Member member : list) {
            LocalDate date = LocalDate.parse(member.getStartDate()).plusYears(1);
            member.setPauseDate(date.toString());
            member.setState(MemberState.Pause);
            resultMessage = memberDao.updateMember(member);
            if (resultMessage == ResultMessage.FAILED) break;
        }
        return resultMessage;
    }

    public ResultMessage stopMembers() {
        ResultMessage resultMessage = ResultMessage.SUCCESS;
        List<Member> list = memberDao.findMembersByStateAndMaxDate(MemberState.Pause, "pauseDate", LocalDate.now().minusYears(1).toString());
        for (Member member : list) {
//            LocalDate date = LocalDate.parse(member.getStartDate()).plusYears(1);
//            member.setPauseDate(date.toString());
            member.setState(MemberState.Stop);
            resultMessage = memberDao.updateMember(member);
            if (resultMessage == ResultMessage.FAILED) break;
        }
        return resultMessage;
    }

    private String generateMemberID() {
        int count = Math.toIntExact(memberDao.countMembers());
        return String.format("%07d", count);
    }
}
