package lk.ijse.gdse.backend.bo.custom;

import lk.ijse.gdse.backend.bo.SuperBO;
import lk.ijse.gdse.backend.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    ArrayList<CustomerDTO> getAllCustomer(Connection connection) throws SQLException;

    boolean saveCustomer(Connection connection, CustomerDTO customerDTO) throws SQLException;

    boolean updateCustomer(Connection connection, CustomerDTO customerDTO) throws SQLException;

    CustomerDTO searchCustomer(Connection connection, String id);

    boolean deleteCustomer(Connection connection, String id) throws SQLException;
}
