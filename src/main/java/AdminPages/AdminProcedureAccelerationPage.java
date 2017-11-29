package AdminPages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.*;

public class AdminProcedureAccelerationPage extends AdminMainPage{

    private static String auctionNumber;
    private static final String[] accelerationTypes = { "applicationReceptionEnds", "considerationEnds", "auctionEnds" };
    private static final String downloadInfoButtonId = "BaseMainContent_MainContent_btnUpdateData";
    private static final String auctionNumberTextFieldId = "BaseMainContent_MainContent_txtTradeId_txtText";
    private static final String tradeNumberLinkId = "BaseMainContent_MainContent_hlTradeNumber_hlValue";
    private static final String[] accelerationButtonIds = {
            "BaseMainContent_MainContent_btnApplicationsEndDateNow",
            "BaseMainContent_MainContent_btnTradeLotAuctionDate",
            "BaseMainContent_MainContent_hlBiddingServiceServicePage"
    };

    public AdminProcedureAccelerationPage(){ super(); }

    public void isPageLoaded(){
        $(By.id(downloadInfoButtonId)).shouldBe(Condition.visible);
    }

    private void setTradeNumber(){
        $(By.id(auctionNumberTextFieldId)).setValue(auctionNumber);
        $(By.id(auctionNumberTextFieldId)).shouldHave(Condition.value(auctionNumber));
    }

    private void downloadTradeInfo(){
        $(By.id(downloadInfoButtonId)).click();
        $(By.id(tradeNumberLinkId)).shouldBe(Condition.visible);
    }

    private void accelerateButtonClick(String accelerationButtonId) throws Exception{
        $(By.id(accelerationButtonId)).click();
        Thread.currentThread().sleep(2000);
    }

    public void procedureAccelerate(String auctionNumber, String accelerationType) throws Exception{
        this.auctionNumber = auctionNumber;
        int accelerationNumber = 0;
        isPageLoaded();
        setTradeNumber();
        downloadTradeInfo();
        for(int i = 0; i < accelerationTypes.length; i++ ){
            if(accelerationType.equals(accelerationTypes[i])){
                accelerationNumber = i;
                break;
            }
        }
        accelerateButtonClick(accelerationButtonIds[accelerationNumber]);
        switch(accelerationNumber){
            case 2: {
                break;
            }
            default: {
                mainMenuItemAdministrationOpen();
                break;
            }
        }
    }

}
