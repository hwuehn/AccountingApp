package de.company.accountingfx.dispatcher;

import de.company.accountingfx.store.Record;

public class RecordMessage implements IMsg {

    public final static String SELECT = "select_record";
    public static final String ADD = "add_task";
    public static final String SHOW_DETAIL = "show_details";
    public final Record oldRecord;
    public final Record newRecord;
    private final String msgType;

    public RecordMessage(String msgType, Record newRecord) {
        this(msgType, newRecord, null);
    }

    public RecordMessage(String msgType) {
        this(msgType, null, null);
    }

    public RecordMessage(String msgType, Record oldRecord, Record newRecord) {
        this.msgType = msgType;
        this.oldRecord = oldRecord;
        this.newRecord = newRecord;
    }

    @Override
    public String getMsgType() {
        return msgType;
    }

    @Override
    public String toString() {
        return "RecordMessage{" +
                "msgType='" + msgType + '\'' +
                ", oldRecord=" + oldRecord +
                ", newRecord=" + newRecord +
                '}';
    }
}
