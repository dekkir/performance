package SupplierPages;

import com.codeborne.selenide.Condition;
import helpers.WebDriverContainer;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.*;

public class SupplierMyApplicationsPage extends SupplierMainPage{

    private static final String dateFromApplicationFilter = "19.10.2016";
    private static final String dateToApplicationFilter = "12.10.2017";
    private static final String filterDateFromTexFieldId = "BaseMainContent_MainContent_txtCreateDate_txtDateFrom";
    private static final String filterDateToTextFieldId = "BaseMainContent_MainContent_txtCreateDate_txtDateTo";
    private static final String searchButtonId = "BaseMainContent_MainContent_btnSearch";
    private static final String firstFitApplication = "table[id='BaseMainContent_MainContent_jqgApplication'] tr:nth-of-type(2) td a";
    private static final String applicationsQuantityOnPageSelector = ".ui-pg-selbox";

    public SupplierMyApplicationsPage(){
        super();
    }

    public void isPageLoaded(){
        $(By.id(searchButtonId)).shouldBe(Condition.visible);
    }

    public void setDateForSearch(){
        $(By.id(filterDateFromTexFieldId)).setValue(dateFromApplicationFilter);
        $(By.id(filterDateToTextFieldId)).setValue(dateToApplicationFilter);
    }

    public void tradeSearch(){
        $(applicationsQuantityOnPageSelector).selectOption("30");
        $(By.id(searchButtonId)).click();
        $(applicationsQuantityOnPageSelector).shouldNotHave(Condition.value("30"));
        $(firstFitApplication).shouldBe(Condition.visible);
    }

    public void tradeNameSearch(){
    }

    public void firstFitApplicationOpen() throws Exception{
        $(firstFitApplication).click();
       // Thread.currentThread().sleep(3500);
        WebDriverContainer.getInstance().switchToNewWindowAndCloseOldWindow();
    }

}
