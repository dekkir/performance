package AdminPages;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.*;

public class AdminAuctionManagementPage extends AdminProcedureAccelerationPage {

    private static final String auctionNumberTextFieldId = "txtAuctionId";
    private static final String auctionEndButtonId = "btnComplete";

    public AdminAuctionManagementPage(){ super(); }

    public void endAuctionButtonClick() throws Exception {
        Thread.currentThread().sleep(1000);
        $(By.id(auctionEndButtonId)).click();
        Thread.currentThread().sleep(2000);
    }

    public void endAuction(String auctionNumber) throws Exception{
        endAuctionButtonClick();
        helpers.WebDriverContainer.getInstance().getWebDriver().navigate().back();
        mainMenuItemAdministrationOpen();
    }
}
