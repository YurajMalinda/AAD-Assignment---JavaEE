package lk.ijse.gdse.backend.dao;

import java.util.ArrayList;

public interface CrudDAO<CONNECTION, T, ID> extends SuperDAO{
    ArrayList<T> getAll(CONNECTION connection);

    boolean save(CONNECTION connection, T dto);

    boolean update(CONNECTION connection, T dto);

    T search(CONNECTION connection, ID id);

    boolean delete(CONNECTION connection, ID id);
}
