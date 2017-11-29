package SupplierPages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class SupplierTradeSearchPage extends SupplierMainPage {

    private static final String tradeNumberTextFieldId = "BaseMainContent_MainContent_txtNumber_txtText";
    private static final String firstFitTrade = "table#jqgTrades tr td div.relative.with_favorite a";
    private static final String searchButton = "BaseMainContent_MainContent_btnSearch";
    private static final String tradesQuantityOnPageSelector = ".ui-pg-selbox";
    private static String tradeNumber;

    public SupplierTradeSearchPage(){ super(); }

    public void isPageLoaded(){
        $(By.id(searchButton)).shouldBe(Condition.visible);
    }

    private void setTradeNumberForSearch() throws Exception{
        $(By.id(tradeNumberTextFieldId)).setValue(tradeNumber);
        Thread.currentThread().sleep(300);
        $(By.id(tradeNumberTextFieldId)).shouldHave(Condition.value(tradeNumber));
    }

    private void tradeSearch(){
        $(tradesQuantityOnPageSelector).selectOption("30");
        $(By.id(searchButton)).click();
        $(tradesQuantityOnPageSelector).shouldNotHave(Condition.value("30"));
        $(firstFitTrade).shouldBe(Condition.visible);
    }

    private void firstFitTradeOpen() throws Exception{
        $(firstFitTrade).click();
        Thread.currentThread().sleep(2000);
        helpers.WebDriverContainer.getInstance().switchToNewWindowAndCloseOldWindow();
    }

    public void searchAndOpenByTradeName(String tradeNumber) throws Exception{
        this.tradeNumber = tradeNumber;
        isPageLoaded();
        setTradeNumberForSearch();
        tradeSearch();
        firstFitTradeOpen();
    }
}
