package domains;

import org.junit.Before;
import org.junit.Test;

public class TemaTest {

   // private StructuraAnUniversitar anuniv=StructuraAnUniversitar.getInstance();

    private Tema t = new Tema((long)1,"lab3",2,3);


    @Before
    public void setUp() throws Exception {
        t.setId((long)1);
    }
    @Test
    public void getId() {

        assert(t.getId()==1);
    }

    @Test
    public void setId() {
        t.setId((long)2);
        assert(t.getId()==2);
    }


    @Test
    public void getDescriere() {

        assert(t.getDescriere().equals("lab3"));
    }

    @Test
    public void setDescriere() {
        t.setDescriere("exercitiu cu...");
        assert(t.getDescriere().equals("exercitiu cu..."));
    }

    @Test
    public void getStartWeek()
    {
        assert(t.getStartWeek() == 2);
    }

    @Test
    public void setStartWeek() {
        t.setStartWeek(4);
        assert(t.getStartWeek() == 4);
    }

    @Test
    public void getDeadlineWeek()  {
        assert(t.getDeadlineWeek() == 3);
    }

    @Test
    public void setDeadlineWeek() {
        t.setDeadlineWeek(7);
        assert(t.getDeadlineWeek() == 7);
    }

    @Test
    public void testEquals()  {
        Tema t2=new Tema((long)1,"lab3",2,3);
        t2.setId((long)1);
        assert(t.equals(t2));
    }


}
