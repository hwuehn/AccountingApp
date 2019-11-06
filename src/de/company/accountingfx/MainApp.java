package de.company.accountingfx;

import com.google.common.eventbus.Subscribe;
import de.company.accountingfx.action_view.TableViewController;
import de.company.accountingfx.dispatcher.DataMessage;
import de.company.accountingfx.dispatcher.Dispatcher;
import de.company.accountingfx.dispatcher.PersistMessage;
import de.company.accountingfx.store.AppDB;
import de.company.accountingfx.store.util.IMainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application implements IMainController {

    private Stage stage;
    private BorderPane rootLayout;

    public MainApp() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("AccountingApp");
        stage.getIcons().add(new Image("file:resources/images/money.png"));
        Dispatcher.subscribe(this);

        initRootLayout();

        Dispatcher.dispatch(new PersistMessage(PersistMessage.LOAD_RECORDTABLE));
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("action_view/TableView.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);

            TableViewController controller = loader.getController();
            controller.setMainController(this);

            Dispatcher.dispatch(new DataMessage(DataMessage.REQUEST));
            Dispatcher.dispatch(new PersistMessage(PersistMessage.LOAD_TESTDATA));

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Subscribe
    public void subscribeAppDb(AppDB appDB) {
        stage.titleProperty().bind(appDB.titleProperty());
    }
}
