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
import com.kanomiya.picket.game.GameInfo.DataSerializerGameInfo;
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
        Map<String, Object> gameMap = buildGameData();

        @SuppressWarnings("unchecked")
        GameInfo info = new DataSerializerGameInfo().deserialize((Map<String, Object>) gameMap.get("info"));
        @SuppressWarnings("unchecked")
        GameRegistry registry = new DataSerializerGameRegistry().deserialize((Map<String, Object>) gameMap.get("registry"));

        App.logger.info("Loaded " + registry.imageRegistry.size() + " images");
        App.logger.info("Loaded " + registry.textureRegistry.size() + " textures");
        App.logger.info("Loaded " + registry.tileRegistry.size() + " tiles");

        @SuppressWarnings("unchecked")
        World world = new DataSerializerWorld(registry).deserialize((Map<String, Object>) gameMap.get("world"));


        App.logger.info("Loaded " + world.mapRegistry.size() + " maps");


        @SuppressWarnings("unchecked")
        Map<String, Object> globalRecords = (Map<String, Object>) gameMap.get("records");
        if (globalRecords == null) globalRecords = Maps.newHashMap();

        return new Game(info, world, registry, globalRecords);

    }




    private static <K1, K2, V2> void putIfAbsentElseMerge(Map<K1, Object> map, K1 key, Map<K2, V2> newValue)
    {
        if (map.containsKey(key))
        {
            if (map.get(key) instanceof Map)
            {
                @SuppressWarnings("unchecked")
                Map<K2, V2> oldValue = (Map<K2, V2>) map.get(key);

                oldValue.putAll(newValue);

            } else throw new IllegalArgumentException("Map#" + key + " should be a Map or null");
        } else map.put(key, newValue);
    }





    private Map<String, Object> buildGameData() throws FileNotFoundException
    {
        @SuppressWarnings("unchecked")
        Map<String, Object> gameMap = yaml.loadAs(reader(file(path, "game.yaml")), Map.class);

        putIfAbsentElseMerge(gameMap, "info", buildGameInfoData());
        putIfAbsentElseMerge(gameMap, "registry", buildRegistryData());
        putIfAbsentElseMerge(gameMap, "world", buildWorldData());

        return gameMap;
    }

    private Map<String, Object> buildGameInfoData() throws FileNotFoundException
    {
        File infoFile = file(path, "info.yaml");

        @SuppressWarnings("unchecked")
        Map<String, Object> infoData = yaml.loadAs(reader(infoFile), Map.class);

        return infoData;
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

            @SuppressWarnings("unchecked")
            final Map<String, String> imageMap = pool.containsKey("images") ? (Map<String, String>) pool.get("images") : Maps.newHashMap();

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

            @SuppressWarnings("unchecked")
            Map<String, Map<String, Object>> textureMap = pool.containsKey("textures") ? (Map<String, Map<String, Object>>) pool.get("textures") : Maps.newHashMap();

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
            @SuppressWarnings("unchecked")
            Map<String, Map<String, Object>> tileMap = pool.containsKey("tiles") ? (Map<String, Map<String, Object>>) pool.get("tiles") : Maps.newHashMap();

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
                @SuppressWarnings("unchecked")
                Map<String, Map<String, Object>> maps = pool.containsKey("maps") ? (Map<String, Map<String, Object>>) pool.get("maps") : Maps.newHashMap();

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
                @SuppressWarnings("unchecked")
                Map<String, Map<String, Object>> events = pool.containsKey("events") ? (Map<String, Map<String, Object>>) pool.get("events") : Maps.newHashMap();

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
