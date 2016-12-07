/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.nebulae.course.world.labs;

import co.com.nebulae.course.entity.Xform;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;

/**
 *
 * @author Esteban Zapata
 */
public class Tank implements WorldShape {

    /**
     * Universe
     */
    private Xform world;
    /**
     * Elements group
     */
    private final Xform elementsGroup = new Xform();

    private final PhongMaterial material = new PhongMaterial();

    private Map<KeyCode, Boolean> movement = new HashMap<>();

    //<editor-fold defaultstate="collapsed" desc="TANK">
    private Xform tankForm;

    private Xform canonForm;

    private Box body;

    private Cylinder canon;

    private double width = 200;

    private double height = 100;

    private double depth = 400;

    private Double speedXTank = 0d;

    private Double speedZTank = 0d;

    private Double angleXTank = 0d;
    private Double angleYTank = 0d;
    private Double angleZTank = 0d;

    private Double speed = 0d;

    private Double traveledDistanceX = 0d;
    private Double traveledDistanceZ = 0d;

    private Double directionX = 1d;
    private Double directionZ = 1d;

    //</editor-fold>
    public void buildElements(Xform world) {
        material.setDiffuseColor(Color.DARKGREY);
        material.setSpecularColor(Color.GREY);

        this.world = world;
        tankForm = new Xform();
        canonForm = new Xform();

        body = new Box(width, height, depth);
        body.setMaterial(material);
        tankForm.getChildren().add(body);
        tankForm.t.setY(height / 2);
        tankForm.t.setX(0);
        tankForm.t.setZ(0);

        canon = new Cylinder(width / 4, height * 3);
        canon.setMaterial(material);
        canon.setTranslateY(canon.getHeight() / 2);
        canonForm.getChildren().add(canon);
        canonForm.t.setY(0);
        canonForm.t.setX(0);
        canonForm.t.setZ(0);
        tankForm.getChildren().add(canonForm);

        elementsGroup.getChildren().add(tankForm);
        world.getChildren().addAll(elementsGroup);
        System.out.println("canonForm.t -> " + canonForm.t.getY());
        System.out.println("canon -> " + canon.getTranslateY());
    }

    @Override
    public boolean collide(WorldShape worldShape) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void redraw(Long time) {
        move(time);
    }

    public void move(Long time) {
        traveledDistanceX = (speedXTank * time) * directionX;
        traveledDistanceZ = (speedZTank * time) * directionZ;
        double z = tankForm.t.getZ() + traveledDistanceZ;
        double x = tankForm.t.getX() + traveledDistanceX;

        tankForm.t.setZ(z);
        tankForm.t.setX(x);
        DirectorMgr.getInstance().setPositionXTank(x);
        DirectorMgr.getInstance().setPositionZTank(z);
    }

    @Override
    public void handleInput(KeyEvent keyEvent, boolean keyPressed) {
        switch (keyEvent.getCode()) {
            //TANK
            case W:
            case S:
            case A:
            case D:
                movement.put(keyEvent.getCode(), keyPressed);
                moveForward();
                DirectorMgr.getInstance().setAngleY(canonForm.rx.getAngle() + tankForm.rx.getAngle());
                DirectorMgr.getInstance().setAngleZ(canonForm.ry.getAngle() + tankForm.ry.getAngle());
                DirectorMgr.getInstance().setAngleX(canonForm.ry.getAngle() + tankForm.ry.getAngle());
                break;
        }

        switch (keyEvent.getCode()) {
            //CANON
            case UP:
                canonForm.rx.setAngle(canonForm.rx.getAngle() + 1);
                if (canonForm.rx.getAngle() > 360) {
                    canonForm.rx.setAngle(canonForm.rx.getAngle() - 360);
                }
                changeCanonPosition();
                break;
            case DOWN:
                canonForm.rx.setAngle(canonForm.rx.getAngle() - 1);
                if (canonForm.rx.getAngle() < 0) {
                    canonForm.rx.setAngle(360 + canonForm.rx.getAngle());
                }
                changeCanonPosition();
                break;
            case RIGHT:
                canonForm.ry.setAngle(canonForm.ry.getAngle() - 1);
                if (canonForm.ry.getAngle() < 0) {
                    canonForm.ry.setAngle(360 - canonForm.ry.getAngle());
                }

                changeCanonPosition();
                break;
            case LEFT:
                canonForm.ry.setAngle(canonForm.ry.getAngle() + 1);
                if (canonForm.ry.getAngle() > 360) {
                    canonForm.ry.setAngle(canonForm.ry.getAngle() - 360);
                }
                changeCanonPosition();
                break;
        }
    }

    private void moveForward() {
        speed = 0d;
        speedXTank = 0d;
        speedZTank = 0d;
        Boolean forward = movement.get(KeyCode.W);

        Boolean back = movement.get(KeyCode.S);

        Boolean right = movement.get(KeyCode.D);

        Boolean left = movement.get(KeyCode.A);

        //System.out.println("forward: " + forward + " -- back: " + back + " -- Right: " + right + " -- Left: " + left);
        if (forward != null && forward) {
            speed = 1d;
            directionX = 1d;
            directionZ = 1d;
        }

        if (back != null && back) {
            speed = 1d;
            directionX = -1d;
            directionZ = -1d;
        }

        if (right != null && right) {
            tankForm.ry.setAngle(tankForm.ry.getAngle() - 3);
            if (tankForm.ry.getAngle() < 0) {
                tankForm.ry.setAngle(360 - tankForm.ry.getAngle());
            }
        }

        if (left != null && left) {
            tankForm.ry.setAngle(tankForm.ry.getAngle() + 3);
            if (tankForm.ry.getAngle() > 360) {
                tankForm.ry.setAngle(tankForm.ry.getAngle() - 360);
            }
        }

        //angleYTank = tankForm.rx.getAngle();
        angleZTank = tankForm.ry.getAngle();
        angleXTank = 90 - tankForm.ry.getAngle();

        speedXTank = speed * Math.cos(Math.toRadians(angleXTank));
        speedZTank = speed * Math.cos(Math.toRadians(angleZTank));
    }

//    private void moveForward(boolean keyPressed, Boolean forward) {
//        if (!keyPressed && forward == null) {
//            return;
//        }
//
//        if (!keyPressed) {
//            speed = 0d;
//            speedXTank = 0d;
//            speedZTank = 0d;
//            return;
//        }
//        //angleYTank = tankForm.rx.getAngle();
//        angleZTank = tankForm.ry.getAngle();
//        angleXTank = 90 - tankForm.ry.getAngle();
//
//        speed = forward == null ? speed : 1d;
//        speedXTank = speed * Math.cos(Math.toRadians(angleXTank));
//        speedZTank = speed * Math.cos(Math.toRadians(angleZTank));
//        directionX = forward != null ? (forward ? 1d : -1d) : directionX;
//        directionZ = forward != null ? (forward ? 1d : -1d) : directionX;
//    }
    /**
     *
     */
    private void changeCanonPosition() {
        canon.setTranslateY(canon.getHeight() / 2);

        DirectorMgr.getInstance().setAngleY(canonForm.rx.getAngle() + tankForm.rx.getAngle());
        DirectorMgr.getInstance().setAngleZ(canonForm.ry.getAngle() + tankForm.ry.getAngle());
        DirectorMgr.getInstance().setAngleX(canonForm.ry.getAngle() + tankForm.ry.getAngle());
    }

}
