package com.kanomiya.picket.game;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.kanomiya.picket.render.Texture;
import com.kanomiya.picket.tile.Tile;

public abstract class GameRegistry
{
    Map<String, BufferedImage> imageRegistry;
    Map<String, Texture> textureRegistry;
    Map<String, Tile> tileRegistry;


    public BufferedImage image(String id)
    {
        return imageRegistry.getOrDefault(id, imageRegistry.get("missing"));
    }

    public Texture texture(String id)
    {
        return textureRegistry.getOrDefault(id, textureRegistry.get("missing"));
    }

    public Tile tile(String id)
    {
        return tileRegistry.getOrDefault(id, tileRegistry.get("null"));
    }

    @Override
    public String toString()
    {
        final int maxLen = 10;
        return "GameRegistry [imageRegistry="
                + (imageRegistry != null ? toString(imageRegistry.entrySet(), maxLen) : null) + ", textureRegistry="
                + (textureRegistry != null ? toString(textureRegistry.entrySet(), maxLen) : null) + ", tileRegistry="
                + (tileRegistry != null ? toString(tileRegistry.entrySet(), maxLen) : null) + "]";
    }

    private String toString(Collection<?> collection, int maxLen)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        int i = 0;
        for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++)
        {
            if (i > 0) builder.append(", ");
            builder.append(iterator.next());
        }
        builder.append("]");
        return builder.toString();
    }

}
