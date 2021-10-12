package com.gnudios.libgdx.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.gnudios.libgdx.SSConfiguration;
import com.gnudios.libgdx.model.AbstractButton;
import com.gnudios.libgdx.model.AbstractTable;
import com.gnudios.libgdx.model.ActorAO;
import com.gnudios.libgdx.model.assets.AssetLoader2;
import com.gnudios.libgdx.model.assets.FontHandler;

import java.util.ArrayList;

public class Overlay {
	protected BitmapFont font = new BitmapFont();
	protected Camera cam;
	protected AbstractStage menuStage;
	private FontHandler fontHandler;
	private ActorAO frame;
	private ArrayList<AbstractButton> buttonArray = new ArrayList<>();
	private AssetLoader2 assetLoader2;

	protected Overlay() {

	}

	protected void init(Camera cam, AbstractStage menuStage,AssetLoader2 assetLoader2) {
		fontHandler = new FontHandler(assetLoader2);
		// Hack start
		// PPM = 1;
		this.cam = cam;
		this.menuStage = menuStage;
		initViewports();
		// PPM = 64;
		// Hack end
	}

	private void initViewports() {
		cam.viewportHeight = SSConfiguration.GAME_HEIGHT;
		cam.viewportWidth = SSConfiguration.GAME_WIDTH;
		cam.position.set(SSConfiguration.GAME_WIDTH / 2, SSConfiguration.GAME_HEIGHT / 2, 0);
	}

	protected void addFont(int size, int borderWidth, Color color, String fontName) {
		this.font = fontHandler.getNewFont(size, borderWidth, color, fontName);
		this.font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}

	protected void addFrame(String textureName, float posX, float posY) {
		this.frame = new ActorAO(textureName, this.menuStage, posX, posY,assetLoader2);
		menuStage.addActor(frame);
	}

	protected void addButton(AbstractButton abstractButton) {
		buttonArray.add(abstractButton);
		menuStage.addActor(abstractButton.getTextButton());
	}

	protected void addButtonToTable(AbstractTable table, AbstractButton abstractButton) {
		buttonArray.add(abstractButton);
		table.addAbstractActor(abstractButton.getTextButton());
	}


}
