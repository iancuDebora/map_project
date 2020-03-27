package domains;

import java.util.Objects;

public class Tema extends Entity<Long> {
    private Long ID;
    private String descriere;
    private Integer startWeek;
    private Integer deadlineWeek;


    /**
     * Constructor pentru entitatea Tema
     * @param ID : Long - id-ul temei
     * @param descriere : String - descrierea temei
     * @param startWeek : Integer - săptămâna în care studenții primesc tema
     * @param deadlineWeek : Integer - săptămâna în care studenții trebuie să predea tema
     */
    public Tema(Long ID, String descriere, Integer startWeek, Integer deadlineWeek) {
        this.ID = ID;
        this.descriere = descriere;
        this.startWeek = startWeek;
        this.deadlineWeek = deadlineWeek;
    }

    /**
     * Functie care obtine id-ul studentului
     * @return - id-ul studentului
     */
    @Override
    public Long getId() {
        return super.getId();
    }

    /**
     * Functie care seteaza id-ul temei
     * @param aLong - noul id al temei
     */
    @Override
    public void setId(Long aLong) {
        super.setId(aLong);
    }

    /**
     * Functie care obtine descrierea temei
     * @return - descrierea temei
     */
    public String getDescriere() {

        return descriere;
    }

    /**
     * Functie care seteaza descrierea temei
     * @param ndescriere - noua descriere a temei
     */
    public void setDescriere(String ndescriere) {

        descriere = ndescriere;
    }

    /**
     * Functie care obtine StartWeek-ul temei
     * @return - startweek-ul temei
     */
    public Integer getStartWeek() {

        return startWeek;
    }

    /**
     * Functie care seteaza startweek-ul temei
     * @param nstartWeek - noul startweek al temei
     */
    public void setStartWeek(Integer nstartWeek) {

        startWeek = nstartWeek;
    }

    /**
     * Functie care obtine deadlineweek-ul temei
     * @return - deadlineweek-ul temei
     */
    public Integer getDeadlineWeek() {

        return deadlineWeek;
    }

    /**
     * Fucntie care seteaza deadlineweek-ul temei
     * @param ndeadlineWeek - noul deadllineweek al temei
     */
    public void setDeadlineWeek(Integer ndeadlineWeek) {

        deadlineWeek = ndeadlineWeek;
    }

    /**
     * Suprascrierea metodei equals
     * @param o - obiect Object
     * @return - string format cu atributele temei
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tema)) return false;
        Tema tema = (Tema) o;
        return Objects.equals(ID, tema.ID) &&
                Objects.equals(getDescriere(), tema.getDescriere()) &&
                Objects.equals(getStartWeek(), tema.getStartWeek()) &&
                Objects.equals(getDeadlineWeek(), tema.getDeadlineWeek());
    }

    /**
     * Suprascrierea metodei hashCode
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(ID, getDescriere(), getStartWeek(), getDeadlineWeek());
    }

    /**
     * Suprascrirea metodei toString
     * @return
     */
    @Override
    public String toString() {
        return "Tema{" +
                "ID=" + super.getId() +
                ", descriere='" + descriere + '\'' +
                ", startWeek=" + startWeek +
                ", deadlineWeek=" + deadlineWeek +
                '}';
    }


}

