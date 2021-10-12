package com.gnudios.libgdx.model;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gnudios.libgdx.view.AbstractStage;
import com.gnudios.libgdx.model.assets.AssetLoader2;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.gnudios.libgdx.SSConfiguration.*;

public class AbstractTable extends Table {
	private Skin skin;
	//private float adjustMoveSpeed = 3.0f; // Default
	private float onScreenX;
	private float onScreenY;
	private float offScreenX;
	private float offScreenY;
	private boolean isOnScreen = false;
	// private float padding = 100; // Default

	public AbstractTable(AbstractStage stage, float posX, float posY, AssetLoader2 assetLoader2) {
		//skin = new Skin();
		//skin.addRegions(AssetLoader2.getInstance().getTextureAtlas_DONT_USE(INTERNAL_BUTTON_ATLAS_FILENAME));
		setX(posX);
		setY(posY);
		setOnScreenX(getX());
		setOnScreenY(getY());
		skin = new Skin();
		skin.addRegions(assetLoader2.getTextureAtlas_DONT_USE(INTERNAL_OBJECT_ATLAS_FILENAME));
		setSkin(skin);
		// setBackground("ninetest1");
		stage.addActor(this);


	}

	public AbstractTable(AssetLoader2 assetLoader2) {
		//skin = new Skin();
		//skin.addRegions(AssetLoader2.getInstance().getTextureAtlas_DONT_USE(INTERNAL_BUTTON_ATLAS_FILENAME));
		skin = new Skin();
		skin.addRegions(assetLoader2.getTextureAtlas_DONT_USE(INTERNAL_OBJECT_ATLAS_FILENAME));
		setSkin(skin);
		// setBackground("ninetest1");

	}

	//This is for testing with Action show and hide views
	private void show() {
		if (!isOnScreen) {
			addAction(sequence(moveTo(onScreenX, onScreenY, 0.20f, Interpolation.swingOut)));
//			if(offScreenY > 0){
//				addAction(sequence(moveTo(onScreenX, onScreenY -30, 0.20f,Interpolation.pow5),moveTo(onScreenX, onScreenY, 0.20f,Interpolation.pow5)));
//			}else{
//				addAction(sequence(moveTo(onScreenX, onScreenY + 30, 0.20f,Interpolation.pow5),moveTo(onScreenX, onScreenY, 0.20f,Interpolation.pow5)));
//			}
		}
	}

	private void hide() {
		if (isOnScreen) {
			addAction(sequence(moveTo(offScreenX, offScreenY, 0.20f, Interpolation.pow5)));
		}
	}


	public void addAbstractActor(Actor actor) {

		add(actor);

		setBounds(getX(), getY(), (actor.getWidth() * (getColumns())), (actor.getHeight() * (getRows() + 1)));


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

	public void slideIn() {
		if (!isOnScreen) {
			enable();
			show();
			isOnScreen = true;
//			adjustToPosX(getOnScreenX());
//			adjustToPosY(getOnScreenY());
		}
	}

	public void slideOut() {
		if (isOnScreen) {
			disable();
			hide();
			isOnScreen = false;
		}
//			adjustToPosX(getOffScreenX());
//			adjustToPosY(getOffScreenY());
	}

	//	public void addSlideAnimationLeft() {
	//		setPosition(onScreenX, onScreenY);
	//		setOffScreenX(0 - getWidth());
	//		setOffScreenY(getY());
	//		setX(0 - getWidth());
	//	}
	//
	//	public void addSlideAnimationRight() {
	//		setPosition(onScreenX, onScreenY);
	//		setOffScreenX(GAME_WIDTH);
	//		setOffScreenY(getY());
	//		setX(GAME_WIDTH);
	//	}
	//
	//	public void addSlideAnimationUp() {
	//		setPosition(onScreenX, onScreenY);
	//		setOffScreenY(0 - getHeight());
	//		setOffScreenX(getX());
	//		setY(0 - getHeight());
	//	}
	//
	//	public void addSlideAnimationDown() {
	//		setPosition(onScreenX, onScreenY);
	//		setOffScreenY(GAME_HEIGHT);
	//		setOffScreenX(getX());
	//		setY(GAME_HEIGHT);
	//	}
	//
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
	//
	//	private void adjustToPosX(float toPosX) {
	//		setX(getX() + (((getX() - toPosX) / -getAdjustMoveSpeed() * Gdx.graphics.getDeltaTime()
	//				* Gdx.graphics.getFramesPerSecond())));
	//	}
	//
	//	private void adjustToPosY(float toPosY) {
	//		setY(getY() + (((getY() - toPosY) / -getAdjustMoveSpeed() * Gdx.graphics.getDeltaTime()
	//				* Gdx.graphics.getFramesPerSecond())));
	//	}

	public void disable() {
		setTouchable(Touchable.disabled);
	}

	public void enable() {
		setTouchable(Touchable.enabled);
	}

	// public float getPadding() {
	// return padding;
	// }
	//
	// public void setPadding(float padding) {
	// this.padding = padding;
	// }

}
