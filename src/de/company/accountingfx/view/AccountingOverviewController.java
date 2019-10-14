package de.company.accountingfx.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import de.company.accountingfx.MainApp;
import de.company.accountingfx.model.AccountingRecord;
import de.company.accountingfx.util.DateUtil;

public class AccountingOverviewController {
    @FXML
    private TableView<AccountingRecord> accountingRecordTable;
    @FXML
    private TableColumn<AccountingRecord, String> iDColumn;
    @FXML
    private TableColumn<AccountingRecord, String> amountColumn;
    @FXML
    private TableColumn<AccountingRecord, String> debitAccColumn;
    @FXML
    private TableColumn<AccountingRecord, String> docNumColumn;
    @FXML
    private TableColumn<AccountingRecord, String> dateColumn;
    @FXML
    private TableColumn<AccountingRecord, String> creditAccColumn;
    @FXML
    private TableColumn<AccountingRecord, String> tagColumn;

    @FXML
    private Label debitAccIDLabel;
    @FXML
    private Label debitAccTagLabel;
    @FXML
    private Label creditAccIDLabel;
    @FXML
    private Label creditAccTagLabel;

    @FXML
    private TextField iDField;
    @FXML
    private TextField amountField;
    @FXML
    private ComboBox debitAccField;
    @FXML
    private TextField docNumField;
    @FXML
    private TextField dateField;
    @FXML
    private ComboBox creditAccField;
    @FXML
    private TextField tagField;

    private AccountingRecord accountingRecord;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public AccountingOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the accountingRecord table with the seven columns.
        iDColumn.setCellValueFactory(cellData -> cellData.getValue().iDProperty().asString());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asString());
        debitAccColumn.setCellValueFactory(cellData -> cellData.getValue().debitAccProperty().asString());
        docNumColumn.setCellValueFactory(cellData -> cellData.getValue().docNumProperty().asString());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty().asString());
        creditAccColumn.setCellValueFactory(cellData -> cellData.getValue().creditAccProperty().asString());
        tagColumn.setCellValueFactory(cellData -> cellData.getValue().tagsProperty());

        // Clear accoundRecord details.
        showAccountingRecordDetails(null);

        // Listen for selection changes and show the accoundRecord details when changed.
        accountingRecordTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showAccountingRecordDetails(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

     // Add observable list data to the table
       accountingRecordTable.setItems(mainApp.getAccountingRecordData());
    }

    /**
     * Fills all text fields to show details about the accountingRecord.
     * If the specified accountingRecord is null, all text fields are cleared.
     *
     * @param accountingRecord the person or null
     */
    private void showAccountingRecordDetails(AccountingRecord accountingRecord) {
        if (accountingRecord != null) {
            // Fill the labels with info from the accountingRecord object.
            //TODO create accTag
            debitAccIDLabel.setText(accountingRecord.getDebitAcc().toString());
            debitAccTagLabel.setText(accountingRecord.getDebitAcc().toString());
            creditAccIDLabel.setText(accountingRecord.getCreditAcc().toString());
            creditAccTagLabel.setText(accountingRecord.getCreditAcc().toString());

        } else {
            // Accountingrecord is null, remove all the text.
            creditAccIDLabel.setText("");
            debitAccTagLabel.setText("");
            debitAccIDLabel.setText("");
            creditAccTagLabel.setText("");

        }
    }

    /**
     * Called when the user selectss a accountingrecord. Showing details of the record.
     */
    @FXML
    private void handleSelectAccountingRecord() {
        AccountingRecord selectedRecord = accountingRecordTable.getSelectionModel().getSelectedItem();
        if (selectedRecord != null) {
            showAccountingRecordDetails(selectedRecord);
        }
    }

    /**
     * Called when the user clicks the sumbmit button. Inserts the userInput in the ne record..
     */
    @FXML
    public void setPerson(AccountingRecord accountingRecord) {
        this.accountingRecord = accountingRecord;
        //TODO
        amountField.setText(accountingRecord.getAmount().toString());
//        debitAccField.setText(accountingRecord.debitAccProperty());
//
//        firstNameField.setText(person.getFirstName());
//        lastNameField.setText(person.getLastName());
//        streetField.setText(person.getStreet());
//        postalCodeField.setText(Integer.toString(person.getPostalCode()));
//        cityField.setText(person.getCity());
//        birthdayField.setText(DateUtil.format(person.getBirthday()));
//        birthdayField.setPromptText("dd.mm.yyyy");
    }


}
