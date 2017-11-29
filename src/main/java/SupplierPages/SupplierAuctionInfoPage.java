package SupplierPages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class SupplierAuctionInfoPage extends SupplierTenderCard{

    private static final String bidsCommittingButtonId = "BaseMainContent_MainContent_hlAuction";

    public SupplierAuctionInfoPage(){ super(); }

    public void isPageLoaded(){
        $(By.id(bidsCommittingButtonId)).shouldBe(Condition.visible);
    }

    public void auctionOpen(){
        $(By.id(bidsCommittingButtonId)).click();
    }
}
