package de.company.accountingfx.store.util;
import de.company.accountingfx.store.Account;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "accounts")
public class AccountXMLWrapper {

    private List<Account> accounts;

    @XmlElement(name = "account")
    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
