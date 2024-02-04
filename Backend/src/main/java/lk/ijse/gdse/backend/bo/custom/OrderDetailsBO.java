package lk.ijse.gdse.backend.bo.custom;

import lk.ijse.gdse.backend.bo.SuperBO;
import lk.ijse.gdse.backend.dto.ItemDTO;
import lk.ijse.gdse.backend.dto.OrderDetailDTO;

import java.sql.Connection;
import java.util.ArrayList;

public interface OrderDetailsBO extends SuperBO {
    ArrayList<OrderDetailDTO> getAllOrderDetail(Connection connection);
}
