package com.kanomiya.picket.render;

public class Texture
{
    private final String id;
    public final boolean enableDirection;

    private final TextureLayer[] layers;

    public Texture(String id, boolean enableDirection, TextureLayer... layers)
    {
        this.id = id;
        this.enableDirection = enableDirection;
        this.layers = layers;
    }

    public String id()
    {
        return id;
    }

    public TextureLayer[] layers()
    {
        return layers;
    }

}
