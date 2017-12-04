package pages;
import com.codeborne.selenide.SelenideElement;
import helpers.Timer;
import helpers.WebDriverContainer;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FilenameFilter;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$;

public abstract class CommonPage{

    protected final static String commonXPathEnding = "')]";
    protected static WebDriver driver;
    protected Timer timer = new Timer();
    protected static final String commonDocument = "C:/Users/User3/IdeaProjects/Pr44/src/main/java/helpers/doc.docx";
    protected static final String downloadsCatalogPath = "C:/Users/User3/Downloads";
    protected static final String supplierLogOutButtonId = "BaseMainContent_ucHeaderControl_lbLogoutText";
    protected static final String customerLogOutButtonId = "header__authorization_loguot";
    private static final String LOADING_XPATH = "//div[@class='k-loading-image' or @class='k-loading-mask' or @class='spinner']";
    private static final String LOADING_1_XPATH = "//div[@class='k-loading-mask']/div[@class='k-loading-image']";
    private static final String loadingString2Xpath = "//div[@class='loading']";
    protected int delayTimeMs = 100000;
    protected int delayTime300 = 300000; //30000
    protected long pollingIntervalMs = 50;
    protected int timeForIsDisplay1 = 5;
    protected int timeForIsDisplay2 = 15; //30

    protected void exitSystem(String userType) throws Exception{
        SelenideElement logOutButton = null;
        if(userType.equals("customer")) $(By.id(customerLogOutButtonId));
        else logOutButton = $(By.id(supplierLogOutButtonId));
        logOutButton.click();
        waitForLoadingImage();
    }

    public CommonPage(){
        this.driver = WebDriverContainer.getInstance().getWebDriver();
    }

    protected void setFocusInKendoNumericTextBoxJS(String idLocator) throws Exception {
        String script = "$(\"#" + idLocator + "\").data(\"kendoNumericTextBox\").focus();";
        ((JavascriptExecutor) driver).executeScript(script);
    }

    protected static void deleteAllFilesInFolder(String folderPath){
        for(File currentFile : new File(folderPath).listFiles())
            if(currentFile.isFile()) currentFile.delete();
    }

    protected Boolean isDirectoryEmpty(File file) throws NullPointerException{
        return !(file.isDirectory() && (file.list().length > 0));
    }

    protected void downloadFile(String downloadDocsButton) throws Exception{
        deleteAllFilesInFolder(downloadsCatalogPath);
        $(downloadDocsButton).click();
        System.out.println(timer.getCurrentTimeMillis());
        while (isDirectoryEmpty(new File(downloadsCatalogPath)))
            Thread.currentThread().sleep(10);
        while (true) {
            try {
                File folder = new File(downloadsCatalogPath);
                String[] files = folder.list(new FilenameFilter() {
                    public boolean accept(File folder, String name) {
                        return name.endsWith(".zip");
                    }
                });
                String fileName = files[0];
                break;
            } catch (ArrayIndexOutOfBoundsException e) {
                Thread.currentThread().sleep(10);
            }
        }
        File folder = new File(downloadsCatalogPath);
        String[] files = folder.list(new FilenameFilter() {
            public boolean accept(File folder, String name) {
                return name.endsWith(".zip");
            }
        });
        String fileName = files[0];
        File downloadedFile = new File(fileName);
        long currentLength = downloadedFile.length();
        Thread.currentThread().sleep(100);
        while(currentLength < new File(fileName).length()){
            currentLength = new File(fileName).length();
            Thread.currentThread().sleep(50);
        }
        System.out.println(timer.getCurrentTimeMillis());
    }

    protected void waitForLoadingImage() throws Exception {
        String functionName = " [-]: ";
        SelenideElement element1 = $(By.xpath(LOADING_XPATH));
        SelenideElement element2 = $(By.xpath(LOADING_1_XPATH));
        SelenideElement elementLoading3 = $(By.xpath(loadingString2Xpath));
        SelenideElement spinnerWrapAfterLogIn = $("div#loading-wrap");
        driver.manage().timeouts().implicitlyWait(timeForIsDisplay1, TimeUnit.SECONDS);

        if((spinnerWrapAfterLogIn).exists()){
            spinnerWrapAfterLogIn.waitUntil(not(exist), delayTimeMs, pollingIntervalMs);
        }

        if (element1.isDisplayed()) {
            element1.waitUntil(disappear, delayTime300, pollingIntervalMs);
            System.out.println(functionName + "появился индикатор ожидания завершения действия. Ветка-1");
            driver.manage().timeouts().implicitlyWait(timeForIsDisplay2, TimeUnit.SECONDS);
            return;
        }
        if (element2.isDisplayed()) {
            element2.waitUntil(disappear, delayTime300, pollingIntervalMs);
            System.out.println(functionName + "появился индикатор ожидания завершения действия. . Ветка-2");
            driver.manage().timeouts().implicitlyWait(timeForIsDisplay2, TimeUnit.SECONDS);
            return;
        }
        if (elementLoading3.isDisplayed()) {
            element2.waitUntil(disappear, delayTime300, pollingIntervalMs);
            System.out.println(functionName + "появился индикатор ожидания завершения действия. . Ветка-3");
            driver.manage().timeouts().implicitlyWait(timeForIsDisplay2, TimeUnit.SECONDS);
            return;
        }
    }


}
