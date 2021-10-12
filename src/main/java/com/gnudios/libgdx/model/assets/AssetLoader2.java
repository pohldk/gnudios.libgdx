package com.gnudios.libgdx.model.assets;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.ArrayList;

public class AssetLoader2 {
    private AssetLoader2 uniqueInstance = null;
    private AssetManager assetManager;
    //private AnimationHandler animationHandler;
    private ArrayList<FontGeneratorID> fontGeneratorIDs = new ArrayList<>();
    private ArrayList<MapID> mapIDs = new ArrayList<>();
    private ArrayList<String> atlasFullPaths = new ArrayList<>();
    private ArrayList<String> pngFullPaths = new ArrayList<>();
    // private ArrayList<String> ttfFullPaths = new ArrayList<String>();
    private ArrayList<String> musicFullPaths = new ArrayList<>();
    private AnimationHandler animationHandler;
    // private ArrayList<String> tmxFullPaths = new ArrayList<String>();

    private ArrayList<TextureAtlas> atlasArray = new ArrayList<>();

    public AssetLoader2() {
        assetManager = new AssetManager();
        animationHandler = new AnimationHandler(this);


        //animationHandler = new AnimationHandler(this);
        if (Gdx.app.getType() == ApplicationType.Desktop) {

            loadConstantAssets();
        } else {
            loadConstantAssets();
        }

    }

//    public static synchronized AssetLoader2 getInstance() {
//        if (uniqueInstance == null) {
//            uniqueInstance = new AssetLoader2();
//        }
//        return uniqueInstance;
//    }

    public float getLoadProgress() {
        return assetManager.getProgress();
    }

    public boolean updateLoadProgress() {
        return assetManager.update();
    }

    public void init() {


        FileHandle dirHandle;
        String initPath = "";

        if (Gdx.app.getType() == ApplicationType.Desktop) {
            // ApplicationType.Desktop

            initPath = "./bin/";
            dirHandle = Gdx.files.internal(initPath);
        } else {
            // Type iOS, still not coded.
            dirHandle = Gdx.files.internal("");
        }

        for (FileHandle tempFolder : dirHandle.list()) {
            String folder;
            //System.out.println("THIS1: " + tempFolder);
            if (Gdx.app.getType() == ApplicationType.Desktop) {
                folder = tempFolder.toString().split(initPath)[1]; // ./bin/images
                // -> images
            } else {
                folder = tempFolder.toString();

            }

            /*
             * Ignores the folders that has no relevance to the asset loader.
             */

            // assetManager.load("buttons/buttonv1.atlas", TextureAtlas.class);

            // if(folder.equals("com") == false &&
            // folder.equals("buttonTextures") == false &&
            // folder.equals("atlas") == false && folder.equals("icons") ==
            // false){

            if (!folder.equalsIgnoreCase("com") && !folder.equalsIgnoreCase("icons")) {
                for (FileHandle tempFile : dirHandle.child(folder).list()) {

                    String[] fileFullName = tempFile.toString().split(initPath + folder + "/")[1].split("\\."); // Sheep_single_baaah_short.mp3
                    String fileType = fileFullName[fileFullName.length - 1]; // mp3

                    if (fileType.equalsIgnoreCase("ogg")) {
                        System.out.println("WARNING!: " + tempFile.name() + " is loaded in a deprecated format. OGG files cannot be used in iOS platforms.");
                    }

                    if (fileType.equalsIgnoreCase("jpg")) {
                        System.out.println("WARNING!: " + tempFile.name() + " is loaded in a deprecated format. JPG files is in a low quality and cant have transparent/crisp edges. Use PNG-24 instead.");
                    }

                    if (fileType.equalsIgnoreCase("atlas")) {
                        assetManager.load(folder + "/" + tempFile.name(), TextureAtlas.class);
                        atlasFullPaths.add(folder + "/" + tempFile.name());
                    } else if (fileType.equalsIgnoreCase("png") || fileType.equalsIgnoreCase("jpg")) {
                        if (!folder.equalsIgnoreCase("atlas")) {
                            assetManager.load(folder + "/" + tempFile.name(), Texture.class);
                            pngFullPaths.add(folder + "/" + tempFile.name());
                        }
                    } else if (fileType.equalsIgnoreCase("ttf")) {
                        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(tempFile);
                        FontGeneratorID fontGeneratorID = new FontGeneratorID(fontGenerator, fileFullName[0]);
                        fontGeneratorIDs.add(fontGeneratorID);
                        // assetManager.load(folder + "/" + tempFile.name(),
                        // FreeType.class);
                        // assetFullPaths.add(folder + "/" + tempFile.name());
                    } else if (fileType.equalsIgnoreCase("mp3") && folder.equals("music") || fileType.equalsIgnoreCase("wav") && folder.equals("music")
                            || fileType.equalsIgnoreCase("mp3") && folder.equals("sounds") || fileType.equalsIgnoreCase("wav") && folder.equals("sounds")) {
                        assetManager.load(folder + "/" + tempFile.name(), Music.class);
                        musicFullPaths.add(folder + "/" + tempFile.name());
                    } else if (fileType.equalsIgnoreCase("tmx")) {
                        TmxMapLoader tmxMapLoader = new TmxMapLoader();
                        TiledMap tiledMap = tmxMapLoader.load(tempFile.toString());
                        MapID mapID = new MapID(tiledMap, fileFullName[0]);
                        mapIDs.add(mapID);

                        // assetManager.load(folder + "/" + tempFile.name(),
                        // DERP.class);
                        // tmxFullPaths.add(folder + "/" + tempFile.name());
                    } else if (fileType.equalsIgnoreCase("gitignore") || fileType.equalsIgnoreCase("json") || tempFile.name().equalsIgnoreCase("default")) {
                        // Swallow files that are supposed to be there, but not be loaded by the assetloader.
                    } else {
                        System.out.println("NOT LOADED: " + tempFile.name());
                    }

                }
                // }
            }
        }
    }

