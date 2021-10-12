package com.gnudios.libgdx.view;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.gnudios.libgdx.handler.BodyHandler;
import com.gnudios.libgdx.SSConfiguration;
import com.gnudios.libgdx.model.Box2dAO;
import com.gnudios.libgdx.model.AnimationObject;
import com.gnudios.libgdx.model.GameObjectAO;
import com.gnudios.libgdx.model.assets.AssetLoader2;

import java.util.ArrayList;
import java.util.Random;

/**
 * RandomSpawnGenerator updates a 2-dimensional matrix of "spawn boxes", such
 * that the boundary of spawn boxes always covers the entire camera. An initial
 * boundary is created determined by verticalExtension and horisontalExtension.
 * Spawn boxes will not be created to the left of the camera, except for the
 * initial boundary. Spawn box columns too far to the left of the camera are
 * continuously deleted. (including the objects) createSpawnBox is where the
 * circumstances for an given spawned object is determined.
 */
public class RandomSpawnGenerator {

	protected int minimum_air_height = 200;
	protected Camera camera;
	protected World world;
	protected AbstractStage stage;
	protected BodyHandler bodyHandler;
	protected Random rand = new Random();
	private AssetLoader2 assetLoader2;

	// private int horizontalBoxReached; // should never go down
	// private int verticalBoxReached; // should never go down
	protected int currentHorizontalBox;
	protected int currentVerticalBox;

	// These values suits each other - we should not change them
	protected float spawnBoxSize = 1000;
	protected int verticalExtension = 4; // spawns objects x spawnBoxSizes ahead
	// of the camera
	protected int horisontalExtension = 3; // spawns objects x spawnBoxSizes ahead
	// of the camera
	protected int maximumSpawnBoxColumns = 6; // defines when columns gets deleted
	protected int maximumSpawnBoxRows = 8; // defines when rows gets deleted
	protected int horisontalDisplacement = 3; // displaces the spawn boxes x
	// spawnBoxSizes to prevent spawns
	// on top of starting point
	protected int verticalDisplacemnt = 15; // changing the offset of the left offset of the screens x-axis,
	// which is used to prevent objects from being deleted before they leave the screens

	// One spawnbox is an array of the spawned objects it contains
	// spawnBoxes is a 2-dimensional matrix of spawnboxes
	protected ArrayList<ArrayList<ArrayList<Box2dAO>>> spawnBoxes = new ArrayList<>();

	// killObjects contain objects to be killed - see more information at
	// killRequestedObjects
	protected ArrayList<Box2dAO> killObjects = new ArrayList<>();

	// spawnObjects contains single instances of the objects that needs to spawn
	// multiple times in randomgenerator.
	protected ArrayList<GameObjectAO> spawnObjects = new ArrayList<>();

	public RandomSpawnGenerator(World world, Camera camera, AbstractStage stage, BodyHandler bodyHandler,AssetLoader2 assetLoader2) {
		this.assetLoader2 = assetLoader2;
		this.camera = camera;
		this.world = world;
		this.stage = stage;
		this.bodyHandler = bodyHandler;

		// this.spawnBoxSize = spawnBoxSize;
		initialiseSpawningBoxes();
		createSpawnObjects();
	}

	/**
	 * Resets the spawning boxes and the contained objects. Should be called
	 * whenever the game resets.
	 */
	public void resetRandomSpawnGenerator() {
		clearRandomSpawnGenerator();
		// horizontalBoxReached = 0;
		// verticalBoxReached = 0;
		currentHorizontalBox = 0;
		currentVerticalBox = 0;

		initialiseSpawningBoxes();
	}

	/**
	 * Deletes all spawning boxes and the contained objects.
	 */
	public void clearRandomSpawnGenerator() {

		while (spawnBoxes.size() > 0) {
			deleteSpawnBoxColumn();
		}
	}

	/**
	 * Kills an object, and deletes in from the spawnBox, during the next update
	 * step.
	 */
	public void killObject(Box2dAO AO) {

		killObjects.add(AO);
	}

	/**
	 * Kills all requested objects, called by killObject.
	 * <p>
	 * Objects with bodies, attached to the world, cannot be destroyed during a
	 * world simulation step. Hence objects must be destroyed "outside" the
	 * world simulation step. Classes like the CollisionHandler operate during
	 * the world simulation step. Hence the object cannot be destroyed directly,
	 * but must be added to a queue of requested killed objects.
	 * <p>
	 * Objects are killed as the RandomSpawnGenerator updates.
	 */
	private void killRequestedObjects() {

		// deletes all objects from behind ;)
		for (int i = killObjects.size() - 1; i >= 0; i--) {
			Box2dAO AO = killObjects.get(i);
			// stage.deleteThisBox2dAO(AO);
			killObjects.remove(i);
			AO.kill();
		}
	}

