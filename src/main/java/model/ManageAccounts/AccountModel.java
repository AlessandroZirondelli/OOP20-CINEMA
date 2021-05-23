package model.ManageAccounts;

import java.util.Collection;

import utilities.ManageAccounts.Account;

public interface AccountModel {
    
    void addAccount(Account newAccount);
    void removeAccount(Account oldAccount);
    Collection<Account> getAccounts();
    //boolean isPresent(Account hereAccount);
}