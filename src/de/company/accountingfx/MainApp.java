package de.company.accountingfx;

import de.company.accountingfx.model.Account;
import de.company.accountingfx.model.AccountingRecord;
import de.company.accountingfx.util.IdCounter;
import de.company.accountingfx.view.AccountingOverviewController;
import de.company.accountingfx.view.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    /**
     * The data as an observable list of AccountingRecords.
     */
    private ObservableList<AccountingRecord> accountingRecordData = FXCollections.observableArrayList();

    public void setAccountList(ObservableList<Account> accountList) {
        this.accountList = accountList;
    }

    private ObservableList<Account> accountList = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        // Add some accounts
        Account fuhrpark = new Account("700","Fuhrpark");
        Account kasse = new Account("1600","Kasse");
        Account bank = new Account("1800","Bank");
        Account reiningung = new Account("6330","Reinigung");
        Account buerobedarf = new Account("6815","Buerobedarf");

        accountList.addAll(fuhrpark,kasse,bank,reiningung,buerobedarf);

        // Add some sample data
        accountingRecordData.add(new AccountingRecord(new IdCounter(), 10000.00, fuhrpark,
                123, LocalDate.of(2019, 10, 2), bank, "PKW"));
        accountingRecordData.add(new AccountingRecord(new IdCounter(), 30.00, reiningung,
                456, LocalDate.of(2019, 10, 4), kasse, "Reinigungsmittel"));
        accountingRecordData.add(new AccountingRecord(new IdCounter(), 45.00, buerobedarf,
                789, LocalDate.of(2019, 10, 6), bank, "Ordner"));
        accountingRecordData.add(new AccountingRecord(new IdCounter(), 160.00, buerobedarf,
                135, LocalDate.of(2019, 10, 9), bank, "Maus & Tastatur"));
        accountingRecordData.add(new AccountingRecord(new IdCounter(), 45.00, buerobedarf,
                999, LocalDate.of(2019, 10, 12), kasse, "Kasten Wasser"));
        accountingRecordData.add(new AccountingRecord(new IdCounter(), 10000.00, fuhrpark,
                123, LocalDate.of(2019, 10, 2), bank, "PKW"));
        accountingRecordData.add(new AccountingRecord(new IdCounter(), 30.00, reiningung,
                456, LocalDate.of(2019, 10, 4), kasse, "Reinigungsmittel"));
        accountingRecordData.add(new AccountingRecord(new IdCounter(), 45.00, buerobedarf,
                789, LocalDate.of(2019, 10, 6), bank, "Ordner"));
        accountingRecordData.add(new AccountingRecord(new IdCounter(), 160.00, buerobedarf,
                135, LocalDate.of(2019, 10, 9), bank, "Maus & Tastatur"));
        accountingRecordData.add(new AccountingRecord(new IdCounter(), 45.00, buerobedarf,
                999, LocalDate.of(2019, 10, 12), kasse, "Kasten Wasser"));
        accountingRecordData.add(new AccountingRecord(new IdCounter(), 10000.00, fuhrpark,
                123, LocalDate.of(2019, 10, 2), bank, "PKW"));
        accountingRecordData.add(new AccountingRecord(new IdCounter(), 30.00, reiningung,
                456, LocalDate.of(2019, 10, 4), kasse, "Reinigungsmittel"));
        accountingRecordData.add(new AccountingRecord(new IdCounter(), 45.00, buerobedarf,
                789, LocalDate.of(2019, 10, 6), bank, "Ordner"));
        accountingRecordData.add(new AccountingRecord(new IdCounter(), 160.00, buerobedarf,
                135, LocalDate.of(2019, 10, 9), bank, "Maus & Tastatur"));
        accountingRecordData.add(new AccountingRecord(new IdCounter(), 45.00, buerobedarf,
                999, LocalDate.of(2019, 10, 12), kasse, "Kasten Wasser"));
    }

    public ObservableList<AccountingRecord> addARecordToList(ObservableList<AccountingRecord> accountingRecordData, AccountingRecord accountingRecord) {
        accountingRecordData.add(accountingRecord);
        return accountingRecordData;
    }

    /**
     * Returns the data as an observable list of AccountingRecords and Accounts.
     * @return
     */
    public ObservableList<AccountingRecord> getAccountingRecordData() {
        return accountingRecordData;
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
            rootLayout = (BorderPane) loader.load();

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
    }
        /**
         * Shows the person overview inside the root layout.
        */

        public void showAccountingOverview() {
            try {
                // Load accounting overview.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("view/AccountingOverview.fxml"));
                AnchorPane accountingOverview = (AnchorPane) loader.load();

                // Set person overview into the center of root layout.
                rootLayout.setCenter(accountingOverview);

                // Give the controller access to the main app.
                AccountingOverviewController controller = loader.getController();
                controller.setMainApp(this);

            } catch (IOException e) {
                e.printStackTrace();
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
