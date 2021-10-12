package com.gnudios.libgdx.model.assets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class FontHandler {

    // private FreeTypeFontGenerator generator = new
    // FreeTypeFontGenerator(Gdx.files.internal("data/Action_Man_Bold.ttf"));
    private AssetLoader2 assetLoader;

    public FontHandler(AssetLoader2 assetLoader2) {
        this.assetLoader = assetLoader2;
    }

    // We have to evaluate whether its necessary to use .dispose() on the
    // generator. As it is now we dont dispose it EVER.

    /**
     * Dynamic creating temporary font for temporary use. !OBS OBS OBS! Remember
     * to .dispose() after finishing use. If .dispose() is not called, this
     * occupies a lot of memory.
     *
     * @param size
     * @param color    - Primary use: Use Color.BLACK / Color.TEAL or create a new.
     *                 Alternative use: new Color(float red, float green, float blue,
     *                 float alpha)
     * @param fontName - Exact name of the font in assets. Can be with and without
     *                 file type.
     * @return BitmapFont
     */
    public BitmapFont getNewFont(int size, int borderWidth, Color color, String fontName) {
        FreeTypeFontParameter parameter;
        parameter = new FreeTypeFontParameter();
        parameter.size = size;
        parameter.borderColor.add(Color.BLACK);
        parameter.borderWidth = borderWidth;
        parameter.shadowColor = Color.GRAY;
        parameter.shadowOffsetX = 1;
        parameter.shadowOffsetY = 1;
        parameter.color = color;
        BitmapFont font;
        font = assetLoader.getFontGenerator(fontName).generateFont(parameter);
//		font.setColor(color);
        font.setUseIntegerPositions(false);
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        // generator.dispose(); // don't forget to dispose to avoid memory
        // leaks!

        return font;
    }

    //NOT USED
//	public BitmapFont getNewFont(int size, Color color, String fontName, FreeTypeFontParameter parameter) {
//		parameter.size = size;
//		parameter.borderColor.add(Color.BLACK);
//		parameter.borderWidth = 2;
//		parameter.shadowColor = Color.GRAY;
//		parameter.shadowOffsetX = 1;
//		parameter.shadowOffsetY = 1;
//		BitmapFont font;
//		font = assetLoader.getFontGenerator(fontName).generateFont(parameter);
//		font.setColor(color);
//		font.setUseIntegerPositions(false);
//		// generator.dispose(); // don't forget to dispose to avoid memory
//		// leaks!
//
//		return font;
//	}

    /**
     * Disposes all fonts that has been initialized in the AssetHandler array.
     * Use with caution.
     */
    public void disposeAllFonts() {
        // TODO: Check if this is necessary.
        // assetLoader.disposeAllFonts();
    }
}
