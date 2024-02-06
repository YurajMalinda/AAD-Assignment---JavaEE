package lk.ijse.gdse.backend.dao.custom.impl;

import lk.ijse.gdse.backend.dao.custom.CustomerDAO;
import lk.ijse.gdse.backend.dao.custom.impl.util.SQLUtil;
import lk.ijse.gdse.backend.entity.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll(Connection connection) throws SQLException {
        String sql = "SELECT * FROM customer";
        ResultSet resultSet = SQLUtil.execute(sql, connection);
        ArrayList<Customer> customers = new ArrayList<>();
        while (resultSet.next()) {
            customers.add(new Customer(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDouble(4)));
        }
        return customers;
    }

    @Override
    public boolean save(Connection connection, Customer entity) throws SQLException {
        String sql = "INSERT INTO customer VALUES (?, ?, ?, ?)";
        return SQLUtil.execute(sql, connection, entity.getCustId(), entity.getCustName(), entity.getCustAddress(), entity.getCustSalary());
    }

    @Override
    public boolean update(Connection connection, Customer entity) throws SQLException {
        String sql = "UPDATE customer SET custName=?, custAddress=?, custSalary=? WHERE custId=?";
        return SQLUtil.execute(sql, connection, entity.getCustName(), entity.getCustAddress(), entity.getCustSalary(), entity.getCustId());
    }

    @Override
    public Customer search(Connection connection, String id) {
        return null;
    }

    @Override
    public boolean delete(Connection connection, String id) throws SQLException {
        String sql = "DELETE FROM customer WHERE custId=?";
        return SQLUtil.execute(sql, connection, id);
    }
}
