package com.gnudios.libgdx.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationObject extends Animation<Object> {

    private String animationName;
    private int frameCol;
    private int frameRows;
    private float aniSpeed;
    private float startTimeAni = 0f;
    private float endTimeAni = 0f;
    private boolean runOnce = false;

    public AnimationObject(float frameDuration, TextureRegion[] keyFrames) {
        super(frameDuration, (Object[]) keyFrames);
    }

    public String getAnimationName() {
        return animationName;
    }

    public void setAnimationName(String animationName) {
        this.animationName = animationName;
    }

    public int getFrameCol() {
        return frameCol;
    }

    public void setFrameCol(int frameCol) {
        this.frameCol = frameCol;
    }

    public int getFrameRows() {
        return frameRows;
    }

    public void setFrameRows(int frameRows) {
        this.frameRows = frameRows;
    }

    public float getAnimationSpeed() {
        return aniSpeed;
    }

    public void setAnimationSpeed(float aniSpeed) {
        this.aniSpeed = aniSpeed;
    }

    public float getAniSpeed() {
        return aniSpeed;
    }

    public void setAniSpeed(float aniSpeed) {
        this.aniSpeed = aniSpeed;
    }

    public float getStartTimeAni() {
        return startTimeAni;
    }

    public void setStartTimeAni(float startTimeAni) {
        this.startTimeAni = startTimeAni;
    }

    public float getEndTimeAni() {
        return endTimeAni;
    }

    public void setEndTimeAni(float endTimeAni) {
        this.endTimeAni = endTimeAni;
    }

    public boolean isRunOnce() {
        return runOnce;
    }

    public void setRunOnce(boolean runOnce) {
        this.runOnce = runOnce;
    }

    @Override
    public boolean isAnimationFinished(float stateTime) {
        if (endTimeAni < getAnimationDuration() + startTimeAni) {
            endTimeAni = getAnimationDuration() + startTimeAni;
        }

        return endTimeAni < stateTime;
    }
}
