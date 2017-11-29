package SupplierPages;

import com.codeborne.selenide.Condition;
import helpers.WebDriverContainer;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class SupplierMyContractsPage extends SupplierMainPage{

    private static final String tradesListCurrentPageNumber = ".ui-pg-input";
    private static final String contractStatusSelector = "div#BaseMainContent_MainContent_ddlContractState_ddlList_chzn a span";
    private static final String contractSearchButtonId = "BaseMainContent_MainContent_btnSearch";
    private static final String firstFitContract = "table#BaseMainContent_MainContent_jqgContract tr:nth-of-type(2) td:nth-of-type(3) a";
    private static final String contractStatusSelectorXpath = "//li[contains(text(),'";
    private static final String contractQuantityOnPageSelector = ".ui-pg-selbox";
    private static final String tradeNumberTextFieldId = "BaseMainContent_MainContent_txtTradeNumber_txtText";
    private static final String firstFitContractTriangle = "table td[title^='Подпишите контракт'] a";
    private static boolean timeReport = false;
    private static String tradeNumber;

    public SupplierMyContractsPage(){ super(); }

    public void isPageLoaded(){
        $(tradesListCurrentPageNumber).shouldHave(Condition.value("1"));
    }

    public void selectContractStatus(String contractStatus){
        $(contractStatusSelector).click();
        $(By.xpath(contractStatusSelectorXpath + contractStatus + commonXPathEnding)).click();
        $(contractQuantityOnPageSelector).selectOption("30");
    }

    public void searchContractWithFilters(){
        $(By.id(contractSearchButtonId)).click();
        $(contractQuantityOnPageSelector).shouldNotHave(Condition.value("30"));
        isPageLoaded();
    }

    public void firstFitContractOpen(){
        $(firstFitContract).click();
        System.out.println(timer.getCurrentTimeMillis());
        WebDriverContainer.getInstance().switchToNewWindowAndCloseOldWindow();
    }

    private void setTradeNumber(){
        $(By.id(tradeNumberTextFieldId)).setValue(tradeNumber);
        $(By.id(tradeNumberTextFieldId)).shouldHave(Condition.value(tradeNumber));
    }

    private void searchButtonClick() throws Exception{
        $(contractQuantityOnPageSelector).selectOption("30");
        $(By.id(contractSearchButtonId)).click();
        $(contractQuantityOnPageSelector).shouldNotHave(Condition.value("30"));
        Thread.currentThread().sleep(1000);
    }

    private void firstFitContractOpenByTriangle(){
        $(firstFitContractTriangle).shouldBe(Condition.visible).click();
        if(timeReport)
        System.out.println(timer.getCurrentTimeMillis());
    }

    public void searchAndOpenContractByTradeNumberByTriangle(String tradeNumber, boolean timeReport) throws Exception{
        this.tradeNumber = tradeNumber;
        this.timeReport = timeReport;
        isPageLoaded();
        setTradeNumber();
        searchButtonClick();
        firstFitContractOpenByTriangle();
        WebDriverContainer.getInstance().switchToNewWindowAndCloseOldWindow();
    }

}
