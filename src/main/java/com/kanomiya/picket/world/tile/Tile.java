package com.kanomiya.picket.world.tile;

import javax.annotation.Nullable;

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



}
