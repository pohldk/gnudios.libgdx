package com.gnudios.libgdx.model.json;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.gnudios.libgdx.handler.BodyHandler;
import com.gnudios.libgdx.SSConfiguration;
import com.gnudios.libgdx.model.Box2dAO;
import com.gnudios.libgdx.model.assets.AssetLoader2;
import com.gnudios.libgdx.view.AbstractStage;
import com.gnudios.libgdx.model.BodyShape;


public class JsonMapHandler {
    private JsonMap b2djsonArray;
    private AbstractStage gameStage;
    private World world;
    private BodyHandler bodyHandler;
    private AssetLoader2 assetLoader2;

    public JsonMapHandler(AbstractStage gameStage, World world, BodyHandler bodyHandler, AssetLoader2 assetLoader2) {
        b2djsonArray = new JsonMap();
        this.gameStage = gameStage;
        this.world = world;
        this.bodyHandler = bodyHandler;
        this.assetLoader2 = assetLoader2;
    }

    public void saveBox2dAO() {

        TextInputListener listener = new TextInputListener() {

            @Override
            public void input(String text) {
                b2djsonArray.getBox2dJsonArray().clear();
                for (Box2dAO aObject : gameStage.getBox2dAOs()) {
                    b2djsonArray.getBox2dJsonArray().add(new JsonBox2dAO(aObject));
                }
                Json json = new Json();
                String map = json.toJson(b2djsonArray, JsonMap.class);

                if (Gdx.files.local(text + ".json").exists()) {
                    Gdx.files.local(text + ".json").delete();
                }

                // Gdx.files.local("..\\android\\assets\\" + text + ".json");
                // LOCAL_JSON_MAPS_PATH
                FileHandle file = Gdx.files.local(SSConfiguration.LOCAL_JSON_MAPS_PATH + text + ".json");
                file.writeString(map, false);

            }

            @Override
            public void canceled() {
                // TODO Auto-generated method stub
            }
        };

        Gdx.input.getTextInput(listener, "Save file", "", "Type a name for your file");

    }

    public void loadFileTextInput() {
        TextInputListener listener = new TextInputListener() {
            @Override
            public void input(String text) {
                loadJson(text);
            }

            @Override
            public void canceled() {
                // TODO Auto-generated method stub

            }

        };
        Gdx.input.getTextInput(listener, "Load file", "", "Type the filename you want to load");
    }

    public void loadJson(String fileName) {
        FileHandle file = Gdx.files.local(SSConfiguration.LOCAL_JSON_MAPS_PATH + fileName + ".json");
        String map = file.readString();
        Json json = new Json();
        JsonMap array = json.fromJson(JsonMap.class, map);

        for (JsonBox2dAO b2aojson : array.getBox2dJsonArray()) {
            BodyShape tempShape = null;
            for (BodyShape shape : BodyShape.values()) {
                if (b2aojson.getShape().equalsIgnoreCase(shape.toString())) {
                    tempShape = shape;
                }
            }

            Box2dAO box2dAO = new Box2dAO(tempShape, b2aojson.getTextureName(), world, b2aojson.isStatic(), b2aojson.getX() * SSConfiguration.PPM, b2aojson.getY() * SSConfiguration.PPM, gameStage, bodyHandler,assetLoader2);
            box2dAO.updateWidth(b2aojson.getWidth());
            box2dAO.updateHeight(b2aojson.getHeight());
            box2dAO.getBody().setTransform(box2dAO.getBody().getPosition(),
                    (float) Math.toRadians(b2aojson.getAngle()));
        }
    }
}