package org.academiadecodigo.javabank.services;

import org.academiadecodigo.javabank.model.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * An {@link AuthService} implementation
 */
public class AuthServiceImpl implements AuthService {

    private Connection dbConnection;
    private CustomerService customerService;
    private Customer accessingCustomer;

    public void setDbConnection(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    /**
     * Sets the customer service
     *
     * @param customerService the customer service to set
     */
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * @see AuthService#authenticate(Integer)
     */
    @Override
    public boolean authenticate(Integer id) {

        try {
            Statement statement = dbConnection.createStatement();

            String query = "SELECT * FROM customer WHERE customer_id =" + id;

            ResultSet resultSet = statement.executeQuery(query);

            if(resultSet.next()){
                accessingCustomer = customerService.get(id);
                System.out.println(accessingCustomer);
                System.out.println("User authenticated");
                return true;
            }
            return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //return accessingCustomer != null;
    }

    /**
     * @see AuthService#getAccessingCustomer()
     */
    @Override
    public Customer getAccessingCustomer() {
        return accessingCustomer;
    }
}
