package com.kanomiya.picket.game;

import com.kanomiya.picket.render.GameRenderer;
import com.kanomiya.picket.world.World;

public class Game
{
    GameInfo info;
    World world;
    GameRegistry registry;

    GameRenderer renderer;

    public Game(GameInfo info, World world, GameRegistry registry)
    {
        this.info = info;
        this.world = world;
        this.registry = registry;

        this.renderer = new GameRenderer(this);
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

    public GameRenderer renderer()
    {
        return renderer;
    }

}
