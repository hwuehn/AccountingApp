package de.company.accountingfx;

import com.google.common.eventbus.Subscribe;
import de.company.accountingfx.action.AddAccountController;
import de.company.accountingfx.action.RecordTableController;
import de.company.accountingfx.action.RootLayoutController;
import de.company.accountingfx.dispatcher.DataMessage;
import de.company.accountingfx.dispatcher.Dispatcher;
import de.company.accountingfx.dispatcher.PersistMessage;
import de.company.accountingfx.store.Account;
import de.company.accountingfx.store.AppDB;
import de.company.accountingfx.store.util.IMainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application implements IMainController {

    private Stage stage;
    private BorderPane rootLayout;

    public MainApp() {
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        this.stage.setTitle("AccountingApp");
        this.stage.getIcons().add(new Image("file:resources/images/money.png"));

        Dispatcher.subscribe(this);

        initRootLayout();



        showAccountingOverview();

        Dispatcher.dispatch(new PersistMessage(PersistMessage.LOAD_RECORDTABLE));
    }

    @Subscribe
    public void subscribeAppDb(AppDB appDB) {
        stage.titleProperty().bind(appDB.titleProperty());
    }

    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainController(this);

            Dispatcher.dispatch(new DataMessage(DataMessage.REQUEST));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

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
                controller.setMainController(this);

                Dispatcher.dispatch(new DataMessage(DataMessage.REQUEST));
                Dispatcher.dispatch(new PersistMessage(PersistMessage.LOAD_TESTDATA));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
            dialogStage.initOwner(stage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the account into the controller.
            AddAccountController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));

            dialogStage.showAndWait();

            return controller.isPushClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public Stage getStage() {
        return stage;
    }
}
