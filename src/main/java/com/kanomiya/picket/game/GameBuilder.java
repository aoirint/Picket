package com.kanomiya.picket.game;

import static com.kanomiya.picket.util.FileUtil.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Predicate;

import org.yaml.snakeyaml.Yaml;

import com.google.common.collect.Maps;
import com.kanomiya.picket.App;
import com.kanomiya.picket.game.GameRegistry.DataSerializerGameRegistry;
import com.kanomiya.picket.world.World;
import com.kanomiya.picket.world.World.DataSerializerWorld;


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
        Map<String, Object> registryMap = buildRegistryData();
        GameRegistry registry = new DataSerializerGameRegistry().deserialize(registryMap);

        App.logger.info("Loaded " + registry.imageRegistry.size() + " images");
        App.logger.info("Loaded " + registry.textureRegistry.size() + " textures");
        App.logger.info("Loaded " + registry.tileRegistry.size() + " tiles");

        Map<String, Object> worldMap = buildWorldData();
        World world = new DataSerializerWorld(registry).deserialize(worldMap);


        App.logger.info("Loaded " + world.maps().size() + " maps");

        @SuppressWarnings("unchecked")
        Map<String, Object> gameData = yaml.loadAs(reader(file(path, "game.yaml")), Map.class);

        @SuppressWarnings("unchecked")
        Map<String, Object> globalRecords = (Map<String, Object>) gameData.get("records");
        if (globalRecords == null) globalRecords = Maps.newHashMap();

        return new Game(info, world, registry, globalRecords);

    }


    private GameInfo buildInfo() throws FileNotFoundException
    {
        File infoFile = file(path, "info.yaml");

        @SuppressWarnings("unchecked")
        Map<String, Object> infoData = yaml.loadAs(reader(infoFile), Map.class);

        String name = (String) infoData.getOrDefault("name", "Unnamed");
        String description = (String) infoData.getOrDefault("description", "");
        String version = (String) infoData.getOrDefault("version", "");
        String author = (String) infoData.getOrDefault("author", "");
        String url = (String) infoData.getOrDefault("url", "");

        return new GameInfo(name, description, version, author, url);
    }

    private Map<String, Object> buildRegistryData()
    {
        final File registryDir = file(path, "registry");
        final Map<String, Object> pool = Maps.newHashMap();

        final File registryFile = file(registryDir, "registry.yaml");

        if (registryFile.exists())
        {
            try
            {
                @SuppressWarnings("unchecked")
                Map<String, Object> registryData = yaml.loadAs(reader(registryFile), Map.class);

                pool.putAll(registryData);

            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }

        }


        final File imgDir = file(registryDir, "images");
        final File textureDir = file(registryDir, "textures");
        final File tileDir = file(registryDir, "tiles");

        if (imgDir.exists())
        {
            final Path imgDirPath = path(imgDir);
            Map<String, String> imageMap = Maps.newHashMap();

            try
            {
                walk(imgDirPath).filter(imgPredicate).forEach((path) ->
                {
                    String id = splitExtension(str(imgDirPath.relativize(path)));
                    File file = file(path);

                    imageMap.put(id, file.getAbsolutePath());
                });

            } catch (IOException e)
            {
                e.printStackTrace();
            }

            pool.put("images", imageMap);
        }

        if (textureDir.exists())
        {
            final Path textureDirPath = path(textureDir);
            Map<String, Map<String, Object>> textureMap = Maps.newHashMap();

            try
            {
                walk(textureDirPath).filter(yamlPredicate).forEach((path) ->
                {
                    String id = splitExtension(str(textureDirPath.relativize(path)));
                    File file = file(path);

                    try
                    {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> textureData = yaml.loadAs(reader(file), Map.class);
                        textureData.put("id", id);

                        textureMap.put(id, textureData);

                    } catch (FileNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                });

            } catch (IOException e)
            {
                e.printStackTrace();
            }

            pool.put("textures", textureMap);
        }

        if (tileDir.exists())
        {
            Map<String, Map<String, Object>> tileMap = Maps.newHashMap();

            try
            {
                walk(path(tileDir), 1).filter(yamlPredicate).forEach((path) ->
                {
                    String id = splitExtension(name(path));
                    File file = file(path);

                    try
                    {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> tileData = yaml.loadAs(reader(file), Map.class);
                        tileData.put("id", id);

                        tileMap.put(id, tileData);

                    } catch (FileNotFoundException e)
                    {
                        e.printStackTrace();
                    }

                });

            } catch (IOException e)
            {
                e.printStackTrace();
            }

            pool.put("tiles", tileMap);
        }



        return pool;
    }



    private Map<String, Object> buildWorldData()
    {
        final File worldDir = file(path, "world");
        final Map<String, Object> pool = Maps.newHashMap();



        final File worldFile = file(worldDir, "world.yaml");

        if (worldFile.exists())
        {
            try
            {
                @SuppressWarnings("unchecked")
                Map<String, Object> worldData = yaml.loadAs(reader(worldFile), Map.class);
                pool.putAll(worldData);

            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }


        final File mapDir = file(worldDir, "maps");

        if (mapDir.exists())
        {
            try
            {
                Map<String, Object> maps = Maps.newHashMap();

                walk(path(mapDir), 1).filter(yamlPredicate).forEach((path) ->
                {
                    String id = splitExtension(name(path));
                    File file = file(path);

                    try
                    {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> mapData = yaml.loadAs(reader(file), Map.class);
                        maps.put(id, mapData);

                    } catch (FileNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                });

                pool.put("maps", maps);

            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }


        final File eventDir = file(worldDir, "events");

        if (eventDir.exists())
        {
            try
            {
                Map<String, Object> events = Maps.newHashMap();

                walk(path(eventDir), 1).filter(yamlPredicate).forEach((path) ->
                {
                    String id = splitExtension(name(path));
                    File file = file(path);

                    try
                    {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> eventData = yaml.loadAs(reader(file), Map.class);
                        events.put(id, eventData);

                    } catch (FileNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                });

                pool.put("events", events);

            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return pool;
    }



}
