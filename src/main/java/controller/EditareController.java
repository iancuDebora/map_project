package controller;

import domains.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceStudent;
import validators.ValidareStudent;
import validators.ValidationException;




public class EditareController {

    @FXML
    private TextField textFieldId;

    @FXML
    private TextField textFieldNume;

    @FXML
    private TextField textFieldPrenume;

    @FXML
    private TextField textFieldGrupa;

    @FXML
    private TextField textFieldEmail;

    @FXML
    private TextField textFieldCadruDidacticIndrumatorLab;

    private ServiceStudent stService;
    private Stage dialogstage;
    private Student student;

    /**
     * Functie care seteaza obiectele pentru PreviewController
     * @param stService - service pentru Student
     * @param dialogstage - obiectStage
     * @param s - obiect student
     */
    public void setEditareController(ServiceStudent stService, Stage dialogstage, Student s) {
        this.stService = stService;
        this.dialogstage = dialogstage;
        this.student=s;
        if (s!=null)
        {
            setFields(s);
            textFieldId.setEditable(false);
        }
    }

    /**
     * Functie care verifica daca un string este numar
     * @param strNum - string-ul pentru care se face verificarea
     * @return - false, daca string-ul nu e numar - true, altfel
     */
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Functie care salveaza sau modifica un student
     * la actionarea butonului 'Salveaza'
     */
    @FXML
    public void handleSave()
    {
        String id=textFieldId.getText();
        if (!isNumeric(id))
        {
            MesajAlerta.afiseazaMesaj(null, Alert.AlertType.INFORMATION,"","Id-ul trebuie sa fie un numar!");
        }
        String nume=textFieldNume.getText();
        String prenume=textFieldPrenume.getText();

        String grupa=textFieldGrupa.getText();
        if (!isNumeric(grupa))
        {
            MesajAlerta.afiseazaMesaj(null, Alert.AlertType.INFORMATION,"","Grupa trebuie sa fie un numar!");
        }
        String email=textFieldEmail.getText();
        String prof=textFieldCadruDidacticIndrumatorLab.getText();

        Student s=new Student(Long.parseLong(id),nume,prenume,Integer.parseInt(grupa),email,prof);
        s.setId(Long.parseLong(id));

        if (student==null)
            saveSt(s);
        else
            updateSt(s);
    }

    /**
     * Functie care seteaza field-urile cu datele studentului
     * @param st - student
     */
    private void setFields(Student st)
    {
        textFieldId.setText(st.getId().toString());
        textFieldNume.setText(st.getNume());
        textFieldPrenume.setText(st.getPrenume());
        textFieldGrupa.setText(st.getGrupa().toString());
        textFieldEmail.setText(st.getEmail());
        textFieldCadruDidacticIndrumatorLab.setText(st.getCadruDidacticIndrumatorLab());
    }

    /**
     * Functie care salveaza un student
     * @param s - studentul care se salveaza
     */
    private void saveSt(Student s)
    {
        try {
            Student st=stService.addSt(s);
            if (st==null) {
                dialogstage.close();
                MesajAlerta.afiseazaMesaj(null, Alert.AlertType.INFORMATION, "Adaugare student",
                        "Studentul a fost adaugat cu succes");
            }
        } catch (ValidationException e) {
            MesajAlerta.afiseazaMesajEroare(null,e.getMessage());
        }

    }

    /**
     * Functie care modifica un student
     * @param s - student
     */
    private void updateSt(Student s)
    {
        try {
           Student st=stService.updateSt(s);
            if (st==null)
                MesajAlerta.afiseazaMesaj(null, Alert.AlertType.INFORMATION,"Modificare student",
                        "Datele studentului au fost modificate cu succes");
        } catch (ValidationException e) {
            MesajAlerta.afiseazaMesajEroare(null,e.getMessage());
        }
        dialogstage.close();
    }

    /**
     * Functie care inchide fereastra
     * la actionarea butonului 'Cancel'
     */
    @FXML
    public void handleCancel(){

        dialogstage.close();
    }

}
