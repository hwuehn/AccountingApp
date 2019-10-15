package de.company.accountingfx.view;

import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import de.company.accountingfx.MainApp;
import de.company.accountingfx.model.AccountingRecord;
import de.company.accountingfx.util.DateUtil;
import javafx.util.Callback;

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
    private Label debitAccAmountLabel;
    @FXML
    private Label creditAccAmountLabel;

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

        //use for selecting details
//        debitAccColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AccountingRecord, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<AccountingRecord, String> param) {
//                return new SimpleStringProperty(param.getValue().getDebitAcc().getAccTag());
//            }
//        });

        debitAccColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AccountingRecord, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AccountingRecord, String> param) {
                return new SimpleStringProperty(param.getValue().getCreditAcc().getAccID());
            }
        });


        docNumColumn.setCellValueFactory(cellData -> cellData.getValue().docNumProperty().asString());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty().asString());

        creditAccColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AccountingRecord, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AccountingRecord, String> param) {
                return new SimpleStringProperty(param.getValue().getCreditAcc().getAccID());
            }
        });

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

     // Add observable List data to the table
        
    }

    /**
     * Fills all text fields to show details about the accountingRecord.
     * If the specified accountingRecord is null, all text fields are cleared.
     *
     * @param accountingRecord or null
     */
    private void showAccountingRecordDetails(AccountingRecord accountingRecord) {
        if (accountingRecord != null) {
            // Fill the labels with info from the accountingRecord object.

            debitAccIDLabel.setText(accountingRecord.getDebitAcc().getAccID());
            debitAccTagLabel.setText(accountingRecord.getDebitAcc().getAccTag());
            debitAccAmountLabel.setText(accountingRecord.getAmount().toString());
            creditAccIDLabel.setText(accountingRecord.getCreditAcc().getAccID());
            creditAccTagLabel.setText(accountingRecord.getCreditAcc().getAccTag());
            creditAccAmountLabel.setText(accountingRecord.getAmount().toString());

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
