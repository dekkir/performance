package helpers;

public class AuctionDates {

    private static int currentDay;
    private static int currentMonth;
    private static int currentYear;
//    private static String submittingApplicationsEndDate;
//    private static String reviewApplicationsEndDate;
    private static final int[] monthDuration = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    private Timer timer = new Timer();

    public AuctionDates(){
        this.currentDay = new Integer(timer.getOnlyDay());
        this.currentMonth = new Integer(timer.getOnlyMonth());
        this.currentYear = new Integer(timer.getOnlyYear());
    }

    private void monthIncrease(){
        int month = currentMonth;
        if(++month > 12){
            month-=12;
            this.yearIncrease();
        }
        this.currentMonth = month;
    }

    private void yearIncrease(){
        this.currentYear++;
    }

    private void dayIncrease(){
        int day = currentDay;
        day += 7;
        if(day > monthDuration[currentMonth - 1]){
            day -= monthDuration[currentMonth - 1];
            monthIncrease();
        }
        this.currentDay = day;
    }

    private String getInTwoDigitsFormatDate(int dayOrMonth){
        String currentDate;
        if(dayOrMonth < 10){
            currentDate = "0" + Integer.toString(dayOrMonth);
        }
        else currentDate = Integer.toString(dayOrMonth);
        return currentDate;
    }

    public String getNextDate(){
        dayIncrease();
        return getInTwoDigitsFormatDate(currentDay) + "."
                + getInTwoDigitsFormatDate(currentMonth) + "."
                + currentYear;
    }
/*
    public String getReviewApplicationsEndDate(){
        return reviewApplicationsEndDate;
    }
*/
}
