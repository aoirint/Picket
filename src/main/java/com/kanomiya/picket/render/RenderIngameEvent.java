package com.kanomiya.picket.render;

import java.awt.Graphics2D;

import com.kanomiya.picket.game.Game;
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
        g.translate(event.x *RenderMap.TILE_SIZE, event.y *RenderMap.TILE_SIZE);

        Texture texture = event.texture();

        if (texture != null)
        {
            renderTexture(texture, event.renderInfo, g);

        }

        g.translate(-event.x *RenderMap.TILE_SIZE, -event.y *RenderMap.TILE_SIZE);
    }

}
