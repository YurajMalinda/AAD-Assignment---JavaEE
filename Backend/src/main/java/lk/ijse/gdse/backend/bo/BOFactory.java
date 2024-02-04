package lk.ijse.gdse.backend.bo;

import lk.ijse.gdse.backend.bo.custom.impl.CustomerBOImpl;
import lk.ijse.gdse.backend.bo.custom.impl.ItemBOImpl;
import lk.ijse.gdse.backend.bo.custom.impl.OrderDetailBOImpl;
import lk.ijse.gdse.backend.bo.custom.impl.PlaceOrderBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {

    }

    public static BOFactory getBoFactory(){
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER_BO,
        ITEM_BO,
        ORDER_DETAILS_BO,
        PLACE_ORDER_BO
    }

    public <T extends SuperBO> T getBO(BOTypes boTypes){
        switch (boTypes) {
            case CUSTOMER_BO:
                return (T) new CustomerBOImpl();
            case ITEM_BO:
                return (T) new ItemBOImpl();
            case ORDER_DETAILS_BO:
                return (T) new OrderDetailBOImpl();
            case PLACE_ORDER_BO:
                return (T) new PlaceOrderBOImpl();
            default:
                return null;
        }
    }
}
