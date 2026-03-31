package com.app.main;

import com.app.utils.Entity;
import com.app.utils.EntityRequest;

public final class Main {
    private static final Gui sGui = new Gui();

    public static void main(String[] args) {
        while (true) {
            EntityRequest req = new EntityRequest();
            Entity entity = req.waitForEntity();
            if (entity == null)
                break;
            sGui.addEntity(entity.getName(), entity);
        }
        sGui.start();
    }
}