    public AnimationHandler getAnimationHandler() {
        return animationHandler;
    }

    public void setAnimationHandler(AnimationHandler animationHandler) {
        this.animationHandler = animationHandler;
    }

    private void addAtlas(String path) {
        assetManager.load(path, TextureAtlas.class);
        atlasFullPaths.add(path);
    }

    private void addTexture(String path) {
        assetManager.load(path, Texture.class);
        pngFullPaths.add(path);
    }

    private void addMusic(String path) {
        assetManager.load(path, Music.class);
        musicFullPaths.add(path);
    }

    public void loadConstantAssets() {

        //Atlass
        addAtlas("atlas/object_atlas.atlas");
        addAtlas("skins/uiskin.atlas");

        //PNGs
//        addTexture("animations/combined.png");
//        addTexture("animations/jump0.png");
//        addTexture("animations/jump1.png");
//        addTexture("animations/jump2.png");
//        addTexture("animations/jump3.png");
//
//        addTexture("animations/coin_v2_128.png");
//        //Sounds
//        addMusic("sounds/coineffect.mp3");

        //Musics

        //Animation

        FreeTypeFontGenerator fontGenerator1 = new FreeTypeFontGenerator(Gdx.files.internal("fonts/toxigenesis_bd.ttf"));
        FontGeneratorID fontGeneratorID1 = new FontGeneratorID(fontGenerator1, "toxigenesis_bd");
        fontGeneratorIDs.add(fontGeneratorID1);



    }

    private String searchFullPath(String textureName, ArrayList<String> pathArray) {

        String tempTextureName = textureName.split("\\.")[0];
        for (String fullPath : pathArray) {
            String tempFileName = fullPath.split("/|\\.")[1];
            if (tempTextureName.equalsIgnoreCase(tempFileName)) {
                return fullPath;
            }
        }
        return null;
    }

