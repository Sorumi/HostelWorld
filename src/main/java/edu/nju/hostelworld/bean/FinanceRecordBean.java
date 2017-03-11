package edu.nju.hostelworld.bean;

import edu.nju.hostelworld.model.FinanceRecord;

/**
 * Created by Sorumi on 17/3/11.
 */
public class FinanceRecordBean {

    private FinanceRecord financeRecord;

    private double resultMoney;

    public FinanceRecord getFinanceRecord() {
        return financeRecord;
    }

    public void setFinanceRecord(FinanceRecord financeRecord) {
        this.financeRecord = financeRecord;
    }

    public double getResultMoney() {
        return resultMoney;
    }

    public void setResultMoney(double resultMoney) {
        this.resultMoney = resultMoney;
    }
}
