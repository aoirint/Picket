package com.kanomiya.picket.render.texture;

import javax.annotation.Nonnull;

import com.kanomiya.picket.render.texture.animation.Animation;

public class TextureRenderInfo
{
    public boolean enableSourceOffset;
    public boolean enableDestOffset;
    public boolean enableSize;

    public int sourceOffsetX, sourceOffsetY;
    public int destOffsetX, destOffsetY;

    public int width, height;

    public boolean enableVariantId;
    public String variantId = "normal";

    public int animationTick;
    public int animationFrameId;

    private Animation cachedAnimation;

    public TextureRenderInfo()
    {

    }

    public void nextTick(@Nonnull Animation animation)
    {
        if (cachedAnimation != animation)
        {
            cachedAnimation = animation;
            animationTick = 0;
        }

        if (animation.totalTick < animationTick +1) animationTick = 0;
        else animationTick ++;

        animationFrameId = animation.getFrameIdFromTick(animationTick);

    }

}
