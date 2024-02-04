package lk.ijse.gdse.backend.bo.custom;

import lk.ijse.gdse.backend.dto.ItemDTO;
import lk.ijse.gdse.backend.dto.OrderDetailDTO;

import java.sql.Connection;
import java.util.ArrayList;

public interface OrderDetailsBO {
    ArrayList<OrderDetailDTO> getAllOrderDetail(Connection connection);
}
