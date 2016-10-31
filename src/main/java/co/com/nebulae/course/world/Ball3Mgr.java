/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.nebulae.course.world;

import co.com.nebulae.course.entity.Xform;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

/**
 *
 * @author Sebastian Molano - nebulae.com.co
 */
public class Ball3Mgr {

    //dependencies
    private Xform world;
    private Xform ballform;
    private Double directionX = 1d;
    private Double directionY = 1d;
    private Sphere oxygenSphere;

    //Free fall
    private Double gravity = -9.8 / 1000;
    private Double angle = 70d;
    private Double speed = 2d;
    private Double speedX = 0d;
    private Double speedY = 0d;
    private Double traveledDistanceX = 0d;
    private Double traveledDistanceY = 0d;
    private Boolean go = false;
    private Boolean finish = false;
    private Double radius = 25d;
    private Double frictionCoefficient = 0.005d;

    final Xform elementsGroup = new Xform();

    //<editor-fold defaultstate="collapsed" desc="SINGLETON">
    private Ball3Mgr() {
    }

    public static Ball3Mgr getInstance() {
        return ElementMgrHolder.INSTANCE;
    }

    /**
     * @return the gravity
     */
    public Double getGravity() {
        return gravity;
    }

    /**
     * @param gravity the gravity to set
     */
    public void setGravity(Double gravity) {
        this.gravity = gravity;
    }

    /**
     * @return the speedX
     */
    public Double getSpeedX() {
        return speedX;
    }

    /**
     * @param speedX the speedX to set
     */
    public void setSpeedX(Double speedX) {
        this.speedX = speedX;
    }

    /**
     * @return the speedY
     */
    public Double getSpeedY() {
        return speedY;
    }

    /**
     * @param speedY the speedY to set
     */
    public void setSpeedY(Double speedY) {
        this.speedY = speedY;
    }

    private static class ElementMgrHolder {

        private static final Ball3Mgr INSTANCE = new Ball3Mgr();
    }

//</editor-fold>
    public void buildElements(Xform world) {
        Log.print("build ball ... ");
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKORANGE);
        redMaterial.setSpecularColor(Color.ORANGE);

        ballform = new Xform();
        oxygenSphere = new Sphere(radius);
        oxygenSphere.setMaterial(redMaterial);
        ballform.getChildren().add(oxygenSphere);

        elementsGroup.getChildren().add(ballform);

        ballform.t.setY(150);
        ballform.t.setX(-500);
        ballform.t.setZ(500);
        world.getChildren().addAll(elementsGroup);
    }

    public Xform getElementsGroup() {
        return elementsGroup;
    }

    public Xform getOxygenform() {
        return ballform;
    }

    public void go() {
        go = false;
        angle = 70d;
        speed = 2d;
        speedX = speed * Math.cos(Math.toRadians(angle));
        speedY = speed * Math.sin(Math.toRadians(angle));
        directionX = 1d;
        directionY = 1d;
        traveledDistanceX = 0d;
        traveledDistanceY = 0d;
        ballform.t.setY(150);
        ballform.t.setX(-500);
        ballform.t.setZ(500);
        go = true;
        finish = false;
    }

    public void move(Long time) {
        if (!go) {
            return;
        }

        if (ballform.t.getY() <= radius) {
            speedX = speedX * (1 - frictionCoefficient);

            if (speedX <= 0.01) {
                speedX = 0d;
            }

            speedY += gravity * time;
            speedY = speedY * 0.80 * -1;

        } else {
            //Final Speed = Initial Speed + (Accelaration * Delta Time)
            speedY += gravity * time;
        }

        if (ballform.t.getX() <= -1000 || ballform.t.getX() >= 1000) {
            directionX = directionX * -1;
            System.out.println("DirectionX: "+ directionX + " ballform.t.getX()-> "+ ballform.t.getX());
        }

        //System.out.println("Speed X: " + speedX);
        //Final Speed = Initial Speed + (Accelaration * Delta Time)
        speedY += gravity * time;
        if (Math.abs(speedY) < 0.03) {
            speedY = 0d;
        }

        //System.out.println( " -- Speed: " + speedY);

        //Delta Y = (Initial Speed * Delta time) + (0.5 * acceleration * (Delta Time)ˆ2)       
        traveledDistanceY = (speedY * time) + (0.5 * gravity * Math.pow(time, 2));

        //double x = ballXform.t.getX() + directionX * speedX * time;
        traveledDistanceX = (speedX * time) * directionX;
//        System.out.println("directionX-> " + directionX);
//        System.out.println("traveledDistanceX-> " + traveledDistanceX);
        //Final Y = (Initial Y) + (Delta Y)
        double y = ballform.t.getY() + traveledDistanceY;
        double x = ballform.t.getX() + traveledDistanceX;

        if (y <= radius) {
            y = radius;
            //speedY = 0d;            
//            finish = true;
        }
        
        if(x <= -1000){
            x = -1000;
        }
        
        if(x >= 1000){
            x = 1000;
        }
        //System.out.println("y: "+y);

        ballform.t.setY(y);
        ballform.t.setX(x);
    }

    public Sphere getOxygenSphere() {
        return oxygenSphere;
    }

}
