package SupplierPages;
import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.*;

public class SupplierActualPropositionsPage extends SupplierMainPage{

    private static final String tradeSearchPageOpenLink = "div#workContainer a[href*='Trade/Search']";

    public SupplierActualPropositionsPage(){ super(); }

    public void tradeSearchOpen(){
        $(tradeSearchPageOpenLink).click();
    }
}
