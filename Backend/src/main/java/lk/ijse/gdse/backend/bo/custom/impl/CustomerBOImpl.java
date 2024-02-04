package lk.ijse.gdse.backend.bo.custom.impl;

import lk.ijse.gdse.backend.bo.custom.CustomerBO;
import lk.ijse.gdse.backend.dto.CustomerDTO;

import java.sql.Connection;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    @Override
    public ArrayList<CustomerDTO> getAllCustomer(Connection connection) {
        return null;
    }

    @Override
    public boolean saveCustomer(Connection connection, CustomerDTO customerDTO) {
        return false;
    }

    @Override
    public boolean updateCustomer(Connection connection, CustomerDTO customerDTO) {
        return false;
    }

    @Override
    public CustomerDTO searchCustomer(Connection connection, String id) {
        return null;
    }

    @Override
    public boolean deleteCustomer(Connection connection, String id) {
        return false;
    }
}
