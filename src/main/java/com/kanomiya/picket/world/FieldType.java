package com.kanomiya.picket.world;

import java.util.Arrays;
import java.util.List;

public class FieldType
{
    public static final FieldType NORMAL = new FieldType(true); // passable
    public static final FieldType BLOCK = new FieldType(false); // unpassable

    public static final FieldType HORIZONTAL_BLOCK = new FieldType(true, false);
    public static final FieldType VERTICAL_BLOCK = new FieldType(false, true);


    private final List<Direction> passableFrom;

    FieldType(boolean all)
    {
        this(all ? Direction.values() : new Direction[0]);
    }

    FieldType(boolean horizontal, boolean vertical)
    {
        this(horizontal ? vertical ? Direction.values() : Direction.horizontals() : vertical ? Direction.verticals() : new Direction[0]);
    }

    FieldType(Direction... passableFrom)
    {
        this.passableFrom = Arrays.asList(passableFrom);
    }

    public boolean passableFrom(Direction dir)
    {
        return passableFrom.contains(dir);
    }



    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((passableFrom == null) ? 0 : passableFrom.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        FieldType other = (FieldType) obj;
        if (passableFrom == null)
        {
            if (other.passableFrom != null) return false;
        } else if (!passableFrom.equals(other.passableFrom)) return false;
        return true;
    }

}
