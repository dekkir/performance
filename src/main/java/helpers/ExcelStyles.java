package helpers;
import org.apache.poi.ss.usermodel.*;


public class ExcelStyles {

    private CellStyle commonStyle;
    //private Workbook book;
    private CellStyle badResultStyle;
    private CellStyle goodResultStyle;

    public ExcelStyles(Workbook book){
        CellStyle style = book.createCellStyle();
        Font font = book.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short)12);
        style.setFont(font);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        this.commonStyle = style;
    }

    public CellStyle getCommonStyle(){
        return commonStyle;
    }

    public CellStyle styleForBadResult(Workbook book){
        CellStyle style = commonStyle;
        Font font = book.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short)12);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        return style;
    }

    public CellStyle styleForGoodResult(Workbook book){
        CellStyle style = book.createCellStyle();
        Font font = book.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short)12);
        style.setFont(font);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        return style;
    }
}
