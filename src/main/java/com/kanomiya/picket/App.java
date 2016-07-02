package com.kanomiya.picket;

import java.io.FileNotFoundException;

import org.apache.log4j.Logger;

import com.kanomiya.picket.game.Game;
import com.kanomiya.picket.game.GameBuilder;

public class App
{
    public static Logger logger;

    public static void main(String[] args)
    {
        logger = Logger.getLogger("Picket");

        GameBuilder builder = new GameBuilder("sample");

        try
        {
            Game game = builder.build();

            logger.info(game.info());


        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
}
