package lk.ijse.gdse.backend.bo.custom.impl;

import lk.ijse.gdse.backend.bo.custom.PlaceOrderBO;
import lk.ijse.gdse.backend.dto.CustomerDTO;
import lk.ijse.gdse.backend.dto.ItemDTO;
import lk.ijse.gdse.backend.dto.PlaceOrderDTO;

import java.sql.Connection;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    @Override
    public boolean purchaseOrder(Connection connection, PlaceOrderDTO placeOrderDTO) {
        return false;
    }

    @Override
    public CustomerDTO searchCustomer(Connection connection, String id) {
        return null;
    }

    @Override
    public ItemDTO searchItem(Connection connection, String code) {
        return null;
    }

    @Override
    public String generateNewOrderID(Connection connection) {
        return null;
    }
}
