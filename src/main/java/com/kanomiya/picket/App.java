package com.kanomiya.picket;

import java.io.FileNotFoundException;

import org.apache.log4j.Logger;

import com.kanomiya.picket.game.Game;
import com.kanomiya.picket.game.GameBuilder;
import com.kanomiya.picket.render.screen.Screen;
import com.kanomiya.picket.window.GameFrame;
import com.kanomiya.picket.world.FieldMap;
import com.kanomiya.picket.world.Tile;

public class App
{
    public static Logger logger;

    public static final int WIDTH = 854;
    public static final int HEIGHT = 480;

    public static void main(String[] args)
    {
        logger = Logger.getLogger("Picket");

        GameBuilder builder = new GameBuilder("sample");

        try
        {
            Game game = builder.build();

            logger.info("### Finish Loading Game");
            logger.info(game.info);
            logger.info(game.registry);
            logger.info(game.world);

            Screen screen = new Screen(WIDTH, HEIGHT);
            GameFrame frame = new GameFrame(game.info.name, screen);

            screen.addPainter(game.renderer);

            frame.addKeyListener(game.controller);
            frame.addMouseListener(game.controller);
            frame.addMouseMotionListener(game.controller);

            frame.setVisible(true);

            Thread ticker = new Thread("Ticker")
            {
                @Override
                public void run()
                {
                    while (true)
                    {
                        if (game.world.enableTick)
                        {
                            game.world.worldEventRegistry.values().forEach((event) ->
                            {
                                if (event.texture != null)
                                {
                                    event.renderInfo.nextTick(event.texture);
                                }
                            });

                            if (game.observer != null)
                            {
                                FieldMap map = game.observer.map;

                                for (int x=0; x<map.width(); x++)
                                {
                                    for (int y=0; y<map.height(); y++)
                                    {
                                        Tile tile = map.tileAt(x, y);
                                        if (tile != null && tile.texture != null)
                                        {
                                            tile.renderInfo.nextTick(tile.texture);
                                        }
                                    }
                                }

                            }
                        }


                        try
                        {
                            sleep(100);
                        } catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            };

            ticker.start();

            Thread thread = new Thread("Repainter")
            {
                @Override
                public void run()
                {
                    while (true)
                    {
                        screen.repaint();

                        try
                        {
                            sleep(16);
                        } catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            };

            thread.start();

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
}