	/**
	 * Creates an initial boundary of spawn boxes, determined by
	 * verticalExtension and horisontalExtension. These values should always
	 * ensure, that the extension causes the boundary of spawn boxes, determined
	 * by spawnBoxSize, covers the entire camera.
	 */
	private void initialiseSpawningBoxes() {

		for (int x = 0; x < horisontalExtension; x++) {

			ArrayList<ArrayList<Box2dAO>> spawnBoxColumn = new ArrayList<>();

			for (int y = 0; y < verticalExtension; y++) {
				ArrayList<Box2dAO> spawnBox = createSpawnBox(x + horisontalDisplacement, y);

				spawnBoxColumn.add(spawnBox);
			}
			spawnBoxes.add(spawnBoxColumn);
		}
	}

	/**
	 * Should be called continuously as the game updates.
	 * <p>
	 * It measures the current position of the camera and handles spawnBoxes
	 * depending of the camera position.
	 * <p>
	 * Adds an extended row of spawnboxes, as the camera rises.
	 * <p>
	 * Adds an extended column of spawnboxes, as the camera moves right. (Always
	 * adds a rightmost column)
	 * <p>
	 * Deletes the leftmost column of spawnboxes, if the number of columns
	 * exceeds maximumSpawnBoxColumns.
	 */
	public void updateRandomSpawns() {

		killRequestedObjects();

		float cameraXposLeft = ((camera.position.x * SSConfiguration.PPM - SSConfiguration.GAME_WIDTH * camera.zoom / 2f) - verticalDisplacemnt * SSConfiguration.PPM);
		float cameraYposBottom = (camera.position.y * SSConfiguration.PPM - SSConfiguration.GAME_HEIGHT * camera.zoom / 2f);

		if ((int) Math.floor(cameraXposLeft / spawnBoxSize) > currentHorizontalBox) {

			// moved one box to the right
			currentHorizontalBox = (int) Math.floor(cameraXposLeft / spawnBoxSize);
			shiftSpawnBoxesRight();

		}

		if ((int) Math.floor(cameraXposLeft / spawnBoxSize) < currentHorizontalBox) {
			// moved one box to the left
			currentHorizontalBox = (int) Math.floor(cameraXposLeft / spawnBoxSize);

		}

		if ((int) Math.floor(cameraYposBottom / spawnBoxSize) > currentVerticalBox) {
			// moved one box up
			currentVerticalBox = (int) Math.floor(cameraYposBottom / spawnBoxSize);
			shiftSpawnBoxesUp();

		}

		if ((int) Math.floor(cameraYposBottom / spawnBoxSize) < currentVerticalBox) {

			// moved one box down
			currentVerticalBox = (int) Math.floor(cameraYposBottom / spawnBoxSize);
			shiftSpawnBoxesDown();
		}

		currentHorizontalBox = (int) Math.floor(cameraXposLeft / spawnBoxSize);
		currentVerticalBox = (int) Math.floor(cameraYposBottom / spawnBoxSize);
	}

	private void shiftSpawnBoxesRight() {

		// creates a column to the right
		ArrayList<ArrayList<Box2dAO>> spawnBoxColumnAdd = new ArrayList<>();
		for (int y = 0; y < maximumSpawnBoxRows; y++) {
			ArrayList<Box2dAO> spawnBox = createSpawnBox(
					currentHorizontalBox + horisontalDisplacement + horisontalExtension - 1,
					y - maximumSpawnBoxRows + verticalExtension + currentVerticalBox);

			spawnBoxColumnAdd.add(spawnBox);
		}
		spawnBoxes.add(spawnBoxColumnAdd);

		// deletes leftmost column
		if (maximumSpawnBoxColumns < spawnBoxes.size()) {
			ArrayList<ArrayList<Box2dAO>> spawnBoxColumnDelete = spawnBoxes.get(0);

			for (ArrayList<Box2dAO> spawnBox : spawnBoxColumnDelete) {

				for (Box2dAO spawnObject : spawnBox) {

					spawnObject.kill();
				}
				spawnBox.clear();
			}
			spawnBoxColumnDelete.clear();
			spawnBoxes.remove(0);
		}
	}

	// private void shiftSpawnBoxesLeft(){
	//
	// }

