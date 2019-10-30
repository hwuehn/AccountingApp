package de.company.accountingfx;

import de.company.accountingfx.model.Account;
import de.company.accountingfx.model.util.RecordWrapper;
import de.company.accountingfx.model.Record;
import de.company.accountingfx.model.util.CounterId;
import de.company.accountingfx.control.AddAccountController;
import de.company.accountingfx.control.RecordTableController;
import de.company.accountingfx.control.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.prefs.Preferences;

public class MainApp extends Application {

    private static final String RECORDS_XML = "./resources/save/records.xml";
    private static final String ACCOUNTS_XML = ".resources/save/accounts.xml";
    private Stage primaryStage;
    private BorderPane rootLayout;

    private ObservableList<Record> recordData = FXCollections.observableArrayList();

    public MainApp() {
        // Add some accounts
        Account fuhrpark = new Account("700","Fuhrpark");
        Account kasse = new Account("1600","Kasse");
        Account bank = new Account("1800","Bank");
        Account reiningung = new Account("6330","Reinigung");
        Account buerobedarf = new Account("6815","Buerobedarf");

        accountList.addAll(fuhrpark,kasse,bank,reiningung,buerobedarf);

        // Add some sample data
        recordData.add(new Record(new CounterId(), 10000.00, fuhrpark,
                123, LocalDate.of(2019, 10, 2), bank, "PKW"));
        recordData.add(new Record(new CounterId(), 30.00, reiningung,
                456, LocalDate.of(2019, 10, 4), kasse, "Reinigungsmittel"));
        recordData.add(new Record(new CounterId(), 45.00, buerobedarf,
                789, LocalDate.of(2019, 10, 6), bank, "Ordner"));
        recordData.add(new Record(new CounterId(), 160.00, buerobedarf,
                135, LocalDate.of(2019, 10, 9), bank, "Maus & Tastatur"));
        recordData.add(new Record(new CounterId(), 45.00, buerobedarf,
                999, LocalDate.of(2019, 10, 12), kasse, "Kasten Wasser"));
    }

    private ObservableList<Account> accountList = FXCollections.observableArrayList();

    public ObservableList<Record> getRecordData() {
        return recordData;
    }

    public ObservableList<Account> getAccountList() {return accountList; }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AccountingApp");

        // Set the application icon.
        this.primaryStage.getIcons().add(new Image("file:resources/images/money.png"));

        initRootLayout();

        showAccountingOverview();
    }

    /**
     * Initializes the root layout and tries to load the last opened
     * person file.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened book file.
        File file = getRecordFilePath();
        if (file != null) {
            loadRecordDataFromFile(file);
        }
    }
        /**
         * Shows the person overview inside the root layout.
        */

        public void showAccountingOverview() {
            try {
                // Load accounting overview.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("view/RecordTable.fxml"));
                AnchorPane accountingOverview = loader.load();

                // Set person overview into the center of root layout.
                rootLayout.setCenter(accountingOverview);

                // Give the controller access to the main app.
                RecordTableController controller = loader.getController();
                controller.setMainApp(this);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    /**
     * Returns the record file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     *
     * @return
     */
    public File getRecordFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     *
     * @param file the file or null to remove the path
     */
    public void setRecordFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("AccountingApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("AccountingApp");
        }
    }

    /**
     * Loads person data from the specified file. The current person data will
     * be replaced.
     *
     * @param file
     */
    public void loadRecordDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(RecordWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            System.out.println("1");

            RecordWrapper wrapper = (RecordWrapper) um.unmarshal(file);

            System.out.println("2");

            recordData.clear();
            recordData.addAll(wrapper.getRecords());

            // Save the file path to the registry.
            setRecordFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());
            e.printStackTrace();
            alert.showAndWait();
        }
    }

    /**
     * Saves the current person data to the specified file.
     *
     * @param file
     */


    public void saveRecordDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(RecordWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // Wrapping our record data.
            RecordWrapper wrapper = new RecordWrapper();
            wrapper.setRecords(recordData);

            // Write to System.out
            m.marshal(wrapper, System.out);

            // Write to File
            m.marshal(wrapper, new File(RECORDS_XML));

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setRecordFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    /**
     * Opens a dialog to add the specified account. If the user
     * clicks Push, the changes are saved into the provided account object and true
     * is returned.
     *
     * @param account object to be created
     * @return true if the user clicked Push, false otherwise.
     */
    public boolean showAccountAddDialog(Account account) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AddAccount.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Account");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the account into the controller.
            AddAccountController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Set the dialog icon.
            dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isPushClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

        /**
         * Returns the main stage.
         * @return
         */
        public Stage getPrimaryStage() {
            return primaryStage;
        }

    public static void main(String[] args) {
        launch(args);
    }
}
