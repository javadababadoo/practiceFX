/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.nebulae.course.world;

import co.com.nebulae.course.entity.Xform;
import javafx.event.EventType;
import javafx.geometry.Point3D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 *
 * @author Sebastian Molano - nebulae.com.co
 */
public class MoleculeMgr {

    //dependencies
    private Xform world;

    final Xform elementsGroup = new Xform();

    //<editor-fold defaultstate="collapsed" desc="SINGLETON">
    private MoleculeMgr() {
    }

    public static MoleculeMgr getInstance() {
        return ElementMgrHolder.INSTANCE;
    }

    private static class ElementMgrHolder {

        private static final MoleculeMgr INSTANCE = new MoleculeMgr();
    }

//</editor-fold>
    public void buildElements(Xform world) {
        Log.print("build elements ... ");
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial whiteMaterial = new PhongMaterial();
        whiteMaterial.setDiffuseColor(Color.WHITE);
        whiteMaterial.setSpecularColor(Color.LIGHTBLUE);

        final PhongMaterial greyMaterial = new PhongMaterial();
        greyMaterial.setDiffuseColor(Color.DARKGREY);
        greyMaterial.setSpecularColor(Color.GREY);

        // Molecule Hierarchy
        // [*] moleculeXform
        //     [*] oxygenXform
        //         [*] oxygenSphere
        //     [*] hydrogen1SideXform
        //         [*] hydrogen1Xform
        //             [*] hydrogen1Sphere
        //         [*] bond1Cylinder
        //     [*] hydrogen2SideXform
        //         [*] hydrogen2Xform
        //             [*] hydrogen2Sphere
        //         [*] bond2Cylinder
        Xform moleculeXform = new Xform();
        Xform oxygenXform = new Xform();
        Xform hydrogen1SideXform = new Xform();
        Xform hydrogen1Xform = new Xform();
        Xform hydrogen2SideXform = new Xform();
        Xform hydrogen2Xform = new Xform();

        Sphere oxygenSphere = new Sphere(40.0);
        oxygenSphere.setMaterial(redMaterial);

        Sphere hydrogen1Sphere = new Sphere(30.0);
        hydrogen1Sphere.setMaterial(whiteMaterial);
        hydrogen1Sphere.setTranslateX(0.0);

        final Sphere hydrogen2Sphere = new Sphere(30.0);
        hydrogen2Sphere.setMaterial(whiteMaterial);
        hydrogen2Sphere.setTranslateZ(0.0);

        hydrogen2Sphere.addEventHandler(MouseEvent.MOUSE_CLICKED, evt -> {
            hydrogen2Sphere.setMaterial(hydrogen2Sphere.getMaterial() == redMaterial ? greyMaterial : redMaterial);
        });
        hydrogen2Sphere.setOnMouseClicked(evt -> System.out.println("SET ON MOUSE CLICKED @ " + hydrogen2Sphere));

        Cylinder bond1Cylinder = new Cylinder(5, 100);
        bond1Cylinder.setMaterial(greyMaterial);
        bond1Cylinder.setTranslateX(50.0);
        bond1Cylinder.setRotationAxis(Rotate.Z_AXIS);
        bond1Cylinder.setRotate(90.0);

        Cylinder bond2Cylinder = new Cylinder(5, 100);
        bond2Cylinder.setMaterial(greyMaterial);
        bond2Cylinder.getTransforms().addAll(
                new Translate(50, 0),
                new Rotate(90, Rotate.Z_AXIS)
        );

        moleculeXform.getChildren().add(oxygenXform);
        moleculeXform.getChildren().add(hydrogen1SideXform);
        moleculeXform.getChildren().add(hydrogen2SideXform);
        oxygenXform.getChildren().add(oxygenSphere);
        hydrogen1SideXform.getChildren().add(hydrogen1Xform);
        hydrogen2SideXform.getChildren().add(hydrogen2Xform);
        hydrogen1Xform.getChildren().add(hydrogen1Sphere);
        hydrogen2Xform.getChildren().add(hydrogen2Sphere);
        hydrogen1SideXform.getChildren().add(bond1Cylinder);
        hydrogen2SideXform.getChildren().add(bond2Cylinder);

        hydrogen1Xform.setTx(100.0);
        hydrogen2Xform.setTx(100.0);
        hydrogen2SideXform.setRotateY(104.5);

        elementsGroup.getChildren().add(moleculeXform);

        world.getChildren().addAll(elementsGroup);

        Log.println("ok");
    }

    public Xform getElementsGroup() {
        return elementsGroup;
    }

}
