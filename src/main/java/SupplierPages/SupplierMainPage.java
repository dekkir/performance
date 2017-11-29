package SupplierPages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import pages.CommonPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.*;

public class SupplierMainPage extends CommonPage {

    private static final String logOutButtonId = "BaseMainContent_ucHeaderControl_lbLogoutText";
    private static final String mainMenuSupplierXPath = "//li[contains(@class,'infoMenu-item')]//*[contains(text(),'";
    private static final String secondaryMenuSupplierXpath = commonXPathEnding + "/following-sibling::ul/li/ul/li/a[contains(text(),'";

    public SupplierMainPage(){
        super();
    }

    public void isLoggedIn(){
        $(By.id(logOutButtonId)).shouldBe(Condition.visible);
    }

    private String mainMenuItemXPathCreating(String mainMenuItemName){
        return mainMenuSupplierXPath + mainMenuItemName + commonXPathEnding;
    }

    private String secondaryMenuItemXPathCreating(String mainMenuItemName,String secondaryMenuItemName){
        return mainMenuSupplierXPath + mainMenuItemName + secondaryMenuSupplierXpath + secondaryMenuItemName + commonXPathEnding;
    }

    private void mainMenuItemTradesOpen(){
        $(By.xpath(mainMenuItemXPathCreating("Закупки"))).click();
    }

    public void mainMenuItemContractsOpen(){
        $(By.xpath(mainMenuItemXPathCreating("Контракты"))).click();
    }

    public void mainMenuMyApplicationsOpen(){
        mainMenuItemTradesOpen();
        $(By.xpath(secondaryMenuItemXPathCreating("Закупки", "Мои заявки"))).click();
    }

    public void mainMenuMyTradesOpen(){
        mainMenuItemTradesOpen();
        $(By.xpath(secondaryMenuItemXPathCreating("Закупки", "Мои Торги/Переторжки"))).click();
    }

    public void mainMenuTradeSearchOpen(){
        mainMenuItemTradesOpen();
        $(By.xpath(secondaryMenuItemXPathCreating("Закупки", "Поиск закупок"))).click();
    }

    public void logOut() throws Exception{
        $(By.id(logOutButtonId)).click();
        Thread.currentThread().sleep(500);
    }
}
