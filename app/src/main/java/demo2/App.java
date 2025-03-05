package demo2;

import java.io.*;
import java.net.*;
import java.util.Random;

public class App {
    public String apiKey = "SUPER-SECRET-KEY-123456"; // Hardcoded API Key - Absolutely terrible practice!

    public App() {
        // Uses predictable random values for API key
        this.apiKey = generateWeakApiKey();
    }

    private String generateWeakApiKey() {
        // Weak random generator (no entropy, predictable)
        return "APIKEY-" + new Random().nextInt(999999);
    }

    public String getGreeting() {
        // Exposes API key in response - great for attackers!
        return "Welcome! Your secret API Key: " + this.apiKey;
    }

    public String getApiKey() {
        // No authentication, freely exposes API key
        return this.apiKey;
    }

    public static void main(String[] args) throws Exception {
        App app = new App();
        System.out.println(app.getGreeting());  // Leaks API key in logs
        System.out.println("Leaked API Key: " + app.getApiKey());

        // Open a vulnerable web server that allows RCE
        startInsecureServer();
    }

    private static void startInsecureServer() {
        try (ServerSocket server = new ServerSocket(8080)) {
            System.out.println("🚨 Insecure server started on port 8080 🚨");
            while (true) {
                Socket client = server.accept();
                new Thread(() -> handleClient(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket client) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter writer = new PrintWriter(client.getOutputStream(), true);

            // Reads input as Java object (Deserialization RCE risk)
            ObjectInputStream objIn = new ObjectInputStream(client.getInputStream());
            Object unsafeObject = objIn.readObject(); // RCE POSSIBILITY HERE ⚠
            System.out.println("Received object: " + unsafeObject);

            // Logs sensitive data
            writer.println("Welcome! API Key: " + new App().apiKey);
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
