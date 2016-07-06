package com.kanomiya.picket.world.tile;

import java.util.Map;

import javax.annotation.Nullable;

import com.kanomiya.picket.data.IDataSerializer;

public class Tile
{
    private final String id;
    private final String texture;

    public Tile(String id, @Nullable String texture)
    {
        this.id = id;
        this.texture = texture;
    }

    public String id()
    {
        return id;
    }

    @Nullable
    public String texture()
    {
        return texture;
    }


    public static class DataSerializerTile implements IDataSerializer<Tile>
    {

        @Override
        public Map<String, Object> serialize(Tile tile)
        {

            return null;
        }

        @Override
        public Tile deserialize(Map<String, Object> map)
        {
            String id = (String) map.get("id");

            String texture = (String) map.getOrDefault("texture", "tiles/" + id);

            return new Tile(id, texture);
        }

    }
}
