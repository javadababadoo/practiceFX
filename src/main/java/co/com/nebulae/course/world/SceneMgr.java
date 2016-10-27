/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.nebulae.course.world;

import co.com.nebulae.course.entity.Xform;
import javafx.scene.Group;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 *
 * @author Sebastian Molano - nebulae.com.co
 */
public class SceneMgr {

    private SubScene scene;
    private final Group root = new Group();
    private final Xform world = new Xform();

    //<editor-fold defaultstate="collapsed" desc="SINGLETON">
    private SceneMgr() {
    }

    public static SceneMgr getInstance() {
        return SceneMgrHolder.INSTANCE;
    }

    private static class SceneMgrHolder {

        private static final SceneMgr INSTANCE = new SceneMgr();
    }

//</editor-fold>
    public void buildScene(AnchorPane canvasPane) {
        Log.print("build scene ... ");
        root.getChildren().add(world);
        scene = new SubScene(root, 1024, 768, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.BLACK);

        scene.widthProperty().bind(canvasPane.widthProperty());
        scene.heightProperty().bind(canvasPane.heightProperty());

        canvasPane.getChildren().add(scene);
        AnchorPane.setBottomAnchor(scene, 0d);
        AnchorPane.setTopAnchor(scene, 0d);
        AnchorPane.setLeftAnchor(scene, 0d);
        AnchorPane.setRightAnchor(scene, 0d);
        
        Log.println("ok");
    }

    //<editor-fold defaultstate="collapsed" desc="GETTERS">
    /**
     * @return the scene
     */
    public SubScene getScene() {
        return scene;
    }

    /**
     * @return the root
     */
    public Group getRoot() {
        return root;
    }

    /**
     * @return the world
     */
    public Xform getWorld() {
        return world;
    }

//</editor-fold>
}
