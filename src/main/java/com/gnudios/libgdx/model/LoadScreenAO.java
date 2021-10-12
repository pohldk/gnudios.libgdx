package com.gnudios.libgdx.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.gnudios.libgdx.model.assets.AnimationHandler;
import com.gnudios.libgdx.view.AbstractStage;
import com.gnudios.libgdx.model.assets.AssetLoader2;

import java.util.ArrayList;

import static com.gnudios.libgdx.general.helpers.Calculator.PPMDivide;

public class LoadScreenAO extends Actor {
	// Class setup.
	private AssetLoader2 assetLoader2;
	private Texture texture;
	private String textureName;
	private ArrayList<AnimationObject> animationList = new ArrayList<>();
	private AnimationHandler animationHandler;
	private Sprite sprite;
	private float stateTime = 0;
	private boolean isAnimationActive = false;
	private int activeAnimationNumber = 0;

	// Variables for ingame.
	private int life;
	private int maxLife;
	private float power;
	private int maxPower;
	private int superPower;
	private int maxSuperPower;
	private int extraPower;
	private int maxExtraPower;
	private int defense;
	private int armor;
	private int resistance;
	private int damage;
	private int level;
	private int maxLevel;
	private float range;
	private String type;
	private boolean isActive;
	private boolean isAlive;
	private int value;
	private int price;

	/**
	 * Automatically sets hit detection width and height to same as texture size
	 *
	 * @param name
	 * @param textureName
	 */
	public LoadScreenAO(String name, String textureName) {
		this.setName(name);
		this.init(textureName,assetLoader2);
	}

	/**
	 * Creates an AbstractObject with only a name. Can be converted into other
	 * objects by adding several other variables.
	 *
	 * @param name
	 */
	public LoadScreenAO(String name) {
		this.setName(name);
	}

	/**
	 * Creates an empty AbstractObject. Can be converted into other objects by
	 * adding several other variables. This needs to be here for Box2dAO to
	 * extend multiple parameter constructors.
	 */
	public LoadScreenAO() {
	}

	/**
	 * Initializes information given from constructor.
	 */
	protected void init(String textureName,AssetLoader2 assetLoader2) {
	    this.assetLoader2 = assetLoader2;
		this.texture = assetLoader2.getTexture(textureName, true);
		setName(textureName);
		setTextureName(textureName);
		this.sprite = new Sprite(texture);
		this.setBounds(PPMDivide(sprite.getX()), PPMDivide(sprite.getY()), PPMDivide(sprite.getWidth()),
				PPMDivide(sprite.getHeight()));
		this.setWidth(PPMDivide(sprite.getWidth()));
		this.setHeight(PPMDivide(sprite.getHeight()));
	}

	protected void init(String textureName, AbstractStage stage, AssetLoader2 assetLoader2) {
		this.texture = assetLoader2.getTexture(textureName, true);
		setName(textureName);
		setTextureName(textureName);
		this.sprite = new Sprite(texture);
		this.setBounds(sprite.getX() / stage.getPPM(), sprite.getY() / stage.getPPM(),
				sprite.getWidth() / stage.getPPM(), sprite.getHeight() / stage.getPPM());
		this.setWidth(sprite.getWidth() / stage.getPPM());
		this.setHeight(sprite.getHeight() / stage.getPPM());
	}

	/**
	 * Resets the rotation of an object. The 'default' rotation is always -90.
	 */
	public void resetRotation() {
		setRotation(-90);
	}

	public String getTextureName() {
		return textureName;
	}

	public void setTextureName(String textureName) {
		this.textureName = textureName;
	}

	// /**
	// * *WARNING* Deprectaed / Legacy drawing method (Maybe?) *WARNING. Try to
	// * avoid using htis is other drawing methods are available.
	// */
	// @Override
	// public void draw(Batch batch, float parentAlpha) {
	// // TODO Auto-generated method stub
	// if (animationList.size() != 0) {
	// // Have to set size of animation not static (64 * 64)
	// batch.draw(animationHandler.renderAnimation(getAnimation(2), stateTime),
	// getX(), getY(), getWidth() / 2,
	// getHeight() / 2, 64f, 64f, getScaleX(), getScaleY(), getRotation(),
	// false);
	// stateTime = stateTime + Gdx.graphics.getDeltaTime();
	// } else {
	// batch.draw(sprite, getX(), getY(), getWidth() / 2, getHeight() / 2,
	// getWidth(), getHeight(), getScaleX(),
	// getScaleY(), getRotation());
	// }
	//
	// // if(body != null){
	// // setPosition(getBody().getPosition().x -
	// // (getWidth()/2),getBody().getPosition().y - (getHeight()/2));
	// // setRotation((float) Math.toDegrees(getBody().getAngle()));
	// // }
	// }

