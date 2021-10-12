package com.gnudios.libgdx.general.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.gnudios.libgdx.SSConfiguration;

/**
 * Converter used for optimizing different pixels, sprites and calculations for different areas in the game
 * The size of screen, sprites etc. is defined in the application.properties, and should always be changed in there.
 * The most logical way of using this class is through static import of all the methods. For current project this is:
 * import static com.gnudios.general.helpers.Converter.*;
 */
public class Calculator {

    public static int posXToSpriteX(float f) {
        return (int) (f / spriteSizeX());
    }

    public static int posYToSpriteY(float f) {
        return (int) (f / spriteSizeY());
    }

    public static int spriteXtoPixX(int x) {
        return (int) ((float) x * ((float) Gdx.graphics.getWidth() / (float) SSConfiguration.SPRITE_MAX_X));
    }

    public static int spriteYtoPixY(int y) {
        return (int) ((float) spriteSizeY() + ((float) y * ((float) Gdx.graphics.getHeight() / (float) SSConfiguration.SPRITE_MAX_Y)));
    }


    public static int spriteXtoPosX(int x) {

        return (int) (((float) x * ((float) Gdx.graphics.getWidth() / (float) SSConfiguration.SPRITE_MAX_X))
                / ((float) Gdx.graphics.getWidth() / SSConfiguration.GAME_WIDTH));
    }

    public static int spriteYtoPosY(int y) {

        return (int) posYToPixY(spriteYtoPixY(y));
    }

    /**
     * Gets the X pixel size of the sprite X 'box'
     *
     * @return Integer pixel X size.
     */
    private static int spriteSizeX() {
        return Gdx.graphics.getWidth() / SSConfiguration.SPRITE_MAX_X;
    }

    /**
     * Gets the Y pixel size of the sprite Y 'box'
     *
     * @return Integer pixel Y size.
     */
    private static int spriteSizeY() {
        return Gdx.graphics.getHeight() / SSConfiguration.SPRITE_MAX_Y;
    }

    public static float posXToPixX(float x) {

        return (x / ((float) Gdx.graphics.getWidth() / SSConfiguration.GAME_WIDTH));

    }

    public static float posYToPixY2(float y) {

        return (y / ((float) Gdx.graphics.getHeight() / SSConfiguration.GAME_HEIGHT));

    }

    public static float posYToPixY(double d) {

        return SSConfiguration.GAME_HEIGHT - ((float) d / (Gdx.graphics.getHeight() / SSConfiguration.GAME_HEIGHT));
    }

    public static double pixDistanceX(double d) {
        return d * ((double) Gdx.graphics.getWidth() / (double) SSConfiguration.GAME_WIDTH);

    }

    public static double pixDistanceXCam(double d) {
        return d * ((double) Gdx.graphics.getWidth() / (double) SSConfiguration.GAME_WIDTH);

    }

    public static double pixDistanceY(double h) {
        return h * (Gdx.graphics.getHeight() / (double) SSConfiguration.GAME_HEIGHT);

    }

    /**
     * Calculates an angle between 2 coordinates.
     *
     * @param x1 Double coordinate 1 X
     * @param y1 Double coordinate 1 Y
     * @param x2 Double coordinate 2 X
     * @param y2 Double coordinate 2 Y
     * @return Double angle (Degrees)
     */
    public static Double calculateAngle(double x1, double y1, double x2, double y2) {
        double angle = 0;
        if (x2 == x1 && y2 > y1) {
            angle = 270;
        } else if (x2 == x1 && y2 < y1) {
            angle = 90;
        } else {
            angle = ((Math.abs(Math.atan((y1 - y2) / (x2 - x1)))) * 180) / Math.PI;
            if (x1 < x2 && y1 < y2) {
                angle = 90 - angle;
                angle += 270;
            } else if (x1 > x2 && y1 < y2) {
                angle += 180;
            } else if (x1 > x2) {
                angle = 90 - angle;
                angle += 90;
            }
        }

        return angle;
    }

    /**
     * Calculates the distance between 2 coordinates.
     *
     * @param x1 Double coordinate 1 X
     * @param y1 Double coordinate 1 Y
     * @param x2 Double coordinate 2 X
     * @param y2 Double coordinate 2 Y
     * @return Double distance between the 2 coordinates.
     */
    public static double calculateDistance(float x1, float y1, float x2, float y2) {
        return calculateRadius(x1, y1, x2, y2);
    }

    /**
     * Calculates the radius of a circle (the distance between 2 coordinates)
     *
     * @param x1 Double coordinate 1 X
     * @param y1 Double coordinate 1 Y
     * @param x2 Double coordinate 2 X
     * @param y2 Double coordinate 2 Y
     * @return Double distance between the 2 coordinates.
     */
    public static double calculateRadius(float x1, float y1, float x2, float y2) {
        float directionx = x2 - x1;
        float directiony = y2 - y1;
        return Math.sqrt(directionx * directionx + directiony * directiony);
    }

    /**
     * Gets mouse position y in pixels inside game window.
     *
     * @param cam (Inside which the mouse is located)
     * @return float pixel position X of mouse.
     */
    public static float getMouseX(OrthographicCamera cam) {
        return (float) ((Gdx.input.getX()
                - ((Gdx.graphics.getWidth() / 2) - (Gdx.graphics.getWidth() / 2) / PPMDivide(cam.zoom))
                + pixDistanceX(cam.position.x - (SSConfiguration.GAME_WIDTH / 2)) / PPMDivide(cam.zoom)) * PPMDivide(cam.zoom));
    }

    /**
     * Gets mouse position x in pixels inside game window.
     *
     * @param cam OrthographicCamera (Inside which the mouse is located)
     * @return float pixel position Y of mouse.
     */
    public static float getMouseY(OrthographicCamera cam) {
        return (float) ((Gdx.input.getY()
                - ((Gdx.graphics.getHeight() / 2) - (Gdx.graphics.getHeight() / 2) / PPMDivide(cam.zoom))
                - pixDistanceY(cam.position.y - (SSConfiguration.GAME_HEIGHT / 2)) / PPMDivide(cam.zoom)) * PPMDivide(cam.zoom));
    }

    /**
     * Automatic division of the PPM the game is set to
     * Used in order to not have a hardcoded number needing to be changed in several different places,
     * if game has to be scaled differently.
     *
     * @param f
     * @return
     */
    public static float PPMDivide(float f) {
        return (f / SSConfiguration.PPM);
    }

}
