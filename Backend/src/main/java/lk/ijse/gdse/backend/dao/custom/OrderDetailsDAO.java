package lk.ijse.gdse.backend.dao.custom;

import lk.ijse.gdse.backend.dao.CrudDAO;
import lk.ijse.gdse.backend.entity.OrderDetail;

import java.sql.Connection;

public interface OrderDetailsDAO extends CrudDAO<Connection, OrderDetail, String> {
}
