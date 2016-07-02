package com.kanomiya.picket.tile;

public class Tile
{
    private final String id;
    private final String texture;

    public Tile(String id, String texture)
    {
        this.id = id;
        this.texture = texture;
    }

    public String id()
    {
        return id;
    }

    public String texture()
    {
        return texture;
    }



}
