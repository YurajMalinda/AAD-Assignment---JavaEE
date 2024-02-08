package lk.ijse.gdse.backend.dao.custom.impl;

import lk.ijse.gdse.backend.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse.backend.dao.custom.impl.util.SQLUtil;
import lk.ijse.gdse.backend.entity.Item;
import lk.ijse.gdse.backend.entity.OrderDetail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    @Override
    public ArrayList<OrderDetail> getAll(Connection connection) throws SQLException {
        String sql = "SELECT * FROM orderDetail";
        ResultSet resultSet = SQLUtil.execute(sql, connection);
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        while (resultSet.next()) {
            orderDetails.add(new OrderDetail(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getDouble(4)));
        }
        return orderDetails;
    }

    @Override
    public boolean save(Connection connection, OrderDetail entity) throws SQLException {
        String sql = "INSERT INTO orderDetail VALUES(?, ?, ?,?)";
        return SQLUtil.execute(sql, connection, entity.getOrderId(), entity.getItemCode(), entity.getQty(),entity.getUnitPrice());
    }

    @Override
    public boolean update(Connection connection, OrderDetail entity) {
        return false;
    }

    @Override
    public OrderDetail search(Connection connection, String orderId) throws SQLException {
        String sql =  "SELECT * FROM orderDetail WHERE orderId = ?";
        ResultSet resultSet = SQLUtil.execute(sql, connection, orderId);
        if (resultSet.next()) {
            return new OrderDetail(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getDouble(4));
        }
        return null;
    }

    @Override
    public boolean delete(Connection connection, String s) {
        return false;
    }
}
