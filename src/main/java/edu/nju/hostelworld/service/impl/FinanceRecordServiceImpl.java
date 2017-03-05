package edu.nju.hostelworld.service.impl;

import edu.nju.hostelworld.bean.FinanceRecordBean;
import edu.nju.hostelworld.bean.OrderBean;
import edu.nju.hostelworld.dao.FinanceRecordDao;
import edu.nju.hostelworld.model.BookOrder;
import edu.nju.hostelworld.model.FinanceRecord;
import edu.nju.hostelworld.service.FinanceRecordService;
import edu.nju.hostelworld.util.DateAndTimeUtil;
import edu.nju.hostelworld.util.FinanceType;
import edu.nju.hostelworld.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Sorumi on 17/3/11.
 */
@Service
public class FinanceRecordServiceImpl implements FinanceRecordService {

    @Autowired
    private FinanceRecordDao financeRecordDao;


    @Override
    public ResultMessage addBookFinanceRecord(BookOrder bookOrder) {
        FinanceRecord financeRecord = new FinanceRecord();
        financeRecord.setType(FinanceType.Book);
        financeRecord.setTime(bookOrder.getBookedTime());
        financeRecord.setOrderID(bookOrder.getID());
        financeRecord.setMemberID(bookOrder.getMemberID());
        financeRecord.setMoney(bookOrder.getTotalPrice());
        return addFinanceRecord(financeRecord);
    }

    @Override
    public ResultMessage addCancelFinanceRecord(BookOrder bookOrder) {
        FinanceRecord financeRecord = new FinanceRecord();
        financeRecord.setType(FinanceType.Cancel);
        financeRecord.setTime(bookOrder.getCancelledTime());
        financeRecord.setOrderID(bookOrder.getID());
        financeRecord.setMemberID(bookOrder.getMemberID());
        financeRecord.setMoney(bookOrder.getTotalPrice());
        return addFinanceRecord(financeRecord);
    }

    @Override
    public ResultMessage addExpireFinanceRecord(BookOrder bookOrder) {
        FinanceRecord financeRecord = new FinanceRecord();
        financeRecord.setType(FinanceType.Expire);
        LocalDateTime time = LocalDate.parse(bookOrder.getCheckInDate()).plusDays(1).atTime(0, 0);
        financeRecord.setTime(DateAndTimeUtil.timeStringWithHyphen(time));
        financeRecord.setOrderID(bookOrder.getID());
        financeRecord.setMemberID(bookOrder.getMemberID());
        financeRecord.setMoney(0);
        return addFinanceRecord(financeRecord);
    }

    @Override
    public ResultMessage addAccountFinanceRecord(BookOrder bookOrder) {
        FinanceRecord financeRecord = new FinanceRecord();
        financeRecord.setType(FinanceType.Account);
        financeRecord.setTime(bookOrder.getAccountedTime());
        financeRecord.setOrderID(bookOrder.getID());
        financeRecord.setMemberID(bookOrder.getMemberID());
        financeRecord.setMoney(bookOrder.getAccountPrice());
        return addFinanceRecord(financeRecord);
    }

    @Override
    public ResultMessage addDepositFinanceRecord(String memberID, double money) {
        FinanceRecord financeRecord = new FinanceRecord();
        financeRecord.setType(FinanceType.Deposit);
        financeRecord.setTime(DateAndTimeUtil.timeStringWithHyphen(LocalDateTime.now()));
        financeRecord.setMemberID(memberID);
        financeRecord.setMoney(money);
        return addFinanceRecord(financeRecord);
    }

    @Override
    public ResultMessage addExchangeFinanceRecord(String memberID, double money) {
        FinanceRecord financeRecord = new FinanceRecord();
        financeRecord.setType(FinanceType.Exchange);
        financeRecord.setTime(DateAndTimeUtil.timeStringWithHyphen(LocalDateTime.now()));
        financeRecord.setMemberID(memberID);
        financeRecord.setMoney(money);
        return addFinanceRecord(financeRecord);
    }

    @Override
    public List<FinanceRecordBean> findFinanceMemberRecords(String memberID) {
        List<FinanceRecord> financeRecords = financeRecordDao.findFinanceMemberRecords(memberID);
        List<FinanceRecordBean> financeRecordBeans = new ArrayList<>();
        double money = 0;
        for (FinanceRecord financeRecord : financeRecords) {
            FinanceRecordBean financeRecordBean = new FinanceRecordBean();
            financeRecordBean.setFinanceRecord(financeRecord);
            if (financeRecord.getType() == FinanceType.Book) {
                money -= financeRecord.getMoney();
            } else {
                money += financeRecord.getMoney();
            }
            financeRecordBean.setResultMoney(money);
            financeRecordBeans.add(financeRecordBean);
        }
        financeRecordBeans.sort(new FinanceRecordTimeComparator());
        return financeRecordBeans;
    }

    @Override
    public List<FinanceRecordBean> findFinanceHostelRecords(String hostelID) {
        List<FinanceRecord> financeRecords = financeRecordDao.findFinanceHostelRecords(hostelID);
        List<FinanceRecordBean> financeRecordBeans = new ArrayList<>();
        double money = 0;
        for (FinanceRecord financeRecord : financeRecords) {
            FinanceRecordBean financeRecordBean = new FinanceRecordBean();
            financeRecordBean.setFinanceRecord(financeRecord);
            money += financeRecord.getMoney();
            financeRecordBean.setResultMoney(money);
            financeRecordBeans.add(financeRecordBean);
        }
        financeRecordBeans.sort(new FinanceRecordTimeComparator());
        return financeRecordBeans;
    }

    @Override
    public List<FinanceRecordBean> findFinanceManagerRecords() {
        List<FinanceRecord> financeRecords = financeRecordDao.findFinanceManagerRecords();
        List<FinanceRecordBean> financeRecordBeans = new ArrayList<>();
        double money = 0;
        for (FinanceRecord financeRecord : financeRecords) {
            FinanceRecordBean financeRecordBean = new FinanceRecordBean();
            financeRecordBean.setFinanceRecord(financeRecord);
            if (financeRecord.getType() == FinanceType.Book) {
                money += financeRecord.getMoney();
            } else {
                money -= financeRecord.getMoney();
            }
            financeRecordBean.setResultMoney(money);
            financeRecordBeans.add(financeRecordBean);
        }
        financeRecordBeans.sort(new FinanceRecordTimeComparator());
        return financeRecordBeans;
    }

    @Override
    public FinanceRecord findFinanceRecordByID(String ID) {
        return financeRecordDao.findFinanceRecordByID(ID);
    }


    private ResultMessage addFinanceRecord(FinanceRecord financeRecord) {
        financeRecord.setID(generateRecordID());
        return financeRecordDao.addFinanceRecord(financeRecord);
    }

    private String generateRecordID() {
        int count = Math.toIntExact(financeRecordDao.countFinanceRecords());
        return String.format("%010d", count);
    }

    private class FinanceRecordTimeComparator implements Comparator<FinanceRecordBean> {
        public int compare(FinanceRecordBean f1, FinanceRecordBean f2) {
            return -f1.getFinanceRecord().getTime().compareTo(f2.getFinanceRecord().getTime());
        }
    }
}
