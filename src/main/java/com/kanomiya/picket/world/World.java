package com.kanomiya.picket.world;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public abstract class World
{
    protected Map<String, FieldMap> mapRegistry;




    @Override
    public String toString()
    {
        final int maxLen = 10;
        return "World [mapRegistry=" + (mapRegistry != null ? toString(mapRegistry.entrySet(), maxLen) : null) + "]";
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
