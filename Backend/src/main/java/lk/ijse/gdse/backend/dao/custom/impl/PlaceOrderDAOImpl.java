package lk.ijse.gdse.backend.dao.custom.impl;

import lk.ijse.gdse.backend.dao.custom.PlaceOrderDAO;
import lk.ijse.gdse.backend.dao.custom.impl.util.SQLUtil;
import lk.ijse.gdse.backend.entity.PlaceOrder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceOrderDAOImpl implements PlaceOrderDAO {
    @Override
    public ArrayList<PlaceOrder> getAll(Connection connection) {
        return null;
    }

    @Override
    public boolean save(Connection connection, PlaceOrder entity) throws SQLException {
        String sql = "INSERT INTO orders VALUES(?, ?, ?)";
        return SQLUtil.execute(sql, connection, entity.getOrderId(), entity.getOrderDate(), entity.getCustId());
    }

    @Override
    public boolean update(Connection connection, PlaceOrder entity) {
        return false;
    }

    @Override
    public PlaceOrder search(Connection connection, String s) {
        return null;
    }

    @Override
    public boolean delete(Connection connection, String s) {
        return false;
    }

    @Override
    public String generateNextId(Connection connection) throws SQLException {
        String sql = "SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1";
        ResultSet resultSet = SQLUtil.execute(sql, connection);
        if (resultSet.next()) {
            String id = resultSet.getString(1);
            return id;
        }
        return null;
    }
}
