package parallel_exec.suites;


import annotations.ControlNotation;
import annotations.TypedCommand;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;
import parallel_exec.android.web.WebAndroidWrapper;
import utils.FileFilter;
import utils.ReflectionHelper;
import utils.ScannerByLine;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

public class AndroidWebAppSuite {


    private final static String APPIUM_SERVER_URL = "http://127.0.0.1:4723/wd/hub";

    private AndroidDriver<MobileElement> driver;
    private TypedCommand typedCommand;
    private WebAndroidWrapper page;


    @BeforeTest(alwaysRun = true)
    @Parameters({"platform", "udid", "chromeDriverPort", "chromeDriverPath"})
    public void setup(String platform, String udid, String chromeDriverPort, @Optional String chromeDriverPath) throws Exception {
        URL url = new URL(APPIUM_SERVER_URL);

        String[] platformInfo = platform.split(" ");

        Class<?> cls = Class.forName("src.main.java.Main.java");
        Method method = cls.getMethod("main", String[].class);
        typedCommand = method.getAnnotation(TypedCommand.class);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformInfo[0]);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformInfo[1]);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.UDID, udid);


        capabilities.setCapability("chromeDriverPort", chromeDriverPort);

        if (chromeDriverPath != null) {
            capabilities.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE, chromeDriverPath);
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("w3c",false);
            capabilities.setCapability(AndroidMobileCapabilityType.CHROME_OPTIONS,options);
        }

        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
        capabilities.setCapability(MobileCapabilityType.ORIENTATION, "PORTRAIT");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);

        page = new WebAndroidWrapper(driver);


    }
    @ControlNotation(canGoToJira = false)
    @Test
    public void mainTest() throws  IOException, NoSuchMethodException {


            if (typedCommand.runTypedCommand())
                typedCommandReflection(page);
            else hardCodedTestCase();

        for (LogEntry entry : driver.manage().logs().get("browser")) {

            if(entry.getLevel().toString().equals("WARNING")||entry.getLevel().toString().equals("ERROR"))
                for(String s:entry.getMessage().split("\""))
                    System.out.println(s);
        }

    }

    @AfterTest(alwaysRun = true)
    public void teardown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
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

