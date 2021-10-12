package com.gnudios.libgdx.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gnudios.libgdx.model.Box2dAO;
import com.gnudios.libgdx.model.AbstractObject;
import com.gnudios.libgdx.model.ActorAO;

import java.util.ArrayList;

import static com.gnudios.libgdx.general.helpers.Calculator.*;

public class AbstractStage extends Stage {
    private int PPM;

    public AbstractStage(int PPM) {
        this.PPM = PPM;
    }

    public AbstractStage() {
        this.PPM = 1; // This is a PPM for not scaled scenes (Usally for scenses
        // without physics);
    }

    // Abstract objects returned
    public ArrayList<AbstractObject> getAbstractObjects() {
        ArrayList<AbstractObject> arrayList = new ArrayList<>();
        for (Actor actor : this.getActors()) {
            arrayList.add((AbstractObject) actor);
        }
        return arrayList;
    }

    // public AbstractObject getClickedAbstractObject(OrthographicCamera cam){
    // for(AbstractObject aObject : getAbstractObjects()){
    // if(aObject.hit(Converter.posXToPixX(Converter.getMouseX(cam)),
    // Converter.posYToPixY(Converter.getMouseY(cam)), true) != null){
    // return aObject;
    // }
    // }
    // return null;
    // }

    // Box2dAbstract objects returned
    public ArrayList<Box2dAO> getBox2dAOs() {
        ArrayList<Box2dAO> arrayList = new ArrayList<>();
        for (Actor actor : this.getActors()) {
            arrayList.add((Box2dAO) actor);
        }
        return arrayList;
    }



//    public ArrayList<Actor> getActorAOs() {
//        ArrayList<Actor> arrayList = new ArrayList<>();
//        for (Actor actor : this.getActors()) {
//            arrayList.add(actor);
//        }
//        return arrayList;
//    }

    synchronized public ActorAO getActorAOFromLife(int id) {
        for (Actor actor : this.getActors()) {
            ActorAO actorAO = (ActorAO) actor;
            if(actorAO.getLife() == id){
                return actorAO;
            }

        }
        return null;
    }

    synchronized public ArrayList<ActorAO> getActorAOs() {
        ArrayList<ActorAO> arrayList = new ArrayList<>();
        for (Actor actor : this.getActors()) {
            arrayList.add((ActorAO) actor);
        }
        return arrayList;
    }

    synchronized public Box2dAO getClickedBox2dAO(OrthographicCamera cam) {
        if (Gdx.input.isTouched()) {
            for (Box2dAO aObject : getBox2dAOs()) {
                if (aObject.hit(posXToPixX(getMouseX(cam)), posYToPixY(getMouseY(cam)), true) != null) {
                    return aObject;
                }
            }
        }
        return null;
    }

    synchronized public Box2dAO getBox2dAOByName(String name) {

        for (Box2dAO aObject : getBox2dAOs()) {
            if (aObject.getName().equalsIgnoreCase(name)) {
                return aObject;
            }
        }

        return null;
    }

    public Box2dAO getUnClickedBox2dAO(OrthographicCamera cam) {
        if (!Gdx.input.isTouched()) {
            for (Box2dAO aObject : getBox2dAOs()) {
                if (aObject.hit(posXToPixX(getMouseX(cam)), posYToPixY(getMouseY(cam)), true) != null) {
                    return aObject;
                }
            }
        }
        return null;
    }

    synchronized public Actor getClickedActorAO(OrthographicCamera cam) {
        if (Gdx.input.isTouched()) {
            for (Actor aObject : getActorAOs()) {
                if (aObject.hit(posXToPixX(getMouseX(cam)), posYToPixY(getMouseY(cam)), true) != null) {
                    return aObject;
                }
            }
        }
        return null;
    }

    public void deleteEverything() {
        for (Box2dAO aObject : getBox2dAOs()) {
//            for (Fixture fixture : aObject.getBody().getFixtureList()) {
//                aObject.getBody().destroyFixture(fixture);
//            }
            // aObject.getWorld().destroyBody(aObject.getBody());
            aObject.destroyBody();
            aObject.remove();
        }
    }

    public void deleteAllActors() {
        for (Actor aObject : getActorAOs()) {
            aObject.remove();
        }
    }

    public void deleteBox2dAOWithName(String name) {
        for (Box2dAO aObject : getBox2dAOs()) {
            if (aObject.getName().equalsIgnoreCase(name)) {
                aObject.destroyBody();
                // world.destroyBody(aObject.getBody());
                aObject.remove();
            }
        }
    }

    public void deleteThisBox2dAO(Box2dAO BAO) {
        for (Box2dAO aObject : getBox2dAOs()) {
            if (aObject.equals(BAO)) {
                aObject.destroyBody();
                // world.destroyBody(aObject.getBody());
                aObject.remove();
            }
        }
    }

    public void hideStage(boolean hide) {
        for (Box2dAO aObject : getBox2dAOs()) {

            if (hide) {
                aObject.setVisible(false);
            } else {
                aObject.setVisible(true);
            }
        }
    }

    public int getPPM() {
        return PPM;
    }

    public void setPPM(int PPM) {
        this.PPM = PPM;
    }

}
