package lk.ijse.gdse.backend.bo.custom;

import lk.ijse.gdse.backend.bo.SuperBO;
import lk.ijse.gdse.backend.dto.CustomerDTO;

import java.sql.Connection;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    ArrayList<CustomerDTO> getAllCustomer(Connection connection);

    boolean saveCustomer(Connection connection, CustomerDTO customerDTO);

    boolean updateCustomer(Connection connection, CustomerDTO customerDTO);

    CustomerDTO searchCustomer(Connection connection, String id);

    boolean deleteCustomer(Connection connection, String id);
}
