package CustomerPages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import pages.CommonPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.*;

public class CustomerMainPage extends CommonPage {

    protected static final String logOutButtonId = "header__authorization_loguot";
    protected static final String commonTriangleXPath = "//div[contains(@id, 'undefined_top')]//following-sibling::table/tbody//td/a[contains(text(),'";
    private static final String mainMenuSupplierXPath = "//div[contains(@class,'menu_main')]//a[contains(text(),'";

    public CustomerMainPage(){
        super();
    }

    private String mainMenuItemXPathCreating(String mainMenuItemName){
        return mainMenuSupplierXPath + mainMenuItemName + commonXPathEnding;
    }

    public void isLoggedIn(){
        $(By.id(logOutButtonId)).shouldBe(Condition.visible);
    }

    public void mainMenuItemContractsOpen(){
        $(By.xpath(mainMenuItemXPathCreating("КОНТРАКТЫ"))).click();
    }

    public void mainMenuItemCustomerMasterOpen(){
        $(By.xpath(mainMenuItemXPathCreating("МАСТЕР ЗАКАЗЧИКА"))).click();
    }

    public void mainMenuItemTradesOpen(){
        $(By.xpath(mainMenuItemXPathCreating("ЗАКУПКИ"))).click();
    }



    public void logOut(){
        $(By.id(logOutButtonId)).click();
    }
}
