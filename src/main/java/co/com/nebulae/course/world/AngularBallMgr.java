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
public class AngularBallMgr {

    //dependencies
    private Xform world;
    private Xform ballXform;
    private Double speedRadio = 0d;
    private Double speedAngle = 0d;
    private Double directionRadio = 1d;
    private Double directionAngle = 1d;
    private Double centerX = 0d;
    private Double centerZ = 0d;
    private Double radio = 0d;
    private Double angle = 0d;
    private Sphere oxygenSphere;

    final Xform elementsGroup = new Xform();

    //<editor-fold defaultstate="collapsed" desc="SINGLETON">
    private AngularBallMgr() {
    }

    public static AngularBallMgr getInstance() {
        return ElementMgrHolder.INSTANCE;
    }

    /**
     * @return the speedRadio
     */
    public Double getSpeedRadio() {
        return speedRadio;
    }

    /**
     * @param speedRadio the speedRadio to set
     */
    public void setSpeedRadio(Double speedRadio) {
        this.speedRadio = speedRadio;
    }

    /**
     * @return the speedAngle
     */
    public Double getSpeedAngle() {
        return speedAngle;
    }

    /**
     * @param speedAngle the speedAngle to set
     */
    public void setSpeedAngle(Double speedAngle) {
        this.speedAngle = speedAngle;
    }

    /**
     * @return the directionRadio
     */
    public Double getDirectionRadio() {
        return directionRadio;
    }

    /**
     * @param directionRadio the directionRadio to set
     */
    public void setDirectionRadio(Double directionRadio) {
        this.directionRadio = directionRadio;
    }

    /**
     * @return the directionAngle
     */
    public Double getDirectionAngle() {
        return directionAngle;
    }

    /**
     * @param directionAngle the directionAngle to set
     */
    public void setDirectionAngle(Double directionAngle) {
        this.directionAngle = directionAngle;
    }

    /**
     * @return the centerY
     */
    public Double getCenterZ() {
        return centerZ;
    }

    /**
     * @param centerY the centerY to set
     */
    public void setCenterZ(Double centerY) {
        this.centerZ = centerY;
    }

    private static class ElementMgrHolder {

        private static final AngularBallMgr INSTANCE = new AngularBallMgr();
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
        oxygenSphere.setMaterial(whiteMaterial);
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

        angle = angle + directionAngle * (time * speedAngle);
        if(angle >= 360){
            angle = 0d;
        }else if(angle < 0){
            angle = 360d;
        }
        radio = radio + directionRadio * (time * speedRadio);
        if(radio > 900d){
            radio = 900d;
        }else if(radio < 30){
            radio = 30d;
        }
        

        //MRU X
        double x = centerX + radio * Math.cos(angle);
        ballXform.t.setX(x);

        //MRU Z
        double z = centerZ + radio * Math.sin(angle);
        ballXform.t.setZ(z);
    }

    public Sphere getOxygenSphere() {
        return oxygenSphere;
    }
    
    

}
