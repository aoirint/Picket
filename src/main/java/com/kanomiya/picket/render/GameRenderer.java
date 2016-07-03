package com.kanomiya.picket.render;

import java.awt.Color;
import java.awt.Graphics2D;

import com.kanomiya.picket.game.Game;
import com.kanomiya.picket.render.screen.IScreenPainter;
import com.kanomiya.picket.render.screen.Screen;
import com.kanomiya.picket.world.FieldMap;

public class GameRenderer implements IScreenPainter
{
    final Game game;
    RenderMap mapRenderer;

    private RenderTexture textureRenderer;

    public GameRenderer(Game game)
    {
        this.game = game;
        this.mapRenderer = new RenderMap(game);

        textureRenderer = new RenderTexture(game);
    }

    @Override
    public void paint(Screen screen)
    {
        Graphics2D g = screen.getGraphics();

        g.setColor(Color.BLACK);
        g.clearRect(0, 0, screen.getWidth(), screen.getHeight());

        FieldMap map = game.player().map;
        mapRenderer.render(map, g);

        if (game.player().texture() != null)
        {
            g.translate(game.player().x *RenderMap.TILE_SIZE, game.player().y *RenderMap.TILE_SIZE);
            textureRenderer.render(game.registry().texture(game.player().texture()), g);
            g.translate(-game.player().x *RenderMap.TILE_SIZE, -game.player().y *RenderMap.TILE_SIZE);
        }

    }

}
