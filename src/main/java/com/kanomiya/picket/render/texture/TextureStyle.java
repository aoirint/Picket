package com.kanomiya.picket.render.texture;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.kanomiya.picket.render.texture.TextureLayer.DataSerializerTextureLayer;
import com.kanomiya.picket.util.IDataSerializer;

public class TextureStyle
{
    public final TextureStyleSelector selector;
    public final List<TextureLayer> layers;

    public TextureStyle(TextureStyleSelector selector, List<TextureLayer> layers)
    {
        this.selector = selector;
        this.layers = layers;
    }



    public static class DataSerializerTextureStyle implements IDataSerializer<TextureStyle>
    {
        private final DataSerializerTextureLayer layerSerializer;

        public DataSerializerTextureStyle(DataSerializerTextureLayer layerSerializer)
        {
            this.layerSerializer = layerSerializer;
        }

        @Override
        public Map<String, Object> serialize(TextureStyle style)
        {

            return null;
        }

        @Override
        public TextureStyle deserialize(Map<String, Object> map)
        {
            TextureStyleSelector selector = new TextureStyleSelector((String) map.get("selector"));

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> layerDataList = (List<Map<String, Object>>) map.get("layers");
            List<TextureLayer> layers = Lists.newArrayList();

            layerDataList.forEach(layerData ->
            {
                layers.add(layerSerializer.deserialize(layerData));
            });


            return new TextureStyle(selector, layers);
        }

    }

}
