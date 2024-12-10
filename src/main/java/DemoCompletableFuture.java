import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class DemoCompletableFuture {
    public static void main(String[] args) {
        traditional();
    }

    private static void traditional() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("process thread starts...");
                Thread.sleep(2000);
                System.out.println("process thread stops...");
                return "Hello";
            } catch (InterruptedException e) {
                e.printStackTrace();
                return "Error occurred";
            }
        });

        future.thenAccept(System.out::println);

        System.out.println("Main thread continues...");

        // Wait for the CompletableFuture to complete
        try {
            future.get(); // This blocks until the future completes
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


    private static void compact() {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("process thread stops...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello";
        }).thenAccept(System.out::println);

        System.out.println("Main thread continues...");
    }

    private  static void  inline() {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello";
        }).thenAccept(System.out::println);

        System.out.println("Main thread continues...");
    }




}
