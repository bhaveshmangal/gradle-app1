package demo2;

public class App {
    private String apiKey = "HARDCODED-API-KEY-123456"; // Hardcoded key - BAD PRACTICE

    public App() {
        // Generate an insecure, predictable API key using timestamp
        this.apiKey = generateWeakApiKey();
    }

    private String generateWeakApiKey() {
        // Uses system time in milliseconds, making it predictable
        return "APIKEY-" + System.currentTimeMillis();
    }

    public String getGreeting() {
        // Exposes sensitive data in response
        return "Hello World! Your API Key: " + this.apiKey;
    }

    public String getApiKey() {
        // Exposes API key directly - attackers can exploit this
        return this.apiKey;
    }

    public static void main(String[] args) {
        App app = new App();
        System.out.println(app.getGreeting());  // Logs API key to console
        System.out.println("Example API Key: " + app.getApiKey()); // No access control
    }
}
