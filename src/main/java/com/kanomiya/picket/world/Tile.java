package com.kanomiya.picket.world;

import java.awt.image.BufferedImage;
import java.util.Map;

import javax.annotation.Nullable;

import com.kanomiya.picket.render.Texture;
import com.kanomiya.picket.render.TextureLayer;
import com.kanomiya.picket.util.IDataSerializer;

public class Tile
{
    private final String id;
    private final Texture texture;

    public Tile(String id, @Nullable Texture texture)
    {
        this.id = id;
        this.texture = texture;
    }

    public String id()
    {
        return id;
    }

    @Nullable
    public Texture texture()
    {
        return texture;
    }


    @Override
    public String toString()
    {
        return "Tile [id=" + id + ", texture=" + texture + "]";
    }


    public static class DataSerializerTile implements IDataSerializer<Tile>
    {
        Map<String, BufferedImage> imageRegistry;
        Map<String, Texture> textureRegistry;

        public DataSerializerTile(Map<String, BufferedImage> imageRegistry, Map<String, Texture> textureRegistry)
        {
            this.imageRegistry = imageRegistry;
            this.textureRegistry = textureRegistry;
        }

        @Override
        public Map<String, Object> serialize(Tile tile)
        {

            return null;
        }

        @Override
        public Tile deserialize(Map<String, Object> map)
        {
            String id = (String) map.get("id");

            String textureId = (String) map.getOrDefault("texture", "tiles/" + id);

            if (! textureRegistry.containsKey(textureId) && imageRegistry.containsKey(textureId))
            {
                textureRegistry.put(textureId, new Texture(textureId, false, new TextureLayer(textureId, 0d)));
            }

            Texture texture = textureRegistry.get(textureId);


            return new Tile(id, texture);
        }

    }

}
