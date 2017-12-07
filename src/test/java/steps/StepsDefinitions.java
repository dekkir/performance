package steps;

import AdminPages.AdminAuctionManagementPage;
import AdminPages.AdminContractConclusionAdministrationPage;
import AdminPages.AdminMainPage;
import AdminPages.AdminProcedureAccelerationPage;
import CustomerPages.*;
import SupplierPages.*;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import helpers.WebDriverContainer;
import pages.CommonLogInPage;
import pages.MainPage;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import CustomerPages.CustomerLogInPage;
import CustomerPages.CustomerMainPage;
import CustomerPages.CustomerMasterPage;

public class StepsDefinitions extends CommonStepDefinition{


    private String tradeNumber;
    private String auctionNumber;
    private String tradeName;
    private String contractId;
    private String[] secondaryTradesNames = {"", ""};
    private String[][] secondaryTradesInfo = {
            {"", ""},
            {"", ""}
    };
    private MainPage mainPage = new MainPage();
    private CommonLogInPage commonLogInPage = new CommonLogInPage();

    private SupplierLogInPage supplierLogInPage = new SupplierLogInPage();
    private SupplierMainPage supplierMainPage = new SupplierMainPage();
    private SupplierMyApplicationsPage supplierMyApplicationsPage = new SupplierMyApplicationsPage();
    private SupplierApplicationPage supplierApplicationPage = new SupplierApplicationPage();
    private SupplierMyTradesPage supplierMyTradesPage = new SupplierMyTradesPage();
    private SupplierMyContractsPage supplierMyContractsPage = new SupplierMyContractsPage();
    private SupplierContractPage supplierContractPage = new SupplierContractPage();
    private SupplierTenderCard supplierTenderCard = new SupplierTenderCard();
    private SupplierDocumentsExplainingRequest supplierDocumentsExplainingRequest = new SupplierDocumentsExplainingRequest();
    private SupplierTradeSearchPage supplierTradeSearchPage = new SupplierTradeSearchPage();
    private SupplierApplicationAddingPage supplierApplicationAddingPage = new SupplierApplicationAddingPage();
    private SupplierAuctionInfoPage supplierAuctionInfoPage = new SupplierAuctionInfoPage();
    private SupplierAuctionPage supplierAuctionPage = new SupplierAuctionPage();
    private SupplierActualPropositionsPage supplierActualPropositionsPage = new SupplierActualPropositionsPage();

    private CustomerLogInPage customerLogInPage = new CustomerLogInPage();
    private CustomerMainPage customerMainPage = new CustomerMainPage();
    private CustomerMasterPage customerMasterPage = new CustomerMasterPage();
    private CustomerAuctionCreatingPage customerAuctionCreatingPage = new CustomerAuctionCreatingPage();
    private CustomerMyTradesPage customerMyTradesPage = new CustomerMyTradesPage();
    private CustomerTenderCard customerTenderCard = new CustomerTenderCard();
    private CustomerConsiderationProtocolPage customerConsiderationProtocolPage = new CustomerConsiderationProtocolPage();
    private CustomerSummarizingProtocolPage customerSummarizingProtocolPage = new CustomerSummarizingProtocolPage();
    private CustomerContractPage customerContractPage = new CustomerContractPage();
    private CustomerMyContractsPage customerMyContractsPage = new CustomerMyContractsPage();

    private AdminMainPage adminMainPage = new AdminMainPage();
    private AdminProcedureAccelerationPage adminProcedureAccelerationPage = new AdminProcedureAccelerationPage();
    private AdminAuctionManagementPage adminAuctionManagementPage = new AdminAuctionManagementPage();
    private AdminContractConclusionAdministrationPage adminContractConclusionAdministrationPage = new AdminContractConclusionAdministrationPage();

    private String logsPath = "C://Users//User3//IdeaProjects//Pr44//build//reports//logs//";
    private String fileName;
    public PrintStream printStream;

    public StepsDefinitions(){
        super();
    }

    @Given("^Создается файл для метрик$")
    public void fileForReportCreate() throws IOException{
        fileName = "supplierAndCustomerPerformanceTest.txt";
        File file = new File(logsPath + fileName);
        printStream = new PrintStream(file);
        System.setOut(printStream);
        System.out.println("Test starts " + timer.getDateForReport());
    }

