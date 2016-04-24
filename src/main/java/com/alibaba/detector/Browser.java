package com.alibaba.detector;

public class Browser {
    public static final String IE = "ie";
    public static final String SAFARI = "safari";
    public static final String OPERA = "opera";
    public static final String CHROME = "chrome";
    public static final String FIREFOX = "firefox";
    public static final String OTHER = "other";

    private String name;
    private String version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
