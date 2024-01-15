package CLI;

import GUI.ViewProductGUI;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class WestminsterShoppingManager implements ShoppingManager, Serializable {

    //Creating the product list array
    public static ArrayList<Product> productsList = new ArrayList<>(50);
    public static ArrayList<User> userList = new ArrayList<>(50);
    public static int MAX_PRODUCT_COUNT = 50;
    public static int productCount = 0;

    /**
     * This method is used to add a new product to the inventory
     *
     */
    public void addNewProduct(){

        //Checking whether the inventory is full
        if(productCount == MAX_PRODUCT_COUNT){
            System.out.println("\nNo more products can be added to the inventory!");
            return;
        }

        Scanner input = new Scanner(System.in);
        int productChoice = validateProductChoice("\nProducts that are available to add :- \n\n\t1. Clothing \n\t2. Electronics \n\nChoose the product you want to add : ", "\nInvalid product choice! Choose Either 1 or 2");

        //Getting the product ID
        String productID = validateProductID("Enter the product ID : ");

        //Getting the product name
        System.out.print("Enter the product Name : ");
        String productName = input.nextLine();

        //Getting the number of available items
        int noOfAvailableItems = validateNoOfAvailableItems();

        //Getting the product price
        double price = validatePrice();

        if (productChoice == 1){
            System.out.print("Enter the product size : ");
            String size = input.next();

            System.out.print("Enter the product colour : ");
            String colour = input.next();

            productsList.add(new Clothing(productID, productName, noOfAvailableItems, price, size, colour));
            productCount = productsList.size();
        }else if(productChoice == 2){
            System.out.print("Enter the product brand :");
            String brand = input.next();

            int warrantyPeriod = validateWarrantyPeriod();

            productsList.add(new Electronics(productID,productName,noOfAvailableItems,price,brand,warrantyPeriod));
            productCount = productsList.size();
        }
    }

    /**
     * This method is used to delete a product from the inventory
     *
     */
    public void deleteProduct(){
        //TODO- Need to display the total number of products that are there in the inventory
        Scanner input = new Scanner(System.in);

        //Displaying the product IDs of the products
        if (productsList.isEmpty()){
            System.out.print("\nNo products are there in the inventory to delete!");
            return;
        }else{
            System.out.println("\nProduct IDs of the products that are available to delete :-\n");
            for (int i = 0; i < productsList.size(); i++) {
                System.out.println("\t"+(i+1) + ". Product ID - " + productsList.get(i).productID + "\tProduct Name - " + productsList.get(i).productName);
            }
        }

        System.out.print("\nEnter the ID of the product you want to delete : ");
        String productID = input.next();

        for (int i = 0; i < productsList.size(); i++) {
            if (productID.equals(productsList.get(i).productID)){
                String productType;
                if (productsList.get(i) instanceof Clothing){
                    productType = "Clothing";
                }else{
                    productType = "Electronics";
                }
                System.out.println("\n"+productType + " product ("+productsList.get(i).getProductName()+") was successfully removed!");

                productsList.remove(i);
                productCount = productsList.size();
                System.out.println("\nNo of products in the inventory : " + productCount);
                break;
            }else if(i == productsList.size()-1){
                System.out.println("\n+++++Error!++++++\nProduct ID does not exist!");
                i = 0;
                System.out.print("\nRenter the ID of the product you want to delete : ");
                productID = input.next();
            }
        }
    }

    /**
     * This method is used to display the products that are there in the inventory in alphabetical order
     *
     */
    public void displayProducts(){
        //Copying the products list to a new array list to sort
        ArrayList<Product> sortedProductsList = new ArrayList<>(productsList);

        //Sorting the products list
        Comparator<Product> productComparator = Comparator.comparing(Product::getProductID);
        sortedProductsList.sort(productComparator);


        if (productsList.isEmpty()){
            System.out.println("\nNo products are there in the inventory to display!\n");
        }else{
            System.out.println("\nProducts that are there in the inventory :-\n");
            for (int i = 0; i < sortedProductsList.size(); i++) {
                if (sortedProductsList.get(i) instanceof Clothing){
                    System.out.println((i+1) + ". Clothing product");
                }else{
                    System.out.println((i+1) + ". Electronics product");
                }

                System.out.println(sortedProductsList.get(i).toString());
                System.out.println();
            }
        }
    }

    /**
     * This method is used to save the information in the file
     *
     */
    public void saveInFile(){
        try {
            FileOutputStream file = new FileOutputStream("src/CLI/ProductsData.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(productsList);
            out.close();
            file.close();
            System.out.println("File saved successfully");
        } catch (IOException e){
            System.out.println("error");
        }

    }

    /**
     * This method is used to read the information from the file
     *
     */
    public void readFromFile(){
        try {
            FileInputStream file = new FileInputStream("src/CLI/ProductsData.ser");
            ObjectInputStream inputStream = new ObjectInputStream(file);

            productsList = (ArrayList<Product>) inputStream.readObject();
            inputStream.close();
            file.close();

        } catch (IOException | ClassNotFoundException e ){
            System.out.println("error");
        }

    }

    /**
     * This method is used to validate the product ID
     * @param message String
     * @return String productID
     */
    public String validateProductID(String message){
        Scanner input = new Scanner(System.in);
        System.out.print(message);
        String productID = input.next();

        for (int i = 0; i < productsList.size(); i++) {
            if (productID.equals(productsList.get(i).productID)){
                System.out.print("\n+++++Error!++++++\nProduct ID already exists! \n\nPlease enter a unique product ID : ");
                productID = input.next();
                i = 0;
            }
        }

        return productID;
    }

    /**
     * This method is used to validate the number of available items
     * @return int noOfAvailableItems
     */
    private int validateNoOfAvailableItems(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number of available items : ");
        int noOfAvailableItems;

        while (true){
            try {
                noOfAvailableItems = Integer.parseInt(input.next());
                if (noOfAvailableItems <= 0){
                    System.out.print("\n+++++Error!++++++\nNo of available items cannot be 0 or negative! \n\nPlease re enter a valid number : ");
                }else{
                    return noOfAvailableItems;
                }
            }catch (NumberFormatException nfe){
                System.out.print("\n+++++Error!++++++\nNo of available items should be a number! \n\nPlease re enter a valid number : ");
            }
        }
    }

    /**
     * This method is used to validate the product price
     * @return double price
     */
    private double validatePrice(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the product price : ");
        double price;

        while (true){
            try {
                price = Double.parseDouble(input.next());
                if (price <= 0){
                    System.out.print("\n+++++Error!++++++\nPrice cannot be 0 or negative! \n\nPlease enter a valid amount : ");
                }else{
                    return price;
                }
            }catch (NumberFormatException nfe){
                System.out.print("\n+++++Error!++++++\nPrice should be a number! \n\nPlease enter a valid amount : ");
            }
        }
    }

    /**
     * This method is used to validate the product warranty period
     * @return int warrantyPeriod
     */
    public int validateWarrantyPeriod(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the product warranty period in weeks : ");
        int warrantyPeriod;

        while (true){
            try {
                warrantyPeriod = Integer.parseInt(input.next());
                if (warrantyPeriod <= 0){
                    System.out.print("\n+++++Error!++++++\nWarranty period cannot be 0 or negative! \n\nPlease enter a valid number : ");
                }else{
                    return warrantyPeriod;
                }
            }catch (NumberFormatException nfe){
                System.out.print("\n+++++Error!++++++\nWarranty period should be a number! \n\nPlease enter a valid number : ");
            }
        }
    }

    /**
     * This method is used to validate the product option
     *
     * @param errorMessage  String
     * @param inputMessage String
     * @return int userChoice
     */

    public int validateProductChoice(String inputMessage, String errorMessage){
        Scanner input = new Scanner(System.in);
        int userChoice = 0;
        boolean isValidInput = true;

        while(isValidInput){
            try{
                System.out.print(inputMessage);
                userChoice = Integer.parseInt(input.next());
                if (userChoice == 1 || userChoice == 2){
                    isValidInput = false;
                }else{
                    System.out.println(errorMessage);
                }
            }catch (NumberFormatException nfe){
                System.out.println(errorMessage);
            }
        }

        return userChoice;
    }

    /**
     * This method is used to validate the user option
     *
     */
    public void registerUser() {
        Scanner input = new Scanner(System.in);
        String username = null;
        boolean isAccountCreated = false;

        while (!isAccountCreated) {
            System.out.print("Enter your username : ");
            username = input.next();
            boolean usernameExists = false;

            for (int i = 0; i < userList.size(); i++) {
                if (username.equals(userList.get(i).getUsername())) {
                    System.out.println("Error! Username already exists!");
                    usernameExists = true;
                    break;
                }
            }

            if (!usernameExists) {
                isAccountCreated = true;  // Set to true only if the username is unique
            }
        }

        System.out.print("Enter your password : ");
        String password = input.next();

        for (int i = 0; i < 2; i++) {
            if (password.length() < 8) {
                System.out.print("\n+++++Error!++++++\nPassword should be at least 8 characters long! \n\nPlease enter again : ");
                password = input.next();
                i = 0;
            }
        }

        userList.add(new User(username, password));
        saveUserData();
        System.out.print("\nAccount created successfully! \n\n");
    }


    /**
     * This method is used to login the user
     *
     */
    public void loginUser(){
        if(userList.isEmpty()){
            System.out.println("\nNo users are there in the system to login!");
            return;
        }
        Scanner input = new Scanner(System.in);
        boolean credentialsValid = false;
        do {
            System.out.print("Enter your username: ");
            String username = input.next();

            System.out.print("Enter your password: ");
            String password = input.next();

            for (int i = 0; i < userList.size(); i++) {
                if (username.equals(userList.get(i).getUsername()) && password.equals(userList.get(i).getPassword())) {
                    System.out.println("\n+++++Success!++++++\nLogin successful!\n\n");
                    userList.get(i).setLoggedIn(true);
                    new ViewProductGUI(userList.get(i));
                    saveUserData();
                    return;
                }
            }

            if (!credentialsValid) {
                System.out.print("\n+++++Error!++++++\nInvalid credentials! \nPlease enter again.\n\n");
            }

        } while (!credentialsValid);
    }
    public void saveUserData(){
        try {
            FileOutputStream file = new FileOutputStream("src/CLI/UserData.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(userList);
            out.close();
            file.close();
            System.out.println("File saved successfully");
        } catch (IOException e){
            System.out.println("error");
        }

    }

    /**
     * This method is used to read the information from the file
     *
     */
    public void readUserData(){
        try {
            FileInputStream file = new FileInputStream("src/CLI/UserData.ser");
            ObjectInputStream inputStream = new ObjectInputStream(file);
            userList = (ArrayList<User>) inputStream.readObject();
            inputStream.close();
            file.close();
        } catch (IOException | ClassNotFoundException e ){
            System.out.println("error");
        }
    }

}


