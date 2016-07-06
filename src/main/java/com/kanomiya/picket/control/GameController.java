package com.kanomiya.picket.control;


import static java.awt.event.KeyEvent.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.kanomiya.picket.game.Game;
import com.kanomiya.picket.world.Direction;
import com.kanomiya.picket.world.IngameEvent;

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
        if (game.actor() != null)
        {
            IngameEvent actor = game.actor();

            if (e.getKeyCode() == VK_UP) actor.move(Direction.NORTH);
            else if (e.getKeyCode() == VK_DOWN) actor.move(Direction.SOUTH);
            else if (e.getKeyCode() == VK_LEFT) actor.move(Direction.WEST);
            else if (e.getKeyCode() == VK_RIGHT) actor.move(Direction.EAST);
            else if (e.getKeyCode() == VK_CLEAR)
            {
                actor.x = 0;
                actor.y = 0;
            }

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
