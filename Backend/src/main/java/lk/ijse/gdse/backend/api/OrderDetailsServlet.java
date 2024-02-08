package lk.ijse.gdse.backend.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.gdse.backend.bo.BOFactory;
import lk.ijse.gdse.backend.bo.custom.OrderDetailsBO;
import lk.ijse.gdse.backend.dto.ItemDTO;
import lk.ijse.gdse.backend.dto.OrderDetailDTO;

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
import java.util.ArrayList;
import java.util.Objects;

@WebServlet (name = "orderDetailsServlet", urlPatterns = "/orderDetails")
public class OrderDetailsServlet extends HttpServlet {
    OrderDetailsBO orderDetailsBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER_DETAILS_BO);
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

        String orderID = req.getParameter("orderId");
        System.out.println(orderID);

        resp.setContentType("application/json");
        Jsonb jsonb = JsonbBuilder.create();

        try(Connection connection = dataSource.getConnection()){
            if (Objects.equals(option, "getAll")) {
                ArrayList<OrderDetailDTO> allOrders = orderDetailsBO.getAllOrderDetail(connection);
                jsonb.toJson(allOrders, resp.getWriter());
            }else if (Objects.equals(option, "search")) {
                OrderDetailDTO orderDetailDTO = orderDetailsBO.searchOrder(connection, orderID);
                jsonb.toJson(orderDetailDTO, resp.getWriter());
            }
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
}
