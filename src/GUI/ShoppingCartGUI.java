package GUI;

import CLI.Product;
import CLI.User;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.HashMap;

import static CLI.WestminsterShoppingManager.userList;

public class ShoppingCartGUI extends JFrame {
    HashMap<Product, Integer> shoppingCartList;
    JTable shoppingCartTable;
    JScrollPane scrollPane;
    public ShoppingCartGUI(HashMap<Product, Integer> shoppingCartList, User user){
        this.shoppingCartList = shoppingCartList;

        System.out.println("Shopping cart list size: " + shoppingCartList.size());

        setTitle("Shopping Cart");

        System.out.println("Shopping cart list size: " + shoppingCartList.size());

        shoppingCartTable = new JTable();
        ShoppingCartTableModel shoppingCartTableModel = new ShoppingCartTableModel(shoppingCartList, user);
        shoppingCartTable.setModel(shoppingCartTableModel);

        shoppingCartTable.setBounds(30, 20, 720, 250);
        shoppingCartTable.setFont(new Font("Helvetica",Font.PLAIN, 15));
        shoppingCartTable.getTableHeader().setFont(new Font("Helvetica",Font.BOLD, 16));
        shoppingCartTable.setRowHeight(100);
        add(shoppingCartTable, BorderLayout.NORTH);

        scrollPane = new JScrollPane(shoppingCartTable);
        scrollPane.setBounds(30, 20, 720, 250);
        add(scrollPane);

        shoppingCartTable.setRowHeight(70);
        shoppingCartTable.setFillsViewportHeight(true);
        shoppingCartTable.setShowGrid(true);

        //Centering the text in the table
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        shoppingCartTable.setDefaultRenderer(Object.class, renderer);
        //Need to break the lines in the table for the 1st column
        shoppingCartTable.getColumnModel().getColumn(0).setCellRenderer(new MultiLineCellRenderer());



        JLabel total = new JLabel("Total", SwingConstants.RIGHT);
        total.setFont(new Font("Helvetica",Font.PLAIN, 16));
        total.setBounds(50, 300, 500, 30);
        add(total, BorderLayout.SOUTH);

        JLabel firstPurchaseDiscount = new JLabel("First Purchase Discount (10%)", SwingConstants.RIGHT);
        firstPurchaseDiscount.setFont(new Font("Helvetica",Font.PLAIN, 16));
        firstPurchaseDiscount.setBounds(50, 330, 500, 30);
        add(firstPurchaseDiscount, BorderLayout.SOUTH);

        JLabel threeProductDiscount = new JLabel("Three Items in same Category Discount (20%)", SwingConstants.RIGHT);
        threeProductDiscount.setFont(new Font("Helvetica",Font.PLAIN, 16));
        threeProductDiscount.setBounds(50, 360, 500, 30);
        add(threeProductDiscount, BorderLayout.SOUTH);

        JLabel finalTotal = new JLabel("Final Total", SwingConstants.RIGHT);
        finalTotal.setFont(new Font("Helvetica",Font.BOLD, 16));
        finalTotal.setBounds(50, 400, 500, 30);
        add(finalTotal, BorderLayout.SOUTH);


        //Displaying the information
        JLabel totalDisplay = new JLabel("0 £", SwingConstants.LEFT);
        totalDisplay.setFont(new Font("Helvetica",Font.PLAIN, 16));
        totalDisplay.setBounds(600, 300, 150, 30);
        add(totalDisplay, BorderLayout.SOUTH);

        JLabel firstPurchaseDiscountDisplay = new JLabel("0 £", SwingConstants.LEFT);
        firstPurchaseDiscountDisplay.setFont(new Font("Helvetica",Font.PLAIN, 16));
        firstPurchaseDiscountDisplay.setBounds(600, 330, 150, 30);
        add(firstPurchaseDiscountDisplay, BorderLayout.SOUTH);

        JLabel threeProductDiscountDisplay = new JLabel("0 £", SwingConstants.LEFT);
        threeProductDiscountDisplay.setFont(new Font("Helvetica",Font.PLAIN, 16));
        threeProductDiscountDisplay.setBounds(600, 360, 150, 30);
        add(threeProductDiscountDisplay, BorderLayout.SOUTH);

        JLabel finalTotalDisplay = new JLabel("0 £", SwingConstants.LEFT);
        finalTotalDisplay.setFont(new Font("Helvetica",Font.BOLD, 16));
        finalTotalDisplay.setBounds(600, 400, 150, 30);
        add(finalTotalDisplay, BorderLayout.SOUTH);


        //Calculating the totalCart value
        double totalCartValue = 0;
        for (int i = 0; i < shoppingCartTable.getRowCount(); i++) {
            double totalValue = (double) shoppingCartTable.getValueAt(i, 2);
             totalCartValue += totalValue;
        }
        totalDisplay.setText(totalCartValue + " £");

        //Calculating the first purchase discount
        double firstPurchaseDiscountValue = 0;
        if(user.isFirstTimeUser()){
            firstPurchaseDiscountValue = totalCartValue * 0.1;
            firstPurchaseDiscountDisplay.setText("-" + firstPurchaseDiscountValue + " £");
        }

        //Calculating the three product discount
        double threeProductDiscountValue = 0;
        boolean isEligibleForDiscount = false;
        for (int i = 0; i < shoppingCartTable.getRowCount(); i++) {
            if ((int)shoppingCartTable.getValueAt(i,1) >= 3){
                isEligibleForDiscount = true;
            }
        }
        if (isEligibleForDiscount){
            threeProductDiscountValue = totalCartValue * 0.2;
            threeProductDiscountDisplay.setText("-" + threeProductDiscountValue + " £");
        }

        //Calculating the final total
        double finalTotalValue = totalCartValue - threeProductDiscountValue - firstPurchaseDiscountValue;
        finalTotalDisplay.setText(finalTotalValue + " £");


        setLayout(new BorderLayout(20,15));
        setVisible(true);
        setSize(800, 500);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(false); //Preventing from resizing the screen
        setLocationRelativeTo(null);//To open the window in the middle of the screen
    }

    private class MultiLineCellRenderer extends JScrollPane implements TableCellRenderer {

        private final JTextArea textArea;

        public MultiLineCellRenderer() {
            textArea = new JTextArea();
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            setViewportView(textArea);
            setBorder(null);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value != null) {
                textArea.setText(value.toString());
                textArea.setFont(new Font("Helvetica",Font.PLAIN, 15));
                textArea.setBorder(BorderFactory.createEmptyBorder());
            } else {
                textArea.setText("");
            }

            // Adjust the row height based on the text content
            int textHeight = textArea.getPreferredSize().height;
            table.setRowHeight(row, Math.max(table.getRowHeight(), textHeight));

            return this;
        }
    }
}
