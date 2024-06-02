package com.ddcsoftware.customer;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object (DAO) interface for managing customer data.
 * Provides methods for performing CRUD operations on customer data.
 */
public interface CustomerDao {

    /**
     * Retrieves all customers from the data source.
     *
     * @return a list of all customers.
     */
    List<Customer> selectAllCustomers();

    /**
     * Retrieves a customer by their ID.
     *
     * @param customerId the ID of the customer to retrieve.
     * @return an Optional containing the customer if found, or an empty Optional if not found.
     */
    Optional<Customer> selectCustomerById(Integer customerId);

    /**
     * Checks if a customer with the given email exists in the data source.
     *
     * @param email the email to check.
     * @return true if a customer with the email exists, false otherwise.
     */
    boolean existsCustomerWithEmail(String email);

    /**
     * Checks if a customer with the given ID exists in the data source.
     *
     * @param customerId the ID to check.
     * @return true if a customer with the ID exists, false otherwise.
     */
    boolean existsCustomerWithId(Integer customerId);

    /**
     * Inserts a new customer into the data source.
     *
     * @param customer the customer to insert.
     */
    void insertCustomer(Customer customer);

    /**
     * Deletes a customer by their ID from the data source.
     *
     * @param customerId the ID of the customer to delete.
     */
    void deleteCustomer(Integer customerId);

    /**
     * Updates an existing customer in the data source.
     *
     * @param update the customer data to update.
     */
    void updateCustomer(Customer update);

    /**
     * Retrieves a customer by their email.
     *
     * @param email the email of the customer to retrieve.
     * @return an Optional containing the customer if found, or an empty Optional if not found.
     */
    Optional<Customer> selectUserByEmail(String email);
}
