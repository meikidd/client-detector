package com.meikidd.detector;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;

public class ClientDetector {
    private static final String REGEXFILE = "/regexes.json";
    private static DetectorRegexes dr;

    static {
        String jsonStr = "{}";
        try {
            InputStream is = ClientDetector.class.getResourceAsStream(REGEXFILE);
            if(is != null) {
                jsonStr = IOUtils.toString(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        dr = JSON.parseObject(jsonStr, DetectorRegexes.class);
        for (int i=0; i < dr.getBrowser().size(); i++) {
            String regex = dr.getBrowser().get(i).getRegex();
            dr.getBrowser().get(i).setPattern(Pattern.compile(regex));
        }
        for (int i=0; i < dr.getDevice().size(); i++) {
            String regex = dr.getDevice().get(i).getRegex();
            dr.getDevice().get(i).setPattern(Pattern.compile(regex));
        }
        for (int i=0; i < dr.getOs().size(); i++) {
            String regex = dr.getOs().get(i).getRegex();
            dr.getOs().get(i).setPattern(Pattern.compile(regex));
        }
    }

    public static Browser detectBrowser(String ua) {
        ua = ua.toLowerCase();
        Browser browser = new Browser();
        for (int i=0; i < dr.getBrowser().size(); i++) {
            Matcher matcher = dr.getBrowser().get(i).getPattern().matcher(ua);
            if(matcher.find() && matcher.groupCount() >= 2) {
                browser.setName(dr.getBrowser().get(i).getFamily());
                for(int j=0; j < matcher.groupCount(); j++) {
                    if(isVersionString(matcher.group(j))) {
                        browser.setVersion(getMajorVersion(matcher.group(j)));
                    }
                }
                break;
            }
        }
        if(browser.getName() == null) {
            browser.setName(Browser.OTHER);
        }
        return browser;
    }
    public static OS detectOS(String ua) {
        OS os = new OS();
        os.setName(OS.MAC);
        return os;
    }
    public static Device detectDevice(String ua) {
        ua = ua.toLowerCase();
        Device device = new Device();
        for (int i=0; i < dr.getDevice().size(); i++) {
            DetectorObject detect = dr.getDevice().get(i);
            Matcher matcher = detect.getPattern().matcher(ua);
            if(matcher.find()) {
                device.setName(detect.getFamily());
                break;
            }
        }
        if(device.getName() == null) {
            device.setName(Device.PC);
        }
        return device;
    }

    public static boolean isVersionString(String version) {
        Pattern versionPattern = Pattern.compile("^[0-9\\.]+$");
        Matcher matcher = versionPattern.matcher(version);
        return matcher.find();
    }

    public static String getMajorVersion(String version) {
        if(version.contains(".")) {
            return version.substring(0, version.indexOf("."));
        } else {
            return version;
        }
    }

}
