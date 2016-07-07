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


    private int cameraX, cameraY;

    public RenderMap(Game game)
    {
        super(game);

    }

    public void setCamera(IngameEvent observer)
    {
        int cameraX = observer.x -TILE_COLUMN/2;
        int cameraY = observer.y -TILE_ROW/2;

        if (cameraX < 0) cameraX = 0;
        else if (observer.map.width() <= cameraX) cameraX = observer.map.width() -1 -TILE_COLUMN/2;

        if (cameraY < 0) cameraY = 0;
        else if (observer.map.height() <= cameraY) cameraY = observer.map.height() -1 -TILE_ROW/2;

        setCamera(cameraX, cameraY);
    }

    public void setCamera(int cameraX, int cameraY)
    {
        this.cameraX = cameraX;
        this.cameraY = cameraY;
    }

    public int cameraX()
    {
        return cameraX;
    }
    public int cameraY()
    {
        return cameraY;
    }

    @Override
    public void render(FieldMap map, Graphics2D g)
    {

        if (map.background != null)
        {
            int sx = -cameraX *TILE_SIZE;
            int sy = -cameraY *TILE_SIZE;

            g.translate(sx, sy);
            g.setClip(0, 0, WIDTH, HEIGHT);
            renderTexture(map.background, map.backgroundRenderInfo, g);
            g.setClip(null);
            g.translate(-sx, -sy);
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

                if (tile.texture != null)
                {
                    g.translate(i *TILE_SIZE, j *TILE_SIZE);
                    renderTexture(tile.texture, tile.renderInfo, g);
                    g.translate(-i *TILE_SIZE, -j *TILE_SIZE);
                }
            }
        }

    }

}
