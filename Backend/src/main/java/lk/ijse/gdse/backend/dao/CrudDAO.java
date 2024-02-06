package lk.ijse.gdse.backend.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<CONNECTION, T, ID> extends SuperDAO{
    ArrayList<T> getAll(CONNECTION connection) throws SQLException;

    boolean save(CONNECTION connection, T entity) throws SQLException;

    boolean update(CONNECTION connection, T entity) throws SQLException;

    T search(CONNECTION connection, ID id);

    boolean delete(CONNECTION connection, ID id) throws SQLException;
}
