package de.company.accountingfx.model;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Helper class to wrap a list of records. This is used for saving the
 * list of records to XML.
 *
 * @author Henning Wuehn
 */
@XmlRootElement(name = "records")
public class RecordListWrapper {

    private List<Record> records;

    @XmlElement(name = "record")
    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }
}