	/**
	 * *WARNING* Deprecated / Legacy hit detection *WARNING*. Try to avoid using
	 * this if other hit detection options are available.
	 */
	@Override
	public Actor hit(float x, float y, boolean touchable) {
		if (touchable && this.getTouchable() != Touchable.enabled)
			return null;
		return x >= getX() && x < getX() + getWidth() && y >= getY() && y < getY() + getHeight() ? this : null;

	}

	/**
	 * Sets the position of the actor's bottom left corner.
	 */
	@Override
	public void setPosition(float x, float y) {
		if (getX() != x || getY() != y) {
			setX(x);
			setY(y);

			positionChanged();

		}
	}

	/**
	 * Adds an Animation to animationList (ArrayList<Animation>). Gets added
	 * last on dynamic ArrayList.
	 *
	 * @param fileName
	 * @param frameCol
	 * @param frameRows
	 * @param aniSpeed
	 */
	public void addAnimation(String fileName, int frameCol, int frameRows, float aniSpeed) {
		animationList.add(animationHandler.loadAnimation(fileName, frameCol, frameRows, aniSpeed));
	}

	/**
	 * Returns the animation listed on (animationNumber) spot in array. Starts
	 * at 0.
	 *
	 * @param animationNumber
	 * @return Animation
	 */
	public AnimationObject getAnimation(int animationNumber) {
		return animationList.get(animationNumber);
	}

	/**
	 * Returns entire Texture object.
	 *
	 * @return texture object
	 */
	public Texture getTexture() {
		return texture;
	}

	/**
	 * Sets texture of object, automatically sets the width and height hit
	 * detection to the same width and height as texture.
	 *
	 * @param textureName
	 */
	public void setTexture(String textureName,AssetLoader2 assetLoader2) {
		this.texture = assetLoader2.getTexture(textureName, true);
		setWidth(this.texture.getWidth());
		setHeight(this.texture.getHeight());
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
		this.sprite = new Sprite(texture);
		this.setBounds(PPMDivide(sprite.getX()), PPMDivide(sprite.getY()), PPMDivide(sprite.getWidth()),
				PPMDivide(sprite.getHeight()));
		this.setWidth(PPMDivide(sprite.getWidth()));
		this.setHeight(PPMDivide(sprite.getHeight()));
	}

	/**
	 * Returns the height of currently applied texture.
	 *
	 * @return float
	 */
	public float getTextureHeight() {
		return getTexture().getHeight();
	}

	/**
	 * Returns the width of currently applied texture.
	 *
	 * @return float
	 */
	public float getTextureWidth() {
		return getTexture().getWidth();
	}

	/**
	 * Gets the width of object in pixels, this is the hit detection width as
	 * well. This width is not the same as texture width.
	 *
	 * @return float
	 */
	public float getObjectWidth() {
		return getWidth();
	}

	/**
	 * Sets the width of object in pixels, this is the hit detection width as
	 * well. This width is not the same as texture height.
	 *
	 * @param width
	 */
	public void setObjectWidth(int width) {
		setWidth(width);
	}

	/**
	 * Gets the height of object in pixels, this is the hit detection height as
	 * well. This height is not the same as texture height.
	 *
	 * @return float
	 */
	public float getObjectHeight() {
		return getHeight();
	}

	/**
	 * Sets the height of object in pixels, this is the hit detection height as
	 * well. This height is not the same as texture height.
	 *
	 * @param height
	 */
	public void setObjectHeight(int height) {
		setHeight(height);
	}

	/**
	 * Gets the specific statetime of this object Objects need to have specific
	 * statetimes in order to have a stand-alone animation timer.
	 *
	 * @return float
	 */
	public float getStateTime() {
		return stateTime;
	}

	/**
	 * Sets the statetime for this object only. Objects need to have specific
	 * statetimes in order to have a stand-alone animation timer.
	 *
	 * @param stateTime
	 */
	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	/**
	 * Gets an ArrayList of Animation object.
	 *
	 * @return ArrayList<Animation>
	 */
	public ArrayList<AnimationObject> getAnimationList() {
		return animationList;
	}

	/**
	 * Overwrites a current animation list, should not be used unless importing
	 * animationlist from another object.
	 *
	 * @param animationList
	 */
	public void setAnimationList(ArrayList<AnimationObject> animationList) {
		this.animationList = animationList;
	}

	// /**
	// * Gets action class for
	// *
	// * @return
	// */
	// public Action getAction() {
	// return this.action;
	// }

	/*
	 ********************************************************************************************************
	 * DO NOT CHANGE SETTERS BELOW THIS LINE TO BE PUBLIC INSTEAD OF PROTECTED!
	 * Protected betyder at kun subclasses til denne klasse kan hente dem Hvis
	 * du har brug for en setter her under, brug .getAction() og kald metoderne
	 * der inde. Findes metoderne ikke der, boer metoderne laves der inde og
	 * kaldes paa f.eks. aObject.setLife() DO NOT CHANGE SETTERS BELOW THIS LINE
	 * TO BE PUBLIC INSTEAD OF PROTECTED!
	 ********************************************************************************************************
	 */

