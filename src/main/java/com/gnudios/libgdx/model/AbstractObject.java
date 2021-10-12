package com.gnudios.libgdx.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.gnudios.libgdx.SSConfiguration;
import com.gnudios.libgdx.model.assets.AnimationHandler;
import com.gnudios.libgdx.view.AbstractStage;
import com.gnudios.libgdx.model.assets.AssetLoader2;

import java.util.ArrayList;

import static com.gnudios.libgdx.general.helpers.Calculator.PPMDivide;

public class AbstractObject extends Actor {
    // Class setup.
    private AssetLoader2 assetLoader2;
    private TextureRegion texture;
    private String textureName;
    private ArrayList<AnimationObject> animationList = new ArrayList<>();
    private ModelAction action = new ModelAction(this);
    private Sprite sprite;
    private int connectionId;
    private String userName;
    private float stateTime = 0;
    private boolean isAnimationActive = false;
    private int activeAnimationNumber = 0;
    private int defaultAnimationNumber = 0;

    // Variables for ingame.
    private int life = -1;
    private int maxLife;
    private float power;
    private float speed;
    private float speedInit;
    private int maxPower;
    private int superPower;
    private int maxSuperPower;
    private int extraPower;
    private int maxExtraPower;
    private int defense;
    private int armor;
    private int resistance;
    private int damage;
    private int coinsCollected;
    private int maxCoins;
    private int level;
    private int maxLevel;
    private boolean actionMoveActive;
    private boolean placedTurnEnd;
    private float range;
    private String type;
    private boolean isActive = true;
    private boolean isAlive;
    private boolean scalerActive;
    private int value;
    private int price;
    private int jump;
    private int maxJump;
    private float jumpPower;
    private float jumpPowerInit;
    private int disability;
    private Vector2 grid = new Vector2();
    private AnimationHandler animationHandler;


    /**
     * Automatically sets hit detection width and height to same as texture size
     *
     * @param name
     * @param textureName
     */
    public AbstractObject(String name, String textureName,AssetLoader2 assetLoader2) {
        this.assetLoader2 = assetLoader2;
        this.setName(name);
        this.init(textureName,assetLoader2);
    }

    /**
     * Creates an AbstractObject with only a name. Can be converted into other
     * objects by adding several other variables.
     *
     * @param name
     */
    public AbstractObject(String name) {
        this.setName(name);
    }

    /**
     * Creates an empty AbstractObject. Can be converted into other objects by
     * adding several other variables. This needs to be here for Box2dAO to
     * extend multiple parameter constructors.
     */
    public AbstractObject() {
    }

    /**
     * Initializes information given from constructor.
     */
    protected void init(String textureName, AssetLoader2 assetLoader2) {
        this.assetLoader2 = assetLoader2;
        this.texture = this.assetLoader2.getTexture(textureName);
        setName(textureName);
        setTextureName(textureName);
        this.sprite = new Sprite(texture);
        this.setBounds(PPMDivide(sprite.getX()), PPMDivide(sprite.getY()), PPMDivide(sprite.getWidth()),
                PPMDivide(sprite.getHeight()));
        this.setWidth(PPMDivide(sprite.getWidth()));
        this.setHeight(PPMDivide(sprite.getHeight()));
        this.animationHandler = assetLoader2.getAnimationHandler();
        this.setAlive(true);
    }

