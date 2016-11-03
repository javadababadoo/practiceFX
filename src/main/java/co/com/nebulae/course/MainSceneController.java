package co.com.nebulae.course;

import co.com.nebulae.course.world.AngularBallMgr;
import co.com.nebulae.course.world.AxisMgr;
import co.com.nebulae.course.world.BallMgr;
import co.com.nebulae.course.world.CameraMgr;
import co.com.nebulae.course.world.GameLoop;
import co.com.nebulae.course.world.InputMgr;
import co.com.nebulae.course.world.Log;
import co.com.nebulae.course.world.PlaneMgr;
import co.com.nebulae.course.world.SceneMgr;
import co.com.nebulae.course.world.labs.Ball3Mgr;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class MainSceneController implements Initializable {

    @FXML
    private AnchorPane canvasPane;
    @FXML
    private TextArea consoleTextArea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Log.setTextArea(consoleTextArea);
        SceneMgr.getInstance().buildScene(canvasPane);
        CameraMgr.getInstance().buildCamera(SceneMgr.getInstance().getScene(), SceneMgr.getInstance().getRoot());
        AxisMgr.getInstance().buildAxes(SceneMgr.getInstance().getWorld());
        PlaneMgr.getInstance().buildPlane(SceneMgr.getInstance().getWorld());
        //MoleculeMgr.getInstance().buildElements(SceneMgr.getInstance().getWorld());
        //BallMgr.getInstance().buildElements(SceneMgr.getInstance().getWorld());
        //Ball2Mgr.getInstance().buildElements(SceneMgr.getInstance().getWorld());
        Ball3Mgr.getInstance().buildElements(SceneMgr.getInstance().getWorld());
        AngularBallMgr.getInstance().buildElements(SceneMgr.getInstance().getWorld());

        InputMgr.getInstance().configureKeyboardActions(SceneMgr.getInstance().getScene(), SceneMgr.getInstance().getWorld());
        InputMgr.getInstance().configureMouseActions(SceneMgr.getInstance().getScene(), SceneMgr.getInstance().getWorld());

        Log.println("initialize... ok");
        
        GameLoop gameLoop = new GameLoop();
        gameLoop.init();
        gameLoop.start();
    }

}
