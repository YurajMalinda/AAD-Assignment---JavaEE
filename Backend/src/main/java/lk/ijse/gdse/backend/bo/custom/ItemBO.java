package lk.ijse.gdse.backend.bo.custom;

import lk.ijse.gdse.backend.bo.SuperBO;
import lk.ijse.gdse.backend.dto.CustomerDTO;
import lk.ijse.gdse.backend.dto.ItemDTO;

import java.sql.Connection;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    ArrayList<ItemDTO> getAllItem(Connection connection);

    boolean saveItem(Connection connection, ItemDTO itemDTO);

    boolean updateItem(Connection connection, ItemDTO itemDTO);

    ItemDTO searchItem(Connection connection, String code);

    boolean deleteItem(Connection connection, String code);
}
