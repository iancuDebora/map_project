package domains;


import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Clasa care se comporta ca si un singleton.
 * Pentru a obtine o instanta a acestei clase se apeleaza metoda getInstance().
 */
public class StructuraSemestru {
    private static StructuraSemestru instance=null;

    /**
     * Functie care obtine o instanta a clasei StructuraSemestru
     * @return - o instanta a clasei StructuraSemestru
     */
    public static StructuraSemestru getInstance(){
        if (instance == null)
        {
            return instance = new StructuraSemestru();
        }
        else
            return instance;
    }

    /**
     * Constructor fara parametrii pentruStructuraSemestru
     */
    public StructuraSemestru() { }

    /**
     * Functie care calculeaza numarul saptamanii din anul universitar de a o anumita data
     * @param data - data penntru care se calculeaza numarul saptamanii
     * @param startSemester - data in care a inceput semestrul
     * @return -  numarul saptamanii din anul universitar de a o anumita data
     */
    public int getWeekNumber(LocalDate data, Date startSemester,Date beginHoliday,Date endHoliday,Date endSemester)
    {
        Date date = java.sql.Date.valueOf(data);
        Calendar calendar=new GregorianCalendar();
        calendar.setTime(date);
        int currentWeekofYear=calendar.get(Calendar.WEEK_OF_YEAR);

        Calendar calendar2=new GregorianCalendar();
        calendar2.setTime(startSemester);
        int yearstartweek=calendar2.get(Calendar.WEEK_OF_YEAR);

        Calendar calendar3=new GregorianCalendar();
        calendar3.setTime(beginHoliday);
        int vacstartweek=calendar3.get(Calendar.WEEK_OF_YEAR);

        calendar3.setTime(endHoliday);
        int vacendweek=calendar3.get(Calendar.WEEK_OF_YEAR);

        long diff = endHoliday.getTime()-beginHoliday.getTime();
        int days = Math.toIntExact(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        int weeks=days/7+1;
        int result=0;
        if (date.after(startSemester) && date.before(beginHoliday)) {
            result = currentWeekofYear- yearstartweek + 1;
        }
        else if (date.after(endHoliday) && date.before(endSemester))
            result=currentWeekofYear-yearstartweek-weeks + 1;
        else if(date.after(beginHoliday) && date.before(endHoliday))
            result = -yearstartweek+ vacstartweek;
        return result;
    }

    /**
     * Functie care calculeaza saptamana curenta a anului universitar
     * @param startSemester - data in care incepe semestrul
     * @param beginHoliday - data in care incepe vacanta
     * @param endHoliday - data in care se termina vacanta
     * @param endSemester - data in care se termina semestrul
     * @return - numarul saptamanii curente a anului universitar
     */
    public int getCurrentWeek(Date startSemester,Date beginHoliday,Date endHoliday,Date endSemester)
    {
        Calendar calendar=new GregorianCalendar();
        Date data=new Date();
        calendar.setTime(data);
        int currentWeekofYear=calendar.get(Calendar.WEEK_OF_YEAR);

        Calendar calendar2=new GregorianCalendar();
        calendar2.setTime(startSemester);
        int yearstartweek=calendar2.get(Calendar.WEEK_OF_YEAR);

        Calendar calendar3=new GregorianCalendar();
        calendar3.setTime(beginHoliday);
        int vacstartweek=calendar3.get(Calendar.WEEK_OF_YEAR);

        calendar3.setTime(endHoliday);
        int vacendweek=calendar3.get(Calendar.WEEK_OF_YEAR);

        long diff = endHoliday.getTime()-beginHoliday.getTime();
        int days = Math.toIntExact(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        int weeks=days/7+1;
        int result=0;
        if (data.after(startSemester) && data.before(beginHoliday)) {
            result = currentWeekofYear- yearstartweek + 1;
        }
        else if (data.after(endHoliday) && data.before(endSemester)) {
            result = currentWeekofYear - yearstartweek - weeks + 1;

        }
        else if(data.after(beginHoliday) && data.before(endHoliday))
            result = -yearstartweek+ vacstartweek;
        return result;
    }

}