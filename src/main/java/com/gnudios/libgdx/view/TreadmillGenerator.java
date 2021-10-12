package com.gnudios.libgdx.view;

import com.badlogic.gdx.physics.box2d.World;
import com.gnudios.libgdx.handler.BodyHandler;
import com.gnudios.libgdx.SSConfiguration;
import com.gnudios.libgdx.model.AbstractObject;
import com.gnudios.libgdx.model.Box2dAO;
import com.gnudios.libgdx.model.ActorAO;
import com.gnudios.libgdx.model.assets.AssetLoader2;

import java.util.ArrayList;

import static com.gnudios.libgdx.model.BodyShape.SHAPE_RECTANGLE;

public class TreadmillGenerator {

	private Camera camera;
	private World world;
	private AbstractStage stage;
	private BodyHandler bodyHandler;
	private ArrayList<AbstractObject> treadmillSegments = new ArrayList<AbstractObject>();
	private AbstractObject aObject;
	private String textureName;
	private float imgWidth;
	private float yPos;
	private float scale;
	private boolean isBox2dAO;
	private int extraDistanceLeft = 0;
	private int extraDistanceRight = 0;
	private boolean isParralax = false;
	private AssetLoader2 assetLoader2;

	public TreadmillGenerator(World world, Camera camera, AbstractStage stage, BodyHandler bodyHandler,
							  String textureName, boolean isStatic, float yPos, float scale,AssetLoader2 assetLoader2) {
		this.assetLoader2 = assetLoader2;
		this.camera = camera;
		this.world = world;
		this.stage = stage;
		this.bodyHandler = bodyHandler;
		this.textureName = textureName;
		this.aObject = new Box2dAO(SHAPE_RECTANGLE, textureName, world, isStatic, bodyHandler,assetLoader2);
		this.aObject.setY(yPos);
		this.scale = scale;
		this.imgWidth = scale * aObject.getTextureWidth();
//		if (scale != 1) {
//			aObject.setY((yPos + ((aObject.getTextureHeight() / 2) * scale )) - (aObject.getTextureHeight() / 2)); // -(box2dAO.getY()/2)
//		}
		this.isBox2dAO = true;
	}

	public TreadmillGenerator(Camera camera, AbstractStage stage, String textureName, float yPos, float scale, boolean isParralax,AssetLoader2 assetLoader2) {
		this.assetLoader2 = assetLoader2;
		this.camera = camera;
		this.isParralax = isParralax;
		this.stage = stage;
		this.textureName = textureName;
		this.aObject = new ActorAO(textureName,assetLoader2);
		this.aObject.setY(yPos);
		this.scale = scale;
		this.imgWidth = scale * aObject.getTextureWidth();
		if (scale != 1) {
			aObject.setY((yPos + ((aObject.getTextureHeight() / 2) * scale)) - (aObject.getTextureHeight() / 2)); // -(box2dAO.getY()/2)
		}
		this.yPos = aObject.getY();
		this.isBox2dAO = false;
	}

	public void addSegment(float xPos) {
		AbstractObject segment;
		if (this.isBox2dAO) {
			Box2dAO tempAO = new Box2dAO(SHAPE_RECTANGLE, textureName, world, true, xPos, aObject.getY(), stage, bodyHandler,assetLoader2);
			//tempAO.setName(textureName);
			tempAO.updateWidth(tempAO.getWidth() * scale);
			tempAO.updateHeight(tempAO.getHeight() * scale);
			tempAO.toBack();
			segment = tempAO;
		} else {
			segment = new ActorAO(textureName, stage, xPos, aObject.getY(),assetLoader2);
			segment.toBack();
			segment.setScale(scale, scale);
		}

		//segment.setTouchable(Touchable.disabled);

		treadmillSegments.add(segment);
	}

