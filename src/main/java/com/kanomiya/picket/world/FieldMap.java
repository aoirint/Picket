package com.kanomiya.picket.world;

import java.util.Arrays;

import com.kanomiya.picket.tile.Tile;

public class FieldMap
{
    private final String id;
    private int width, height;
    private Tile[][] tiles;
    private FieldType[][] fieldTypes;

    public FieldMap(String id, int width, int height, Tile[][] tiles, FieldType[][] fieldTypes)
    {
        this.id = id;
        this.width = width;
        this.height = height;
        this.tiles = tiles;
        this.fieldTypes = fieldTypes;
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
