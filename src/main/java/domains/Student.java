package domains;

import java.util.Objects;

public class Student extends Entity<Long> {
    private Long ID;
    private String nume;
    private String prenume;
    private Integer grupa;
    private String email;
    private String cadruDidacticIndrumatorLab;

    /**
     * Constructor pentru entitatea Student
     * @param ID : Long - id-ul studentului
     * @param nume : String - numele studentului
     * @param prenume : String - prenumele studentului
     * @param grupa : Integer - grupa studentului
     * @param email : String - emailul studentului
     * @param cadruDidacticIndrumatorLab - profesorul îndrumator al studentului
     */
    public Student(Long ID, String nume, String prenume, Integer grupa, String email, String cadruDidacticIndrumatorLab) {
        this.ID=ID;
        this.nume = nume;
        this.prenume = prenume;
        this.grupa = grupa;
        this.email = email;
        this.cadruDidacticIndrumatorLab = cadruDidacticIndrumatorLab;
    }

    /**
     * Funcție care obține id-ul studentului
     * @return - id-ul studentului
     */
    @Override
    public Long getId() {
        return super.getId();
    }

    /**
     * Funcție care setează Id-ul studentului
     * @param aLong - noul id al studentului
     */
    @Override
    public void setId(Long aLong) {
        super.setId(aLong);
    }

    /**
     * Funcție care obține numele studentului
     * @return - numele studentului
     */
    public String getNume() {
        return nume;
    }

    /**
     * Functie care setează numele studentului
     * @param nume - noul nume al studentului
     */
    public void setNume(String nume) {
        this.nume = nume;
    }


    /**
     * Funcție care obține prenumele studentului
     * @return - prenumele studentului
     */
    public String getPrenume() {
        return prenume;
    }

    /**
     * Funcție care setează prenumele studentului
     * @param prenume - noul prenume al studentului
     */
    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    /**
     * Funcție care obține grupa studentului
     * @return - grupa studentului
     */
    public Integer getGrupa() {
        return grupa;
    }

    /**
     * Funcție care setează grupa unui student
     * @param grupa - noua grupa a studentului
     */
    public void setGrupa(Integer grupa) {
        this.grupa = grupa;
    }

    /**
     * Funcție care obține emailul studentului
     * @return - emailul studentului
     */
    public String getEmail() {
        return email;
    }

    /**
     * Funcție care setează emailul
     * @param email - noul email al studentului
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Funcție care obtine profesorul îndrumator al studentului
     * @return - profesorul îndrumator al studentului
     */
    public String getCadruDidacticIndrumatorLab() {

        return cadruDidacticIndrumatorLab;
    }

    /**
     * Funcție care setează profesorul îndrumator al studentului
     * @param cadruDidacticIndrumatorLab - noul profesor al studentului
     */
    public void setCadruDidacticIndrumatorLab(String cadruDidacticIndrumatorLab) {
        this.cadruDidacticIndrumatorLab = cadruDidacticIndrumatorLab;
    }


    /**
     * Suprascrierea metodei equals
     * @param o - obiect de tipul Object
     * @return - true, dacă o este egal cu this - false, altfel
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(super.getId(), student.getId()) &&
                Objects.equals(getNume(), student.getNume()) &&
                Objects.equals(getPrenume(), student.getPrenume()) &&
                Objects.equals(getGrupa(), student.getGrupa()) &&
                Objects.equals(getEmail(), student.getEmail()) &&
                Objects.equals(getCadruDidacticIndrumatorLab(), student.getCadruDidacticIndrumatorLab());
    }

    /**
     * Suprascrierea metodei toString
     * @return - string format cu atributele studentului
     */
    @Override
    public String toString() {
        return "Student{" +
                "ID=" + super.getId() +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", grupa=" + grupa +
                ", email='" + email + '\'' +
                ", cadruDidacticIndrumatorLab='" + cadruDidacticIndrumatorLab + '\'' +
                '}';
    }
}
