package com.kanomiya.picket.world;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Maps;
import com.kanomiya.picket.game.GameRegistry;
import com.kanomiya.picket.render.texture.Texture;
import com.kanomiya.picket.render.texture.TextureRenderInfo;
import com.kanomiya.picket.util.IDataSerializer;

public class IngameEvent
{
    public final String id;
    public FieldMap map;
    public int x, y;
    private Direction direction;
    public boolean directionLock;

    @Nullable
    public Texture texture;
    public final TextureRenderInfo renderInfo;

    @Nullable
    public String script;
    public final Map<String, Object> eventRecords;

    public IngameEvent(String id, FieldMap map, int x, int y, Direction direction, boolean directionLock, @Nullable Texture texture, @Nullable String script, Map<String, Object> eventRecords)
    {
        this.id = id;
        this.map = map;
        this.x = x;
        this.y = y;

        this.texture = texture;
        this.renderInfo = new TextureRenderInfo();

        this.script = script;
        this.eventRecords = eventRecords;

        direction(direction);
        this.directionLock = directionLock;
    }



    public void pos(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Direction direction()
    {
        return direction;
    }

    public void direction(@Nonnull Direction direction)
    {
        this.direction = direction;

        if (texture.enableDirection)
        {
            renderInfo.enableVariantId = true;

            switch (direction)
            {
            case SOUTH: renderInfo.variantId = "south";
                break;
            case NORTH: renderInfo.variantId = "north";
                break;
            case WEST:  renderInfo.variantId = "west";
                break;
            case EAST:  renderInfo.variantId = "east";
                break;
            }
        }
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

        if (! directionLock) direction(to);

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
        GameRegistry registry;
        Map<String, FieldMap> mapRegistry;

        public DataSerializerIngameEvent(GameRegistry registry, Map<String, FieldMap> mapRegistry)
        {
            this.registry = registry;
            this.mapRegistry = mapRegistry;
        }

        @Override
        public Map<String, Object> serialize(IngameEvent event)
        {
            Map<String, Object> map = Maps.newHashMap();


            return null;
        }

        @Override
        public IngameEvent deserialize(Map<String, Object> map)
        {
            String id = (String) map.get("id");

            FieldMap fieldMap = mapRegistry.get(map.get("map"));
            int x = (int) map.get("x");
            int y = (int) map.get("y");
            Direction direction = Direction.SOUTH;

            if (map.containsKey("direction"))
            {
                switch ((String) map.get("direction"))
                {
                case "s":
                case "south":
                    direction = Direction.SOUTH;
                    break;
                case "n":
                case "north":
                    direction = Direction.NORTH;
                    break;
                case "e":
                case "east":
                    direction = Direction.EAST;
                    break;
                case "w":
                case "west":
                    direction = Direction.WEST;
                    break;
                }
            }

            boolean directionLock = (boolean) map.getOrDefault("directionLock", true);

            String textureId = (String) map.getOrDefault("texture", null);
            Texture texture = textureId != null ? registry.texture(textureId) : null;
            String script = (String) map.getOrDefault("script", null);

            @SuppressWarnings("unchecked")
            Map<String, Object> eventRecords = (Map<String, Object>) map.get("records");
            if (eventRecords == null) eventRecords = Maps.newHashMap();

            return new IngameEvent(id, fieldMap, x, y, direction, directionLock, texture, script, eventRecords);
        }

    }


}