    @When("^Заказчик входит в систему со снятием метрик$")
    public void customerEntersSystemWithReport() throws Exception{
        mainPage.openLoginPage();
        commonLogInPage.customerLogInPageOpen();
        customerLogInPage.logInWithCertificate(true);
        customerMainPage.isLoggedIn();
        waitForLoadingImage();
        System.out.println(timer.getCurrentTimeMillis());
        webDriverContainer.getWebDriver().navigate().refresh();
        waitForLoadingImage();
        System.out.println(timer.getCurrentTimeMillis());
    }

    @And("^Заказчик снимает метрики$")
    public void customerCollectMetrics() throws Exception{
        System.out.println("Заказчик открывает реестр закупок");
        System.out.println(timer.getCurrentTimeMillis());
        customerMainPage.mainMenuItemTradesOpen();
        customerMyTradesPage.isPageLoaded();
        waitForLoadingImage();
        System.out.println(timer.getCurrentTimeMillis());
        System.out.println("Заказчик открывает реестр контрактов");
        customerMyTradesPage.mainMenuItemContractsOpen();
        System.out.println(timer.getCurrentTimeMillis());
        customerMyContractsPage.isPageLoaded();
        waitForLoadingImage();
        System.out.println(timer.getCurrentTimeMillis());
    }

    @And("^Заказчик создает три аукциона$")
    public void customerCreatesAuction() throws Throwable, Exception{
        String currentTradeName = "";
        customerMainPage.mainMenuItemCustomerMasterOpen();
        for(int eaNumber = 0; eaNumber <= 2; eaNumber++ ){
            customerMasterPage.newAuctionCreating();
            currentTradeName = customerAuctionCreatingPage.createNewEA(eaNumber);
            if(eaNumber == 0) this.tradeName = currentTradeName;
            else secondaryTradesNames[eaNumber-1] = currentTradeName;
        }
        customerMasterPage.mainMenuItemTradesOpen();
    }

    @And("^Заказчик получает информацию о созданных аукционах$")
    public void customerGetsInfoAboutAuctions() throws Exception{
        Thread.currentThread().sleep(300000);
        ArrayList<String> info = customerGetsAuctionInfo(tradeName, true);
        this.tradeNumber = info.get(1);
        this.auctionNumber = info.get(0);
        for(int i = 0; i <= 1; i++){
            String currentTradeName = secondaryTradesNames[i];
            ArrayList<String> currentTradeInfo = customerGetsAuctionInfo(currentTradeName, false);
            secondaryTradesInfo[i][0] = currentTradeInfo.get(0);
            secondaryTradesInfo[i][1] = currentTradeInfo.get(1);
        }
    }

    @Then("^Заказчик выходит из системы$")
    public void customerExitSystem() throws Exception{
        exitSystem("customer");
        mainPage.isPageLoaded();
    }

    @When("^Поставщик безденежный входит в систему$")
    public void supplierWithoutMoneyEntersSystem() throws Exception {
        mainPage.openLoginPage();
        commonLogInPage.supplierLogInPageOpen();
        supplierLogInPage.logInWithCertificate("Автотест Без Денег", false);
        supplierMainPage.isLoggedIn();
        waitForLoadingImage();
    }

    @And("^Поставщик делает поиск закупок$")
    public void supplierWithoutMoneySearchForTrades(){
        supplierMainPage.mainMenuTradeSearchOpen();
        //9999000041217011564
        //9999000051217012602
    }

    @Then("^Поставщик подает отложенные заявки$")
    public void supplierCreatesDelayedApplications() throws Exception{
        for(int i = 11564; i <= 12602; i++){
            String number = "99990000412170" + i;
            supplierMainPage.mainMenuTradeSearchOpen();
            supplierTradeSearchPage.searchAndOpenByTradeNumber(number);
        }
    }

    @When("^Поставщик \"([^\"]*)\" входит в систему со снятием метрик$")
    public void supplierEntersSystemWithReport(String supplierNumber) throws Exception{
        mainPage.openLoginPage();
        commonLogInPage.supplierLogInPageOpen();
        System.out.println("Поставщик выполняет вход по сертификату");
        supplierLogInPage.logInWithCertificate("Участник для отладки " + supplierNumber, true);
        supplierMainPage.isLoggedIn();
        waitForLoadingImage();
        System.out.println(timer.getCurrentTimeMillis());
        WebDriverContainer.getInstance().getWebDriver().navigate().refresh();
        waitForLoadingImage();
        System.out.println(timer.getCurrentTimeMillis());
    }

