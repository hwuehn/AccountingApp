package de.company.accountingfx.model;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Helper class to wrap a list of accounts. This is used for saving the
 * list of accounts to XML.
 *
 * @author Henning Wuehn
 */
@XmlRootElement(name = "accounts")
public class AccListWrapper {

    private List<Account> accounts;

    @XmlElement(name = "account")
    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
