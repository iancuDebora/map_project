package repository;

import config_services.ApplicationContext;
import domains.Student;
import org.junit.Before;
import org.junit.Test;
import validators.ValidareStudent;
import validators.ValidationException;

import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.*;

public class StudentXMLFileRepositoryTest {
    private ValidareStudent validator = new ValidareStudent();
    private StudentXMLFileRepository repo = new StudentXMLFileRepository(validator, ApplicationContext.getPROPERTIES().getProperty("test.studenti"));
    private Student student1 = new Student(1L,"nume1","prenume1",223,"email1@cs.ubbclujro","profesor1");
    private Student student2 = new Student(2L,"nume2","prenume2",223,"elail2@scs.ubbcluj.ro","profesor2");
    private Student student3 = new Student(3L,"nume3","prenume3",223,"email3@scs.ubbcluj.ro","profesor");


    @Before
    public void save() {
        try {
            student1.setId(1L);
            repo.save(student1);
            student2.setId(2L);
            repo.save(student2);
            student3.setId(3L);
            repo.save(student3);
            assert (true);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findOne() {
        assert (repo.findOne(2L).equals(student2));
        assert (repo.findOne(4L)==null);
    }

    @Test
    public void findAll() {
        Iterator<Student> it = repo.findAll().iterator();
        int nr=0;
        while (it.hasNext())
        {
            nr++;
            it.next();
        }
        assert(nr==3);
    }

    @Test
    public void delete() {
        assert (((Collection<?>)repo.findAll()).size() == 3);
        assert (repo.delete(1L).equals(student1));
        assert (((Collection<?>)repo.findAll()).size() == 2);
        try {
            repo.delete(null);
            assert (false);
        } catch (IllegalArgumentException ex)
        {
            assert (true);
        }
    }

    @Test
    public void update() {
        Student student4 = new Student(3L,"nume","prenume",223,"email2@scs.ubbcluj.ro","profesor");
        student4.setId(3L);
        assert(repo.update(student4) == null);

        assert (repo.findOne(3L).getNume().equals("nume"));
        try
        {
            repo.update(null);
            assert (false);
        }catch(IllegalArgumentException ex)
        {
            assert (true);
        }

        Student student5 = new Student(3L,"","prenume",221,"","");
        student5.setId(3L);
        try
        { repo.update(student5);
            assert (false);
        }catch (ValidationException ex)
        {
            assert (true);
        }
    }
}