    @And("^Поставщик снимает метрики о заявках$")
    public void supplierCollectsMetricsAboutApplications() throws Exception{
        System.out.println("Поставщик открывает реестр заявок");
        System.out.println(timer.getCurrentTimeMillis());
        supplierMainPage.mainMenuMyApplicationsOpen();
        System.out.println(timer.getCurrentTimeMillis());
        supplierMyApplicationsPage.setDateForSearch();
        supplierMyApplicationsPage.tradeSearch();
        System.out.println("Поставщик открывает первую подходящую карточку заявки из найденных");
        System.out.println(timer.getCurrentTimeMillis());
        supplierMyApplicationsPage.firstFitApplicationOpen();
        supplierApplicationPage.isPageLoaded();
        System.out.println(timer.getCurrentTimeMillis());
        supplierApplicationPage.goToSupplierMainPage();
    }

    @Then("^Поставщик подает заявку на участие в торгах со снятием метрик$")
    public void supplierCreatesApplicationWithReport() throws Exception{
        supplierMainPage.mainMenuTradeSearchOpen();
        supplierTradeSearchPage.searchAndOpenByTradeNumber(secondaryTradesInfo[0][1]);
        //Подача заявки на аукцион
        supplierTenderCard.isPageLoaded();
        supplierTenderCard.applicationAdd();
        supplierApplicationAddingPage.createApplication(true);
        //System.out.println(timer.getCurrentTimeMillis());
        supplierActualPropositionsPage.tradeSearchOpen();
        supplierTradeSearchPage.searchAndOpenByTradeNumber(secondaryTradesInfo[0][1]);
        //Thread.currentThread().sleep(40000);
        supplierTenderCard.applicationOpen();
        supplierApplicationPage.withdrawAndRecreateApplication();
    }

    @Then("^Поставщик подает заявку на участие в торгах$")
    public void supplierCreatesApplication() throws Exception{
        supplierMainPage.mainMenuTradeSearchOpen();
        supplierTradeSearchPage.searchAndOpenByTradeNumber(tradeNumber);
        //Подача заявки на второстепенный аукцион
        supplierTenderCard.isPageLoaded();
        supplierTenderCard.applicationAdd();
        supplierApplicationAddingPage.createApplication(false);
        supplierActualPropositionsPage.goToSupplierMainPage();
    }

    @And("^Поставщик подает запрос на разъяснение документации$")
    public void supplierCreatesDocumentExplainingRequest() throws Exception{
        supplierMainPage.mainMenuTradeSearchOpen();
        //Отправка запроса на разъяснение документации
        supplierTradeSearchPage.searchAndOpenByTradeNumber(tradeNumber);
        supplierTenderCard.addDocumentsExplainingRequest();
        supplierDocumentsExplainingRequest.createRequest();
        supplierTenderCard.goToSupplierMainPage();
    }

    @And("^Поставщик снимает метрики о торгах и контрактах$")
    public void supplierCollectsMetricsAboutContracts() throws Exception{
        System.out.println("Поставщик открывает реестр торгов и переторжек");
        System.out.println(timer.getCurrentTimeMillis());
        supplierMainPage.mainMenuMyTradesOpen();
        supplierMyTradesPage.isPageLoaded();
        waitForLoadingImage();
        System.out.println(timer.getCurrentTimeMillis());
        System.out.println("Поставщик открывает реестр контрактов");
        System.out.println(timer.getCurrentTimeMillis());
        supplierMyTradesPage.mainMenuItemContractsOpen();
        supplierMyContractsPage.isPageLoaded();
        waitForLoadingImage();
        System.out.println(timer.getCurrentTimeMillis());
        System.out.println("Поставщик открывает первый подходящий контракт из реестра");
        supplierMyContractsPage.selectContractStatus("Контракт подписан участником");
        supplierMyContractsPage.searchContractWithFilters();
        supplierMyContractsPage.firstFitContractOpen();
        WebDriverContainer.getInstance().switchToNewWindow();
        supplierContractPage.isPageLoaded();
        waitForLoadingImage();
        System.out.println(timer.getCurrentTimeMillis());
        supplierContractPage.goToSupplierMainPage();
    }

