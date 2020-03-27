package validators;

import domains.Tema;

public class ValidareTema implements Validator<Tema> {
    /**
     * Suprascrierea metodei validate pentru o entitate Tema
     * @param entity - tema de validat
     * @throws ValidationException - daca tema e invalida
     */
    @Override
    public void validate(Tema entity) throws ValidationException {
        String erori="";
        if (entity.getId()<0)
            erori+="Id-ul trebuie sa fie un numar pozitiv";
        if (entity.getDescriere().equals(""))
            erori+="Descrierea nu poate fi vida";
        if (entity.getStartWeek()<1 || entity.getStartWeek()>14)
            erori+="startWeek trrebuie sa fie un numar intre 1 si 14";
        if (entity.getDeadlineWeek()<1 || entity.getDeadlineWeek()>14)
            erori+="DeadlineWeek trrebuie sa fie un numar intre 1 si 14";
        if (entity.getDeadlineWeek()<=entity.getStartWeek())
            erori+="Deadlineweek trebuie sa fie mai mare decat startweek";

        if (erori.length()!=0)
            throw new ValidationException(erori);
    }
}
