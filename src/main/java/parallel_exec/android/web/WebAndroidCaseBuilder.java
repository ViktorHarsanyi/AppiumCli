package parallel_exec.android.web;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebAndroidCaseBuilder {


    public AndroidDriver<MobileElement> driver;
    public WebDriverWait wait;

    public WebAndroidCaseBuilder(AndroidDriver<MobileElement> driver){
        this.driver = driver;
        wait = new WebDriverWait(driver,30);
    }

    public void goTo(String url){
        driver.get(url);
    }

    public void waitFor(By e){
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(e));
    }

    public void clickEvent(By e){
        waitFor(e);
        driver.findElement(e).click();
    }

    void sendKeys(String k,By e){
        waitFor(e);
        driver.findElement(e).sendKeys(k);
    }

    void press(AndroidKey k){
        driver.pressKey(new KeyEvent(k));
    }

    public void hideKey(){
        driver.hideKeyboard();
    }

    void swipe(int startY,int startX,int endY,int endX, int duration) {
        new TouchAction<>(driver).press(PointOption.point(startX,startY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(duration))).moveTo(PointOption.point(endX,endY))
                .release().perform();
    }
}
