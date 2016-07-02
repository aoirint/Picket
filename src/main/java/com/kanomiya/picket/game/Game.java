package com.kanomiya.picket.game;

import com.kanomiya.picket.world.World;

public class Game
{
    GameInfo info;
    World world;
    GameRegistry registry;

    public Game(GameInfo info, World world, GameRegistry registry)
    {
        this.info = info;
        this.world = world;
        this.registry = registry;
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

}
