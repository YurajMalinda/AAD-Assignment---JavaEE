package lk.ijse.gdse.backend.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.gdse.backend.bo.BOFactory;
import lk.ijse.gdse.backend.bo.custom.ItemBO;
import lk.ijse.gdse.backend.dto.CustomerDTO;
import lk.ijse.gdse.backend.dto.ItemDTO;

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

@WebServlet(name = "ïtemServlet", urlPatterns = "/items")
public class ItemServlet extends HttpServlet {

    ItemBO itemBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM_BO);
    DataSource dataSource;

    @Override
    public void init() throws ServletException {
        try {
            InitialContext initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/pos");

            Connection connection = dataSource.getConnection();
            System.out.println(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("GET Method");

        String option = req.getParameter("option");
        System.out.println(option);

        String itemCode = req.getParameter("itemCode");
        System.out.println(itemCode);

        resp.setContentType("application/json");
        Jsonb jsonb = JsonbBuilder.create();

        try(Connection connection = dataSource.getConnection()){
            if (Objects.equals(option, "getAll")) {
                ArrayList<ItemDTO> allItems = itemBO.getAllItem(connection);
                jsonb.toJson(allItems, resp.getWriter());
            }else if (Objects.equals(option, "search")) {
                ItemDTO itemDTO = itemBO.searchItem(connection, itemCode);
                jsonb.toJson(itemDTO, resp.getWriter());
            }
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("POST Method");
        Jsonb jsonb = JsonbBuilder.create();
        ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);

        String itemCode = itemDTO.getItemCode();
        String itemName = itemDTO.getItemName();
        String price = String.valueOf(itemDTO.getPrice());
        String qty = String.valueOf(itemDTO.getQty());

        if (itemCode == null || !itemCode.matches("^I00-[0-9]{3}$")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid item code format!. Please use the pattern 'I00-001'."));
            return;
        }
        if (itemName == null || !itemName.matches("[A-Za-z ]+")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid item name format!."));
            return;
        }
        if (price == null || !price.matches("[1-9]\\d*(\\.\\d+)?")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid item price format!. Please use the pattern '100000.00'."));
            return;
        }
        if (!qty.matches("[1-9]\\d*")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid item qty format!. Please use the pattern '1000.00'."));
            return;
        }

        try (Connection connection = dataSource.getConnection()) {
            boolean saveItem = itemBO.saveItem(connection, itemDTO);
            if (saveItem) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson("Item added successfully!."));
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson("Item added failed!."));
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            resp.getWriter().write(jsonb.toJson("Duplicate ID!. Item not added!."));
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(jsonb.toJson("Database error!"));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("PUT Method");
        Jsonb jsonb = JsonbBuilder.create();
        ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);

        String itemName = itemDTO.getItemName();
        String price = String.valueOf(itemDTO.getPrice());
        String qty = String.valueOf(itemDTO.getQty());

        if (itemName == null || !itemName.matches("[A-Za-z ]+")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid item name format!."));
            return;
        }
        if (price == null || !price.matches("[1-9]\\d*(\\.\\d+)?")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid item price format!. Please use the pattern '100000.00'."));
            return;
        }
        if (!qty.matches("[1-9]\\d*")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid item qty format!. Please use the pattern '1000.00'."));
            return;
        }

        try (Connection connection = dataSource.getConnection()) {
            boolean updateItem = itemBO.updateItem(connection, itemDTO);
            if (updateItem) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson("Item updated successfully!."));
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson("Item updated failed!."));
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(jsonb.toJson("Database error!"));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemCode = req.getParameter("itemCode");
        Jsonb jsonb = JsonbBuilder.create();

        if (itemCode == null || !itemCode.matches("^I00-[0-9]{3}$")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson("Invalid item code format!. Please use the pattern 'I00-001'."));
            return;
        }

        try (Connection connection = dataSource.getConnection()) {
            boolean deleteItem = itemBO.deleteItem(connection, itemCode);
            if (deleteItem) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson("Item deleted successfully!."));
            }else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson("Item deleted failed!."));
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(jsonb.toJson("Database error!"));
            throw new RuntimeException(e);
        }
    }
}
