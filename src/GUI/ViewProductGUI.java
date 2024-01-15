package GUI;

import CLI.Clothing;
import CLI.Electronics;
import CLI.Product;
import CLI.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import static CLI.Main.westminsterShoppingManager;
import static CLI.WestminsterShoppingManager.productsList;

public class ViewProductGUI extends JFrame implements ActionListener {
    JButton shoppingCart;
    JLabel homePageText;
    JComboBox dropdownMenu;
    JTable detailsTable;
    ArrayList<JLabel> detailsLabelList;
    JButton addToShoppingCart;

    public ViewProductGUI(User user) {

        setTitle("Westminster Shopping Centre");

        String options[] = {"All", "Clothing", "Electronics"};
        dropdownMenu = new JComboBox<>(options);
        dropdownMenu.setBounds(270,40,125,25);
        dropdownMenu.setFont(new Font("Helvetica",Font.PLAIN, 16));
        add(dropdownMenu, BorderLayout.NORTH);

        homePageText = new JLabel("Select Product Category");
        homePageText.setBounds(60, 40, 200, 25);
        homePageText.setFont(new Font("Helvetica",Font.PLAIN, 16));
        add(homePageText, BorderLayout.WEST);

        shoppingCart = new JButton("Shopping Cart");
        shoppingCart.setBounds(620,10,150,40);
        shoppingCart.setFont(new Font("Helvetica",Font.PLAIN, 16));
        add(shoppingCart, BorderLayout.EAST);

        addToShoppingCart = new JButton("Add to Shopping Cart");
        addToShoppingCart.setBounds(300, 720, 200, 30);
        addToShoppingCart.setFont(new Font("Helvetica",Font.PLAIN, 16));
        add(addToShoppingCart, BorderLayout.SOUTH);

        detailsTable = new JTable();  //creating a table
        ViewProductTableModel tableModel = new ViewProductTableModel(productsList);   //passing the products list to the table model
        detailsTable.setBounds(30, 150, 720, 300);
        detailsTable.setModel(tableModel);  //setting the table model to the table
        detailsTable.setAutoCreateRowSorter(true); //Sorting the rows
        detailsTable.setFont(new Font("Helvetica",Font.PLAIN, 15)); //setting the font of the table
        detailsTable.getTableHeader().setFont(new Font("Helvetica",Font.BOLD, 16)); //setting the font of the table header

        //setting the font of the table header
        MyTableCellRenderer renderer = new MyTableCellRenderer();
        for (int i = 0; i < detailsTable.getColumnCount(); i++) {
            detailsTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        //Centering the text in the table
        renderer.setHorizontalAlignment(JLabel.CENTER);
        detailsTable.setDefaultRenderer(Object.class, renderer);

        //adding the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(detailsTable);
        scrollPane.setBounds(30, 150, 720, 300);
        add(scrollPane, BorderLayout.CENTER);


        detailsTable.setRowHeight(50);
        detailsTable.getColumnModel().getColumn(4).setPreferredWidth(200); //setting the width of the last column
        detailsTable.setFillsViewportHeight(true);
        detailsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //to select only one row at a time
        detailsTable.setDragEnabled(false); //to disable dragging
        detailsTable.getTableHeader().setReorderingAllowed(false); //to disable reordering


        //adding an event listener to the table if the user selects a row
        detailsTable.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        int selectedRow = detailsTable.getSelectedRow();
                        if (selectedRow != -1) {
                            String productID = (String) detailsTable.getValueAt(selectedRow, 0);
                            Product selectedProduct = null;
                            for (Product product : productsList) {
                                if (product.getProductID().equals(productID)) {
                                    selectedProduct = product;
                                    break;
                                }
                            }
                            detailsLabelList.get(1).setText("Product ID: " + selectedProduct.getProductID());
                            detailsLabelList.get(2).setText("Category: Clothing");
                            detailsLabelList.get(3).setText("Name: " + selectedProduct.getProductName());
                            if (selectedProduct instanceof Clothing) {
                                detailsLabelList.get(4).setText("Size: " + ((Clothing) selectedProduct).getSize());
                                detailsLabelList.get(5).setText("Colour: " + ((Clothing) selectedProduct).getColour());
                            } else {
                                detailsLabelList.get(4).setText("Brand: " + ((Electronics) selectedProduct).getBrand());
                                detailsLabelList.get(5).setText("Warranty Period: " + ((Electronics) selectedProduct).getWarrantyPeriod() + " weeks");
                            }
                            detailsLabelList.get(6).setText("No of Available Items: " + selectedProduct.getNoOfAvailableItems());
                        }
                    }
                }
        );


        //creating a list of labels to display the details of the selected product
        detailsLabelList = new ArrayList<JLabel>();
        for (int i = 0; i < 7; i++) {
            detailsLabelList.add(new JLabel());
        }

        //setting the font and bounds of the labels
        detailsLabelList.get(0).setText("Selected Product - Details");
        detailsLabelList.get(0).setFont(new Font("Helvetica",Font.BOLD, 20));
        detailsLabelList.get(0).setBounds(50, 470, 400, 20);
        add(detailsLabelList.get(0));
        detailsLabelList.get(1).setText("Product ID: No product selected");
        detailsLabelList.get(2).setText("Category: No product selected");
        detailsLabelList.get(3).setText("Name: No product selected");
        detailsLabelList.get(4).setText("Size: No product selected");
        detailsLabelList.get(5).setText("Colour: No product selected");
        detailsLabelList.get(6).setText("No of Available Items: No product selected");


        for (int i = 1; i < 7; i++) {
            detailsLabelList.get(i).setFont(new Font("Helvetica",Font.PLAIN, 16));
            detailsLabelList.get(i).setBounds(50, 470 + (i * 30), 300, 20);
            add(detailsLabelList.get(i));
        }

        //adding an event listener to the dropdown menu
        dropdownMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String category = dropdownMenu.getSelectedItem().toString();
               tableModel.filterByCategory(dropdownMenu.getSelectedItem().toString());


            }
        });

        //adding an event listener to the add to shopping cart button
        ArrayList<Product> selectedProductsList = new ArrayList<>();
        addToShoppingCart.addActionListener(e -> {
            int selectedRow = detailsTable.getSelectedRow();
            if (selectedRow != -1) {
                String productID = (String) detailsTable.getValueAt(selectedRow, 0);
                Product selectedProduct;
                for (Product product : productsList) {
                    if (product.getProductID().equals(productID)) {
                        if(product.getNoOfAvailableItems() == 0){
                            JFrame jf = new JFrame();
                            jf.setAlwaysOnTop(true);
                            JOptionPane.showMessageDialog(jf,"No more items available", "Error", JOptionPane.ERROR_MESSAGE);
                            break;
                        }
                        selectedProduct = product;
                        product.setNoOfAvailableItems(product.getNoOfAvailableItems() - 1);
                        detailsLabelList.get(6).setText("No of Available Items: " + selectedProduct.getNoOfAvailableItems());
                        selectedProductsList.add(selectedProduct);
                        break;
                    }
                }

            }else{
                JFrame jf=new JFrame();
                jf.setAlwaysOnTop(true);
                JOptionPane.showMessageDialog(jf,"Please select a product", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        //adding an event listener to the shopping cart button
        shoppingCart.addActionListener(e -> {
            ArrayList<Product> duplicateProducts = new ArrayList<>(selectedProductsList);


            HashMap<Product, Integer> mappedProducts = new HashMap<>();

            for (int i = 0; i < selectedProductsList.size(); i++) {;

                if (mappedProducts.containsKey(selectedProductsList.get(i))) {
                    mappedProducts.put(selectedProductsList.get(i), mappedProducts.get(selectedProductsList.get(i)) + 1);
                } else {
                    mappedProducts.put(selectedProductsList.get(i), 1);
                }
            }

            ShoppingCartGUI shoppingCart= new ShoppingCartGUI(mappedProducts, user);
            System.out.println("Shopping cart is getting opened \nSelected products list size: " + selectedProductsList.size());
//            dispose();
        });

        //Adding an event listener to the window to set the first time user to false
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                user.setFirstTimeUser(false);
                westminsterShoppingManager.saveUserData();
            }
            });

        setLayout(new BorderLayout(20,15));
        setVisible(true);
        setSize(800, 800);

        //To open the window on top of other screen
        setAlwaysOnTop(true);

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        //Preventing from resizing the screen
        setResizable(false);

        //To open the window in the middle of the screen
        setLocationRelativeTo(null);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
    class MyTableCellRenderer extends JLabel implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            setText(value.toString());
            setFont(new Font("Helvetica",Font.PLAIN, 15));
            setBorder(BorderFactory.createEmptyBorder());

            if (productsList.get(row).getNoOfAvailableItems() < 3) {
                setForeground(Color.WHITE);
                setBackground(Color.RED);
            }else{
                if (isSelected){
                    setForeground(Color.BLACK);
                    setBackground(Color.LIGHT_GRAY);
                }else {
                    setForeground(Color.BLACK);
                    setBackground(Color.WHITE);
                }
            }
            setOpaque(true);
            return this;
        }
    }
}
