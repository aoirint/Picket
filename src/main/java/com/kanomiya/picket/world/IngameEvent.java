package com.kanomiya.picket.world;

import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;
import com.kanomiya.picket.util.IDataSerializer;

public class IngameEvent
{
    private final String id;
    public FieldMap map;
    public int x, y;

    private String texture;
    private String script;
    private Map<String, Object> eventRecords;

    public IngameEvent(String id, FieldMap map, int x, int y, @Nullable String texture, @Nullable String script, Map<String, Object> eventRecords)
    {
        this.id = id;
        this.map = map;
        this.x = x;
        this.y = y;

        this.texture = texture;
        this.script = script;
        this.eventRecords = eventRecords;
    }


    public String id()
    {
        return id;
    }

    @Nullable
    public String texture()
    {
        return texture;
    }

    @Nullable
    public String script()
    {
        return script;
    }

    public Map<String, Object> eventRecords()
    {
        return eventRecords;
    }

    public boolean move(int offsetX, int offsetY)
    {
        return move(offsetX, offsetY, false);
    }

    public boolean move(int offsetX, int offsetY, boolean force)
    {
        if (offsetX == 0 && offsetY == 0) return true;

        int i = x +offsetX;
        int j = y +offsetY;

        Direction to = Direction.fromOffset(offsetX, offsetY);

        if (map.tileAt(i, j) != null && map.fieldTypeAt(i, j).passableFrom(to.opposite()))
        {
            x = i;
            y = j;

            return true;
        }

        return false;
    }

    public boolean move(Direction dir)
    {
        return move(dir, false);
    }

    public boolean move(Direction dir, boolean force)
    {
        return move(dir.offsetX(), dir.offsetY(), force);
    }





    public static class DataSerializerIngameEvent implements IDataSerializer<IngameEvent>
    {
        Map<String, FieldMap> mapRegistry;

        public DataSerializerIngameEvent(Map<String, FieldMap> mapRegistry)
        {
            this.mapRegistry = mapRegistry;
        }

        @Override
        public Map<String, Object> serialize(IngameEvent event)
        {

            return null;
        }

        @Override
        public IngameEvent deserialize(Map<String, Object> map)
        {
            String id = (String) map.get("id");

            FieldMap fieldMap = mapRegistry.get(map.get("map"));
            int x = (int) map.get("x");
            int y = (int) map.get("y");
            String texture = (String) map.getOrDefault("texture", null);
            String script = (String) map.getOrDefault("script", null);

            @SuppressWarnings("unchecked")
            Map<String, Object> eventRecords = (Map<String, Object>) map.get("records");
            if (eventRecords == null) eventRecords = Maps.newHashMap();

            return new IngameEvent(id, fieldMap, x, y, texture, script, eventRecords);
        }

    }


}
