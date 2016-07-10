package com.kanomiya.picket.render.texture;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.kanomiya.picket.render.texture.TextureLayer.DataSerializerTextureLayer;
import com.kanomiya.picket.util.IDataSerializer;

public class TextureVariant
{
    public final String id;
    public final List<TextureLayer> layers;

    public TextureVariant(String id, List<TextureLayer> layers)
    {
        this.id = id;
        this.layers = layers;
    }



    public static class DataSerializerTextureVariant implements IDataSerializer<TextureVariant>
    {
        private final DataSerializerTextureLayer layerSerializer;

        public DataSerializerTextureVariant()
        {
            layerSerializer = new DataSerializerTextureLayer();

        }

        @Override
        public Map<String, Object> serialize(TextureVariant frame)
        {

            return null;
        }

        @Override
        public TextureVariant deserialize(Map<String, Object> map)
        {
            final String id = (String) map.get("id");

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> layerDataList = (List<Map<String, Object>>) map.get("layers");
            List<TextureLayer> layers = Lists.newArrayList();

            layerDataList.forEach(layerData ->
            {
                layers.add(layerSerializer.deserialize(layerData));
            });

            return new TextureVariant(id, layers);
        }

    }

}
