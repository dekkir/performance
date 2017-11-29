import AdminPages.AdminAuctionManagementPage;
import AdminPages.AdminContractConclusionAdministrationPage;
import AdminPages.AdminMainPage;
import AdminPages.AdminProcedureAccelerationPage;
import CustomerPages.CustomerLogInPage;
import CustomerPages.CustomerMainPage;
import CustomerPages.CustomerMasterPage;
import SupplierPages.*;
import com.codeborne.selenide.Configuration;
import helpers.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.apache.poi.ss.usermodel.*;
import pages.*;
import java.io.*;
import static com.codeborne.selenide.Selenide.*;
import java.util.*;
import CustomerPages.*;

public class SupplierAndCustomerPerformanceTest  extends AbstractTest {

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

    private String logsPath = "C://Users//User3//IdeaProjects//_44rts//build//reports//logs//";
    private Workbook book;
    private Sheet sheet;
    private String xlsxFile = logsPath + "report.xlsx";
    private int workCellNumber;
    private static final int dateRowNumber = 3;
    private ArrayList<Double> supplierMetricList = new ArrayList<Double>();
    private ArrayList<Double> customerMetricList = new ArrayList<Double>();
    private ExcelStyles excelStyles;
    private String fileName;

    public SupplierAndCustomerPerformanceTest(){
        super();
    }

    private void setWorkCellNumber() {
        //int workCellNumber = 7;
        Row dateRow = sheet.getRow(dateRowNumber);
        int cellNumber;
        for(cellNumber = 7; cellNumber < dateRow.getLastCellNum(); cellNumber++ ){
            Cell currentCell = dateRow.getCell(cellNumber);
            if(currentCell.toString().equals("")){
                this.workCellNumber = cellNumber;
                return;
            }
        }
    }

    @Before
    public void settings() {
        Configuration.timeout = 90000;
    }

    @Test
    public void performanceTest() throws Exception {

        fileName = "supplierAndCustomerPerformanceTest" + timer.getDate() + ".txt";
        File file = new File(logsPath + fileName);
        PrintStream printStream = new PrintStream(file);
        System.setOut(printStream);
        System.out.println("Test starts " + timer.getDateForReport());

        //Trade creation starts
        this.tradeName = customerCreatesAuction(true, 0);
        //customerCreatesAuction(false);
        secondaryTradesNames[0] = customerCreatesAuction(false, 1);
        secondaryTradesNames[1] = customerCreatesAuction(false, 2);
        Thread.currentThread().sleep(240000);
        ArrayList<String> info = customerGetsAuctionInfo(tradeName, true);
        this.tradeNumber = info.get(1);
        this.auctionNumber = info.get(0);

        for(int i = 0; i <= 1; i++){
            String currentTradeName = secondaryTradesNames[i];
            ArrayList<String> currentTradeInfo = customerGetsAuctionInfo(currentTradeName, false);
            secondaryTradesInfo[i][0] = currentTradeInfo.get(0);
            secondaryTradesInfo[i][1] = currentTradeInfo.get(1);
        }
        supplierGetsInfo();
        suppliersCreateApplications(tradeNumber,2);
        suppliersCreateApplications(secondaryTradesInfo[0][1], 1);
        supplierCreatesDocumentExplainingRequest();
        supplierGetsInfo2();
        adminAccelerateProcedure(auctionNumber, "applicationReceptionEnds");
        for(int i = 0; i <= 1; i++){
            adminAccelerateProcedure(secondaryTradesInfo[i][0], "applicationReceptionEnds");
        }
        Thread.currentThread().sleep(300000);
        customerCreateConsiderationProtocol(tradeNumber, true, 2);
        customerCreateConsiderationProtocol(secondaryTradesInfo[0][1], false, 1);
        customerCreateConsiderationProtocol(secondaryTradesInfo[1][1], false, 0);
        //Ускорение до этапа начала торгов
        adminAccelerateProcedure(auctionNumber,"considerationEnds");
        Thread.currentThread().sleep(300000);
        suppliersSubmitBids();
        //Насильственное завершение аукциона
        adminAccelerateProcedure(auctionNumber,"auctionEnds");
        Thread.currentThread().sleep(300000);
        //Оформление протокола подведения итогов
        customerCreatesSummarizingProtocol();
        Thread.currentThread().sleep(60000);
        //Направление протокола разногласий
        supplierCreatesDifferenceProtocol();
        //Изменение контракта
        Thread.currentThread().sleep(60000);
        customerChangesContract();
        Thread.currentThread().sleep(60000);
        //Подписание контракта поставщиком
        supplierSignsContract();
        adminAccelerateContractConclusion();
        Thread.currentThread().sleep(180000);
        customerSignsContract();

        System.out.println("Test ends " + timer.getDateForReport());
        printStream.close();
    }

