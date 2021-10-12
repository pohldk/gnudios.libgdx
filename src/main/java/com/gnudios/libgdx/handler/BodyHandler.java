package com.gnudios.libgdx.handler;

import com.badlogic.gdx.physics.box2d.*;
import com.gnudios.libgdx.model.Box2dAO;
import com.gnudios.libgdx.model.BodyShape;


public class BodyHandler {

    public Body createRectangleBody(Box2dAO box2dAO, World world, boolean isStatic) {
        return createBody(box2dAO, world, isStatic, BodyShape.SHAPE_RECTANGLE);
    }

    public Body createCircleBody(Box2dAO box2dAO, World world, boolean isStatic) {
        return createBody(box2dAO, world, isStatic, BodyShape.SHAPE_CIRCLE);
    }

    public Body createEdgeBody(Box2dAO box2dAO, World world, boolean isStatic) {
        return createBody(box2dAO, world, isStatic, BodyShape.SHAPE_EDGE);
    }

    public Body createChainBody(Box2dAO box2dAO, World world, boolean isStatic) {
        return createBody(box2dAO, world, isStatic, BodyShape.SHAPE_CHAIN);
    }

    private Body createBody(Box2dAO box2dAO, World world, boolean isStatic, BodyShape shapeStr) {
        BodyDef def = new BodyDef();
        if (isStatic) {
            def.type = BodyDef.BodyType.StaticBody;
        } else {
            def.type = BodyDef.BodyType.DynamicBody;
            def.fixedRotation = false;
        }

        def.position.set(box2dAO.getX() + (box2dAO.getWidth() / 2), box2dAO.getY() + (box2dAO.getHeight() / 2));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef = createShape(box2dAO, fixtureDef, shapeStr);

        fixtureDef.density = 1.0f;
        //FRICTION EDIT
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.5f;
        // fixtureDef.isSensor = true;

        Body body = world.createBody(def);
        body.setLinearDamping(0.20f);
        body.setAngularDamping(0.40f);

        body.createFixture(fixtureDef).setUserData(box2dAO);
        fixtureDef.shape.dispose();
        return body;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    private Body createRectangleBody(Box2dAO box2dAO, World world, boolean isStatic, float width, float height) {
        BodyDef def = new BodyDef();
        if (isStatic) {
            def.type = BodyDef.BodyType.StaticBody;
        } else {
            def.type = BodyDef.BodyType.DynamicBody;
            def.fixedRotation = false;
        }

        def.position.set(box2dAO.getX() + (box2dAO.getWidth() / 2), box2dAO.getY() + (box2dAO.getHeight() / 2));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef = createRectangleShape(fixtureDef, height, width);

        fixtureDef.density = 1.0f;

        Body body = world.createBody(def);
        body.createFixture(fixtureDef).setUserData(box2dAO);
        fixtureDef.shape.dispose();
        return body;
    }

    private FixtureDef createShape(Box2dAO box2dAO, FixtureDef fixtureDef, BodyShape shapeStr) {
        float radius = (((box2dAO.getWidth() / 2) + (box2dAO.getHeight() / 2)) / 2);

        switch (shapeStr) {
            case SHAPE_CIRCLE:
                CircleShape shape1 = new CircleShape();
                shape1.setRadius(radius);
                fixtureDef.shape = shape1;
                break;
            case SHAPE_RECTANGLE:
                PolygonShape shape2 = new PolygonShape();
                shape2.setAsBox(box2dAO.getWidth() / 2, box2dAO.getHeight() / 2);
                fixtureDef.shape = shape2;
                break;
            case SHAPE_EDGE:
                EdgeShape shape3 = new EdgeShape();
                // Add Edge spefic properties here.
                shape3.setRadius(radius);
                fixtureDef.shape = shape3;
                break;
            case SHAPE_CHAIN:
                ChainShape shape4 = new ChainShape();
                // Add Chain spefic properties here.
                shape4.setRadius(radius);
                fixtureDef.shape = shape4;
                break;
        }

        return fixtureDef;
    }

    private FixtureDef createRectangleShape(FixtureDef fixtureDef, float width, float height) {

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);
        fixtureDef.shape = shape;

        return fixtureDef;
    }

    public void setRadius(Box2dAO box2dAO, float radius) {
        // 1 body can have multiple fixtures (shapes) but we work only with 1,
        // so getting first in array.
        box2dAO.getBody().getFixtureList().get(0).getShape().setRadius(radius);
    }

    public Body setSizeRectangle(Box2dAO box2dAO, World world, boolean isStatic, float width, float height) {
        world.destroyBody(box2dAO.getBody());
        return createRectangleBody(box2dAO, world, isStatic, width / 2, height / 2);
    }

    public float getRadius(Box2dAO box2dAO) {
        // 1 body can have multiple fixtures (shapes) but we work only with 1,
        // so getting first in array.
        return box2dAO.getBody().getFixtureList().get(0).getShape().getRadius();
    }
}
