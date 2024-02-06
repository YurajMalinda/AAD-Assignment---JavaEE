package lk.ijse.gdse.backend.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.gdse.backend.bo.BOFactory;
import lk.ijse.gdse.backend.bo.custom.CustomerBO;
import lk.ijse.gdse.backend.dto.CustomerDTO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Objects;

@WebServlet(name = "customerServlet", urlPatterns = "/customers")
public class CustomerServlet extends HttpServlet {

    CustomerBO customerBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER_BO);
    DataSource dataSource;

    @Override
    public void init() throws ServletException {
        try{
            InitialContext initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/pos");

            Connection connection = dataSource.getConnection();
            System.out.println(connection);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("GET Method");

        String option = req.getParameter("option");
        System.out.println(option);

        String id = req.getParameter("id");
        System.out.println(id);

        resp.setContentType("application/json");
        Jsonb jsonb = JsonbBuilder.create();

        try(Connection connection = dataSource.getConnection()){
            if (Objects.equals(option, "getAll")) {
                ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomer(connection);
                jsonb.toJson(allCustomers, resp.getWriter());
            }else if (Objects.equals(option, "search")) {
                CustomerDTO customerDTO = customerBO.searchCustomer(connection, id);
                jsonb.toJson(customerDTO, resp.getWriter());
            }
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        System.out.println("POST Method");
        Jsonb jsonb = JsonbBuilder.create();
        CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);

        String id = customerDTO.getCustId();
        String name = customerDTO.getCustName();
        String address = customerDTO.getCustAddress();
        String salary = String.valueOf(customerDTO.getCustSalary());
        System.out.println(salary);

        if (id == null || !id.matches("^C00-[0-9]{3}$")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid customer ID format!. Please use the pattern 'C00-001'."));
            return;
        }
        if (name == null || !name.matches("[A-Za-z ]+")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid customer name format!."));
            return;
        }
        if (address == null || address.length() < 3) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid customer address format!. Please use minimum 4 characters."));
            return;
        }
        if (salary == null || !salary.matches("[1-9]\\d*(\\.\\d+)?")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid customer salary format!. Please use the pattern '1000.00'."));
            return;
        }
        try (Connection connection = dataSource.getConnection()) {
            boolean saveCustomer = customerBO.saveCustomer(connection, customerDTO);
            if (saveCustomer) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson("Customer added successfully!."));
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson("Customer added failed!."));
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            resp.getWriter().write(jsonb.toJson("Duplicate ID!. Customer not added!."));
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(jsonb.toJson("Database error!"));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("PUT Method");
        Jsonb jsonb = JsonbBuilder.create();
        CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);

        String name = customerDTO.getCustName();
        String address = customerDTO.getCustAddress();
        String salary = String.valueOf(customerDTO.getCustSalary());
        System.out.println(salary);

        if (name == null || !name.matches("[A-Za-z ]+")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid customer name format!."));
            return;
        }
        if (address == null || address.length() < 3) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid customer address format!. Please use minimum 4 characters."));
            return;
        }
        if (salary == null || !salary.matches("[1-9]\\d*(\\.\\d+)?")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid customer salary format!. Please use the pattern '1000.00'."));
            return;
        }

        try (Connection connection = dataSource.getConnection()) {
            boolean updateCustomer = customerBO.updateCustomer(connection, customerDTO);
            if (updateCustomer) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson("Customer updated successfully!."));
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson("Customer updated failed!."));
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(jsonb.toJson("Database error!"));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusId = req.getParameter("id");
        Jsonb jsonb = JsonbBuilder.create();

        if (cusId == null || !cusId.matches("^C00-[0-9]{3}$")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid customer ID format!. Please use the pattern 'C00-001'."));
            return;
        }
        try (Connection connection = dataSource.getConnection()) {
            boolean deleteCustomer = customerBO.deleteCustomer(connection, cusId);
            if (deleteCustomer) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson("Customer deleted successfully!."));
            }else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson("Customer deleted failed!."));
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(jsonb.toJson("Database error!"));
            throw new RuntimeException(e);
        }
    }
}
