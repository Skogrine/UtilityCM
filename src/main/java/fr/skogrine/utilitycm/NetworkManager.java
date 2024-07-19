package fr.skogrine.utilitycm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * NetworkManager simplifies network operations, providing methods for making HTTP requests, handling retries, caching responses, and monitoring network status.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * NetworkManager networkManager = new NetworkManager();
 * String response = networkManager.get("https://api.example.com/data");
 * System.out.println(response);
 * }</pre>
 */
public class NetworkManager {

    private final Map<String, String> cache = new HashMap<>();

    /**
     * Makes a GET request to the specified URI and returns the response.
     *
     * @param uriString the URI to request
     * @return the response from the server
     * @throws Exception if an error occurs during the request
     */
    public String get(String uriString) throws Exception {
        // Check if the response is already in the cache
        if (cache.containsKey(uriString)) {
            return cache.get(uriString);
        }

        // Convert the string to URI
        URI uri = new URI(uriString);
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setRequestMethod("GET");

        // Read the response from the server
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            // Cache and return the response
            String response = content.toString();
            cache.put(uriString, response);
            return response;
        } finally {
            connection.disconnect();
        }
    }
}

