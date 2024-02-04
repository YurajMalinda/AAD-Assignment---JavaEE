package lk.ijse.gdse.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private String itemCode;
    private String itemName;
    private double price;
    private int qty;
}
