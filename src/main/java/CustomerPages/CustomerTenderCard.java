package CustomerPages;

import com.codeborne.selenide.Condition;
import helpers.WebDriverContainer;
import org.openqa.selenium.By;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.$;


public class CustomerTenderCard extends CustomerMyTradesPage {

    private static final String goBackButton = "section#page div#viewAuctionBlock div button";
    private static final String tradeNumberTextField = "div.page div.panel_content table.info-table tr.even-row td.v-align.middle span:first-of-type";
    private static final String openConsiderationProtocolFormButton = "div#viewAuctionBlock table.info-table tr td button.btn[tradeid]";
    private static final String openSummarizingProtocolFormButton = "//div[contains(@id, 'eventsProtocols')]/div/table/tbody/tr[3]/td[4]/button";
    private static final String openContractFormButton = "tbody a[href*='CreateContract']";
    private static final String openContractButton = "div#contractsTab table";
    private static final String historyButton = "button[data-bind*='history']";
    private static final String closeHistoryButton = "#HistoryWindow_wnd_title~div>a";
    private static final String viewEISNoticeXPath = "//div[contains(@id, 'ContractHistoryGrid')]//td[contains(text(), 'Импорт извещения из ЕИС')]/following-sibling::td/a";

    public CustomerTenderCard() { super(); }

    public void isPageLoaded(){
        $(goBackButton).shouldBe(Condition.visible);
    }

    public ArrayList<String> getTradeInfo(){
        isPageLoaded();
        ArrayList<String> info = new ArrayList<String>();
        String currentURL = WebDriverContainer.getInstance().getWebDriver().getCurrentUrl();
        String auctionNumber = currentURL.substring(currentURL.lastIndexOf('/' ) + 1);
        String tradeNumber = $(tradeNumberTextField).getText();
        info.add(auctionNumber);
        info.add(tradeNumber);
        System.out.println(auctionNumber + "  " + tradeNumber);
        return info;
    }

    public void openConsiderationProtocolForm() throws Exception{
        $(openConsiderationProtocolFormButton).click();
        System.out.println("Заказчик открывает форму протокола рассмотрения заявок");
        System.out.println(timer.getCurrentTimeMillis());
        Thread.currentThread().sleep(100);
        WebDriverContainer.getInstance().switchToNewWindowAndCloseOldWindow();
    }

    public void openSummarizingProtocolForm(){
        System.out.println("Заказчик открывает форму протокола подведения итогов торгов");
        System.out.println(timer.getCurrentTimeMillis());
        $(By.xpath(openSummarizingProtocolFormButton)).click();
        WebDriverContainer.getInstance().switchToNewWindow();
    }

    public void viewTenderHistory() throws Exception{
        System.out.println("Заказчик открывает историю закупки");
        $(historyButton).shouldBe(Condition.visible).click();
        System.out.println(timer.getCurrentTimeMillis());
        $(By.xpath(viewEISNoticeXPath)).shouldBe(Condition.visible);
        System.out.println(timer.getCurrentTimeMillis());
        $(closeHistoryButton).click();
        Thread.currentThread().sleep(200);

    }

    public void openContractForm(){
        isPageLoaded();
        $(openContractFormButton).click();
    }

}
