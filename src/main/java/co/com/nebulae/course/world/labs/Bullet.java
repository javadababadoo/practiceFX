/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.nebulae.course.world.labs;

import co.com.nebulae.course.world.*;
import co.com.nebulae.course.entity.Xform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;

/**
 *
 * @author Sebastian Molano - nebulae.com.co
 */
public class Bullet implements WorldShape {

    private Xform world;
    final Xform elementsGroup = new Xform();
    
    private final double spaceSize = 5000;

    //<editor-fold defaultstate="collapsed" desc="ARROW">


    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="LAUNCHBALL">
    private Xform ballform;
    private Sphere oxygenSphere;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="PROPERTIES">
    private Double directionX = 1d;
    private Double directionY = 1d;
    private Double directionZ = 1d;
    private Double gravity = -9.8 / 1000;
    private Double angle = 70d;
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
    private Boolean finish = false;
    private final Double radius = 45d;
    private final Double frictionCoefficient = 0.005d;

    //</editor-fold>

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
        this.world = world;
        ballform = new Xform();
        Log.print("build bullet ... ");

        final PhongMaterial greyMaterial = new PhongMaterial();
        greyMaterial.setDiffuseColor(Color.DARKRED);
        greyMaterial.setSpecularColor(Color.RED);

        oxygenSphere = new Sphere(radius);
        oxygenSphere.setMaterial(greyMaterial);
        ballform.getChildren().add(oxygenSphere);

        elementsGroup.getChildren().add(ballform);

        ballform.t.setY(150);
        ballform.t.setX(-500);
        ballform.t.setZ(500);

        world.getChildren().addAll(elementsGroup);
    }


    public void move(Long time) {
        if (!go) {
            return;
        }

        if (ballform.t.getY() <= radius) {
            speedX = speedX * (1 - frictionCoefficient);
            speedZ = speedZ * (1 - frictionCoefficient);
            if (Math.abs(speedX) <= 0.01) {
                speedX = 0d;
            }
            if (Math.abs(speedZ) <= 0.01) {
                speedZ = 0d;
            }
            speedY += gravity * time;
            speedY = speedY * 0.80 * -1;
            stopY = speedY < 0.6;
        } else {
            speedY += gravity * time;
        }

        if (stopY) {
            speedY = 0d;
        }
        if (ballform.t.getX() <= -spaceSize || ballform.t.getX() >= spaceSize) {
            directionX = directionX * -1;
        }
        if (ballform.t.getZ() <= -spaceSize || ballform.t.getZ() >= spaceSize) {
            directionZ = directionZ * -1;
        }

        traveledDistanceY = (speedY * time) + (0.5 * gravity * Math.pow(time, 2));
        traveledDistanceX = (speedX * time) * directionX;
        traveledDistanceZ = (speedZ * time) * directionZ;
        double z = ballform.t.getZ() + traveledDistanceZ;
        double y = ballform.t.getY() + traveledDistanceY;
        double x = ballform.t.getX() + traveledDistanceX;

        if (y <= radius) {
            y = radius;
        }
        if (x <= -spaceSize) {
            x = -spaceSize;
        }
        if (x >= spaceSize) {
            x = spaceSize;
        }
        if (z <= -spaceSize) {
            z = -spaceSize;
        }
        if (z >= spaceSize) {
            z = spaceSize;
        }

        ballform.t.setZ(z);
        ballform.t.setY(y);
        ballform.t.setX(x);

        if (stopY && speedX == 0 && speedZ == 0) {
            world.getChildren().remove(elementsGroup);
            go = false;
            finish = true;
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
    public void handleInput(KeyEvent keyEvent, boolean keyPressed) {
    }

    public void shot() {
        go = false;
        directionX = 1d;
        directionY = 1d;
        directionZ = 1d;
        traveledDistanceX = 0d;
        traveledDistanceY = 0d;
        traveledDistanceZ = 0d;
        ballform.t.setY(300);
        ballform.t.setX(DirectorMgr.getInstance().getPositionXTank());
        ballform.t.setZ(DirectorMgr.getInstance().getPositionZTank());
        
//        System.out.println("AngleY-> "+ DirectorMgr.getInstance().getAngleY());
//        System.out.println("getAngleZ-> "+DirectorMgr.getInstance().getAngleZ());
//        System.out.println("getAngleX-> "+DirectorMgr.getInstance().getAngleX());
        
        //vertical
        angleY = DirectorMgr.getInstance().getAngleY();
        //horizontal
        angleZ = DirectorMgr.getInstance().getAngleZ();
        angleX = DirectorMgr.getInstance().getAngleX();
        //double inverterX = (angleY >= 0 && angleY <= 180) ? 1d : -1d;

        double inverterX = Math.sin(Math.toRadians(angleY));

        speed = 3d;
        speedX = speed * Math.cos(Math.toRadians(angleX)) * inverterX;
        speedY = speed * Math.cos(Math.toRadians(angleY));
        speedZ = speed * Math.cos(Math.toRadians(angleZ)) * inverterX;

        go = true;
        finish = false;
    }

    @Override
    public boolean isValid() {
        return !finish;
    }

}
