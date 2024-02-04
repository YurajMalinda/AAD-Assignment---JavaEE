package lk.ijse.gdse.backend.dao;

import lk.ijse.gdse.backend.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.gdse.backend.dao.custom.impl.ItemDAOImpl;
import lk.ijse.gdse.backend.dao.custom.impl.OrderDAOImpl;
import lk.ijse.gdse.backend.dao.custom.impl.OrderDetailsDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {

    }

    public static DAOFactory getDaoFactory(){
        return (daoFactory == null)? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes{
        CUSTOMER_DAO,
        ITEM_DAO,
        ORDERS_DAO,
        ORDER_DETAILS_DAO
    }

    public <T extends SuperDAO> T getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case CUSTOMER_DAO:
                return (T) new CustomerDAOImpl();
            case ITEM_DAO:
                return (T) new ItemDAOImpl();
            case ORDERS_DAO:
                return (T) new OrderDAOImpl();
            case ORDER_DETAILS_DAO:
                return (T) new OrderDetailsDAOImpl();
            default:
                return null;
        }
    }
}
