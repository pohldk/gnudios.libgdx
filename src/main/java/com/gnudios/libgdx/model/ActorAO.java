package com.gnudios.libgdx.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.gnudios.libgdx.SSConfiguration;
import com.gnudios.libgdx.model.assets.AssetLoader2;
import com.gnudios.libgdx.view.AbstractStage;
import com.gnudios.libgdx.view.Camera;

public class ActorAO extends AbstractObject {

	private BitmapFont font;
	private String labelText;
	private float fontScale = 1f / SSConfiguration.PPM;
	private Camera cam;
	private boolean flipX = false;
	private boolean flipY = false;

	/**
	 * Automatically sets hit detection width and height to same as texture
	 * size. Automatically adds physics to this object. Automatically adds this
	 * object to stage.
     */

	public ActorAO(KryoActorAO2 kryoActorAO2, AbstractStage stage, AssetLoader2 assetLoader2){
		this.init(kryoActorAO2.getTextureName(), stage, assetLoader2);
		this.setLevel(kryoActorAO2.getId());
		this.setLife(kryoActorAO2.getId());
		this.setX(kryoActorAO2.getX());
		this.setY(kryoActorAO2.getY());
		this.setHeight(kryoActorAO2.getHeight());
		this.setWidth(kryoActorAO2.getWidth());
		this.setUserName(kryoActorAO2.getUserName());
		//stage.addActor(this);
	}

	public ActorAO(KryoActorAO2 kryoActorAO2, AssetLoader2 assetLoader2){
		this.init(kryoActorAO2.getTextureName(), assetLoader2);
		this.setLevel(kryoActorAO2.getId());
		this.setLife(kryoActorAO2.getId());
		this.setX(kryoActorAO2.getX());
		this.setY(kryoActorAO2.getY());
		this.setHeight(kryoActorAO2.getHeight());
		this.setWidth(kryoActorAO2.getWidth());
		this.setRotation(kryoActorAO2.getAngle());
		this.setUserName(kryoActorAO2.getUserName());

	}

//	public void updateActorAO(KryoActorAO2 kryoActorAO2){
//		addAction(parallel(
//				moveTo(kryoActorAO2.getX() - (getWidth() / 2), kryoActorAO2.getY() - (getHeight() / 2), 0.01f ),
//				rotateTo(kryoActorAO2.getAngle(),0.01f)));
//
////                parallel(com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy((float) (Math.random() * 1000) - 500, (float) (Math.random() * 1000) - 500, 1.0f, Interpolation.pow3Out),
////                        com.badlogic.gdx.scenes.scene2d.actions.Actions.rotateBy((float) (Math.random() * 2000) - 100, 1.0f, Interpolation.linear))));
//		//this.setX(kryoActorAO2.getX() / PPM);
//		//this.setY(kryoActorAO2.getY() / PPM);
//		//this.setHeight(kryoActorAO2.getHeight() / 64);
//		//this.setWidth(kryoActorAO2.getWidth() / 64);
//		if(kryoActorAO2.isTouchable()){
//			this.setTouchable(Touchable.enabled);
//		}else{
//			this.setTouchable(Touchable.disabled);
//		}
//	}



	public ActorAO(String textureName, AbstractStage actorStage, AssetLoader2 assetLoader2) {
		this.init(textureName, actorStage, assetLoader2);
		// this.setX(Converter.PPMDivide(posX));
		// this.setY(Converter.PPMDivide(posY));
		//actorStage.addActor(this);
	}

	public ActorAO(String textureName, AbstractStage stage, float posX, float posY,AssetLoader2 assetLoader2) {
		this.init(textureName, stage,assetLoader2);
		this.setX(posX / stage.getPPM());
		this.setY(posY / stage.getPPM());
		//stage.addActor(this);
	}

	public ActorAO(String textureName, AbstractStage stage, float posX, float posY, float width, float height ,AssetLoader2 assetLoader2) {
		this.init(textureName, stage,assetLoader2 );
		this.setX(posX / stage.getPPM());
		this.setY(posY / stage.getPPM());
		this.setHeight(height);
		this.setWidth(width);
		//stage.addActor(this);
	}

	/**
	 * Automatically sets hit detection width and height to same as texture size
	 *
	 * @param textureName
	 */
	public ActorAO(String textureName,AssetLoader2 assetLoader2) {
		init(textureName,assetLoader2);
	}

	public void addActorAOAction(Action action){
		this.addAction(action);
	}

	public void addLabel(BitmapFont font, String labelText, Camera cam) {
		this.font = font;
		this.labelText = labelText;
		this.cam = cam;
	}

	public void setLabelText(String labelText) {
		this.labelText = labelText;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (isActive()) {
			if (isAnimationActive()) {
				// Have to set size of animation not static (64 * 64)
				batch.draw(getAnimationHandler().renderAnimation(getAnimation(getActiveAnimationNumber()), getStateTime()),
						getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), flipY ? -1 : 1 * getScaleX(),
						flipX ? -1 : 1 * getScaleY(),
						getRotation() - 90, false);
				setStateTime(getStateTime() + Gdx.graphics.getDeltaTime());
			} else {
				batch.draw(getSprite(), getX() , getY(), (getWidth() / 2), (getHeight() / 2), getWidth(), getHeight(),
						getScaleX(), getScaleY(), getRotation());
			}

			if (font != null) {

				font.getData().setScale(cam.zoom * fontScale);
				float labelY;
				float labelX;
				float cameraXposLeft = (cam.position.x * SSConfiguration.PPM - (SSConfiguration.GAME_WIDTH - 400) * cam.zoom / 2f) / SSConfiguration.PPM;
				float cameraXposRight = (cam.position.x * SSConfiguration.PPM + (SSConfiguration.GAME_WIDTH - 600) * cam.zoom / 2f) / SSConfiguration.PPM;
				float cameraYposTop = (cam.position.y * SSConfiguration.PPM + (SSConfiguration.GAME_HEIGHT - 300) * cam.zoom / 2f) / SSConfiguration.PPM;
				float cameraYposBottom = (cam.position.y * SSConfiguration.PPM - (SSConfiguration.GAME_HEIGHT) * cam.zoom / 2f) / SSConfiguration.PPM;
				//For libgdx version 1.9.9
				float labelTextLength = (labelText.length() * font.getSpaceXadvance()) / 2;
				//float labelTextLength = (labelText.length() * font.getSpaceWidth()) / 2;
				float textHeight = font.getLineHeight() + getHeight();

				// for X
				if (cameraXposRight < getX()) {
					labelX = cameraXposRight - labelTextLength;
				} else if (cameraXposLeft > getX()) {
					labelX = cameraXposLeft - labelTextLength;
				} else {
					labelX = getX() - labelTextLength;
				}
				// for Y top
				if (cameraYposTop < getY()) {
					labelY = cameraYposTop + textHeight;
				} else if (cameraYposBottom > getY()) {
					labelY = cameraYposBottom + textHeight;
				} else {
					labelY = getY() + textHeight;
				}

				font.draw(batch, labelText + " (" + String.format("%.0f", getX()) + ")", labelX, labelY);
			}
		}
	}

	public boolean isFlipX() {
		return flipX;
	}

	public void setFlipX(boolean flipX) {
		this.flipX = flipX;
	}

	public boolean isFlipY() {
		return flipY;
	}

	public void setFlipY(boolean flipY) {
		this.flipY = flipY;
	}
}
