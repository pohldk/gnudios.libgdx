package com.gnudios.libgdx.model.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gnudios.libgdx.model.AnimationObject;

public class AnimationHandler {

    private AnimationObject walkAnimation;
    private Texture walkSheet;
    private TextureRegion[] walkFrames;
    private TextureRegion currentFrame;
    private AssetLoader2 assetLoader;

    public AnimationHandler(AssetLoader2 assetLoader2) {
        this.assetLoader = assetLoader2;
    }

    /**
     * @param fileName  filename of texture sprite-sheet.
     * @param frameCol  number of Cols in sprite-sheet.
     * @param frameRows number of Rows in sprite-sheet.
     * @param aniSpeed  The time between each frame in the animation
     * @return AnimationObject animationObject
     */
    public AnimationObject loadAnimation(String fileName, int frameCol, int frameRows, float aniSpeed) {

        walkSheet = assetLoader.getAnimationTexture(fileName);
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / frameCol,
                walkSheet.getHeight() / frameRows);
        walkFrames = new TextureRegion[frameCol * frameRows];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCol; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        walkAnimation = new AnimationObject(aniSpeed, walkFrames);
        walkAnimation.setAnimationName(fileName);
        walkAnimation.setFrameCol(frameCol);
        walkAnimation.setFrameRows(frameRows);
        walkAnimation.setAnimationSpeed(aniSpeed);

        return walkAnimation;
    }


    public TextureRegion renderAnimation(AnimationObject animation, float stateTime) {

        currentFrame = (TextureRegion) animation.getKeyFrame(stateTime, true);
        return currentFrame;

    }

}
