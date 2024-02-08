package lk.ijse.gdse.backend.dao.custom;

import lk.ijse.gdse.backend.dao.CrudDAO;
import lk.ijse.gdse.backend.entity.PlaceOrder;

import java.sql.Connection;
import java.sql.SQLException;

public interface PlaceOrderDAO extends CrudDAO<Connection, PlaceOrder, String> {
    String generateNextId(Connection connection) throws SQLException;
}
