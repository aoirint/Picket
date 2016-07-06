package com.kanomiya.picket.world;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.kanomiya.picket.world.event.IngameEvent;

public abstract class World
{
    protected Map<String, FieldMap> mapRegistry;
    protected Map<String, IngameEvent> globalEventRegistry;
    protected Map<String, Object> worldRecords;

    public FieldMap getMap(String id)
    {
        return mapRegistry.get(id);
    }

    public Map<String, FieldMap> maps()
    {
        return mapRegistry;
    }

    public IngameEvent globalEvent(String id)
    {
        return globalEventRegistry.get(id);
    }

    public Map<String, IngameEvent> globalEvents()
    {
        return globalEventRegistry;
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
                + (globalEventRegistry != null ? toString(globalEventRegistry.entrySet(), maxLen) : null)
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

}
