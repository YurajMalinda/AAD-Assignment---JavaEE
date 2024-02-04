package lk.ijse.gdse.backend.dao.custom;

import lk.ijse.gdse.backend.dao.CrudDAO;
import lk.ijse.gdse.backend.entity.Customer;

import java.sql.Connection;

public interface CustomerDAO extends CrudDAO<Connection, Customer, String> {
}
