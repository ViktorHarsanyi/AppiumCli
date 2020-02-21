package parallel_exec.suites;


import annotations.ControlNotation;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import parallel_exec.android.NativeWrapper;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AndroidNativeSuite {

    private final static String APPIUM_SERVER_URL = "http://127.0.0.1:4723/wd/hub";
    private AndroidDriver<MobileElement> driver;
    private WebDriverWait wait;

    private NativeWrapper screen;

    @ControlNotation(canGoToJira = false)
    @BeforeTest(alwaysRun = true)
    @Parameters({"platform", "udid", "systemPort"})
    public void setup(String platform, String udid, String systemPort) throws Exception {

        URL url = new URL(APPIUM_SERVER_URL);

        String[] platformInfo = platform.split(" ");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformInfo[0]);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformInfo[1]);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.UDID, udid);
        capabilities.setCapability(MobileCapabilityType.NO_RESET,true);
        capabilities.setCapability(MobileCapabilityType.FULL_RESET,false);
        capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, systemPort);
        capabilities.setCapability("appPackage", "hu.playsic.android");
        capabilities.setCapability("appActivity","hu.drukka.playsic.view.activity.MainActivity");
        capabilities.setCapability("autoGrantPermissions", "true");
        capabilities.setCapability(MobileCapabilityType.ORIENTATION, "PORTRAIT");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);

        driver = new AndroidDriver<MobileElement>(url, capabilities);
        wait = new WebDriverWait(driver,300);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        screen = new NativeWrapper(driver);
    }

    @Test
    public void mainTest() throws InterruptedException {

    }

    private String getMessage() {
        return driver.findElementByAccessibilityId("Alt Message").getText();
    }

    @AfterTest(alwaysRun = true)
    public void teardown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }

    private void navigateToCategory(String categoryName) throws InterruptedException {
        MobileElement toggle = driver.findElement(By.id("hu.drukka.servee:id/btnPay"));
        toggle.click();
        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
        MobileElement categoryItem = driver.findElementById(categoryName);
        categoryItem.click();

        TimeUnit.SECONDS.sleep(60);
    }

    private void selectStuff(String id) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id(id))).click();

    }

    private List<MobileElement> sweepElements(String className){
        return driver.findElements(By.className(className));
    }

    private void editTextSendKeys(String id, String keysToSend){
        MobileElement element = driver.findElement(By.id(id));
        element.sendKeys(keysToSend);
        driver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
    }

    private void editTextSendKeys(MobileElement element, String keysToSend){
        element.click();
        element.sendKeys(keysToSend);
        driver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
    }


}