	private void shiftSpawnBoxesUp() {

		// add row of spawn boxes on top - delete row of spawn boxes on bottom
		for (int x = spawnBoxes.size() - 1; x >= 0; x--) {

			ArrayList<ArrayList<Box2dAO>> spawnBoxColumn = spawnBoxes.get(x);
			ArrayList<Box2dAO> spawnBox = createSpawnBox(
					currentHorizontalBox + horisontalDisplacement + horisontalExtension + x - spawnBoxes.size(),
					currentVerticalBox + verticalExtension - 1);
			spawnBoxColumn.add(spawnBox);

			if (maximumSpawnBoxRows < spawnBoxColumn.size()) {
				for (Box2dAO spawnObject : spawnBoxColumn.get(0)) {
					spawnObject.kill();
				}
				spawnBoxColumn.get(0).clear();
				spawnBoxColumn.remove(0);
			}
		}
	}

	private void shiftSpawnBoxesDown() {

		// add row of spawn boxes on top - delete row of spawn boxes on bottom
		for (int x = spawnBoxes.size() - 1; x >= 0; x--) {

			ArrayList<ArrayList<Box2dAO>> spawnBoxColumn = spawnBoxes.get(x);
			ArrayList<Box2dAO> spawnBox = createSpawnBox(
					currentHorizontalBox + horisontalDisplacement + horisontalExtension + x - spawnBoxes.size(),
					verticalExtension + currentVerticalBox - maximumSpawnBoxRows);
			spawnBoxColumn.add(0, spawnBox);

			if (maximumSpawnBoxRows < spawnBoxColumn.size()) {
				for (Box2dAO spawnObject : spawnBoxColumn.get(spawnBoxColumn.size() - 1)) {
					spawnObject.kill();

				}
				spawnBoxColumn.get(spawnBoxColumn.size() - 1).clear();
				spawnBoxColumn.remove(spawnBoxColumn.size() - 1);
			}
		}
	}

	/**
	 * Deletes the first spawnBox column and the ontained object, which will
	 * always be the leftmost one. It is called, when the 2-dimensional array
	 * spawnBoxes exceeds maximumSpawnBoxColumns.
	 */
	private void deleteSpawnBoxColumn() {

		ArrayList<ArrayList<Box2dAO>> spawnBoxColumn = spawnBoxes.get(0);

		for (ArrayList<Box2dAO> spawnBox : spawnBoxColumn) {

			for (Box2dAO spawnObject : spawnBox) {

				spawnObject.kill();
			}
			spawnBox.clear();
		}
		spawnBoxColumn.clear();
		spawnBoxes.remove(0);
	}

	/**
	 * Creates the spawnBox - here we define the circumstances in which objects
	 * should be added, and also define the objects to be added.
	 */
	private ArrayList<Box2dAO> createSpawnBox(int x, int y) {
		ArrayList<Box2dAO> spawnBox = new ArrayList<>();


		for (GameObjectAO goAO : spawnObjects) {
			goAO.setTouchable(Touchable.disabled);
			goAO.setVisible(false);
			spawnBox.addAll(createSpawn(x, y, goAO));
		}

		return spawnBox;
	}


	/**
	 * Overwrite this class
	 */
	public void createSpawnObjects() {
		// This code gets overwritten.

//		GameObjectAO coin = new GameObjectAO(GameObjectAO.SHAPE_CIRCLE, "coin", world, true, bodyHandler, 2, 0.4f);
//		coin.addAnimation("coin_v2", 6, 1, 0.20f);
//		coin.setAnimationActive(true);
//		// coin.getAnimation(0).setPlayMode(PlayMode.LOOP_PINGPONG);
//		spawnObjects.add(coin);
//		
//		GameObjectAO power = new GameObjectAO(GameObjectAO.SHAPE_CIRCLE, "power", world, true, bodyHandler, 1, 0.1f);
//		power.addAnimation("power_v2", 1, 1, 0);
//		power.setAnimationActive(true);
//		spawnObjects.add(power);
//
//		GameObjectAO trampoline = new GameObjectAO(GameObjectAO.SHAPE_RECTANGLE, "trampoline", world, true, bodyHandler, 1, 0.2f);
//		spawnObjects.add(trampoline);
	}

	/**
	 * Overwrite this class
	 */
	public ArrayList<Box2dAO> createSpawn(int x, int y, GameObjectAO goAO) {
		// This code gets overwritten.

//		if(goAO.getTextureName().equals("power")){
//			return createAirPowerup(x, y, goAO);
//		}
//		if(goAO.getTextureName().equals("coin")){
//			return createAirPowerup(x, y, goAO);
//		}
//		if(goAO.getTextureName().equals("trampoline")){
//			return createGroundPowerup(x,y, goAO);
//		}
//	
//		return null;

		return null;
	}

