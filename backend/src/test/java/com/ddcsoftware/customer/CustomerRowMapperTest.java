package com.ddcsoftware.customer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerRowMapperTest {

    @Test
    void mapRow() throws SQLException {
        CustomerRowMapper customerRowMapper = new CustomerRowMapper();
        Customer customer = new Customer(
                2, "James", "James@example.com", 31);

        //forward declare a mock within method
        ResultSet resultSet = mock(ResultSet.class);
        //then tell the mock what to return
        when(resultSet.getInt("id")).thenReturn(customer.getId());
        when(resultSet.getString("name")).thenReturn(customer.getName());
        when(resultSet.getString("email")).thenReturn(customer.getEmail());
        when(resultSet.getInt("age")).thenReturn(customer.getAge());

        //mock method and get customer. column for mapRow doesn't matter as its mocked
        Customer expected = customerRowMapper.mapRow(resultSet, 1);

        assertThat(customer).isEqualTo(expected);
    }
}