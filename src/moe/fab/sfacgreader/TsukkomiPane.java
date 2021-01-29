package moe.fab.sfacgreader;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import moe.fab.sfacgreader.Component.CmtPane;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class TsukkomiPane extends BasePane {
    private JFXHamburger back;
    private StackPane main,loadPane;
    private ScrollPane scroller;
    private VBox container;
    private Label title;
    private Api api;
    private int chapId,row,num;
    public TsukkomiPane(Api api,int chapId,int row,int num)
    {
        super();
        this.api=api;
        this.chapId=chapId;
        this.row=row;
        this.num=num;

        back=new JFXHamburger();
        JFXRippler backR=new JFXRippler(back, JFXRippler.RipplerMask.CIRCLE);
        title=new Label("吐槽");
        container=new VBox();
        scroller=new ScrollPane(container);
        JFXSpinner loadSpinner=new JFXSpinner();
        loadSpinner.setMaxSize(100,100);
        loadPane=new StackPane(loadSpinner);
        main=new StackPane(scroller);

        scroller.setFitToWidth(true);
        scroller.setFitToHeight(true);
        JFXScrollPane.smoothScrolling(scroller);

        loadPane.prefHeightProperty().bind(main.heightProperty());

        container.prefWidthProperty().bind(widthProperty().subtract(20));
        main.maxHeightProperty().bind(heightProperty().subtract(toolbar.heightProperty()));
        setVgrow(main, Priority.ALWAYS);


        backR.setOnMouseClicked(event -> ContentManager.back());
        toolbar.setLeftItems(backR,title);

        getChildren().add(main);


        new Thread(()->{
            setTsukkomis();
            Platform.runLater(()-> {
                HamburgerBackArrowBasicTransition ht1 = new HamburgerBackArrowBasicTransition(back);
                ht1.setRate(1);
                ht1.play();
            });
        }).start();

    }

    private void setTsukkomis()
    {
        Platform.runLater(()->{
            container.getChildren().setAll(loadPane);
        });
        BookInfo.Tsukkomi tsukkomi=api.getTsukkomi(chapId,row,num);
        ArrayList<Node> cmtPanes=new ArrayList<>();
        cmtPanes.add(new Separator());
        for(int i=0;i<tsukkomi.count;i++)
        {
            CmtPane cmtPane=new CmtPane();
            cmtPane.setContent(tsukkomi.contents.get(i));
            cmtPane.setUser(tsukkomi.users.get(i));
            cmtPane.setDate(tsukkomi.dates.get(i));
            cmtPane.setFavNum(tsukkomi.favNums.get(i));
            cmtPane.setReplyNum(tsukkomi.replyNums.get(i));
            cmtPane.prefWidthProperty().bind(widthProperty().subtract(20));
            cmtPane.maxWidthProperty().bind(widthProperty().subtract(20));
            cmtPanes.add(cmtPane);
            cmtPanes.add(new Separator());
        }
        Platform.runLater(()->{
            container.getChildren().setAll(cmtPanes);
        });

    }

    public void refresh()
    {
        new Thread(this::setTsukkomis).start();
    }

    @Override
    public void onKeyRelease(KeyCode keyCode) {
        super.onKeyRelease(keyCode);
        if (keyCode==KeyCode.F5)
            refresh();
    }
}
