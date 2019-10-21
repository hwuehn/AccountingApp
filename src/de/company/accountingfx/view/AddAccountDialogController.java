package de.company.accountingfx.view;

import de.company.accountingfx.MainApp;
import de.company.accountingfx.model.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AddAccountDialogController {

    @FXML
    private ListView<Account> accListView;

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
    static Account fuhrpark = new Account("700","Fuhrpark");
    static Account kasse = new Account("1600","Kasse");
    static Account bank = new Account("1800","Bank");
    static Account reiningung = new Account("6330","Reinigung");
    static Account buerobedarf = new Account("6815","Buerobedarf");

    public static ObservableList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ObservableList<Account> accounts) {
        this.accounts = accounts;
    }

    static private ObservableList<Account> accounts = FXCollections.observableArrayList(fuhrpark,kasse,bank,reiningung,buerobedarf);

    // Reference to the main application.
    private MainApp mainApp;

    public AddAccountDialogController() {

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    public void initialize() {
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

        // Add observable list data to the view
        accListView = new ListView<Account>(accounts);
        accListView.setItems(accounts);
    }

    /**
     * Called when the user clicks push.
     */
    @FXML
    void handlePushBtn(ActionEvent event) {

        Account account = new Account();
        account.setAccID(accIdTextField.getText());
        account.setAccTag(accTagTextField.getText());

        accounts.add(account);

        pushClicked = true;

    }

}
