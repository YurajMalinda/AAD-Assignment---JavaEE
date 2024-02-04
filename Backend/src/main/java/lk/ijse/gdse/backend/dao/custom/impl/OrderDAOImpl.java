package lk.ijse.gdse.backend.dao.custom.impl;

import lk.ijse.gdse.backend.dao.custom.OrderDAO;
import lk.ijse.gdse.backend.entity.PlaceOrder;

import java.sql.Connection;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public ArrayList<PlaceOrder> getAll(Connection connection) {
        return null;
    }

    @Override
    public boolean save(Connection connection, PlaceOrder entity) {
        return false;
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
}
