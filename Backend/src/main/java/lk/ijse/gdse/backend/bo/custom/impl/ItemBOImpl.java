package lk.ijse.gdse.backend.bo.custom.impl;

import lk.ijse.gdse.backend.bo.custom.ItemBO;
import lk.ijse.gdse.backend.dao.DAOFactory;
import lk.ijse.gdse.backend.dao.custom.ItemDAO;
import lk.ijse.gdse.backend.dto.CustomerDTO;
import lk.ijse.gdse.backend.dto.ItemDTO;
import lk.ijse.gdse.backend.entity.Customer;
import lk.ijse.gdse.backend.entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {

    ItemDAO itemDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM_DAO);
    @Override
    public ArrayList<ItemDTO> getAllItem(Connection connection) throws SQLException {
        ArrayList<Item> items = itemDAO.getAll(connection);
        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();

        for (Item i : items) {
            itemDTOS.add(new ItemDTO(i.getItemCode(), i.getItemName(), i.getPrice(), i.getQty()));
        }
        return itemDTOS;
    }

    @Override
    public boolean saveItem(Connection connection, ItemDTO itemDTO) throws SQLException {
        return itemDAO.save(connection, new Item(itemDTO.getItemCode(), itemDTO.getItemName(), itemDTO.getPrice(), itemDTO.getQty()));
    }

    @Override
    public boolean updateItem(Connection connection, ItemDTO itemDTO) throws SQLException {
        return itemDAO.update(connection, new Item(itemDTO.getItemCode(), itemDTO.getItemName(), itemDTO.getPrice(), itemDTO.getQty()));
    }

    @Override
    public ItemDTO searchItem(Connection connection, String code) throws SQLException {
        Item item = itemDAO.search(connection, code);
        if (item != null){
            return new ItemDTO(item.getItemCode(), item.getItemName(), item.getPrice(), item.getQty());
        }
        return null;
    }

    @Override
    public boolean deleteItem(Connection connection, String code) throws SQLException {
        return itemDAO.delete(connection, code);
    }
}
