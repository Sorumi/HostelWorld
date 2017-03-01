package edu.nju.hostelworld.util;

/**
 * Created by Sorumi on 17/2/5.
 */
public enum ApplicationType {
    Open("开店申请"),
    Edit("修改申请");

    private String name;

    private ApplicationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ApplicationType getByString(String string) {
;
        for (ApplicationType applicationType : ApplicationType.values()) {
            if (string.toLowerCase().equals(applicationType.toString().toLowerCase()))
                return applicationType;
        }
        return null;
    }
}
