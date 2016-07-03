package com.kanomiya.picket.game;

import com.kanomiya.picket.control.GameController;
import com.kanomiya.picket.render.GameRenderer;
import com.kanomiya.picket.world.Player;
import com.kanomiya.picket.world.World;

public class Game
{
    GameInfo info;
    World world;
    GameRegistry registry;
    Player player;

    GameRenderer renderer;
    GameController controller;

    public Game(GameInfo info, World world, GameRegistry registry, Player player)
    {
        this.info = info;
        this.world = world;
        this.registry = registry;
        this.player = player;

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

    public Player player()
    {
        return player;
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
