package com.kanomiya.picket.world;

import javax.annotation.Nonnull;

public enum Direction
{
    NORTH(0, -1),
    SOUTH(0, 1),
    WEST(-1, 0),
    EAST(1, 0);

    private int xOffset, yOffset;

    Direction(int xOffset, int yOffset)
    {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public int offsetX()
    {
        return xOffset;
    }
    public int offsetY()
    {
        return yOffset;
    }


    private static final Direction[] verticals = new Direction[] { NORTH, SOUTH };
    private static final Direction[] horizontals = new Direction[] { WEST, EAST };

    public static Direction[] verticals()
    {
        return verticals.clone();
    }

    public static Direction[] horizontals()
    {
        return horizontals.clone();
    }



    public Direction turn(@Nonnull SubDirection subDir)
    {
        if (subDir == SubDirection.FRONT) return this;
        if (subDir == SubDirection.BACK) return opposite();

        boolean isLeft = subDir == SubDirection.LEFT;

        switch (this)
        {
        case NORTH: return isLeft ? WEST  : EAST ;
        case SOUTH: return isLeft ? EAST  : WEST ;
        case WEST:  return isLeft ? SOUTH : NORTH;
        case EAST:  return isLeft ? NORTH : SOUTH;
        }

        return this;
    }

    public Direction opposite()
    {
        switch (this)
        {
        case NORTH: return SOUTH;
        case SOUTH: return NORTH;
        case WEST:  return EAST;
        case EAST:  return WEST;
        }

        return this;
    }

    public static Direction fromOffset(int offsetX, int offsetY)
    {
        if (offsetX == 0 && offsetY == 0) return Direction.SOUTH;


        if (offsetX == 0)
        {
            return offsetY < 0 ? NORTH : SOUTH;
        } else if (offsetX < 0)
        {
            return WEST;
        } else
        {
            return EAST;
        }

    }

}
