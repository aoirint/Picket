package com.kanomiya.picket.render.texture;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.kanomiya.picket.render.texture.TextureVariant.DataSerializerTextureVariant;
import com.kanomiya.picket.render.texture.TextureVariantSelector.DataSerializerTextureVariantSelector;
import com.kanomiya.picket.util.IDataSerializer;

public class Texture
{
    public final String id;
    public final boolean enableDirection;

    public final Map<String, TextureVariant> variants;

    public final Map<String, TextureVariantSelector> variantSelectors;



    public Texture(String id, boolean enableDirection, TextureLayer layer)
    {
        this(id, enableDirection, Arrays.asList(layer));
    }
    public Texture(String id, boolean enableDirection, List<TextureLayer> layers)
    {
        this(id, enableDirection, new HashMap<String, TextureVariant>(){ { put("normal", new TextureVariant("normal", layers)); } });
    }

    public Texture(String id, boolean enableDirection, Map<String, TextureVariant> variants)
    {
        this(id, enableDirection, variants, new HashMap<String, TextureVariantSelector>(){ { put("normal", new TextureVariantSelector("normal", new TextureFrame(variants.get("normal")))); } });
    }

    public Texture(String id, boolean enableDirection, Map<String, TextureVariant> variants, Map<String, TextureVariantSelector> variantSelectors)
    {
        this.id = id;
        this.enableDirection = enableDirection;
        this.variants = variants;

        this.variantSelectors = variantSelectors;
    }





    public static class DataSerializerTexture implements IDataSerializer<Texture>
    {
        private final DataSerializerTextureVariant variantSerializer;

        public DataSerializerTexture()
        {
            variantSerializer = new DataSerializerTextureVariant();
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

            @SuppressWarnings("unchecked")
            Map<String, Map<String, Object>> variantDataList = (Map<String, Map<String, Object>>) map.get("variants");
            Map<String, TextureVariant> variants = Maps.newHashMap();

            boolean enableDirection = (boolean) map.getOrDefault("enableDirection", false);

            variantDataList.forEach((variantId, variantData) ->
            {
                variantData.put("id", variantId);

                variants.put(variantId, variantSerializer.deserialize(variantData));
            });


            DataSerializerTextureVariantSelector variantSelectorSerializer = new DataSerializerTextureVariantSelector(variants);

            Map<String, TextureVariantSelector> variantSelectors = Maps.newHashMap();
            if (map.containsKey("variantSelector"))
            {
                @SuppressWarnings("unchecked")
                Map<String, Map<String, Object>> variantSelectorDataMap = (Map<String, Map<String, Object>>) map.get("variantSelector");


                variantSelectorDataMap.forEach((variantSelectorId, variantSelectorData) ->
                {
                    variantSelectorData.put("id", variantSelectorId);

                    variantSelectors.put(variantSelectorId, variantSelectorSerializer.deserialize(variantSelectorData));
                });

            }

            if (! variantSelectors.containsKey("normal"))
            {
                variantSelectors.put("normal", new TextureVariantSelector("normal", new TextureFrame(variants.get("normal"))));
            }

            return new Texture(id, enableDirection, variants, variantSelectors);
        }

    }

}
