package com.kanomiya.picket.render.texture;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.kanomiya.picket.render.texture.TextureLayer.DataSerializerTextureLayer;
import com.kanomiya.picket.render.texture.TextureStyle.DataSerializerTextureStyle;
import com.kanomiya.picket.render.texture.animation.Animation;
import com.kanomiya.picket.render.texture.animation.AnimationCommand;
import com.kanomiya.picket.render.texture.animation.AnimationCommand.DataSerializerAnimationCommand;
import com.kanomiya.picket.util.IDataSerializer;

public class Texture
{
    public final String id;
    public final List<TextureLayer> layers;
    public final List<TextureStyle> styles;

    @Nullable
    public final Animation animation;


    public Texture(String id)
    {
        this(id, new TextureLayer(id));
    }

    public Texture(String id, TextureLayer layer)
    {
        this(id, Arrays.asList(layer));
    }

    public Texture(String id, List<TextureLayer> layers)
    {
        this(id, layers, Collections.emptyList(), null);
    }

    public Texture(String id, List<TextureLayer> layers, List<TextureStyle> styles, @Nullable Animation animation)
    {
        this.id = id;
        this.layers = layers;
        this.styles = styles;
        this.animation = animation;
    }


    public static class DataSerializerTexture implements IDataSerializer<Texture>
    {
        private final DataSerializerTextureLayer layerSerializer;
        private final DataSerializerTextureStyle styleSerializer;
        private final DataSerializerAnimationCommand animationCommandSerializer;

        public DataSerializerTexture()
        {
            layerSerializer = new DataSerializerTextureLayer();
            styleSerializer = new DataSerializerTextureStyle();
            animationCommandSerializer = new DataSerializerAnimationCommand();
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


            Animation animation = null;

            if (map.containsKey("animation"))
            {
                List<AnimationCommand> animations = Lists.newArrayList();

                @SuppressWarnings("unchecked")
                List<Map<String, Object>> animationDataList = (List<Map<String, Object>>) map.get("animation");

                animationDataList.forEach(animationData ->
                {
                    animations.add(animationCommandSerializer.deserialize(animationData));
                });

                animation = new Animation(animations);
            }

            return new Texture(id, Collections.unmodifiableList(layers), Collections.unmodifiableList(styles), animation);
        }

    }

}
