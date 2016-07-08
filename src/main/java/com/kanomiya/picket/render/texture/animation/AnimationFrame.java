package com.kanomiya.picket.render.texture.animation;

import java.util.Map;

import com.kanomiya.picket.util.IDataSerializer;

public class AnimationFrame
{
    public final String variantId;
    public final int delay;

    public AnimationFrame(String variantId, int delay)
    {
        this.variantId = variantId;
        this.delay = delay;
    }



    public static class DataSerializerAnimationFrame implements IDataSerializer<AnimationFrame>
    {
        public DataSerializerAnimationFrame()
        {

        }

        @Override
        public Map<String, Object> serialize(AnimationFrame frame)
        {

            return null;
        }

        @Override
        public AnimationFrame deserialize(Map<String, Object> map)
        {
            String variantId = (String) map.get("variant");
            int delay = (int) map.get("delay");

            return new AnimationFrame(variantId, delay);
        }

    }

}
