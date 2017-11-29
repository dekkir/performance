package SupplierPages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class SupplierTenderCard extends SupplierApplicationPage {

    private final static String documentsExplainingRequestButtonId = "BaseMainContent_MainContent_btnCreateClarificationRequest";
    private final static String applicationAddButtonId = "BaseMainContent_MainContent_btnAddApplication";
    private final static String auctionInformationButtonId = "BaseMainContent_MainContent_hlAuctionInfo";
    private final static String applicationViewButtonId = "BaseMainContent_MainContent_btnViewApplication";

    public SupplierTenderCard(){ super(); }

    public void isPageLoaded(){ $(By.id(documentsExplainingRequestButtonId)).shouldBe(Condition.visible); }

    public void addDocumentsExplainingRequest(){
        $(By.id(documentsExplainingRequestButtonId)).click();
    }

    public void applicationAdd(){
        $(By.id(applicationAddButtonId)).click();
    }

    public void auctionInfoOpen(){
        $(By.id(auctionInformationButtonId)).click();
    }

    public void applicationOpen(){
        $(By.id(applicationViewButtonId)).click();
    }

}
