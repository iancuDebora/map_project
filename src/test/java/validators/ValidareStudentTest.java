package validators;

import domains.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidareStudentTest {

    private Student student1 = new Student((long)1,"Popescu","Ioana",221,"piir1@scs.ubbcluj.ro","Ionescu");
    private Student student2 = new Student((long)2,"","Ioana",-7,"piir1@scs.ubbcluj.ro","Ionescu");
    private ValidareStudent validator = new ValidareStudent();

    @Before
    public void setUp() throws Exception {
        student1.setId(1L);
        student2.setId(2L);
    }

    @Test
    public void validate() {
        try {
            validator.validate(student1);
        } catch (ValidationException e) {
            assert (false);
        }
        try
        {
            validator.validate(student2);
        } catch (ValidationException e) {
            assert (true);
        }
    }
}