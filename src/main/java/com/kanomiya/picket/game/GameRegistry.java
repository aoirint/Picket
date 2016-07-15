package com.kanomiya.picket.game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;

import com.google.common.collect.Maps;
import com.kanomiya.picket.render.texture.Texture;
import com.kanomiya.picket.render.texture.Texture.DataSerializerTexture;
import com.kanomiya.picket.util.IDataSerializer;
import com.kanomiya.picket.world.Tile;
import com.kanomiya.picket.world.Tile.DataSerializerTile;

public class GameRegistry
{
    @Nonnull
    public final Map<String, BufferedImage> imageRegistry;
    @Nonnull
    public final Map<String, Texture> textureRegistry;
    @Nonnull
    public final Map<String, Tile> tileRegistry;

    public GameRegistry(@Nonnull Map<String, BufferedImage> imageRegistry, @Nonnull Map<String, Texture> textureRegistry, @Nonnull Map<String, Tile> tileRegistry)
    {
        this.imageRegistry = imageRegistry;
        this.textureRegistry = textureRegistry;
        this.tileRegistry = tileRegistry;
    }




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
                + (imageRegistry != null ? toString(imageRegistry.keySet(), maxLen) : null) + ", textureRegistry="
                + (textureRegistry != null ? toString(textureRegistry.keySet(), maxLen) : null) + ", tileRegistry="
                + (tileRegistry != null ? toString(tileRegistry.keySet(), maxLen) : null) + "]";
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



    public static class DataSerializerGameRegistry implements IDataSerializer<GameRegistry>
    {
        private final DataSerializerTexture textureSerializer;

        public DataSerializerGameRegistry()
        {
            textureSerializer = new DataSerializerTexture();
        }

        @Override
        public Map<String, Object> serialize(GameRegistry registry)
        {

            return null;
        }

        @Override
        public GameRegistry deserialize(Map<String, Object> map)
        {
            final Map<String, BufferedImage> imageRegistry = Maps.newHashMap();
            final Map<String, Texture> textureRegistry = Maps.newHashMap();
            final Map<String, Tile> tileRegistry = Maps.newHashMap();

            try
            {
                imageRegistry.put("missing", ImageIO.read(ClassLoader.getSystemResourceAsStream("missing.png")));
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            textureRegistry.put("missing", new Texture("missing"));
            tileRegistry.put("null", new Tile("null", null));



            @SuppressWarnings("unchecked")
            Map<String, Object> imageMap = (Map<String, Object>) map.get("images");
            if (imageMap != null)
            {
                imageMap.forEach((id, path) ->
                {
                    try
                    {
                        imageRegistry.put(id, ImageIO.read(new File((String) path)));

                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                });
            }

            @SuppressWarnings("unchecked")
            Map<String, Map<String, Object>> textureMap = (Map<String, Map<String, Object>>) map.get("textures");
            if (textureMap != null)
            {
                textureMap.forEach((id, textureData) ->
                {
                    textureData.put("id", id);

                    Texture texture = textureSerializer.deserialize(textureData);

                    textureRegistry.put(id, texture);
                });
            }

            imageRegistry.forEach((id, image) ->
            {
                if (! textureRegistry.containsKey(id))
                {
                    textureRegistry.put(id, new Texture(id));
                }
            });


            @SuppressWarnings("unchecked")
            Map<String, Map<String, Object>> tileMap = (Map<String, Map<String, Object>>) map.get("tiles");
            if (tileMap != null)
            {
                DataSerializerTile tileSerializer = new DataSerializerTile(imageRegistry, textureRegistry);

                tileMap.forEach((id, tileData) ->
                {
                    Tile tile = tileSerializer.deserialize(tileData);

                    tileRegistry.put(id, tile);

                });
            }


            return new GameRegistry(imageRegistry, textureRegistry, tileRegistry);
        }

    }


}