	public void updateBackground() {

		float cameraXposBegin = (camera.position.x -100 * SSConfiguration.PPM - SSConfiguration.GAME_WIDTH * camera.zoom / 2f) - (imgWidth);
		float cameraXposEnd = (camera.position.x * SSConfiguration.PPM + SSConfiguration.GAME_WIDTH * camera.zoom / 2f);

		ArrayList<Float> segmentList = new ArrayList<>();

		float xCam = Math.round(cameraXposBegin) - Math.round(cameraXposBegin % imgWidth);
		segmentList.add(xCam + imgWidth);
		while (xCam <= cameraXposEnd) {
			xCam = xCam + imgWidth;
			segmentList.add(xCam + imgWidth);
		}

		if (isParralax) {
			for (AbstractObject treadmillSegment : treadmillSegments) {

//				if(treadmillSegment.getTextureName().equalsIgnoreCase("grassBackground")){
//					treadmillSegment.setY(yPos/PPM + (0.5f*(camera.position.y * PPM - (GAME_HEIGHT) * camera.zoom / 2f) / PPM));
				//}else
				if (treadmillSegment.getTextureName().equalsIgnoreCase("citybg")) {

					treadmillSegment.setY(yPos / SSConfiguration.PPM + (0.9f * (camera.position.y * SSConfiguration.PPM - (SSConfiguration.GAME_HEIGHT) * camera.zoom / 2f) / SSConfiguration.PPM));
				}
					//System.out.println(cameraXposBegin + " xEnd: " + cameraXposEnd + " added: " + xCam + " imgWidth: " + imgWidth);
					//treadmillSegment.setX(treadmillSegment.getX()/PPM + (camera.position.x * PPM - (GAME_WIDTH) * camera.zoom / 2f) / PPM);
				if (treadmillSegment.getTextureName().equalsIgnoreCase("mountainsBackground")) {
					treadmillSegment.setY(yPos / SSConfiguration.PPM + (0.9f * (camera.position.y * SSConfiguration.PPM - (SSConfiguration.GAME_HEIGHT) * camera.zoom / 2f) / SSConfiguration.PPM));
					//System.out.println(cameraXposBegin + " xEnd: " + cameraXposEnd + " added: " + xCam + " imgWidth: " + imgWidth);
					//treadmillSegment.setX(treadmillSegment.getX()/PPM + (camera.position.x * PPM - (GAME_WIDTH) * camera.zoom / 2f) / PPM);
				} else if (treadmillSegment.getTextureName().equalsIgnoreCase("cloudsBackground")) {
					treadmillSegment.setY(yPos / SSConfiguration.PPM + (0.95f * (camera.position.y * SSConfiguration.PPM - (SSConfiguration.GAME_HEIGHT) * camera.zoom / 2f) / SSConfiguration.PPM));
				} else if (treadmillSegment.getTextureName().equalsIgnoreCase("blueSky2")) {
					treadmillSegment.setY(yPos / SSConfiguration.PPM + (0.97f * (camera.position.y * SSConfiguration.PPM - (SSConfiguration.GAME_HEIGHT) * camera.zoom / 2f) / SSConfiguration.PPM));
				}

			}
		}

//		This is the code that automatically deletes treadmill segements when they are out of screen. Needs to be updated to delete correctly and appropriate distance.

		// for (int j = 0; j < treadmillSegments.size(); j++) {
		// AbstractObject treadmillSegment = treadmillSegments.get(j);
		// boolean removeSegment = true;
		// for (int x : segmentList) {
		// if (Math.round(treadmillSegment.getX() * Finals.PPM) == x) {
		// removeSegment = false;
		// }
		// }
		//
		// if (removeSegment) {
		//// treadmillSegments.remove(treadmillSegment);
		//
		// // Dette sletter "AO count" paa screen men bugger med vores andre slet
		// metoder.
		//// world.destroyBody(treadmillSegment.getBody());
		// }
		// }

		// Adding necessary segments
		for (float xPos : segmentList) {
			boolean addSegment = true;
			for (AbstractObject treadmillSegment : treadmillSegments) {
				if (Math.round(treadmillSegment.getX() * SSConfiguration.PPM) == xPos) {
					addSegment = false;
				}
			}
			if (addSegment) {
				addSegment(xPos);
			}
		}
	}

	public void deleteAllSegments(){
		for (AbstractObject treadmillSegment : treadmillSegments) {
			treadmillSegment.remove();
		}
	}

	public int getExtraDistanceLeft() {
		return extraDistanceLeft;
	}

	public void setExtraDistanceLeft(int extraDistanceLeft) {
		this.extraDistanceLeft = extraDistanceLeft;
	}

	public int getExtraDistanceRight() {
		return extraDistanceRight;
	}

	public void setExtraDistanceRight(int extraDistanceRight) {
		this.extraDistanceRight = extraDistanceRight;
	}

}
