package com.kanomiya.picket.control;


import static java.awt.event.KeyEvent.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.kanomiya.picket.game.Game;

public class GameController implements KeyListener, MouseListener, MouseMotionListener
{
    final Game game;

    public GameController(Game game)
    {
        this.game = game;
    }




    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == VK_UP)
            game.player().y --;
        else if (e.getKeyCode() == VK_DOWN)
            game.player().y ++;
        else if (e.getKeyCode() == VK_LEFT)
            game.player().x --;
        else if (e.getKeyCode() == VK_RIGHT)
            game.player().x ++;
        else if (e.getKeyCode() == VK_CLEAR)
        {
            game.player().x = 0;
            game.player().y = 0;
        }


    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {

    }

    @Override
    public void mouseMoved(MouseEvent e)
    {

    }

    @Override
    public void mouseDragged(MouseEvent e)
    {

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }


}
