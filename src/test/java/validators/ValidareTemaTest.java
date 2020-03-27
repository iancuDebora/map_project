package validators;

import domains.Tema;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidareTemaTest {

    private Tema tema1 = new Tema(1L,"...",1,2);
    private Tema tema2 = new Tema(2L,"...",13,15);
    private ValidareTema validator = new ValidareTema();

    @Before
    public void setUp() throws Exception {
        tema1.setId(1L);
        tema2.setId(2L);
    }

    @Test
    public void validate() {
        try {
            validator.validate(tema1);
        } catch (ValidationException e) {
            assert (false);
        }
        try {
            validator.validate(tema2);
        } catch (ValidationException e) {
            assert (true);
        }

    }
}