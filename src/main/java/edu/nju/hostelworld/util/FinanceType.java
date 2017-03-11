package edu.nju.hostelworld.util;

/**
 * Created by Sorumi on 17/3/10.
 */
public enum FinanceType {
    Book("预定", "blue"),
    Cancel("退订", "yellow"),
    Expire("过期", "purple"),
    Deposit("充值", "orange"),
    Exchange("兑换", "green"),
    Account("结算", "pink");


    private final String name;
    private final String color;

    private FinanceType(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return this.name;
    }

    public String getColor() {
        return color;
    }
}
