package domains;



import java.time.LocalDate;
import java.util.Date;
/**
 * Clasa care se comporta ca si un singleton.
 * Pentru a obtine o instanta a acestei clase se apeleaza metoda getInstance().
 */
public class StructuraAnUniversitar extends Entity<Long> {

    private StructuraSemestru1 sem1 = StructuraSemestru1.getInstance();
    private StructuraSemestru2 sem2 = StructuraSemestru2.getInstance();

    private static StructuraAnUniversitar instance=null;

    /**
     * Functie care obtine o instanta a clasei StructuraAnUniversitar.
     * @return - instanta a clasei StructuraAnUniversitar
     */
    public static StructuraAnUniversitar getInstance(){
        if (instance == null)
        {
            return instance =new StructuraAnUniversitar();
        }
        else
            return instance;
    }
    public StructuraAnUniversitar() { }


    /**
     * Functie care calculeaza numarul saptamanii din anul universitar pentru o anumita data calendaristica
     * @param data - data pentru care se calculeaza numarul saptamanii
     * @return -  numarul saptamanii din anul scolar pentru o  anumita data calendaristica
     */
    public int getWeekNumber(LocalDate data)  {
        Date nou=convertToDateViaSqlDate(data);
        if (sem1.validateDate(nou)) return sem1.getWeekNumber(data);
        if (sem2.validateDate(nou)) return sem2.getWeekNumber(data);
        return 0;
    }


    /**
     * Functie care calculeaza saptamana curenta a anului universitar
     * @return - numarul saptamanii curente a anului universitar
     */
    public int getCurrentWeek()  {
        Date data = new Date();
        if (sem1.validateDate(data))
        { return sem1.getCurrentWeek();
        }
        if (sem2.validateDate(data)) {
            return sem2.getCurrentWeek();
        }
        return 0;
    }

    /**
     * Functie care transforma un obiect LocalDate intr-unul Date
     * @param data - obiect LocalDate
     * @return - valoarea Date pentru obiectul dat
     */
    private Date convertToDateViaSqlDate(LocalDate data) {
        return java.sql.Date.valueOf(data);
    }

}
