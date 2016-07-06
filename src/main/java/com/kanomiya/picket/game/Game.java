package com.kanomiya.picket.game;

import java.util.Map;

import com.kanomiya.picket.control.GameController;
import com.kanomiya.picket.render.GameRenderer;
import com.kanomiya.picket.world.World;
import com.kanomiya.picket.world.event.IngameEvent;

public class Game
{
    GameInfo info;
    World world;
    GameRegistry registry;

    Map<String, Object> globalRecords;

    IngameEvent observer;
    IngameEvent actor;

    GameRenderer renderer;
    GameController controller;

    public Game(GameInfo info, World world, GameRegistry registry, Map<String, Object> globalRecords)
    {
        this.info = info;
        this.world = world;
        this.registry = registry;
        this.globalRecords = globalRecords;

        this.observer = globalRecords.containsKey("observer") ? world.globalEvent((String) globalRecords.get("observer")) : null;
        this.actor = globalRecords.containsKey("actor") ? world.globalEvent((String) globalRecords.get("actor")) : null;

        this.renderer = new GameRenderer(this);
        this.controller = new GameController(this);
    }

    public GameInfo info()
    {
        return info;
    }

    public World world()
    {
        return world;
    }

    public GameRegistry registry()
    {
        return registry;
    }

    public IngameEvent observer()
    {
        return observer;
    }

    public IngameEvent actor()
    {
        return actor;
    }

    public Map<String, Object> globalRecords()
    {
        return globalRecords;
    }

    public GameRenderer renderer()
    {
        return renderer;
    }

    public GameController controller()
    {
        return controller;
    }

}
