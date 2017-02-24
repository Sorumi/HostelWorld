package edu.nju.hostelworld.util;

/**
 * Created by Sorumi on 17/2/3.
 */
public enum MemberState {
    Inactive("未激活"),
    Normal("正常"),
    Pause("已暂停"),
    Stop("已停止");

    private String name;

    private MemberState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
