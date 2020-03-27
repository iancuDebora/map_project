package repository;



import domains.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import validators.ValidareStudent;
import validators.ValidationException;

import java.util.Iterator;

public class InMemoryRepositoryStudentTest {
    private InMemoryRepository<Long,Student> repo=new InMemoryRepository<>(new ValidareStudent());
    private Student s1=new Student((long)1,"Popescu","Ioana",221,"pi@scs.ubbcluj.ro","Ionescu");

    private Student s2=new Student((long)2,"Popescu","Stefan",222,"ps@scs.ubbcluj.ro","Alexandrescu");
    private Student s3=new Student((long)3,"gg","Aida",223,"pa@scs.ubbcluj.ro","Ionescu");

    public InMemoryRepositoryStudentTest() throws ValidationException {
        setUp();
    }

    @Before
    public void setUp() {

        s1.setId((long)1);
        s2.setId((long)2);
        s3.setId((long)3);
        try {
            repo.save(s1);
            repo.save(s2);

            repo.save(s3);
        } catch (ValidationException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void save() throws ValidationException {
        try {
            repo.save(null);
            assert(false);
        } catch (IllegalArgumentException | ValidationException e) {
            assert(true);
        }


        Student s=new Student((long)8,"j","o",333,"o","u");
        s.setId((long)8);
        assert(repo.save(s)==null);// entitatea este salvata
        assert(repo.save(s1)==s1);// entitatea era deja in repo
        Student ss=new Student((long)10,"h","k",223,"i","");
        ss.setId((long)10);
        try
        {
            repo.save(ss);
            assert(false);
        }
        catch (ValidationException e)
        {  //  e.printStackTrace();
            assert(true);
        }
    }

    @Test
    public void findOne() {
        try{
            repo.findOne(null);
            assert false;
        }catch (IllegalArgumentException ex){
            assert true;
        }
        assert(repo.findOne((long)1).equals(s1));
        assert(repo.findOne((long)5) == null);

    }

    @Test
    public void findAll() throws ValidationException {

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
        try
        {
            repo.delete(null);
            assert(false);
        } catch (IllegalArgumentException e) {
            assert(true);
        }
        assert(repo.delete((long)1).getId()==1);
        Iterator<Student> it = repo.findAll().iterator();
        int nr=0;
        while (it.hasNext())
        {
            nr++;
            it.next();
        }
        assert(nr==2);

    }

    @Test
    public void update()  {
        try
        {
            repo.update(null);
            assert(false);
        } catch (IllegalArgumentException e) {
            assert(true);
        }


        repo.update(new Student((long)2,"","",23,"",""));
        assert (repo.findOne((long) 2).getNume().equals("Popescu"));


        Student ss=new Student((long)2,"e","f",3,"r","r");
        ss.setId((long)2);
        assert(repo.update(ss)==null);
        assert(repo.findOne(2L).getNume().equals("e"));
        Student s500=new Student((long)100,"e","f",3,"r","r");
        s500.setId((long)100);
        assert(repo.update(s500)==s500);

    }

}
