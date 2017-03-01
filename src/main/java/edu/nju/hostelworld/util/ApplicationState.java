package edu.nju.hostelworld.util;

/**
 * Created by Sorumi on 17/2/3.
 */
public enum ApplicationState {
    Unchecked("未审核", "yellow"),
    Passed("已通过", "blue"),
    Failed("未通过", "pink");


    private String name;

    private String color;

    private ApplicationState(String name, String color) {
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
