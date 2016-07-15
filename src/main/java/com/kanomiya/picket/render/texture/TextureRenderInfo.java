package com.kanomiya.picket.render.texture;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class TextureRenderInfo
{
    private final Map<String, Object> properties;
    private boolean isPropertyUpdated;

    public List<TextureStylePart> layers; // ベースと有効なスタイルを統合した一時的なもの

    private boolean enableAnimation;
    public int animationTick;

    private Texture cachedTexture;

    public TextureRenderInfo()
    {
        properties = Maps.newHashMap();
    }


    public void setProperty(String key, @Nullable String value)
    {
        if (value == null) properties.remove(key);
        else properties.put(key, value);

        isPropertyUpdated = true;
    }


    public void nextTick(Texture texture)
    {
        if (cachedTexture != texture)
        {
            cachedTexture = texture;

            if (texture.animation != null)
            {
                enableAnimation = true;
                animationTick = 0;
            }

            layers = null;
        }

        if (layers == null || isPropertyUpdated)
        {
            layers = Lists.newArrayList();

            Map<String, TextureStylePart> styles = Maps.newHashMap();

            texture.layers.forEach(layer ->
            {
                styles.put(layer.id, layer.baseStyle);
            });

            texture.styles.forEach(style ->
            {
                if (style.selector.compiled.test(properties))
                {
                    style.layers.forEach((layerId, stylePart) ->
                    {
                        styles.put(layerId, styles.containsKey(layerId) ? styles.get(layerId).merge(stylePart) : stylePart);
                    });
                }
            });

            texture.layers.forEach(layer -> // レイヤーの並び順を保持
            {
                layers.add(styles.get(layer.id));
            });

            isPropertyUpdated = false;
        }

        if (enableAnimation)
        {
            int index = texture.animation.tickForUpdate.indexOf(animationTick); // 0及び末端Tickを含むので注意

            if (index != -1 && index < texture.animation.cmdCount)
            {
                int hash = properties.hashCode();
                texture.animation.commands.get(index).compiled.accept(properties);

                if (hash != properties.hashCode()) isPropertyUpdated = true;
            }

            animationTick ++;

            if (texture.animation.maxTick < animationTick)
            {
                animationTick = 0;
            }
        }

    }

}
