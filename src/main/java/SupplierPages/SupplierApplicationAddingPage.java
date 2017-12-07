package SupplierPages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class SupplierApplicationAddingPage extends SupplierTenderCard{

    private final static String inn = "123";
    private final static String phoneNumberPrefix = "999";
    private final static String phoneNumber = "9999999";
    private static double resultTime;
    private static boolean timeReport = false;

    private final static String applicationSignAndSubmitButtonId = "btnApplyTop";
    private final static String phoneNumberTextField1= "div.wg-phone-edit input.ng-pristine:nth-of-type(2)";
    private final static String phoneNumberTextField2= "div.wg-phone-edit input.ng-pristine:nth-of-type(3)";
    private final static String innTextField = "div#workContainer table.info-table.ng-scope tbody tr td textarea";
    private final static String documentAttachFieldId = "uploader-112";
    private final static String attachedFileLink = "div#workContainer table.info-table.ng-scope ul li a.file-link.ng-binding";
    private final static String deleteAttachedFileButton = "div#workContainer table tbody tr td div div ul>li>i.fa.fa-times.cursor";
    private final static String acceptButton = "html#ng-app.ng-scope div.ui-dialog.ui-widget.ui-dialog-buttons div.ui-dialog-buttonpane div button.ui-widget";
    private final static String successButtonXPath = "//html/body//div/button/span[contains(text(),'OK')]";

    public SupplierApplicationAddingPage(){ super(); }

    public void isPageLoaded(){
        $(By.id(applicationSignAndSubmitButtonId)).shouldBe(Condition.visible);
    }

    public void phoneNumberFill(){
        $(phoneNumberTextField1).setValue(phoneNumberPrefix);
        $(phoneNumberTextField1).shouldHave(Condition.text(phoneNumberPrefix));
        $(phoneNumberTextField2).setValue(phoneNumber);
        $(phoneNumberTextField2).shouldHave(Condition.text(phoneNumber));
    }

    private void innFill(){
        $(innTextField).setValue(inn);
        $(innTextField).shouldHave(Condition.value(inn));
    }

    private void documentAdd(){
        if(timeReport)
        System.out.println("Поставщик прикрепляет документ к заявке");
        $(By.id(documentAttachFieldId)).sendKeys(commonDocument);
        if(timeReport)
        System.out.println(timer.getCurrentTimeMillis());
        $(attachedFileLink).shouldBe(Condition.visible);
        $(deleteAttachedFileButton).shouldBe(Condition.visible);
        if(timeReport)
        System.out.println(timer.getCurrentTimeMillis());
    }

    private void applicationSignAndSubmit() throws Exception{
        if(timeReport)
        System.out.println("Поставщик подает заяву на участие в торгах");
        $(By.id(applicationSignAndSubmitButtonId)).shouldBe(Condition.enabled).click();
        $(acceptButton).shouldBe(Condition.visible).click();
        if(timeReport)
        System.out.println(timer.getCurrentTimeMillis());
        Thread.currentThread().sleep(500);
        $(By.xpath(successButtonXPath)).shouldBe(Condition.visible).click();
//      $(By.xpath(successButtonXPath)).click();
        if(timeReport)
        System.out.println(timer.getCurrentTimeMillis());
    }

    public void createApplication(boolean timeReport) throws Exception{
        this.timeReport = timeReport;
        isPageLoaded();
        innFill();
        documentAdd();
        applicationSignAndSubmit();
    }

    public void createDelayedApplication() throws Exception{
        this.timeReport = timeReport;
        isPageLoaded();
        innFill();
        //documentAdd();
        $(By.id(applicationSignAndSubmitButtonId)).shouldBe(Condition.enabled).click();
        $(acceptButton).shouldBe(Condition.visible).click();
        Thread.currentThread().sleep(500);
        $(By.xpath(successButtonXPath)).shouldBe(Condition.visible).click();
    }

}
