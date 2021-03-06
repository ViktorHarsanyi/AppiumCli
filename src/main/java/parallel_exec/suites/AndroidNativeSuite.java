package parallel_exec.suites;


import annotations.ApplicationParams;
import annotations.ControlNotation;
import annotations.TypedCommand;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import parallel_exec.android.NativeWrapper;
import utils.FileFilter;
import utils.ReflectionHelper;
import utils.ScannerByLine;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AndroidNativeSuite {

    private final static String APPIUM_SERVER_URL = "http://127.0.0.1:4723/wd/hub";
    private AndroidDriver<MobileElement> driver;
    private WebDriverWait wait;
    private TypedCommand typedCommand;
    private NativeWrapper screen;

    @ControlNotation(canGoToJira = false)
    @BeforeTest(alwaysRun = true)
    @Parameters({"platform", "udid", "systemPort"})
    public void setup(String platform, String udid, String systemPort) throws Exception {

        URL url = new URL(APPIUM_SERVER_URL);

        String[] platformInfo = platform.split(" ");

        Class<?> cls = Class.forName("src.main.java.Main.java");
        Method method = cls.getMethod("main", String[].class);
        ApplicationParams params = method.getAnnotation(ApplicationParams.class);
        typedCommand = method.getAnnotation(TypedCommand.class);



        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformInfo[0]);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformInfo[1]);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.UDID, udid);
        capabilities.setCapability(MobileCapabilityType.NO_RESET,true);
        capabilities.setCapability(MobileCapabilityType.FULL_RESET,false);
        capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, systemPort);
        capabilities.setCapability("appPackage", params.appPackage());
        capabilities.setCapability("appActivity",params.pathToApp());
        capabilities.setCapability("autoGrantPermissions", "true");
        capabilities.setCapability(MobileCapabilityType.ORIENTATION, "PORTRAIT");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);

        driver = new AndroidDriver<MobileElement>(url, capabilities);
        wait = new WebDriverWait(driver,300);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        screen = new NativeWrapper(driver);
    }

    @Test
    public void mainTest() throws InterruptedException, IOException {
        if (typedCommand.runTypedCommand())
            typedCommandReflection(screen);
        else hardCodedTestCase();
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


    private List<MobileElement> sweepElements(String className){
        return driver.findElements(By.className(className));
    }

    private void typedCommandReflection(Object object) throws IOException {
        ScannerByLine scn = new ScannerByLine(FileFilter.finder("PATH_TO_DIRECTORY")[0]);
        ReflectionHelper reflectionHelper = ReflectionHelper.getInstance();
        scn.processLineByLine().forEach((k,v)->{
            try {
                reflectionHelper.reflectiveListInitializer(object, k, v);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void hardCodedTestCase(){
        //TODO
    }


}

