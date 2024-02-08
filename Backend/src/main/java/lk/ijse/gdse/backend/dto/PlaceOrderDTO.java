package lk.ijse.gdse.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderDTO {
    private String orderId;
    private LocalDate orderDate;
    private String custId;
    private List<OrderDetailDTO> orderDetails;
}
