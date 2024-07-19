package fr.skogrine.utilitycm;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NetworkManagerTest {

    @Test
    void testNetworkManager() throws Exception {
        NetworkManager networkManager = new NetworkManager();
        String response = networkManager.get("https://api.mojang.com/users/profiles/minecraft/Notch");

        assertNotNull(response);
    }
}