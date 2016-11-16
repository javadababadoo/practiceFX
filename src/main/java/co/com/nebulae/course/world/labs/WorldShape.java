/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.nebulae.course.world.labs;

import javafx.scene.input.KeyEvent;

/**
 *
 * @author Esteban Zapata
 */
public interface WorldShape {
    
    public boolean collide(WorldShape worldShape);
    
    public void redraw(Long time);
    
    public void handleInput(KeyEvent keyEvent);
    
}
