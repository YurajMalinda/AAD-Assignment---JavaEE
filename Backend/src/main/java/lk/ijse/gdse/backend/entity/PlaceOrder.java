package lk.ijse.gdse.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrder {
    private String orderId;
    private String orderDate;
    private String custId;
    private double total;
}
