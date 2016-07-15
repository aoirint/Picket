package com.kanomiya.picket.render.texture;

import java.util.Map;

import com.kanomiya.picket.render.texture.TextureStylePart.DataSerializerTextureStylePart;
import com.kanomiya.picket.util.IDataSerializer;

public class TextureLayer
{
    public final String id;
    public final TextureStylePart baseStyle;

    public TextureLayer(String id)
    {
        this.id = id;
        this.baseStyle = new TextureStylePart(id);
    }

    public TextureLayer(String id, TextureStylePart baseStyle)
    {
        this.id = id;
        this.baseStyle = baseStyle;
    }

    @Override
    public String toString()
    {
        return "TextureLayer [id=" + id + "]";
    }




    public static class DataSerializerTextureLayer implements IDataSerializer<TextureLayer>
    {
        private final DataSerializerTextureStylePart stylePartSerializer;

        public DataSerializerTextureLayer()
        {
            stylePartSerializer = new DataSerializerTextureStylePart();
        }

        @Override
        public Map<String, Object> serialize(TextureLayer layer)
        {

            return null;
        }

        @Override
        public TextureLayer deserialize(Map<String, Object> map)
        {
            String id = (String) map.get("id");

            TextureStylePart baseStyle = stylePartSerializer.deserialize(map);

            return new TextureLayer(id, baseStyle);
        }

    }

}
