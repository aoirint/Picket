package com.kanomiya.picket.render.texture;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;
import com.kanomiya.picket.render.texture.TextureFrame.DataSerializerTextureFrame;
import com.kanomiya.picket.util.IDataSerializer;

public class TextureVariantSelector
{
    public final String id;

    @Nonnull
    public final List<TextureFrame> frames;

    public final int totalFrame;
    public final int totalTick;

    @Nonnull
    private final List<Integer> frameBorderTick;

    public TextureVariantSelector(String id, @Nonnull TextureFrame frame)
    {
        this(id, Arrays.asList(frame));
    }

    public TextureVariantSelector(String id, @Nonnull List<TextureFrame> frames)
    {
        this.id = id;
        this.frames = frames;

        totalFrame = frames.size();

        frameBorderTick = Lists.newArrayList();
        int delayCount = 0;

        for (int i=0; i<totalFrame; i++)
        {
            delayCount += frames.get(i).delay;
            frameBorderTick.add(delayCount +1);
        }

        totalTick = delayCount;
    }

    public TextureFrame getFrameFor(int tick)
    {
        if (tick < 0 || totalTick < tick) throw new IllegalArgumentException("Tick should be from 0 to TotaTick(" + totalTick + "). Tick=" + tick);

        if (tick < frameBorderTick.get(0)) return frames.get(0);

        for (int i=1; i<totalFrame; i++)
        {
            if (frameBorderTick.get(i -1) <= tick && tick < frameBorderTick.get(i))
            {
                return frames.get(i);
            }
        }

        throw new RuntimeException("No frame was found. This is a bug? Tick=" + tick);
    }


    public static class DataSerializerTextureVariantSelector implements IDataSerializer<TextureVariantSelector>
    {
        private final DataSerializerTextureFrame frameSerializer;

        public DataSerializerTextureVariantSelector(Map<String, TextureVariant> variants)
        {
            frameSerializer = new DataSerializerTextureFrame(variants);
        }

        @Override
        public Map<String, Object> serialize(TextureVariantSelector variantSelector)
        {

            return null;
        }

        @Override
        public TextureVariantSelector deserialize(Map<String, Object> map)
        {
            final String id = (String) map.get("id");

            @SuppressWarnings("unchecked")
            final List<Map<String, Object>> frameDataList = (List<Map<String, Object>>) map.get("frames");

            final List<TextureFrame> frames = Lists.newArrayList();

            frameDataList.forEach(frameData ->
            {
                frames.add(frameSerializer.deserialize(frameData));
            });

            return new TextureVariantSelector(id, frames);
        }

    }
}
