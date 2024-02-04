package lk.ijse.gdse.backend.bo.custom;

import lk.ijse.gdse.backend.dto.CustomerDTO;
import lk.ijse.gdse.backend.dto.ItemDTO;
import lk.ijse.gdse.backend.dto.PlaceOrderDTO;

import java.sql.Connection;
import java.util.ArrayList;

public interface PlaceOrderBO {
    boolean purchaseOrder(Connection connection, PlaceOrderDTO placeOrderDTO);

    CustomerDTO searchCustomer(Connection connection, String id);

    ItemDTO searchItem(Connection connection, String code);

    String generateNewOrderID(Connection connection);
}
