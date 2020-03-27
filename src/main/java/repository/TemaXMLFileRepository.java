package repository;

import domains.Tema;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import validators.Validator;

public class TemaXMLFileRepository extends XMLFileRepository<Long, Tema> {


    /**
     * Constructor XML File Repository pentru entitatea Tema
     * @param validator - validator pentru Tema
     * @param fileName - calea catre fisierul XML
     */
    public TemaXMLFileRepository(Validator<Tema> validator, String fileName) {
        super(validator,fileName);
    }

    /**
     * Suprascrierea metodei createElemenetfromEntity
     * @param document - tipul documentului
     * @param tema - tema care trebuie transformata
     * @return - obiectul Element format
     */
    @Override
    public Element createElementfromEntity(Document document, Tema tema) {
        Element element = document.createElement("tema");

        element.setAttribute("id", tema.getId().toString());

        Element descriere = document.createElement("descriere");
        descriere.setTextContent(tema.getDescriere());
        element.appendChild(descriere);

        Element startweek = document.createElement("startweek");
        startweek.setTextContent(((Integer) tema.getStartWeek()).toString());
        element.appendChild(startweek);

        Element deadlineweek= document.createElement("deadlineweek");
        deadlineweek.setTextContent(((Integer) tema.getDeadlineWeek()).toString());
        element.appendChild(deadlineweek);

        return element;
    }


    /**
     * Suprascrierea metodei createEntityfromElement
     * @param temaElement - obiect Element
     * @return - o noua entitate Student formata
     */
    @Override
    public Tema createEntityfromElement(Element temaElement) {
        String id = temaElement.getAttribute("id");
        String descriere = temaElement.getElementsByTagName("descriere")
                .item(0)
                .getTextContent();

        String startweek = temaElement.getElementsByTagName("startweek")
                .item(0)
                .getTextContent();

        String deadlineweek = temaElement.getElementsByTagName("deadlineweek")
                .item(0)
                .getTextContent();

        Tema t = new Tema(Long.parseLong(id), descriere,Integer.parseInt(startweek),Integer.parseInt(deadlineweek));
        t.setId(Long.parseLong(id));
        return t;
    }

}

