package repository;

import domains.Nota;
import domains.Student;
import javafx.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import validators.ValidationException;
import validators.Validator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class NotaXMLFileRepository extends XMLFileRepository<Pair<Long,Long>,Nota> {

    /**
     * Constructor XML File Repository pentru entitatea Nota
     * @param validator - validator pentru Nota
     * @param fileName - calea catre fisierul XML
     */
    public NotaXMLFileRepository(Validator<Nota> validator, String fileName) {
        super(validator,fileName);
    }

    /**
     * Suprascrierea metodei createElemenetfromEntity
     * @param document - tipul documentului
     * @param nota - nota care trebuie transformata
     * @return - obiectul de tip Elememt format
     */
    @Override
    public Element createElementfromEntity(Document document, Nota nota) {
        Element element = document.createElement("nota");

        element.setAttribute("ids", nota.getId().getKey().toString());
        element.setAttribute("idt", nota.getId().getValue().toString());

        Element notaval = document.createElement("nota");
        notaval.setTextContent(((Double) nota.getNota()).toString());
        element.appendChild(notaval);


        Element data = document.createElement("data");
        data.setTextContent(((LocalDate) nota.getData()).toString());
        element.appendChild(data);

        Element prof = document.createElement("profesor");
        prof.setTextContent(nota.getProfesor());
        element.appendChild(prof);

        Element feedback = document.createElement("feedback");
        feedback.setTextContent((nota.getFeedback()));
        element.appendChild(feedback);

        return element;
    }

    /**
     * Suprascrierea metodei createEntityfromElement
     * @param notaElement - obiect Element
     * @return - o noua entitate Nota formata
     */
    @Override
    public Nota createEntityfromElement(Element notaElement) {
        String ids = notaElement.getAttribute("ids");
        String idt = notaElement.getAttribute("idt");
        String notaval = notaElement.getElementsByTagName("nota")
                .item(0)
                .getTextContent();

        String data = notaElement.getElementsByTagName("data")
                .item(0)
                .getTextContent();

        String prof= notaElement.getElementsByTagName("profesor")
                .item(0)
                .getTextContent();
        String feedback= notaElement.getElementsByTagName("feedback")
                .item(0)
                .getTextContent();

        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-d");
        Nota n= new Nota(Long.parseLong(ids),Long.parseLong(idt), Double.parseDouble(notaval),LocalDate.parse(data,formatter),prof,feedback);
        Long pids=Long.parseLong(ids);
        Long pidt=Long.parseLong(idt);
        n.setId(new Pair<>(pids,pidt));
        return n;
    }

}
