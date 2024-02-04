package lk.ijse.gdse.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderDTO {
    private String orderId;
    private String orderDate;
    private String custId;
    private double total;
    private List<OrderDetailDTO> orderDetailList;
}
