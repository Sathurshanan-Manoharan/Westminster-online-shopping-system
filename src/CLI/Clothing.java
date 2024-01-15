package CLI;

public class Clothing extends Product {
    private String size;
    private String colour;

    public Clothing(String productID, String productName, int noOfAvailableItems, double price, String size, String colour) {
        super(productID, productName, noOfAvailableItems, price);
        this.size = size;
        this.colour = colour;
    }

    public String getColour() {
        return colour;
    }

    public String getSize() {
        return size;
    }

    @Override
    public String toString() {
        return  "\nProduct ID               : " + productID +
                "\nProduct Name             : " + productName +
                "\nNo of available items    : " + noOfAvailableItems +
                "\nPrice                    : " + price +
                "\nCloth Size               : " + size +
                "\nCloth Colour             : " + colour;
    }
}

