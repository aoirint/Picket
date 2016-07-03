package com.kanomiya.picket.render;

import java.awt.Graphics2D;

import com.kanomiya.picket.game.Game;

public abstract class RenderBase<T>
{
    Game game;

    public RenderBase(Game game)
    {
        this.game = game;
    }

    public abstract void render(T obj, Graphics2D g);

}
