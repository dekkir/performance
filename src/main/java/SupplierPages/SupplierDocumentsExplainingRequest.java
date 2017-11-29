package SupplierPages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class SupplierDocumentsExplainingRequest extends SupplierTenderCard{

    private static final String requestText  = "Тестирование производительности";
    private static final String requestSubmitButtonId = "BaseMainContent_MainContent_btnSave";
    private static final String requestTextFieldId = "BaseMainContent_MainContent_txtText_txtText";
    private static final String dialogWindowCloseButton = "button.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-icon-only.ui-dialog-titlebar-close";
    private static final String dialogWindowAcceptButton = "div.ui-dialog-buttonset button.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-text-only:first-of-type";
    //private static final String secondDialogWindowCloseButton = "button.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-icon-only.ui-dialog-titlebar-close";
    //private static final String secondDialogWindowAcceptButton = "div.ui-dialog-buttonset button.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-text-only:first-of-type";
    private static final String windowOfSuccessfullRequest = "html#ng-app.ng-scope body div.ui-dialog.ui-widget.ui-widget-content.ui-corner-all.ui-front.ui-dialog-buttons.ui-draggable.ui-resizable div.ui-dialog-buttonpane.ui-widget-content.ui-helper-clearfix div.ui-dialog-buttonset button.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-text-only";


    public SupplierDocumentsExplainingRequest(){
        super();
    }

    public void isPageLoaded(){
        $(By.id(requestSubmitButtonId)).shouldBe(Condition.visible);
    }

    public void createRequest() throws InterruptedException{
        System.out.println("Поставщик направляет запрос на разъяснение документации");
        $(By.id(requestTextFieldId)).setValue(requestText);
        $(By.id(requestTextFieldId)).shouldHave(Condition.value(requestText));
        $(By.id(requestSubmitButtonId)).click();
        Thread.currentThread().sleep(500);

        $(dialogWindowCloseButton).shouldBe(Condition.visible);
        $(dialogWindowAcceptButton).click();
        System.out.println(timer.getCurrentTimeMillis());
        Thread.currentThread().sleep(500);
       /*
        if($(dialogWindowCloseButton).isDisplayed()){
            $(dialogWindowAcceptButton).click();
            Thread.currentThread().sleep(100);
        }
        */
        $(windowOfSuccessfullRequest).shouldBe(Condition.visible);
        $(windowOfSuccessfullRequest).click();
        System.out.println(timer.getCurrentTimeMillis());
    }

}
