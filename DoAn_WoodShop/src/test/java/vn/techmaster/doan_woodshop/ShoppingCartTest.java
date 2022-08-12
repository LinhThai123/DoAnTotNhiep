package vn.techmaster.doan_woodshop;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ShoppingCartTest {

//    @Autowired private CartItemRepository cartRepo ;

//    @Autowired private TestEntityManager entityManager ;

//    @Test
//    public void testAddOneCartItem () {
//       Product product =  entityManager.find(Product.class , "JH6DBFWy5JTk") ;
//       User user =  entityManager.find(User.class , 1) ;
//
//        CartItem newItem = new CartItem() ;
//        newItem.setUser(user);
//        newItem.setProduct(product);
//        newItem.setQuantity(1);
//
//        CartItem saveCartItem =  cartRepo.save(newItem) ;
//        assertTrue( saveCartItem.getId() > 0);
//    }
//    @Test
//    public void testGetCartItemByUser () {
//        User user = new User() ;
//        user.setId(1);
//        List<CartItem> cartItemList = cartRepo.findByUser(user) ;
//        assertEquals(1 , cartItemList.size());
//    }
}
