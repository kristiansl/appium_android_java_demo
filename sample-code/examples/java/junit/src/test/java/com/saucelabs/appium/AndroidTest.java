package com.saucelabs.appium;

import static org.junit.Assert.assertEquals;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

public class AndroidTest {

    private AppiumDriver driver;

    @Before
     public void setUp() throws Exception {

        String sauceUsername = "kristianmeiersl";
        String sauceAccessKey = "69c9ea29-59c8-4b3a-9909-18b1b05343f6"; 

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("Name", "Travis CI Android Emulator");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName","Android Emulator");
       /* capabilities.setCapability("deviceName","Samsung Galaxy S4 Device"); */
        capabilities.setCapability("platformVersion", "4.3");
        capabilities.setCapability("app", "http://saucelabs.com/example_files/ContactManager.apk");
        capabilities.setCapability("appPackage", "com.example.android.contactmanager");
        capabilities.setCapability("appActivity", ".ContactManager");
        driver = new AndroidDriver(new URL("http://"+ sauceUsername +":"+ sauceAccessKey +"@ondemand.saucelabs.com/wd/hub"), capabilities);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void addContact(){
        WebElement el = driver.findElement(By.name("Add Contact"));
        el.click();
        List<WebElement> textFieldsList = driver.findElementsByClassName("android.widget.EditText");
        textFieldsList.get(0).sendKeys("Some Name");
        textFieldsList.get(2).sendKeys("Some@example.com");
        driver.swipe(100, 500, 100, 100, 2);
        driver.findElementByName("Save").click();
    
    }

}