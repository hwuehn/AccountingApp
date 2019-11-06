package de.company.accountingfx.action;

import de.company.accountingfx.dispatcher.DialogMessage;
import de.company.accountingfx.dispatcher.Dispatcher;
import de.company.accountingfx.dispatcher.PersistMessage;
import de.company.accountingfx.store.util.IMainController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

    public class RootLayoutController {

        private IMainController mainController;

        public void setMainController(IMainController controller) {
            this.mainController = controller;
        }

        public RootLayoutController() {
        }

        @FXML
        private void handleNew() {
            Dispatcher.dispatch(new PersistMessage(PersistMessage.NEW_RECORDTABLE));
        }

        @FXML
        private void handleOpen()  {
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
//        Account account = new Account();
//        mainApp.showAccountAddDialog(account);
//        mainApp.getAccountList().add(account);
    }
}


