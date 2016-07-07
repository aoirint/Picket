package com.kanomiya.picket.game;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.kanomiya.picket.control.GameController;
import com.kanomiya.picket.render.GameRenderer;
import com.kanomiya.picket.world.IngameEvent;
import com.kanomiya.picket.world.World;

public class Game
{
    @Nonnull
    public final GameInfo info;
    @Nonnull
    public final GameRegistry registry;
    @Nonnull
    public final GameRenderer renderer;
    @Nonnull
    public final GameController controller;


    public World world;

    public Map<String, Object> globalRecords;

    @Nullable
    public IngameEvent observer;

    @Nullable
    public IngameEvent actor;


    public Game(GameInfo info, World world, GameRegistry registry, Map<String, Object> globalRecords)
    {
        this.info = info;
        this.world = world;
        this.registry = registry;
        this.globalRecords = globalRecords;

        this.observer = globalRecords.containsKey("observer") ? world.worldEvent((String) globalRecords.get("observer")) : null;
        this.actor = globalRecords.containsKey("actor") ? world.worldEvent((String) globalRecords.get("actor")) : null;

        this.renderer = new GameRenderer(this);
        this.controller = new GameController(this);
    }


}
