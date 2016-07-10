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

    public List<TextureLayer> layers; // ベースと有効なスタイルを統合した一時的なもの

    public int animationTick;

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
        if (layers == null || isPropertyUpdated)
        {
            Map<String, Map<String, Object>> integrated = Maps.newHashMap();

            texture.layers.forEach(layer ->
            {
                Map<String, Object> layerMap = Maps.newHashMap();

                layerMap.put("imageId", layer.imageId);
                if (layer.sourcePos != null)
                {
                    layerMap.put("sourceX", layer.sourcePos.x);
                    layerMap.put("sourceY", layer.sourcePos.y);
                }

                if (layer.sourceSize != null)
                {
                    layerMap.put("sourceWidth", layer.sourceSize.width);
                    layerMap.put("sourceHeight", layer.sourceSize.height);
                }

                layerMap.put("rotate", layer.rotate);

                integrated.put(layer.id, layerMap);
            });

            texture.styles.stream().filter(style -> style.selector.compiled.test(properties)).forEach(style ->
            {
                style.layers.forEach(layer ->
                {
                    Map<String, Object> layerMap = integrated.get(layer.id);
                    if (layerMap == null) layerMap = Maps.newHashMap();

                    layerMap.put("imageId", layer.imageId);

                    if (layer.sourcePos != null)
                    {
                        int sourceX = (int) layerMap.getOrDefault("sourceX", 0);
                        int sourceY = (int) layerMap.getOrDefault("sourceY", 0);

                        layerMap.put("sourceX", sourceX +layer.sourcePos.x);
                        layerMap.put("sourceY", sourceY +layer.sourcePos.y);
                    }

                    if (layer.sourceSize != null)
                    {
                        layerMap.put("sourceWidth", layer.sourceSize.width);
                        layerMap.put("sourceHeight", layer.sourceSize.height);
                    }

                    layerMap.put("rotate", layer.rotate);

                    integrated.put(layer.id, layerMap);
                });
            });


            layers = Lists.newArrayList();

            integrated.forEach((layerId, layerData) ->
            {
                layers.add(new TextureLayer(layerId, layerData.get("imageId"), new Point(layerData.get("sourceX"), layerData.get("sourceY")), ...));
            });

            animationTick = 0;
            isPropertyUpdated = false;
        }


    }

}
