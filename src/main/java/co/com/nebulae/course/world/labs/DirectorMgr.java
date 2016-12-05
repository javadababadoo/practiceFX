/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.nebulae.course.world.labs;

import co.com.nebulae.course.world.SceneMgr;
import java.util.ArrayList;
import java.util.List;
import javafx.event.Event;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Esteban Zapata
 */
public class DirectorMgr {

    private List<WorldShape> dynamicObjects = new ArrayList<>();

    private List<WorldShape> staticObjects = new ArrayList<>();

    private List<WorldShape> partialDynamicObjects = new ArrayList<>();

    private LaunchableBall launchableBallMain;

    private Double angleX = 0d;
    private Double angleY = 0d;
    private Double angleZ = 0d;
    
    private Double positionXTank = 0d;
    private Double positionYTank = 0d;
    private Double positionZTank = 0d;

    //<editor-fold defaultstate="collapsed" desc="SINGLETON">
    private DirectorMgr() {
        buildWorld();
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

    /**
     * @return the launchableBallMain
     */
    public LaunchableBall getLaunchableBallMain() {
        return launchableBallMain;
    }

    /**
     * @param launchableBallMain the launchableBallMain to set
     */
    public void setLaunchableBallMain(LaunchableBall launchableBallMain) {
        this.launchableBallMain = launchableBallMain;
    }

    /**
     * @return the angleX
     */
    public Double getAngleX() {
        return angleX;
    }

    /**
     * @param angleX the angleX to set
     */
    public void setAngleX(Double angleX) {
        this.angleX = angleX;
    }

    /**
     * @return the angleY
     */
    public Double getAngleY() {
        return angleY;
    }

    /**
     * @param angleY the angleY to set
     */
    public void setAngleY(Double angleY) {
        this.angleY = angleY;
    }

    /**
     * @return the angleZ
     */
    public Double getAngleZ() {
        return angleZ;
    }

    /**
     * @param angleZ the angleZ to set
     */
    public void setAngleZ(Double angleZ) {
        this.angleZ = angleZ;
    }

    /**
     * @return the positionXTank
     */
    public Double getPositionXTank() {
        return positionXTank;
    }

    /**
     * @param positionXTank the positionXTank to set
     */
    public void setPositionXTank(Double positionXTank) {
        this.positionXTank = positionXTank;
    }

    /**
     * @return the positionYTank
     */
    public Double getPositionYTank() {
        return positionYTank;
    }

    /**
     * @param positionYTank the positionYTank to set
     */
    public void setPositionYTank(Double positionYTank) {
        this.positionYTank = positionYTank;
    }

    /**
     * @return the positionZTank
     */
    public Double getPositionZTank() {
        return positionZTank;
    }

    /**
     * @param positionZTank the positionZTank to set
     */
    public void setPositionZTank(Double positionZTank) {
        this.positionZTank = positionZTank;
    }

    private static class ElementMgrHolder {

        private static final DirectorMgr INSTANCE = new DirectorMgr();
    }
    //</editor-fold>

    private void buildWorld() {
        buildTank();
    }

    public void buildTank() {
        Tank tank = new Tank();
        tank.buildElements(SceneMgr.getInstance().getWorld());
        dynamicObjects.add(tank);
    }

    public void handleGameLoop(Long time) {
        dynamicObjects.stream().forEach((worldShape) -> {
            worldShape.redraw(time);
        });

        partialDynamicObjects.stream().forEach((worldShape) -> {
            worldShape.redraw(time);
        });
        partialDynamicObjects.removeIf((worldShape) -> !worldShape.isValid());
    }

    public void inputEventListener(KeyEvent event, boolean keyPressed) {

        switch (event.getCode()) {
            case SPACE:
                if (keyPressed) {
                    System.out.println("keyPressed->"+ keyPressed);
                    Bullet bullet = new Bullet();
                    bullet.buildElements(SceneMgr.getInstance().getWorld());
                    bullet.shot();
                    
                    partialDynamicObjects.add(bullet);
                    break;
                }


            default:
                if (!dynamicObjects.isEmpty()) {
                    dynamicObjects.stream().forEach(t -> t.handleInput(event, keyPressed));
                }

                if (!partialDynamicObjects.isEmpty()) {
                    partialDynamicObjects.stream().forEach(t -> t.handleInput(event, keyPressed));
                    //partialDynamicObjects.get(ball).handleInput(event, keyPressed);
                }

                break;

        }
    }

}
