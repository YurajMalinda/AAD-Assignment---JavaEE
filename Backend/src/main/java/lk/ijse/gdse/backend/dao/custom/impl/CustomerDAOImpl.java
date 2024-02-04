package lk.ijse.gdse.backend.dao.custom.impl;

import lk.ijse.gdse.backend.dao.custom.CustomerDAO;
import lk.ijse.gdse.backend.entity.Customer;

import java.sql.Connection;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll(Connection connection) {
        return null;
    }

    @Override
    public boolean save(Connection connection, Customer entity) {
        return false;
    }

    @Override
    public boolean update(Connection connection, Customer entity) {
        return false;
    }

    @Override
    public Customer search(Connection connection, String s) {
        return null;
    }

    @Override
    public boolean delete(Connection connection, String s) {
        return false;
    }
}
