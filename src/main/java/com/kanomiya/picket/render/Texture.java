package com.kanomiya.picket.render;

public class Texture
{
    private final String id;
    private final TextureLayer[] layers;

    public Texture(String id, TextureLayer... layers)
    {
        this.id = id;
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
