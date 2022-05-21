package sptech.bentscadastro.restaurant.form;

public class RestaurantUpdateForm {
    private String foodType;
    private Double priceAverage;
    private String openingTime;
    private String closingTime;
    private String description;

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public Double getPriceAverage() {
        return priceAverage;
    }

    public void setPriceAverage(Double priceAverage) {
        this.priceAverage = priceAverage;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}