    protected void init(String textureName, AbstractStage stage, AssetLoader2 assetLoader2) {
        this.assetLoader2 = assetLoader2;
        this.texture = this.assetLoader2.getTexture(textureName);
        setName(textureName);
        setTextureName(textureName);
        this.sprite = new Sprite(texture);
        this.setBounds(sprite.getX() / SSConfiguration.PPM, sprite.getY() / SSConfiguration.PPM,
                sprite.getWidth() / SSConfiguration.PPM, sprite.getHeight() / SSConfiguration.PPM);
        this.setWidth(sprite.getWidth() / SSConfiguration.PPM);
        this.setHeight(sprite.getHeight() / SSConfiguration.PPM);
        this.animationHandler = assetLoader2.getAnimationHandler();
        this.setAlive(true);
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

    // /** Sets the position of the actor's bottom left corner. */
    // @Override
    // public void setPosition(float x, float y) {
    // if (getX() != x || getY() != y) {
    // setX(x);
    // setY(y);
    //
    // positionChanged();
    //
    // }
    // }

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
    public TextureRegion getTexture() {
        return texture;
    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
        this.sprite = new Sprite(texture);
        this.setBounds(PPMDivide(sprite.getX()), PPMDivide(sprite.getY()), PPMDivide(sprite.getWidth()),
                PPMDivide(sprite.getHeight()));
        this.setWidth(PPMDivide(sprite.getWidth()));
        this.setHeight(PPMDivide(sprite.getHeight()));
    }

    /**
     * Sets texture of object, automatically sets the width and height hit
     * detection to the same width and height as texture.
     *
     * @param textureName
     */
    public void setTexture(String textureName) {
        this.texture = assetLoader2.getTexture(textureName);
        this.sprite = new Sprite(texture);
        setWidth(this.texture.getRegionWidth());
        setHeight(this.texture.getRegionHeight());
    }

    /**
     * Sets texture of object, automatically sets the width and height hit
     * detection to the same width and height as texture.
     *
     * @param pixmap
     */
//    public void setTexture(Pixmap pixmap) {
//        Texture tempTex = new Texture(pixmap);
//        //TextureRegion texRegion = new TextureRegion();
//        //texRegion.setTexture(tempTex);
//        //this.texture = texRegion;
//        this.sprite = new Sprite(texture);
//        setWidth(tempTex.getWidth());
//        setHeight(tempTex.getHeight());
//    }

    /**
     * Returns the height of currently applied texture.
     *
     * @return float
     */
    public float getTextureHeight() {
        return getTexture().getRegionHeight();
    }

    /**
     * Returns the width of currently applied texture.
     *
     * @return float
     */
    public float getTextureWidth() {
        return getTexture().getRegionWidth();
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

    /**
     * Gets action class for
     *
     * @return
     */
    public ModelAction getAction() {
        return this.action;
    }

    public boolean isActionMoveActive() {
        return actionMoveActive;
    }

    public void setActionMoveActive(boolean actionMoveActive) {
        this.actionMoveActive = actionMoveActive;
    }

    public boolean isPlacedTurnEnd() {
        return placedTurnEnd;
    }

    public void setPlacedTurnEnd(boolean placedTurnEnd) {
        this.placedTurnEnd = placedTurnEnd;
    }

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

    public int getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public int getDefaultAnimationNumber() {
        return defaultAnimationNumber;
    }

    public void setDefaultAnimationNumber(int defaultAnimatioNumber) {
        this.defaultAnimationNumber = defaultAnimatioNumber;
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
    public void setLife(int life) {
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
    //i did naughty stuff here
    public void setDefense(int defense) {
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
    public void setType(String type) {
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

    public int getMaxCoins() {
        return maxCoins;
    }

    /*
     * Do not make this public.
     */
    protected void setMaxCoins(int maxCoins) {
        this.maxCoins = maxCoins;
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
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isAlive() {
        return isAlive;
    }

    /*
     * Do not make this public.
     */
    //i did naughty stuff here
    public void setAlive(boolean isAlive) {
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

    public int getCoinsCollected() {
        return coinsCollected;
    }

    /*
     * Do not make this public.
     */
    protected void setCoinsCollected(int coinsCollected) {
        this.coinsCollected = coinsCollected;
    }

    /*
     * Do not make this public.
     */
    protected AnimationHandler getAnimationHandler() {
        return animationHandler;
    }

    // Do not make changes from protected to public or private on above getters
    // and setters!!

    public int getJump() {
        return jump;
    }

    public void setJump(int jump) {
        this.jump = jump;
    }

    public int getMaxJump() {
        return maxJump;
    }

    public void setMaxJump(int maxJump) {
        this.maxJump = maxJump;
    }

    public float getJumpPower() {
        return jumpPower;
    }

    public void setJumpPower(float jumpPower) {
        this.jumpPower = jumpPower;
    }

    public float getJumpPowerInit() {
        return jumpPowerInit;
    }

    public void setJumpPowerInit(float jumpPowerInit) {
        setJumpPower(jumpPowerInit);
        this.jumpPowerInit = jumpPowerInit;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeedInit() {
        return speedInit;
    }

    public void setSpeedInit(float speedInit) {
        this.setSpeed(speedInit);
        this.speedInit = speedInit;
    }

    public void setGrid(int x, int y) {
        this.grid.add(x, y);
    }

    public void setGrid(Vector2 grid) {
        this.grid = grid;
    }

    public Vector2 getGrid() {
        return grid;
    }

    public int getDisability() {
        return disability;
    }

    public void setDisability(int disability) {
        this.disability = disability;
    }

    public boolean isScalerActive() {
        return scalerActive;
    }

    public void setScalerActive(boolean scalerActive) {
        this.scalerActive = scalerActive;
    }

}