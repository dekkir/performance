package steps;

import com.codeborne.selenide.Configuration;
import helpers.ExcelStyles;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.junit.After;
import org.junit.Before;
import javax.naming.directory.InvalidSearchControlsException;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static com.codeborne.selenide.Selenide.close;

public class CucumberTestBeforeAndAfter extends CommonStepDefinition{

    private String fileName = "supplierAndCustomerPerformanceTest.txt";
    private String logsPath = "C://Users//User3//IdeaProjects//Pr44//build//reports//logs//";
    private ArrayList<Double> supplierMetricList = new ArrayList<Double>();
    private ArrayList<Double> customerMetricList = new ArrayList<Double>();
    private ExcelStyles excelStyles;
    private Workbook book;
    private Sheet sheet;
    private String xlsxFile = logsPath + "report.xlsx";
    private int workCellNumber;
    private static final int dateRowNumber = 3;
    private PrintStream printStream;

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

    @cucumber.api.java.Before
    public void setUp() throws IOException{
        Configuration.timeout = 90000;
        fileName = "supplierAndCustomerPerformanceTest.txt";
        File file = new File(logsPath + fileName);
        printStream = new PrintStream(file);
        System.setOut(printStream);
        System.out.println("Test starts " + timer.getDateForReport());
    }

    @cucumber.api.java.After
    public void tearDown() throws IOException, InvalidFormatException{
        close();
        System.out.println("Test ends " + timer.getDateForReport());
        printStream.close();

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
}
