package de.company.accountingfx;

import de.company.accountingfx.model.AccountingRecord;
import de.company.accountingfx.view.AccountingOverviewController;
import de.company.accountingfx.view.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    /**
     * The data as an observable list of AccountingRecords.
     */
    private ObservableList<AccountingRecord> accountingRecordData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
        accountingRecordData.add(new AccountingRecord(1, 10000.00, 700,
                123, LocalDate.of(2019, 10, 2), 1800, "PKW"));
        accountingRecordData.add(new AccountingRecord(2, 30.00, 6330,
                456, LocalDate.of(2019, 10, 4), 1600, "Reinigungsmittel"));
        accountingRecordData.add(new AccountingRecord(3, 45.00, 6815,
                789, LocalDate.of(2019, 10, 6), 1800, "Ordner"));
        accountingRecordData.add(new AccountingRecord(4, 160.00, 6815,
                135, LocalDate.of(2019, 10, 9), 1800, "Maus & Tastatur"));
        accountingRecordData.add(new AccountingRecord(5, 45.00, 6815,
                999, LocalDate.of(2019, 10, 12), 1600, "Kasten Wasser"));
        accountingRecordData.add(new AccountingRecord(1, 10000.00, 700,
                123, LocalDate.of(2019, 10, 2), 1800, "PKW"));
        accountingRecordData.add(new AccountingRecord(2, 30.00, 6330,
                456, LocalDate.of(2019, 10, 4), 1600, "Reinigungsmittel"));
        accountingRecordData.add(new AccountingRecord(3, 45.00, 6815,
                789, LocalDate.of(2019, 10, 6), 1800, "Ordner"));
        accountingRecordData.add(new AccountingRecord(4, 160.00, 6815,
                135, LocalDate.of(2019, 10, 9), 1800, "Maus & Tastatur"));
        accountingRecordData.add(new AccountingRecord(5, 45.00, 6815,
                999, LocalDate.of(2019, 10, 12), 1600, "Kasten Wasser"));
        accountingRecordData.add(new AccountingRecord(1, 10000.00, 700,
                123, LocalDate.of(2019, 10, 2), 1800, "PKW"));
        accountingRecordData.add(new AccountingRecord(2, 30.00, 6330,
                456, LocalDate.of(2019, 10, 4), 1600, "Reinigungsmittel"));
        accountingRecordData.add(new AccountingRecord(3, 45.00, 6815,
                789, LocalDate.of(2019, 10, 6), 1800, "Ordner"));
        accountingRecordData.add(new AccountingRecord(4, 160.00, 6815,
                135, LocalDate.of(2019, 10, 9), 1800, "Maus & Tastatur"));
        accountingRecordData.add(new AccountingRecord(5, 45.00, 6815,
                999, LocalDate.of(2019, 10, 12), 1600, "Kasten Wasser"));
    }

    /**
     * Returns the data as an observable list of AccountingRecords.
     * @return
     */
    public ObservableList<AccountingRecord> getAccountingRecordData() {
        return accountingRecordData;
    }

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