    @And("^Поставщик выходит из системы$")
    public void supplierExitSystem() throws Exception{
        exitSystem("supplier");
        mainPage.isPageLoaded();
    }

    @When("^Поставщик \"([^\"]*)\" входит в систему$")
    public void supplierEntersSystem(String supplierNumber) throws Exception{
        mainPage.openLoginPage();
        commonLogInPage.supplierLogInPageOpen();
        supplierLogInPage.logInWithCertificate("Участник для отладки " + supplierNumber, false);
        supplierMainPage.isLoggedIn();
        waitForLoadingImage();
    }

    @When("^Заказчик входит в систему$")
    public void customerEntersSystem() throws Exception{
        mainPage.openLoginPage();
        commonLogInPage.customerLogInPageOpen();
        customerLogInPage.logInWithCertificate(false);
        customerMainPage.isLoggedIn();
        waitForLoadingImage();
    }

    @When("^Администратор входит в систему$")
    public void adminEntersSystem() throws Exception{
        mainPage.openLoginPage();
        commonLogInPage.supplierLogInPageOpen();
        supplierLogInPage.logInWithCertificate("Админ для отладки 0", false);
        adminMainPage.isPageLoaded();
    }

    @And("^Администратор открывает страницу ускорения процедур$")
    public void adminOpensAccelerationPage() throws Exception{
        adminMainPage.mainMenuProcedureAccelerationOpen();
    }

    @Then("^Администратор ускоряет закупки до этапа рассмотрения заявок$")
    public void adminAcceleratesTradeToApplicationsReception() throws Exception{
        adminProcedureAccelerationPage.procedureAccelerate(auctionNumber, "applicationReceptionEnds");
        for(int i = 0; i <= 1; i++){
            adminProcedureAccelerationPage.procedureAccelerate(secondaryTradesInfo[i][0], "applicationReceptionEnds");
        }
        Thread.currentThread().sleep(330000);
    }

    @Then("^Администратор ускоряет закупку до этапа начала торгов$")
    public void adminAcceleratesTradeToConsiderationEnds() throws Exception{
        adminProcedureAccelerationPage.procedureAccelerate(auctionNumber, "considerationEnds");
        Thread.currentThread().sleep(330000);
    }

    @Then("^Администратор ускоряет закупку до этапа подведения итогов торгов$")
    public void adminAcceleratesTradeToAuctionEnd() throws Exception{
        adminProcedureAccelerationPage.procedureAccelerate(auctionNumber, "auctionEnds");
        adminAuctionManagementPage.endAuction(auctionNumber);
        Thread.currentThread().sleep(300000);
    }

    @And("^Администратор открывает страницу сроков подписания контракта$")
    public void adminOpensContractAccelerationPage() throws Exception{
        adminMainPage.mainMenuContractConclusionAdministrationOpen();
    }

    @Then("^Администратор сдвигает регламентный срок подписания контракта$")
    public void adminAcceleratesContractConclusion() throws Exception{
        adminContractConclusionAdministrationPage.accelerateContractConclusion(contractId);
        Thread.currentThread().sleep(180000);
    }

    @And("^Администратор выходит из системы$")
    public void adminExitSystem() throws Exception{
        exitSystem("admin");
        mainPage.isPageLoaded();
    }

