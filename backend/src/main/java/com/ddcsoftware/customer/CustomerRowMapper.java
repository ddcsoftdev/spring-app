package com.ddcsoftware.customer;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerRowMapper implements RowMapper<Customer> {

    //Construct the RowMapper with a Lambda
    //with rs (which points to the data of the row) you select the column type and name that you want
    //RowMapper goes through the table row by row,
    // selecting the data from all the columns you have selected with rs
    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Customer(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getInt("age"),
                Gender.valueOf(rs.getString("gender")));
    }
}
