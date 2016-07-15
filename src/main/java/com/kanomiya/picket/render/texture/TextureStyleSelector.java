package com.kanomiya.picket.render.texture;

import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextureStyleSelector
{
    public static final Predicate<Map<String, Object>> WILD_CARD = (properties) -> true;
    public static final Predicate<Map<String, Object>> UN_WILD_CARD = (properties) -> false;

    public static final Pattern PAT_ATTR = Pattern.compile("\\[(!)?(.*?)((!)?=(.*?))?\\]"); // [attr] / [attr=val]

    public final String raw;
    public final Predicate<Map<String, Object>> compiled;

    public TextureStyleSelector(String raw)
    {
        this.raw = raw;

        if ("*".equals(raw))
        {
            compiled = WILD_CARD;
        } else
        {
            Matcher matcher = PAT_ATTR.matcher(raw);

            if (matcher.matches())
            {
                boolean flag1 = matcher.group(1) == null;

                if (matcher.group(3) == null) // [attr]
                {
                    compiled = (properties) -> flag1 == properties.containsKey(matcher.group(2));
                } else // [attr=val]
                {
                    boolean flag2 = matcher.group(4) == null;

                    compiled = (properties) -> flag2 == (properties.containsKey(matcher.group(2)) && flag2 == matcher.group(5).equals(properties.get(matcher.group(2))));
                }

            } else
            {
                compiled = UN_WILD_CARD;
            }
        }

    }


}
