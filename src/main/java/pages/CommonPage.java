package pages;
import helpers.Timer;
import helpers.WebDriverContainer;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FilenameFilter;

import static com.codeborne.selenide.Selenide.$;

public abstract class CommonPage{

    protected final static String commonXPathEnding = "')]";
    protected static WebDriver driver;
    protected Timer timer = new Timer();
    protected static final String commonDocument = "C:/Users/User3/IdeaProjects/_44rts/target/classes/helpers/doc.docx";
    protected static final String downloadsCatalogPath = "C:/Users/User3/Downloads";

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

}
