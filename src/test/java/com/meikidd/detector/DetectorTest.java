package com.meikidd.detector;

import com.alibaba.fastjson.JSON;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class DetectorTest extends TestCase {

    private static final String BROWSERS = "/browser.json";
    private static ClientCases browserCases;
    private static ClientCases osCases;
    private static ClientCases deviceCases;

    static {
        String jsonBrowser = "{}";
        String jsonOS = "{}";
        String jsonDevice = "{}";
        try {
            InputStream browserStream = DetectorTest.class.getResourceAsStream("/browser.json");
            InputStream osStream = DetectorTest.class.getResourceAsStream("/os.json");
            InputStream deviceStream = DetectorTest.class.getResourceAsStream("/device.json");
            if(browserStream != null) {
                jsonBrowser = IOUtils.toString(browserStream);
            }
            if(osStream != null) {
                jsonOS = IOUtils.toString(osStream);
            }
            if(deviceStream != null) {
                jsonDevice = IOUtils.toString(deviceStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        browserCases = JSON.parseObject(jsonBrowser, ClientCases.class);
        osCases = JSON.parseObject(jsonOS, ClientCases.class);
        deviceCases = JSON.parseObject(jsonDevice, ClientCases.class);
    }
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DetectorTest(String testName)
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( DetectorTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testBrowser() {
        System.out.println("[test browser]:");
        for (int i = 0; i < browserCases.getCases().size(); i++) {
            ClientObject client = browserCases.getCases().get(i);
            String ua = client.getUa();
            Browser browser = ClientDetector.detectBrowser(ua);
            System.out.print(client.getBrowser().getName() + "," + client.getBrowser().getVersion());
            System.out.print(" = ");
            System.out.print(browser.getName() + "," + browser.getVersion());
            System.out.println("");
            assertEquals(browser.getName(), client.getBrowser().getName());
            assertEquals(browser.getVersion(), client.getBrowser().getVersion());
        }
    }
    public void testOS() {
        System.out.println("[test os]:");
        for (int i = 0; i < osCases.getCases().size(); i++) {
            ClientObject client = osCases.getCases().get(i);
            String ua = client.getUa();
            OS os = ClientDetector.detectOS(ua);
            System.out.println(client.getOs().getName() + " = " + os.getName());
            assertEquals(os.getName(), client.getOs().getName());
        }
    }
    public void testDevice() {
        System.out.println("[test device]:");
        for (int i = 0; i < deviceCases.getCases().size(); i++) {
            ClientObject client = deviceCases.getCases().get(i);
            String ua = client.getUa();
            Device device = ClientDetector.detectDevice(ua);
            System.out.println(client.getDevice().getName() + " = " + device.getName());
            assertEquals(device.getName(), client.getDevice().getName());
        }
    }
}
