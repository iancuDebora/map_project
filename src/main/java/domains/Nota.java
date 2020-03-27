package domains;

import javafx.util.Pair;

import java.time.LocalDate;

public class Nota extends Entity<Pair<Long,Long>> {
    private Pair<Long,Long> ID;
    private Double nota;
    private LocalDate data;
    private String profesor;
    private String feedback;

    /**
     * Constructor pentru entitatea Nota
     * @param stId : Long - id-ul studentului
     * @param temaId : Long - id-ul temei
     * @param nota : Double - valoarea notei
     * @param data : LocalDate - data predarii temei
     * @param profesor : String - profesorul care introduce nota
     * @param feedback : String - feedback-ul profesorului legat de temă
     */
    public Nota(Long stId, Long temaId,Double nota, LocalDate data, String profesor, String feedback) {
        this.ID = new Pair<>(stId,temaId);
        this.data = data;
        this.nota =nota;
        this.profesor = profesor;
        this.feedback = feedback;
    }


    /**
     * Funcție care obține id-ul studentului
     * @return - id-ul studentului
     */
    public Long getStID()
    {
        return getId().getKey();
    }

    /**
     * Funcție care obține id-ul temei
     * @return - id-ul temei
     */
    public Long getTemaID()
    {
        return getId().getValue();
    }

    /**
     * Funcție care obține feedback-ul
     * @return - feedback-ul oferit de profesor
     */
    public String getFeedback() {

        return feedback;
    }

    /**
     * Funcție care setează feedback-ul
     * @param feedback - noul feedback
     */
    public void setFeedback(String feedback) {

        this.feedback = feedback;
    }

    /**
     * Funcție care obține valoarea notei
     * @return - valoarea notei
     */
    public Double getNota() {

        return nota;
    }

    /**
     * Funcție care setează valoarea notei
     * @param nota - noua valoare a notei
     */
    public void setNota(Double nota) {

        this.nota = nota;
    }

    /**
     * Funcție care setează id-ul studentului
     * @param stID - id-ul studentului
     */
    public void setStID(Long stID)
    {
        setId(new Pair<>(stID,getId().getValue()));
    }

    /**
     * Funcție care setează id-ul temei
     * @param temaID - id-ul temei
     */
    public void setTemaID(Long temaID)
    {
        setId(new Pair<>(getId().getKey(),temaID));
    }

    /**
     * Funcție care obtine Id-ul
     * @return - id-ul notei
     */
    @Override
    public Pair<Long, Long> getId() {
        return super.getId();
    }

    /**
     * Funcție care setează id-ul unei note
     * @param longLongPair - noul id al notei
     */
    @Override
    public void setId(Pair<Long, Long> longLongPair) {
        super.setId(longLongPair);
    }

    /**
     * Funcție care obține data predării temei
     * @return - data în care nota a fost obținută
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Funcție care setează data predării temei
     * @param data - data cand tema a fost predată
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /**
     * Funcție care obține profesorul care a introdus nota
     * @return - numele profesorului
     */
    public String getProfesor() {
        return profesor;
    }

    /**
     * Funcție care setează profesorul responsabil de corectarea temei
     * @param profesor - numele noului profesor
     */
    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    /**
     * Suprascrierea metodei toString
     * @return - string format cu atributele notei
     */
    @Override
    public String toString() {
        return "Nota{" +
                "ID=" + ID +
                ", nota=" + nota +
                ", data=" + data +
                ", profesor='" + profesor + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
