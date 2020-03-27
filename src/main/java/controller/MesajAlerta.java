package controller;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MesajAlerta {
    /**
     * Functie care afiseaza un mesaj de informare
     * @param proprietar - obiect Stage
     * @param tip - obiect Alert.AlertType
     * @param antet - antetul mesajului
     * @param continut - continurul mesajului
     */
    public static void afiseazaMesaj(Stage proprietar, Alert.AlertType tip,String antet,String continut)
    {
        Alert mesaj=new Alert(tip);
        mesaj.setHeaderText(antet);
        mesaj.setContentText(continut);
        mesaj.initOwner(proprietar);
        mesaj.showAndWait();
    }

    /**
     * Functie care afiseaza un mesaj de eroare
     * @param proprietar - obiect Stage
     * @param continut - continutul mesajului
     */
    public static void afiseazaMesajEroare(Stage proprietar, String continut)
    {
        Alert mesaj = new Alert(Alert.AlertType.ERROR);
        mesaj.initOwner(proprietar);
        mesaj.setTitle("Mesaj eroare");
        mesaj.setContentText(continut);
        mesaj.showAndWait();
    }
}
