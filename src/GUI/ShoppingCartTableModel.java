package GUI;

import CLI.Clothing;
import CLI.Electronics;
import CLI.Product;
import CLI.User;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingCartTableModel extends AbstractTableModel {

    String[] columnNames = {"Product", "Quantity", "Price(£)"};
    HashMap<Product,Integer> shoppingCartList;
    User user;
    public ShoppingCartTableModel(HashMap<Product,Integer> shoppingCartList, User user){

        this.shoppingCartList = new HashMap<Product, Integer>(shoppingCartList);
        this.user = user;
    }


    //set the table column names

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return shoppingCartList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ArrayList<Product> listOfProducts = new ArrayList<>(shoppingCartList.keySet());//Mapping the keys to an arraylist
        user.getShoppingCartArrayList().setListOfProducts(listOfProducts);

        if (columnIndex ==0) {
            if (listOfProducts.get(rowIndex) instanceof Clothing) {
                return (listOfProducts.get(rowIndex).getProductID() + "\n" +
                        ((Clothing) listOfProducts.get(rowIndex)).getProductName() + "\n" +
                        ((Clothing) listOfProducts.get(rowIndex)).getSize() + ", " +
                        ((Clothing) listOfProducts.get(rowIndex)).getColour());
            } else {
                return (listOfProducts.get(rowIndex).getProductID() + "\n" +
                        ((Electronics) listOfProducts.get(rowIndex)).getProductName() + "\n" +
                        ((Electronics) listOfProducts.get(rowIndex)).getBrand() + ", " +
                        ((Electronics) listOfProducts.get(rowIndex)).getWarrantyPeriod() + " weeks");
            }
        }else if (columnIndex == 1){
            return shoppingCartList.values().toArray()[rowIndex];
        }else{
            return calculateTotal((int) shoppingCartList.values().toArray()[rowIndex], listOfProducts.get(rowIndex).getPrice());
        }
    }

public double calculateTotal(int noOfItems, double price){
        double total = noOfItems * price;
        return total;
}

}
