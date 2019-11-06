package de.company.accountingfx.dispatcher;

import de.company.accountingfx.MainApp;
import de.company.accountingfx.store.AppDB;
import de.company.accountingfx.store.Record;
import de.company.accountingfx.store.util.RecordXMLWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;
import java.util.prefs.Preferences;

public class PersistService {

    private static final String ACCOUNTLIST_XML = "./resources/save/accountList.xml";

    public PersistService() {
    }

    public static void loadRecordDataFromFile(File file, List<Record> records) {
        if (file == null) return;
        try {
            JAXBContext context = JAXBContext.newInstance(RecordXMLWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            RecordXMLWrapper wrapper = (RecordXMLWrapper) um.unmarshal(file);

            records.clear();
            records = wrapper.getRecords();

            //    Dispatcher.dispatch(new PersistMessage(PersistMessage.SET_PATH, file, null));
            Dispatcher.dispatch(new PersistMessage(PersistMessage.LOADED_RECORDTABLE, null, records));

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static void setRecordFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());
        } else {
            prefs.remove("filePath");
        }
        Dispatcher.dispatch(new PersistMessage(PersistMessage.SET_TITLE, file, null));
    }

    public static void loadTestData(AppDB appDB) {
        appDB.loadTestData();
    }

    public static File getRecordFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        return filePath != null ? new File(filePath) : null;
    }

    public static void clearView(List<Record> records) {
        records.clear();
    }

    public static void saveRecordDataToFile(File file, List<Record> records) {
        if (file == null) return;
        try {
            JAXBContext context = JAXBContext.newInstance(RecordXMLWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            try {
                RecordXMLWrapper wrapper = new RecordXMLWrapper();
                wrapper.setRecords(records);
                m.marshal(wrapper, file);
            } catch (Exception e) {
                e.printStackTrace();
        }
        Dispatcher.dispatch(new PersistMessage(PersistMessage.SET_PATH, file, null));
        } catch (Exception e) {
        }
    }

    public static void exit() {
        System.exit(0);
    }


}
