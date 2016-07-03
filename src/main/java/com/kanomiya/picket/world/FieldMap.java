package com.kanomiya.picket.world;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

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

    public FieldMap(String id, int width, int height, @Nullable String background, Tile[][] tiles, FieldType[][] fieldTypes)
    {
        this.id = id;
        this.width = width;
        this.height = height;
        this.background = background;
        this.tiles = tiles;
        this.fieldTypes = fieldTypes;
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
        return tiles[x][y];
    }

    public FieldType fieldTypeAt(int x, int y)
    {
        return fieldTypes[x][y];
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

}
