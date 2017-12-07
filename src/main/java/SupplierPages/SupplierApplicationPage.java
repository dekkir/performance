package SupplierPages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import helpers.WebDriverContainer;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;

import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.*;

public class SupplierApplicationPage extends SupplierMyApplicationsPage{

    public SupplierApplicationPage(){ super(); }

    private static final String tenderCardOpeningButton = "button[ng-click='goToTenderCard()']";
    private static final String applicationWithdrawButton = "button[ng-click='withdrawClick()']";
    private static final String confirmationButton = "div#dialogConfirm~div div button";
    private static final String successButton = "div#dialogSuccess~div div button";
    private static final String applicationStatusTextSpanXPath = "//div[contains(@id,'workContainer')]//tr/td[contains(text(), 'Статус')]/following-sibling::td";
    private static final String signAndSubmitButton = "button[ng-click='applyClick()']";

    private static double resultTime;

    public void isPageLoaded(){
        $(tenderCardOpeningButton).shouldBe(Condition.visible);
    }

    public void tenderCardOpen(){
        $(tenderCardOpeningButton).click();
    }

    public void test() throws ElementNotVisibleException{
        $(applicationWithdrawButton).waitUntil(Condition.visible, 5000, 1000);
    }

    private void withdrawApplication() throws Exception{
        for(int i = 0; i <= 30; i++){
            String status = "";
            status = $(applicationWithdrawButton).getAttribute("class");
            if(status.equals("btn btn-grey")) break;
            else {
                Thread.currentThread().sleep(3000);
                WebDriverContainer.getInstance().getWebDriver().navigate().refresh();
            }
        }
        System.out.println("Поставщик отзывает заявку");
        $(applicationWithdrawButton).click();
        $(confirmationButton).shouldBe(Condition.visible).click();
        System.out.println(timer.getCurrentTimeMillis());
        $(successButton).shouldBe(Condition.visible).click();
        $(By.xpath(applicationStatusTextSpanXPath)).shouldHave(Condition.text("Отозвана участником"));
        System.out.println(timer.getCurrentTimeMillis());
    }

    private void reCreateApplication() throws Exception{
        $(signAndSubmitButton).click();
        $(confirmationButton).shouldBe(Condition.visible).click();
        Thread.currentThread().sleep(500);
        $(successButton).shouldBe(Condition.visible).click();
        Thread.currentThread().sleep(2000);
        //   $(By.xpath(applicationStatusTextSpanXPath)).shouldHave(Condition.text("Отозвана участником"));
    }

    public void withdrawAndRecreateApplication() throws Exception{
        isPageLoaded();
        withdrawApplication();
        reCreateApplication();
    }



}
