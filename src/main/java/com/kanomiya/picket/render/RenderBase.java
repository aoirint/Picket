package com.kanomiya.picket.render;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.annotation.Nullable;

import com.kanomiya.picket.game.Game;

public abstract class RenderBase<T>
{
    Game game;

    public RenderBase(Game game)
    {
        this.game = game;
    }

    public abstract void render(T obj, Graphics2D g);


    public void renderTexture(Texture texture, @Nullable TextureRenderInfo info, Graphics2D g)
    {
        TextureLayer[] layers = texture.layers();

        int sx = 0;
        int sy = 0;

        int dx = 0;
        int dy = 0;

        int width = -1;
        int height = -1;

        if (info != null)
        {
            if (info.enableSourceOffset)
            {
                sx = info.sourceOffsetX;
                sy = info.sourceOffsetY;
            }

            if (info.enableDestOffset)
            {
                dx = info.destOffsetX;
                dy = info.destOffsetY;
            }

            if (info.enableSize)
            {
                width = info.width;
                height = info.height;
            }

        }


        for (int i=0; i<layers.length; i++)
        {
            TextureLayer layer = layers[i];

            if (layer.imageId != null)
            {
                BufferedImage image = game.registry().image(layer.imageId);

                double theta = layer.rotate/180 *Math.PI;
                int w = width == -1 ? image.getWidth() : width;
                int h = height == -1 ? image.getHeight() : height;

                g.rotate(theta, w/2, h/2);

                g.drawImage(image, dx, dy, dx +w, dy +h, sx, sy, sx +w, sy +h, null);

                g.rotate(-theta, w/2, h/2);

            }
        }
    }



}
