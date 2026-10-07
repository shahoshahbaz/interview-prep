package practice.javaconcepts.threading.volatileKeyword;

public class StockPrice {

    // ❌ WITHOUT volatile — reader may see stale price
    private double priceWithoutVolatile= 100.0;

    // ✅ WITH volatile — reader always sees fresh price
    private volatile double priceWithVolatile = 100.0;

    // --- without volatile ---
    public void updatePriceWithoutVolatile(double newPrice) {
        priceWithoutVolatile = newPrice;
    }

    public double getPriceWithoutVolatile() {
        return priceWithoutVolatile;
    }

    // --- with volatile ---
    public void updatePriceWithVolatile(double newPrice) {
        priceWithVolatile = newPrice;
    }

    public double getPriceWithVolatile() {
        return priceWithVolatile;
    }

}
