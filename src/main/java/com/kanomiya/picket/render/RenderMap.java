package com.kanomiya.picket.render;

import java.awt.Color;
import java.awt.Graphics2D;

import com.kanomiya.picket.game.Game;
import com.kanomiya.picket.world.FieldMap;
import com.kanomiya.picket.world.IngameEvent;
import com.kanomiya.picket.world.Tile;

public class RenderMap extends RenderBase<FieldMap>
{
    public static final int TILE_SIZE = 32;

    public static final int TILE_COLUMN = 21;
    public static final int TILE_ROW = 15;

    public static final int WIDTH = TILE_COLUMN * TILE_SIZE;
    public static final int HEIGHT = TILE_ROW * TILE_SIZE;


    private final TextureRenderInfo renderInfo;
    public int cameraX, cameraY;

    public RenderMap(Game game)
    {
        super(game);
        renderInfo = new TextureRenderInfo();

        renderInfo.enableSourceOffset = true;

    }

    public void setCamera(IngameEvent observer)
    {
        cameraX = observer.x -TILE_COLUMN/2;
        cameraY = observer.y -TILE_ROW/2;
    }

    @Override
    public void render(FieldMap map, Graphics2D g)
    {
        renderInfo.sourceOffsetX = cameraX *TILE_SIZE;
        renderInfo.sourceOffsetY = cameraY *TILE_SIZE;

        if (map.background() != null)
        {
            g.setClip(0, 0, WIDTH, HEIGHT);
            renderTexture(game.registry().texture(map.background()), renderInfo, g);
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
            if (cameraX +i < 0 || map.width() <= cameraX +i) continue;

            for (int j=0; j<TILE_ROW; j++)
            {
                if (cameraY +j < 0 || map.height() <= cameraY +j) continue;

                Tile tile = map.tileAt(cameraX +i, cameraY +j);

                if (tile.texture() != null)
                {
                    Texture texture = tile.texture();

                    g.translate(i *TILE_SIZE, j *TILE_SIZE);
                    renderTexture(texture, null, g);
                    g.translate(-i *TILE_SIZE, -j *TILE_SIZE);
                }
            }
        }

    }

}