    @After
    public void reportCreating() throws IOException, InvalidFormatException{
        close();

        File file = new File(logsPath + fileName);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()) {
            String currentString = scanner.nextLine();
            if(currentString.startsWith(" ")){
                continue;
            }
            else if(currentString.startsWith("Поставщик выполняет вход")) {
                ArrayList<Long> list = new ArrayList<Long>();
                for (int i = 0; i <= 2; i++)
                    list.add(new Long(scanner.nextLine()));
                supplierMetricList.add(timer.getDifference(list.get(1), list.get(0)) - timer.getDifference(list.get(2), list.get(1)));
            }
            else if(currentString.startsWith("Поставщик")){
                ArrayList<Long> list = new ArrayList<Long>();
                for (int i = 0; i <= 1; i++)
                    list.add(new Long(scanner.nextLine()));
                supplierMetricList.add(timer.getDifference(list.get(1), list.get(0)));
            }
            else if(currentString.startsWith("Заказчик выполняет вход")) {
                ArrayList<Long> list = new ArrayList<Long>();
                for (int i = 0; i <= 2; i++)
                    list.add(new Long(scanner.nextLine()));
                customerMetricList.add(timer.getDifference(list.get(1), list.get(0)) - timer.getDifference(list.get(2), list.get(1)));
            }
            else if(currentString.startsWith("Заказчик")){
                ArrayList<Long> list = new ArrayList<Long>();
                for (int i = 0; i <= 1; i++)
                    list.add(new Long(scanner.nextLine()));
                customerMetricList.add(timer.getDifference(list.get(1), list.get(0)));
            }
        }
        scanner.close();
        String[] sheets = {"Supplier", "Customer"};
        String reportDate = timer.getDateForReport();
        for(int i = 0; i <= 1; i++) {
            FileInputStream inputStream = new FileInputStream(new File(xlsxFile));
            book = WorkbookFactory.create(inputStream);
            sheet = book.getSheet(sheets[i]);
            setWorkCellNumber();
            excelStyles = new ExcelStyles(book);
            int currentDateRowNumber = dateRowNumber;
            Row dateRow = sheet.getRow(currentDateRowNumber++);
            Cell dateCell = dateRow.getCell(workCellNumber);
            dateCell.setCellValue(reportDate);
            final int indicatorCellNumber = 6;
            ArrayList<Double> currentMetricsList;
            if(i == 0)
                currentMetricsList = supplierMetricList;
            else
                currentMetricsList = customerMetricList;
            for (double currentMetric : currentMetricsList) {
                Row currentRow = sheet.getRow(currentDateRowNumber++);
                Cell currentCell = currentRow.getCell(workCellNumber);
                currentCell.setCellValue(currentMetric);
                Cell comparedCell = currentRow.getCell(indicatorCellNumber);
                if ((Double.valueOf(comparedCell.toString()) >= currentMetric))
                    currentCell.setCellStyle(excelStyles.styleForGoodResult(book));
                else
                    currentCell.setCellStyle(excelStyles.styleForBadResult(book));
            }
            inputStream.close();
            FileOutputStream outputStream = new FileOutputStream(xlsxFile);
            book.write(outputStream);
            book.close();
            outputStream.close();
        }
    }

    private String customerCreatesAuction(boolean mainAuction, int auctionNumber) throws Exception{
        mainPage.openLoginPage();
        commonLogInPage.customerLogInPageOpen();
        customerLogInPage.logInWithCertificate(mainAuction);
        customerMainPage.isLoggedIn();
        if(mainAuction) {
            System.out.println(timer.getCurrentTimeMillis());
            webDriverContainer.getWebDriver().navigate().refresh();
            System.out.println(timer.getCurrentTimeMillis());
            System.out.println("Заказчик открывает реестр закупок");
            System.out.println(timer.getCurrentTimeMillis());
            customerMainPage.mainMenuItemTradesOpen();
            customerMyTradesPage.isPageLoaded();
            System.out.println(timer.getCurrentTimeMillis());
            customerMyTradesPage.downloadApplications();
            System.out.println("Заказчик открывает реестр контрактов");
            customerMyTradesPage.mainMenuItemContractsOpen();
            System.out.println(timer.getCurrentTimeMillis());
            customerMyContractsPage.isPageLoaded();
            System.out.println(timer.getCurrentTimeMillis());
        }
        customerMainPage.mainMenuItemCustomerMasterOpen();
        customerMasterPage.newAuctionCreating();
        String currentTradeName = customerAuctionCreatingPage.createNewEA(auctionNumber);
        customerMasterPage.logOut();
        mainPage.isPageLoaded();
        return currentTradeName;
    }

    private ArrayList<String> customerGetsAuctionInfo(String tradeName, boolean timeReport) throws Exception{
        mainPage.openLoginPage();
        commonLogInPage.customerLogInPageOpen();
        customerLogInPage.logInWithCertificate(false);
        customerMainPage.isLoggedIn();
        customerMainPage.mainMenuItemTradesOpen();
        customerMyTradesPage.searchAndOpenTradeByTradeName(tradeName, timeReport);
        customerTenderCard.isPageLoaded();
        if(timeReport)
            System.out.println(timer.getCurrentTimeMillis());
        ArrayList<String> tradeInfo = customerTenderCard.getTradeInfo();
        customerTenderCard.logOut();
        mainPage.isPageLoaded();
        return tradeInfo;
    }

    private void supplierGetsInfo() throws Exception{
        mainPage.openLoginPage();
        commonLogInPage.supplierLogInPageOpen();
        System.out.println("Поставщик выполняет вход по сертификату");
        supplierLogInPage.logInWithCertificate("Участник для отладки 2", true);
        supplierMainPage.isLoggedIn();
        System.out.println(timer.getCurrentTimeMillis());
        WebDriverContainer.getInstance().getWebDriver().navigate().refresh();
        System.out.println(timer.getCurrentTimeMillis());
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
        supplierApplicationPage.logOut();
        mainPage.isPageLoaded();
    }

    private void suppliersCreateApplications(String tradeNumber, int numberOfParticipants) throws Exception{
        for(int i = 1; i <= numberOfParticipants; i++){
            mainPage.openLoginPage();
            commonLogInPage.supplierLogInPageOpen();
            supplierLogInPage.logInWithCertificate("Участник для отладки " + i, false);
            supplierMainPage.isLoggedIn();
            //Поиск в разделе "Поиск закупок"
            supplierMainPage.mainMenuTradeSearchOpen();
            supplierTradeSearchPage.searchAndOpenByTradeName(tradeNumber);
            //Подача заявки на аукцион
            supplierTenderCard.isPageLoaded();
            supplierTenderCard.applicationAdd();
            if(i == 2) {
                supplierApplicationAddingPage.createApplication(true);
                //System.out.println(timer.getCurrentTimeMillis());
                supplierApplicationAddingPage.mainMenuTradeSearchOpen();
                supplierTradeSearchPage.searchAndOpenByTradeName(tradeNumber);
                supplierTenderCard.applicationOpen();
                supplierApplicationPage.withdrawAndRecreateApplication();
                supplierMainPage.logOut();
                mainPage.isPageLoaded();
            }
            else {
                supplierApplicationAddingPage.createApplication(false);
                supplierApplicationAddingPage.logOut();
                mainPage.isPageLoaded();
            }

        }
    }

    private void supplierCreatesDocumentExplainingRequest() throws Exception{
        mainPage.openLoginPage();
        commonLogInPage.supplierLogInPageOpen();
        supplierLogInPage.logInWithCertificate("Участник для отладки 2", false);
        supplierMainPage.isLoggedIn();
        supplierApplicationAddingPage.mainMenuTradeSearchOpen();
        //Отправка запроса на разъяснение документации
        supplierTradeSearchPage.searchAndOpenByTradeName(tradeNumber);
        supplierTenderCard.addDocumentsExplainingRequest();
        supplierDocumentsExplainingRequest.createRequest();
        supplierTenderCard.logOut();
        mainPage.isPageLoaded();
    }

    private void supplierGetsInfo2() throws Exception{
        mainPage.openLoginPage();
        commonLogInPage.supplierLogInPageOpen();
        supplierLogInPage.logInWithCertificate("Участник для отладки 2", false);
        supplierMainPage.isLoggedIn();
        System.out.println("Поставщик открывает реестр торгов и переторжек");

        System.out.println(timer.getCurrentTimeMillis());
        supplierMainPage.mainMenuMyTradesOpen();
        supplierMyTradesPage.isPageLoaded();
        System.out.println(timer.getCurrentTimeMillis());

        System.out.println("Поставщик открывает реестр контрактов");

        System.out.println(timer.getCurrentTimeMillis());
        supplierMyTradesPage.mainMenuItemContractsOpen();
        supplierMyContractsPage.isPageLoaded();
        System.out.println(timer.getCurrentTimeMillis());

        System.out.println("Поставщик открывает первый подходящий контракт из реестра");
        supplierMyContractsPage.selectContractStatus("Контракт подписан участником");
        supplierMyContractsPage.searchContractWithFilters();

        supplierMyContractsPage.firstFitContractOpen();
        WebDriverContainer.getInstance().switchToNewWindow();
        supplierContractPage.isPageLoaded();
        System.out.println(timer.getCurrentTimeMillis());

        supplierContractPage.logOut();
        mainPage.isPageLoaded();
    }

    private void adminAccelerateProcedure(String auctionNumber, String accelerationType) throws Exception{
        //Ускорение до этапа рассмотрения заявок
        mainPage.openLoginPage();
        commonLogInPage.supplierLogInPageOpen();
        supplierLogInPage.logInWithCertificate("Админ для отладки 0", false);
        adminMainPage.isPageLoaded();
        adminMainPage.mainMenuProcedureAccelerationOpen();
        adminProcedureAccelerationPage.procedureAccelerate(auctionNumber, accelerationType); //
        if(accelerationType.equals("auctionEnds")){
            adminAuctionManagementPage.endAuction(auctionNumber);
            adminProcedureAccelerationPage.logOut();
        }
        else{
            adminMainPage.logOut();
        }
        mainPage.isPageLoaded();
    }

    private void customerCreateConsiderationProtocol(String tradeNumber, boolean timeReport, int numberOfParticipants) throws Exception{
        //Заказчик рассматривает заявки
        mainPage.openLoginPage();
        commonLogInPage.customerLogInPageOpen();
        customerLogInPage.logInWithCertificate(false);
        customerMainPage.isLoggedIn();
        customerMainPage.mainMenuItemTradesOpen();
        customerMyTradesPage.considerApplicationsTriangleOpen(tradeNumber, timeReport);
        customerConsiderationProtocolPage.isPageLoaded();
        if(timeReport) {
            System.out.println(timer.getCurrentTimeMillis());
            customerConsiderationProtocolPage.mainMenuItemTradesOpen();
            customerMyTradesPage.searchAndOpenTradeByTradeNumber(tradeNumber);
            customerTenderCard.openConsiderationProtocolForm();
            customerConsiderationProtocolPage.isPageLoaded();
            System.out.println(timer.getCurrentTimeMillis());
        }
        customerConsiderationProtocolPage.createAndPublishConsiderationProtocol(numberOfParticipants);
        customerConsiderationProtocolPage.logOut();
        mainPage.isPageLoaded();
    }

    private void suppliersSubmitBids() throws Exception{
        for(int i = 1; i <= 2; i++) {
            mainPage.openLoginPage();
            commonLogInPage.supplierLogInPageOpen();
            supplierLogInPage.logInWithCertificate("Участник для отладки " + i, false);
            supplierMainPage.isLoggedIn();
            //Поиск в разделе "Поиск закупок"
            supplierMainPage.mainMenuTradeSearchOpen();
            supplierTradeSearchPage.searchAndOpenByTradeName(tradeNumber);
            //Подача заявки на аукцион
            supplierTenderCard.isPageLoaded();
            supplierTenderCard.auctionInfoOpen();
            supplierAuctionInfoPage.auctionOpen();
            if(i == 1)
                supplierAuctionPage.createAndSubmitNewBid(true);
            else
                supplierAuctionPage.createAndSubmitNewBid(false);
            supplierAuctionPage.logOut();
            mainPage.isPageLoaded();
        }
    }

    private void customerCreatesSummarizingProtocol() throws Exception{
        mainPage.openLoginPage();
        commonLogInPage.customerLogInPageOpen();
        customerLogInPage.logInWithCertificate(false);
        customerMainPage.isLoggedIn();
        customerMainPage.mainMenuItemTradesOpen();
        customerMyTradesPage.summarizeTriangleOpen(tradeNumber);
        customerSummarizingProtocolPage.isPageLoaded();
        System.out.println(timer.getCurrentTimeMillis());
        customerConsiderationProtocolPage.mainMenuItemTradesOpen();
        customerMyTradesPage.searchAndOpenTradeByTradeNumber(tradeNumber);
        customerTenderCard.viewTenderHistory();
        customerTenderCard.openSummarizingProtocolForm();
        customerSummarizingProtocolPage.isPageLoaded();
        System.out.println(timer.getCurrentTimeMillis());
        customerSummarizingProtocolPage.createAndPublishSummarizingProtocol();
        customerTenderCard.mainMenuItemTradesOpen();
        Thread.currentThread().sleep(30000);
        customerMyTradesPage.contractSendTriangleOpen(tradeNumber);
        //customerTenderCard.openContractForm();
        customerContractPage.isPageLoaded();
        System.out.println(timer.getCurrentTimeMillis());
        customerContractPage.createAndSendContract();
        customerContractPage.logOut();
        mainPage.isPageLoaded();
    }

    private void supplierCreatesDifferenceProtocol() throws Exception{
        mainPage.openLoginPage();
        commonLogInPage.supplierLogInPageOpen();
        supplierLogInPage.logInWithCertificate("Участник для отладки 2", false);
        supplierMainPage.isLoggedIn();
        supplierMainPage.mainMenuItemContractsOpen();
        System.out.println("Поставщик открывает контракт через треугольник 'Подпишите контракт'");

        supplierMyContractsPage.searchAndOpenContractByTradeNumberByTriangle(tradeNumber, true);
        supplierContractPage.isPageLoaded();
        System.out.println(timer.getCurrentTimeMillis());
        System.out.println("Поставщик создает протокол разногласий");
        supplierContractPage.createDifferenceProtocol();
        supplierContractPage.logOut();
        mainPage.isPageLoaded();
    }

    private void customerChangesContract() throws Exception{
        mainPage.openLoginPage();
        commonLogInPage.customerLogInPageOpen();
        customerLogInPage.logInWithCertificate(false);
        customerMainPage.isLoggedIn();
        customerMainPage.mainMenuItemTradesOpen();
        customerMyTradesPage.contractResendTriangleOpen(tradeNumber);
        customerContractPage.isPageLoaded();
        System.out.println(timer.getCurrentTimeMillis());
        String currentURL = WebDriverContainer.getInstance().getWebDriver().getCurrentUrl();
        this.contractId = currentURL.substring(currentURL.lastIndexOf('/' ) + 1);
        customerContractPage.makeChange();
        customerContractPage.downloadDocs();
        customerContractPage.logOut();
        mainPage.isPageLoaded();
    }

    private void supplierSignsContract() throws Exception{
        mainPage.openLoginPage();
        commonLogInPage.supplierLogInPageOpen();
        supplierLogInPage.logInWithCertificate("Участник для отладки 2", false);
        supplierMainPage.isLoggedIn();
        supplierMainPage.mainMenuItemContractsOpen();
        supplierMyContractsPage.searchAndOpenContractByTradeNumberByTriangle(tradeNumber, false);
        System.out.println("Поставщик подписывает контракт");
        supplierContractPage.acceptAndSignContract();
        supplierContractPage.downloadDocs();
        supplierContractPage.logOut();
        mainPage.isPageLoaded();
    }

    private void adminAccelerateContractConclusion() throws Exception{
        mainPage.openLoginPage();
        commonLogInPage.supplierLogInPageOpen();
        supplierLogInPage.logInWithCertificate("Админ для отладки 0", false);
        adminMainPage.isPageLoaded();
        adminMainPage.mainMenuContractConclusionAdministrationOpen();
        adminContractConclusionAdministrationPage.accelerateContractConclusion(contractId);
        adminMainPage.logOut();
        mainPage.isPageLoaded();
    }

    private void customerSignsContract() throws Exception{
        mainPage.openLoginPage();
        commonLogInPage.customerLogInPageOpen();
        customerLogInPage.logInWithCertificate(false);
        customerMainPage.isLoggedIn();
        customerMainPage.mainMenuItemTradesOpen();
        customerMyTradesPage.contractSignTriangleOpen(tradeNumber);
        customerContractPage.isPageLoaded();
        System.out.println(timer.getCurrentTimeMillis());
        customerContractPage.signContract();
        customerContractPage.logOut();
        mainPage.isPageLoaded();
    }
}