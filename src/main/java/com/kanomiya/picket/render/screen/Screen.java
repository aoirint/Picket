package com.kanomiya.picket.render.screen;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import com.google.common.collect.Lists;

public class Screen
{
    private int width, height;

    private BufferedImage screenImage;
    private Graphics2D graphics;

    private List<IScreenPainter> painters;
    private List<IScreenListener> listeners;

    public Screen(int width, int height)
    {
        this.width = width;
        this.height = height;

        this.screenImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        this.graphics = screenImage.createGraphics();

        this.painters = Lists.newArrayList();
        this.listeners = Lists.newArrayList();
    }

    public void paint()
    {
        synchronized (painters)
        {
            for (IScreenPainter ptr: painters)
            {
                ptr.paint(this);
            }
        }
    }

    public void repaint()
    {
        paint(); // TODO:

        synchronized (listeners)
        {
            for (IScreenListener lis: listeners)
            {
                lis.onScreenUpdate(this);
            }
        }
    }

    public BufferedImage getScreenImage()
    {
        return screenImage;
    }

    public Graphics2D getGraphics()
    {
        return graphics;
    }


    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }


    public void addPainter(IScreenPainter painter)
    {
        synchronized (painters)
        {
            painters.add(painter);
        }
    }

    public void removePainter(IScreenPainter painter)
    {
        synchronized (painters)
        {
            painters.remove(painter);
        }
    }

    public void addListener(IScreenListener listener)
    {
        synchronized (listeners)
        {
            listeners.add(listener);
        }
    }

    public void removeListener(IScreenListener listener)
    {
        synchronized (listeners)
        {
            listeners.remove(listener);
        }
    }

}
