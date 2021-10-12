package com.gnudios.libgdx.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

import static com.gnudios.libgdx.general.helpers.Calculator.*;


public class CameraGestureController implements GestureListener {
	private float velX, velY;
	private boolean flinging = false;
	private float initialScale = 1;
	private boolean touchDown = false;
	private OrthographicCamera cam;

	public CameraGestureController(OrthographicCamera cam) {
		this.cam = cam;

	}

	public CameraGestureController() {

	}

	public boolean touchDown(float x, float y, int pointer, int button) {
		touchDown = true;
		flinging = false;
		initialScale = cam.zoom;

		return false;
	}

	public boolean touchUp() {
		if (!Gdx.input.isTouched() && touchDown) {
			touchDown = false;
		}
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// Gdx.app.log("GestureDetectorTest", "tap at " + x + ", " + y +
		// ", count: " + count);
		// System.out.println("x: " + x + " y: " + y);
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// System.out.println("Long press has been made");
		// Gdx.app.log("GestureDetectorTest", "long press at " + x + ", " + y);
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {

		flinging = true;
		velX = posXToPixX(PPMDivide(cam.zoom) * velocityX * 0.5f);
		velY = posYToPixY2(PPMDivide(cam.zoom) * velocityY * 0.5f);

		return false;

	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {

		if (Gdx.input.isTouched()) {
			if (deltaX < 0) {
				cam.translate(posXToPixX(-deltaX * PPMDivide(cam.zoom)), 0, 0);
			}
			if (deltaX > 0) {
				cam.translate(posXToPixX(-deltaX * PPMDivide(cam.zoom)), 0, 0);
			}

			if (deltaY < 0) {
				cam.translate(0, posYToPixY2(deltaY * PPMDivide(cam.zoom)), 0);
			}

			if (deltaY > 0) {
				cam.translate(0, posYToPixY2(deltaY * PPMDivide(cam.zoom)), 0);
			}
		}

		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// Gdx.app.log("GestureDetectorTest", "pan stop at " + x + ", " + y);
		return false;
	}

	@Override
	public boolean zoom(float originalDistance, float currentDistance) {

		float ratio = originalDistance / currentDistance;
		cam.zoom = initialScale * ratio;

		// }
		return false;
	}

	// setting up a zoomlimit
	public void setZoom(float zoom) {

		// if (((float) (Finals.VIRTUAL_SPRITESIZE) / Finals.GAME_WIDTH >
		// (float) (Finals.VIRTUAL_SPRITESIZE) / Finals.GAME_HEIGHT)) {
		// cam.zoom = MathUtils.clamp(zoom, 0.5f, ((float) (
		// Finals.VIRTUAL_SPRITESIZE) / Finals.GAME_WIDTH));
		// } else {
		// cam.zoom = MathUtils.clamp(zoom, 0.5f, ((float) (
		// Finals.VIRTUAL_SPRITESIZE) / Finals.GAME_HEIGHT));
		// }

	}

	@Override
	public boolean pinch(Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer,
						 Vector2 secondPointer) {

		return false;
	}

	public void update() {
		touchUp();
		if (Gdx.input.isKeyPressed(Keys.A)) {
			// ((float) ( Finals.VIRTUAL_SPRITESIZE) / Finals.GAME_WIDTH >
			// cam.zoom &&
			// (float) (Finals.VIRTUAL_SPRITESIZE) / Finals.GAME_HEIGHT >
			// cam.zoom ||
			// && cam.zoom < 2) {
			cam.zoom = cam.zoom + 0.02f;
		}
		if (Gdx.input.isKeyPressed(Keys.Z)) {
			// && cam.zoom > 0.5) {
			cam.zoom = cam.zoom - 0.02f;
		}

		if (flinging) {

			velX *= 0.95f;
			velY *= 0.95f;
			cam.position.add(-velX * Gdx.graphics.getDeltaTime(), velY * Gdx.graphics.getDeltaTime(), 0);
			if (Math.abs(velX) < 0.01f)
				velX = 0;
			if (Math.abs(velY) < 0.01f)
				velY = 0;
		}

	}

	@Override
	public void pinchStop() {
		// TODO Auto-generated method stub

	}
}