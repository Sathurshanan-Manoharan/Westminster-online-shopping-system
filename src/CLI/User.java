package CLI;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private boolean firstTimeUser = true;
    private boolean isLoggedIn = false;
    private ShoppingCart shoppingCartArrayList;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        shoppingCartArrayList = new ShoppingCart();
    }


    public String getPassword() {
        return password;
    }

    public boolean isFirstTimeUser() {
        return firstTimeUser;
    }

    public ShoppingCart getShoppingCartArrayList() {
        return shoppingCartArrayList;
    }

    public void setFirstTimeUser(boolean firstTimeUser) {

        this.firstTimeUser = firstTimeUser;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public String getUsername() {
        return username;
    }
}
