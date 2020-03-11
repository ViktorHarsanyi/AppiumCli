package parallel_exec.android;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AndroidCaseBuilder {

    public AndroidDriver<MobileElement> driver;
    public WebDriverWait wait;

    public AndroidCaseBuilder(AndroidDriver<MobileElement> driver){
        this.driver = driver;
        wait = new WebDriverWait(driver,30);
    }

    public void waitFor(By element){
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(element));
    }

    public void waitForEnabled(By element){wait.until(ExpectedConditions.elementToBeClickable(element));}

    public void justWait(Long time){
        driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
    }

    public void waitFor(MobileElement element){
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void clickEvent(By element){
        waitFor(element);
        driver.findElement(element).click();
    }

    public void clickEvent(MobileElement element){
        waitFor(element);
        element.click();
    }

    public void sendText(By element,String text){
        waitFor(element);
        driver.findElement(element).sendKeys(text);
    }

    public void sendText(MobileElement element,String text){
        waitFor(element);
        element.sendKeys(text);
    }

    public List<MobileElement> getElementsByClass(String classPath){
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver.findElementsByClassName(classPath);
    }

    public List<MobileElement> getElementsById(String id){
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        return driver.findElementsById(id);
    }

    public String read(By element){
        waitFor(element);
        return driver.findElement(element).getText();
    }

    public void assertEq(By element,String expected){
        waitFor(element);
        Assert.assertEquals(read(element),expected);
    }

    public void pressButton(AndroidKey key){
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        driver.pressKey(new KeyEvent(key));
        driver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
    }

    void swipe(int startY,int startX,int endY,int endX, int duration) {
        new TouchAction<>(driver).press(PointOption.point(startX,startY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(duration))).moveTo(PointOption.point(endX,endY))
                .release().perform();
    }

    void scroll(By e,int start,int stop){
        TouchActions action = new TouchActions(driver);
        action.scroll(driver.findElement(e), start,stop);
        action.perform();
    }

    void scroll(MobileElement e,int start,int stop){
        TouchActions action = new TouchActions(driver);
        action.scroll(e, start,stop);
        action.perform();
    }
}
