package com.kanomiya.picket.render.texture.animation;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

public class Animation
{
    public final List<AnimationCommand> commands;
    public final List<Integer> tickForUpdate;
    public final int cmdCount;
    public final int maxTick;

    public Animation(List<AnimationCommand> commands)
    {
        this.commands = Collections.unmodifiableList(commands);
        this.cmdCount = commands.size();

        int totalTick = 0;

        List<Integer> tickForUpdate = Lists.newArrayList();

        tickForUpdate.add(0);

        for (int i=1; i<=cmdCount; i++)
        {
            AnimationCommand cmd = commands.get(i -1);
            int updateTick = cmd.delay;

            Iterator<Integer> itr = tickForUpdate.iterator();
            while (itr.hasNext())
            {
                updateTick += itr.next();
            }

            tickForUpdate.add(updateTick);
            totalTick += cmd.delay;
        }

        this.tickForUpdate = Collections.unmodifiableList(tickForUpdate);
        this.maxTick = totalTick;
    }
}
