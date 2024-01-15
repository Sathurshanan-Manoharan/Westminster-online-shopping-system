package CLI;

import java.io.Serializable;
import java.util.ArrayList;

public class ShoppingCart implements Serializable {
    private ArrayList<Product> listOfProducts;

    public void addItem(Product product){
        listOfProducts.add(product);
    }

    public void removeItem(Product product){
        listOfProducts.remove(product);
    }
    
    public double totalCost(){
        double totalPrice = 0;

        for (Product listOfProduct : listOfProducts) {
            totalPrice += listOfProduct.price;
        }
        return totalPrice;
    }

    public void setListOfProducts(ArrayList<Product> listOfProducts) {
        this.listOfProducts = listOfProducts;
    }
}
