package com.kanomiya.picket.render.texture.animation;

import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;

import com.kanomiya.picket.render.texture.TextureStyleSelector;
import com.kanomiya.picket.util.IDataSerializer;

public class AnimationCommand
{
    public static final Consumer<Map<String, Object>> NOOP = (obj) -> {};

    public final String raw;
    public final Consumer<Map<String, Object>> compiled;
    public final int delay;

    public AnimationCommand(String rawCmd, int delay)
    {
        this.raw = rawCmd;

        Matcher matcher = TextureStyleSelector.PAT_ATTR.matcher(rawCmd);

        if (matcher.matches())
        {
            boolean flag1 = matcher.group(1) == null;

            if (matcher.group(3) == null) // [attr]
            {
                compiled = flag1 ?
                        (properties) -> properties.put(matcher.group(2), "")
                        : (properties) -> properties.remove(matcher.group(2));
            } else // [attr=val]
            {
                boolean flag2 = matcher.group(4) == null;

                compiled = flag1 ? (properties) -> properties.put(matcher.group(2), matcher.group(5))
                        : flag2 ? (properties) -> properties.remove(matcher.group(2))
                                : (properties) -> properties.remove(matcher.group(2), matcher.group(5));
            }

        } else
        {
            compiled = NOOP;
        }

        this.delay = delay;
    }



    public static class DataSerializerAnimationCommand implements IDataSerializer<AnimationCommand>
    {

        public DataSerializerAnimationCommand()
        {
        }

        @Override
        public Map<String, Object> serialize(AnimationCommand cmd)
        {

            return null;
        }

        @Override
        public AnimationCommand deserialize(Map<String, Object> map)
        {
            String cmd = (String) map.get("cmd");
            int delay = (int) map.get("delay");

            return new AnimationCommand(cmd, delay);
        }

    }

}
