package SupplierPages;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.*;

public class SupplierMyTradesPage extends SupplierMainPage {

    private static final String tradesListCurrentPageNumber = ".ui-pg-input";

    public SupplierMyTradesPage(){ super(); }

    public void isPageLoaded(){
        $(tradesListCurrentPageNumber).shouldHave(Condition.value("1"));
    }


}
