package edu.nju.hostelworld.util;

/**
 * Created by Sorumi on 17/2/3.
 */
public enum OrderState {
    UnCheckIn("未入住"), // 未入住
    CheckIn("已入住"), // 已入住
    CheckOut("已退房"), // 已退房
    Cancelled("已取消"), // 已取消
    Expired("已过期"); // 已过期
//    Accounted("已结算"); // 已结算


    private final String name;

    private OrderState(String name) {

        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
