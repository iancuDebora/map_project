package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {

    private static final SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
    public static Date SEMESTER_START1;
    public static Date SEMESTER_START2;
    public static Date SEMESTER_END1;
    public static Date SEMESTER_END2;
    public static Date HOLIDAY_START1;
    public static Date HOLIDAY_START2;
    public static Date HOLIDAY_END1;
    public static Date HOLIDAY_END2;


    static {
        try {
            SEMESTER_START1 = myFormat.parse("30 09 2019");
            HOLIDAY_START1 = myFormat.parse("23 12 2019");
            HOLIDAY_END1 = myFormat.parse("05 01 2020");
            SEMESTER_END1 = myFormat.parse("17 01 2020");
            SEMESTER_START2 = myFormat.parse("24 02 2020");
            HOLIDAY_START2 = myFormat.parse("20 04 2020");
            HOLIDAY_END2 = myFormat.parse("27 04 2020");
            SEMESTER_END2 = myFormat.parse("05 06 2020");

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


}