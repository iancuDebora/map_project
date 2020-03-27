package validators;

import domains.Nota;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class ValidareNotaTest {
    private Nota nota1 = new Nota(1L,1L,10D, LocalDate.of(2020, 3,10),"numeProf","feedback");
    private Nota nota2 = new Nota(2L,2L,0D, LocalDate.of(2020, 3,10),"numeProf","feedback");
    private ValidareNota validator = new ValidareNota();

    @Before
    public void setUp() throws Exception {
        nota1.setId(new Pair<>(1L,1L));
        nota2.setId(new Pair<>(2L,2L));
    }

    @Test
    public void validate() {
        try {
            validator.validate(nota1);
        } catch (ValidationException e) {
            assert (false);
        }
        try {
            validator.validate(nota2);
        } catch (ValidationException e) {
            assert (true);
        }
    }
}