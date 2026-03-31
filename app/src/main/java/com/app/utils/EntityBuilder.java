package com.app.utils;

public final class EntityBuilder {
    private Entity mEntity;

    public EntityBuilder(int idx) {
        mEntity = new Entity(idx);
    }

    public EntityBuilder() {
        mEntity = new Entity();
    }

    public EntityBuilder(Entity e) {
        mEntity = e;
    }

    public EntityBuilder setName(String name) {
        mEntity.setName(name);
        return this;
    }

    public EntityBuilder setAx(float ax) {
        mEntity.setAx(ax);
        return this;
    }

    public EntityBuilder setX(float x) {
        mEntity.setX(x);
        return this;
    }

    public EntityBuilder setVx(float vx) {
        mEntity.setVx(vx);
        return this;
    }

    public EntityBuilder setAy(float ay) {
        mEntity.setAy(ay);
        return this;
    }

    public EntityBuilder setY(float y) {
        mEntity.setY(y);
        return this;
    }

    public EntityBuilder setVy(float vy) {
        mEntity.setY(vy);
        return this;
    }

    public Entity build() {
        return mEntity;
    }
}
