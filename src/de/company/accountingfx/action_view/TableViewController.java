package de.company.accountingfx.action_view;

import com.google.common.eventbus.Subscribe;
import de.company.accountingfx.dispatcher.DialogMessage;
import de.company.accountingfx.dispatcher.Dispatcher;
import de.company.accountingfx.dispatcher.PersistMessage;
import de.company.accountingfx.dispatcher.RecordMessage;
import de.company.accountingfx.store.Account;
import de.company.accountingfx.store.AppDB;
import de.company.accountingfx.store.Record;
import de.company.accountingfx.store.util.DateUtil;
import de.company.accountingfx.store.util.IMainController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class TableViewController implements Initializable {

    @FXML
    private TableView<Record> accountingRecordTable;
    @FXML
    private TableColumn<Record, String> iDColumn;
    @FXML
    private TableColumn<Record, String> amountColumn;
    @FXML
    private TableColumn<Record, String> debitAccColumn;
    @FXML
    private TableColumn<Record, String> docNumColumn;
    @FXML
    private TableColumn<Record, String> dateColumn;
    @FXML
    private TableColumn<Record, String> creditAccColumn;
    @FXML
    private TableColumn<Record, String> tagColumn;
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
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    @FXML
    private IMainController mainController;

    @FXML
    private ComboBox<Account> debitAccField;

    public TableViewController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Dispatcher.subscribe(this);
        assert accountingRecordTable != null : "fx:id\"accountingRecordTable\" was not injected: check your FXML file 'TableView.fxml'.";

        accountingRecordTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Dispatcher.dispatch(new RecordMessage(RecordMessage.SELECT, newValue));
        });

        // Initialize the accountingRecord table with the seven columns.
        iDColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asString());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asString());
        debitAccColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Record, String> param) {
                return new SimpleStringProperty(param.getValue().getDebitAcc().getAccID());
            }
        });
        docNumColumn.setCellValueFactory(cellData -> cellData.getValue().docNumProperty().asString());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty().asString());
        creditAccColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Record, String> param) {
                return new SimpleStringProperty(param.getValue().getCreditAcc().getAccID());
            }
        });

        tagColumn.setCellValueFactory(cellData -> cellData.getValue().tagsProperty());

        // Clear accoundRecord details.
        showAccountingRecordDetails(null);

        // Listen for selection changes and show the accoundRecord details when changed.
        accountingRecordTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showAccountingRecordDetails(newValue));

        debitAccField.setItems(FXCollections.observableArrayList(AccountController.getAccounts()));
        debitAccField.getSelectionModel().selectFirst();
        creditAccField.setItems(FXCollections.observableArrayList(AccountController.getAccounts()));
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

    @Subscribe
    public void subscribeAppDb(AppDB appDB) {
        setAppState(appDB);
    }

    private void showAccountingRecordDetails(Record record) {

        //  Dispatcher.dispatch(new RecordMessage(RecordMessage.SHOW_DETAIL, null, record));

        if (record != null) {
            // Fill the labels with info from the record object.
            debitAccIDLabel.setText(record.getDebitAcc().getAccID());
            debitAccTagLabel.setText(record.getDebitAcc().getAccTag());
            debitAccAmountLabel.setText(record.getAmount().toString());
            creditAccIDLabel.setText(record.getCreditAcc().getAccID());
            creditAccTagLabel.setText(record.getCreditAcc().getAccTag());
            creditAccAmountLabel.setText(record.getAmount().toString());
        } else {
            // Accountingrecord is null, remove all the text.
            creditAccIDLabel.setText("");
            debitAccTagLabel.setText("");
            debitAccIDLabel.setText("");
            creditAccTagLabel.setText("");
        }
    }

    @FXML
    private void handleSelectRecord() {

        Record selectedRecord = accountingRecordTable.getSelectionModel().getSelectedItem();
        if (selectedRecord != null) {
            showAccountingRecordDetails(selectedRecord);
        }
    }

    public void setAppState(AppDB appState) {
        accountingRecordTable.setItems(appState.getRecords());
        enableFiltering();
    }

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

    @FXML
    public void setAccountingRecord() {

//        if (isInputValid()) {
//            appDB.getRecords().add(new Record(new CounterId(), Double.parseDouble(amountField.getText()),
//                    debitAccField.getSelectionModel().getSelectedItem(), Integer.parseInt(docNumField.getText()),
//                    DateUtil.parse(dateField.getText()), creditAccField.getSelectionModel().getSelectedItem(),
//                    tagField.getText()));
//        }
    }

    public void enableFiltering() {
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
//        FilteredList<Record> filteredData = new FilteredList<>(mainApp.getRecordData(), r -> true);
//        // 2. Set the filter Predicate whenever the filter changes.
//        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
//            filteredData.setPredicate(record -> {
//                // If filter text is empty, display all records.
//                if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//                // Compare all columns with filter text.
//                String lowerCaseFilter = newValue; //.toLowerCase();
//
//                if (record.getDebitAcc().toString().contains(lowerCaseFilter)) {
//                    return true; // Filter matches debitAcc.
//                } else if (record.getCreditAcc().toString().contains(lowerCaseFilter)) {
//                    return true; // Filter matches creditAcc.
////                } else if (record.getId().contains(lowerCaseFilter)) {
////                    return true; // Filter matches iD.
//                } else if (record.getAmount().toString().contains(lowerCaseFilter)) {
//                    return true; // Filter matches amount.
//                } else if (record.getDate().toString().contains(lowerCaseFilter)) {
//                    return true; // Filter matches date.
//                } else if (record.getDocNum().toString().contains(lowerCaseFilter)) {
//                    return true; // Filter matches docNum.
//                } else return record.getTags().contains(lowerCaseFilter); // Filter matches tags.
//                // Does not match.
//            });
//        });
//
//        // 3. Wrap the FilteredList in a SortedList.
//        SortedList<Record> sortedData = new SortedList<>(filteredData);
//        // 4. Bind the SortedList comperator to the TableView comperator.
//        sortedData.comparatorProperty().bind(accountingRecordTable.comparatorProperty());
//        // 5. Add sorted (and filtered) data to the table.
//        accountingRecordTable.setItems(sortedData);
    }

    public void setMainController(IMainController controller) {
        this.mainController = controller;
    }

    @FXML
    private void handleNew() {
        Dispatcher.dispatch(new PersistMessage(PersistMessage.NEW_RECORDTABLE));
    }

    @FXML
    private void handleOpen() {
        Dispatcher.dispatch(new DialogMessage(DialogMessage.LOAD_DIALOG, mainController.getStage()));
    }

    @FXML
    private void handleSave() {
        Dispatcher.dispatch(new PersistMessage(PersistMessage.SAVE_RECORDTABLE));
    }

    @FXML
    private void handleSaveAs() {
        Dispatcher.dispatch(new DialogMessage(DialogMessage.SAVEAS_DIALOG, mainController.getStage()));
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AccountingApp");
        alert.setHeaderText("About");
        alert.setContentText("Author: Henning Wuehn");
        alert.showAndWait();
    }

    @FXML
    private void handleExit() {
        Dispatcher.dispatch(new PersistMessage(PersistMessage.EXIT));

    }

    @FXML
    private void handleEditAcc() {
//          Account account = new Account();
//           mainApp.showAccountAddDialog(account);
//           mainApp.getAccountList().add(account);
    }

    public Label getDebitAccIDLabel() {
        return debitAccIDLabel;
    }

    public Label getDebitAccTagLabel() {
        return debitAccTagLabel;
    }

    public Label getCreditAccIDLabel() {
        return creditAccIDLabel;
    }

    public Label getCreditAccTagLabel() {
        return creditAccTagLabel;
    }

    public Label getDebitAccAmountLabel() {
        return debitAccAmountLabel;
    }

    public Label getCreditAccAmountLabel() {
        return creditAccAmountLabel;
    }
}


