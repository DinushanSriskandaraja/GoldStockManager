package Models;

import java.util.List;

public class User {
    public String email;
    public String username;
    public String shopName;
    private double stockInGram;
//    private double stockValueInLkr;
    private List<Buy> buys;
    private List<Sell> sells;

    public User() {
    }

    public User(String email, String username, String shopName, double stockInGram, List<Buy> buys, List<Sell> sells) {
        this.email = email;
        this.username = username;
        this.shopName = shopName;
        this.stockInGram = stockInGram;
        this.buys = buys;
        this.sells = sells;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public double getStockInGram() {
        return stockInGram;
    }

    public void setStockInGram(double stockInGram) {
        this.stockInGram = stockInGram;
    }

    public List<Buy> getBuys() {
        return buys;
    }

    public void setBuys(List<Buy> buys) {
        this.buys = buys;
    }

    public List<Sell> getSells() {
        return sells;
    }

    public void setSells(List<Sell> sells) {
        this.sells = sells;
    }
}
