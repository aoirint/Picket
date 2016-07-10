package com.kanomiya.picket.render.texture;

import java.util.Map;

import com.kanomiya.picket.util.IDataSerializer;

public class TextureFrame
{
    public final TextureVariant variant;
    public final int delay;

    public TextureFrame(TextureVariant variant)
    {
        this(variant, 0);
    }

    public TextureFrame(TextureVariant variant, int delay)
    {
        this.variant = variant;
        this.delay = delay;
    }



    public static class DataSerializerTextureFrame implements IDataSerializer<TextureFrame>
    {
        private final Map<String, TextureVariant> variants;

        public DataSerializerTextureFrame(Map<String, TextureVariant> variants)
        {
            this.variants = variants;
        }

        @Override
        public Map<String, Object> serialize(TextureFrame frame)
        {

            return null;
        }

        @Override
        public TextureFrame deserialize(Map<String, Object> map)
        {
            TextureVariant variant = variants.get(map.get("variant"));
            int delay = (int) map.getOrDefault("delay", 0);

            return new TextureFrame(variant, delay);
        }

    }

}
