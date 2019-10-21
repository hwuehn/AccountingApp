package de.company.accountingfx.view;

import de.company.accountingfx.MainApp;
import de.company.accountingfx.model.Account;
import de.company.accountingfx.model.AccListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;

public class AccountAdministrationController {

    @FXML
    private TextField accIdTextField;
    @FXML
    private TextField accTagTextField;
    @FXML
    private Button accPushBtn;
    @FXML
    private ListView<Account> listView = new ListView(accounts);;

    private Stage dialogStage;
    private boolean pushClicked = false;
    private BookingViewController bookingViewController = new BookingViewController();

    // Add some accounts
    static private ObservableList<Account> accounts = FXCollections.observableArrayList(
            new Account("700","Fuhrpark"),
            new Account("1600","Kasse"),
            new Account("1800","Bank"),
            new Account("6330","Reinigung"),
            new Account("6815","Buerobedarf")
    );

    public static ObservableList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ObservableList<Account> accounts) {
        this.accounts = accounts;
    }

    // Reference to the main application.
    private MainApp mainApp;

        /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    public void initialize() {
        createCellFactory();
        listView.setItems(accounts);
    }

    /**
    * Sets the stage of this dialog.
    *
    * @param dialogStage
    */

    public void setDialogStage(Stage dialogStage) {
            this.dialogStage = dialogStage;
            // Set the dialog icon.
            //this.dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isPushClicked() {
        return pushClicked;
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Called when the user clicks push.
     */
    @FXML
    void handlePushBtn(ActionEvent event) {
        String iD = accIdTextField.getText();
        String text = accTagTextField.getText();
        accounts.add(new Account(iD, text));


    }

    public void createCellFactory() {
        listView.setCellFactory(param -> new ListCell<Account>() {
            @Override
            protected void updateItem(Account item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getAccID() == null) {
                    setText(null);
                } else {
                    setText(item.getAccID() + " " + item.getAccTag());
                }
            }
        });
    }

    public void saveAccListToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(AccListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our list data.
            AccListWrapper wrapper = new AccListWrapper();
            wrapper.setAccounts(accounts);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file.
            returnPathFile("/save/accounts.xml");
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public String returnPathFile(String filename){
        String path=getClass().getResource(filename).getPath();
        path=path.substring(6);
        int p=path.lastIndexOf("/dist/");
        if (p>0){
            path=path.substring(0, p);
            path+="/src"+filename;
        }
        return path;
    }

    public void saveAccList() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            saveAccListToFile(file);
        }
    }

}
