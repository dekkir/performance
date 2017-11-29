package CustomerPages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.*;

public class CustomerMasterPage extends CustomerMainPage{

    private static final String tradeAddingButtonId = ".dropdown-toggle";
    private static final String auctionCreatingButton = ".dropdown-menu li:nth-of-type(1) a";

    public CustomerMasterPage(){super();}

    public void newAuctionCreating(){
        $(tradeAddingButtonId).click();
        $(auctionCreatingButton).click();
    }

}
