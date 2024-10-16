package demo2;

import java.util.UUID;

public class App {
    private String apiKey;

    public App() {
        // Generate a random API key using UUID
        this.apiKey = generateRandomApiKey();
    }

    private String generateRandomApiKey() {
        // Generate a random UUID and return it as a string to serve as the API key
        return UUID.randomUUID().toString().replace("-", "");
    }

    public String getGreeting() {
        return "Hello World!";
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public static void main(String[] args) {
        App app = new App();
        System.out.println(app.getGreeting());
        System.out.println("Example API Key: " + app.getApiKey());
    }
}
