package com.gnudios.libgdx.model;

import static com.gnudios.libgdx.SSConfiguration.PPM;

public class KryoActorAO2 {

	private int id;
	private String textureName = "";
	private String name = "";
	private String bodyShape;
	private int connectionId;
	private String userName = "";
	private float x, y;
	private float width, height;
	private float angle;
	private float pulseX, pulseY;
	private float gridX, gridY;
	private boolean isStatic;
	private boolean isSensor;
	private boolean isTouchable;


	public KryoActorAO2() {
		// TODO Auto-generated constructor stub
	}

	public KryoActorAO2(String bodyShape, String textureName, boolean isStatic, float x, float y, float width, float height){
		this.textureName = textureName;
		this.name = textureName;
		this.bodyShape = bodyShape;
		this.connectionId = connectionId;
		this.userName = userName;
		this.x = x / PPM;
		this.y = y / PPM;
		this.width = width / PPM;
		this.height = height / PPM;
		this.angle = angle;
		this.isStatic = isStatic;
		this.isSensor = isSensor;
	}

	public KryoActorAO2(String textureName, int connectionId, String userName, float x, float y, float width, float height, float angle){
		this.textureName = textureName;
		this.name = textureName;
		this.connectionId = connectionId;
		this.userName = userName;
		this.x = x / PPM;
		this.y = y / PPM;
		this.width = width / PPM;
		this.height = height / PPM;
		this.angle = angle;
	}

	public KryoActorAO2(Box2dAO box2dAO) {
		this.textureName = box2dAO.getTextureName();
		this.name = textureName;
		this.x = box2dAO.getBody().getPosition().x;
		this.y = box2dAO.getBody().getPosition().y;
		this.width = box2dAO.getWidth();
		this.height = box2dAO.getHeight();
		this.angle = box2dAO.getRotation();
		this.connectionId = box2dAO.getConnectionId();
		this.userName = box2dAO.getUserName();
		this.pulseX = box2dAO.getBody().getLinearVelocity().x;
		this.pulseY = box2dAO.getBody().getLinearVelocity().y;
		this.gridX = box2dAO.getGrid().x;
		this.gridY = box2dAO.getGrid().y;
		this.isStatic = box2dAO.isStatic();
		//For testing maybe it should have its own box2dName, this is made for the collision-detector
		//class, so the engine is able to make collision detection on stuff loaded from a jsonfile
		//class, so the engine is able to make collision detection on stuff loaded from a jsonfile
		//since the client name is never used with in the json-map-loader method, this should be fine
	}

	public KryoActorAO2(ActorAO actorAO) {
		this.textureName = actorAO.getTextureName();
		this.x = actorAO.getX();
		this.y = actorAO.getY();
		this.name = actorAO.getTextureName();
		this.width = actorAO.getWidth();
		this.height = actorAO.getHeight();
		this.angle = actorAO.getRotation();
		this.connectionId = actorAO.getConnectionId();
		this.userName = actorAO.getUserName();
		this.pulseX = 0;
		this.pulseY = 0;
		this.id = actorAO.getLife();
		this.gridX = actorAO.getGrid().x;
		this.gridY = actorAO.getGrid().y;
		this.isStatic = actorAO.isAlive();
	}

	public KryoActorAO2(String textureName, float x, float y){
		this.textureName = textureName;
		this.name = textureName;
		this.x = x;
		this.y = y;
	}

	public void setGrid(float x, float y){
		this.gridX = x;
		this.gridY = y;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTextureName() {
		return textureName;
	}

	public void setTextureName(String textureName) {
		this.textureName = textureName;
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

	public float getPulseX() {
		return pulseX;
	}

	public void setPulseX(float pulseX) {
		this.pulseX = pulseX;
	}

	public float getPulseY() {
		return pulseY;
	}

	public void setPulseY(float pulseY) {
		this.pulseY = pulseY;
	}

	public float getGridX() {
		return gridX;
	}

	public void setGridX(float gridX) {
		this.gridX = gridX;
	}

	public float getGridY() {
		return gridY;
	}

	public void setGridY(float gridY) {
		this.gridY = gridY;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean aStatic) {
		isStatic = aStatic;
	}

	public boolean isSensor() {
		return isSensor;
	}

	public void setSensor(boolean sensor) {
		isSensor = sensor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBodyShape() {
		return bodyShape;
	}

	public void setBodyShape(String bodyShape) {
		this.bodyShape = bodyShape;
	}

	public boolean isTouchable() {
		return isTouchable;
	}

	public void setTouchable(boolean touchable) {
		isTouchable = touchable;
	}
}