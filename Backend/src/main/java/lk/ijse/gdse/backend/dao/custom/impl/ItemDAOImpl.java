package lk.ijse.gdse.backend.dao.custom.impl;

import lk.ijse.gdse.backend.dao.custom.ItemDAO;
import lk.ijse.gdse.backend.dao.custom.impl.util.SQLUtil;
import lk.ijse.gdse.backend.entity.Customer;
import lk.ijse.gdse.backend.entity.Item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Item> getAll(Connection connection) throws SQLException {
        String sql = "SELECT * FROM item";
        ResultSet resultSet = SQLUtil.execute(sql, connection);
        ArrayList<Item> items = new ArrayList<>();
        while (resultSet.next()) {
            items.add(new Item(resultSet.getString(1), resultSet.getString(2), resultSet.getDouble(3), resultSet.getInt(4)));
        }
        return items;
    }

    @Override
    public boolean save(Connection connection, Item entity) throws SQLException {
        String sql = "INSERT INTO item VALUES (?, ?, ?, ?)";
        return SQLUtil.execute(sql, connection, entity.getItemCode(), entity.getItemName(), entity.getPrice(), entity.getQty());
    }

    @Override
    public boolean update(Connection connection, Item entity) throws SQLException {
        String sql = "UPDATE item SET itemName=?, price=?, qty=? WHERE itemCode=?";
        return SQLUtil.execute(sql, connection, entity.getItemName(), entity.getPrice(), entity.getQty(), entity.getItemCode());
    }

    @Override
    public Item search(Connection connection, String code) throws SQLException {
        String sql =  "SELECT * FROM item WHERE itemCode = ?";
        ResultSet resultSet = SQLUtil.execute(sql, connection, code);
        if (resultSet.next()) {
            return new Item(resultSet.getString(1), resultSet.getString(2), resultSet.getDouble(3), resultSet.getInt(4));
        }
        return null;
    }

    @Override
    public boolean delete(Connection connection, String code) throws SQLException {
        String sql = "DELETE FROM item WHERE itemCode=?";
        return SQLUtil.execute(sql, connection, code);
    }
}
