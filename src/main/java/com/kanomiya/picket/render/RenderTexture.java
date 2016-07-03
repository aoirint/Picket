package com.kanomiya.picket.render;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.kanomiya.picket.game.Game;

public class RenderTexture extends RenderBase<Texture>
{

    public RenderTexture(Game game)
    {
        super(game);
    }

    @Override
    public void render(Texture texture, Graphics2D g)
    {
        TextureLayer[] layers = texture.layers();

        for (int i=0; i<layers.length; i++)
        {
            TextureLayer layer = layers[i];

            BufferedImage image = game.registry().image(layer.imageId);

            g.drawImage(image, 0,0, null);
        }
    }

}
