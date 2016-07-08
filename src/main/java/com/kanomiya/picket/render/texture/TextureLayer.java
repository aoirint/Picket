package com.kanomiya.picket.render.texture;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Map;

import javax.annotation.Nullable;

import com.kanomiya.picket.util.IDataSerializer;

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




    public static class DataSerializerTextureLayer implements IDataSerializer<TextureLayer>
    {
        public DataSerializerTextureLayer()
        {

        }

        @Override
        public Map<String, Object> serialize(TextureLayer layer)
        {

            return null;
        }

        @Override
        public TextureLayer deserialize(Map<String, Object> map)
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



            return new TextureLayer(imageId, sourcePos, sourceSize, rotate);
        }

    }

}
