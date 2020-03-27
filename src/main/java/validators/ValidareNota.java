package validators;

import domains.Nota;

public class ValidareNota  implements Validator<Nota>{

    /**
     * Suprascrierea metodei validate pentru o entitate Nota
     * @param entity - nota de validat
     * @throws ValidationException - daca nota e invalida
     */
    @Override
    public void validate(Nota entity) throws ValidationException {
        String erori="";
        if (entity==null)
            throw new IllegalArgumentException("Nota nu poate fi null");
        if (entity.getId().getValue()<0)
            erori+="Id-ul studentului nu poate fi null";
        if (entity.getId().getValue() <0)
            erori+="Id-ul temei nu poate fi null";
        if (entity.getNota()<1 || entity.getNota() >10)
            erori+="Nota trebuie sa fie intre 1 si 10";
        if (entity.getData()==null)
            erori+="Data nu poate fi null";
        if (entity.getFeedback().equals(""))
            erori+="Feedback-ul nu poate fi vid";
        if (entity.getProfesor().equals(""))
            erori+="Numele profesorului nu poate fi vid";

        if (erori.length()>0)
            throw new ValidationException(erori);
    }
}
