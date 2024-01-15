package CLI;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static WestminsterShoppingManager westminsterShoppingManager = new WestminsterShoppingManager();
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        //Reading the information back if the file exists
        File productsFile = new File("src/CLI/ProductsData.ser");
        if (productsFile.exists()){
            westminsterShoppingManager.readFromFile();
            System.out.println("ALERT! Data from the previous fun has been restored successfully!\n");
        }
        File usersFile = new File("src/CLI/UserData.ser");
        if (usersFile.exists()){
            westminsterShoppingManager.readUserData();
        }

        System.out.println("""
                +++++++++++++++++++++++++++++++++++++
                +  Westminster Shopping Centre CLI  +
                +++++++++++++++++++++++++++++++++++++
                """);

        boolean isTrue = true;
        while (isTrue){
            System.out.print("""
                         
                         =======================
                         =      Main Menu      =
                         =======================
                    
                    \t\t1. Add a new product
                    \t\t2. Delete a product
                    \t\t3. Print the list of the products
                    \t\t4. Save in a file
                    \t\t5. Register/Login for Customer
                    \t\t6. Exit 
                    """);
            System.out.print("\nSelect your desired option : ");
            int userChoice;

            while(true){
                try{
                    userChoice = input.nextInt();
                    break;
                }catch (InputMismatchException e){
                    System.out.println("\nInvalid option!\nOption should be an integer\n");
                    System.out.print("Select your desired option : ");
                    input.next();
                }
            }

            switch (userChoice) {
                case 1 -> westminsterShoppingManager.addNewProduct();
                case 2 -> westminsterShoppingManager.deleteProduct();
                case 3 -> westminsterShoppingManager.displayProducts();
                case 4 -> westminsterShoppingManager.saveInFile();
                case 5 -> {
                    System.out.print("""
                            
                            =======================
                            =      Customer        =
                            =======================
                            
                            \t\t1. Register Account
                            \t\t2. Login
                            \t\t3. Exit
                            """);
                    System.out.print("\nSelect your desired option : ");
                    int customerSelection;

                    while(true){
                        try{
                            customerSelection = input.nextInt();
                            break;
                        }catch (InputMismatchException e){
                            System.out.println("\nInvalid option!\nOption should be an integer\n");
                            System.out.print("Select your desired option : ");
                            input.next();
                        }
                    }
                    switch (customerSelection) {
                        case 1 -> westminsterShoppingManager.registerUser();
                        case 2 -> westminsterShoppingManager.loginUser();
                        case 3 -> System.exit(0);
                        default -> System.out.println("\nInvalid option!\n");
                    }
                }
                case 6 -> System.exit(0);
                default -> System.out.println("\nInvalid option!\n");
            }
        }
    }

}

