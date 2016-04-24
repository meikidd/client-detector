package com.meikidd.detector;

import com.alibaba.detector.Browser;
import com.alibaba.detector.Device;
import com.alibaba.detector.OS;

public class ClientObject {
    private String ua;
    private Browser browser;
    private OS os;
    private Device device;

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public Browser getBrowser() {
        return browser;
    }

    public void setBrowser(Browser browser) {
        this.browser = browser;
    }

    public OS getOs() {
        return os;
    }

    public void setOs(OS os) {
        this.os = os;
    }
}
