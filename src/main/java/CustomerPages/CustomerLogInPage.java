package CustomerPages;

import org.openqa.selenium.By;
import pages.CommonLogInPage;

import static com.codeborne.selenide.Selenide.$;

public class CustomerLogInPage extends CommonLogInPage {

    private static final String currentSupplierCertificateName = "Заказчик для отладки1";
    private static final String supplierCertificateXPath = "//table[contains(@id,'certlist')]//tr[contains(@data-isvalid,'true')]//td[contains(text(), '";
    private static final String enterButtonId = "submitSignin";

    public CustomerLogInPage(){
        super();
    }

    public void logInWithCertificate(boolean timeReport){
        $(By.xpath(supplierCertificateXPath + currentSupplierCertificateName + commonXPathEnding)).click();
        if(timeReport) {
            System.out.println("Заказчик выполняет вход по сертификату");
            System.out.println(timer.getCurrentTimeMillis());
        }
        $(By.id(enterButtonId)).click();
    }

}
