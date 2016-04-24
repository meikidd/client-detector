package com.meikidd.detector;

import com.alibaba.detector.Browser;
import com.alibaba.detector.ClientDetector;
import com.alibaba.detector.Device;
import com.alibaba.detector.OS;
import com.alibaba.fastjson.JSON;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class DetectorTest extends TestCase {

    private static final String CASEFILE = "/clientCases.json";
    private static ClientCases clientCases;

    static {
        String jsonStr = "{}";
        try {
            InputStream is = DetectorTest.class.getResourceAsStream(CASEFILE);
            if(is != null) {
                jsonStr = IOUtils.toString(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientCases = JSON.parseObject(jsonStr, ClientCases.class);
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
    public void testClient() {
        for (int i = 0; i < clientCases.getCases().size(); i++) {
            ClientObject client = clientCases.getCases().get(i);
            String ua = client.getUa();
            Browser browser = ClientDetector.detectBrowser(ua);
            OS os = ClientDetector.detectOS(ua);
            Device device = ClientDetector.detectDevice(ua);
            System.out.print(client.getBrowser().getName()+","+client.getBrowser().getVersion());
            System.out.print("=");
            System.out.print(browser.getName()+","+browser.getVersion());
            System.out.println("");
            assertEquals(browser.getName(), client.getBrowser().getName());
            assertEquals(browser.getVersion(), client.getBrowser().getVersion());
//            assertEquals(os.getName(), client.getOs().getName());
            assertEquals(device.getName(), client.getDevice().getName());
        }
    }
}
