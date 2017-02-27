package edu.nju.hostelworld.util;

/**
 * Created by Sorumi on 17/2/3.
 */
public enum MemberState {
    Inactive("未激活", "yellow"),
    Normal("正常", "blue"),
    Pause("已暂停", "purple"),
    Stop("已停止", "pink");

    private String name;

    private String color;

    private MemberState(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
