package moe.fab.sfacgreader;

import com.jfoenix.controls.JFXToolbar;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class BasePane extends VBox {
    protected JFXToolbar toolbar;
    public BasePane()
    {
        toolbar=new JFXToolbar();
        getChildren().add(toolbar);
    }


    public void onKeyRelease(KeyCode keyCode)
    {

    }

}
