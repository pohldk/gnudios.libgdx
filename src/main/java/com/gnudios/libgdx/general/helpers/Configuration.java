package com.gnudios.libgdx.general.helpers;

import com.badlogic.gdx.Gdx;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Creating global static variables that can be changed from a single point, should be done inhere.
 * Referencing the variables can be done through static import like following: (For current package structure)
 * import static com.gnudios.general.helpers.Configuration.*;
 *
 * @author Lasse Svensson
 */
public class Configuration {

    private String ANDROID_PATH;
    private String DESKTOP_PATH;
    private boolean IS_DESKTOP;

    /**
     * The constructor automatically sets the different variables,
     * depending on the application.properties file with matching property names.
     *
     * @param IS_DESKTOP   boolean value of whether the application is run from Desktop or not. This is set because the load path is different from desktop to iOS/android.
     * @param ANDROID_PATH String path of where the android application.properties is: Example: "config.properties"
     * @param DESKTOP_PATH String path of where the desktop application.properties is: Example: "../android/assets/config.properties"
     */
    public Configuration(boolean IS_DESKTOP, String ANDROID_PATH, String DESKTOP_PATH) {
        this.IS_DESKTOP = IS_DESKTOP;
        this.ANDROID_PATH = ANDROID_PATH;
        this.DESKTOP_PATH = DESKTOP_PATH;
    }

//	/**
//	 * <h1><u> OBS OBS OBS, this is for single-platform games only. OBS OBS OBS </u></h1>
//	 * The constructor automatically sets the different variables, 
//	 * depending on the application.properties file with matching property names.
//	 * @param propertiesPath String path of properties file: Example: "../android/assets/config.properties"
//	 */
//	public Configuration(String propertiesPath) {
//		this.IS_DESKTOP = true;
//		this.DESKTOP_PATH = propertiesPath;
//	}

    /**
     * Gets the property from application.properties, the first time it is initialized
     * (Only done once, not every time the properties are used)
     *
     * @param property String name of property.
     * @return String value of the property defined.
     */
    protected String getString(String property) {
        Properties prop = new Properties();
        InputStream input;
        try {
            if (IS_DESKTOP) {
                input = new FileInputStream(DESKTOP_PATH);
            } else {
                input = Gdx.files.internal(ANDROID_PATH).read();
            }
            // load a properties file
            prop.load(input);
            return prop.getProperty(property);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the property from application.properties, the first time it is initialized
     * (Only done once, not every time the properties are used)
     *
     * @param property String name of property.
     * @return Integer value of the property defined.
     */
    protected int getInt(String property) {
        Properties prop = new Properties();
        InputStream input;
        try {
            if (IS_DESKTOP) {
                input = new FileInputStream(DESKTOP_PATH);
            } else {
                input = Gdx.files.internal(ANDROID_PATH).read();
            }
            // load a properties file
            prop.load(input);
            return Integer.parseInt(prop.getProperty(property));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * Gets the property from application.properties, the first time it is initialized
     * (Only done once, not every time the properties are used)
     *
     * @param property String name of property.
     * @return Double value of the property defined.
     */
    protected double getDouble(String property) {
        Properties prop = new Properties();
        InputStream input;
        try {
            if (IS_DESKTOP) {
                input = new FileInputStream(DESKTOP_PATH);
            } else {
                input = Gdx.files.internal(ANDROID_PATH).read();
            }
            // load a properties file
            prop.load(input);
            return Double.parseDouble(prop.getProperty(property));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * Gets the property from application.properties, the first time it is initialized
     * (Only done once, not every time the properties are used)
     *
     * @param property String name of property.
     * @return Float value of the property defined.
     */
    protected float getFloat(String property) {
        Properties prop = new Properties();
        InputStream input;
        try {
            if (IS_DESKTOP) {
                input = new FileInputStream(DESKTOP_PATH);
            } else {
                input = Gdx.files.internal(ANDROID_PATH).read();
            }
            // load a properties file
            prop.load(input);
            return Float.parseFloat(prop.getProperty(property));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return 0f;
    }
}
