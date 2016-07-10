package com.kanomiya.picket.render;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.annotation.Nullable;

import com.kanomiya.picket.game.Game;
import com.kanomiya.picket.render.texture.Texture;
import com.kanomiya.picket.render.texture.TextureLayer;
import com.kanomiya.picket.render.texture.TextureRenderInfo;
import com.kanomiya.picket.render.texture.TextureVariant;

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
        TextureVariant variant = null;

        int basesx = 0;
        int basesy = 0;

        int basedx = 0;
        int basedy = 0;

        int basewidth = -1;
        int baseheight = -1;

        if (info != null)
        {
            if (info.enableSourceOffset)
            {
                basesx = info.sourceOffsetX;
                basesy = info.sourceOffsetY;
            }

            if (info.enableDestOffset)
            {
                basedx = info.destOffsetX;
                basedy = info.destOffsetY;
            }

            if (info.enableSize)
            {
                basewidth = info.width;
                baseheight = info.height;
            }

            if (info.animationFrame != null)
            {
                variant = info.animationFrame.variant;
            }
        }

        if (variant == null) variant = texture.variants.get("normal");

        List<TextureLayer> layers = variant.layers;
        if (layers != null)
        {
            for (TextureLayer layer: layers)
            {

                if (layer.imageId != null)
                {
                    BufferedImage image = game.registry.image(layer.imageId);

                    int sx = basesx;
                    int sy = basesy;

                    if (layer.sourcePos != null)
                    {
                        sx += layer.sourcePos.x;
                        sy += layer.sourcePos.y;
                    }

                    int swidth = basewidth == -1 ? layer.sourceSize != null ? layer.sourceSize.width : image.getWidth() : basewidth;
                    int sheight = baseheight == -1 ? layer.sourceSize != null ? layer.sourceSize.height : image.getHeight() : baseheight;

                    double theta = layer.rotate/180 *Math.PI;

                    g.rotate(theta, swidth/2, sheight/2);

                    g.drawImage(image, basedx, basedy, basedx +swidth, basedy +sheight, sx, sy, sx +swidth, sy +sheight, null);

                    g.rotate(-theta, swidth/2, sheight/2);

                }
            }

        }

    }



}
