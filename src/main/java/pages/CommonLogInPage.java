package pages;

import static com.codeborne.selenide.Selenide.*;

public class CommonLogInPage extends CommonPage {

    private static final String commonCabinetForm = ".service__item_l.itemdisabled";
    private static final String supplierCabinetEnterButton = "a[href='/suppliers/supplier-web-PageParticipantEdit']";
    private static final String customerCabinetEnterButton = "a[href='/customers/customer-web-PageLogin']";


    public CommonLogInPage(){
        super();
    }

    public void supplierLogInPageOpen(){
        $$(commonCabinetForm).get(1).hover();
        $(supplierCabinetEnterButton).click();
    }

    public void customerLogInPageOpen(){
        $$(commonCabinetForm).get(0).hover();
        $(customerCabinetEnterButton).click();
    }

}
