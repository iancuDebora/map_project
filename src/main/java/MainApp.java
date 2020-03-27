import config_services.ApplicationContext;
import controller.ControllerNote;
import controller.ControllerStudent;
import domains.StructuraAnUniversitar;
import domains.Student;
import domains.Tema;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import repository.NotaXMLFileRepository;
import repository.StudentXMLFileRepository;
import repository.TemaXMLFileRepository;
import repository.XMLFileRepository;
import services.ServiceNota;
import services.ServiceStudent;
import services.ServiceTema;
import validators.ValidareNota;
import validators.ValidareStudent;
import validators.ValidareTema;

import java.io.IOException;

public class MainApp extends Application {

    private ServiceStudent studentService;
    private ServiceNota notaService;
    private ServiceTema temaService;

    private StructuraAnUniversitar stAnUniv = StructuraAnUniversitar.getInstance();


    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ValidareStudent stVal = new ValidareStudent();
        XMLFileRepository<Long, Student> stXMLFileRepository = new StudentXMLFileRepository(stVal,
                ApplicationContext.getPROPERTIES().getProperty("catalog.studenti"));
        studentService = new ServiceStudent(stXMLFileRepository,stVal);

        ValidareTema tVal = new ValidareTema();
        TemaXMLFileRepository tXMLFILERepository = new TemaXMLFileRepository(tVal
                ,ApplicationContext.getPROPERTIES().getProperty("catalog.teme"));
        temaService = new ServiceTema(tXMLFILERepository,tVal);

        ValidareNota nVal = new ValidareNota();
        NotaXMLFileRepository nXMLFILERepository = new NotaXMLFileRepository(nVal,
                ApplicationContext.getPROPERTIES().getProperty("catalog.note"));

        notaService = new ServiceNota(nXMLFILERepository,nVal,stXMLFileRepository,tXMLFILERepository);

        initialize(primaryStage);
        primaryStage.setWidth(1000);
        primaryStage.show();

    }

    private void initialize(Stage primaryStage) throws IOException {
        FXMLLoader messageLoader = new FXMLLoader();
        messageLoader.setLocation(getClass().getResource("/views/Note.fxml"));
        AnchorPane noteLayout = messageLoader.load();
        primaryStage.setScene(new Scene(noteLayout));

        ControllerNote nController = messageLoader.getController();
        nController.setServiceNota(notaService,studentService,temaService,stAnUniv);
    }
}
