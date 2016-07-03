package com.kanomiya.picket.game;

import static com.kanomiya.picket.game.FileUtil.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import javax.imageio.ImageIO;

import org.yaml.snakeyaml.Yaml;

import com.google.common.collect.Maps;
import com.kanomiya.picket.App;
import com.kanomiya.picket.render.Texture;
import com.kanomiya.picket.render.TextureLayer;
import com.kanomiya.picket.tile.Tile;
import com.kanomiya.picket.world.FieldMap;
import com.kanomiya.picket.world.FieldType;
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
        GameRegistry registry = buildRegistry(info);

        App.logger.info("Loaded " + registry.imageRegistry.size() + " images");
        App.logger.info("Loaded " + registry.textureRegistry.size() + " textures");
        App.logger.info("Loaded " + registry.tileRegistry.size() + " tiles");

        World world = buildWorld(info, registry);

        App.logger.info("Loaded " + world.maps().size() + " maps");

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

    private GameRegistry buildRegistry(GameInfo info)
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
                imageRegistry = Maps.newHashMap();
                textureRegistry = Maps.newHashMap();
                tileRegistry = Maps.newHashMap();

                try
                {
                    imageRegistry.put("missing", ImageIO.read(ClassLoader.getSystemResourceAsStream("missing.png")));
                } catch (IOException e)
                {
                    e.printStackTrace();
                }

                textureRegistry.put("missing", new Texture("missing", new TextureLayer("missing", 0d)));
                tileRegistry.put("null", new Tile("null", "missing"));

                try
                {
                    File imgDir = file(registryDir, "images");
                    File textureDir = file(registryDir, "textures");
                    File tileDir = file(registryDir, "tiles");

                    if (imgDir.exists())
                    {
                        Path imgDirPath = path(imgDir);

                        walk(imgDirPath).filter(imgPredicate).forEach((path) ->
                        {
                            String id = splitExtension(str(imgDirPath.relativize(path)));
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
                        Path textureDirPath = path(textureDir);

                        walk(textureDirPath).filter(yamlPredicate).forEach((path) ->
                        {
                            String id = splitExtension(str(textureDirPath.relativize(path)));
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
                            String id = splitExtension(name(path));
                            File file = file(path);

                            try
                            {
                                @SuppressWarnings("unchecked")
                                Map<String, Object> tileData = yaml.loadAs(reader(file), Map.class);

                                String texture = (String) tileData.getOrDefault("texture", "tiles/" + id);


                                if (! textureRegistry.containsKey(texture) && imageRegistry.containsKey(texture))
                                {
                                    textureRegistry.put(texture, new Texture(texture, new TextureLayer(texture, 0d)));
                                }


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



    private World buildWorld(GameInfo info, GameRegistry registry)
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
                mapRegistry = Maps.newHashMap();

                try
                {
                    final File mapDir = file(worldDir, "maps");

                    if (mapDir.exists())
                    {
                        walk(path(mapDir), 1).filter(yamlPredicate).forEach((path) ->
                        {
                            String id = splitExtension(name(path));
                            File file = file(path);


                            try
                            {
                                @SuppressWarnings("unchecked")
                                Map<String, Object> mapData = yaml.loadAs(reader(file), Map.class);

                                int width = (int) mapData.get("width");
                                int height = (int) mapData.get("height");

                                @SuppressWarnings("unchecked")
                                List<List<String>> tileData = (List<List<String>>) mapData.get("tiles");
                                Objects.requireNonNull(tileData, "Not found 'tiles' at map '" + id + "'");

                                @SuppressWarnings("unchecked")
                                List<List<String>> fieldTypeData = (List<List<String>>) mapData.get("fieldTypes");
                                Objects.requireNonNull(fieldTypeData, "Not found 'fieldTypes' at map '" + id + "'");

                                Tile[][] tiles = new Tile[width][height];
                                FieldType[][] fieldTypes = new FieldType[width][height];

                                Tile nullTile = registry.tileRegistry.get("null");

                                for (int y=0, realH=tileData.size(); y<height; y++)
                                {
                                    List<String> line = (y < realH) ? tileData.get(y) : null;
                                    int realW = line != null ? line.size() : 0;

                                    for (int x=0; x<width; x++)
                                    {
                                        String tileId = x < realW && line != null ? line.get(x) : null;
                                        Tile tile = tileId != null ? registry.tileRegistry.getOrDefault(tileId, nullTile) : nullTile;

                                        tiles[x][y] = tile;
                                    }
                                }

                                for (int y=0, realH=fieldTypeData.size(); y<height; y++)
                                {
                                    List<String> line = (y < realH) ? fieldTypeData.get(y) : null;
                                    int realW = line != null ? line.size() : 0;

                                    for (int x=0; x<width; x++)
                                    {
                                        String fieldTypeId = x < realW && line != null ? line.get(x) : null;
                                        FieldType fieldType = FieldType.NORMAL;

                                        if (fieldTypeId != null)
                                        {
                                            switch (fieldTypeId)
                                            {
                                            case "none":
                                            case "o":
                                                fieldType = FieldType.NORMAL;
                                                break;
                                            case "block":
                                            case "x":
                                                fieldType = FieldType.BLOCK;
                                                break;
                                            case "horizontal":
                                            case "h":
                                                fieldType = FieldType.HORIZONTAL_BLOCK;
                                                break;
                                            case "vertical":
                                            case "v":
                                                fieldType = FieldType.VERTICAL_BLOCK;
                                                break;
                                            }
                                        }

                                        fieldTypes[x][y] = fieldType;
                                    }
                                }


                                mapRegistry.put(id, new FieldMap(id, width, height, tiles, fieldTypes));

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



}
