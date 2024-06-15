package Models;

public class Buy {
    private String date;
    private String description;
    private double price;
    private double weight;

    public Buy(String date, String description, double price, double weight) {
        this.date = date;
        this.description = description;
        this.price = price;
        this.weight = weight;
    }
    public Buy() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
