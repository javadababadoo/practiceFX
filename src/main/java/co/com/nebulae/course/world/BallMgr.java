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
public class BallMgr {

    //dependencies
    private Xform world;
    private Xform ballXform;
    private Double speedX = 0d;
    private Double speedZ = 0d;
    private Double directionX = 1d;
    private Double directionZ = 1d;
    private Sphere oxygenSphere;

    final Xform elementsGroup = new Xform();

    //<editor-fold defaultstate="collapsed" desc="SINGLETON">
    private BallMgr() {
    }

    public static BallMgr getInstance() {
        return ElementMgrHolder.INSTANCE;
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
     * @return the speedZ
     */
    public Double getSpeedZ() {
        return speedZ;
    }

    /**
     * @param speedZ the speedZ to set
     */
    public void setSpeedZ(Double speedZ) {
        this.speedZ = speedZ;
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

    private static class ElementMgrHolder {

        private static final BallMgr INSTANCE = new BallMgr();
    }

//</editor-fold>
    public void buildElements(Xform world) {
        Log.print("build ball ... ");
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial whiteMaterial = new PhongMaterial();
        whiteMaterial.setDiffuseColor(Color.WHITE);
        whiteMaterial.setSpecularColor(Color.LIGHTBLUE);

        final PhongMaterial greyMaterial = new PhongMaterial();
        greyMaterial.setDiffuseColor(Color.DARKGREY);
        greyMaterial.setSpecularColor(Color.GREY);

        ballXform = new Xform();
        oxygenSphere = new Sphere(40.0);
        oxygenSphere.setMaterial(redMaterial);
        ballXform.getChildren().add(oxygenSphere);

        elementsGroup.getChildren().add(ballXform);
        world.getChildren().addAll(elementsGroup);
    }

    public Xform getElementsGroup() {
        return elementsGroup;
    }

    public Xform getOxygenXform() {
        return ballXform;
    }

    public void move(Long time) {
        //MRU X
        double x = ballXform.t.getX() + directionX * speedX * time;
        if (x < -500) {
            x = -500;
        } else if (x > 500) {
            x = 500;
        }
        ballXform.t.setX(x);

        //MRU Z
        double z = ballXform.t.getZ() + directionZ * speedZ * time;
        if (z < -500) {
            z = -500;
        } else if (z > 500) {
            z = 500;
        }
        ballXform.t.setZ(z);
    }

    public Sphere getOxygenSphere() {
        return oxygenSphere;
    }

}
