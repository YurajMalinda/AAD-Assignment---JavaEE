package lk.ijse.gdse.backend.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.gdse.backend.bo.BOFactory;
import lk.ijse.gdse.backend.bo.custom.PlaceOrderBO;
import lk.ijse.gdse.backend.dto.PlaceOrderDTO;

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
import java.util.Objects;

@WebServlet (name = "placeOrderServlet", urlPatterns = "/placeOrder")
public class PlaceOrderServlet extends HttpServlet {

    PlaceOrderBO placeOrderBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PLACE_ORDER_BO);
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
        resp.setContentType("application/json");
        Jsonb jsonb = JsonbBuilder.create();

        try (Connection connection = dataSource.getConnection()) {
            if (Objects.equals(option, "generateId")) {
                String orderId = placeOrderBO.generateNewOrderID(connection);
                jsonb.toJson(orderId, resp.getWriter());
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
        PlaceOrderDTO placeOrderDTO = jsonb.fromJson(req.getReader(), PlaceOrderDTO.class);
        System.out.println(placeOrderDTO);

        try (Connection connection = dataSource.getConnection()) {
            boolean saveOrder = placeOrderBO.purchaseOrder(connection, placeOrderDTO);
            if (saveOrder) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson("Order saved successfully!."));
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.setContentType("application/json");
                resp.getWriter().write(jsonb.toJson("Order saved failed!."));
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(jsonb.toJson("Database error!"));
        }
    }
}
