package lk.ijse.gdse.backend.bo.custom.impl;

import lk.ijse.gdse.backend.bo.custom.CustomerBO;
import lk.ijse.gdse.backend.dao.DAOFactory;
import lk.ijse.gdse.backend.dao.custom.CustomerDAO;
import lk.ijse.gdse.backend.dto.CustomerDTO;
import lk.ijse.gdse.backend.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER_DAO);
    @Override
    public ArrayList<CustomerDTO> getAllCustomer(Connection connection) throws SQLException {
        ArrayList<Customer> customers = customerDAO.getAll(connection);
        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();

        for (Customer c : customers) {
            customerDTOS.add(new CustomerDTO(c.getCustId(), c.getCustName(), c.getCustAddress(), c.getCustSalary()));
        }
        return customerDTOS;
    }

    @Override
    public boolean saveCustomer(Connection connection, CustomerDTO customerDTO) throws SQLException {
        return customerDAO.save(connection, new Customer(customerDTO.getCustId(), customerDTO.getCustName(), customerDTO.getCustAddress(), customerDTO.getCustSalary()));
    }

    @Override
    public boolean updateCustomer(Connection connection, CustomerDTO customerDTO) throws SQLException {
        return customerDAO.update(connection, new Customer(customerDTO.getCustId(), customerDTO.getCustName(), customerDTO.getCustAddress(), customerDTO.getCustSalary()));
    }

    @Override
    public CustomerDTO searchCustomer(Connection connection, String id) throws SQLException {
        Customer customer = customerDAO.search(connection,id);
        if (customer != null){
            return new CustomerDTO(customer.getCustId(), customer.getCustName(), customer.getCustAddress(), customer.getCustSalary());
        }
        return null;
    }

    @Override
    public boolean deleteCustomer(Connection connection, String id) throws SQLException {
        return customerDAO.delete(connection, id);
    }
}
