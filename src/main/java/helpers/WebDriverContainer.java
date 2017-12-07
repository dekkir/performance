package helpers;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

public class WebDriverContainer {

    private static WebDriverContainer instance;
    private static WebDriver driver;
    private static String mainWindowHandle;
    private static String secondaryWindowHandle;

    public static synchronized WebDriverContainer getInstance(){
        if(instance == null)
            instance = new WebDriverContainer();
        return instance;
    }

    public WebDriverContainer(){
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setBinary(new File("Chromium/chrome.exe"));
        options.addExtensions(new File("cadesplugin.crx"));
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        WebDriverRunner.setWebDriver(driver);
    }

    public WebDriver getWebDriver(){
        return driver;
    }

    public void setMainWindowHandle(){
        mainWindowHandle = driver.getWindowHandle();
    }

    public void setSecondaryWindowHandle(){
        secondaryWindowHandle = driver.getWindowHandle();
    }

    public void switchToNewWindow(){
        Boolean switched = false;
        String windowHandleBefore = driver.getWindowHandle();
        String windowHandleAfter = "";
        for(String windowHandle : driver.getWindowHandles()){
            if(!windowHandle.equals(windowHandleBefore)){
                windowHandleAfter = windowHandle;
                switched = true;
                break;
            }
        }
        if (switched) driver.switchTo().window(windowHandleAfter);
        else switchToWindow(windowHandleBefore);
        //((JavascriptExecutor) driver).executeScript("window.focus();");
    }

    public void switchToWindow(String windowHandle){
        driver.switchTo().window(windowHandle);
        ((JavascriptExecutor) driver).executeScript("window.focus();");
    }

    public void closeUnactiveWindow(){
        switchToNewWindow();
        driver.close();
        switchToNewWindow();
    }

    public void switchToNewWindowAndCloseOldWindow(){
        switchToNewWindow();
        String currentWindowHandle = driver.getWindowHandle();
        switchToNewWindow();
        driver.close();
        switchToWindow(currentWindowHandle);
    }
/*
    public void switchToPopUpWindow(){
        driver.switchTo(Popup);
    }
*/
}
