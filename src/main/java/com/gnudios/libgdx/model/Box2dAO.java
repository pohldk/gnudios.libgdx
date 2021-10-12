package com.gnudios.libgdx.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.gnudios.libgdx.handler.BodyHandler;
import com.gnudios.libgdx.model.assets.AssetLoader2;
import com.gnudios.libgdx.view.AbstractStage;

import static com.gnudios.libgdx.general.helpers.Calculator.PPMDivide;

public class Box2dAO extends AbstractObject {

    private Body body;
    private BodyHandler bodyHandler;
    private World world;
    private boolean isStatic;
    private BodyShape shape;
    private RopeJointDef ropeJointDef;
    private DistanceJointDef distanceJointDef;
    private Joint joint;
    private boolean flipX = false;
    private boolean flipY = false;
    float adjustSpeed = 10.0f; // Default

    public Box2dAO() {

    }

    /**
     * Automatically sets hit detection width and height to same as texture
     * size. Automatically adds physics to this object. Automatically adds this
     * object to stage.
     *
     * @param shape
     * @param textureName
     * @param world
     * @param isStatic
     * @param posX
     * @param posY
     */

    public Box2dAO(BodyShape shape, String textureName, World world, boolean isStatic, float posX, float posY,
                   AbstractStage gameStage, BodyHandler bodyHandler, AssetLoader2 assetLoader2) {
        // this.setName(name);
        this.setShape(shape);
        this.init(textureName, gameStage, assetLoader2);
        this.world = world;
        this.isStatic = isStatic;
        this.setX(PPMDivide(posX));
        this.setY(PPMDivide(posY));
        this.bodyHandler = bodyHandler;
        createBody(shape);
        gameStage.addActor(this);

        // setWidth(texture.getWidth());
    }

    /**
     * Automatically sets hit detection width and height to same as texture size
     * Automatically adds physics to this object
     *
     * @param shape
     * @param textureName
     * @param world
     * @param isStatic
     * @param posX
     * @param posY
     */
    public Box2dAO(BodyShape shape, String textureName, World world, boolean isStatic, float posX, float posY,
                   BodyHandler bodyHandler, AssetLoader2 assetLoader2) {
        // this.setName(name);
        this.setShape(shape);
        this.init(textureName, assetLoader2);
        this.world = world;
        this.isStatic = isStatic;
        this.setX(PPMDivide(posX));
        this.setY(PPMDivide(posY));
        this.bodyHandler = bodyHandler;
        createBody(shape);

        // setWidth(texture.getWidth());
    }

    /**
     * Automatically sets hit detection width and height to same as texture size
     *
     * @param shape
     * @param textureName
     * @param world
     * @param isStatic
     */
    public Box2dAO(BodyShape shape, String textureName, World world, boolean isStatic, BodyHandler bodyHandler, AssetLoader2 assetLoader2) {
        this.setShape(shape);
        init(textureName, assetLoader2);
        this.world = world;
        this.isStatic = isStatic;
        this.bodyHandler = bodyHandler;
        createBody(shape);

        // setWidth(texture.getWidth());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Set position before drawing it!! :D
        if (isActive()) {
            if (getBody() != null) {

                setPosition(getBody().getPosition().x - (getWidth() / 2), getBody().getPosition().y - (getHeight() / 2));
                setRotation((float) Math.toDegrees(getBody().getAngle()));

                if (isAnimationActive()) {
                    // Have to set size of animation not static (64 * 64)
                    batch.draw(
                            getAnimationHandler().renderAnimation(getAnimation(getActiveAnimationNumber()), getStateTime()),
                            getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), isFlipY() ? -1 * getScaleX() : 1 * getScaleX(),
                            isFlipX() ? -1 * getScaleY() : 1 * getScaleY(), getRotation() - 90, false);
                    setStateTime(getStateTime() + Gdx.graphics.getDeltaTime());
                    if (getAnimation(getActiveAnimationNumber()).isRunOnce() && getAnimation(getActiveAnimationNumber()).isAnimationFinished(getStateTime())) {
                        setActiveAnimationNumber(getDefaultAnimationNumber());
                    }
                } else {
                    batch.draw(getSprite(), getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(),
                            getScaleX(), getScaleY(), getRotation());
                }

            }
        }

