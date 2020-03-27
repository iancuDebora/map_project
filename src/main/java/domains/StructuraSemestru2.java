package domains;

import utils.Constants;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class StructuraSemestru2 {
    private Date startSemester;
    private Date beginHoliday;
    private Date endHoliday;
    private Date endSemester;


    private StructuraSemestru sem = StructuraSemestru.getInstance();
    private static StructuraSemestru2 instance = null;

    /**
     * Clasa care se comporta ca si un singleton.
     * Pentru a obtine o instanta a acestei clase se apeleaza metoda getInstance().
     * @return - o instanta a clasei StructuraSemestru2
     */
    public static StructuraSemestru2 getInstance(){
        if (instance==null)
        {
            return instance =new StructuraSemestru2();
        }
        else
            return instance;
    }

    /**
     * Constructor pentru clasa StructuraSemestru2
     */
    public StructuraSemestru2() {
        startSemester= Constants.SEMESTER_START2;
        beginHoliday=Constants.HOLIDAY_START2;
        endHoliday=Constants.HOLIDAY_END2;
        endSemester=Constants.SEMESTER_END2;

    }

    /**
     * Functie care verifica daca o data este in interiorul unui semestru
     * @param Date - data pentru care se face verificarea
     * @return - true - daca data este in interiorul aferent unui semestru, false - altfel
     */
    public boolean validateDate(Date Date) {
        if (Date.after(startSemester) && Date.before(endSemester))
            if (Date.after(endHoliday) || Date.before(beginHoliday))
                return true;
        return false;

    }

    /**
     * Functie care calculeaza numarul saptamanii din anul universitar de a o anumita data
     * @param data - data pentru care se calculeaza numarul saptamanii
     * @return -  numarul saptamanii din anul universitar de a o anumita data
     */
    public int getWeekNumber(LocalDate data)
    {

        return sem.getWeekNumber(data,startSemester,beginHoliday,endHoliday,endSemester);
    }

    /**
     * Functie care calculeaza saptamana curenta a anului universitar
     * @return - numarul saptamanii curente a anului universitar
     */
    public int getCurrentWeek()
    {

        return sem.getCurrentWeek(startSemester,beginHoliday,endHoliday,endSemester);
    }
}
