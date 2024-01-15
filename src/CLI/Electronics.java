package CLI;

public class Electronics extends Product {
    private String brand;
    private int warrantyPeriod;

    public Electronics(String productID, String productName, int noOfAvailableItems, double price, String brand, int warrantyPeriod) {
        super(productID, productName, noOfAvailableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public String getBrand() {
        return brand;
    }

    @Override
    public String toString() {
        return  "\nProduct ID              : " + productID +
                "\nProduct Name            : " + productName +
                "\nNo of available items   : " + noOfAvailableItems +
                "\nPrice                   : " + price +
                "\nBrand                   : " + brand +
                "\nWarranty Period         : " + warrantyPeriod + " weeks";
    }
}
