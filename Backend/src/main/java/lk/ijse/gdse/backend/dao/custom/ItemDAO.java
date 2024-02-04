package lk.ijse.gdse.backend.dao.custom;

import lk.ijse.gdse.backend.dao.CrudDAO;
import lk.ijse.gdse.backend.entity.Item;

import java.sql.Connection;

public interface ItemDAO extends CrudDAO<Connection, Item, String> {
}
