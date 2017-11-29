package AdminPages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.*;

public class AdminContractConclusionAdministrationPage extends AdminMainPage{

    private static final String contractIdTextFieldId = "BaseMainContent_MainContent_txtDealId_txtText";
    private static final String contractConclusionAccelerateButtonId = "BaseMainContent_MainContent_btnDealRegulationDate";
    private static String contractId;

    public AdminContractConclusionAdministrationPage(){ super(); }

    public void isPageLoaded(){
        $(By.id(contractConclusionAccelerateButtonId)).shouldBe(Condition.visible);
    }

    private void setContractId(){
        $(By.id(contractIdTextFieldId)).setValue(contractId);
        $(By.id(contractIdTextFieldId)).shouldHave(Condition.value(contractId));
    }

    private void accelerateButtonClick() throws Exception{
        $(By.id(contractConclusionAccelerateButtonId)).click();
        Thread.currentThread().sleep(2000);
    }

    public void accelerateContractConclusion(String contractId) throws Exception{
        isPageLoaded();
        this.contractId = contractId;
        setContractId();
        accelerateButtonClick();
        mainMenuItemAdministrationOpen();
    }
}
