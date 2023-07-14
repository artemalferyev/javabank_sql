package org.academiadecodigo.javabank.services;

import org.academiadecodigo.javabank.model.account.Account;
import org.academiadecodigo.javabank.model.account.AccountType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * An {@link AccountService} implementation
 */
public class AccountServiceImpl implements AccountService {

    private Connection dbConnection;
    private Map<Integer, Account> accountMap = new HashMap<>();

    public void setDbConnection(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    /**
     * Gets the next account id
     *
     * @return the next id
     */
    private Integer getNextId() {
        String query = "SELECT MAX(account_id) FROM account";

        try {
            Statement statement = dbConnection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

            int result = 1;

            if (resultSet.next()) {
                result = resultSet.getInt(1)+1;
            }
            System.out.println("returning result: " + result);
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

     //   return accountMap.isEmpty() ? 1 : Collections.max(accountMap.keySet()) + 1;
    }

    /**
     * @see AccountService#add(Account)
     */
    public void add(Account account) {

        /*String query = "INSERT INTO account(account_id, customer_id, account_balance, account_type) VALUES(" + account.getId()" , " + ";*/

        try {
            Statement statement = dbConnection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * @see AccountService#deposit(int, double)
     */
    public void deposit(int id, double amount) {
        accountMap.get(id).credit(amount);
    }

    /**
     * @see AccountService#withdraw(int, double)
     */
    public void withdraw(int id, double amount) {

        Account account = accountMap.get(id);

        if (!account.canWithdraw()) {
            return;
        }

        accountMap.get(id).debit(amount);
    }

    /**
     * @see AccountService#transfer(int, int, double)
     */
    public void transfer(int srcId, int dstId, double amount) {

        Account srcAccount = accountMap.get(srcId);
        Account dstAccount = accountMap.get(dstId);

        // make sure transaction can be performed
        if (srcAccount.canDebit(amount) && dstAccount.canCredit(amount)) {
            srcAccount.debit(amount);
            dstAccount.credit(amount);
        }
    }
}
