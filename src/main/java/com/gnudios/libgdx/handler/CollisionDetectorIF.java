package com.gnudios.libgdx.handler;

import com.gnudios.libgdx.model.Box2dAO;

public interface CollisionDetectorIF {
    void collision(Box2dAO AO1, Box2dAO AO2);
    void endCollision(Box2dAO AO1, Box2dAO AO2);
    void preContact(Box2dAO AO1, Box2dAO AO2);
    void postContact(Box2dAO AO1, Box2dAO AO2);
}
