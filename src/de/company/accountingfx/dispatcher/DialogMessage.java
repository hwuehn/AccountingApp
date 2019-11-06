package de.company.accountingfx.dispatcher;

import javafx.stage.Stage;

import java.io.File;

public class DialogMessage implements IMsg {

    public final static String LOAD_DIALOG = "load_Dialog";
    public final static String SAVEAS_DIALOG = "saveAS_Dialog";

    private final String msgType;
    private final File file;
    private Stage stage;

    public DialogMessage(String msgType, File file, Stage stage) {
        this.msgType = msgType;
        this.file = file;
        this.stage = stage;
    }

    public DialogMessage(String msgType) {
        this(msgType, null, null);
    }

    public DialogMessage(String msgType, Stage stage) {
        this(msgType, null, stage);
    }

    @Override
    public String getMsgType() {
        return msgType;
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public String toString() {
        return "DialogMessage{" +
                "msgType='" + msgType + '\'' +
                ", file=" + file +
                ", stage=" + stage +
                '}';
    }
}
