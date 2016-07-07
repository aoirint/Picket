package com.kanomiya.picket.game;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kanomiya.picket.render.texture.Texture;
import com.kanomiya.picket.render.texture.TextureLayer;
import com.kanomiya.picket.render.texture.animation.AnimationFrame;
import com.kanomiya.picket.util.IDataSerializer;
import com.kanomiya.picket.world.Tile;
import com.kanomiya.picket.world.Tile.DataSerializerTile;

public class GameRegistry
{
    @Nonnull
    public final Map<String, BufferedImage> imageRegistry;
    @Nonnull
    public final Map<String, Texture> textureRegistry;
    @Nonnull
    public final Map<String, Tile> tileRegistry;

    public GameRegistry(@Nonnull Map<String, BufferedImage> imageRegistry, @Nonnull Map<String, Texture> textureRegistry, @Nonnull Map<String, Tile> tileRegistry)
    {
        this.imageRegistry = imageRegistry;
        this.textureRegistry = textureRegistry;
        this.tileRegistry = tileRegistry;
    }




    public BufferedImage image(String id)
    {
        return imageRegistry.getOrDefault(id, imageRegistry.get("missing"));
    }

    public Texture texture(String id)
    {
        return textureRegistry.getOrDefault(id, textureRegistry.get("missing"));
    }

    public Tile tile(String id)
    {
        return tileRegistry.getOrDefault(id, tileRegistry.get("null"));
    }

    @Override
    public String toString()
    {
        final int maxLen = 10;
        return "GameRegistry [imageRegistry="
                + (imageRegistry != null ? toString(imageRegistry.entrySet(), maxLen) : null) + ", textureRegistry="
                + (textureRegistry != null ? toString(textureRegistry.entrySet(), maxLen) : null) + ", tileRegistry="
                + (tileRegistry != null ? toString(tileRegistry.entrySet(), maxLen) : null) + "]";
    }

    private String toString(Collection<?> collection, int maxLen)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        int i = 0;
        for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++)
        {
            if (i > 0) builder.append(", ");
            builder.append(iterator.next());
        }
        builder.append("]");
        return builder.toString();
    }



    public static class DataSerializerGameRegistry implements IDataSerializer<GameRegistry>
    {
        public DataSerializerGameRegistry()
        {

        }

        @Override
        public Map<String, Object> serialize(GameRegistry registry)
        {

            return null;
        }

        @Override
        public GameRegistry deserialize(Map<String, Object> map)
        {
            final Map<String, BufferedImage> imageRegistry = Maps.newHashMap();
            final Map<String, Texture> textureRegistry = Maps.newHashMap();
            final Map<String, Tile> tileRegistry = Maps.newHashMap();

            try
            {
                imageRegistry.put("missing", ImageIO.read(ClassLoader.getSystemResourceAsStream("missing.png")));
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            textureRegistry.put("missing", new Texture("missing", false, new TextureLayer("missing", null, null, 0d)));
            tileRegistry.put("null", new Tile("null", null));



            @SuppressWarnings("unchecked")
            Map<String, Object> imageMap = (Map<String, Object>) map.get("images");
            if (imageMap != null)
            {
                imageMap.forEach((id, path) ->
                {
                    try
                    {
                        imageRegistry.put(id, ImageIO.read(new File((String) path)));

                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                });
            }

            @SuppressWarnings("unchecked")
            Map<String, Map<String, Object>> textureMap = (Map<String, Map<String, Object>>) map.get("textures");
            if (textureMap != null)
            {
                textureMap.forEach((id, textureData) ->
                {
                    @SuppressWarnings("unchecked")
                    Map<String, List<Map<String, Object>>> variantDataList = (Map<String, List<Map<String, Object>>>) textureData.get("variants");
                    Map<String, List<TextureLayer>> variants = Maps.newHashMap();

                    boolean enableDirection = (boolean) textureData.getOrDefault("enableDirection", false);

                    variantDataList.forEach((variantId, variantData) ->
                    {
                        List<TextureLayer> layers = Lists.newArrayList();

                        variantData.forEach(layerData ->
                        {
                            String imageId = (String) layerData.get("image");
                            Point sourcePos = null;
                            if (layerData.containsKey("sourceX") && layerData.containsKey("sourceY"))
                            {
                                sourcePos = new Point((int) layerData.get("sourceX"), (int) layerData.get("sourceY"));
                            } else if (layerData.containsKey("sourceX"))
                            {
                                sourcePos = new Point((int) layerData.get("sourceX"), 0);
                            } else if (layerData.containsKey("sourceY"))
                            {
                                sourcePos = new Point(0, (int) layerData.get("sourceY"));
                            }

                            Dimension sourceSize = null;
                            if (layerData.containsKey("sourceSize"))
                            {
                                sourceSize = new Dimension((int) layerData.get("sourceSize"), (int) layerData.get("sourceSize"));
                            } else if (layerData.containsKey("sourceWidth") && layerData.containsKey("sourceHeight"))
                            {
                                sourceSize = new Dimension((int) layerData.get("sourceWidth"), (int) layerData.get("sourceHeight"));
                            }

                            double rotate = (double) layerData.getOrDefault("rotate", 0d);

                            layers.add(new TextureLayer(imageId, sourcePos, sourceSize, rotate));
                        });

                        variants.put(variantId, layers);
                    });

                    List<AnimationFrame> animation;
                    if (textureData.containsKey("animation"))
                    {
                        @SuppressWarnings("unchecked")
                        List<Map<String, Object>> animationDataList = (List<Map<String, Object>>) textureData.get("animation");

                        animation = Lists.newArrayList();

                        animationDataList.forEach(animationData ->
                        {
                            String variantId = (String) animationData.get("variant");
                            int delay = (int) animationData.get("delay");

                            animation.add(new AnimationFrame(variantId, delay));
                        });

                    } else animation = null;


                    textureRegistry.put(id, new Texture(id, enableDirection, variants, animation));
                });
            }

            imageRegistry.forEach((id, image) ->
            {
                if (! textureRegistry.containsKey(id))
                {
                    textureRegistry.put(id, new Texture(id, false, new TextureLayer(id, null, null, 0d)));
                }
            });


            @SuppressWarnings("unchecked")
            Map<String, Map<String, Object>> tileMap = (Map<String, Map<String, Object>>) map.get("tiles");
            if (tileMap != null)
            {
                DataSerializerTile tileSerializer = new DataSerializerTile(imageRegistry, textureRegistry);

                tileMap.forEach((id, tileData) ->
                {
                    Tile tile = tileSerializer.deserialize(tileData);

                    tileRegistry.put(id, tile);

                });
            }


            return new GameRegistry(imageRegistry, textureRegistry, tileRegistry);
        }

    }


}
