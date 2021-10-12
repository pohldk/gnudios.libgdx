package com.gnudios.libgdx.model;

import com.badlogic.gdx.physics.box2d.World;
import com.gnudios.libgdx.handler.BodyHandler;
import com.gnudios.libgdx.model.assets.AssetLoader2;

public class GameObjectAO extends Box2dAO {

    private int spawnQuantity = 1;
    private float spawnProbability = 1.0f;

    public GameObjectAO(BodyShape bodyShape, String textureName, World world, boolean isStatic, BodyHandler bodyHandler, int spawnQuantity, float spawnProbability, AssetLoader2 assetLoader2) {
        super(bodyShape, textureName, world, isStatic, bodyHandler,assetLoader2);
        this.spawnQuantity = spawnQuantity;
        this.spawnProbability = spawnProbability;
        this.getBody().setActive(false);
        // TODO Auto-generated constructor stub
    }

    public int getSpawnQuantity() {
        return spawnQuantity;
    }

    public void setSpawnQuantity(int spawnQuantity) {
        this.spawnQuantity = spawnQuantity;
    }

    public float getSpawnProbability() {
        return spawnProbability;
    }

    public void setSpawnProbability(float spawnProbability) {
        this.spawnProbability = spawnProbability;
    }

}