        if(getLife() == 100){
            destroyJoint();
            kill();
        }
    }


    public void runAnimationOnce(int animationNumber) {
        setActiveAnimationNumber(animationNumber);
        getAnimation(animationNumber).setStartTimeAni(getStateTime());
    }

    private void createBody(BodyShape shape) {
        switch (shape) {
            case SHAPE_RECTANGLE:
                this.body = bodyHandler.createRectangleBody(this, world, isStatic);
                break;
            case SHAPE_CIRCLE:
                this.body = bodyHandler.createCircleBody(this, world, isStatic);
                break;
            case SHAPE_EDGE:
                this.body = bodyHandler.createEdgeBody(this, world, isStatic);
                break;
            case SHAPE_CHAIN:
                this.body = bodyHandler.createChainBody(this, world, isStatic);
                break;
        }
    }

    public void createDistanceJoint(Box2dAO box2dAOLinkObject, float f) {
        distanceJointDef = new DistanceJointDef();
        distanceJointDef.bodyA = getBody();
        distanceJointDef.bodyB = box2dAOLinkObject.getBody();
        distanceJointDef.length = f;
        distanceJointDef.localAnchorA.set(0.0f, 0.0f);
        distanceJointDef.localAnchorB.set(0, 0);
        distanceJointDef.frequencyHz = 2f;
        joint = world.createJoint(distanceJointDef);
    }

    public void createRopeJoint(Box2dAO box2dAOLinkObject, float f) {
        ropeJointDef = new RopeJointDef();
        ropeJointDef.bodyA = getBody();
        ropeJointDef.bodyB = box2dAOLinkObject.getBody();
        ropeJointDef.maxLength = f;
        ropeJointDef.localAnchorA.set(0.0f, 0.0f);
        ropeJointDef.localAnchorB.set(0, 0);
        joint = world.createJoint(ropeJointDef);
    }

    public void createWeldJoint(Box2dAO box2dAOLinkObject, float f) {
        WeldJointDef weldJointDef = new WeldJointDef();
        weldJointDef.bodyA = getBody();
        weldJointDef.bodyB = box2dAOLinkObject.getBody();
        weldJointDef.frequencyHz = 0;
        weldJointDef.localAnchorA.set(0, 0);
        weldJointDef.localAnchorB.set(0, 0);
        weldJointDef.collideConnected = true;
        joint = world.createJoint(weldJointDef);
    }

    public void destroyJoint() {

        if (joint != null) {
            world.destroyJoint(joint);
        }
    }

    public void setRopeLength(int maxLength) {
        ropeJointDef.maxLength = maxLength;
    }

    public void setDistanceLength(int length) {
        ropeJointDef.maxLength = length;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public void setCircleSize(float radius) {
        bodyHandler.setRadius(this, radius);
        setSize(getRadius() * 2, getRadius() * 2);
    }

    public void setRectangleSize(float width, float height) {
        setWidth(width);
        setHeight(height);
        this.body = bodyHandler.setSizeRectangle(this, world, isStatic, width, height);
    }

    public float getRadius() {
        return bodyHandler.getRadius(this);
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void destroy() {
        this.getWorld().destroyBody(this.getBody());
        this.remove();
    }

    public void kill() {
        this.getWorld().destroyBody(this.getBody());
        this.remove();
    }

    public void removeBox2dAO() {
        this.getWorld().destroyBody(this.getBody());
        this.remove();
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean isStatic) {
        if (body != null) {
            this.isStatic = isStatic;
            if (isStatic) {
                body.setType(BodyType.StaticBody);
            } else {
                body.setType(BodyType.DynamicBody);
            }
        } else {
            System.out.println("Failed to change Body.Type, due to the body is Null");
        }
    }

    public BodyShape getShape() {
        return shape;
    }

    public void setShape(BodyShape shape) {
        this.shape = shape;
    }

    public void updateWidth(float width) {
        setWidth(width);
        this.getWorld().destroyBody(this.getBody());
        createBody(shape);
    }

    public void updateHeight(float height) {
        setHeight(height);
        this.getWorld().destroyBody(this.getBody());
        createBody(shape);
    }

    public void updateWidthHeight(float heightAndHeight) {
        setHeight(heightAndHeight);
        setWidth(heightAndHeight);
        this.getWorld().destroyBody(this.getBody());
        createBody(shape);
    }

    public void adjustCamToPos(float x, float y) {
        setTransform(getPosition().x + (((getPosition().x - x) / -getAdjustSpeed() * Gdx.graphics.getDeltaTime() * 60.0f)),
                getPosition().y + (((getPosition().y - y) / -getAdjustSpeed() * Gdx.graphics.getDeltaTime() * 60.0f)), body.getAngle());
        //this.position.x = this.position.x + (((this.position.x - x) / -getAdjustSpeed() * Gdx.graphics.getDeltaTime() * 60.0f));
    }

    public float getAdjustSpeed() {
        return adjustSpeed;
    }

    public void setAdjustSpeed(float adjustSpeed) {
        this.adjustSpeed = adjustSpeed;
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

    public void destroyBody() {
        getWorld().destroyBody(getBody());
    }

    public boolean equals(String name) {
        return getName().equalsIgnoreCase(name);
    }

    public boolean equalsIgnoreCase(String name) {
        return getName().equalsIgnoreCase(name);
    }

    public void setTransform(float x, float y, float angle) {
        getBody().setTransform(x, y, angle);
    }

    public void setTransform(Vector2 position, float angle) {
        getBody().setTransform(position, angle);
    }

    public Vector2 getPosition() {
        return getBody().getPosition();
    }
}
