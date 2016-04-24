package com.alibaba.detector;

public class OS {
    public static final String WIN = "win";
    public static final String MAC = "mac";
    public static final String LINUX = "linux";
    public static final String IOS = "ios";
    public static final String ANDROID = "android";
    public static final String OTHER = "other";

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
