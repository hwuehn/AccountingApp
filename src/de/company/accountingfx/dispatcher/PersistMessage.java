package de.company.accountingfx.dispatcher;

import java.io.File;
import java.util.List;

public class PersistMessage<T> implements IMsg{

    public static final String LOAD_RECORDTABLE = " load_recordTable ";
    public static final String LOAD_ACCOUNTLIST = " load_accountList ";
    public static final String SAVE_RECORDTABLE = " save_recordTable ";
    public static final String SAVE_ACCOUNTLIST = " save_accountList ";
    public static final String SET_PATH =         " set_recordFilePath ";
    public static final String NEW_RECORDTABLE =  " new_recordTable ";
    public static final String LOAD_TESTDATA =    " load_testData ";
    public static final String EXIT =             " exit_project ";
    public static final String SET_TITLE =        " set_title ";
    public static final String LOADED_RECORDTABLE = " loading_success ";

    public File file;
    public List<T> payload;
    private String msgType;

    public PersistMessage(String msgType) {
        this(msgType, null, null);
    }

    public PersistMessage(String msgType, File file, List<T> payload) {
        this.msgType = msgType;
        this.file = file;
        this.payload = payload;
    }

    @Override
    public String getMsgType() {
        return msgType;
    }

    @Override
    public String toString() {
        return "PersistMessage{" +
                "file=" + file +
                ", msgType='" + msgType + '\'' +
                '}';
    }
}



