package edu.nju.hostelworld.util;

/**
 * Created by Sorumi on 17/2/3.
 */
public enum OrderState {
    UnCheckIn("未入住", "orange"), // 未入住
    CheckIn("已入住", "blue"), // 已入住
    CheckOut("已退房", "green"), // 已退房
    Cancelled("已退订", "pink"), // 已取消
    Expired("已过期", "purple"); // 已过期

    private final String name;
    private final String color;

    private OrderState(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return this.name;
    }

    public String getColor() {
        return color;
    }

    public static OrderState getByString(String string) {
        for (OrderState orderState : OrderState.values()) {
            if (string.toLowerCase().equals(orderState.toString().toLowerCase()))
                return orderState;
        }
        return null;
    }
}
