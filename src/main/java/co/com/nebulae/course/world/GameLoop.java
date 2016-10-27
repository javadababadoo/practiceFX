/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.nebulae.course.world;

import javafx.animation.AnimationTimer;

/**
 *
 * @author Sebastian Molano - nebulae.com.co
 */
public class GameLoop extends AnimationTimer {

    private long last = 0;
    private long times;
    
    private Long lastCall = 0l;

    public void init(){
        lastCall = System.currentTimeMillis();
    }
    
    @Override
    public void handle(long now) {
        times++;
        if (System.currentTimeMillis() - last > 1000) {
            last = System.currentTimeMillis();
            System.out.println(times+" Ghz");
            times = 0;
        }
        long t =  System.currentTimeMillis() - lastCall;
        
        
        BallMgr.getInstance().move(t);
        Ball3Mgr.getInstance().move(t);
        AngularBallMgr.getInstance().move(t);        
        
        SceneMgr.getInstance().getScene().requestFocus();
        
        lastCall = System.currentTimeMillis();        
    }
    
    

}
