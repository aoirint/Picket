package com.kanomiya.picket.render;

import java.awt.Color;
import java.awt.Graphics2D;

import com.kanomiya.picket.game.Game;
import com.kanomiya.picket.render.screen.IScreenPainter;
import com.kanomiya.picket.render.screen.Screen;
import com.kanomiya.picket.world.FieldMap;
import com.kanomiya.picket.world.IngameEvent;

public class GameRenderer implements IScreenPainter
{
    final Game game;
    RenderMap mapRenderer;

    private final RenderIngameEvent eventRenderer;

    public GameRenderer(Game game)
    {
        this.game = game;
        this.mapRenderer = new RenderMap(game);

        eventRenderer = new RenderIngameEvent(game);
    }

    @Override
    public void paint(Screen screen)
    {
        Graphics2D g = screen.getGraphics();

        g.setColor(Color.BLACK);
        g.clearRect(0, 0, screen.getWidth(), screen.getHeight());

        if (game.observer() != null)
        {
            IngameEvent observer = game.observer();

            FieldMap map = observer.map;

            mapRenderer.setCamera(observer);
            mapRenderer.render(map, g);

            g.translate(RenderMap.WIDTH/2 -RenderMap.TILE_SIZE/2, RenderMap.HEIGHT/2 -RenderMap.TILE_SIZE/2);
            eventRenderer.render(observer, g);
            g.translate(-RenderMap.WIDTH/2 +RenderMap.TILE_SIZE/2, -RenderMap.HEIGHT/2 +RenderMap.TILE_SIZE/2);

        }

    }

}
