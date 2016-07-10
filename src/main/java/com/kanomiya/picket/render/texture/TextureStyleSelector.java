package com.kanomiya.picket.render.texture;

import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextureStyleSelector
{
    private static final Predicate<Map<String, Object>> WILD_CARD = (properties) -> true;
    private static final Predicate<Map<String, Object>> UN_WILD_CARD = (properties) -> false;

    private static final Pattern PAT_ATTR = Pattern.compile("\\[(.*?)(=(.*?))?\\]"); // [attr] / [attr=val]

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
                if (matcher.group(2) == null) // [attr]
                {
                    compiled = (properties) -> properties.containsKey(matcher.group(1));
                } else // [attr=val]
                {
                    compiled = (properties) -> properties.containsKey(matcher.group(1)) && matcher.group(3).equals(properties.get(matcher.group(1)));
                }

            } else
            {
                compiled = UN_WILD_CARD;
            }
        }

    }


}
