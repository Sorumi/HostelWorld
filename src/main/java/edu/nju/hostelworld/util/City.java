package edu.nju.hostelworld.util;

/**
 * Created by Sorumi on 17/3/13.
 */
public enum City {
    ShangHai("上海"),
    NanJing("南京"),
    BeiJing("北京"),
    Guanghou("广州"),
    HangZhou("杭州"),
    XiAn("西安"),
    ChongQing("重庆"),
    WuHan("武汉"),
    XiaMen("厦门");

    private final String name;

    private City(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
