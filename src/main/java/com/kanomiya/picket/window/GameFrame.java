package com.kanomiya.picket.window;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.kanomiya.picket.render.screen.IScreenListener;
import com.kanomiya.picket.render.screen.Screen;

public class GameFrame extends Frame implements IScreenListener
{
    private Screen screen;
    private Insets insets;

    public GameFrame(String title, Screen screen)
    {
        super(title);

        this.screen = screen;
        screen.addListener(this);

        pack();
        setRealSize(screen.getWidth(), screen.getHeight());

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });

    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);

        if (insets == null)
        {
            insets = getInsets();
        }

        g.translate(insets.left, insets.top);
        g.drawImage(screen.getScreenImage(), 0, 0, this);
        g.translate(-insets.left, -insets.top);

    }

    @Override
    public void update(Graphics g)
    {
        paint(g);
    }

    @Override
    public void onScreenUpdate(Screen screen)
    {
        repaint();
    }


    public void setRealSize(int width, int height)
    {
        insets = getInsets();
        setSize(width +insets.left +insets.right, height +insets.top +insets.bottom);
    }


}
