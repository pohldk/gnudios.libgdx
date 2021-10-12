package com.gnudios.libgdx;


import com.gnudios.libgdx.general.helpers.Configuration;

public class SSConfiguration extends Configuration {
    // Generic paths.
    public static String INTERNAL_ATLAS_PATH;
    public static String INTERNAL_BUTTON_ATLAS_FILENAME;
    public static String INTERNAL_OBJECT_ATLAS_FILENAME;
    public static String SPLASH_LOAD_PATH;
    public static String SPLASH_LOAD_FRAME_FILENAME;
    public static String SPLASH_LOAD_IMAGE_FILENAME;
    public static String LOCAL_JSON_MAPS_PATH;
    // Windows Path.
    public static String PACKER_INPUT_PATH;
    public static String PACKER_OUTPUT_PATH;
    public static String ICON_IMAGE_PATH_128;
    public static String ICON_IMAGE_PATH_64;
    public static String ICON_IMAGE_PATH_32;
    // MacOSX Path.
    public static String PACKER_INPUT_PATH_OSX;
    public static String PACKER_OUTPUT_PATH_OSX;
    // Finals.
    public static float GAME_WIDTH;
    public static float GAME_HEIGHT;
    public static int PPM;
    public static int VIRTUAL_SPRITESIZE;
    public static int SPRITE_MAX_X;
    public static int SPRITE_MAX_Y;
    // Game settings.
    public static String GAME_TITLE;
    public static String SERVER_HOST_IP;
    public static int SERVER_HOST_PORT;
    public static int SERVER_HOST_ALT_PORT;
    public static int SERVER_CONNECTION_TIMEOUT;
    public static int PACKAGE_MILLIS;
    // Android / Desktop paths.
    private static String ANDROID_PATH = "config.properties";
    private static String DESKTOP_PATH = "config.properties";

    public SSConfiguration(boolean IS_DESKTOP) {
        super(IS_DESKTOP, ANDROID_PATH, DESKTOP_PATH);
        INTERNAL_ATLAS_PATH = getString("INTERNAL_ATLAS_PATH");
        INTERNAL_BUTTON_ATLAS_FILENAME = getString("INTERNAL_BUTTON_ATLAS_FILENAME");
        INTERNAL_OBJECT_ATLAS_FILENAME = getString("INTERNAL_OBJECT_ATLAS_FILENAME");
        PACKER_INPUT_PATH = getString("PACKER_INPUT_PATH");
        PACKER_OUTPUT_PATH = getString("PACKER_OUTPUT_PATH");
        PACKER_INPUT_PATH_OSX = getString("PACKER_INPUT_PATH_OSX");
        PACKER_OUTPUT_PATH_OSX = getString("PACKER_OUTPUT_PATH_OSX");
        GAME_WIDTH = getFloat("GAME_WIDTH");
        GAME_HEIGHT = getFloat("GAME_HEIGHT");
        PPM = getInt("PPM");
        VIRTUAL_SPRITESIZE = getInt("VIRTUAL_SPRITESIZE");
        SPRITE_MAX_X = getInt("SPRITE_MAX_X");
        SPRITE_MAX_Y = getInt("SPRITE_MAX_Y");
        SPLASH_LOAD_PATH = getString("SPLASH_LOAD_PATH");
        SPLASH_LOAD_FRAME_FILENAME = getString("SPLASH_LOAD_FRAME_FILENAME");
        SPLASH_LOAD_IMAGE_FILENAME = getString("SPLASH_LOAD_IMAGE_FILENAME");
        ICON_IMAGE_PATH_128 = getString("ICON_IMAGE_PATH_128");
        ICON_IMAGE_PATH_64 = getString("ICON_IMAGE_PATH_64");
        ICON_IMAGE_PATH_32 = getString("ICON_IMAGE_PATH_32");
        GAME_TITLE = getString("GAME_TITLE");
        LOCAL_JSON_MAPS_PATH = getString("LOCAL_JSON_MAPS_PATH");
        SERVER_HOST_IP = getString("SERVER_HOST_IP");
        SERVER_HOST_PORT = getInt("SERVER_HOST_PORT");
        SERVER_HOST_ALT_PORT = getInt("SERVER_HOST_ALT_PORT");
        SERVER_CONNECTION_TIMEOUT = getInt("SERVER_CONNECTION_TIMEOUT");
        PACKAGE_MILLIS = getInt("PACKAGE_MILLIS");
    }
}
