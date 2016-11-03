/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.nebulae.course.world;

import co.com.nebulae.course.world.labs.Ball3Mgr;
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
            Duration currentTime;
            switch (event.getCode()) {
                case Z:
                    if (event.isShiftDown()) {
                        cameraMgr.getCameraXform_1_xy_r().ry.setAngle(0.0);
                        cameraMgr.getCameraXform_1_xy_r().rx.setAngle(0.0);
                        cameraMgr.getCamera().setTranslateZ(-300.0);
                    }
                    cameraMgr.getCameraXform_2_xy_t().t.setX(0.0);
                    cameraMgr.getCameraXform_2_xy_t().t.setY(0.0);
                    break;
                case X:
                    if (event.isControlDown()) {
                        if (AxisMgr.getInstance().getAxisGroup().isVisible()) {
                            System.out.println("setVisible(false)");
                            AxisMgr.getInstance().getAxisGroup().setVisible(false);
                        } else {
                            System.out.println("setVisible(true)");
                            AxisMgr.getInstance().getAxisGroup().setVisible(true);
                        }
                    }
                    break;
                case V:
                    if (event.isControlDown()) {
                        if (MoleculeMgr.getInstance().getElementsGroup().isVisible()) {
                            MoleculeMgr.getInstance().getElementsGroup().setVisible(false);
                        } else {
                            MoleculeMgr.getInstance().getElementsGroup().setVisible(true);
                        }
                    }
                    break;
                case SPACE:
//                    Ball2Mgr.getInstance().getOxygenform().t.setY(800);
//                    Ball2Mgr.getInstance().setSpeedY(0d);
//                    Ball2Mgr.getInstance().setGravity(0.00098);
                    Ball3Mgr.getInstance().go();
                    break;
                case W:
                    if (event.isControlDown() && event.isShiftDown()) {
                        cameraMgr.getCameraXform_2_xy_t().t.setY(cameraMgr.getCameraXform_2_xy_t().t.getY() - 10.0 * CONTROL_MULTIPLIER);
                    } else if (event.isAltDown() && event.isShiftDown()) {
                        cameraMgr.getCameraXform_1_xy_r().rx.setAngle(cameraMgr.getCameraXform_1_xy_r().rx.getAngle() - 10.0 * ALT_MULTIPLIER);
                    } else if (event.isControlDown()) {
                        cameraMgr.getCameraXform_2_xy_t().t.setY(cameraMgr.getCameraXform_2_xy_t().t.getY() - 1.0 * CONTROL_MULTIPLIER);
                    } else if (event.isAltDown()) {
                        cameraMgr.getCameraXform_1_xy_r().rx.setAngle(cameraMgr.getCameraXform_1_xy_r().rx.getAngle() - 2.0 * ALT_MULTIPLIER);
                    } else if (event.isShiftDown()) {
                        double z = cameraMgr.getCamera().getTranslateZ();
                        double newZ = z + 5.0 * SHIFT_MULTIPLIER;
                        cameraMgr.getCamera().setTranslateZ(newZ);
                    }
                    break;

                case S:
                    if (event.isControlDown() && event.isShiftDown()) {
                        cameraMgr.getCameraXform_2_xy_t().t.setY(cameraMgr.getCameraXform_2_xy_t().t.getY() + 10.0 * CONTROL_MULTIPLIER);
                    } else if (event.isAltDown() && event.isShiftDown()) {
                        cameraMgr.getCameraXform_1_xy_r().rx.setAngle(cameraMgr.getCameraXform_1_xy_r().rx.getAngle() + 10.0 * ALT_MULTIPLIER);
                    } else if (event.isControlDown()) {
                        cameraMgr.getCameraXform_2_xy_t().t.setY(cameraMgr.getCameraXform_2_xy_t().t.getY() + 1.0 * CONTROL_MULTIPLIER);
                    } else if (event.isAltDown()) {
                        cameraMgr.getCameraXform_1_xy_r().rx.setAngle(cameraMgr.getCameraXform_1_xy_r().rx.getAngle() + 2.0 * ALT_MULTIPLIER);
                    } else if (event.isShiftDown()) {
                        double z = cameraMgr.getCamera().getTranslateZ();
                        double newZ = z - 5.0 * SHIFT_MULTIPLIER;
                        cameraMgr.getCamera().setTranslateZ(newZ);
                    }
                    break;
                case D:
                    if (event.isControlDown() && event.isShiftDown()) {
                        cameraMgr.getCameraXform_2_xy_t().t.setX(cameraMgr.getCameraXform_2_xy_t().t.getX() + 10.0 * CONTROL_MULTIPLIER);
                    } else if (event.isAltDown() && event.isShiftDown()) {
                        cameraMgr.getCameraXform_1_xy_r().ry.setAngle(cameraMgr.getCameraXform_1_xy_r().ry.getAngle() - 10.0 * ALT_MULTIPLIER);
                    } else if (event.isControlDown()) {
                        cameraMgr.getCameraXform_2_xy_t().t.setX(cameraMgr.getCameraXform_2_xy_t().t.getX() + 1.0 * CONTROL_MULTIPLIER);
                    } else if (event.isAltDown()) {
                        cameraMgr.getCameraXform_1_xy_r().ry.setAngle(cameraMgr.getCameraXform_1_xy_r().ry.getAngle() - 2.0 * ALT_MULTIPLIER);
                    }
                    break;
                case A:
                    if (event.isControlDown() && event.isShiftDown()) {
                        cameraMgr.getCameraXform_2_xy_t().t.setX(cameraMgr.getCameraXform_2_xy_t().t.getX() - 10.0 * CONTROL_MULTIPLIER);
                    } else if (event.isAltDown() && event.isShiftDown()) {
                        cameraMgr.getCameraXform_1_xy_r().ry.setAngle(cameraMgr.getCameraXform_1_xy_r().ry.getAngle() + 10.0 * ALT_MULTIPLIER);  // -
                    } else if (event.isControlDown()) {
                        cameraMgr.getCameraXform_2_xy_t().t.setX(cameraMgr.getCameraXform_2_xy_t().t.getX() - 1.0 * CONTROL_MULTIPLIER);
                    } else if (event.isAltDown()) {
                        cameraMgr.getCameraXform_1_xy_r().ry.setAngle(cameraMgr.getCameraXform_1_xy_r().ry.getAngle() + 2.0 * ALT_MULTIPLIER);  // -
                    }
                    break;
                case UP:
                    if (event.isMetaDown()) {
                        AngularBallMgr.getInstance().setDirectionRadio(1d);
                        AngularBallMgr.getInstance().setSpeedRadio(0.1d);
                    } else {
                        BallMgr.getInstance().setDirectionZ(1d);
                        BallMgr.getInstance().setSpeedZ(0.1d);
                    }
                    break;
                case DOWN:
                    if (event.isMetaDown()) {
                        AngularBallMgr.getInstance().setDirectionRadio(-1d);
                        AngularBallMgr.getInstance().setSpeedRadio(0.1d);
                    } else {
                        BallMgr.getInstance().setDirectionZ(-1d);
                        BallMgr.getInstance().setSpeedZ(0.1d);
                    }
                    break;
                case LEFT:
                    if (event.isMetaDown()) {
                        AngularBallMgr.getInstance().setDirectionAngle(1d);
                        AngularBallMgr.getInstance().setSpeedAngle(0.005d);
                    } else {
                        BallMgr.getInstance().setDirectionX(1d);
                        BallMgr.getInstance().setSpeedX(0.1d);
                    }
                    break;
                case RIGHT:
                    if (event.isMetaDown()) {
                        AngularBallMgr.getInstance().setDirectionAngle(-1d);
                        AngularBallMgr.getInstance().setSpeedAngle(0.005d);
                    } else {
                        BallMgr.getInstance().setDirectionX(-1d);
                        BallMgr.getInstance().setSpeedX(0.1d);
                    }
                    break;
            }
        });

        scene.setOnKeyReleased((event) -> {
            System.out.println(event);
            switch (event.getCode()) {
                case UP:
                case DOWN:
//                    AngularBallMgr.getInstance().setDirectionRadio(1d);
//                    AngularBallMgr.getInstance().setSpeedRadio(0d);
                    BallMgr.getInstance().setDirectionZ(1d);
                    BallMgr.getInstance().setSpeedZ(0d);
                    break;
                case LEFT:
                case RIGHT:
//                    AngularBallMgr.getInstance().setDirectionAngle(1d);
//                    AngularBallMgr.getInstance().setSpeedAngle(0d);
                    BallMgr.getInstance().setDirectionX(1d);
                    BallMgr.getInstance().setSpeedX(0d);
                    break;
            }
        });

    }

}
