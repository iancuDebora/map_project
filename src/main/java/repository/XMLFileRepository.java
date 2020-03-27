package repository;

import domains.Entity;
import domains.Student;
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

public abstract class XMLFileRepository<ID,E extends Entity<ID>> extends InMemoryRepository<ID,E>{

    private String fileName;

    /**
     * Constructor XML File Repository pentru o entitate generica
     * @param validator - validator pentru entitate
     * @param fileName - path-ul catre fisierul XML
     */
    public XMLFileRepository(Validator<E> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }


    public abstract Element createElementfromEntity(Document document,E entity);

    public abstract E createEntityfromElement(Element element);

    /**
     * Functie care citeste datele din fisier
     */
    private void loadData() {
        try {
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(this.fileName);

            Element root = document.getDocumentElement();
            NodeList children = root.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node element = children.item(i);
                if (element instanceof Element) {
                    E entity=createEntityfromElement((Element) element);
                    super.save(entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Functie care scrie datele in fisier
     */
    private void writeToFile() {
        try {
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .newDocument();
            Element root = document.createElement("entity");
            document.appendChild(root);
            super.findAll().forEach(s -> {
                Element e = createElementfromEntity(document, s);
                root.appendChild(e);
            });

            Transformer transformer = TransformerFactory.
                    newInstance().newTransformer();

            Source source=new DOMSource(document);

            transformer.transform(source,
                    new StreamResult(fileName));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Suprascrierea metodei save pentru fisiere XML
     * @param entity - entitatea generica care trebuie salvata
     * entity must be not null
     * @return - null - daca entitatea s-a salvat, entitatea data - altfel
     * @throws ValidationException - daca entitatea e invalida
     */
    @Override
    public E save(E entity) throws ValidationException {
        E en = super.save(entity);
        if (en == null) {
            writeToFile();
        }
        return en;
    }


    /**
     * Suprascrierea metodei delete pentru fisiere XML
     * @param id - id-ul entitatii care trebuie sterse
     * id must be not null
     * @return - entitatea daca aceasta s-a sters, alfel null
     * @throws IllegalArgumentException - daca id-ul este null
     */
    @Override
    public E delete(ID id) throws IllegalArgumentException {
        E aux = super.delete(id);
        writeToFile();
        return aux;
    }

    /**
     * Suprascrierea metodei update pentru fisiere XML
     * @param entity - entitatea care trebuie modificata
     * entity must not be null
     * @return - null, daca entitatea a fost modificata, altfel returneaza entitatea
     * @throws IllegalArgumentException - daca entitatea este null
     */
    @Override
    public E update(E entity) throws  IllegalArgumentException {
        E aux = super.update(entity);
        writeToFile();
        return aux;
    }


}

