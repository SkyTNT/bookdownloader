package moe.fab.sfacgreader;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;



public class BookPane extends BasePane {
    Api my_api;
    VBox container,vBox1;
    HBox hBox2;
    FlowPane flowPane1;
    ScrollPane scrollPane;
    StackPane header;
    ImageView bg;
    JFXHamburger back;
    Label title;
    Text name,charCount,isFinish,authorName,markCount,point,intro,tags,sysTags,lastCap,lastUpdateTime;
    public BookPane(Api api, BookInfo bookInfo)
    {
        super();
        this.my_api=api;
        scrollPane=new ScrollPane();
        container=new VBox();
        vBox1=new VBox();
        flowPane1=new FlowPane();
        hBox2=new HBox();
        header=new StackPane();
        bg=new ImageView();
        back=new JFXHamburger();
        title=new Label();
        name=new Text();
        charCount=new Text();
        isFinish=new Text();
        authorName=new Text();
        markCount=new Text();
        point=new Text();
        intro=new Text();
        tags=new Text();
        sysTags=new Text();
        lastCap=new Text();
        lastUpdateTime=new Text();

        name.setStyle("-fx-font-size: 30px;-fx-font-weight: bold");
        name.setFill(Paint.valueOf("#FFFFFF"));
        flowPane1.getChildren().addAll(charCount,isFinish,point,markCount);
        for (Node node:flowPane1.getChildren())
            ((Text)node).setFill(Paint.valueOf("#FFFFFF"));
        flowPane1.setHgap(20);

        vBox1.setAlignment(Pos.BOTTOM_LEFT);
        vBox1.getChildren().addAll(name,flowPane1);
        header.getChildren().addAll(bg,vBox1);
        header.setAlignment(Pos.BOTTOM_LEFT);
        intro.wrappingWidthProperty().bind(bg.fitWidthProperty());
        hBox2.getChildren().addAll(sysTags,tags);

        container.getChildren().addAll(header,hBox2,authorName,intro,lastCap,lastUpdateTime);

        scrollPane.setContent(container);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.maxHeightProperty().bind(heightProperty().subtract(toolbar.heightProperty()));
        getChildren().addAll(scrollPane);
        JFXScrollPane.smoothScrolling(scrollPane);

        back.setOnMouseClicked(event->ContentManager.back());
        toolbar.setLeftItems(new JFXRippler(back, JFXRippler.RipplerMask.CIRCLE),title);

        new Thread(()->{
            api.updateBookDetail(bookInfo);
            Image image=new Image(bookInfo.bgBanner.equals("")?bookInfo.novelCover:bookInfo.bgBanner);
            Platform.runLater(()->{

                bg.setImage(image);
                bg.fitWidthProperty().bind(ContentManager.getWidth().subtract(20));
                bg.setPreserveRatio(true);
                title.setText(bookInfo.novelName);
                name.setText(bookInfo.novelName);
                charCount.setText(bookInfo.charCount+"字");
                isFinish.setText(bookInfo.isFinish?"已完结":"连载中");
                authorName.setText("作者："+bookInfo.authorName);
                markCount.setText(bookInfo.markCount+"收藏");
                point.setText("评分："+bookInfo.point);
                intro.setText("简介："+bookInfo.intro);
                tags.setText(bookInfo.tags);
                sysTags.setText(bookInfo.sysTags);
                lastCap.setText("最新章节："+bookInfo.lastChap);
                lastUpdateTime.setText(bookInfo.lastUpdateTime);
                HamburgerBackArrowBasicTransition ht1 = new HamburgerBackArrowBasicTransition(back);
                ht1.setRate(1);
                ht1.play();
            });

        }).start();

    }


}
