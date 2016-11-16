/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.nebulae.course.world;

import co.com.nebulae.course.world.labs.LaunchableBall;
import co.com.nebulae.course.world.labs.DirectorMgr;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 *
 * @author Sebastian Molano - nebulae.com.co
 */
public class InputMgr {

    //Opt. animation
    private Timeline timeline;
    boolean timelinePlaying = false;

    //SPEED
    double ONE_FRAME = 1.0 / 24.0;
    double DELTA_MULTIPLIER = 200.0;
    double CONTROL_MULTIPLIER = 3;
    double SHIFT_MULTIPLIER = 1;
    double ALT_MULTIPLIER = 1;

    //Mouse state tracking
    double mousePosX;
    double mousePosY;
    double mouseOldX;
    double mouseOldY;
    double mouseDeltaX;
    double mouseDeltaY;

    //<editor-fold defaultstate="collapsed" desc="SINGLETON">
    private InputMgr() {
    }

    public static InputMgr getInstance() {
        return InputMgrHolder.INSTANCE;
    }

    private static class InputMgrHolder {

        private static final InputMgr INSTANCE = new InputMgr();
    }

//</editor-fold>
    public void configureMouseActions(SubScene scene, final Node root) {

        CameraMgr cameraMgr = CameraMgr.getInstance();

        scene.setOnMouseEntered(e -> {
            scene.requestFocus();
        });

        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseOldX = me.getSceneX();
                mouseOldY = me.getSceneY();
            }
        });
        scene.setOnScroll(evt -> {
            double modifier = 1.0;
            double modifierFactor = 0.1;

            if (evt.isControlDown()) {
                modifier = 0.1;
            }
            if (evt.isShiftDown()) {
                modifier = 10.0;
            }
            if (evt.isMetaDown()) {
                cameraMgr.getCameraXform_2_xy_t().t.setX(cameraMgr.getCameraXform_2_xy_t().t.getX() + evt.getDeltaX() * modifierFactor * modifier * 0.3);  // -
                cameraMgr.getCameraXform_2_xy_t().t.setY(cameraMgr.getCameraXform_2_xy_t().t.getY() + evt.getDeltaY() * modifierFactor * modifier * 0.3);  // -
            } else {
                double z = cameraMgr.getCamera().getTranslateZ();
                double newZ = z + evt.getDeltaY() * modifierFactor * modifier;
                cameraMgr.getCamera().setTranslateZ(newZ);
            }

        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                mouseOldX = mousePosX;
                mouseOldY = mousePosY;
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseDeltaX = (mousePosX - mouseOldX);
                mouseDeltaY = (mousePosY - mouseOldY);

                double modifier = 1.0;
                double modifierFactor = 0.1;

                if (me.isControlDown()) {
                    modifier = 0.1;
                }
                if (me.isShiftDown()) {
                    modifier = 10.0;
                }
                if (me.isPrimaryButtonDown()) {
                    cameraMgr.getCameraXform_1_xy_r().ry.setAngle(cameraMgr.getCameraXform_1_xy_r().ry.getAngle() - mouseDeltaX * modifierFactor * modifier * 2.0);  // +
                    cameraMgr.getCameraXform_1_xy_r().rx.setAngle(cameraMgr.getCameraXform_1_xy_r().rx.getAngle() + mouseDeltaY * modifierFactor * modifier * 2.0);  // -
                } else if (me.isSecondaryButtonDown()) {
                    double z = cameraMgr.getCamera().getTranslateZ();
                    double newZ = z + mouseDeltaX * modifierFactor * modifier;
                    cameraMgr.getCamera().setTranslateZ(newZ);
                } else if (me.isMiddleButtonDown()) {
                    cameraMgr.getCameraXform_2_xy_t().t.setX(cameraMgr.getCameraXform_2_xy_t().t.getX() + mouseDeltaX * modifierFactor * modifier * 0.3);  // -
                    cameraMgr.getCameraXform_2_xy_t().t.setY(cameraMgr.getCameraXform_2_xy_t().t.getY() + mouseDeltaY * modifierFactor * modifier * 0.3);  // -
                }
            }
        });
    }

    public void configureKeyboardActions(SubScene scene, final Node root) {

        CameraMgr cameraMgr = CameraMgr.getInstance();
        final boolean moveCamera = true;

        scene.setOnKeyPressed((event) -> {
            DirectorMgr.getInstance().inputEventListener(event, true);
        });

        scene.setOnKeyReleased((event) -> {
            DirectorMgr.getInstance().inputEventListener(event, false);
        });

    }

}
