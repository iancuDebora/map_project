package controller;

import config_services.ApplicationContext;
import domains.Nota;
import domains.StructuraAnUniversitar;
import domains.Student;
import domains.Tema;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import repository.StudentXMLFileRepository;
import repository.TemaXMLFileRepository;
import services.ServiceNota;
import services.ServiceStudent;
import services.ServiceTema;
import utils.observer.Observable;
import utils.observer.Observer;
import validators.ValidareStudent;
import validators.ValidationException;


import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ControllerNote implements Observer {

    private ServiceNota notaService;
    private ServiceStudent studentService;
    private ServiceTema temaService;
    private ObservableList<Nota> modelNote = FXCollections.observableArrayList();
    private ObservableList<Student> modelStudenti = FXCollections.observableArrayList();

    private StructuraAnUniversitar strAnUnniv = StructuraAnUniversitar.getInstance();


    @FXML
    TableView<Nota> tableView;

    @FXML
    TableView<Student> tableViewSt;

    @FXML
    TableColumn<Student, Long> tableColumnId;

    @FXML
    TableColumn<Student, String> tableColumnNume;

    @FXML
    TableColumn<Student, String> tableColumnPrenume;

    @FXML
    TableColumn<Student, Integer> tableColumnGrupa;

    @FXML
    TableColumn<Student, String> tableColumnEmail;

    @FXML
    TableColumn<Student, String> tableColumnCadruDidacticIndrumatorLab;

    @FXML
    TableColumn<Student,String> tableColumnMedie;

    @FXML
    TableColumn<Nota, Pair<Long, Long>> tableColumnID;

    @FXML
    TableColumn<Nota, Double> tableColumnNota;

    @FXML
    TableColumn<Nota, LocalDate> tableColumnData;

    @FXML
    TableColumn<Nota, String> tableColumnProfesor;

    @FXML
    TableColumn<Nota, String> tableColumnFeedback;

    @FXML
    private ComboBox<Tema> comboBoxTema;

    @FXML
    private TextField textFieldNume;

    @FXML
    private TextField textFieldNota;

    @FXML
    private TextArea textAreaFeedback;

    @FXML
    private CheckBox intarziereProf;

    @FXML
    private TextField saptPredare;

    @FXML
    private CheckBox checkBoxMotivare;

    @FXML
    private TextField inceputField;

    @FXML
    private TextField sfarsitField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextArea raportTextArea;

    @FXML
    private TextField textFieldProf;


    /**
     *Functie care seteaza obiecte pentru ControllerNote
     * @param notaService - service pentru Nota
     * @param studentService - service pentru Student
     * @param temaService - service pentru Tema
     * @param anUniv - structura anului universitar
     */
    public void setServiceNota(ServiceNota notaService,
                               ServiceStudent studentService,
                               ServiceTema temaService,
                               StructuraAnUniversitar anUniv) {
        this.notaService = notaService;
        this.studentService = studentService;
        this.temaService = temaService;
        this.strAnUnniv = anUniv;
        notaService.addObserver(this);
        studentService.addObserver(this);
        tableView.toFront();
        initModel();
    }

    /**
     * Functie de initializare a tabelelor
     */
    @FXML
    private void initialize() {
        tableColumnID.setCellValueFactory(new PropertyValueFactory<Nota, Pair<Long, Long>>("Id"));
        tableColumnNota.setCellValueFactory(new PropertyValueFactory<Nota, Double>("Nota"));
        tableColumnData.setCellValueFactory(new PropertyValueFactory<Nota, LocalDate>("Data"));
        tableColumnProfesor.setCellValueFactory(new PropertyValueFactory<Nota, String>("Profesor"));
        tableColumnFeedback.setCellValueFactory(new PropertyValueFactory<Nota, String>("Feedback"));
        tableView.setItems(modelNote);

        tableColumnId.setCellValueFactory(new PropertyValueFactory<Student, Long>("Id"));
        tableColumnNume.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        tableColumnPrenume.setCellValueFactory(new PropertyValueFactory<Student, String>("Prenume"));
        tableColumnGrupa.setCellValueFactory(new PropertyValueFactory<Student, Integer>("Grupa"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<Student, String>("Email"));
        tableColumnCadruDidacticIndrumatorLab.setCellValueFactory(new PropertyValueFactory<Student, String>("CadruDidacticIndrumatorLab"));
        tableColumnMedie.setCellValueFactory(studentCellDataFeatures -> {
            Student student = studentCellDataFeatures.getValue();
            double averageGrade = notaService.raport1(student.getId());
            return new ReadOnlyStringWrapper(String.valueOf(averageGrade));
        });

        tableViewSt.setItems(modelStudenti);
    }


    /**
     * Functie pentru initializarea modelului pentru note si a combobox-ului pentru teme
     */
    private void initModel() {
        Iterable<Nota> note = notaService.getAllN();
        List<Nota> noteList = StreamSupport.stream(note.spliterator(), false)
                .collect(Collectors.toList());
        modelNote.setAll(noteList);

        Iterable<Tema> teme = temaService.findAll();
        List<Tema> temaList = StreamSupport.stream(teme.spliterator(), false)
                .collect(Collectors.toList());
        ObservableList<Tema> temeOL = FXCollections.observableArrayList(temaList);
        comboBoxTema.setItems(temeOL);

        for (Tema t : teme)
            if (t.getDeadlineWeek() == strAnUnniv.getCurrentWeek())
                comboBoxTema.getSelectionModel().select(t);


    }

    /**
     * Functie pentru initializarea modelului pentru studenti
     */
    private void initModelSt() {
        Iterable<Student> studenti = studentService.getAllSt();
        List<Student> studentiList = StreamSupport.stream(studenti.spliterator(), false)
                .collect(Collectors.toList());

        modelStudenti.setAll(studentiList);
        textFieldNume.setText("");
    }

    /**
     * Override la functia update
     */
    @Override
    public void update() {

        initModel();
        initModelSt();
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
     * Functie care se ocupa de checkBox-ul pentru motivare
     */
    @FXML
    private void handleCheckBoxMotivare() {
        if (checkBoxMotivare.isSelected()) {
            inceputField.setEditable(true);
            sfarsitField.setEditable(true);
            textAreaFeedback.setText("");
        }
        if (!checkBoxMotivare.isSelected()) {
            inceputField.setText("");
            sfarsitField.setText("");
            inceputField.setEditable(false);
            sfarsitField.setEditable(false);
            textAreaFeedback.setText("");
        }
    }

    /**
     * Functie care se ocupa de checkBox-ul pentru intarziere profesor
     */
    @FXML
    private void handleCheckBox() {

        if (intarziereProf.isSelected()) {
            saptPredare.setEditable(true);
            datePicker.setDisable(false);
            textAreaFeedback.setText("");
        } else {
            saptPredare.setText("");
            saptPredare.setEditable(false);
            datePicker.getEditor().setText("");
            datePicker.setDisable(true);
            textAreaFeedback.setText("");

        }
    }

    /**
     * Functie care se ocupa de combobox
     */
    @FXML
    private void temacomboChange()
    {
        textAreaFeedback.setText("");
        textFieldNota.setText("");
    }

    /**
     *Functie care se ocupa de manevrarea butonului 'Adauga Nota'
     * @param actionEvent - obiect actionEvent
     */
    @FXML
    private void onHandleAdaugaNota(ActionEvent actionEvent) {
        showPreview();
    }

    /**
     * Functie care se ocupa de adaugarea unei note.
     * In cazul in care toate field-urile sunt completate corect, se dechide o noua
     * fereastra in care sunt afisate detaliile notei, inclusiv penalizarile
     */
    private void showPreview() {
        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(getClass().getResource("/views/previewNota.fxml"));

        AnchorPane root = null;
        try {
            root = loader2.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Preview Nota");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);

        Student s = tableViewSt.getSelectionModel().getSelectedItem();
        if (s == null) {
            MesajAlerta.afiseazaMesajEroare(null, "Va rog alegeti un student");
            textFieldNume.setText("");
            return;
        }
        int nrsaptIntarziere = 0;

        if (intarziereProf.isSelected() && saptPredare.getText().equals(""))
            nrsaptIntarziere = 0;
        else if (intarziereProf.isSelected())
            nrsaptIntarziere = Integer.parseInt(saptPredare.getText());

        if (textFieldProf.getText().isEmpty()) {
            MesajAlerta.afiseazaMesajEroare(null, "Trebuie sa introduci un profesor");
            return;
        }

        Tema t = comboBoxTema.getSelectionModel().getSelectedItem();

        double depunctare=depunctareGUI();

        if (!isNumeric(textFieldNota.getText())) {
            MesajAlerta.afiseazaMesajEroare(null, "Nota trebuie sa fie un numar!");
            textFieldNota.setText("");
            return;
        }

        LocalDate data = LocalDate.now();
        if (intarziereProf.isSelected()) {
            data = datePicker.getValue();
            int x = t.getDeadlineWeek() + nrsaptIntarziere;
            if (!datePicker.getEditor().getText().equals("") && strAnUnniv.getWeekNumber(data) != x) {
                MesajAlerta.afiseazaMesajEroare(null, "Data aleasa nu e in saptamana " + x);
                return;
            } else if (datePicker.getEditor().getText().equals("")) {
                MesajAlerta.afiseazaMesajEroare(null, "Trebuie sa alegeti o data din saptamana  " + x);
                return;
            }

        } else if (!intarziereProf.isSelected()) data = LocalDate.now();

        boolean scutire = false;
        if (checkBoxMotivare.isSelected() && (inceputField.getText().equals("") || sfarsitField.getText().equals("")))
        {
            MesajAlerta.afiseazaMesajEroare(null, "Va rog sa introduceti saptamanile in care studentul are scutire");
        return;
        }
        else if (checkBoxMotivare.isSelected())
            scutire = true;

        Nota nn = new Nota(s.getId(), t.getId(), Double.parseDouble(textFieldNota.getText()), data, textFieldProf.getText(), textAreaFeedback.getText());
        nn.setId(new Pair(s.getId(), t.getId()));
        double nv = nn.getNota();

        if (depunctare == 0 || depunctare == 1 || depunctare == 2)
        nv = nv - depunctare;
        else nv=1.00;
        nn.setNota(nv);

        PreviewController previewController = loader2.getController();
        previewController.setPreviewController(notaService, dialogStage, nn, s, t, depunctare, scutire, inceputField.getText(), sfarsitField.getText());

        dialogStage.show();

        textFieldNota.setText("");

    }

    /**
     * Functie care calculeaza depunctarea unei note
     * @return - numarul de puncte cu care este depunctata nota
     */
    private double depunctareGUI()
    {
        Tema t=comboBoxTema.getSelectionModel().getSelectedItem();
        int nrsaptIntarziere = 0;
        if (intarziereProf.isSelected() && saptPredare.getText().equals(""))
            nrsaptIntarziere = 0;
        else if (intarziereProf.isSelected())
            nrsaptIntarziere = Integer.parseInt(saptPredare.getText());

        double depunctare=0;
        if (!checkBoxMotivare.isSelected() && !intarziereProf.isSelected())
            depunctare = notaService.intarziere(t.getId());
        else if (checkBoxMotivare.isSelected() && !intarziereProf.isSelected())
            depunctare = notaService.intarziereMedical(t.getId(), Integer.parseInt(inceputField.getText()), Integer.parseInt(sfarsitField.getText()), false, 0);
        else if (intarziereProf.isSelected() && checkBoxMotivare.isSelected())
            depunctare = notaService.intarziereMedical(t.getId(), Integer.parseInt(inceputField.getText()), Integer.parseInt(sfarsitField.getText()), true, nrsaptIntarziere);
        else if (intarziereProf.isSelected() && !checkBoxMotivare.isSelected())
            depunctare = notaService.intarziereProf(t.getId(),nrsaptIntarziere);

        return depunctare;
    }

    /**
     * Functie care seteaza automat un feedback in functie de depunctarile calculate
     */
    @FXML
    private void automaticFeedback()
    {
        double depunctare=depunctareGUI();

        if (depunctare == 1 || depunctare == 2) textAreaFeedback.setText("Feedback: Nota a fost diminuata cu "+depunctare+" puncte din cauza intarzierilor.\n");
        else if (depunctare > 2) textAreaFeedback.setText("Feedback: Nota este 1.00  din cauza intarzierilor.\n");
        else if (depunctare == 0) textAreaFeedback.setText("Feedback: Tema a fost predata la timp.\n");

    }

    /**
     * Functie care se ocupa de butonul pentru raportul 1.
     */
    @FXML
    private void onHandleRaport1() {

        List<Student> studenti = studentService.getStList();
        List<String> rez = studenti.stream()
                .map(s -> "Id: "+s.getId()+" media = "+notaService.raport1(s.getId()))
                .collect(Collectors.toList());
        raportTextArea.appendText("\nRaport1:" + rez);
    }

    /**
     * Functie care se ocupa de butonul pentru raportul 2.
     */
    @FXML
    private void onHandleRaport2() {

        List<Tema> teme = notaService.temaCeaMaiMicaMedie();
        List<String> rez = teme.stream()
                 .map(tema -> tema+" Media: " + notaService.medieTema(tema.getId()))
                 .collect(Collectors.toList());

        raportTextArea.appendText("\nRaport 2:" + rez);
    }

    /**
     * Functie care se ocupa de butonul pentru raportul 3.
     */
    @FXML
    private void onHandleRaport3() {
        raportTextArea.appendText("\nRaport3:" + notaService.raport3Studenti());
        ObservableList<Student> obsL = FXCollections.observableArrayList(notaService.raport3Studenti());
        modelStudenti.setAll(obsL);
        tableViewSt.toFront();
    }

    /**
     * Functie care se ocupa de butonul pentru raportul 4.
     */
    @FXML
    private void onHandleRaport4() {
        raportTextArea.appendText("\nRaport 4" + notaService.raport4Studenti());
        ObservableList<Student> obsL = FXCollections.observableArrayList(notaService.raport4Studenti());
        modelStudenti.setAll(obsL);
        tableViewSt.toFront();
    }

    /**
     * Functie care se ocupa de butonul pentru alegerea unui student.
     * @param event - obiect ActionEvent
     * @return - studentul selectat
     */
    @FXML
    private Student onHandleAlegeStudent(ActionEvent event) {
        Student s = tableViewSt.getSelectionModel().getSelectedItem();
        if (s != null) {
            textFieldNume.setText(s.getNume() + " " + s.getPrenume());
        } else MesajAlerta.afiseazaMesajEroare(null, "Va rog sa selectati un student!");
        if (s != null) tableView.toFront();
        return s;
    }

    /**
     * Functie care se ocupa de filtrarea studentilor dupa nume
     * la modificarea texului din textFieldNume
     */
    @FXML
    public void onHandleStudenti2() {

        if (!textFieldNume.getText().equals("")) {
            Predicate<Student> filtrareNume = n -> n.getNume().startsWith(textFieldNume.getText());
            modelStudenti.setAll(studentService.getStList().stream()
                    .filter(filtrareNume)
                    .collect(Collectors.toList()));
            tableViewSt.toFront();
        }

    }

    /**
     * Functie care deschide o noua fereastra la actionarea butonului 'Gestionare Studenti'
     */
    public void onHandleStudenti() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/StudentView.fxml"));

        AnchorPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Gestionare Studenti");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);

        ControllerStudent stController = loader.getController();
        stController.setServiceStudent(studentService);

        dialogStage.show();
    }


    /**
     * Functie care aduce in prim-plan tabelul Studenti la
     * actionarea butonului 'Tabel Studenti'
     */
    @FXML
    private void studentiTabel() {
        initModelSt();
        tableViewSt.toFront();
    }

    /**
     * Functie care aduce in prim-plan tabelul Note la
     * actionarea butonului 'Tabel Note'
     */
    @FXML
    private void noteTabel() {
        initModel();
        tableView.toFront();
    }
}
