package com.gnudios.libgdx.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gnudios.libgdx.SSConfiguration;

public class Camera extends OrthographicCamera {
    float adjustSpeed = 25.0f; // Default
    float minimumZoomValue = 1.0f;

    public Camera() {
        this.viewportHeight = SSConfiguration.GAME_HEIGHT / SSConfiguration.PPM;
        this.viewportWidth = SSConfiguration.GAME_WIDTH / SSConfiguration.PPM;
        this.near = 0;
        this.position.set((SSConfiguration.GAME_WIDTH / SSConfiguration.PPM) / 2, (SSConfiguration.GAME_HEIGHT / SSConfiguration.PPM) / 2, 0);
        update();

    }

    public void updateCam(SpriteBatch batch) {

        batch.setProjectionMatrix(this.combined);

    }

    public void adjustCamToPos(float x, float y) {
        adjustCamToPosX(x);
        adjustCamToPosY(y);
    }

    public void adjustCamToPosX(float x) {
        this.position.x = this.position.x
                + (((this.position.x - x) / -getAdjustSpeed() * Gdx.graphics.getDeltaTime() * 60.0f));
    }

    public void adjustCamToPosY(float y) {
        this.position.y = this.position.y
                + (((this.position.y - y) / -getAdjustSpeed() * Gdx.graphics.getDeltaTime() * 60.0f));
    }

    public void adjustCamToZoom(float zoom) {
        this.zoom = this.zoom + (((this.zoom - zoom) / -getAdjustSpeed() * Gdx.graphics.getDeltaTime() * 60.0f));
    }

    public float getAdjustSpeed() {
        return adjustSpeed;
    }

    public void setAdjustSpeed(float adjustSpeed) {
        this.adjustSpeed = adjustSpeed;
    }

    public void resetCam() {
        this.position.set((SSConfiguration.GAME_WIDTH / SSConfiguration.PPM) / 2, (SSConfiguration.GAME_HEIGHT / SSConfiguration.PPM) / 2, 0);
        this.zoom = 1;

    }

    public float getMinimumZoomValue() {
        return minimumZoomValue;
    }

    public void setMinimumZoomValue(float minimumZoomValue) {
        this.minimumZoomValue = minimumZoomValue;

//		if(zoom < minimumZoomValue){
//			zoom = minimumZoomValue;
//		}
    }

}
