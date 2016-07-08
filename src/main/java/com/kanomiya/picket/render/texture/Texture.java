package com.kanomiya.picket.render.texture;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kanomiya.picket.render.texture.TextureLayer.DataSerializerTextureLayer;
import com.kanomiya.picket.render.texture.animation.Animation;
import com.kanomiya.picket.render.texture.animation.AnimationFrame;
import com.kanomiya.picket.render.texture.animation.AnimationFrame.DataSerializerAnimationFrame;
import com.kanomiya.picket.util.IDataSerializer;

public class Texture
{
    public final String id;
    public final boolean enableDirection;

    public final Map<String, List<TextureLayer>> variants;

    @Nullable
    public final Animation animation;



    public Texture(String id, boolean enableDirection, TextureLayer layer)
    {
        this(id, enableDirection, Arrays.asList(layer));
    }
    public Texture(String id, boolean enableDirection, List<TextureLayer> layers)
    {
        this(id, enableDirection, new HashMap<String, List<TextureLayer>>(){ { put("normal", layers); } }, null);
    }

    public Texture(String id, boolean enableDirection, Map<String, List<TextureLayer>> variants, @Nullable List<AnimationFrame> animationFrames)
    {
        this.id = id;
        this.enableDirection = enableDirection;
        this.variants = variants;

        this.animation = animationFrames != null ? new Animation(animationFrames) : null;

    }





    public static class DataSerializerTexture implements IDataSerializer<Texture>
    {
        private final DataSerializerTextureLayer layerSerializer;
        private final DataSerializerAnimationFrame frameSerializer;

        public DataSerializerTexture()
        {
            layerSerializer = new DataSerializerTextureLayer();
            frameSerializer = new DataSerializerAnimationFrame();
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
            Map<String, List<Map<String, Object>>> variantDataList = (Map<String, List<Map<String, Object>>>) map.get("variants");
            Map<String, List<TextureLayer>> variants = Maps.newHashMap();

            boolean enableDirection = (boolean) map.getOrDefault("enableDirection", false);

            variantDataList.forEach((variantId, variantData) ->
            {
                List<TextureLayer> layers = Lists.newArrayList();

                variantData.forEach(layerData ->
                {
                    layers.add(layerSerializer.deserialize(layerData));
                });

                variants.put(variantId, layers);
            });

            List<AnimationFrame> animation;
            if (map.containsKey("animation"))
            {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> animationDataList = (List<Map<String, Object>>) map.get("animation");

                animation = Lists.newArrayList();

                animationDataList.forEach(animationData ->
                {
                    animation.add(frameSerializer.deserialize(animationData));
                });

            } else animation = null;


            return new Texture(id, enableDirection, variants, animation);
        }

    }

}
