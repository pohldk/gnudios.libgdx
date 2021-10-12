package com.gnudios.libgdx.model;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.gnudios.libgdx.model.assets.AssetLoader2;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.gnudios.libgdx.SSConfiguration.*;

public class AbstractButton {

	private Skin skin;
	private TextButtonStyle textButtonStyle;
	private TextButton textButton;
	private int buttonNumber;
	private float adjustMoveSpeed = 3.0f; // Default
	private float onScreenX;
	private float onScreenY;
	private float offScreenX;
	private float offScreenY;
	private boolean isOnScreen = false;

	public AbstractButton(BitmapFont font, String upTex, String downTex,AssetLoader2 assetLoader2) {
		skin = new Skin();
		skin.addRegions(assetLoader2.getTextureAtlas_DONT_USE(INTERNAL_OBJECT_ATLAS_FILENAME));
		// new TextureAtlas(Gdx.files.internal(INTERNAL_ATLAS_PATH +
		// INTERNAL_BUTTON_ATLAS_FILENAME)));
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = font;
		textButtonStyle.up = skin.getDrawable(upTex);
		textButtonStyle.pressedOffsetX = -2;
		textButtonStyle.pressedOffsetY = -2;
		textButtonStyle.down = skin.getDrawable(downTex);
		textButton = new TextButton("", textButtonStyle);
	}

	public AbstractButton(BitmapFont font, String upTex, String downTex, String text,AssetLoader2 assetLoader2) {
		skin = new Skin();
		skin.addRegions(assetLoader2.getTextureAtlas_DONT_USE(INTERNAL_OBJECT_ATLAS_FILENAME));
		// new TextureAtlas(Gdx.files.internal(INTERNAL_ATLAS_PATH +
		// INTERNAL_BUTTON_ATLAS_FILENAME)));
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.pressedOffsetX = -2;
		textButtonStyle.pressedOffsetY = -2;
		textButtonStyle.font = font;
		textButtonStyle.up = skin.getDrawable(upTex);
		textButtonStyle.down = skin.getDrawable(downTex);
		textButton = new TextButton("", textButtonStyle);
		setText(text.toUpperCase());
	}

	public AbstractButton(BitmapFont font, String upTex, String downTex, String text, float posX, float posY,AssetLoader2 assetLoader2) {
		skin = new Skin();
		skin.addRegions(assetLoader2.getTextureAtlas_DONT_USE(INTERNAL_OBJECT_ATLAS_FILENAME));
		// new TextureAtlas(Gdx.files.internal(INTERNAL_ATLAS_PATH +
		// INTERNAL_BUTTON_ATLAS_FILENAME)));
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.pressedOffsetX = -2;
		textButtonStyle.pressedOffsetY = -2;
		textButtonStyle.font = font;
		textButtonStyle.up = skin.getDrawable(upTex);
		textButtonStyle.down = skin.getDrawable(downTex);
		textButton = new TextButton("", textButtonStyle);
		setText(text.toUpperCase());
		setPosition(posX, posY);
		setDefaultPosition(posX, posY);
	}

	public boolean isPressed() {

		return getTextButton().isPressed();
	}

	public void show() {
		if (!isOnScreen) {
			getTextButton().addAction(sequence(moveTo(onScreenX, onScreenY, 0.20f, Interpolation.pow5)));
		}
	}

	public void hide() {
		if (isOnScreen) {
			getTextButton().addAction(sequence(moveTo(offScreenX, offScreenY, 0.20f, Interpolation.pow5)));
		}
	}

	public int getButtonNumber() {
		return buttonNumber;
	}

	public void setButtonNumber(int buttonNumber) {
		this.buttonNumber = buttonNumber;
	}

	public Skin getSkin() {
		return skin;
	}

	public void setSkin(Skin skin) {
		this.skin = skin;
	}

	public TextButtonStyle getTextButtonStyle() {
		return textButtonStyle;
	}

	public void setTextButtonStyle(TextButtonStyle textButtonStyle) {
		this.textButtonStyle = textButtonStyle;
	}

	public TextButton getTextButton() {
		return textButton;
	}

	public void setTextButton(TextButton textButton) {
		this.textButton = textButton;
	}

	public void setText(String text) {
		this.textButton.setText(text);
	}

