package com.kanomiya.picket.render.texture.animation;

import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

public class Animation
{
    @Nonnull
    public final List<AnimationFrame> frames;

    public final int totalFrame;
    public final int totalTick;

    @Nonnull
    public final List<Integer> frameBorderTick;

    public Animation(@Nonnull List<AnimationFrame> frames)
    {
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

    public int getFrameIdFromTick(int tick)
    {
        if (tick < 0 || totalTick < tick) throw new IllegalArgumentException("Tick should be from 0 to TotalFrame(" + totalFrame + "). Tick=" + tick);

        if (tick < frameBorderTick.get(0)) return 0;

        for (int i=1; i<totalFrame; i++)
        {
            if (frameBorderTick.get(i -1) <= tick && tick < frameBorderTick.get(i))
            {
                return i;
            }
        }

        throw new RuntimeException("No frame was found. This is a bug? Tick=" + tick);
    }


}
