package validators;

import domains.Student;

public class ValidareStudent implements Validator<Student> {

    /**
     * Suprascrierea metodei validate pentru o entitate Student
     * @param entity - studentul de validat
     * @throws ValidationException - daca studentul e invalid
     */
    @Override
    public void validate(Student entity) throws ValidationException {
        if(entity == null){
            throw new IllegalArgumentException("Studentul nu poate fi null!");
        }
        String erori="";
        if (entity.getId()<0)
            erori+="Id-ul trebuie sa fie un numar pozitiv";
        if (entity.getNume().equals(""))
            erori+="Numele nu poate fi vid";
        if (entity.getGrupa()<0)
            erori+="Grupa trebuie sa fie un numar pozitiv";
        if (entity.getPrenume().equals(""))
            erori+="Prenumele nu poate fi vid";
        if (entity.getCadruDidacticIndrumatorLab().equals(""))
            erori+="Profesorul nu poate fi vid";
        if (entity.getEmail().equals(""))
            erori+="Emailul nu poate fi vid";

        if (erori.length()!=0)
            throw new ValidationException(erori);
    }
}
