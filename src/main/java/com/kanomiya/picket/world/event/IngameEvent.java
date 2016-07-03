package com.kanomiya.picket.world.event;

import javax.annotation.Nullable;

import com.kanomiya.picket.world.FieldMap;

// TODO: 未実装
public class IngameEvent
{
    public FieldMap map;
    public int x, y;

    private String texture;
    private String script;

    public IngameEvent(FieldMap map, int x, int y, @Nullable String texture, @Nullable String script)
    {
        this.map = map;
        this.x = x;
        this.y = y;

        this.texture = texture;
        this.script = script;
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



}
