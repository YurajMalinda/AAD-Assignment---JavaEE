package lk.ijse.gdse.backend.dao.custom;

import lk.ijse.gdse.backend.dao.CrudDAO;
import lk.ijse.gdse.backend.entity.PlaceOrder;

import java.sql.Connection;

public interface OrderDAO extends CrudDAO<Connection, PlaceOrder, String> {
}
