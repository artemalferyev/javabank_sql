package org.academiadecodigo.javabank.services;

import org.academiadecodigo.javabank.model.Customer;
import org.academiadecodigo.javabank.model.account.Account;
import org.omg.CORBA.FREE_MEM;

import java.sql.*;
import java.util.*;

/**
 * An {@link CustomerService} implementation
 */
public class CustomerServiceImpl implements CustomerService {

    //private Map<Integer, Customer> customerMap = new HashMap<>();
    private Connection dbConnection;

    public void setDbConnection(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    /**
     * Gets the next account id
     *
     * @return the next id
     */

    private Integer getNextId() {
        String query = "SELECT MAX(id) FROM customer";

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


    }

    /**
     * @see CustomerService#get(Integer)
     */
    @Override
    public Customer get(Integer id) {
        Customer customer = new Customer();

        try {
            Statement statement = dbConnection.createStatement();

            String query = "SELECT customer_name FROM customer WHERE id=" + id;

            ResultSet resultSet = statement.executeQuery(query);

            resultSet.next();
            customer.setId(id);
            customer.setName(resultSet.getString(1));

            return customer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @see CustomerService#list()
     */
    @Override
    public List<Customer> list() {
        return null;
        //return new ArrayList<>(customerMap.values());
    }

    /**
     * @see CustomerService#listCustomerAccountIds(Integer)
     */
    @Override
    public Set<Integer> listCustomerAccountIds(Integer id) {
        Set<Integer> accountIds = new HashSet<>();
        try {
            Statement statement = dbConnection.createStatement();

            String query = "SELECT * FROM account WHERE customer_id=" + id;

            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                accountIds.add(resultSet.getInt(1));
            }
            return accountIds;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @see CustomerService#getBalance(int)
     */
    @Override
    public double getBalance(int id) {
        try {
            Statement statement = dbConnection.createStatement();

            String query = "SELECT SUM(account_balance) FROM account WHERE customer_id=" +id;

            ResultSet resultSet = statement.executeQuery(query);

            resultSet.next();
            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @see CustomerService#add(Customer)
     */
    @Override
    public void add(Customer customer) {


        /*if (customer.getId() == null) {
            customer.setId(getNextId());
        }

        customerMap.put(customer.getId(), customer);*/
    }
}