    @And("^Заказчик открывает форму протокола рассмотрения заявок со снятием метрик$")
    public void customerOpensConsiderationFormWithReport() throws Exception{
        customerMainPage.mainMenuItemTradesOpen();
        customerMyTradesPage.considerApplicationsTriangleOpen(tradeNumber, true);
        customerConsiderationProtocolPage.isPageLoaded();
        waitForLoadingImage();
        System.out.println(timer.getCurrentTimeMillis());
        customerConsiderationProtocolPage.mainMenuItemTradesOpen();
        customerMyTradesPage.searchAndOpenTradeByTradeNumber(tradeNumber);
        customerTenderCard.openConsiderationProtocolForm();
        customerConsiderationProtocolPage.isPageLoaded();
        waitForLoadingImage();
        System.out.println(timer.getCurrentTimeMillis());
    }
/*
    @And("^Заказчик открывает форму протокола рассмотрения заявок по закупке c номером \"([^\"]*)\"$")
    public void customerOpensConsiderationForm(String tradeNumber) throws Exception{
        customerMainPage.mainMenuItemTradesOpen();
        customerMyTradesPage.considerApplicationsTriangleOpen(tradeNumber, false);
        customerConsiderationProtocolPage.isPageLoaded();
    }
*/
    @Then("^Заказчик создает протокол рассмотрения двух заявок$")
    public void customerCreatesProtocolWithTwoApplications() throws Exception{
        customerConsiderationProtocolPage.createAndPublishConsiderationProtocol(2);
        customerConsiderationProtocolPage.mainMenuItemTradesOpen();
    }

    @Then("^Заказчик создает протокол рассмотрения одной заявки$")
    public void customerCreatesProtocolWithOneApplication() throws Exception{
        customerMainPage.mainMenuItemTradesOpen();
        customerMyTradesPage.considerApplicationsTriangleOpen(secondaryTradesInfo[0][1], false);
        customerConsiderationProtocolPage.isPageLoaded();
        customerConsiderationProtocolPage.createAndPublishConsiderationProtocol(1);
        customerConsiderationProtocolPage.mainMenuItemTradesOpen();
    }

    @Then("^Заказчик создает протокол о признании закупки несостоявшейся$")
    public void customerCreatesProtocolWithoutApplications() throws Exception{
        customerMainPage.mainMenuItemTradesOpen();
        customerMyTradesPage.considerApplicationsTriangleOpen(secondaryTradesInfo[1][1], false);
        customerConsiderationProtocolPage.isPageLoaded();
        customerConsiderationProtocolPage.createAndPublishConsiderationProtocol(0);
        customerConsiderationProtocolPage.mainMenuItemTradesOpen();
    }

    @And("^Поставщик открывает страницу подачи ценовых предложений$")
    public void supplierOpensBidSubmitPage() throws Exception{
        //Поиск в разделе "Поиск закупок"
        supplierMainPage.mainMenuTradeSearchOpen();
        supplierTradeSearchPage.searchAndOpenByTradeNumber(tradeNumber);
        //Подача заявки на аукцион
        supplierTenderCard.isPageLoaded();
        supplierTenderCard.auctionInfoOpen();
        supplierAuctionInfoPage.auctionOpen();
    }

    @And("^Поставщик подает ценовое предложение на аукционе со снятием метрик$")
    public void supplierSubmitBitWithReport() throws Exception{
        supplierAuctionPage.createAndSubmitNewBid(true);
        supplierAuctionPage.goToSupplierMainPage();
    }

    @And("^Поставщик подает ценовое предложение на аукционе$")
    public void supplierSubmitBid() throws Exception{
        supplierAuctionPage.createAndSubmitNewBid(false);
        supplierAuctionPage.goToSupplierMainPage();
    }

    @And("^Заказчик открывает форму протокола подведения итогов торгов$")
    public void customerOpensSummarizingForm() throws Exception{
        customerMainPage.mainMenuItemTradesOpen();
        customerMyTradesPage.summarizeTriangleOpen(tradeNumber);
        customerSummarizingProtocolPage.isPageLoaded();
        waitForLoadingImage();
        System.out.println(timer.getCurrentTimeMillis());
        customerSummarizingProtocolPage.mainMenuItemTradesOpen();
        customerMyTradesPage.searchAndOpenTradeByTradeNumber(tradeNumber);
        customerTenderCard.viewTenderHistory();
        customerTenderCard.openSummarizingProtocolForm();
        customerSummarizingProtocolPage.isPageLoaded();
        waitForLoadingImage();
        System.out.println(timer.getCurrentTimeMillis());
    }

    @Then("^Заказчик создает протокол подведения итогов торгов$")
    public void customerCreatesSummarizingProtoc() throws Exception{
        customerSummarizingProtocolPage.createAndPublishSummarizingProtocol();
        customerTenderCard.mainMenuItemTradesOpen();
        Thread.currentThread().sleep(30000);
    }

