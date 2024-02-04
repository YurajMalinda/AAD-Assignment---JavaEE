package lk.ijse.gdse.backend.bo.custom.impl;

import lk.ijse.gdse.backend.bo.custom.ItemBO;
import lk.ijse.gdse.backend.dto.ItemDTO;

import java.sql.Connection;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    @Override
    public ArrayList<ItemDTO> getAllItem(Connection connection) {
        return null;
    }

    @Override
    public boolean saveItem(Connection connection, ItemDTO itemDTO) {
        return false;
    }

    @Override
    public boolean updateItem(Connection connection, ItemDTO itemDTO) {
        return false;
    }

    @Override
    public ItemDTO searchItem(Connection connection, String code) {
        return null;
    }

    @Override
    public boolean deleteItem(Connection connection, String code) {
        return false;
    }
}
