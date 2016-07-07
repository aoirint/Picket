package com.kanomiya.picket.render.texture;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.kanomiya.picket.render.texture.animation.Animation;
import com.kanomiya.picket.render.texture.animation.AnimationFrame;

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


}
