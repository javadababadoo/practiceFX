/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.nebulae.course.world.labs;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Esteban Zapata
 */
public class DirectorMgr {

    private List<WorldShape> dynamicObjects = new ArrayList<>();

    private List<WorldShape> staticObjects = new ArrayList<>();

    private List<WorldShape> partialDynamicObjects = new ArrayList<>();

    //<editor-fold defaultstate="collapsed" desc="SINGLETON">

    private DirectorMgr() {
    }

    public static DirectorMgr getInstance() {
        return ElementMgrHolder.INSTANCE;
    }

    /**
     * @return the dynamicObjects
     */
    public List<WorldShape> getDynamicObjects() {
        return dynamicObjects;
    }

    /**
     * @param dynamicObjects the dynamicObjects to set
     */
    public void setDynamicObjects(List<WorldShape> dynamicObjects) {
        this.dynamicObjects = dynamicObjects;
    }

    /**
     * @return the staticObjects
     */
    public List<WorldShape> getStaticObjects() {
        return staticObjects;
    }

    /**
     * @param staticObjects the staticObjects to set
     */
    public void setStaticObjects(List<WorldShape> staticObjects) {
        this.staticObjects = staticObjects;
    }

    /**
     * @return the partialDynamicObjects
     */
    public List<WorldShape> getPartialDynamicObjects() {
        return partialDynamicObjects;
    }

    /**
     * @param partialDynamicObjects the partialDynamicObjects to set
     */
    public void setPartialDynamicObjects(List<WorldShape> partialDynamicObjects) {
        this.partialDynamicObjects = partialDynamicObjects;
    }

    private static class ElementMgrHolder {

        private static final DirectorMgr INSTANCE = new DirectorMgr();
    }
    //</editor-fold>
    
    public void handleGameLoop(Long time){
        partialDynamicObjects.stream().forEach((worldShape) -> {
            worldShape.redraw();
        });
    }

    public void addPartialObject(WorldShape worldShape){
        partialDynamicObjects.add(worldShape);
    }
    
}
