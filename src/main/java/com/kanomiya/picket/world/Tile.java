package com.kanomiya.picket.world;

import java.awt.image.BufferedImage;
import java.util.Map;

import javax.annotation.Nullable;

import com.kanomiya.picket.render.texture.Texture;
import com.kanomiya.picket.render.texture.TextureRenderInfo;
import com.kanomiya.picket.util.IDataSerializer;

public class Tile
{
    private final String id;
    @Nullable
    public final Texture texture;
    public final TextureRenderInfo renderInfo;

    public Tile(String id, @Nullable Texture texture)
    {
        this.id = id;
        this.texture = texture;
        this.renderInfo = new TextureRenderInfo();
    }

    public String id()
    {
        return id;
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
                textureRegistry.put(textureId, new Texture(textureId));
            }

            Texture texture = textureRegistry.get(textureId);


            return new Tile(id, texture);
        }

    }

}
