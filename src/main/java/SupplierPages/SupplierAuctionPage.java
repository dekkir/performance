package SupplierPages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class SupplierAuctionPage extends SupplierAuctionInfoPage {

    private static final String bidSubmitButtonId = "BaseMainContent_MainContent_btnConfirmBidEx";
    private static final String nextBidSumId = "spReductionMaxPrice";
    private static final String lotStatisticButtonId = "BaseMainContent_MainContent_btnStatistic";
    private static final String bidSumTextFieldId = "BaseMainContent_MainContent_txtPrice";
    private static final String successButtonXPath = "//div[contains(@id,'dialogSuccess')]/following-sibling::div/div/button";
    private static boolean timeReport = false;

    public SupplierAuctionPage(){ super(); }

    public void isPageLoaded(){
        $(By.id(lotStatisticButtonId)).shouldBe(Condition.visible);
    }

    private void setNewBid() throws Exception{
        $(By.id(nextBidSumId)).click();
        Thread.currentThread().sleep(1000);
    }

    private void submitBid(){
        if(timeReport)
            System.out.println("Поставщик подает ценовое предложение");
        $(By.id(bidSubmitButtonId)).click();
        if(timeReport)
            System.out.println(timer.getCurrentTimeMillis());
        $(By.xpath(successButtonXPath)).shouldBe(Condition.visible).click();
        if(timeReport)
            System.out.println(timer.getCurrentTimeMillis());
    }

    public void createAndSubmitNewBid(boolean timeReport) throws Exception{
        this.timeReport = timeReport;
        isPageLoaded();
        setNewBid();
        submitBid();
    }
}
