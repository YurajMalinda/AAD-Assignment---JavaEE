package lk.ijse.gdse.backend.dao.custom.impl;

import lk.ijse.gdse.backend.dao.custom.ItemDAO;
import lk.ijse.gdse.backend.entity.Item;

import java.sql.Connection;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Item> getAll(Connection connection) {
        return null;
    }

    @Override
    public boolean save(Connection connection, Item entity) {
        return false;
    }

    @Override
    public boolean update(Connection connection, Item entity) {
        return false;
    }

    @Override
    public Item search(Connection connection, String s) {
        return null;
    }

    @Override
    public boolean delete(Connection connection, String s) {
        return false;
    }
}
