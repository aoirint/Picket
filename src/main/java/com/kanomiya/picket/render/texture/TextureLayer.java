package com.kanomiya.picket.render.texture;

import java.awt.Dimension;
import java.awt.Point;

import javax.annotation.Nullable;

public class TextureLayer
{
    public final String imageId;
    public final Point sourcePos;
    public final Dimension sourceSize;
    public final double rotate;

    public TextureLayer(String imageId, @Nullable Point sourcePos, @Nullable Dimension sourceSize, double rotate)
    {
        this.imageId = imageId;
        this.sourcePos = sourcePos;
        this.sourceSize = sourceSize;
        this.rotate = rotate;
    }

    @Override
    public String toString()
    {
        return "TextureLayer [imageId=" + imageId + ", rotate=" + rotate + "]";
    }

}
