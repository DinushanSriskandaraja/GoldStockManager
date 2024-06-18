package Models;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class ApiResponse {

    @SerializedName("rates")
    private Map<String, Double> rates;

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }

    // Add setters/getters for other fields if required
}
