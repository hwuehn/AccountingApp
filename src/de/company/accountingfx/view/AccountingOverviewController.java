package de.company.accountingfx.view;

import de.company.accountingfx.model.Account;
import de.company.accountingfx.util.CounterId;
import de.company.accountingfx.util.DateUtil;
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
    private ComboBox<Account> creditAccField;
    @FXML
    private TextField tagField;

    @FXML Button submitButton;

    private AccountingRecord accountingRecord;
    private Account account;

    // Reference to the main application.
    private MainApp mainApp;

    @FXML
    private ComboBox<Account> debitAccField;

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

        accounts = getAccounts();
        debitAccField.setItems(FXCollections.observableArrayList(accounts));
        debitAccField.getSelectionModel().selectFirst();
        creditAccField.setItems(FXCollections.observableArrayList(accounts));
        creditAccField.getSelectionModel().selectFirst();
        // list of values showed in combo box drop down

        Callback<ListView<Account>, ListCell<Account>> cellFactory = createCellFactoryAcc();
        // Just set the button cell here:
        debitAccField.setButtonCell(cellFactory.call(null));
        debitAccField.setCellFactory(cellFactory);
        creditAccField.setButtonCell(cellFactory.call(null));
        creditAccField.setCellFactory(cellFactory);

        //selected value showed in combo box
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

        creditAccField.setConverter(new StringConverter<Account>() {
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
    }

    private Callback<ListView<Account>, ListCell<Account>> createCellFactoryAcc() {
        return new Callback<ListView<Account>, ListCell<Account>>() {
                @Override
                public ListCell<Account> call(ListView<Account> l) {
                    return new ListCell<Account>() {
                        @Override
                        protected void updateItem(Account item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item == null || empty) {
                                setGraphic(null);
                            } else {
                                setText(item.getAccID() + "    " + item.getAccTag());
                            }
                        }
                    };
                }
            };
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
    public void setAccountingRecord() {

        AccountingRecord accountingRecord = new AccountingRecord();

        if (isInputValid()) {

            accountingRecord.setAmount(Double.parseDouble(amountField.getText()));
            accountingRecord.setDebitAcc(debitAccField.getSelectionModel().getSelectedItem());
            accountingRecord.setDocNum(Integer.parseInt(docNumField.getText()));
            accountingRecord.setDate(DateUtil.parse(dateField.getText()));
            accountingRecord.setCreditAcc(creditAccField.getSelectionModel().getSelectedItem());
            accountingRecord.setTags(tagField.getText());

            System.out.println(accountingRecord.getiD());
            System.out.println(accountingRecord.getAmount());
            System.out.println(accountingRecord.getDebitAcc());
            System.out.println(accountingRecord.getDocNum());
            System.out.println(accountingRecord.getDate());
            System.out.println(accountingRecord.getCreditAcc());
            System.out.println(accountingRecord.getTags());

            mainApp.getAccountingRecordData().add(accountingRecord);
        }
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (amountField.getText() == null || amountField.getText().length() == 0) {
            errorMessage += "No valid amount!\n";
        } else {
            // try to parse the amount into an double.
            try {
                Double.parseDouble(amountField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid amount (must be an double)!\n";
            }
        }

        if (docNumField.getText() == null || docNumField.getText().length() == 0) {
            errorMessage += "No valid document number!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(docNumField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid document number (must be an integer)!\n";
            }
        }

        if (dateField.getText() == null || dateField.getText().length() == 0) {
            errorMessage += "No valid date!\n";
        } else {
            if (!DateUtil.validDate(dateField.getText())) {
                errorMessage += "No valid date. Use the format dd.mm.yyyy!\n";
            }
        }

        if (tagField.getText() == null || tagField.getText().length() == 0) {
            errorMessage += "No valid tag!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }


}
