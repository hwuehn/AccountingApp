package de.company.accountingfx.view;

import de.company.accountingfx.MainApp;
import de.company.accountingfx.model.Account;
import de.company.accountingfx.model.AccountingRecord;
import de.company.accountingfx.model.AccountingRecordListWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 *
 * @author Henning Wuehn
 */

public class RootLayoutController {

    // Reference to the main application
    private MainApp mainApp;

    public RootLayoutController() {

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
     * Creates an empty accounting book.
     */
    @FXML
    private void handleNew() {
        mainApp.getAccountingRecordData().clear();
        mainApp.setRecordFilePath(null);
    }

    /**
     * Opens a FileChooser to let the user select an accounting book to load.
     */
    @FXML
    private void handleOpen() throws JAXBException, FileNotFoundException {

        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadRecordDataFromFile(file);

        }
    }

    /**
     * Saves the file to the book file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSave() {
        File recordFile = mainApp.getRecordFilePath();
        if (recordFile != null) {
            mainApp.saveRecordDataToFile(recordFile);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {
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
            mainApp.saveRecordDataToFile(file);
        }
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AccountingApp");
        alert.setHeaderText("About");
        alert.setContentText("Author: Henning Wuehn");


        alert.showAndWait();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }

    @FXML
    private void handleEditAcc() {
        Account account = new Account();
        mainApp.showAccountAddDialog(account);
        mainApp.getAccountList().add(account);
    }
}


