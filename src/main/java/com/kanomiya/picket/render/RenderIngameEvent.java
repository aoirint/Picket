package com.kanomiya.picket.render;

import java.awt.Graphics2D;

import com.kanomiya.picket.game.Game;
import com.kanomiya.picket.render.texture.Texture;
import com.kanomiya.picket.world.IngameEvent;

public class RenderIngameEvent extends RenderBase<IngameEvent>
{

    public RenderIngameEvent(Game game)
    {
        super(game);
    }

    @Override
    public void render(IngameEvent event, Graphics2D g)
    {

        Texture texture = event.texture;

        if (texture != null)
        {
            renderTexture(texture, event.renderInfo, g);

        }

    }

}
