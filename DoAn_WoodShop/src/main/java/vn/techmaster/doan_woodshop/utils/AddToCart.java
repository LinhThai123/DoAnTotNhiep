package vn.techmaster.doan_woodshop.utils;

import vn.techmaster.doan_woodshop.entity.CartProduct;
import vn.techmaster.doan_woodshop.model.dto.CartProductDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddToCart {

    public static int coutCart(Map<String , CartProductDTO> cart) {
        int q = 0 ;
        if(cart != null) {
            for (CartProductDTO c : cart.values()) {
                q += c.getQuantity();
            }
        }
        return q ;
    }
//    public static CartProduct  cartStats (List<CartProduct> cart) {
//        Long sum = 0l ;
//        int quantity = 0 ;
//        if(cart != null) {
//            for (CartProductDTO c: cart.values()) {
//                quantity += c.getQuantity();
//                sum += c.getQuantity() * c.getPrice() ;
//            }
//        }
//        Map<String , String> kp = new HashMap<>() ;
//        kp.put("counter" , String.valueOf(quantity)) ;
//        kp.put("amount" , String.valueOf(sum)) ;
//
//        return kp;
//
//    }
}
