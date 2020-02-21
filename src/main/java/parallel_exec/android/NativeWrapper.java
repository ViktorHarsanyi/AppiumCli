package parallel_exec.android;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import org.openqa.selenium.By;

import java.util.List;


public class NativeWrapper extends AndroidCaseBuilder   {

    public NativeWrapper(AndroidDriver<MobileElement> driver){
        super(driver);
    }

    public NativeWrapper toScreen(){
        return this;
    }

    public NativeWrapper clickIt(By element){
        clickEvent(element);
        return this;
    }

    public NativeWrapper waitForClickable(By element){
        waitForEnabled(element);
        return this;
    }

    public NativeWrapper clickIt(MobileElement element){
        clickEvent(element);
        return this;
    }

    public NativeWrapper typeText(By element, String text){
        sendText(element,text);
        return this;
    }

    public NativeWrapper typeText(MobileElement element, String text){
        sendText(element,text);
        return this;
    }


    public NativeWrapper repeatClick(int times, List<MobileElement> list){
        for(int i=0;i<times;i++)
            for(MobileElement e:list) clickIt(e);
        return this;
    }

    public NativeWrapper repeatClick(int times, By e){
        for(int i=0;i<times;i++)
            clickIt(e);
        return this;
    }

    public NativeWrapper swipe_scroll(int sy, int sx, int ey, int ex, int d){
        swipe(sy,sx,ey,ex,d);
        return this;
    }

    public NativeWrapper scrollTO(By e, int start, int stop){
        scroll(e,start,stop);
        return this;
    }

    public NativeWrapper scrollTO(MobileElement e, int start, int stop){
        scroll(e,start,stop);
        return this;
    }

    public NativeWrapper press(AndroidKey key){
        pressButton(key);
        return this;
    }


}
