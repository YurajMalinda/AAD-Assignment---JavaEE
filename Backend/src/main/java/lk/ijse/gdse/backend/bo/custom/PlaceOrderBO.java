package lk.ijse.gdse.backend.bo.custom;

import lk.ijse.gdse.backend.bo.SuperBO;
import lk.ijse.gdse.backend.dto.CustomerDTO;
import lk.ijse.gdse.backend.dto.ItemDTO;
import lk.ijse.gdse.backend.dto.PlaceOrderDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface PlaceOrderBO extends SuperBO {
    boolean purchaseOrder(Connection connection, PlaceOrderDTO placeOrderDTO) throws SQLException;

    CustomerDTO searchCustomer(Connection connection, String id);

    ItemDTO searchItem(Connection connection, String code);

    String generateNewOrderID(Connection connection) throws SQLException;
}
