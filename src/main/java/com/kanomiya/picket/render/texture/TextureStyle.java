package com.kanomiya.picket.render.texture;

import java.util.Map;

import com.google.common.collect.Maps;
import com.kanomiya.picket.render.texture.TextureStylePart.DataSerializerTextureStylePart;
import com.kanomiya.picket.util.IDataSerializer;

public class TextureStyle
{
    public final TextureStyleSelector selector;
    public final Map<String, TextureStylePart> layers;

    public TextureStyle(TextureStyleSelector selector, Map<String, TextureStylePart> layers)
    {
        this.selector = selector;
        this.layers = layers;
    }



    public static class DataSerializerTextureStyle implements IDataSerializer<TextureStyle>
    {
        private final DataSerializerTextureStylePart stylePartSerializer;

        public DataSerializerTextureStyle()
        {
            this.stylePartSerializer = new DataSerializerTextureStylePart();
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
            Map<String, Map<String, Object>> layerDataMap = (Map<String, Map<String, Object>>) map.get("layers");
            Map<String, TextureStylePart> layers = Maps.newHashMap();

            layerDataMap.forEach((layerId, styleData) ->
            {
                layers.put(layerId, stylePartSerializer.deserialize(styleData));
            });


            return new TextureStyle(selector, layers);
        }

    }

}
