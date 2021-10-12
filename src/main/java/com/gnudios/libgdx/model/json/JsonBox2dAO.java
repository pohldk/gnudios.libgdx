package com.gnudios.libgdx.model.json;

import com.gnudios.libgdx.model.Box2dAO;
import com.gnudios.libgdx.model.BodyShape;

public class JsonBox2dAO {

    private String textureName = "";
    private int connectionId;
    private String userName;
    private String shape;
    private float x, y;
    private float width, height;
    private boolean isStatic;
    private float angle;

    public JsonBox2dAO() {
        // TODO Auto-generated constructor stub
    }

    public JsonBox2dAO(Box2dAO box2dAO) {
        textureName = box2dAO.getTextureName();
        shape = box2dAO.getShape().toString();
        x = box2dAO.getX();
        y = box2dAO.getY();
        width = box2dAO.getWidth();
        height = box2dAO.getHeight();
        isStatic = box2dAO.isStatic();
        angle = box2dAO.getRotation();
        connectionId = box2dAO.getConnectionId();
        userName = box2dAO.getUserName();
        //For testing maybe it should have its own box2dName, this is made for the collision-detector
        //class, so the engine is able to make collision detection on stuff loaded from a jsonfile
        //since the client name is never used with in the json-map-loader method, this should be fine
    }

    public JsonBox2dAO(String textureName, BodyShape shape, float x, float y, float width, float height, boolean isStatic,
                       float angle) {
        super();
        this.textureName = textureName;
        this.shape = shape.toString();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isStatic = isStatic;
        this.angle = angle;
    }

    public JsonBox2dAO(String textureName, BodyShape shape, float x, float y, float width, float height, boolean isStatic,
                       float angle, int connectionId, String userName) {
        super();
        this.textureName = textureName;
        this.shape = shape.toString();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isStatic = isStatic;
        this.angle = angle;
        this.connectionId = connectionId;
        this.userName = userName;
    }

    public String getTextureName() {
        return textureName;
    }

    public void setTextureName(String textureName) {
        this.textureName = textureName;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

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

}