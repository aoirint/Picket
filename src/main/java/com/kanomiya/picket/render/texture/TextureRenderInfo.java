package com.kanomiya.picket.render.texture;

import java.util.Map;

import com.google.common.collect.Maps;

public class TextureRenderInfo
{
    public boolean enableSourceOffset;
    public boolean enableDestOffset;
    public boolean enableSize;

    public int sourceOffsetX, sourceOffsetY;
    public int destOffsetX, destOffsetY;

    public int width, height;

    public int animationTick;
    public TextureFrame animationFrame;

    private final Map<String, String> properties;
    private TextureVariantSelector cachedSelector;

    private boolean isPropertyUpdated;

    public void setProperty(String key, String value)
    {
        properties.put(key, value);
        isPropertyUpdated = true;
    }

    public TextureRenderInfo()
    {
        properties = Maps.newHashMap();
    }

    public void nextTick(Texture texture)
    {
        if (cachedSelector == null || isPropertyUpdated)
        {
            String selectorId = "normal";

            if (properties.containsKey("direction")) // TODO: 普遍的に
            {
                selectorId = "[direction=" + properties.get("direction") + "]";
            }

            cachedSelector = texture.variantSelectors.get(selectorId);
            animationTick = 0;
            isPropertyUpdated = false;
        }

        if (cachedSelector.totalTick < animationTick +1) animationTick = 0;
        else if (1 < cachedSelector.totalFrame) animationTick ++;


        animationFrame = cachedSelector.getFrameFor(animationTick);

    }

}
