package SupplierPages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class SupplierContractPage extends SupplierMyContractsPage {


    private static final long fileLength = 9669539;

    private static final String printButtonId = "BaseMainContent_MainContent_lnkPrint";
    private static final String downloadDocsButton = "#BaseMainContent_MainContent_btnDownloadDocs";
    private static final String differencesProtocolCreateButtonId = "BaseMainContent_MainContent_btnDifferenceProtocol";
    private static final String acceptAndSignContractButtonId = "BaseMainContent_MainContent_btnSignAndSend";
    private static final String attachFileToDifferenceProtocolField = "div#BaseMainContent_MainContent_ufDifferenceProtocolFile_btnUpload>input:last-of-type";
    private static final String xpath = "/html/body/form/div[4]/div[2]/div/div[2]/fieldset/div/ul/li[2]/div/input[2]";
    private static final String deleteAttachedFileButton = "ul#BaseMainContent_MainContent_ufDifferenceProtocolFile_ulDocuments a[class='delete']";
    private static final String continueDifferenceProtocolCreationButtonId = "BaseMainContent_MainContent_btnContinueDifferenceProtocol";
    private static final String confirmationButton = "div#dialogConfirm~div div.ui-dialog-buttonset>button";
    private static final String contractStatusInfoSpanId = "BaseMainContent_MainContent_tfvState_lblValue";

    public SupplierContractPage(){ super(); }

    public void isPageLoaded(){
        $(By.id(printButtonId)).shouldBe(Condition.visible);
    }

    public void downloadDocs() throws Exception{
        System.out.println("Поставщик выгружает документы контракта");
        downloadFile(downloadDocsButton);
    }

    private void differencesProtocolCreateButtonClick() throws Exception{
        $(By.id(differencesProtocolCreateButtonId)).click();
        Thread.currentThread().sleep(1500);
    }

    private void attachFileToDifferenceProtocol(){
        $(By.xpath(xpath)).sendKeys(commonDocument);
        $(deleteAttachedFileButton).shouldBe(Condition.visible);
    }

    private void continueDifferenceProtocolCreationButtonClick() throws Exception{
        $(By.id(continueDifferenceProtocolCreationButtonId)).click();
        System.out.println(timer.getCurrentTimeMillis());
        $(By.id(contractStatusInfoSpanId)).shouldHave(Condition.text("Участником создан протокол разногласий"));
        System.out.println(timer.getCurrentTimeMillis());

    }

    public void createDifferenceProtocol() throws Exception{
        isPageLoaded();
        differencesProtocolCreateButtonClick();
        attachFileToDifferenceProtocol();
        continueDifferenceProtocolCreationButtonClick();
    }

    public void acceptAndSignContract(){
        $(By.id(acceptAndSignContractButtonId)).click();
        $(confirmationButton).shouldBe(Condition.visible).click();
        System.out.println(timer.getCurrentTimeMillis());
        $(By.id(contractStatusInfoSpanId)).shouldHave(Condition.text("Контракт подписан участником"));
        System.out.println(timer.getCurrentTimeMillis());
    }

}