    @And("^Заказчик отправляет контракт на подпись поставщику$")
    public void customersSendsContract() throws Exception{
        customerMyTradesPage.contractSendTriangleOpen(tradeNumber);
        customerContractPage.isPageLoaded();
        waitForLoadingImage();
        System.out.println(timer.getCurrentTimeMillis());
        customerContractPage.createAndSendContract();
        customerContractPage.mainMenuItemTradesOpen();
    }

    @And("^Поставщик открывает контракт через треугольник 'Подпишите контракт' со снятием метрик$")
    public void supplierOpensContractPageViaTrgSignWithReport() throws Exception{
        supplierMainPage.mainMenuItemContractsOpen();
        System.out.println("Поставщик открывает контракт через треугольник 'Подпишите контракт'");
        supplierMyContractsPage.searchAndOpenContractByTradeNumberByTriangle(tradeNumber, true);
        supplierContractPage.isPageLoaded();
        waitForLoadingImage();
        System.out.println(timer.getCurrentTimeMillis());
    }

    @Then("^Поставщик создает протокол разногласий$")
    public void supplierCreatesDifferencesProtocol() throws Exception{
        System.out.println("Поставщик создает протокол разногласий");
        supplierContractPage.createDifferenceProtocol();
        supplierContractPage.goToSupplierMainPage();
        Thread.currentThread().sleep(60000);
    }

    @And("^Заказчик открывает контракт через треугольник 'Переотправьте контракт'$")
    public void customerOpensContractPageViaTrgResend() throws Exception{
        customerMainPage.mainMenuItemTradesOpen();
        customerMyTradesPage.downloadApplications(tradeNumber);
        customerMyTradesPage.contractResendTriangleOpen(tradeNumber);
        customerContractPage.isPageLoaded();
        waitForLoadingImage();
        System.out.println(timer.getCurrentTimeMillis());
        String currentURL = WebDriverContainer.getInstance().getWebDriver().getCurrentUrl();
        this.contractId = currentURL.substring(currentURL.lastIndexOf('/' ) + 1);
    }

    @Then("^Заказчик вносит изменения в контракт$")
    public void customerMakesChangesToContract() throws Exception{
        customerContractPage.makeChange();
        customerContractPage.downloadDocs();
        customerContractPage.mainMenuItemTradesOpen();
    }

    @And("^Поставщик открывает контракт через треугольник 'Подпишите контракт'$")
    public void supplierOpensContractPageViaTrgSign() throws Exception{
        supplierMainPage.mainMenuItemContractsOpen();
        supplierMyContractsPage.searchAndOpenContractByTradeNumberByTriangle(tradeNumber, false);
    }

    @Then("^Поставщик подписывает контракт$")
    public void supplierSignsContract() throws Exception{
        System.out.println("Поставщик подписывает контракт");
        supplierContractPage.acceptAndSignContract();
        supplierContractPage.downloadDocs();
        supplierContractPage.goToSupplierMainPage();
    }

    @And("^Заказчик открывает контракт через треугольник 'Подпишите контракт'$")
    public void customerOpensContractPageViaTrgSing() throws Exception{
        customerMainPage.mainMenuItemTradesOpen();
        customerMyTradesPage.contractSignTriangleOpen(tradeNumber);
        customerContractPage.isPageLoaded();
        waitForLoadingImage();
        System.out.println(timer.getCurrentTimeMillis());
    }

    @Then("^Заказчик подписывает контракт$")
    public void customerSignsContract() throws Exception{
        customerContractPage.signContract();
    }

    @Then("^Закрытие файла отчета$")
    public void reportFileClose(){
        System.out.println("Test ends " + timer.getDateForReport());
        printStream.close();
    }

    private ArrayList<String> customerGetsAuctionInfo(String tradeName, boolean timeReport) throws Exception{
        customerMyTradesPage.searchAndOpenTradeByTradeName(tradeName, timeReport);
        customerTenderCard.isPageLoaded();
        waitForLoadingImage();
        if(timeReport)
            System.out.println(timer.getCurrentTimeMillis());
        ArrayList<String> tradeInfo = customerTenderCard.getTradeInfo();
        customerTenderCard.mainMenuItemTradesOpen();
        return tradeInfo;
    }
}
