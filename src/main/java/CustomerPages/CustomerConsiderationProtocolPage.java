package CustomerPages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.*;

public class CustomerConsiderationProtocolPage extends CustomerTenderCard{

    private static final String allApplicationsAllowButtonId = "AllAllowS";
    private static final String formAndAttachProtocolButtonId = "protocolFormAndAdd";
    private static final String deleteAttachedProtocolButton = "div#filesItemTemplate a[onclick^='remove']";
    private static final String signAndPublishProtocolButtonId = "publishAndSignProtocol";
    private static final String signAndPublishProtocolInPopUpButtonId = "publishAndSignPreviewProtocolButton";
    private static final String successButtonId = "CommonErrorWindowOk";
    private static final String exportApplicationsAsWordButtonId = "ExportDocumentsAsWord";
    private static int numberOfParticipants = 2;

    public CustomerConsiderationProtocolPage(){ super(); }

    public void isPageLoaded(){
        $(By.id(signAndPublishProtocolButtonId)).shouldBe(Condition.visible);
        $(By.id(allApplicationsAllowButtonId)).shouldBe(Condition.visible);
    }

    private void allowAllApplications() throws Exception{
        $(By.id(allApplicationsAllowButtonId)).click();
        Thread.currentThread().sleep(1000);
    }

    private void formAndAttachProtocol() throws Exception{
        $(By.id(formAndAttachProtocolButtonId)).click();
        Thread.currentThread().sleep(1000);
        $(deleteAttachedProtocolButton).shouldBe(Condition.visible);
    }

    private void signAndPublishButtonClick() throws Exception{
        if(numberOfParticipants == 1)
            System.out.println("Заказчик публикует протокол рассмотрения единственной заявки");
        else if(numberOfParticipants == 0)
            System.out.println("Заказчик публикует протокол о признании закупки не состоявшейся");
        else
            System.out.println("Заказчик публикует протокол рассмотрения заявок на участие");
        $(By.id(signAndPublishProtocolButtonId)).click();
        $(By.id(signAndPublishProtocolInPopUpButtonId)).shouldBe(Condition.visible).click();
        System.out.println(timer.getCurrentTimeMillis());
        Thread.currentThread().sleep(400);
        $(By.id(successButtonId)).shouldBe(Condition.visible);
        System.out.println(timer.getCurrentTimeMillis());
        $(By.id(successButtonId)).click();
        Thread.currentThread().sleep(1000);
    }

    public void createAndPublishConsiderationProtocol(int numberOfParticipants) throws Exception{
        this.numberOfParticipants = numberOfParticipants;
        isPageLoaded();
        if(numberOfParticipants != 0)
        allowAllApplications();
        Thread.currentThread().sleep(1000);
        formAndAttachProtocol();
        signAndPublishButtonClick();
    }


}
