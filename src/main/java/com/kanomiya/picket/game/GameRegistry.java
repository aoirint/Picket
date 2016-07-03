package com.kanomiya.picket.game;

import java.awt.image.BufferedImage;
import java.util.Map;

import com.kanomiya.picket.render.Texture;
import com.kanomiya.picket.tile.Tile;

public abstract class GameRegistry
{
    Map<String, BufferedImage> imageRegistry;
    Map<String, Texture> textureRegistry;
    Map<String, Tile> tileRegistry;

}
