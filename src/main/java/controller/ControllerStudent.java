package controller;

import domains.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceStudent;
import utils.observer.Observable;
import utils.observer.Observer;
import validators.ValidareStudent;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ControllerStudent implements Observer {
    private ServiceStudent stServ;
    private ObservableList<Student> model= FXCollections.observableArrayList();


    @FXML
    TableView<Student> tableView;

    @FXML
    TableColumn<Student,Long> tableColumnId;

    @FXML
    TableColumn<Student,String> tableColumnNume;

    @FXML
    TableColumn<Student,String> tableColumnPrenume;

    @FXML
    TableColumn<Student,Integer> tableColumnGrupa;

    @FXML
    TableColumn<Student,String> tableColumnEmail;

    @FXML
    TableColumn<Student,String> tableColumnCadruDidacticIndrumatorLab;

    @FXML
    private TextField textFieldNume;


    /**
     * Functie care seteaza obiecte pentru ControllerStudent
     * @param serv - obiect ServiceStudent
     */
   public void setServiceStudent(ServiceStudent serv)
    {
        this.stServ = serv;
        stServ.addObserver(this);
        initModel();
    }

    /**
     * Functie care initializeaza tabelul pentru studenti
     */
    @FXML
    public void initialize()
    {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<Student,Long>("Id"));
        tableColumnNume.setCellValueFactory(new PropertyValueFactory<Student,String>("Nume"));
        tableColumnPrenume.setCellValueFactory(new PropertyValueFactory<Student,String>("Prenume"));
        tableColumnGrupa.setCellValueFactory(new PropertyValueFactory<Student,Integer>("Grupa"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<Student,String>("Email"));
        tableColumnCadruDidacticIndrumatorLab.setCellValueFactory(new PropertyValueFactory<Student,String>("CadruDidacticIndrumatorLab"));
        tableView.setItems(model);
        textFieldNume.textProperty().addListener(((observableValue, s, t1) -> onHandleFilterByName()));
    }

    /**
     * Functie care initializeaza modelul pentru studenti
     */
    private void initModel() {
        Iterable<Student> studenti=stServ.getAllSt();
        List<Student> studentiList= StreamSupport.stream(studenti.spliterator(), false)
                .collect(Collectors.toList());

        model.setAll(studentiList);
    }


    /**
     * Functie care sterge un student selectat
     * la actionarea butonului 'Sterge student'
     * @param actionEvent - obiect ActionEvent
     */
    public void handleDeleteMessage(ActionEvent actionEvent) {
       Student s = tableView.getSelectionModel().getSelectedItem();
        if (s != null) {
            Student deleted = stServ.deleteStudent(s.getId());
            if (deleted != null)
                MesajAlerta.afiseazaMesaj(null, Alert.AlertType.INFORMATION, "Sterge student", "Studentul a fost sters cu succes!");
        }
        else MesajAlerta.afiseazaMesajEroare(null, "Va rog sa selectati un student!");
    }


    /**
     * Functie care adauga un nou student
     * la actionarea butonului 'Adauga Student'.
     * @param ev - obiect ActionEvent
     */
    @FXML
    public void handleAddMessage(ActionEvent ev) {

        showEditDialog(null);
    }

    /**
     * Functie care deschide o noua fereastra pentru un student
     * @param s - student
     */
    public void showEditDialog(Student s) {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/editStudent1.fxml"));

            AnchorPane root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editare");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            EditareController editareViewController = loader.getController();
            editareViewController.setEditareController(stServ, dialogStage, s);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Functie care modifica un student selectat din lista
     * la actionarea butonului 'Modifica student'
     * @param ev - obiect ActionEvent
     */
    @FXML
    public void handleUpdateMessage(ActionEvent ev) {

       Student s= tableView.getSelectionModel().getSelectedItem();
        if (s != null) {
            showEditDialog(s);
        } else
            MesajAlerta.afiseazaMesajEroare(null, "Va rog sa selectati un student!");
    }

    /**
     * Functie care filtreaza lista studentilor dupa nume
     * la actionarea butonului 'Cauta dupa Nume'
     */
    @FXML
    public void onHandleFilterByName()
    {
        Predicate<Student> filtrareNume= n->n.getNume().startsWith(textFieldNume.getText());
             model.setAll(stServ.getStList().stream()
            .filter(filtrareNume)
            .collect(Collectors.toList()));
    }

    /**
     * Override la functia update
     */
    @Override
    public void update() {
        initModel();
    }


}
