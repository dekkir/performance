package steps;
import com.codeborne.selenide.SelenideElement;
import helpers.Timer;
import helpers.WebDriverContainer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$;

public class CommonStepDefinition extends AbstractStepDefinition{

    protected static WebDriver driver;
    protected Timer timer = new Timer();
    protected static final String supplierLogOutButtonId = "BaseMainContent_ucHeaderControl_lbLogoutText";
    protected static final String customerLogOutButtonId = "header__authorization_loguot";
    private static final String LOADING_XPATH = "//div[@class='k-loading-image' or @class='k-loading-mask' or @class='spinner']";
    private static final String LOADING_1_XPATH = "//div[@class='k-loading-mask']/div[@class='k-loading-image']";
    private static final String loadingString2Xpath = "//div[@class='loading']";
    protected int delayTimeMs = 100000;
    protected int delayTime300 = 300000; //30000
    protected long pollingIntervalMs = 50;
    protected int timeForIsDisplay1 = 5;
    protected int timeForIsDisplay2 = 15; //30

    public CommonStepDefinition(){
            this.driver = WebDriverContainer.getInstance().getWebDriver();
        }

    protected void exitSystem(String userType) throws Exception{
        SelenideElement logOutButton = null;
        if(userType.equals("customer")) logOutButton = $(By.id(customerLogOutButtonId));
        else logOutButton = $(By.id(supplierLogOutButtonId));
        logOutButton.click();
        waitForLoadingImage();
    }

    protected void waitForLoadingImage() throws Exception {
        String functionName = " [-]: ";
        SelenideElement element1 = $(By.xpath(LOADING_XPATH));
        SelenideElement element2 = $(By.xpath(LOADING_1_XPATH));
        SelenideElement elementLoading3 = $(By.xpath(loadingString2Xpath));
        SelenideElement spinnerWrapAfterLogIn = $("div#loading-wrap");
        driver.manage().timeouts().implicitlyWait(timeForIsDisplay1, TimeUnit.SECONDS);

        if((spinnerWrapAfterLogIn).exists()){
            spinnerWrapAfterLogIn.waitUntil(not(exist), delayTimeMs, pollingIntervalMs);
        }

        if (element1.isDisplayed()) {
            element1.waitUntil(disappear, delayTime300, pollingIntervalMs);
            System.out.println(functionName + "появился индикатор ожидания завершения действия. Ветка-1");
            driver.manage().timeouts().implicitlyWait(timeForIsDisplay2, TimeUnit.SECONDS);
            return;
        }
        if (element2.isDisplayed()) {
            element2.waitUntil(disappear, delayTime300, pollingIntervalMs);
            System.out.println(functionName + "появился индикатор ожидания завершения действия. . Ветка-2");
            driver.manage().timeouts().implicitlyWait(timeForIsDisplay2, TimeUnit.SECONDS);
            return;
        }
        if (elementLoading3.isDisplayed()) {
            element2.waitUntil(disappear, delayTime300, pollingIntervalMs);
            System.out.println(functionName + "появился индикатор ожидания завершения действия. . Ветка-3");
            driver.manage().timeouts().implicitlyWait(timeForIsDisplay2, TimeUnit.SECONDS);
            return;
        }
    }

}

