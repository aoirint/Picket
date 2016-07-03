package com.kanomiya.picket.world;

import javax.annotation.Nullable;

public class Player
{
    public FieldMap map;
    public int x, y;

    private String texture;

    public Player(FieldMap map, int x, int y, @Nullable String texture)
    {
        this.map = map;
        this.x = x;
        this.y = y;
        this.texture = texture;
    }

    @Nullable
    public String texture()
    {
        return texture;
    }

}
