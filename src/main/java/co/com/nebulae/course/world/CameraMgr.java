/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.nebulae.course.world;

import co.com.nebulae.course.entity.Xform;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;

/**
 *
 * @author Sebastian Molano - nebulae.com.co
 */
public class CameraMgr {

    //dependencies
    private Group root;
    private SubScene scene;

    private final PerspectiveCamera camera = new PerspectiveCamera(true);
    private final Xform cameraXform_1_xy_r = new Xform();
    private final Xform cameraXform_2_xy_t = new Xform();
    private final Xform cameraXform_3_z_r = new Xform();
    private final double cameraDistance = 5600;

    //<editor-fold defaultstate="collapsed" desc="SINGLETON">
    private CameraMgr() {
    }
    
    public static CameraMgr getInstance() {
        return CameraMgrHolder.INSTANCE;
    }


    private static class CameraMgrHolder {

        private static final CameraMgr INSTANCE = new CameraMgr();
    }

//</editor-fold>
    public void buildCamera(SubScene scene, Group root) {
        Log.print("build camera ... ");
        root.getChildren().add(cameraXform_1_xy_r);
        cameraXform_1_xy_r.getChildren().add(cameraXform_2_xy_t);
        cameraXform_2_xy_t.getChildren().add(cameraXform_3_z_r);
        cameraXform_3_z_r.getChildren().add(camera);
        cameraXform_3_z_r.setRotateZ(180.0);

        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setFieldOfView(35);
        camera.setTranslateZ(-cameraDistance);
        cameraXform_1_xy_r.ry.setAngle(320.0);
        cameraXform_1_xy_r.rx.setAngle(40);

        scene.setCamera(camera);
        
        Log.println("ok");
    }
    
    
    //<editor-fold defaultstate="collapsed" desc="GETTERS">
    
    /**
     * @return the camera
     */
    public PerspectiveCamera getCamera() {
        return camera;
    }
    
    /**
     * @return the cameraXform_1_xy_r
     */
    public Xform getCameraXform_1_xy_r() {
        return cameraXform_1_xy_r;
    }
    
    /**
     * @return the cameraXform_2_xy_t
     */
    public Xform getCameraXform_2_xy_t() {
        return cameraXform_2_xy_t;
    }
    
    /**
     * @return the cameraXform_3_z_r
     */
    public Xform getCameraXform_3_z_r() {
        return cameraXform_3_z_r;
    }
    
    /**
     * @return the cameraDistance
     */
    public double getCameraDistance() {
        return cameraDistance;
    }
    
//</editor-fold>
    
}
