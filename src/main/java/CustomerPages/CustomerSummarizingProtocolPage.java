package CustomerPages;

import com.codeborne.selenide.Condition;
import helpers.WebDriverContainer;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.*;

public class CustomerSummarizingProtocolPage extends CustomerTenderCard{

    private static final String allSuppliersAllowButtonId = "AllAllowS";
    private static final String formAndAttachProtocolButtonId = "protocolFormAndAdd";
    private static final String deleteAttachedProtocolButton = "div#filesItemTemplate a[onclick^='remove']";
    private static final String signAndPublishProtocolButtonId = "publishAndSignProtocol";
    private static final String signAndPublishProtocolInPopUpButtonId = "publishAndSignPreviewProtocolButton";
    private static final String successButtonId = "CommonErrorWindowOk";
    private static final String downloadDocsButtonId = "DownloadApplications";
    private static final String downloadDocsButtonInPopUpId = "StartArchiveDownloadingButton";
    private static final String cancelDownloadButtonId = "CancelArchiveDownloadingButton";

    public CustomerSummarizingProtocolPage(){ super(); }

    public void isPageLoaded(){
        $(By.id(signAndPublishProtocolButtonId)).shouldBe(Condition.visible);
    }

    private void allowAllSuppliers() throws Exception{
        $(By.id(allSuppliersAllowButtonId)).click();
        Thread.currentThread().sleep(1000);
    }

    private void formAndAttachProtocol() throws Exception{
        $(By.id(formAndAttachProtocolButtonId)).click();
        Thread.currentThread().sleep(1000);
        $(deleteAttachedProtocolButton).shouldBe(Condition.visible);
    }

    private void signAndPublishButtonClick() throws Exception{
        System.out.println("Заказчик публикует протокол подведения итогов торгов");
        $(By.id(signAndPublishProtocolButtonId)).click();
        $(By.id(signAndPublishProtocolInPopUpButtonId)).shouldBe(Condition.visible).click();
        System.out.println(timer.getCurrentTimeMillis());
        Thread.currentThread().sleep(400);
        $(By.id(successButtonId)).shouldBe(Condition.visible);
        System.out.println(timer.getCurrentTimeMillis());
        $(By.id(successButtonId)).click();
        Thread.currentThread().sleep(1000);
    }

    public void createAndPublishSummarizingProtocol() throws Exception{
        isPageLoaded();
        allowAllSuppliers();
        formAndAttachProtocol();
        signAndPublishButtonClick();
        downloadDocs();
        WebDriverContainer.getInstance().switchToNewWindowAndCloseOldWindow();
        WebDriverContainer.getInstance().getWebDriver().navigate().refresh();
    }

    public void downloadDocs() throws Exception{
        System.out.println("Заказчик выгружает документы из протокола подведения итогов");
        $(By.id(downloadDocsButtonId)).shouldBe(Condition.visible).click();
        $(By.id(downloadDocsButtonInPopUpId)).shouldBe(Condition.visible).click();
        System.out.println(timer.getCurrentTimeMillis());
        $(By.id(cancelDownloadButtonId)).shouldNotBe(Condition.visible);
        System.out.println(timer.getCurrentTimeMillis());
    }
}
