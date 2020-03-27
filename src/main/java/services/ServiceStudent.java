package services;

import domains.Student;
import repository.XMLFileRepository;
import utils.observer.Observable;
import utils.observer.Observer;
import validators.ValidareStudent;
import validators.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class ServiceStudent implements Observable{
    private XMLFileRepository<Long, Student> repo;
    private ValidareStudent validator;
    private List<Observer> observers = new ArrayList<>();

    /**
     * Constructor pentru ServiceStudent
     * @param repo - repository Student
     * @param validator - validator Student
     */
    public ServiceStudent(XMLFileRepository<Long, Student> repo, ValidareStudent validator) {
        this.repo = repo;
        this.validator = validator;
    }

    /**
     * Functie care adauga un student
     * @param st - studentul de adaugat
     * @return - null - daca studentul s-a salvat, studentul dat - altfel
     * @throws ValidationException - daca entitatea nu este valida
     */
    public Student addSt(Student st) throws ValidationException {
        validator.validate(st);
        Student s=repo.save(st);
        if (s==null)
            notifyObservers();
        return s;
    }

    /**
     * Functie care sterge un student
     * @param id - id-ul studentului de sters
     * @return - studentul daca acesta s-a sters, alfel null
     */
    public Student deleteStudent(Long id){
        Student s=repo.delete(id);
        if (s!=null) {
            s.setId(id);
            notifyObservers();
        }
        return s;
    }

    /**
     * Functie care modifica un student
     * @param st - studentul care trebuie modificat
     * @return - null, daca studentul a fost modificat, altfel returneaza studentul
     * @throws ValidationException - daca studentul nu este valid
     */
    public Student updateSt(Student st) throws ValidationException {
        Student s1=repo.findOne(st.getId());
        validator.validate(st);
        if (s1!=null)
        {
            Student s2=repo.update(st);
            notifyObservers();
            return s2;
        };
        return s1;
    }

    /**
     * Functie care cauta un student dupa id
     * @param id - id-ul studentului cautat
     * @return - studentul, daca acesta a fost gasit, altfel returneaza null
     */
    public Student findStudent(Long id) {

        return this.repo.findOne(id);
    }

    /**
     * Functie care returneaza un Iterable care contine toti studentii din repository
     * @return - Iterable care contine toti studentii din repository
     */
    public Iterable<Student> getAllSt(){

        return this.repo.findAll();
    }

    @Override
    public void addObserver(Observer e) {
        observers.add(e);

    }

    @Override
    public void removeObserver() {

    }

    @Override
    public void notifyObservers() {
        observers.stream().forEach(x->x.update());

    }

    /**
     * Functie care returneaza un List cu toti studentii din repository
     * @return - List cu toti studentii din repository
     */
    public List<Student> getStList()
    {
        List<Student> rez=new ArrayList<Student>();
        getAllSt().forEach(rez::add);
        return rez;
    }
}
