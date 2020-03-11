package parallel_exec.android.web;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import org.openqa.selenium.By;

import java.util.concurrent.TimeUnit;

public class WebAndroidWrapper extends WebAndroidCaseBuilder {
    public WebAndroidWrapper(AndroidDriver<MobileElement> driver) {
        super(driver);
    }

    public WebAndroidWrapper toPage(){return this;}

    public WebAndroidWrapper clickIt(String eString){

        clickEvent(By.xpath(eString));
        System.out.println("clicked "+eString);
        return this;
    }

    public WebAndroidWrapper goToUrl(String url){
        goTo(url);
        System.out.println("accessed "+url);
        return this;
    }

    public WebAndroidWrapper typeText(String k, String v){

        sendKeys(k,By.xpath(v));
        return this;
    }

    public WebAndroidWrapper justWait(String time){
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(time), TimeUnit.SECONDS);
        System.out.println("wait "+time);
        return this;
    }

    public WebAndroidWrapper pressButton(AndroidKey k){
        press(k);
        return this;
    }

    public WebAndroidWrapper hide(){
        hideKey();
        return this;
    }

    public WebAndroidWrapper swipe_scroll(int sy, int sx, int ey, int ex, int d){
        swipe(sy,sx,ey,ex,d);
        return this;
    }

    public WebAndroidWrapper swipe_scroll(int duration){
        swipe(1000,500,10,500,duration);
        return this;
    }
}

