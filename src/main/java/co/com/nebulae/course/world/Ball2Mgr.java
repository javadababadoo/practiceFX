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
public class Ball2Mgr {

    //dependencies
    private Xform world;
    private Xform ballYform;
    private Double directionX = 1d;
    private Double directionZ = 1d;
    private Double directionY = -1d;
    private Sphere oxygenSphere;
    
    //Free fall
    private Double gravity = 0d;
    private Double speedY = 0d;

    final Xform elementsGroup = new Xform();

    //<editor-fold defaultstate="collapsed" desc="SINGLETON">
    private Ball2Mgr() {
    }

    public static Ball2Mgr getInstance() {
        return ElementMgrHolder.INSTANCE;
    }


    /**
     * @return the directionX
     */
    public Double getDirectionX() {
        return directionX;
    }

    /**
     * @param directionX the directionX to set
     */
    public void setDirectionX(Double directionX) {
        this.directionX = directionX;
    }

    /**
     * @return the directionZ
     */
    public Double getDirectionZ() {
        return directionZ;
    }

    /**
     * @param directionZ the directionZ to set
     */
    public void setDirectionZ(Double directionZ) {
        this.directionZ = directionZ;
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

    private static class ElementMgrHolder {

        private static final Ball2Mgr INSTANCE = new Ball2Mgr();
    }

//</editor-fold>
    public void buildElements(Xform world) {
        Log.print("build ball ... ");
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKBLUE);
        redMaterial.setSpecularColor(Color.BLUE);

        ballYform = new Xform();
        oxygenSphere = new Sphere(40.0);
        oxygenSphere.setMaterial(redMaterial);
        ballYform.getChildren().add(oxygenSphere);

        elementsGroup.getChildren().add(ballYform);
        
        ballYform.t.setY(800);
        ballYform.t.setX(100);
        ballYform.t.setZ(100);
        world.getChildren().addAll(elementsGroup);
    }

    public Xform getElementsGroup() {
        return elementsGroup;
    }

    public Xform getOxygenYform() {
        return ballYform;
    }

    public void move(Long time) {
        
        double y = ballYform.t.getY() + directionY * speedY * time;
        speedY = speedY + (gravity* time);        
     
        if(y <= 40){
            y = 40;
            speedY = 0d;
        }
        System.out.println("Y: "+ y);
        System.out.println("speedY: "+ speedY);
        ballYform.t.setY(y);
        
//        
//        //MRU X
//        double x = ballYform.t.getX() + directionX * speedX * time;
//        if (x < -500) {
//            x = -500;
//        } else if (x > 500) {
//            x = 500;
//        }
//        ballYform.t.setX(x);
//
//        //MRU Z
//        double z = ballYform.t.getZ() + directionZ * speedZ * time;
//        if (z < -500) {
//            z = -500;
//        } else if (z > 500) {
//            z = 500;
//        }
//        ballYform.t.setZ(z);
    }

    public Sphere getOxygenSphere() {
        return oxygenSphere;
    }

}
