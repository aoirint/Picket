package com.kanomiya.picket.world;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.google.common.collect.Maps;
import com.kanomiya.picket.game.GameRegistry;
import com.kanomiya.picket.util.IDataSerializer;
import com.kanomiya.picket.world.FieldMap.DataSerializerFieldMap;
import com.kanomiya.picket.world.IngameEvent.DataSerializerIngameEvent;

public class World
{
    protected Map<String, FieldMap> mapRegistry;
    protected Map<String, IngameEvent> worldEventRegistry;
    protected Map<String, Object> worldRecords;

    public World(Map<String, FieldMap> mapRegistry, Map<String, IngameEvent> worldEventRegistry, Map<String, Object> worldRecords)
    {
        this.mapRegistry = mapRegistry;
        this.worldEventRegistry = worldEventRegistry;
        this.worldRecords = worldRecords;
    }

    public FieldMap getMap(String id)
    {
        return mapRegistry.get(id);
    }

    public Map<String, FieldMap> maps()
    {
        return mapRegistry;
    }

    public IngameEvent worldEvent(String id)
    {
        return worldEventRegistry.get(id);
    }

    public Map<String, IngameEvent> worldEvents()
    {
        return worldEventRegistry;
    }

    public Map<String, Object> worldRecords()
    {
        return worldRecords;
    }


    @Override
    public String toString()
    {
        final int maxLen = 10;
        return "World [mapRegistry=" + (mapRegistry != null ? toString(mapRegistry.entrySet(), maxLen) : null)
                + ", globalEventRegistry="
                + (worldEventRegistry != null ? toString(worldEventRegistry.entrySet(), maxLen) : null)
                + ", worldRecords=" + (worldRecords != null ? toString(worldRecords.entrySet(), maxLen) : null) + "]";
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




    public static class DataSerializerWorld implements IDataSerializer<World>
    {
        private final DataSerializerFieldMap mapSerializer;

        public DataSerializerWorld(GameRegistry registry)
        {
            this.mapSerializer = new DataSerializerFieldMap(registry);
        }

        @Override
        public Map<String, Object> serialize(World world)
        {

            return null;
        }

        @Override
        public World deserialize(Map<String, Object> map)
        {
            final Map<String, FieldMap> mapRegistry = Maps.newHashMap();
            final Map<String, IngameEvent> worldEventRegistry = Maps.newHashMap();

            @SuppressWarnings("unchecked")
            Map<String, Map<String, Object>> mapMap = (Map<String, Map<String, Object>>) map.get("maps");

            mapMap.forEach((id, mapData) ->
            {
                mapData.put("id", id);
                mapRegistry.put(id, mapSerializer.deserialize(mapData));
            });

            DataSerializerIngameEvent eventSerializer = new DataSerializerIngameEvent(mapRegistry);

            @SuppressWarnings("unchecked")
            Map<String, Map<String, Object>> eventMap = (Map<String, Map<String, Object>>) map.get("events");

            eventMap.forEach((id, eventData) ->
            {
                eventData.put("id", id);
                worldEventRegistry.put(id, eventSerializer.deserialize(eventData));
            });

            @SuppressWarnings("unchecked")
            Map<String, Object> worldRecords = (Map<String, Object>) map.get("records");
            if (worldRecords == null) worldRecords = Maps.newHashMap();

            return new World(mapRegistry, worldEventRegistry, worldRecords);
        }

    }

}
