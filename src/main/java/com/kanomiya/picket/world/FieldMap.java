package com.kanomiya.picket.world;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;
import com.kanomiya.picket.data.IDataSerializer;
import com.kanomiya.picket.game.GameRegistry;
import com.kanomiya.picket.world.event.IngameEvent;
import com.kanomiya.picket.world.tile.Tile;

public class FieldMap
{
    private final String id;
    private int width, height;
    private Tile[][] tiles;
    private FieldType[][] fieldTypes;
    private List<IngameEvent> events;
    private String background;
    private Map<String, Object> mapRecords;

    public FieldMap(String id, int width, int height, @Nullable String background, Tile[][] tiles, FieldType[][] fieldTypes, Map<String, Object> mapRecords)
    {
        this.id = id;
        this.width = width;
        this.height = height;
        this.background = background;
        this.tiles = tiles;
        this.fieldTypes = fieldTypes;
        this.mapRecords = mapRecords;
    }

    public String id()
    {
        return id;
    }

    public int width()
    {
        return width;
    }

    public int height()
    {
        return height;
    }

    @Nullable
    public String background()
    {
        return background;
    }

    public Tile tileAt(int x, int y)
    {
        if (0 <= x && x < tiles.length && 0 <= y && y < tiles[x].length) return tiles[x][y];
        return null;
    }

    public FieldType fieldTypeAt(int x, int y)
    {
        if (0 <= x && x < fieldTypes.length && 0 <= y && y < fieldTypes[x].length) return fieldTypes[x][y];
        return null;
    }

    public Map<String, Object> mapRecords()
    {
        return mapRecords;
    }

    @Override
    public String toString()
    {
        final int maxLen = 10;
        return "FieldMap [id=" + id + ", width=" + width + ", height=" + height + ", tiles="
                + (tiles != null ? Arrays.asList(tiles).subList(0, Math.min(tiles.length, maxLen)) : null)
                + ", fieldTypes=" + (fieldTypes != null
                        ? Arrays.asList(fieldTypes).subList(0, Math.min(fieldTypes.length, maxLen)) : null)
                + "]";
    }




    public static class DataSerializerFieldMap implements IDataSerializer<FieldMap>
    {
        private final GameRegistry registry;

        public DataSerializerFieldMap(GameRegistry registry)
        {
            this.registry = registry;
        }

        @Override
        public Map<String, Object> serialize(FieldMap fieldMap)
        {

            return null;
        }

        @Override
        public FieldMap deserialize(Map<String, Object> map)
        {
            String id = (String) map.get("id");



            int width = (int) map.get("width");
            int height = (int) map.get("height");

            String background = (String) map.getOrDefault("background", null);

            @SuppressWarnings("unchecked")
            List<List<String>> tileData = (List<List<String>>) map.get("tiles");
            Objects.requireNonNull(tileData, "Not found 'tiles' at map '" + id + "'");

            @SuppressWarnings("unchecked")
            List<List<String>> fieldTypeData = (List<List<String>>) map.get("fieldTypes");
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
                    FieldType fieldType = FieldType.BLOCK;

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


            @SuppressWarnings("unchecked")
            Map<String, Object> mapRecords = (Map<String, Object>) map.get("records");
            if (mapRecords == null) mapRecords = Maps.newHashMap();

            return new FieldMap(id, width, height, background, tiles, fieldTypes, mapRecords);
        }

    }


}
