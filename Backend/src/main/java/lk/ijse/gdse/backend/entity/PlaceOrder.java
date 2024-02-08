package lk.ijse.gdse.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrder {
    private String orderId;
    private LocalDate orderDate;
    private String custId;
}