    public TextureRegion getTexture(String textureName) {
        if (atlasArray.isEmpty()) {
            for (String tempPath : atlasFullPaths) {
                TextureAtlas tempAtlas = assetManager.get(tempPath, TextureAtlas.class);
                if (tempAtlas.findRegion(textureName) != null) {
                    TextureRegion tempRegion = tempAtlas.findRegion(textureName);

                    // DENNE LINJE Maa IKKE SLETTES!!! BUG I LIBRARY VI HENTER
                    // FRA HVIS IKKE!!
                    tempRegion.getTexture();
                    // DENNE LINJE Maa IKKE SLETTES!!! BUG I LIBRARY VI HENTER
                    // FRA HVIS IKKE!!

                    return tempRegion;
                }
            }
        }

        System.out.println("There was an error in searching for a texture. Please fix me.");
        return null;
    }

    /**
     * Dont use this! Find a way of using getTexture instead. The only reason
     * this code is accessible is because buttons only accepts TextureAtlas, not
     * textures.
     *
     * @param textureAtlasName
     * @return TextureAtlas for Skin on buttons.
     */
    public TextureAtlas getTextureAtlas_DONT_USE(String textureAtlasName) {
        String fullPath = searchFullPath(textureAtlasName, atlasFullPaths);
        return assetManager.get(fullPath);
    }

    public Texture getTexture(String textureName, Boolean ISTHISVERYDERP) {
        String fullPath = searchFullPath(textureName, pngFullPaths);
        return assetManager.get(fullPath);
    }

    // Duplicate code for less confusion on other classes atm.
    public Texture getAnimationTexture(String textureName) {
        String fullPath = searchFullPath(textureName, pngFullPaths);
        return assetManager.get(fullPath);
    }

    //	public Music getSound(String soundName) {
    //		String fullPath = searchFullPath(soundName, soundsFullPaths);
    //		return assetManager.get(fullPath);
    //	}

    public Music getMusic(String musicName) {
        String fullPath = searchFullPath(musicName, musicFullPaths);
        return assetManager.get(fullPath);
    }

    public void disposeEverything() {
        assetManager.clear();
    }

    public FreeTypeFontGenerator getFontGenerator(String name) {
        name = name.split(".ttf")[0]; // disposes ".ttf" at the end of the
        // string

        for (FontGeneratorID fontGeneratorID : fontGeneratorIDs) {

            if (fontGeneratorID.getName().equals(name)) {
                return fontGeneratorID.getFontGenerator();
            }
        }

        System.out.println(
                "Warning - FontGenerator by name " + name + "\n" + "was not found in fontGenerators in AssetHandler");
        return null;
    }

    public TiledMap getMap(String name) {
        name = name.split(".tmx")[0]; // disposes ".png" at the end of the
        // string

        for (MapID mapID : mapIDs) {

            if (mapID.getName().equals(name)) {
                return mapID.getTiledMap();
            }
        }

        System.out.println("Warning - TiledMap by name " + name + "\n" + "was not found in tiledmaps in AssetHandler");
        return null;
    }

    private class FontGeneratorID {

        FreeTypeFontGenerator fontGenerator;
        String name;

        private FontGeneratorID(FreeTypeFontGenerator fontGenerator, String name) {

            this.fontGenerator = fontGenerator;
            this.name = name;
        }

        private FreeTypeFontGenerator getFontGenerator() {
            return fontGenerator;
        }

        // private void setFontGenerator(FreeTypeFontGenerator fontGenerator) {
        // this.fontGenerator = fontGenerator;
        // }

        private String getName() {
            return name;
        }

        // private void setName(String name) {
        // this.name = name;
        // }
    }

    // ----------------------------- Getters End ------------------------------
    // ----- Local Classes to bind Objects to the desired String Name  --------

    /**
     * This class is a subclass that should not be referenced anywhere outside
     * this class. It is there only for the purpose of referencing an object
     * from a string name.
     */
    private class MapID {

        TiledMap tiledMap;
        String name;

        private MapID(TiledMap tiledMap, String name) {

            this.tiledMap = tiledMap;
            this.name = name;
        }

        private TiledMap getTiledMap() {
            return tiledMap;
        }

        private String getName() {
            return name;
        }


    }
}