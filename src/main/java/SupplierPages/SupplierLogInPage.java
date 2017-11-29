package SupplierPages;

import org.openqa.selenium.By;
import pages.CommonLogInPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.*;

public class SupplierLogInPage extends CommonLogInPage {

    //private static final String currentSupplierCertificateName = "Участник для отладки 1";
    private static final String supplierCertificateXPath = "//table[contains(@id,'certlist')]//tr[contains(@data-isvalid,'true')]//td[contains(text(), '";
    private static final String enterButtonId = "submitSignin";

    public SupplierLogInPage(){
        super();
    }

    public void logInWithCertificate(String supplierCertificateName, boolean timeReport){
        $(By.xpath(supplierCertificateXPath + supplierCertificateName + commonXPathEnding)).click();
        $(By.id(enterButtonId)).click();
        if(timeReport)
        System.out.println(timer.getCurrentTimeMillis());
    }

}
