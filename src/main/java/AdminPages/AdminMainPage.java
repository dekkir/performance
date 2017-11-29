package AdminPages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import pages.CommonPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.*;

public class AdminMainPage extends CommonPage {

    protected static final String logOutButtonId = "BaseMainContent_ucHeaderControl_lbLogoutText";
    protected static final String mainMenuSupplierXPath = "//li[contains(@class,'infoMenu-item')]//*[contains(text(),'";
    private static final String secondaryMenuSupplierXpath = commonXPathEnding + "/following-sibling::ul/li/ul/li/a[contains(text(),'";

    public AdminMainPage(){ super(); }

    public void isPageLoaded(){
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

    public void mainMenuItemAdministrationOpen() throws Exception{
        $(By.xpath(mainMenuItemXPathCreating("Администрирование"))).click();
        Thread.currentThread().sleep(500);
    }

    protected void mainMenuTradeSearchOpen(){
        mainMenuItemTradesOpen();
        $(By.xpath(secondaryMenuItemXPathCreating("Закупки", "Поиск закупок"))).click();
    }

    public void mainMenuProcedureAccelerationOpen() throws Exception{
        mainMenuItemAdministrationOpen();
        $(By.xpath(secondaryMenuItemXPathCreating("Администрирование", "Ускорение процедур"))).click();
    }

    protected void auctionManagementOpen() throws Exception{
        mainMenuItemAdministrationOpen();
        $(By.xpath(secondaryMenuItemXPathCreating("Администрирование", "Управление торгами"))).click();
    }

    public void mainMenuContractConclusionAdministrationOpen() throws Exception{
        mainMenuItemAdministrationOpen();
        $(By.xpath(secondaryMenuItemXPathCreating("Администрирование", "Управление заключением контрактов"))).click();
    }

    public void logOut(){
        $(By.id(logOutButtonId)).click();
    }
}
