package com.kammoun.HDuels.API.Utils.Skulls;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.UUID;

public class SkullHandler {

    private static final UUID RANDOM_UUID = UUID.fromString("92864445-51c5-4c3b-9039-517c9927d1b4"); // Reuse the same random UUID

    /**
     * Get a custom skull item based on the server version.
     * @param textureUrl The URL of the texture to use.
     * @return The customized skull item.
     */
    public static ItemStack getCustomSkull(String textureUrl) {
        String serverVersion = getServerMajorVersion();
        if (Integer.parseInt(serverVersion) > 16) {
            return getCustomSkullNewVersion(textureUrl); // Use the new version method for 1.17+
        }
        return new ItemStack(Material.PLAYER_HEAD);
    }

    /**
     * Gets a custom skull for versions 1.17 and above.
     * @param texture The texture URL.
     * @return The customized skull item.
     */
    private static ItemStack getCustomSkullNewVersion(String texture) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        if (texture.isEmpty()) return head;
        meta.setOwnerProfile(getProfile(texture));
        head.setItemMeta(meta);
        return head;
    }

    /**
     * Gets a custom skull for versions 1.16 and below.
     * @param textureUrl The texture URL.
     * @return The customized skull item.
    private static ItemStack getCustomSkullLegacy(String textureUrl) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        if (textureUrl == null || textureUrl.isEmpty()) {
            return head;
        }

        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();

        GameProfile profile = new GameProfile(RANDOM_UUID, null);
        String base64Texture = Base64.getEncoder().encodeToString((
                "{\"textures\":{\"SKIN\":{\"url\":\"" + textureUrl + "\"}}}"
        ).getBytes());
        profile.getProperties().put("textures", new Property("textures", base64Texture));

        try {
            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile); // Set the custom profile to the skull meta
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        head.setItemMeta(skullMeta);
        return head;
    }*/

    /**
     * Creates a PlayerProfile with a custom texture.
     * @param url The URL of the skin texture.
     * @return A PlayerProfile with the texture applied.
     */
    private static PlayerProfile getProfile(String url) {
        PlayerProfile profile = Bukkit.createPlayerProfile(RANDOM_UUID); // Get a new player profile
        PlayerTextures textures = profile.getTextures();
        URL urlObject;
        try {
            urlObject = new URL(url); // The URL to the skin
        } catch (MalformedURLException exception) {
            throw new RuntimeException("Invalid URL", exception);
        }
        textures.setSkin(urlObject); // Set the skin of the player profile to the URL
        profile.setTextures(textures); // Set the textures back to the profile
        return profile;
    }

    /**
     * Get the major server version (e.g., "1.16", "1.17").
     * @return The server major version as a string.
     */
    private static String getServerMajorVersion() {
        String version = Bukkit.getBukkitVersion(); // e.g., "1.18.1-R0.1-SNAPSHOT"
        return version.split("\\.")[1]; // Returns the major version, e.g., "18" for "1.18"
    }
}
