package com.kanomiya.picket.render.texture;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.kanomiya.picket.render.texture.TextureLayer.DataSerializerTextureLayer;
import com.kanomiya.picket.render.texture.TextureStyle.DataSerializerTextureStyle;
import com.kanomiya.picket.util.IDataSerializer;

public class Texture
{
    public final String id;
    public final List<TextureLayer> layers;
    public final List<TextureStyle> styles;


    public Texture(String id, List<TextureLayer> layers, List<TextureStyle> styles)
    {
        this.id = id;
        this.layers = layers;
        this.styles = styles;
    }


    public static class DataSerializerTexture implements IDataSerializer<Texture>
    {
        private final DataSerializerTextureLayer layerSerializer;
        private final DataSerializerTextureStyle styleSerializer;

        public DataSerializerTexture()
        {
            layerSerializer = new DataSerializerTextureLayer();
            styleSerializer = new DataSerializerTextureStyle(layerSerializer);
        }

        @Override
        public Map<String, Object> serialize(Texture texture)
        {

            return null;
        }

        @Override
        public Texture deserialize(Map<String, Object> map)
        {
            final String id = (String) map.get("id");

            List<TextureLayer> layers = Lists.newArrayList();

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> layerDataList = (List<Map<String, Object>>) map.get("layers");

            layerDataList.forEach(layerData ->
            {
                layers.add(layerSerializer.deserialize(layerData));
            });


            List<TextureStyle> styles = Lists.newArrayList();

            if (map.containsKey("styles"))
            {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> styleDataList = (List<Map<String, Object>>) map.get("styles");

                styleDataList.forEach(styleData ->
                {
                    styles.add(styleSerializer.deserialize(styleData));
                });
            }

            return new Texture(id, layers, Collections.unmodifiableList(styles));
        }

    }

}
