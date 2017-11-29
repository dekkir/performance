package CustomerPages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.*;

public class CustomerMyContractsPage extends CustomerMainPage {

    private static final String firstFitContractButton = "#undefined_top~table tr td a[href*='Contracts/View']";
    private static final String submitContractTriangleXPath = commonTriangleXPath + "Направьте контракт" + commonXPathEnding;
    private static final String signContractTriangleXPath = commonTriangleXPath + "Подпишите контракт" + commonXPathEnding;
    private static final String searchContractButton = "input.searchButton[value='Найти']";

    public CustomerMyContractsPage(){ super(); }

    public void isPageLoaded(){
        $(firstFitContractButton).shouldBe(Condition.visible);
        $(searchContractButton).shouldBe(Condition.visible);
    }

    public void submitContractTriangleOpen(){
        System.out.println("Заказчик открывает контракт через треугольник 'Направьте контракт'");
        $(By.xpath(submitContractTriangleXPath)).shouldBe(Condition.visible).click();
        System.out.println(timer.getCurrentTimeMillis());
    }

    public void signContractTriangleOpen(){
        System.out.println("Заказчик открывает контракт через треугольник 'Подпишите контракт'");
        $(By.xpath(signContractTriangleXPath)).shouldBe(Condition.visible).click();
        System.out.println(timer.getCurrentTimeMillis());
    }

}
