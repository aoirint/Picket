package com.kanomiya.picket.render;

public class TextureLayer
{
    public final String imageId;
    public final double rotate;

    public TextureLayer(String imageId, double rotate)
    {
        this.imageId = imageId;
        this.rotate = rotate;
    }

    @Override
    public String toString()
    {
        return "TextureLayer [imageId=" + imageId + ", rotate=" + rotate + "]";
    }

}
