package com.gnudios.libgdx.view;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.gnudios.libgdx.handler.BodyHandler;
import com.gnudios.libgdx.model.Box2dAO;
import com.gnudios.libgdx.model.AbstractObject;
import com.gnudios.libgdx.model.ActorAO;
import com.gnudios.libgdx.model.assets.AssetLoader2;

import java.util.ArrayList;

import static com.gnudios.libgdx.model.BodyShape.SHAPE_RECTANGLE;
import static com.gnudios.libgdx.SSConfiguration.GAME_HEIGHT;
import static com.gnudios.libgdx.SSConfiguration.PPM;

public class TreadmillGeneratorVertical {

	private Camera camera;
	private World world;
	private AbstractStage stage;
	private BodyHandler bodyHandler;
	private ArrayList<AbstractObject> treadmillSegments = new ArrayList<>();
	private AbstractObject aObject;
	private float cameraRatio = 0f;
	private String textureName;
	private float imgHeight;
	private float scale;
	private boolean isBox2dAO;
	private int extraDistanceBottom = 0;
	private int extraDistanceTop = 0;
	private AssetLoader2 assetLoader2;

	public TreadmillGeneratorVertical(World world, Camera camera, AbstractStage stage, BodyHandler bodyHandler,
									  String textureName, boolean isStatic, float xPos, float scale,AssetLoader2 assetLoader2) {
		this.assetLoader2 = assetLoader2;
		this.camera = camera;
		this.world = world;
		this.stage = stage;
		this.bodyHandler = bodyHandler;
		this.textureName = textureName;
		this.aObject = new Box2dAO(SHAPE_RECTANGLE, textureName, world, isStatic, bodyHandler,assetLoader2);
		this.aObject.setX(xPos);
		this.scale = scale;
		this.imgHeight = scale * aObject.getTextureHeight();
		if (scale != 1) {
			aObject.setX((xPos + ((aObject.getTextureWidth() / 2 * scale + 1))) - (aObject.getTextureWidth() / 2)); // -(box2dAO.getY()/2)
		}
		this.isBox2dAO = true;
	}

	public TreadmillGeneratorVertical(Camera camera, AbstractStage stage, String textureName, float xPos, float scale,AssetLoader2 assetLoader2) {
		this.camera = camera;
		this.stage = stage;
		this.textureName = textureName;
		this.aObject = new ActorAO(textureName, stage,assetLoader2);
		this.aObject.setX(xPos);
		this.scale = scale;
		this.imgHeight = scale * aObject.getTextureHeight();
		if (scale != 1) {
			aObject.setX((xPos + ((aObject.getTextureWidth() / 2 * scale + 1))) - (aObject.getTextureWidth() / 2)); // -(box2dAO.getY()/2)
		}
		this.isBox2dAO = false;
	}

	public void addSegment(int yPos) {
		AbstractObject segment;
		if (this.isBox2dAO) {
			segment = new Box2dAO(SHAPE_RECTANGLE, textureName, world, true, aObject.getX(), yPos, stage,
					bodyHandler, assetLoader2);
		} else {
			segment = new ActorAO(textureName, stage, aObject.getX(), yPos,assetLoader2);
		}

		segment.setTouchable(Touchable.disabled);
		segment.setScale(scale, scale);
		if (scale != 1) {
			segment.toBack();
		}
		treadmillSegments.add(segment);
	}

	public void updateBackground() {
		int height = (int) imgHeight;
		float cameraYposBegin = 0;// (camera.position.y * Finals.PPM -
		// Finals.GAME_HEIGHT * camera.zoom / 2f) -
		// (imgHeight*Finals.PPM) -
		// (extraDistanceBottom * Finals.PPM);
		//System.out.println(cameraYposBegin);
		float cameraYposEnd = (camera.position.y * PPM + GAME_HEIGHT * camera.zoom / 2f) + (extraDistanceTop * PPM);
		int yDisplacement = Math.round((Math.round(cameraYposBegin)) * cameraRatio);
		ArrayList<Integer> segmentList = new ArrayList<>();

		int yCam = Math.round(cameraYposBegin) - Math.round(cameraYposBegin % height);
		segmentList.add(yCam + yDisplacement % height);
		while (yCam <= cameraYposEnd) {
			yCam = yCam + height;
			segmentList.add(yCam + yDisplacement % height);
		}

		// Adding necessary segments
		for (int yPos : segmentList) {
			boolean addSegment = true;
			for (AbstractObject treadmillSegment : treadmillSegments) {
				if (Math.round(treadmillSegment.getY() * PPM) == yPos) {
					addSegment = false;
				}
			}
			if (addSegment) {
				addSegment(yPos);
			}
		}
	}

	public int getExtraDistanceTop() {
		return extraDistanceTop;
	}

	public void setExtraDistanceTop(int extraDistanceTop) {
		this.extraDistanceTop = extraDistanceTop;
	}

	public int getExtraDistanceBottom() {
		return extraDistanceBottom;
	}

	public void setExtraDistanceBottom(int extraDistanceBottom) {
		this.extraDistanceBottom = extraDistanceBottom;
	}

}
