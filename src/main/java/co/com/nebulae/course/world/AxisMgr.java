/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.nebulae.course.world;

import co.com.nebulae.course.entity.Xform;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

/**
 *
 * @author Sebastian Molano - nebulae.com.co
 */
public class AxisMgr {
    
    //dependencies
    private Xform world;
    
    final Group axisGroup = new Group();
    
    //<editor-fold defaultstate="collapsed" desc="SINGLETON">
    private AxisMgr() {
    }
    
    public static AxisMgr getInstance() {
        return AxisMgrHolder.INSTANCE;
    }
    
    private static class AxisMgrHolder {
        
        private static final AxisMgr INSTANCE = new AxisMgr();
    }
    
//</editor-fold>
    
    public void buildAxes(Xform world) {                
        Log.print("build axes ... ");
        
        float len = 1000;
        float bold = 3;
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);        

        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        final Box xAxis = new Box(len, bold, bold);
        final Box yAxis = new Box(bold, len, bold);
        final Box zAxis = new Box(bold, bold, len);

        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);
                
        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        world.getChildren().addAll(axisGroup);
        
        Log.println("ok");
    }

    public Group getAxisGroup() {
        return axisGroup;
    }
    
    
    
}
