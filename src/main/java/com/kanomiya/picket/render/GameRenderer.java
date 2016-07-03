package com.kanomiya.picket.render;

import java.awt.Color;
import java.awt.Graphics2D;

import com.kanomiya.picket.game.Game;
import com.kanomiya.picket.render.screen.IScreenPainter;
import com.kanomiya.picket.render.screen.Screen;
import com.kanomiya.picket.world.FieldMap;

public class GameRenderer implements IScreenPainter
{
    Game game;
    RenderMap mapRenderer;

    public GameRenderer(Game game)
    {
        this.game = game;
        this.mapRenderer = new RenderMap(game);
    }

    @Override
    public void paint(Screen screen)
    {
        Graphics2D g = screen.getGraphics();

        g.setColor(Color.BLACK);
        g.clearRect(0, 0, screen.getWidth(), screen.getHeight());

        FieldMap map = game.world().getMap("dbg1");
        mapRenderer.render(map, g);


    }

}
