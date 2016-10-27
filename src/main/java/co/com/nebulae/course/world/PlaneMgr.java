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
public class PlaneMgr {
    
    //dependencies
    private Xform world;
    
    final Group planeGroup = new Group();
    
    //<editor-fold defaultstate="collapsed" desc="SINGLETON">
    private PlaneMgr() {
    }
    
    public static PlaneMgr getInstance() {
        return PlaneMgrHolder.INSTANCE;
    }
    
    private static class PlaneMgrHolder {
        
        private static final PlaneMgr INSTANCE = new PlaneMgr();
    }
    
//</editor-fold>
    
    public void buildPlane(Xform world) {
        Log.print("build plane ... ");
        final PhongMaterial whiteMaterial = new PhongMaterial();
        whiteMaterial.setDiffuseColor(Color.DARKGRAY);
        whiteMaterial.setSpecularColor(Color.WHITE);

        float bold = 0.5f;
        int halfPlaneSize = 500;

        for (int pos = -halfPlaneSize; pos < halfPlaneSize; pos += 20) {
            final Box xLines = new Box(halfPlaneSize * 2, bold, bold);
            xLines.setTranslateZ(pos);
            xLines.setMaterial(whiteMaterial);
            final Box zLine = new Box(bold, bold, halfPlaneSize * 2);
            zLine.setTranslateX(pos);
            zLine.setMaterial(whiteMaterial);

            planeGroup.getChildren().addAll(xLines, zLine);
        }
        world.getChildren().addAll(planeGroup);
        Log.println("ok");
    }

    public Group getPlaneGroup() {
        return planeGroup;
    }
    
    
}
