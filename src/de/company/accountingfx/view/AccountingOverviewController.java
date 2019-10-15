package de.company.accountingfx.view;

import de.company.accountingfx.model.Account;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import de.company.accountingfx.MainApp;
import de.company.accountingfx.model.AccountingRecord;
import javafx.util.Callback;
import javafx.util.StringConverter;

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
    private TextField docNumField;
    @FXML
    private TextField dateField;
    @FXML
    private ComboBox creditAccField;
    @FXML
    private TextField tagField;

    private AccountingRecord accountingRecord;
    private Account account;

    // Reference to the main application.
    private MainApp mainApp;

    @FXML
    private ComboBox<Account> debitAccField;

//    ObservableList<String> names = FXCollections.observableArrayList("Bob","Klaus", "Tina");
//
//    public ObservableList<String> getNames() {
//        return names;
//    }
//    public void setNames(ObservableList<String> names) {
//        this.names = names;
//    }

    // Add some accounts
    Account fuhrpark = new Account("700","Fuhrpark");
    Account kasse = new Account("1600","Kasse");
    Account bank = new Account("1800","Bank");
    Account reiningung = new Account("6330","Reinigung");
    Account buerobedarf = new Account("6815","Buerobedarf");

    public ObservableList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ObservableList<Account> accounts) {
        this.accounts = accounts;
    }

    ObservableList<Account> accounts = FXCollections.observableArrayList(fuhrpark,kasse,bank,reiningung,buerobedarf);



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

        debitAccColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AccountingRecord, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AccountingRecord, String> param) {
                return new SimpleStringProperty(param.getValue().getDebitAcc().getAccID());
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

        //debitAccField.setItems(accounts);

        debitAccField.setCellFactory(new Callback<ListView<Account>,ListCell<Account>>(){
            @Override
            public ListCell<Account> call(ListView<Account> l){
                return new ListCell<Account>(){

                    @Override
                    protected void updateItem(Account item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getAccID()+"    "+item.getAccTag());
                        }
                    }
                } ;
            }
        });

        debitAccField.setConverter(new StringConverter<Account>() {
            @Override
            public String toString(Account account) {
                if (account== null){
                    return null;
                } else {
                    return account.getAccID();
                }
            }

            @Override
            public Account fromString(String id) {
                return null;
            }
        });

        accounts = getAccounts();
        debitAccField.setItems(FXCollections.observableArrayList(accounts));
    }

//    public void getAccountDataFields() {
//        debitAccField.getItems().clear();
//        debitAccField.setItems(mainApp.getAccountList());
//        //debitAccField.setCellFactory();
//    }


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
        //debitAccField.setItems(mainApp.getAccountList());
       // getAccountDataFields();





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
     * Called when the user clicks the submit button. Inserts the userInput in the ne record..
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
