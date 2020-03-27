package repository;

import domains.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import validators.Validator;


public class StudentXMLFileRepository extends XMLFileRepository<Long,Student> {

    /**
     * Constructor XML File Repository pentru entitatea Student
     * @param validator - validator pentru Student
     * @param fileName - calea catre fisierul XML
     */
    public StudentXMLFileRepository(Validator<Student> validator, String fileName) {
        super(validator, fileName);
    }

    /**
     * Suprascrierea metodei createElemenetfromEntity
     * @param document - tipul documentului
     * @param student - studentul care trebuie transformat
     * @return - obiectul de tip Element format
     */
    @Override
    public Element createElementfromEntity(Document document, Student student) {
        Element element = document.createElement("student");

        element.setAttribute("id", student.getId().toString());

        Element nume = document.createElement("nume");
        nume.setTextContent(student.getNume());
        element.appendChild(nume);


        Element prenume = document.createElement("prenume");
        prenume.setTextContent(student.getPrenume());
        element.appendChild(prenume);

        Element grupa = document.createElement("grupa");
        grupa.setTextContent(((Integer) student.getGrupa()).toString());
        element.appendChild(grupa);

        Element email = document.createElement("email");
        email.setTextContent((student.getEmail()));
        element.appendChild(email);

        Element prof = document.createElement("profesor");
        prof.setTextContent((student.getCadruDidacticIndrumatorLab()));
        element.appendChild(prof);

        return element;
    }

    /**
     * Suprascrierea metodei createEntityfromElement
     * @param studentElement - obiect Element
     * @return - o noua entitate Student formata
     */
    @Override
    public Student createEntityfromElement(Element studentElement) {
        String id = studentElement.getAttribute("id");
        String nume = studentElement.getElementsByTagName("nume")
                .item(0)
                .getTextContent();

        String prenume = studentElement.getElementsByTagName("prenume")
                .item(0)
                .getTextContent();

        String grupa = studentElement.getElementsByTagName("grupa")
                .item(0)
                .getTextContent();

        String email= studentElement.getElementsByTagName("email")
                .item(0)
                .getTextContent();

        String prof= studentElement.getElementsByTagName("profesor")
                .item(0)
                .getTextContent();

        Student s= new Student(Long.parseLong(id), nume,prenume,Integer.parseInt(grupa),email,prof);
        s.setId(Long.parseLong(id));
        return s;
    }

}
