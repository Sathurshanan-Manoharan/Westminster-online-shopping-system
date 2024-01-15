package CLI;

import java.io.Serializable;


public abstract class Product implements Serializable, Comparable<Product> {
    String productID;
    String productName;
    int noOfAvailableItems;
    double price;

    public Product(String productID, String productName, int noOfAvailableItems, double price) {
        this.productID = productID;
        this.productName = productName;
        this.noOfAvailableItems = noOfAvailableItems;
        this.price = price;
    }

    public String getProductID() {
        return productID;
    }


    public String getProductName() {
        return productName;
    }


    public int getNoOfAvailableItems() {
        return noOfAvailableItems;
    }

    public void setNoOfAvailableItems(int noOfAvailableItems) {
        this.noOfAvailableItems = noOfAvailableItems;
    }

    public double getPrice() {
        return price;
    }


    @Override
    public String toString() {
        return "productID                    : " + productID +
                "\n\tproductName             : " + productName +
                "\n\tNo of Available Items   : " + noOfAvailableItems +
                "\n\tprice                   : " + price;
    }

    @Override
    public int compareTo(Product product) {
        return this.productID.compareTo(product.productID);
    }
}
