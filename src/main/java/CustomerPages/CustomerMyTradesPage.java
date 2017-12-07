package CustomerPages;

import com.codeborne.selenide.*;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CustomerMyTradesPage extends CustomerMainPage {

    private static final String tradeNameTextFieldId = "TradeLotName";
    private static final String tradeNumberTextFieldId = "NoticeNumber";
    private static final String searchButton = "input.searchButton";
    private static final String publishDateTextFieldId = "PublishDateFrom";
    private static final String considerationFilterButton = "div#tabs>a:nth-of-type(3)";
    private static final String receptionFilterButton = "div#tabs>a:nth-of-type(2)";
    private static final String firstFitTradeByNameXPath = "//div[contains(@id, 'CustomKendoGrid')]//table//td[contains(@role,'gridcell')]//div[contains(text(),'";
    private static final String firstFitTradeByNameXPathEnding = "')]//parent::td//preceding-sibling::td[contains(@class,'notice-number')]/a";
    private static final String firstFitTradeByNumberXPath = "//table//a[contains(text(),'";
    private static final String tradesQuantitySelector = "div#undefined_top.k-pager-wrap.pagerTop.helpers_row.no-bg.no-position.k-widget.k-floatwrap div.filter span.k-pager-sizes.k-label span.k-widget.k-dropdown.k-header";
    private static final String tradesQuantity100Select = "body.visible div.k-animation-container div.k-list-container.k-popup.k-group.k-reset div.k-list-scroller ul.k-list.k-reset li.k-item:last-of-type";
    private static final String applicationCheckBox = "#undefined_top~table td.check label[for]";
    private static final String considerApplicationsTriangleXPath = commonTriangleXPath + "Рассмотрите заявки') or contains(text(), 'Опубликуйте протокол о признании аукциона несостоявшимся') or contains(text(), 'Рассмотрите заявку" + commonXPathEnding;
    private static final String summarizeTriangleXPath = commonTriangleXPath + "Подведите итоги" + commonXPathEnding;
    private static final String contractSendTriangleXPath = commonTriangleXPath + "Направьте контракт" + commonXPathEnding;
    private static final String contractResendTriangleXPath = commonTriangleXPath + "Перенаправьте контракт" + commonXPathEnding;
    private static final String contractSignTriangleXPath = commonTriangleXPath + "Подпишите контракт" + commonXPathEnding;
    private static final String downloadDocsButton = "#undefined_top div a[onclick*='UploadDocumentsArchive']";
    private static final String downloadDocsButtonInPopUpId = "StartArchiveDownloadingButton";
    private static final String cancelDownloadButtonId = "CancelArchiveDownloadingButton";
    private static final String startPriceFieldId = "StartPriceFrom";
    private static final String endPriceFieldId = "StartPriceTo";
    private static String tradeName;
    private static String tradeNumber;
    private static boolean timeReport = false;

    public CustomerMyTradesPage(){ super(); }

    public void isPageLoaded(){
        $(By.id(tradeNameTextFieldId)).shouldBe(Condition.visible);
    }

    private void setTradeNameTextFieldForSearch(String tradeName){
        $(By.id(tradeNameTextFieldId)).setValue(tradeName);
        $(By.id(tradeNameTextFieldId)).shouldHave(Condition.value(tradeName));
    }

    private void setTradeNumberTextFieldForSearch(){
        $(By.id(tradeNumberTextFieldId)).setValue(tradeNumber);
        $(By.id(tradeNumberTextFieldId)).shouldHave(Condition.value(tradeNumber));
    }

    private void tradeSearchByTradeName() throws Exception{
        $(tradesQuantitySelector).click();
        Thread.currentThread().sleep(500);
        $(tradesQuantity100Select).click();
        $(tradesQuantitySelector).shouldHave(Condition.text("100"));
        $(searchButton).click();
        Thread.currentThread().sleep(3000);
        $(By.xpath(firstFitTradeByNameXPath + tradeName + firstFitTradeByNameXPathEnding)).shouldBe(Condition.visible);
    }

    private void tradeSearchByTradeNumber() throws Exception{
        $(tradesQuantitySelector).click();
        Thread.currentThread().sleep(500);
        $(tradesQuantity100Select).click();
        $(tradesQuantitySelector).shouldHave(Condition.text("100"));
        $(searchButton).click();
        Thread.currentThread().sleep(3000);
        $(By.xpath(firstFitTradeByNumberXPath + tradeNumber + commonXPathEnding)).shouldBe(Condition.visible);
    }

    private void firstFitTradeOpenByName(){
       if(timeReport)
           System.out.println("Заказчик открывает одну из закупок из реестра");
           $(By.xpath(firstFitTradeByNameXPath + tradeName + firstFitTradeByNameXPathEnding)).click();
       if(timeReport)
           System.out.println(timer.getCurrentTimeMillis());
    }

    private void firstFitTradeOpenByNumber(){
        $(By.xpath(firstFitTradeByNumberXPath + tradeNumber + commonXPathEnding)).click();
    }

    private void setDateFieldForSearch() throws Exception{
        SelenideElement dat = $(By.id(publishDateTextFieldId));
        //setFocusInKendoNumericTextBoxJS("PublishDateFrom");
        dat.clear();
        dat.sendKeys(timer.getOnlyDate());
        dat.shouldHave(Condition.value(timer.getOnlyDate()));
    }

    public void searchAndOpenTradeByTradeName(String tradeName, boolean timeReport) throws Exception {
        this.tradeName = tradeName;
        this.timeReport = timeReport;
        isPageLoaded();
        //setDateFieldForSearch();
        $(receptionFilterButton).click();
        waitForLoadingImage();
        setTradeNameTextFieldForSearch(tradeName);
        tradeSearchByTradeName();
        firstFitTradeOpenByName();
    }

    public void searchAndOpenTradeByTradeNumber(String tradeNumber) throws Exception{
        this.tradeNumber = tradeNumber;
        isPageLoaded();
        setTradeNumberTextFieldForSearch();
        tradeSearchByTradeNumber();
        firstFitTradeOpenByNumber();
    }

    public void considerApplicationsTriangleOpen(String tradeNumber, boolean timeReport) throws Exception{
        this.tradeNumber = tradeNumber;
        isPageLoaded();
        setTradeNumberTextFieldForSearch();
        tradeSearchByTradeNumber();
        if(timeReport)
        System.out.println("Заказчик открывает протокол рассмотрения заявок через треугольник 'Рассмотрите заявки'");
        $(By.xpath(considerApplicationsTriangleXPath)).shouldBe(Condition.visible).click();
        if(timeReport)
        System.out.println(timer.getCurrentTimeMillis());
    }

    public void summarizeTriangleOpen(String tradeNumber) throws Exception{
        this.tradeNumber = tradeNumber;
        isPageLoaded();
        setTradeNumberTextFieldForSearch();
        tradeSearchByTradeNumber();
        System.out.println("Заказчик открывает протокол подведения итогов через треугольник 'Подведите итоги'");
        $(By.xpath(summarizeTriangleXPath)).shouldBe(Condition.visible).click();
        System.out.println(timer.getCurrentTimeMillis());
    }

    public void contractSendTriangleOpen(String tradeNumber) throws Exception{
        this.tradeNumber = tradeNumber;
        isPageLoaded();
        setTradeNumberTextFieldForSearch();
        tradeSearchByTradeNumber();
        System.out.println("Заказчик открывает карточку контракта через треугольник 'Направьте контракт'");
        $(By.xpath(contractSendTriangleXPath)).shouldBe(Condition.visible).click();
        System.out.println(timer.getCurrentTimeMillis());
    }

    public void contractResendTriangleOpen(String tradeNumber) throws Exception{
        this.tradeNumber = tradeNumber;
        isPageLoaded();
        setTradeNumberTextFieldForSearch();
        tradeSearchByTradeNumber();
        System.out.println("Заказчик открывает карточку контракта через треугольник 'Перенаправьте контракт'");
        $(By.xpath(contractResendTriangleXPath)).shouldBe(Condition.visible).click();
        System.out.println(timer.getCurrentTimeMillis());
    }

    public void contractSignTriangleOpen(String tradeNumber) throws Exception{
        this.tradeNumber = tradeNumber;
        isPageLoaded();
        setTradeNumberTextFieldForSearch();
        tradeSearchByTradeNumber();
        System.out.println("Заказчик открывает карточку контракта через треугольник 'Подпишите контракт'");
        $(By.xpath(contractSignTriangleXPath)).shouldBe(Condition.visible).click();
        System.out.println(timer.getCurrentTimeMillis());
    }

    private void setPriceFields() throws Exception{
        setFocusInKendoNumericTextBoxJS(startPriceFieldId);
        $(By.id(startPriceFieldId)).setValue("100,00");
        setFocusInKendoNumericTextBoxJS(endPriceFieldId);
        $(By.id(endPriceFieldId)).setValue("100,00");
    }

    private void selectFewApplications() throws Exception{
        Thread.currentThread().sleep(2000);
        //setPriceFields();
        //setDateFieldForSearch();
        //$(By.id(tradeNameTextFieldId)).setValue("производительности");
        //$(considerationFilterButton).click();
        //waitForLoadingImage();
        setTradeNumberTextFieldForSearch();
        $(searchButton).click();
        waitForLoadingImage();
        ElementsCollection applications = $$(applicationCheckBox);
        int i = 0;
        for(SelenideElement currentApplication : applications){
            if(i >= 2) break;
            Actions builder = new Actions(driver);
            builder.moveToElement(currentApplication, 2, 2).click().build().perform();
            Thread.currentThread().sleep(200);
            i++;
        }
    }

    private void downloadSelectedApplications(){
        System.out.println("Заказчик выгружает документы из реестра извещений");
        $(downloadDocsButton).shouldBe(Condition.visible).click();
        $(By.id(downloadDocsButtonInPopUpId)).shouldBe(Condition.visible).click();
        System.out.println(timer.getCurrentTimeMillis());
        $(By.id(cancelDownloadButtonId)).shouldNotBe(Condition.visible);
        System.out.println(timer.getCurrentTimeMillis());
    }

    public void downloadApplications(String tradeNumber) throws Exception{
        this.tradeNumber = tradeNumber;
        selectFewApplications();
        downloadSelectedApplications();
    }

}
