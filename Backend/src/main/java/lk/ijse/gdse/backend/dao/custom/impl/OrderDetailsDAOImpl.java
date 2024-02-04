package lk.ijse.gdse.backend.dao.custom.impl;

import lk.ijse.gdse.backend.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse.backend.entity.OrderDetail;

import java.sql.Connection;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    @Override
    public ArrayList<OrderDetail> getAll(Connection connection) {
        return null;
    }

    @Override
    public boolean save(Connection connection, OrderDetail entity) {
        return false;
    }

    @Override
    public boolean update(Connection connection, OrderDetail entity) {
        return false;
    }

    @Override
    public OrderDetail search(Connection connection, String s) {
        return null;
    }

    @Override
    public boolean delete(Connection connection, String s) {
        return false;
    }
}
