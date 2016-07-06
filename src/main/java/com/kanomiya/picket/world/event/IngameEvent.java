package com.kanomiya.picket.world.event;

import java.util.Map;

import javax.annotation.Nullable;

import com.kanomiya.picket.world.Direction;
import com.kanomiya.picket.world.FieldMap;

public class IngameEvent
{
    public FieldMap map;
    public int x, y;

    private String texture;
    private String script;
    private Map<String, Object> eventRecords;

    public IngameEvent(FieldMap map, int x, int y, @Nullable String texture, @Nullable String script, Map<String, Object> eventRecords)
    {
        this.map = map;
        this.x = x;
        this.y = y;

        this.texture = texture;
        this.script = script;
        this.eventRecords = eventRecords;
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

}
