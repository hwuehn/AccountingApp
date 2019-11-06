package de.company.accountingfx.dispatcher;

import de.company.accountingfx.store.AppDB;
import de.company.accountingfx.store.Record;

public class RecordService {

    public static void selectRecord(Record newValue, AppDB appDB) {
        appDB.setCurrentRecord(newValue);
    }

//    public static void showDetail(Record record) {
//        TableViewController tableViewController = new TableViewController();
//
//        if (record != null) {
//            // Fill the labels with info from the record object.
//            tableViewController.getDebitAccIDLabel().setText(record.getDebitAcc().getAccID());
//            tableViewController.getDebitAccTagLabel().setText(record.getDebitAcc().getAccTag());
//            tableViewController.getDebitAccAmountLabel().setText(record.getAmount().toString());
//            tableViewController.getCreditAccIDLabel().setText(record.getCreditAcc().getAccID());
//            tableViewController.getCreditAccTagLabel().setText(record.getCreditAcc().getAccTag());
//            tableViewController.getCreditAccAmountLabel().setText(record.getAmount().toString());
//        } else {
//            // Accountingrecord is null, remove all the text.
//            tableViewController.getCreditAccIDLabel().setText("");
//            tableViewController.getDebitAccTagLabel().setText("");
//            tableViewController.getDebitAccIDLabel().setText("");
//            tableViewController.getCreditAccTagLabel().setText("");
//        }
//
//    }

}
