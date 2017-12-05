package pages;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.*;

public class MainPage extends CommonPage{

    private static final String homePage = "https://stable.rts-tender.ru";
    private static final String loginButton = "a[href='/login']";

    public MainPage(){
        super();
    }

    public void isPageLoaded(){
        $(loginButton).shouldBe(Condition.visible);
    }

    public void openLoginPage(){
        open(homePage);
        $(loginButton).click();
    }

}
