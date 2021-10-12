package com.gnudios.libgdx.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.gnudios.libgdx.handler.BodyHandler;
import com.gnudios.libgdx.SSConfiguration;
import com.gnudios.libgdx.view.AbstractStage;
import com.gnudios.libgdx.view.Camera;
import com.gnudios.libgdx.model.assets.AssetLoader2;

/**
 * Our main class, everything evolves from this class. (Called by the platform that needs to start the game: Android, Desktop, iOS).
 */
public class GnudiosScreen implements Screen {

    public Vector3 clearColorBit = new Vector3(0.25f, 0.25f, 0.25f);
    public AssetLoader2 assetLoader;
    protected Camera gameCam, menuCam; // bgCam;
    protected AbstractStage gameStage, menuStage, bgStage;
   // protected TMXMapHandler tmxMapHandler;
    protected BodyHandler bodyHandler;

    protected InputMultiplexer inputMultiplexer;

    /**
     * This is the prehandling method, everything inhere is the 'initial code' for starting up the game.
     * Mainly used for loading up assets (Both the assets used for load screen and the assets used ingame).
     * Also initializes all the initial classes.
     */
    @Override
    public void show() {

        //THIS HAS TO RUN WHEN IN ANDROID OR IOS AND NOT ON DESKTOP
        new SSConfiguration(false);
        initImports();
        initShow();
    }

    public void initShow() {

    }

    public void setGlClearColor(float r, float g, float b) {
        clearColorBit.x = r;
        clearColorBit.y = g;
        clearColorBit.z = b;
    }

    public void adjustGlClearColor(float r,float g, float b, float adjustingSpeed) {
        clearColorBit.x = clearColorBit.x + (((clearColorBit.x - r) / -adjustingSpeed * Gdx.graphics.getDeltaTime() * 60.0f));
        clearColorBit.y = clearColorBit.y + (((clearColorBit.y - g) / -adjustingSpeed * Gdx.graphics.getDeltaTime() * 60.0f));
        clearColorBit.z = clearColorBit.z + (((clearColorBit.z - b) / -adjustingSpeed * Gdx.graphics.getDeltaTime() * 60.0f));
    }

    /**
     * Essentially, everything inhere is run as a while loop. Rendering the game is continously,
     * telling the system to draw the models and assets (or play for sounds) as they change depending on inputs.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(clearColorBit.x, clearColorBit.y, clearColorBit.z, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        initRenderBackground(delta);
        initRenderGame(delta);
        initRenderMenu(delta);

        renderGame(delta);
        renderBackground(delta);
        renderMenu(delta);
    }

    public void renderMenu(float delta) {

    }

    public void renderGame(float delta) {

    }

    public void renderBackground(float delta) {

    }

    /**
     * Resize does as it sounds, however on top, it ensures that everything is scaled proportionally, even if screen sizes differ.
     */
    @Override
    public void resize(int width, int height) {
        initResize(width, height);
        resizeCam(width, height);
    }

    public void resizeCam(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    /**
     * Internally called by show() to initialize all of the classes we'll be using in the game.
     * This is also for setting up stages, so they can be resized for the right proportions used in the game.
     */
    protected void initImports() {
        //assetLoader = new AssetLoader2();
        bodyHandler = new BodyHandler();
        //assetLoader = AssetLoader2.getInstance();


        //tmxMapHandler = new TMXMapHandler();

        bgStage = new AbstractStage(SSConfiguration.PPM);
        // bgCam = new Camera();

        gameStage = new AbstractStage(SSConfiguration.PPM);
        gameCam = new Camera();

        menuStage = new AbstractStage();
        menuCam = new Camera();

        bgStage.getViewport().setCamera(gameCam);
        gameStage.getViewport().setCamera(gameCam);
        menuStage.getViewport().setCamera(menuCam);

        inputMultiplexer = new InputMultiplexer();

        Gdx.input.setInputProcessor(inputMultiplexer);
        inputMultiplexer.addProcessor(menuStage);
        inputMultiplexer.addProcessor(gameStage);
        inputMultiplexer.addProcessor(bgStage);


    }

    /**
     * Internally called method that resizes all the stages added. All stages that need to work under the same proportions should be added here.
     *
     * @param width  int width of the game in pixels.
     * @param height int height of the game in pixels.
     */
    protected void initResize(int width, int height) {
        bgStage.getViewport().setScreenSize(width, height);
        gameStage.getViewport().setScreenSize(width, height);
        menuStage.getViewport().setScreenSize(width, height);
    }

    /**
     * Rendering the actual game objects is being handled inhere, in the game stage.
     * Calling act inhere activates all of the actors added to the stage, so they all work synchronized and update at the same time.
     * Remember to add "stage".getbatch().EnableBlending(); here in order to allow transparent pixels. (Far more crisp look of the entire game unless it's x-bit-gamestyle.
     *
     * @param delta float time, making sure the entirety of the game loads at the same pace (synchronized).
     */
    private void initRenderGame(float delta) {
        try{
            gameStage.act(delta);
        } catch (Exception e){
            System.out.println("RuntimeException: ");
            e.printStackTrace();
        }

        gameStage.getBatch().enableBlending();
        gameStage.draw();
    }

    /**
     * Rendering the menus (flowing menu buttons etc) is being handled inhere, in menu stage.
     * Calling act inhere activates all of the actors added to the stage, so they all work synchronized and update at the same time.
     * Remember to add "stage".getbatch().EnableBlending(); here in order to allow transparent pixels. (Far more crisp look of the entire game unless it's x-bit-gamestyle.
     *
     * @param delta float time, making sure the entirety of the game loads at the same pace (synchronized).
     */
    private void initRenderMenu(float delta) {
        menuStage.act(delta);
        menuStage.getBatch().enableBlending();
        menuStage.draw();
    }

    /**
     * Rendering the backgrounds is being handled inhere, in the background stage.
     * Calling act inhere activates all of the actors added to the stage, so they all work synchronized and update at the same time.
     * Remember to add "stage".getbatch().EnableBlending(); here in order to allow transparent pixels. (Far more crisp look of the entire game unless it's x-bit-gamestyle.
     *
     * @param delta float time, making sure the entirety of the game loads at the same pace (synchronized).
     */
    private void initRenderBackground(float delta) {
        bgStage.act(delta);
        bgStage.getBatch().enableBlending();
        bgStage.draw();
    }
}
