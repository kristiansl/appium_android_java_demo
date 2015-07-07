package com.yourcompany;

import com.saucelabs.common.SauceOnDemandAuthentication;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.saucelabs.junit.ConcurrentParameterized;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import java.net.URL;

import java.util.LinkedList;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
/**
 * Demonstrates how to write a JUnit test that runs tests against Sauce Labs using multiple emulators in parallel.
 * <p/>
 * The test also includes the {@link SauceOnDemandTestWatcher} which will invoke the Sauce REST API to mark
 * the test as passed or failed. 
 *
 * @author Kristian 
 */
@RunWith(ConcurrentParameterized.class)
public class SampleSauceTest implements SauceOnDemandSessionIdProvider {

    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */

   // public String username = System.getenv("SAUCE_USER_NAME") != null ? System.getenv("SAUCE_USER_NAME") : System.getenv("SAUCE_USERNAME");
   // public String accesskey = System.getenv("SAUCE_API_KEY") != null ? System.getenv("SAUCE_API_KEY") : System.getenv("SAUCE_ACCESS_KEY");
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication("kristianmeiersl", "69c9ea29-59c8-4b3a-9909-18b1b05343f6");

   // public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(username, accesskey);
   
    /**
     * JUnit Rule which will mark the Sauce Job as passed/failed when the test succeeds or fails.
     */
    @Rule
    public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

    /**
     * Represents the platform to be used as part of the test run (i.e. Android).
     */
   private String platformName;
    /**
     * Represents the device name to be used as part of the test run. (i.e. Android Emulator, Google Nexus 7 HD Emulator)
     */
    private String deviceName;
    /**
     * Represents the version of the platform to be used as part of the test run. (i.e. 4.3)
     */
    private String platformVersion;
    /**
     * Location of the app
     */
    private String app;
    private String name;
    private String locale;
    private String language;
    //private String browserName;
    /**
     * Instance variable which contains the Sauce Job Id.
     */
    private String sessionId;

    /**
     * The {@link WebDriver} instance which is used to perform browser interactions with.
     */
    private WebDriver driver;

    /**
     * Constructs a new instance of the test.  The constructor requires three string parameters, which represent the operating
     * system, version and browser to be used when launching a Sauce VM.  The order of the parameters should be the same
     * as that of the elements within the {@link #browsersStrings()} method.
     * @param platformName
     * @param deviceName
     * @param platformVersion
     * @param app
     */

    public SampleSauceTest(String platformName, String deviceName, String platformVersion, String app, String name, String locale, String language) {
    //public SampleSauceTest(String platformName, String deviceName, String platformVersion, String app, String name, String browserName) {
        super();
        this.platformName = platformName;
        this.deviceName = deviceName;
        this.platformVersion = platformVersion;
        this.app = app;
        this.name = name;
        this.locale = locale;
        this.language = language;
      //this.browserName = browserName;
    }

    /**
     * @return a LinkedList containing String arrays representing the mobile device emulator combinations the test should be run against.
     * The values in the String array are used as part of the invocation of the test constructor
     */ 
    @ConcurrentParameterized.Parameters
    public static LinkedList browsersStrings() {
        LinkedList browsers = new LinkedList();

        browsers.add(new String[]{"Android", "Android Emulator", "4.3", "http://saucelabs.com/example_files/ContactManager.apk", "Android Emulator-Jenkins", "de", "de"});
        browsers.add(new String[]{"Android", "Google Nexus 7 HD Emulator", "4.4", "http://saucelabs.com/example_files/ContactManager.apk", "Google Nexus 7 HD Emulator", "de", "de"});
        browsers.add(new String[]{"Android", "Samsung Galaxy S4 Emulator", "4.4", "http://saucelabs.com/example_files/ContactManager.apk", "Samsung Galaxy S4 Emulator", "de", "de"});
        browsers.add(new String[]{"Android", "LG Nexus 4 Emulator", "4.4", "http://saucelabs.com/example_files/ContactManager.apk", "LG Nexus 4 Emulator", "de", "de"});
        //browsers.add(new String[]{"Android", "Samsung Galaxy S4 Device", "5.1", "http://saucelabs.com/example_files/ContactManager.apk", "Samsung Galaxy S4 REAL Device", "de", "de"});
  
        return browsers;
    } 
 
    /**
     * Constructs a new {@link RemoteWebDriver} instance which is configured to use the capabilities defined by the {@link #browser},
     * {@link #version} and {@link #os} instance variables, and which is configured to run against ondemand.saucelabs.com, using
     * the username and access key populated by the {@link #authentication} instance.
     *
     * @throws Exception if an error occurs during the creation of the {@link RemoteWebDriver} instance.
     */
    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", platformName);
        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("platformVersion", platformVersion);
        capabilities.setCapability("app", app);
        capabilities.setCapability("name", name);
        capabilities.setCapability("locale", locale);
        capabilities.setCapability("language", language);
        //capabilities.setCapability("browserName", browserName);
        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                capabilities);

        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
    } 
  
    /**
     * Runs a simple test that clicks the add contact button
     * @throws Exception
     */
    @Test
    public void sampleTest() throws Exception {
        WebElement el = driver.findElement(By.name("Add Contact"));
        el.click();
  

        // TODO: verify add contact click worked 
    }

    /**
     * Closes the {@link WebDriver} session.
     *
     * @throws Exception 
     */
    @After
    public void tearDown() throws Exception {
        driver.quit();
    } 

    /**
     *
     * @return the value of the Sauce Job id.
     */
    @Override
    public String getSessionId() {
        return sessionId;
    }
}