	/*
	 * Do not make this public.
	 */
	protected Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public boolean isAnimationActive() {
		return isAnimationActive;
	}

	public void setAnimationActive(boolean isAnimationActive) {
		this.isAnimationActive = isAnimationActive;
	}

	/*
	 * Do not make this public.
	 */
	public int getActiveAnimationNumber() {
		return activeAnimationNumber;
	}

	public void setActiveAnimationNumber(int activeAnimationNumber) {
		this.activeAnimationNumber = activeAnimationNumber;
	}

	public int getLife() {
		return life;
	}

	/*
	 * Do not make this public.
	 */
	protected void setLife(int life) {
		this.life = life;
	}

	public float getPower() {
		return power;
	}

	/*
	 * Do not make this public.
	 */
	protected void setPower(float power) {
		this.power = power;
	}

	public int getDefense() {
		return defense;
	}

	/*
	 * Do not make this public.
	 */
	protected void setDefense(int defense) {
		this.defense = defense;
	}

	public int getArmor() {
		return armor;
	}

	/*
	 * Do not make this public.
	 */
	protected void setArmor(int armor) {
		this.armor = armor;
	}

	public int getResistance() {
		return resistance;
	}

	/*
	 * Do not make this public.
	 */
	protected void setResistance(int resistance) {
		this.resistance = resistance;
	}

	public int getDamage() {
		return damage;
	}

	/*
	 * Do not make this public.
	 */
	protected void setDamage(int damage) {
		this.damage = damage;
	}

	public int getLevel() {
		return level;
	}

	/*
	 * Do not make this public.
	 */
	protected void setLevel(int level) {
		this.level = level;
	}

	public String getType() {
		return type;
	}

	/*
	 * Do not make this public.
	 */
	protected void setType(String type) {
		this.type = type;
	}

	public int getMaxLife() {
		return maxLife;
	}

	/*
	 * Do not make this public.
	 */
	protected void setMaxLife(int maxLife) {
		this.maxLife = maxLife;
	}

	public int getMaxPower() {
		return maxPower;
	}

	/*
	 * Do not make this public.
	 */
	protected void setMaxPower(int maxPower) {
		this.maxPower = maxPower;
	}

	public int getSuperPower() {
		return superPower;
	}

	/*
	 * Do not make this public.
	 */
	protected void setSuperPower(int superPower) {
		this.superPower = superPower;
	}

	public int getMaxSuperPower() {
		return maxSuperPower;
	}

	/*
	 * Do not make this public.
	 */
	protected void setMaxSuperPower(int maxSuperPower) {
		this.maxSuperPower = maxSuperPower;
	}

	public int getExtraPower() {
		return extraPower;
	}

	/*
	 * Do not make this public.
	 */
	protected void setExtraPower(int extraPower) {
		this.extraPower = extraPower;
	}

	public int getMaxExtraPower() {
		return maxExtraPower;
	}

	/*
	 * Do not make this public.
	 */
	protected void setMaxExtraPower(int maxExtraPower) {
		this.maxExtraPower = maxExtraPower;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	/*
	 * Do not make this public.
	 */
	protected void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	public boolean isActive() {
		return isActive;
	}

	/*
	 * Do not make this public.
	 */
	protected void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isAlive() {
		return isAlive;
	}

	/*
	 * Do not make this public.
	 */
	protected void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public float getRange() {
		return range;
	}

	/*
	 * Do not make this public.
	 */
	protected void setRange(float range) {
		this.range = range;
	}

	protected int getValue() {
		return value;
	}

	/*
	 * Do not make this public.
	 */
	protected void setValue(int value) {
		this.value = value;
	}

	protected int getPrice() {
		return price;
	}

	/*
	 * Do not make this public.
	 */
	protected void setPrice(int price) {
		this.price = price;
	}

	/*
	 * Do not make this public.
	 */
	protected AnimationHandler getAnimationHandler() {
		return animationHandler;
	}

	// Do not make changes from protected to public or private on above getters
	// and setters!!

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (isAnimationActive()) {
			// Have to set size of animation not static (64 * 64)
			batch.draw(getAnimationHandler().renderAnimation(getAnimation(getActiveAnimationNumber()), getStateTime()),
					getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), getScaleX(), getScaleY(),
					getRotation() - 90, false);
			setStateTime(getStateTime() + Gdx.graphics.getDeltaTime());
		} else {
			batch.draw(getSprite(), getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(),
					getScaleX(), getScaleY(), getRotation());
		}
	}
}