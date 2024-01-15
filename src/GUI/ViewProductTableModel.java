package GUI;

import CLI.Clothing;
import CLI.Electronics;
import CLI.Product;

import javax.swing.table.AbstractTableModel;

import java.util.ArrayList;

import static CLI.WestminsterShoppingManager.productsList;

public class ViewProductTableModel extends AbstractTableModel {
    String[] columnNames = {"Product ID", "Name", "Category", "Price(£)", "Info"};
    ArrayList<Product> productsListFromManager;

    public ViewProductTableModel(ArrayList<Product> listOfProducts){
        productsListFromManager = new ArrayList<>(listOfProducts);
    }


   //set the table column names
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return productsListFromManager.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0){
            return productsListFromManager.get(rowIndex).getProductID();
        }else if (columnIndex == 1) {
            return productsListFromManager.get(rowIndex).getProductName();
        }else if (columnIndex == 2){
            if(productsListFromManager.get(rowIndex) instanceof Clothing){
                return "Clothing";
            } else{
                return "Electronics";
            }
        }else if (columnIndex == 3) {
            return productsListFromManager.get(rowIndex).getPrice();
        }else{
            if(productsListFromManager.get(rowIndex) instanceof Clothing){
                return (((Clothing) productsListFromManager.get(rowIndex)).getColour() + ", " + ((Clothing) productsListFromManager.get(rowIndex)).getSize());
            }else{
                return (((Electronics)productsListFromManager.get(rowIndex)).getBrand() + ", " + ((Electronics)productsListFromManager.get(rowIndex)).getWarrantyPeriod()+" weeks warranty");
            }
        }
    }

    public void updateData(ArrayList<Product> newData) {
        this.productsListFromManager = newData;
        this.fireTableDataChanged();
    }

    public void filterByCategory(String category) {
        ArrayList<Product> filteredList = new ArrayList<>();

        for (int i = 0; i < productsList.size(); i++) {
            if (category.equals("All")) {
                filteredList.add(productsList.get(i));
            }else if(category.equals("Clothing")){
                if(productsList.get(i) instanceof Clothing){
                    filteredList.add(productsList.get(i));
                }
            }else if(category.equals("Electronics")){
                if(productsList.get(i) instanceof Electronics){
                    filteredList.add(productsList.get(i));
                }
            }
        }
        updateData(filteredList);
    }
}
