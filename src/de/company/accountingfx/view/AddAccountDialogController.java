package de.company.accountingfx.view;

import de.company.accountingfx.MainApp;
import de.company.accountingfx.model.Account;
import javafx.beans.InvalidationListener;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.*;

public class AddAccountDialogController {

    @FXML
    private TextField accReferenceTextField;

    @FXML
    private TextField accIdTextField;

    @FXML
    private TextField accTagTextField;

    @FXML
    private Button accPushBtn;

    private Stage dialogStage;
    private Account account;
    private boolean pushClicked = false;

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

    @FXML
    private ListView<Account> listView = new ListView(accounts);;

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


        pushClicked = true;

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


}
