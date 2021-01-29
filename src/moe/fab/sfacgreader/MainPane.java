package moe.fab.sfacgreader;

import com.jfoenix.controls.*;
import moe.fab.sfacgreader.Component.DownloadPane;
import moe.fab.sfacgreader.Component.PocketPane;
import moe.fab.sfacgreader.Component.RecommendPane;
import moe.fab.sfacgreader.Component.UserPane;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;


public class MainPane extends BasePane {
    Api my_api;

    JFXTabPane tabPane;
    Tab tab0,tab1,tab2,tab3;
    PocketPane pocketPane;
    DownloadPane downloadPane;
    RecommendPane recommendPane;
    public MainPane(Api api)
    {
        super();
        this.my_api=api;
        my_api.getBooks();
        my_api.getAccountApi().signInfo();//自动签到
        StackPane main =new StackPane();
        setVgrow(main,Priority.ALWAYS);
        getChildren().add(main);
        JFXHamburger more=new JFXHamburger();
        more.getStyleClass().add("jfx-options-burger");
        StackPane popupContent=new StackPane();
        JFXButton exit=new JFXButton("退出");
        exit.setPrefHeight(40);
        popupContent.getChildren().add(exit);
        exit.prefWidthProperty().bind(popupContent.widthProperty());
        JFXPopup popup=new JFXPopup(popupContent);
        more.setOnMouseClicked(event -> {
            popup.show(more, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT, 0, 60);
        });
        exit.setOnAction(event -> {
            ContentManager.close();
            System.exit(0);
        });


        toolbar.setLeftItems(new Label("主页"));
        JFXRippler moreR=new JFXRippler(more);
        moreR.setMaskType(JFXRippler.RipplerMask.CIRCLE);
        toolbar.setRightItems(moreR);

        tabPane=new JFXTabPane();
        tab0=new Tab("书架");
        tab1=new Tab("推荐");
        tab2=new Tab("下载");
        tab3=new Tab("账号");
        pocketPane=new PocketPane(my_api);
        downloadPane=new DownloadPane(my_api);
        recommendPane=new RecommendPane(my_api);

        tab0.setContent(pocketPane);
        tab1.setContent(recommendPane);
        tab2.setContent(downloadPane);
        tab3.setContent(new UserPane(my_api));

        tabPane.maxHeightProperty().bind(heightProperty().subtract(toolbar.heightProperty()));
        tabPane.getTabs().addAll(tab0,tab1,tab2,tab3);
        main.getChildren().addAll(tabPane);
    }

    @Override
    public void onKeyRelease(KeyCode keyCode) {
        super.onKeyRelease(keyCode);
        if (keyCode==KeyCode.F5)
        {
            int id=tabPane.getSelectionModel().getSelectedIndex();
            if (id==0)
            {
                refreshAll();
            }
            if (id==1)recommendPane.refresh();
            if (id==2)downloadPane.refresh();
        }
    }

    public void addBookToDownload(BookInfo bookInfo)
    {
        downloadPane.addBookToDownload(bookInfo);
        tabPane.getSelectionModel().select(tab2);
    }
    public void refreshAll()
    {
        new Thread(()->{
            my_api.updateBooks();
            Platform.runLater(()->{
                pocketPane.refresh();
                downloadPane.refresh();
            });
        }).start();
    }

    public void showFrame()
    {
        setVisible(true);

    }
}
