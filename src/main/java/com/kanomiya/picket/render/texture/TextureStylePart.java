package com.kanomiya.picket.render.texture;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Map;

import javax.annotation.Nullable;

import com.kanomiya.picket.util.IDataSerializer;

public class TextureStylePart
{
    public final String imageId;
    public final Point sourcePos;
    public final Dimension sourceSize;
    public final double rotate;

    public TextureStylePart(String imageId)
    {
        this(imageId, null, null, 0d);
    }

    public TextureStylePart(String imageId, @Nullable Point sourcePos, @Nullable Dimension sourceSize, double rotate)
    {
        this.imageId = imageId;
        this.sourcePos = sourcePos;
        this.sourceSize = sourceSize;
        this.rotate = rotate;
    }



    public TextureStylePart merge(TextureStylePart other)
    {
        String imageId = other.imageId == null ? this.imageId : other.imageId;

        Point sourcePos = this.sourcePos;
        if (other.sourcePos != null)
        {
            sourcePos = sourcePos == null ? other.sourcePos : new Point(sourcePos.x +other.sourcePos.x, sourcePos.y +other.sourcePos.y);
        }

        Dimension sourceSize = other.sourceSize == null ? this.sourceSize : other.sourceSize;

        double rotate = this.rotate + other.rotate;

        return new TextureStylePart(
                imageId,
                sourcePos,
                sourceSize,
                rotate
                );
    }



    @Override
    public String toString()
    {
        return "TextureStylePart [imageId=" + imageId + ", sourcePos=" + sourcePos + ", sourceSize=" + sourceSize
                + ", rotate=" + rotate + "]";
    }




    public static class DataSerializerTextureStylePart implements IDataSerializer<TextureStylePart>
    {
        public DataSerializerTextureStylePart()
        {

        }

        @Override
        public Map<String, Object> serialize(TextureStylePart layer)
        {

            return null;
        }

        @Override
        public TextureStylePart deserialize(Map<String, Object> map)
        {
            String imageId = (String) map.get("image");
            Point sourcePos = null;
            if (map.containsKey("sourceX") && map.containsKey("sourceY"))
            {
                sourcePos = new Point((int) map.get("sourceX"), (int) map.get("sourceY"));
            } else if (map.containsKey("sourceX"))
            {
                sourcePos = new Point((int) map.get("sourceX"), 0);
            } else if (map.containsKey("sourceY"))
            {
                sourcePos = new Point(0, (int) map.get("sourceY"));
            }

            Dimension sourceSize = null;
            if (map.containsKey("sourceSize"))
            {
                sourceSize = new Dimension((int) map.get("sourceSize"), (int) map.get("sourceSize"));
            } else if (map.containsKey("sourceWidth") && map.containsKey("sourceHeight"))
            {
                sourceSize = new Dimension((int) map.get("sourceWidth"), (int) map.get("sourceHeight"));
            }

            double rotate = (double) map.getOrDefault("rotate", 0d);



            return new TextureStylePart( imageId, sourcePos, sourceSize, rotate );
        }

    }

}
