package practice.javaconcepts.threading.volatileKeyword;

public class StockPriceDemo {

    public static void main(String[] args)  throws InterruptedException{
        System.out.println("==============================");
        System.out.println("SCENARIO 1 — WITHOUT volatile");
        System.out.println("==============================");
        runWithoutVolatile();


        Thread.sleep(2000);  // pause between scenarios

        System.out.println("\n==============================");
        System.out.println("SCENARIO 2 — WITH volatile");
        System.out.println("==============================");
        runWithVolatile();
    }

    private static void runWithoutVolatile() throws InterruptedException {
        StockPrice stock = new StockPrice();

        Thread writer = new Thread(() ->{
            double[] prices = {105.5, 98.3, 110.0};
            for(double newPrice:prices) {
                stock.updatePriceWithoutVolatile(newPrice);
                System.out.println("Price updated to: $" + newPrice);
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        // reader — reads price
        Thread reader = new Thread(() -> {
            for (int i = 0; i < 6; i++) {
                System.out.println("     Current price: $"
                        + stock.getPriceWithoutVolatile()
                        + " ← may be stale!");
                try { Thread.sleep(400); }
                catch (InterruptedException e) { e.printStackTrace(); }
            }
        });

        writer.start();
        reader.start();
        writer.join();
        reader.join();



    }


    // ✅ WITH volatile — reader always sees fresh price
    private static void runWithVolatile() throws InterruptedException {

        StockPrice stock = new StockPrice();

        // writer — updates price
        Thread writer = new Thread(() -> {
            double[] prices = {105.5, 98.3, 110.0};
            for (double newPrice : prices) {
                stock.updatePriceWithVolatile(newPrice);
                System.out.println("Price updated to: $" + newPrice);
                try { Thread.sleep(1000); }
                catch (InterruptedException e) { e.printStackTrace(); }
            }
        });

        // reader — reads price
        Thread reader = new Thread(() -> {
            for (int i = 0; i < 6; i++) {
                System.out.println("     Current price: $"
                        + stock.getPriceWithVolatile()
                        + " ← always fresh!");
                try { Thread.sleep(400); }
                catch (InterruptedException e) { e.printStackTrace(); }
            }
        });

        writer.start();
        reader.start();
        writer.join();
        reader.join();
    }
}
