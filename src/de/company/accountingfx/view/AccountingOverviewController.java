package de.company.accountingfx.view;

import de.company.accountingfx.model.Account;
import de.company.accountingfx.util.CounterId;
import de.company.accountingfx.util.DateUtil;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
    @FXML
    private TextField filterTextField;
    @FXML
    private Button filterBtn;
    // Reference to the main application.
    private MainApp mainApp;
    @FXML
    private ComboBox<Account> debitAccField;

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
        debitAccField.setItems(FXCollections.observableArrayList(AddAccountDialogController.getAccounts()));
        debitAccField.getSelectionModel().selectFirst();
        creditAccField.setItems(FXCollections.observableArrayList(AddAccountDialogController.getAccounts()));
        creditAccField.getSelectionModel().selectFirst();
        Callback<ListView<Account>, ListCell<Account>> cellFactory = createCellFactoryAcc();
        debitAccField.setButtonCell(cellFactory.call(null));
        debitAccField.setCellFactory(cellFactory);
        creditAccField.setButtonCell(cellFactory.call(null));
        creditAccField.setCellFactory(cellFactory);
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
        enableFiltering();
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

        if (isInputValid()) {
            mainApp.getAccountingRecordData().add(new AccountingRecord(Double.parseDouble(amountField.getText()),
                    debitAccField.getSelectionModel().getSelectedItem(), Integer.parseInt(docNumField.getText()),
                    DateUtil.parse(dateField.getText()), creditAccField.getSelectionModel().getSelectedItem(),
                    tagField.getText()));
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

    public void enableFiltering() {
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<AccountingRecord> filteredData = new FilteredList<>(mainApp.getAccountingRecordData(), r -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(record -> {
                // If filter text is empty, display all records.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare all columns with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (record.getDebitAcc().toString().contains(lowerCaseFilter)) {
                    return true; // Filter matches debitAcc.
                } else if (record.getCreditAcc().toString().contains(lowerCaseFilter)) {
                    return true; // Filter matches creditAcc.
                } else if (record.getiD().toString().contains(lowerCaseFilter)) {
                    return true; // Filter matches iD.
                } else if (record.getAmount().toString().contains(lowerCaseFilter)) {
                    return true; // Filter matches amount.
                } else if (record.getDate().toString().contains(lowerCaseFilter)) {
                    return true; // Filter matches date.
                } else if (record.getDocNum().toString().contains(lowerCaseFilter)) {
                    return true; // Filter matches docNum.
                } else if (record.getTags().contains(lowerCaseFilter)) {
                    return true; // Filter matches tags.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<AccountingRecord> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comperator to the TableView comperator.
        sortedData.comparatorProperty().bind(accountingRecordTable.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        accountingRecordTable.setItems(sortedData);
    }
}
