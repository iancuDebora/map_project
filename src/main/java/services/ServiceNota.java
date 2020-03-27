package services;

import domains.Nota;
import domains.StructuraAnUniversitar;
import domains.Student;
import domains.Tema;
import javafx.util.Pair;
import repository.XMLFileRepository;
import utils.observer.Observable;
import utils.observer.Observer;
import validators.ValidareNota;
import validators.ValidareStudent;
import validators.ValidationException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ServiceNota implements Observable {
    private XMLFileRepository<Pair<Long,Long>, Nota> repo;
    private XMLFileRepository<Long,Student> stRepo;
    private XMLFileRepository<Long, Tema> tRepo;
    private ValidareNota validator;
    private StructuraAnUniversitar strAnUniv = StructuraAnUniversitar.getInstance();
    private List<Observer> observers = new ArrayList<>();

    /**
     * Constructor pentru ServiceNota
     * @param repo - repository Nota
     * @param validator - validator Nota
     * @param stRepo - repository Student
     * @param tRepo - repository Tema
     */
    public ServiceNota(XMLFileRepository<Pair<Long,Long>, Nota> repo,
                       ValidareNota validator,
                       XMLFileRepository<Long,Student> stRepo,
                       XMLFileRepository<Long,Tema> tRepo) {
        this.repo = repo;
        this.validator = validator;
        this.stRepo=stRepo;
        this.tRepo=tRepo;
    }

    /**
     * Functie care adauga o nota
     * @param n - nota de adaugat
     * @return - null - daca nota s-a salvat, nota data - altfel
     * @throws ValidationException - daca entitatea nu este valida
     */
    public Nota addNota(Nota n) throws ValidationException {
        validator.validate(n);
       Nota nn=repo.save(n);
        if (nn==null)
            notifyObservers();
        return nn;
    }

    /**
     * Functie care calculeaza punctele cu care este depunctata o nota din cauza intarzierilor
     * @param id - id-ul notei
     * @return - punctele cu care este depunctata valoarea notei
     */
    public Double intarziere(Long id)
    {
        int diff=tRepo.findOne(id).getDeadlineWeek()-strAnUniv.getCurrentWeek();
        double intarziere=0;
        if (diff==-1)
            intarziere=1;
        else if (diff==-2)
            intarziere=2;
        else if (diff<-2)
            intarziere=9.00;
        return intarziere;
    }

    /**
     * Functie care calculeaza punctele cu care este depunctata o nota din cauza intarzierilor, in cazul in care
     * a intarziat si profesorul cu adaugarea notei
     * @param id - id-ul notei
     * @return - punctele cu care este depunctata valoarea notei
     */
    public Double intarziereProf(Long id,int nrSaptIntPredare)
    {
        if (nrSaptIntPredare==0)
            return 0D;
        else if (nrSaptIntPredare==1)
            return 1D;
        else if (nrSaptIntPredare==2)
            return 2D;
        else
            return 9D;
    }

    /**
     * Functie care calculeaza punctele cu care este depunctata o nota din cauza intarzierilor, in cazul in care
     * studentul are scutire medicala
     * @param id - id-ul notei
     * @return - punctele cu care este depunctata valoarea notei
     */
    public Double intarziereMedical(Long id,int inceput,int sfarsit,boolean intarziereProf,int nrsaptintarziere)
    {

        double intarziere=0;
        Tema t=tRepo.findOne(id);
        int diff;
        int sapt_predare;
        if (intarziereProf)  sapt_predare=t.getDeadlineWeek()+nrsaptintarziere;
        else
            sapt_predare=strAnUniv.getCurrentWeek();

        if (sfarsit<t.getDeadlineWeek()) return intarziere(id);
        if (inceput>strAnUniv.getCurrentWeek()) return intarziere(id);

        if (t.getDeadlineWeek()==inceput || inceput<t.getDeadlineWeek())
        {
            diff=sfarsit+1-sapt_predare;
            if (diff==-1)
                intarziere=1;
            else if (diff==-2)
                intarziere=2;
            else if (diff<-2)
                intarziere=9.00;
        }
        else
        {
            diff=t.getDeadlineWeek()-inceput;
            if (diff==-1)
                intarziere=1;
            else if (diff==-2)
                intarziere=2;
            else if (diff<-2)
                intarziere=9.00;

            int diff2=sfarsit+1-sapt_predare;
            if (diff2==-1)
                intarziere+=1;
            else if (diff2==-2)
                intarziere+=2;
            else if (diff2<-2)
                intarziere=9;
        }

        return intarziere;
    }

    /**
     * Functie care cauta o nota dupa id
     * @param id - id-ul notei cautate
     * @return - nota, daca aceasta a fost gasita, altfel returneaza null
     */
    public Nota findNota(Pair<Long,Long> id) {

        return this.repo.findOne(id);
    }

    /**
     * Functie care returneaza un Iterable care contine toate notele din repository
     * @return - Iterable care contine toate notele din repository
     */
    public Iterable<Nota> getAllN(){

        return this.repo.findAll();
    }

    /**
     * Functie care returneaza un List cu toti studentii din Student repository
     * @return - List cu toti studentii
     */
    private  List<Student> getStudenti()
    {
        return StreamSupport.stream(stRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
    /**
     * Functie care returneaza un List cu toate temele din Tema repository
     * @return - List cu toate temele
     */
    public List<Tema> getTeme()
    {
       return StreamSupport.stream(tRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Functie care returneaza un List cu toate notele din repository
     * @return - List cu toti studentii din repository
     */
    private List<Nota> getNote()
    {
        return StreamSupport.stream(repo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Functie care returneaza lista notelor unui student dat
     * @param sId - id-ul studentului
     * @return - lista notelor unui student dat
     */
    public List<Nota> noteStudent(Long sId)
    {

        List<Nota> note=getNote();

        return note.stream()
                .filter(nota -> nota.getId().getKey().equals(sId))
                .collect(Collectors.toList());
    }

    /**
     * Functie care verifica daca exista o nota pentru un student la o tema data
     * @param stId - id-ul studentului
     * @param tId - id-ul temei
     * @return - returneaza 1 daca nu exista o nota pentru studentul si tema data,
     *                sau valoarea notei in caz contrar
     */
    public double verifNotaExista(Long stId,Long tId) {
        List<Tema> teme = new ArrayList<>();
        tRepo.findAll().forEach(teme::add);

        List<Nota> note = new ArrayList<Nota>();
        repo.findAll().forEach(note::add);


        Predicate<Nota> p1=nota -> nota.getId().getValue().equals(tId);
        Predicate<Nota> p2=nota -> nota.getId().getKey().equals(stId);

        List<Nota> collect = note.stream()
                .filter(p1.and(p2))
                .collect(Collectors.toList());
       if (collect.isEmpty())
       {
       return 1D;
       }
       else {
           return collect.get(0).getNota();
       }
    }

   /**
     * Functie care calculeaza media unui student
     * @param sId - id-ul studentului
     * @return - media ponderata a notelor studentului
     */
    public Double raport1(Long sId)
    {
        List<Tema> teme=getTeme();

        Optional<Integer> ponderiSuma = teme.stream()
                .map(t -> t.getDeadlineWeek() - t.getStartWeek()
                )
                .reduce(Integer::sum);

        if (ponderiSuma.get()==0) return 0D;

        Optional<Double> notapondere=teme.stream()
                .map(t-> verifNotaExista(sId,t.getId())*(t.getDeadlineWeek()-t.getStartWeek())
                )
                .reduce(Double::sum);

        return notapondere.get()/ponderiSuma.get();
    }

    /**
     * Functie care calculeaza media studentilor
     * @return - List cu mediile studentilor
     */
    public List<Double> raport1Studenti()
    {
        List<Student> studenti=getStudenti();

        return studenti.stream()
                .map(s->raport1(s.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Functie care calculeaza media notelor pentru o nota
     * @param tId - id-ul temei
     * @return - media notelor pentru o tema data
     */
    public Double medieTema(Long tId)
    {
        List<Nota> note = getNote();

        List<Student> studenti=getStudenti();

        Predicate<Nota> p=n-> n.getId().getValue().equals(tId);
        Optional<Double> sumaNote = note.stream()
                .filter(p)
                .map(Nota::getNota)
                .reduce(Double::sum);

        long nrTemePredate = note.stream()
                .filter(p)
                .count();

        double nepredate=studenti.size()-nrTemePredate;//studentii care nu au predat tema au nota 1
        if (!sumaNote.isPresent()) return  (nepredate)/studenti.size();
        return (sumaNote.get()+nepredate)/studenti.size();
    }

    /**
     * Functie care returneaza temele ce au cea mai mica medie a notelor
     * @return - List cu temele
     */
    public List<Tema> temaCeaMaiMicaMedie()
    {
        List<Tema> teme = getTeme();

        List<Tema> rez=new ArrayList<>();

        double min=1000;

        for (Tema t: teme) {
            if (medieTema(t.getId())<min)
            {
                min=medieTema(t.getId());
            }
        }

        for (Tema t:teme)
        {
            if (min==medieTema(t.getId()))
                rez.add(t);
        }

        return rez;
    }
  /**
     * Functie care returneaza studentii care au media mai mare sau egala cu 4
     * @return - List cu studentii
     */
    public List<Student> raport3Studenti()
    {
        List<Student> studenti=getStudenti();

        Predicate<Student> p=s->raport1(s.getId())>=4;
        return studenti.stream()
                .filter(p)
                .collect(Collectors.toList());

    }

    /**
     * Functie care verifica daca un student a predat la timp toate temele
     * @param stId - id-ul studentului
     * @return - true, daca studnetul a predat la timp toate temele, false altfel
     */
    private boolean raport4(Long stId)
    {
        List<Nota> noteSt=noteStudent(stId);

        Predicate<Nota> p=n->strAnUniv.getWeekNumber(n.getData())<=tRepo.findOne(n.getId().getValue()).getDeadlineWeek();

        List<Tema> teme = getTeme();

        List<Nota> collect = noteSt.stream()
                .filter(p)
                .collect(Collectors.toList());
        return collect.size() == teme.size();
    }

    /**
     * Functie care returneaza studentii care au predat l atimp toate temele
     * @return - List cu studnetii
     */
    public List<Student> raport4Studenti()
    {
        List<Student> studenti=new ArrayList<>();
        stRepo.findAll().forEach(studenti::add);

        Predicate<Student> p=s->raport4(s.getId())==true;

        return studenti.stream()
                .filter(p)
                .collect(Collectors.toList());
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
}
