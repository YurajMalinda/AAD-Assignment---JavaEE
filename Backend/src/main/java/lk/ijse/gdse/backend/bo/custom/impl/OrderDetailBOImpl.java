package lk.ijse.gdse.backend.bo.custom.impl;

import lk.ijse.gdse.backend.bo.custom.OrderDetailsBO;
import lk.ijse.gdse.backend.dao.DAOFactory;
import lk.ijse.gdse.backend.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse.backend.dto.OrderDetailDTO;
import lk.ijse.gdse.backend.entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailBOImpl implements OrderDetailsBO {

    OrderDetailsDAO orderDetailsDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS_DAO);

    @Override
    public ArrayList<OrderDetailDTO> getAllOrderDetail(Connection connection) throws SQLException {
        ArrayList<OrderDetail> orderDetails = orderDetailsDAO.getAll(connection);
        ArrayList<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();

        for (OrderDetail o : orderDetails) {
            orderDetailDTOS.add(new OrderDetailDTO(o.getOrderId(), o.getItemCode(), o.getQty(), o.getUnitPrice()));
        }
        return orderDetailDTOS;
    }

    @Override
    public OrderDetailDTO searchOrder(Connection connection, String orderID) throws SQLException {
        OrderDetail orderDetail = orderDetailsDAO.search(connection, orderID);
        if (orderDetail != null){
            return new OrderDetailDTO(orderDetail.getOrderId(), orderDetail.getItemCode(), orderDetail.getQty(), orderDetail.getUnitPrice());
        }
        return null;
    }
}
