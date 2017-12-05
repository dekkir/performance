package helpers;
import java.text.SimpleDateFormat;

public class Timer {
    private long startTime = 0;
    private long endTime = 0;

    public void start(){
        this.startTime = System.currentTimeMillis();
    }

    public void end(){
        this.endTime = System.currentTimeMillis();
    }

    public double getInterval(){
        return (double)(this.endTime - this.startTime)/1000.00;
    }

    public double getDifference(long endTime, long startTime){return (double)(endTime - startTime)/1000.00;}

    public String getDate(){
        long currentTime = System.currentTimeMillis();
        String currentDateString = new SimpleDateFormat("dd.MM.yyyy HH.mm.ss").format(currentTime);
        return currentDateString;
    }

    public String getOnlyDate(){
        long currentTime = System.currentTimeMillis();
        String currentDateString = new SimpleDateFormat("dd.MM.yy").format(currentTime);
        return currentDateString;
    }

    public String getOnlyDay(){
        long currentTime = System.currentTimeMillis();
        String currentDateString = new SimpleDateFormat("dd").format(currentTime);
        return currentDateString;
    }

    public String getOnlyMonth(){
        long currentTime = System.currentTimeMillis();
        String currentDateString = new SimpleDateFormat("MM").format(currentTime);
        return currentDateString;
    }

    public String getOnlyYear(){
        long currentTime = System.currentTimeMillis();
        String currentDateString = new SimpleDateFormat("yyyy").format(currentTime);
        return currentDateString;
    }

    public String getDateForReport(){
        long currentTime = System.currentTimeMillis();
        String currentDateString = new SimpleDateFormat("dd.MM.yy HH:mm").format(currentTime);
        return currentDateString;
    }

    public long getCurrentTimeMillis(){
        return System.currentTimeMillis();
    }

}
