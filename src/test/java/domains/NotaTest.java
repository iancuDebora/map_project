package domains;

import javafx.util.Pair;
import jdk.vm.ci.meta.Local;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class NotaTest {

    private Nota n = new Nota(1L,1L,10D, LocalDate.now(),"numeProfesor","excelent");
    @Before
    public void setUp() throws Exception {
        n.setId(new Pair<>((long)1,(long)1));
    }

    @Test
    public void getStID() {
        assert(n.getStID()==1L);
    }

    @Test
    public void getTemaID() {
        assert(n.getTemaID()==1L);
    }

    @Test
    public void setStID()
    {
        n.setStID(2L);
        assert (n.getStID() == 2L);
    }

    @Test
    public void setTemaID()
    {
        n.setTemaID(2L);
        assert (n.getTemaID() == 2L);
    }

    @Test
    public void getFeedback() {
        assert(n.getFeedback().equals("excelent"));
    }

    @Test
    public void setFeedback() {
        n.setFeedback("foarte bine");
        assert (n.getFeedback().equals("foarte bine"));
    }

    @Test
    public void getNota() {
        assert(n.getNota()==10D);
    }

    @Test
    public void setNota() {
        n.setNota(9D);
        assert(n.getNota()==9D);
    }

    @Test
    public void getData() {
        assert (n.getData().equals(LocalDate.now()));
    }

    @Test
    public void setData() {
        n.setData(LocalDate.of(2019,10,23));
        assert (n.getData().equals(LocalDate.of(2019,10,23)));
    }

    @Test
    public void getProfesor() {
        assert (n.getProfesor().equals("numeProfesor"));
    }

    @Test
    public void setProfesor() {
        n.setProfesor("numeProfesor2");
        assert (n.getProfesor().equals("numeProfesor2"));
    }
}