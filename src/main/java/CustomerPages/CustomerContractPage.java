package CustomerPages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.*;

public class CustomerContractPage extends CustomerTenderCard{

    private static final String saveContractButtonId = "saveProtocol";
    private static final String contractProjectAttachButtonId = "ContractProjectUpload";
    private static final String contractProjectDeleteButton = "table#ContractProjectGrid td a[data-role=contract-remove]";
    private static final String sendContractButtonId = "SendContractToProviderButton";
    private static final String successButtonId = "CommonErrorWindowOk";
    private static final String makeChangesButtonId = "MakeChangesButton";
    private static final String confirmationButtonId = "CommonConfirmWindowOk";
    private static final String historyOpenButtonId = "HistoryButton";
    private static final String downloadDocsButtonId = "ShowExportDocumentsWindowButton";
    private static final String downloadDocsButtonInFirstPopUpId = "ExportDocumentsButton";
    private static final String downloadDocsButtonInPopUpId = "StartArchiveDownloadingButton";
    private static final String cancelDownloadButtonId = "CancelArchiveDownloadingButton";
    private static final String contractSignButtonId = "AcceptAndSign";
    private static final String confirmSigningButtonId = "CommonConfirmWindowOk";

    public CustomerContractPage(){ super(); }

    public void isPageLoaded(){
        $(By.id(historyOpenButtonId)).shouldBe(Condition.visible);
    }

    private void attachContractProject(){
        $(By.id(contractProjectAttachButtonId)).sendKeys(commonDocument);
        $(contractProjectDeleteButton).shouldBe(Condition.visible);
    }

    private void saveContractButtonClick() throws Exception{
        $(By.id(saveContractButtonId)).click();
        Thread.currentThread().sleep(3000);
    }

    private void sendContractButtonClick(boolean timeReport) throws Exception{
        if(timeReport)
        System.out.println("Заказчик направляет контракт поставщику");
        $(By.id(sendContractButtonId)).shouldBe(Condition.visible).click();
        if(timeReport)
        System.out.println(timer.getCurrentTimeMillis());
        Thread.currentThread().sleep(500);
        $(By.id(successButtonId)).shouldBe(Condition.visible);
        if(timeReport)
        System.out.println(timer.getCurrentTimeMillis());
        $(By.id(successButtonId)).shouldBe(Condition.visible).click();
        Thread.currentThread().sleep(2000);
    }

    private void makeChangesButtonClick(){
        $(By.id(makeChangesButtonId)).click();
        $(By.id(confirmationButtonId)).shouldBe(Condition.visible).click();
    }

    public void createAndSendContract() throws Exception{
        isPageLoaded();
        attachContractProject();
       // saveContractButtonClick();
        sendContractButtonClick(true);
    }

    public void makeChange() throws Exception{
        isPageLoaded();
        makeChangesButtonClick();
        sendContractButtonClick(false);
    }

    public void downloadDocs() throws Exception{
        System.out.println("Заказчик выгружает документы из карточки контракта");
        $(By.id(downloadDocsButtonId)).shouldBe(Condition.visible).click();
        Thread.currentThread().sleep(1000);
        $(By.id(downloadDocsButtonInFirstPopUpId)).shouldBe(Condition.enabled).click();
        Thread.currentThread().sleep(1000);
        $(By.id(downloadDocsButtonInPopUpId)).shouldBe(Condition.visible).click();
        System.out.println(timer.getCurrentTimeMillis());
        $(By.id(cancelDownloadButtonId)).shouldNotBe(Condition.visible);
        System.out.println(timer.getCurrentTimeMillis());
    }

    public void signContract() throws Exception{
        System.out.println("Заказчик подписывает контракт");
        $(By.id(contractSignButtonId)).shouldBe(Condition.visible).click();
        Thread.currentThread().sleep(500);
        $(By.id(confirmationButtonId)).shouldBe(Condition.visible).click();
        System.out.println(timer.getCurrentTimeMillis());
        Thread.currentThread().sleep(500);
        $(By.id(successButtonId)).shouldBe(Condition.visible);
        System.out.println(timer.getCurrentTimeMillis());
        $(By.id(successButtonId)).click();
    }
}
