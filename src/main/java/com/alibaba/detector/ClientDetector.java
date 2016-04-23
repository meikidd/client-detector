package com.alibaba.detector;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
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

    public static HashMap<String, String> detectBrowser(String ua) {
        ua = ua.toLowerCase();
        HashMap<String, String> map = new HashMap<String, String>();
        for (int i=0; i < dr.getBrowser().size(); i++) {
            Matcher matcher = dr.getBrowser().get(i).getPattern().matcher(ua);
            if(matcher.find() && matcher.groupCount() >= 3) {
                map.put("browser", dr.getBrowser().get(i).getFamily());
                map.put("version", matcher.group(2));
                break;
            }
        }
        return map;
    }
    public static HashMap<String, String> detectOS(String ua) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("os", "mac");
        return map;
    }
    public static HashMap<String, String> detectDevice(String ua) {
        ua = ua.toLowerCase();
        HashMap<String, String> map = new HashMap<String, String>();
        for (int i=0; i < dr.getDevice().size(); i++) {
            DetectorObject detect = dr.getDevice().get(i);
            Matcher matcher = detect.getPattern().matcher(ua);
            if(matcher.find()) {
                map.put("device", detect.getFamily());
                break;
            }
        }
        if(map.size() == 0) {
            map.put("device", "pc");
        }
        return map;
    }

    public static void main(String[] args) {
        // pc chrome
//         String uaStr = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36";
//         System.out.println(ClientDetector.detectBrowser(uaStr));

        // iphone chrome
        String uaStr = "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1";
        System.out.println(ClientDetector.detectDevice(uaStr));
    }
}
