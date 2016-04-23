package com.alibaba.detector;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;

public class DetectorRegexes {
    private ArrayList<DetectorObject> browser;
    private ArrayList<DetectorObject> device;
    private ArrayList<DetectorObject> os;

    public ArrayList<DetectorObject> getDevice() {
        return device;
    }

    public void setDevice(ArrayList<DetectorObject> device) {
        this.device = device;
    }

    public ArrayList<DetectorObject> getOs() {
        return os;
    }

    public void setOs(ArrayList<DetectorObject> os) {
        this.os = os;
    }

    public ArrayList<DetectorObject> getBrowser() {
        return browser;
    }

    public void setBrowser(ArrayList<DetectorObject> browser) {
        this.browser = browser;
    }

    public static void main(String[] args) {

        DetectorObject detectorObject = new DetectorObject();
        detectorObject.family = "hello";
        detectorObject.regex = "/(chrome)/";

        DetectorRegexes dr = new DetectorRegexes();
        dr.setBrowser(new ArrayList<DetectorObject>());
        dr.getBrowser().add(0, detectorObject);

        System.out.println(JSON.toJSONString(dr));
    }
}
