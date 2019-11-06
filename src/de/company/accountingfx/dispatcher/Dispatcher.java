package de.company.accountingfx.dispatcher;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.company.accountingfx.store.AppDB;
import de.company.accountingfx.store.Record;

import java.io.File;
import java.util.List;

public class Dispatcher {

    private final AppDB appDB;
    private final EventBus eventBus;

    private Dispatcher() {
        appDB = new AppDB();
        eventBus = new EventBus();
        eventBus.register(this);
    }

    public static void subscribe(Object o) {
        System.out.println("Dispatcher.subscribe");
        System.out.println("o= " + o);
        getInstance().eventBus.register(o);
    }

    public static Dispatcher getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final Dispatcher INSTANCE = new Dispatcher();
    }

    public static void dispatch(IMsg msg) {
        getInstance().dispatch_(msg);
    }

    private void dispatch_(IMsg msg) {
        System.out.println(msg);
        eventBus.post(msg);
    }

    public void setTitle(File file) {
        String path = file != null ? " - " + file.getName() : "";
        appDB.setTitle("AccountingApp" + path);
    }

    public File pathOrDefaultPath(PersistMessage msg) {
        return msg.file == null ? PersistService.getRecordFilePath() : msg.file;
    }

    @Subscribe
    public void dispatchDataMessage(DataMessage msg) {
        switch (msg.getMsgType()){

            case DataMessage.REQUEST:
                eventBus.post(appDB);
                break;

            default:
        }
    }

    @Subscribe
    public void handleDeadEvent(DeadEvent deadEvent) {
        System.out.println("!!! No subscriber message: !!!");
        System.out.println(deadEvent.getEvent().toString());
    }

    @Subscribe
    public void dispatchDialogMessage(DialogMessage msg) {
        switch (msg.getMsgType()) {

            case DialogMessage.LOAD_DIALOG:
                DialogService.showLoadDialog(msg);
                break;
            case DialogMessage.SAVEAS_DIALOG:
                DialogService.showSaveAsDialog(msg);
                break;

            default:
                throw new IllegalStateException("Message not defined: " + msg.getMsgType());
        }
    }

    @Subscribe
    public void dispatchPersistMessage(PersistMessage msg) {
        switch (msg.getMsgType()) {

            case PersistMessage.LOAD_TESTDATA:
                PersistService.loadTestData(appDB);
                break;
            case PersistMessage.LOAD_RECORDTABLE:
                PersistService.loadRecordDataFromFile(pathOrDefaultPath(msg), appDB.getRecords());
                break;
            case PersistMessage.SET_TITLE:
                setTitle(msg.file);
                break;
            case PersistMessage.SET_PATH:
                PersistService.setRecordFilePath(msg.file);
                break;
            case PersistMessage.NEW_RECORDTABLE:
                PersistService.clearView(appDB.getRecords());
                break;
            case PersistMessage.SAVE_RECORDTABLE:
                PersistService.saveRecordDataToFile(pathOrDefaultPath(msg), appDB.getRecords());
                break;
            case PersistMessage.EXIT:
                PersistService.exit();
                break;
            case PersistMessage.LOADED_RECORDTABLE:
                setTable(((PersistMessage<Record>) msg).payload);
                break;

            default:
                throw new IllegalStateException("Message not defined: " + msg.getMsgType());
        }
    }

    private void setTable(List<Record> records) {
        appDB.getRecords().clear();
        appDB.getRecords().addAll(records);

    }


}
