package lk.ijse.gdse.backend.bo.custom.impl;

import lk.ijse.gdse.backend.bo.custom.PlaceOrderBO;
import lk.ijse.gdse.backend.dao.DAOFactory;
import lk.ijse.gdse.backend.dao.custom.ItemDAO;
import lk.ijse.gdse.backend.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse.backend.dao.custom.PlaceOrderDAO;
import lk.ijse.gdse.backend.dto.CustomerDTO;
import lk.ijse.gdse.backend.dto.ItemDTO;
import lk.ijse.gdse.backend.dto.OrderDetailDTO;
import lk.ijse.gdse.backend.dto.PlaceOrderDTO;
import lk.ijse.gdse.backend.entity.Item;
import lk.ijse.gdse.backend.entity.OrderDetail;
import lk.ijse.gdse.backend.entity.PlaceOrder;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderBOImpl implements PlaceOrderBO {

    PlaceOrderDAO placeOrderDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERS_DAO);
    ItemDAO itemDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM_DAO);
    OrderDetailsDAO orderDetailsDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS_DAO);
    @Override
    public boolean purchaseOrder(Connection connection, PlaceOrderDTO placeOrderDTO) throws SQLException {

        connection.setAutoCommit(false);
        PlaceOrder placeOrder = new PlaceOrder(placeOrderDTO.getOrderId(),placeOrderDTO.getOrderDate(),placeOrderDTO.getCustId());

        boolean orderSaved = placeOrderDAO.save(connection, placeOrder);
        if (!orderSaved){
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        for (OrderDetailDTO orderDetail : placeOrderDTO.getOrderDetailList()) {
            boolean orderDetailSaved = orderDetailsDAO.save(connection, new OrderDetail(placeOrderDTO.getOrderId(), orderDetail.getItemCode(), orderDetail.getQty(), orderDetail.getUnitPrice()));
            if (!orderDetailSaved) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            Item item = itemDAO.search(connection, orderDetail.getItemCode());

            item.setQty(item.getQty()-orderDetail.getQty());

            boolean isUpdated = itemDAO.update(connection, item);

            if (!isUpdated) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }

        connection.commit();
        connection.setAutoCommit(true);

        return true;
    }

    @Override
    public CustomerDTO searchCustomer(Connection connection, String id) {
        return null;
    }

    @Override
    public ItemDTO searchItem(Connection connection, String code) {
        return null;
    }

    @Override
    public String generateNewOrderID(Connection connection) throws SQLException {
        return placeOrderDAO.generateNextId(connection);
    }
}
