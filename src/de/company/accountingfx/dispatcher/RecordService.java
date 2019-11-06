package de.company.accountingfx.dispatcher;

import de.company.accountingfx.store.AppDB;
import de.company.accountingfx.store.Record;

public class RecordService {

    public static void selectRecord(Record newValue, AppDB appDB) {

        appDB.setCurrentRecord(newValue);
    }

}
