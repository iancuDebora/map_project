package controller;

import domains.Nota;
import domains.Student;
import domains.Tema;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import repository.XMLFileRepository;
import services.ServiceNota;
import validators.ValidationException;

public class PreviewController {

    @FXML
    private Label studentLabel;

    @FXML
    private Label temaLabel;

    @FXML
    private Label notaLabel;

    @FXML
    private Label penalizareLabel;

    @FXML
    private  Label motivareLabel;

    private ServiceNota notaService;
    private Stage dialogstage;
    private Nota n;
    private Student s;
    private Tema t;
    private boolean scutire;
    private String inceput;
    private String sfarsit;
    private double depunctare;

    /**
     * Functie care initializeaza obiecte pentru PreviewController
     * @param notaService - service pentru Nota
     * @param dialogstage - obiect Stage
     * @param n - obiect Nota
     * @param s - obiect Student
     * @param t - obiect Tema
     * @param depunctare - nr de puncte cu care a fost depunctat tema
     * @param scutire - scutirea studentului
     * @param inceput - saptamana in care a inceput scutirea studentului
     * @param sfarsit - saptamana in care scutirea studentului s-a incheiat
     */
    public void setPreviewController(ServiceNota notaService, Stage dialogstage,Nota n,Student s, Tema t,
                                     double depunctare,boolean scutire,String inceput,String sfarsit)
    {
        this.notaService = notaService;
        this.dialogstage = dialogstage;
        this.n=n;
        this.s=s;
        this.t=t;
        this.depunctare=depunctare;
        this.scutire=scutire;
        this.inceput=inceput;
        this.sfarsit=sfarsit;
        setFields();
    }

    /**
     * Functie care completeaza campurile
     */
    @FXML
    private void setFields()
    {
        studentLabel.setText(s.toString());
        temaLabel.setText(t.toString());
        notaLabel.setText(n.getNota().toString());
        if (depunctare!=0)
            penalizareLabel.setText("Nota a fost penalizata cu "+depunctare+" puncte datorita intarzierilor");
        else
            penalizareLabel.setText("Tema a fost predata la timp.");
        if (scutire && !inceput.equals(sfarsit)) motivareLabel.setText("Studentul a avut motivare pentru saptamanile "+inceput +" si "+sfarsit);
        else if (scutire)
            motivareLabel.setText("Studentul a avut motivare pentru saptamana "+ inceput);
    }

    /**
     * Functie care se ocupa de butonul 'Cancel'
     */
    @FXML
    private void handleCancel()
    {
        dialogstage.close();
    }

    /**
     * Functie care se ocupa de butonul 'Save'
     */
    @FXML
    private void handleSave()
    {

        try {
            notaService.addNota(n);
        } catch (ValidationException e) {
            e.printStackTrace();
        }

        dialogstage.close();
    }
}
