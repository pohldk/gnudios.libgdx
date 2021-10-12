package com.gnudios.libgdx.handler;

import com.badlogic.gdx.physics.box2d.*;
import com.gnudios.libgdx.model.Box2dAO;


public class CollisionDetector implements CollisionDetectorIF {

    @Override
    public void collision(Box2dAO AO1, Box2dAO AO2) {
        // This code will be overwritten by sub-class.
    }

    @Override
    public void endCollision(Box2dAO AO1, Box2dAO AO2) {
        // Code inhere gets overwritten by subclass.
    }

    @Override
    public void preContact(Box2dAO AO1, Box2dAO AO2) {
        // Code inhere gets overwritten by subclass.
    }

    @Override
    public void postContact(Box2dAO AO1, Box2dAO AO2) {
        // Code inhere gets overwritten by subclass.
    }

    protected Box2dAO defineThisObject(Box2dAO AO1, Box2dAO AO2, String primaryObjectName) {
        if (AO1.getName().equalsIgnoreCase(primaryObjectName)) {
            return AO1;
        } else if (AO2.getName().equalsIgnoreCase(primaryObjectName)) {
            return AO2;
        }

        return null;
    }

    protected Box2dAO defineNotThisObject(Box2dAO AO1, Box2dAO AO2, String primaryObjectName) {
        if (AO1.getName().equalsIgnoreCase(primaryObjectName)) {
            return AO2;
        } else if (AO2.getName().equalsIgnoreCase(primaryObjectName)) {
            return AO1;
        }

        return null;
    }

    protected void createCollisionListener(World world) {

        world.setContactListener(new ContactListener() {

            @Override
            public void beginContact(Contact contact) {
                Fixture fixture1 = contact.getFixtureA();
                Fixture fixture2 = contact.getFixtureB();

                if (fixture1 == null || fixture2 == null)
                    return;
                if (fixture1.getUserData() == null || fixture1.getUserData() == null)
                    return;

                if (isBox2DContact(fixture1, fixture2)) {
                    Box2dAO AO1 = (Box2dAO) fixture1.getUserData();
                    Box2dAO AO2 = (Box2dAO) fixture2.getUserData();

                    if (AO1.getName() == null || AO2.getName() == null) {
                        throw new NullPointerException("Error, could not track collision detection because one of the objects were null. (CollisionDetector)");
                    } else if (!AO1.isTouchable() || !AO2.isTouchable()) {

                    } else {
                        collision(AO1, AO2);
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {
                Fixture fixture1 = contact.getFixtureA();
                Fixture fixture2 = contact.getFixtureB();

                if (fixture1 == null || fixture2 == null)
                    return;
                if (fixture1.getUserData() == null || fixture1.getUserData() == null)
                    return;

                if (isBox2DContact(fixture1, fixture2)) {
                    Box2dAO AO1 = (Box2dAO) fixture1.getUserData();
                    Box2dAO AO2 = (Box2dAO) fixture2.getUserData();


                    if (AO1.getName() == null || AO2.getName() == null) {
                        throw new NullPointerException("Error, could not track collision detection because one of the objects were null. (CollisionDetector)");
                    } else if (!AO1.isTouchable() || !AO2.isTouchable()) {

                    } else {
                        endCollision(AO1, AO2);
                    }
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                Fixture fixture1 = contact.getFixtureA();
                Fixture fixture2 = contact.getFixtureB();

                if (fixture1 == null || fixture2 == null)
                    return;
                if (fixture1.getUserData() == null || fixture1.getUserData() == null)
                    return;

                if (isBox2DContact(fixture1, fixture2)) {
                    Box2dAO AO1 = (Box2dAO) fixture1.getUserData();
                    Box2dAO AO2 = (Box2dAO) fixture2.getUserData();


                    if (AO1.getName() == null || AO2.getName() == null) {
                        throw new NullPointerException("Error, could not track collision detection because one of the objects were null. (CollisionDetector)");
                    } else if (!AO1.isTouchable() || !AO2.isTouchable()) {

                    } else {
                        preContact(AO1, AO2);
                    }
                }
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                Fixture fixture1 = contact.getFixtureA();
                Fixture fixture2 = contact.getFixtureB();

                if (fixture1 == null || fixture2 == null)
                    return;
                if (fixture1.getUserData() == null || fixture1.getUserData() == null)
                    return;
                // System.out.println("beginContact" + "between " +
                // fixtureA.toString() + " and " + fixtureB.toString());

                if (isBox2DContact(fixture1, fixture2)) {
                    Box2dAO AO1 = (Box2dAO) fixture1.getUserData();
                    Box2dAO AO2 = (Box2dAO) fixture2.getUserData();


                    if (AO1.getName() == null || AO2.getName() == null) {
                        throw new NullPointerException("Error, could not track collision detection because one of the objects were null. (CollisionDetector)");
                    } else if (!AO1.isTouchable() || !AO2.isTouchable()) {

                    } else {
                        postContact(AO1, AO2);
                    }
                }
            }

            private boolean isBox2DContact(Fixture a, Fixture b) {
                return (a.getUserData() instanceof Box2dAO && b.getUserData() instanceof Box2dAO);

            }
        });
    }
}
