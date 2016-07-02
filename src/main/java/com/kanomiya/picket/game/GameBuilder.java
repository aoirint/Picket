package com.kanomiya.picket.game;

import static com.kanomiya.picket.game.FileUtil.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javax.imageio.ImageIO;

import org.yaml.snakeyaml.Yaml;

import com.google.common.collect.Maps;
import com.kanomiya.picket.render.Texture;
import com.kanomiya.picket.render.TextureLayer;
import com.kanomiya.picket.tile.Tile;
import com.kanomiya.picket.world.World;


public class GameBuilder
{
    Yaml yaml;
    String path;

    private static final Predicate<Path> commentPredicate = (path) -> path.toString().startsWith("_");
    private static final Predicate<Path> yamlPredicate = (path) -> ! commentPredicate.test(path) && path.toString().endsWith(".yaml");

    private static final Predicate<Path> imgPredicate = (path) -> ! commentPredicate.test(path) && path.toString().endsWith(".png");

    public GameBuilder(String path)
    {
        yaml = new Yaml();

        this.path = path;
    }

    public Game build() throws FileNotFoundException
    {
        GameInfo info = buildInfo();
        GameRegistry registry = buildRegistry();
        World world = buildWorld();

        return new Game(info, world, registry);

    }


    private GameInfo buildInfo() throws FileNotFoundException
    {
        File infoFile = file(path, "game.yaml");

        @SuppressWarnings("unchecked")
        Map<String, Object> infoData = yaml.loadAs(reader(infoFile), Map.class);

        String name = (String) infoData.getOrDefault("name", "Unnamed");
        String description = (String) infoData.getOrDefault("description", "");
        String version = (String) infoData.getOrDefault("version", "");
        String author = (String) infoData.getOrDefault("author", "");
        String url = (String) infoData.getOrDefault("url", "");

        return new GameInfo(name, description, version, author, url);
    }

    private GameRegistry buildRegistry()
    {
        final File registryDir = file(path, "registry");
        /*
        final File registryFile = file(registryDir, "registry.yaml");

        try
        {
            Map registryData = yaml.loadAs(reader(registryFile), Map.class);

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        */

        return new GameRegistry()
        {
            {
                tileRegistry = Maps.newHashMap();
                imageRegistry = Maps.newHashMap();
                textureRegistry = Maps.newHashMap();

                try
                {
                    File imgDir = file(registryDir, "images");
                    File textureDir = file(registryDir, "textures");
                    File tileDir = file(registryDir, "tiles");

                    if (imgDir.exists())
                    {
                        walk(path(imgDir)).filter(imgPredicate).forEach((path) ->
                        {
                            String id = path.getFileName().toString();
                            File file = file(path);

                            try
                            {
                                imageRegistry.put(id, ImageIO.read(file));

                            } catch (IOException e)
                            {
                                e.printStackTrace();
                            }

                        });
                    }

                    if (textureDir.exists())
                    {
                        walk(path(textureDir)).filter(yamlPredicate).forEach((path) ->
                        {
                            String id = path.getFileName().toString();
                            File file = file(path);

                            try
                            {
                                @SuppressWarnings("unchecked")
                                Map<String, Object> textureData = yaml.loadAs(reader(file), Map.class);

                                @SuppressWarnings("unchecked")
                                List<Map<String, Object>> layerDataList = (List<Map<String, Object>>) textureData.get("layers");
                                TextureLayer[] layers = new TextureLayer[layerDataList.size()];

                                for (int i=0, len=layerDataList.size(); i<len; i++)
                                {
                                    Map<String, Object> layerData = layerDataList.get(i);

                                    String imageId = (String) layerData.get("image");
                                    double rotate = (double) layerData.getOrDefault("rotate", 0d);

                                    layers[i] = new TextureLayer(imageId, rotate);
                                }

                                textureRegistry.put(id, new Texture(id, layers));

                            } catch (FileNotFoundException e)
                            {
                                e.printStackTrace();
                            }

                        });
                    }

                    if (tileDir.exists())
                    {
                        walk(path(tileDir), 1).filter(yamlPredicate).forEach((path) ->
                        {
                            String id = path.getFileName().toString();
                            File file = file(path);

                            try
                            {
                                @SuppressWarnings("unchecked")
                                Map<String, Object> tileData = yaml.loadAs(reader(file), Map.class);

                                String texture = (String) tileData.getOrDefault("texture", "tiles/" + id);

                                tileRegistry.put(id, new Tile(id, texture));

                            } catch (FileNotFoundException e)
                            {
                                e.printStackTrace();
                            }

                        });
                    }

                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        };
    }



    private World buildWorld()
    {
        final File worldDir = file(path, "world");
        /*
        final File worldFile = file(worldDir, "world.yaml");

        try
        {
            Map worldData = yaml.loadAs(reader(worldFile), Map.class);

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        */


        return new World()
        {
            {
                try
                {
                    final File mapDir = file(worldDir, "maps");

                    if (mapDir.exists())
                    {
                        walk(path(mapDir), 1).filter(yamlPredicate).forEach((path) ->
                        {

                        });
                    }

                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        };
    }



}
