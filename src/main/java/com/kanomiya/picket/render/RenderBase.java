package com.kanomiya.picket.render;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.annotation.Nullable;

import com.kanomiya.picket.game.Game;
import com.kanomiya.picket.render.texture.Texture;
import com.kanomiya.picket.render.texture.TextureLayer;
import com.kanomiya.picket.render.texture.TextureRenderInfo;

public abstract class RenderBase<T>
{
    final Game game;

    public RenderBase(Game game)
    {
        this.game = game;
    }

    public abstract void render(T obj, Graphics2D g);


    public void renderTexture(Texture texture, @Nullable TextureRenderInfo info, Graphics2D g)
    {



        List<TextureLayer> layers = info.layers;
        if (layers != null)
        {
            for (TextureLayer layer: layers)
            {

                if (layer.imageId != null)
                {
                    BufferedImage image = game.registry.image(layer.imageId);

                    int sx = 0;
                    int sy = 0;

                    if (layer.sourcePos != null)
                    {
                        sx += layer.sourcePos.x;
                        sy += layer.sourcePos.y;
                    }

                    int swidth = layer.sourceSize != null ? layer.sourceSize.width : image.getWidth();
                    int sheight = layer.sourceSize != null ? layer.sourceSize.height : image.getHeight();

                    double theta = layer.rotate/180 *Math.PI;

                    g.rotate(theta, swidth/2, sheight/2);

                    g.drawImage(image, 0, 0, swidth, sheight, sx, sy, sx +swidth, sy +sheight, null);

                    g.rotate(-theta, swidth/2, sheight/2);

                }
            }

        }

    }



}