	public void setPosition(float x, float y, int alignment) {
		this.textButton.setPosition(x, y, alignment);
	}

	public void setPosition(float x, float y) {
		this.textButton.setPosition(x, y);
	}

	public void setDefaultPosition(float x, float y) {
		this.onScreenX = x;
		this.onScreenY = y;
	}

	public void setDefaultPositionX(float x) {
		this.onScreenX = x;
	}

	public void setDefaultPositionY(float y) {
		this.onScreenY = y;
	}

	public void addListener(EventListener listener) {
		this.textButton.addListener(listener);
	}

	public float getHeight() {
		return this.textButton.getHeight();
	}

	public void setHeight(int height) {
		textButton.setHeight(height);
	}

	public float getWidth() {
		return this.textButton.getWidth();
	}

	public void setWidth(int width) {
		textButton.setWidth(width);
	}

	public float getY() {
		return this.textButton.getY();
	}

	public void setY(float yPos) {
		this.textButton.setY(yPos);
	}

	public float getX() {
		return this.textButton.getX();
	}

	public void setX(float xPos) {
		this.textButton.setX(xPos);
	}

	public float getOnScreenX() {
		return onScreenX;
	}

	public void setOnScreenX(float onScreenX) {
		this.onScreenX = onScreenX;
	}

	public float getOnScreenY() {
		return onScreenY;
	}

	public void setOnScreenY(float onScreenY) {
		this.onScreenY = onScreenY;
	}

	public float getOffScreenX() {
		return offScreenX;
	}

	public void setOffScreenX(float offScreenX) {
		this.offScreenX = offScreenX;
	}

	public float getOffScreenY() {
		return offScreenY;
	}

	public void setOffScreenY(float offScreenY) {
		this.offScreenY = offScreenY;
	}

	public void addSlideAnimationLeft() {
		setPosition(onScreenX, onScreenY);
		setOffScreenX(0 - getWidth());
		setOffScreenY(getY());
		setX(0 - getWidth());
	}

	public void addSlideAnimationRight() {
		setPosition(onScreenX, onScreenY);
		setOffScreenX(GAME_WIDTH);
		setOffScreenY(getY());
		setX(GAME_WIDTH);
	}

//	public void slideIn() {
//		enable();
//		adjustToPosX(getOnScreenX());
//		adjustToPosY(getOnScreenY());
//	}
//
//	public void slideOut() {
//		disable();
//		adjustToPosX(getOffScreenX());
//		adjustToPosY(getOffScreenY());
//	}

	public void addSlideAnimationUp() {
		setPosition(onScreenX, onScreenY);
		setOffScreenY(0 - getHeight());
		setOffScreenX(getX());
		setY(0 - getHeight());
	}

	public void addSlideAnimationDown() {
		setPosition(onScreenX, onScreenY);
		setOffScreenY(GAME_HEIGHT);
		setOffScreenX(getX());
		setY(GAME_HEIGHT);
	}
//
//	private void adjustToPosX(float toPosX) {
//		setX(getX() + (((getX() - toPosX) / -getAdjustMoveSpeed() * Gdx.graphics.getDeltaTime() * 60.0f)));
//	}
//
//	private void adjustToPosY(float toPosY) {
//		setY(getY() + (((getY() - toPosY) / -getAdjustMoveSpeed() * Gdx.graphics.getDeltaTime() * 60.0f)));
//	}

	public void slideIn() {
		if (!isOnScreen) {
			enable();
			show();
			isOnScreen = true;
//		adjustToPosX(getOnScreenX());
//		adjustToPosY(getOnScreenY());
		}
	}

	public void slideOut() {
		if (isOnScreen) {
			disable();
			hide();
			isOnScreen = false;
		}
//		adjustToPosX(getOffScreenX());
//		adjustToPosY(getOffScreenY());
	}

	public float getAdjustMoveSpeed() {
		return adjustMoveSpeed;
	}

	public void setAdjustMoveSpeed(float adjustMoveSpeed) {
		this.adjustMoveSpeed = adjustMoveSpeed;
	}

	public void disable() {
		textButton.setTouchable(Touchable.disabled);
	}

	public void enable() {
		textButton.setTouchable(Touchable.enabled);
	}
}
