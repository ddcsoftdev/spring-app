package com.ddcsoftware.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                SELECT *
                FROM customer
                """;
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer customerId) {
        var sql = """
                SELECT * FROM customer
                WHERE id = ?
                """;
        //return a list with stream and then findFirst.
        //this is the fast way and oneliner method to check if empty,
        // and get without throwing exception with get(0)
        return jdbcTemplate.query(sql, customerRowMapper, customerId).stream().findFirst();
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        var sql = """
                SELECT count(*)
                FROM customer
                WHERE email = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public boolean existsCustomerWithId(Integer customerId) {
        var sql = """
                SELECT count(*)
                FROM customer
                WHERE id = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, customerId);
        return count != null && count > 0;
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                INSERT INTO customer (name, email, password, age, gender)
                VALUES (?, ?, ?, ?, ?)
                """;

        //returns number of rows affected
        int rowsAffected = jdbcTemplate.update(sql,
                customer.getName(),
                customer.getEmail(),
                customer.getPassword(),
                customer.getAge(),
                customer.getGender().name());
        System.out.printf("Insert Customer, rowsAffected = %d\n", rowsAffected);
    }

    @Override
    public void deleteCustomer(Integer customerId) {
        var sql = """
                DELETE FROM customer
                WHERE id = ?
                """;
        int rowsAffected = jdbcTemplate.update(sql, customerId);
        System.out.printf("Delete Customer by Id, rowsAffected = %d\n", rowsAffected);
    }

    @Override
    public void updateCustomer(Customer update) {
        var sql = """
                UPDATE customer
                SET name = ? , email = ? , password = ?, age = ?, gender = ?
                WHERE id = ?
                """;
        int rowsAffected = jdbcTemplate.update(sql,
                update.getName(),
                update.getEmail(),
                update.getPassword(),
                update.getAge(),
                update.getGender().name(),
                update.getId());
        System.out.printf("Update Customer, rowsAffected = %d\n", rowsAffected);
    }

    @Override
    public Optional<Customer> selectUserByEmail(String email) {
        var sql = """
                SELECT * FROM customer
                WHERE email = ?
                """;
        //return a list with stream and then findFirst.
        //this is the fast way and oneliner method to check if empty,
        // and get without throwing exception with get(0)
        return jdbcTemplate.query(sql, customerRowMapper, email).stream().findFirst();
    }
}