	public ArrayList<Box2dAO> createAirPowerup(int x, int y, GameObjectAO gameObject) {
		ArrayList<Box2dAO> spawnBox = new ArrayList<>();

		if (y < 0) { // dont add spawns if spawnBox if y < 0
			return spawnBox;
		}

		for (int i = 0; i < gameObject.getSpawnQuantity(); i++) {

			if (rand.nextFloat() <= gameObject.getSpawnProbability()) { // objects
				// is
				// created
				float xLeft = x * spawnBoxSize; // + object width
				float xRight = xLeft + spawnBoxSize;
				float yBottom = (y * spawnBoxSize) + minimum_air_height; // + object height
				float yTop = yBottom + spawnBoxSize;

				float finalX = rand.nextFloat() * (xRight - xLeft) + xLeft;
				float finalY = rand.nextFloat() * (yTop - yBottom) + yBottom;

				Box2dAO tempGameObject = new Box2dAO(gameObject.getShape(), gameObject.getTextureName(), world,
						gameObject.isStatic(), finalX, finalY, stage, bodyHandler,assetLoader2);
				tempGameObject.setName(gameObject.getName());
				tempGameObject.getBody().getFixtureList().get(0).setSensor(true);
				if (gameObject.isAnimationActive()) {
					AnimationObject tempAnimation = gameObject.getAnimation(gameObject.getActiveAnimationNumber());
					tempGameObject.addAnimation(tempAnimation.getAnimationName(), tempAnimation.getFrameCol(),
							tempAnimation.getFrameRows(), tempAnimation.getAnimationSpeed());
					tempGameObject.getAnimation(tempGameObject.getActiveAnimationNumber())
							.setPlayMode(gameObject.getAnimation(gameObject.getActiveAnimationNumber()).getPlayMode());
					tempGameObject.setAnimationActive(true);

				}

				spawnBox.add(tempGameObject);
			}
		}

		return spawnBox;
	}

	public ArrayList<Box2dAO> createGroundPowerup(int x, int y, GameObjectAO gameObject) {
		ArrayList<Box2dAO> spawnBox = new ArrayList<>();

		if (y != 0) { // dont add spawns if spawnBox if y != 0
			return spawnBox;
		}

		for (int i = 0; i < gameObject.getSpawnQuantity(); i++) {

			if (rand.nextFloat() <= gameObject.getSpawnProbability()) { // objects
				// is

				float xLeft = x * spawnBoxSize; // + object width
				float xRight = xLeft + spawnBoxSize;
				//float yBottom = y * spawnBoxSize; // + object height
				//float yTop = yBottom + spawnBoxSize;

				float finalX = rand.nextFloat() * (xRight - xLeft) + xLeft;
				float finalY = 50;

				Box2dAO tempGameObject = new Box2dAO(gameObject.getShape(), gameObject.getTextureName(), world,
						gameObject.isStatic(), finalX, finalY, stage, bodyHandler,assetLoader2);
				tempGameObject.setName(gameObject.getName());
				tempGameObject.getBody().getFixtureList().get(0).setSensor(true);
				if (gameObject.isAnimationActive()) {
					AnimationObject tempAnimation = gameObject.getAnimation(gameObject.getActiveAnimationNumber());
					tempGameObject.addAnimation(tempAnimation.getAnimationName(), tempAnimation.getFrameCol(),
							tempAnimation.getFrameRows(), tempAnimation.getAnimationSpeed());
					tempGameObject.getAnimation(tempGameObject.getActiveAnimationNumber())
							.setPlayMode(gameObject.getAnimation(gameObject.getActiveAnimationNumber()).getPlayMode());
					tempGameObject.setAnimationActive(true);

				}
				tempGameObject.toFront();

				spawnBox.add(tempGameObject);
			}
		}

		return spawnBox;
	}

	/**
	 * Adds the GameObjectAO to spawnObjects array. This will add it to random
	 * spawning in all spawn boxes. If GameObjectAO doesnt have spawnQuantity it
	 * will default to int 1 If GameObjectAO doesnt have spawnProbability it
	 * will default to float 1.0f
	 *
	 * @param goAO
	 */
	public void addObjectToSpawns(GameObjectAO goAO) {
		spawnObjects.add(goAO);
	}

	/**
	 * Adds the GameObjectAO to spawnObjects array. This will add it to random
	 * spawning in all spawn boxes.
	 *
	 * @param goAO
	 * @param spawnQuantity
	 * @param spawnProbability
	 */
	public void addObjectsToSpawns(GameObjectAO goAO, int spawnQuantity, int spawnProbability) {
		goAO.setSpawnQuantity(spawnQuantity);
		goAO.setSpawnProbability(spawnProbability);
		spawnObjects.add(goAO);
	}

	public int getMinimum_air_height() {
		return minimum_air_height;
	}

	public void setMinimum_air_height(int minimum_air_height) {
		this.minimum_air_height = minimum_air_height;
	}


}
