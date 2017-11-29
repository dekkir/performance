package CustomerPages;

import com.codeborne.selenide.*;
import helpers.AuctionDates;
import helpers.Timer;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class CustomerAuctionCreatingPage extends CustomerMasterPage{

    private static final String tradeName = "Тест производительности ";
    private static final String phoneCityCode = "123";
    private static final String phoneNumber = "1231231";
    private static String tradeNameFinal;
    private static int auctionNumber;
    private static final String purchaseId = "111111111111111111111111111111111111";
    private static final String purchaseStartingSum = "100,00";
    private static final String destinationPlaceForProduct = "место";
    private static final String deadlineForProductDelivery = "10.10";
    private static final String customerInn = "4205081030";
    private static final String financeSourceName = "Федеральный бюджет";
    private static final String okpdCode = "01.11.11.111";

    private static final String tradeNameTextField = "input#ContractName";
    private static final String nextStepButtonId = "nextstepbutton";

    private static final String phoneCityCodeTextFieldId = "Contacts_PhoneCityCode";
    private static final String phoneNumberTextFieldId = "Contacts_PhoneMain";

    private static final String placeForApplicationSubmittingFormFillButtonId = "ApplicationSubmittingPlaceButton";
    private static final String placeForApplicationSubmittingFormFillTextFieldId = "ApplicationSubmittingPlace";
    private static final String orderOfApplicationSubmittingFormFillButtonId = "ApplicationSubmittingOrderButton";
    private static final String orderOfApplicationSubmittingFormFillTextFieldId = "ApplicationSubmittingOrder";
    private static final String applicationSubmitEndDateFieldId = "SubmittingEndDate_Date";
    private static final String applicationSubmitEndTimeHoursFieldId = "SubmittingEndDate_Hours";
    private static final String applicationSubmitEndTimeMinutesFieldId = "SubmittingEndDate_Minutes";
    private static final String applicationReviewEndDateFieldId = "ReviewEndDate";
    private static final String auctionStartingDateFieldId = "auctionDateInElectronicFormDP";
    private static final String financeSourceSelector = "span.k-widget[aria-owns='FinanceSourceType_listbox']";
    private static final String financeSourceSelectorFederalMoneyOption = "ul#FinanceSourceType_listbox li[data-offset-index='1']";
    private static final String financeSourceTextFieldId = "FinanceSource";
    private static final String addCustomerRequirementButtonId = "add-customer";
    private static final String customerRequirementWindow = "span#CustomerWindow_wnd_title~div>a>span.k-icon.k-i-close";
    private static final String purchaseIdTextFieldId = "PurchaseCode";
    private static final String purchaseStartingSumTextFieldId = "CustomerContractSum";
    private static final String destinationPlaceForProductTextFieldId = "Place";
    private static final String deadlineForProductDeliveryTextFieldId = "Deadlines";
    private static final String textFieldFillingButton = "div#AddCustomerBlock div.form-horizontal div.well div.form-group span div.form-block button.k-button.button-in-text";
    private static final String searchForCustomerButtonId = "SearchCustomerButton";

    private static final String customerSearchingWindow = "span#CustomerSearchWindow_wnd_title~div>a>span.k-icon.k-i-close";
    private static final String customerInnTextFieldId = "CustomerInnSearch";
    private static final String searchForCustomerInPopUpWindowButtonId = "SearchCustomerWindowButton";
    private static final String firstFitCustomer = "table.k-selectable tbody tr td";
    private static final String selectCustomerButtonId = "SelectCustomerWindowButton";
    private static final String saveCustomerRequirementButton = "div#AddCustomerBlock > div.buttons-wrap > button";
    private static final String okpdCodeTextFieldId = "Okpd2Code";
    private static final String tradeNameTextFieldId = "Name";
    private static final String measurementSelector = "span.k-icon.k-i-arrow-s";
    private static final String measurementOptionKg = "li.k-item[data-offset-index='120']";
    private static final String quantityOfProductsTextFieldId = "Quantity";
    private static final String metricsPriceTextFieldId = "MetricsPrice";
    private static final String productAddingButtonId = "add-product";
    private static final String addedProduct = "div#layoutwrapper.layout.blockCenter div.well div#ProductsGrid.k-grid.k-widget table tr td button.k-button.k-state-default";

    private static final String documentAddingButton = "input[accept]";
    private static final String fileDeletingButton = "div#FilesGrid table tbody tr td a[onclick]";
    private static final String sendingToEISButtonId = "loginbutton";

    private AuctionDates auctionDates = new AuctionDates();
    private Timer timer = new Timer();

    public CustomerAuctionCreatingPage(){
        super();
    }

    private void tradeNaming(){
        if(auctionNumber == 0)
            tradeNameFinal = tradeName + timer.getDateForReport();
        else
            tradeNameFinal = tradeName + timer.getDateForReport() + " " + auctionNumber;
        $(tradeNameTextField).setValue(tradeNameFinal);
    }

    private void phoneNumberFilling(){
        $(By.id(phoneCityCodeTextFieldId)).shouldBe(Condition.visible).setValue(phoneCityCode);
        $(By.id(phoneNumberTextFieldId)).setValue(phoneNumber);
        $(By.id(phoneCityCodeTextFieldId)).shouldHave(Condition.value(phoneCityCode));
        $(By.id(phoneNumberTextFieldId)).shouldHave(Condition.value(phoneNumber));
    }

    private void nextStepPassing() throws InterruptedException{
        $(By.id(nextStepButtonId)).click();
        $(By.id(logOutButtonId)).should(Condition.appear);
        Thread.currentThread().sleep(1000);
    }

    private void settingsFieldsForApplicationSubmittingFilling() throws Exception{
        $(By.id(placeForApplicationSubmittingFormFillTextFieldId)).click();
        $(By.id(placeForApplicationSubmittingFormFillButtonId)).click();
        Thread.currentThread().sleep(500);
        $(By.id(orderOfApplicationSubmittingFormFillTextFieldId)).click();
        $(By.id(orderOfApplicationSubmittingFormFillButtonId)).click();
        Thread.currentThread().sleep(500);
    }

    private void dateFieldsFilling() throws InterruptedException{
        $(By.id(applicationSubmitEndDateFieldId)).setValue(auctionDates.getNextDate()).pressTab();
        $(By.id(applicationSubmitEndTimeHoursFieldId)).setValue("10").pressTab();
        $(By.id(applicationSubmitEndTimeMinutesFieldId)).setValue("10");
        $(By.id(applicationReviewEndDateFieldId)).setValue(auctionDates.getNextDate()).pressTab();
        Thread.currentThread().sleep(1000);
        if($(By.id(auctionStartingDateFieldId)).getValue().equals(""))
            Thread.currentThread().sleep(1000);

    }

    private void financeSourceFilling() throws InterruptedException{
        $(financeSourceSelector).click();
        Thread.currentThread().sleep(500);
        $(financeSourceSelectorFederalMoneyOption).shouldBe(Condition.visible).click();
        Thread.currentThread().sleep(500);
        $(By.id(financeSourceTextFieldId)).setValue(financeSourceName);
        $(By.id(financeSourceTextFieldId)).shouldHave(Condition.value(financeSourceName));
        Thread.currentThread().sleep(500);
    }

    private void addCustomerRequirement() throws Exception{
        $(By.id(addCustomerRequirementButtonId)).shouldBe(Condition.visible).click();
        Thread.currentThread().sleep(2000);
        $(customerRequirementWindow).shouldBe(Condition.visible);
        $(By.id(searchForCustomerButtonId)).shouldBe(Condition.visible).click();
        Thread.currentThread().sleep(2000);
        $(customerSearchingWindow).shouldBe(Condition.visible);
        $(By.id(customerInnTextFieldId)).setValue(customerInn);
        $(By.id(customerInnTextFieldId)).shouldHave(Condition.value(customerInn));
        $(By.id(searchForCustomerInPopUpWindowButtonId)).click();
        $(firstFitCustomer).click();
        $(By.id(selectCustomerButtonId)).click();
        $(customerSearchingWindow).shouldNotBe(Condition.visible);
        $(By.id(purchaseIdTextFieldId)).setValue(purchaseId);
        setFocusInKendoNumericTextBoxJS(purchaseStartingSumTextFieldId);
        SelenideElement purchaseSumTextField = $(By.id(purchaseStartingSumTextFieldId));
        purchaseSumTextField.clear();
        purchaseSumTextField.click();
        purchaseSumTextField.setValue(purchaseStartingSum);
        $(By.id(destinationPlaceForProductTextFieldId)).setValue(destinationPlaceForProduct);
        $(By.id(deadlineForProductDeliveryTextFieldId)).setValue(deadlineForProductDelivery);
        $$(textFieldFillingButton).get(0).click();
        $$(textFieldFillingButton).get(1).click();
        $(saveCustomerRequirementButton).click();
        $(customerRequirementWindow).shouldNotBe(Condition.visible);
    }

    private void tradeObjectInfoFilling() throws Exception{
        $(By.id(okpdCodeTextFieldId)).setValue(okpdCode);
        Thread.currentThread().sleep(500);
        $(By.id(okpdCodeTextFieldId)).pressTab();
        Thread.currentThread().sleep(500);
        $(By.id(tradeNameTextFieldId)).setValue(tradeName);
        $(By.id(tradeNameTextFieldId)).shouldHave(Condition.value(tradeName));
        $(measurementSelector).click();
        Thread.currentThread().sleep(150);
        $(measurementOptionKg).click();
        setFocusInKendoNumericTextBoxJS(quantityOfProductsTextFieldId);
        SelenideElement quantityOfProductsTextField = $(By.id(quantityOfProductsTextFieldId));
        quantityOfProductsTextField.clear();
        quantityOfProductsTextField.click();
        quantityOfProductsTextField.setValue("1");
        quantityOfProductsTextField.shouldHave(Condition.value("1"));
        setFocusInKendoNumericTextBoxJS(metricsPriceTextFieldId);
        SelenideElement metricsPriceTextField = $(By.id(metricsPriceTextFieldId));
        metricsPriceTextField.clear();
        metricsPriceTextField.click();
        metricsPriceTextField.setValue(purchaseStartingSum);
        metricsPriceTextField.shouldHave(Condition.value(purchaseStartingSum));
        $(By.id(productAddingButtonId)).click();
        Thread.currentThread().sleep(200);
        $(addedProduct).shouldBe(Condition.visible);
    }

    private void documentationAdding() throws Exception{
        Thread.currentThread().sleep(1000);
        $(documentAddingButton).sendKeys(commonDocument);
        Thread.currentThread().sleep(5000);
        $(fileDeletingButton).shouldBe(Condition.visible);
    }

    private void sendToEIS(){
        $(By.id(sendingToEISButtonId)).shouldBe(Condition.visible).click();
    }

    public String createNewEA(int auctionNumber) throws Exception{
        this.auctionNumber = auctionNumber;
        tradeNaming();
        nextStepPassing();
        phoneNumberFilling();
        nextStepPassing();

        settingsFieldsForApplicationSubmittingFilling();
        dateFieldsFilling();
        nextStepPassing();
        financeSourceFilling();
        addCustomerRequirement();
        nextStepPassing();
        tradeObjectInfoFilling();
        nextStepPassing();
        nextStepPassing();
        documentationAdding();
        nextStepPassing();
        nextStepPassing();
        sendToEIS();
        mainMenuItemCustomerMasterOpen();
        return tradeNameFinal;
    }

}
