package com.kanomiya.picket.render;

import java.awt.Color;
import java.awt.Graphics2D;

import com.kanomiya.picket.game.Game;
import com.kanomiya.picket.world.FieldMap;
import com.kanomiya.picket.world.tile.Tile;

public class RenderMap extends RenderBase<FieldMap>
{
    public static final int TILE_SIZE = 32;

    public static final int TILE_COLUMN = 21;
    public static final int TILE_ROW = 15;

    public static final int WIDTH = TILE_COLUMN * TILE_SIZE;
    public static final int HEIGHT = TILE_ROW * TILE_SIZE;

    private RenderTexture textureRenderer;

    public RenderMap(Game game)
    {
        super(game);

        textureRenderer = new RenderTexture(game);
    }

    @Override
    public void render(FieldMap map, Graphics2D g)
    {
        if (map.background() != null)
        {
            g.setClip(0, 0, WIDTH, HEIGHT);
            textureRenderer.render(game.registry().texture(map.background()), g);
            g.setClip(null);
        }

        g.setColor(Color.GRAY);

        for (int x=0; x<=TILE_COLUMN; x++)
        {
            g.drawLine(x *TILE_SIZE, 0, x *TILE_SIZE, HEIGHT);
        }

        for (int y=0; y<=TILE_ROW; y++)
        {
            g.drawLine(0, y *TILE_SIZE, WIDTH, y *TILE_SIZE);
        }

        for (int i=0; i<TILE_COLUMN; i++)
        {
            if (map.width() <= i) break;

            for (int j=0; j<TILE_ROW; j++)
            {
                if (map.height() <= j) break;

                Tile tile = map.tileAt(i, j);

                if (tile.texture() != null)
                {
                    Texture texture = game.registry().texture(tile.texture());

                    g.translate(i *TILE_SIZE, j *TILE_SIZE);
                    textureRenderer.render(texture, g);
                    g.translate(-i *TILE_SIZE, -j *TILE_SIZE);
                }
            }
        }

    }

}
