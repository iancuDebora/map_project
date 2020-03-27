package domains;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


public class StudentTest {

    private Student student=new Student((long)1,"Popescu","Ioana",221,"piir1@scs.ubbcluj.ro","Ionescu");



    @Before
    public void setUp() throws Exception {
        student.setId((long)1);
    }

    @Test
    public void getNume()
    {
        assert(student.getNume().equals("Popescu"));
    }
    @Test
    public void setNume() {
        student.setNume("Ionescu");
        assert (student.getNume().equals("Ionescu"));
    }
    @Test
    public void getPrenume() {
        assert (student.getPrenume().equals("Ioana"));
    }
    @Test
    public void setPrenume() {
        student.setPrenume("Ionela");
        assert (student.getPrenume().equals("Ionela"));
    }
    @Test
    public void getGrupa() {

        assert (student.getGrupa()==221);
    }
    @Test
    public void setGrupa() {
        student.setGrupa(223);
        assert (student.getGrupa()==223);
    }
    @Test
    public void getEmail() {

        assert (student.getEmail().equals("piir1@scs.ubbcluj.ro"));
    }
    @Test
    public void setEmail() {
        student.setEmail("pii2@scs.ubbcluj.ro");
        assert (student.getEmail().equals("pii2@scs.ubbcluj.ro"));
    }
    @Test
    public void getCadruDidacticIndrumatorLab() {

        assert (student.getCadruDidacticIndrumatorLab().equals("Ionescu"));
    }
    @Test
    public void setCadruDidacticIndrumatorLab() {
        student.setCadruDidacticIndrumatorLab("Alexandru");
        assert (student.getCadruDidacticIndrumatorLab().equals("Alexandru"));
    }

    @Test
    public void getId() {

        assert (student.getId()==1);
    }

    @Test
    public void setId() {
        student.setId((long)111);
        assert (student.getId()==111);
    }
}


