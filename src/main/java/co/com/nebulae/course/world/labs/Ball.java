/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.nebulae.course.world.labs;

import co.com.nebulae.course.world.*;
import co.com.nebulae.course.entity.Xform;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

/**
 *
 * @author Sebastian Molano - nebulae.com.co
 */
public class Ball implements WorldShape {

    //dependencies
    private Xform world;
    private Xform ballform;
    private Double directionX = 1d;
    private Double directionZ = 1d;
    private Sphere oxygenSphere;

    //Free fall
    private Double gravity = -9.8 / 1000;
    private Double angleX = 60d;
    private Double angleY = 20d;
    private Double angleZ = 70d;
    private Double speed = 2d;
    private Double speedX = 0d;
    private Double speedY = 0d;
    private Double speedZ = 0d;
    private Double traveledDistanceX = 0d;
    private Double traveledDistanceY = 0d;
    private Double traveledDistanceZ = 0d;
    private Boolean stopY = false;
    private Boolean go = false;
    private Double radius = 25d;
    private Double frictionCoefficient = 0.005d;

    final Xform elementsGroup = new Xform();

    //<editor-fold defaultstate="collapsed" desc="GETTER AND SETTER">
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

    public Xform getElementsGroup() {
        return elementsGroup;
    }

    public Xform getOxygenform() {
        return ballform;
    }

    public Sphere getOxygenSphere() {
        return oxygenSphere;
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
        this.world = world;
        world.getChildren().addAll(elementsGroup);
    }

    public void go() {
        go = false;
        angleX = 60d;
        angleY = 20d;
        angleZ = 70d;
        speed = 3d;
        speedX = speed * Math.cos(Math.toRadians(angleX));
        speedY = speed * Math.cos(Math.toRadians(angleY));
        speedZ = speed * Math.cos(Math.toRadians(angleZ));
        directionX = 1d;
        directionZ = 1d;
        traveledDistanceX = 0d;
        traveledDistanceY = 0d;
        traveledDistanceZ = 0d;
        ballform.t.setY(150);
        ballform.t.setX(-500);
        ballform.t.setZ(500);
        go = true;
    }

    public void move(Long time) {
        if (!go) {
            return;
        }

        if (ballform.t.getY() <= radius) {
            speedX = speedX * (1 - frictionCoefficient);
            speedZ = speedZ * (1 - frictionCoefficient);

            if (speedX <= 0.01) {
                speedX = 0d;
            }

            if (speedZ <= 0.01) {
                speedZ = 0d;
            }

            speedY += gravity * time;
            speedY = speedY * 0.80 * -1;

            stopY = speedY < 0.6;

        } else {
            speedY += gravity * time;
        }
        
        if(stopY){
            speedY = 0d;
        }

        if (ballform.t.getX() <= -1000 || ballform.t.getX() >= 1000) {
            directionX = directionX * -1;
        }

        if (ballform.t.getZ() <= -1000 || ballform.t.getZ() >= 1000) {
            directionZ = directionZ * -1;
        }

        traveledDistanceY = (speedY * time) + (0.5 * gravity * Math.pow(time, 2));
        traveledDistanceX = (speedX * time) * directionX;
        traveledDistanceZ = (speedZ * time) * directionZ;
        double z = ballform.t.getZ() + traveledDistanceZ;
        double y = ballform.t.getY() + traveledDistanceY;
        double x = ballform.t.getX() + traveledDistanceX;

        if (y <= radius) {
            System.out.println("speedY-> " + speedY);
            y = radius;
        }

        System.out.println("y: " + y);

        if (x <= -1000) {
            x = -1000;
        }

        if (x >= 1000) {
            x = 1000;
        }

        if (z <= -1000) {
            z = -1000;
        }

        if (z >= 1000) {
            z = 1000;
        }

        ballform.t.setZ(z);
        ballform.t.setY(y);
        ballform.t.setX(x);
        
        if(stopY && speedX == 0 && speedZ == 0){
            world.getChildren().remove(elementsGroup);
            go = false;
        }
    }

    @Override
    public boolean collide(WorldShape worldShape) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void redraw(Long time) {
        move(time);
    }



    @Override
    public boolean isValid() {
        System.out.println("stopY-> "+ stopY+ " - speedX-> "+speedX+ " - speedZ-> "+ speedZ);
        return !(stopY && speedX == 0 && speedZ == 0);
    }

    @Override
    public void handleInput(KeyEvent keyEvent, boolean keyPressed) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
