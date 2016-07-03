package com.kanomiya.picket;

import java.io.FileNotFoundException;

import org.apache.log4j.Logger;

import com.kanomiya.picket.game.Game;
import com.kanomiya.picket.game.GameBuilder;
import com.kanomiya.picket.render.screen.Screen;
import com.kanomiya.picket.window.GameFrame;

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
            logger.info(game.info());
            logger.info(game.registry());
            logger.info(game.world());

            Screen screen = new Screen(WIDTH, HEIGHT);
            GameFrame frame = new GameFrame(game.info().name(), screen);

            screen.addPainter(game.renderer());

            frame.addKeyListener(game.controller());
            frame.addMouseListener(game.controller());
            frame.addMouseMotionListener(game.controller());

            frame.setVisible(true);

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
