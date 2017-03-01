package edu.nju.hostelworld.service.impl;

import edu.nju.hostelworld.Bean.MemberInfoBean;
import edu.nju.hostelworld.dao.MemberDao;
import edu.nju.hostelworld.model.Member;
import edu.nju.hostelworld.service.MemberService;
import edu.nju.hostelworld.util.MemberState;
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

    @Override
    public MemberInfoBean convertToMemberInfoBean(String memberID) {
        MemberInfoBean memberInfoBean = new MemberInfoBean();
        Member member = findMemberByID(memberID);
        memberInfoBean.setMember(member);
        if (member.getAccount() != null) {
            memberInfoBean.setAccount(member.getAccount());
        }
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
        member.setStartDate(LocalDate.now().toString());
        member.setState(MemberState.Normal);
        member.setMoney(member.getMoney() + 1000);
        return memberDao.updateMember(member);
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
        return memberDao.updateMember(member);
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
        member.setMoney(member.getMoney() + (double) point * 0.01);
        return memberDao.updateMember(member);
    }

    private String generateMemberID() {
        int count = Math.toIntExact(memberDao.countMember());
        return String.format("%07d", count);
    }
}
