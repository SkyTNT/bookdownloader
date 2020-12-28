package com.skytnt.cn.Component;

import com.skytnt.cn.UserInfo;
import com.sun.javafx.scene.text.TextSpan;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;


public class CmtPane extends StackPane {
    private HBox hBox1,hBox2,hBox3;
    private VBox vBox1,vBox2;
    private StackPane avatarContainer,vipContainer;
    private ImageView avatar;
    private Text nickName,vipLevel,date,content;
    public CmtPane()
    {
        super();
        hBox1=new HBox();
        hBox2=new HBox();
        hBox3=new HBox();
        vBox1=new VBox();
        vBox2=new VBox();
        avatar=new ImageView();
        avatarContainer=new StackPane(avatar);
        vipContainer=new StackPane();
        nickName=new Text();
        vipLevel=new Text();
        date=new Text();
        content=new Text();

        avatarContainer.setPadding(new Insets(10));
        avatar.setFitWidth(50);
        avatar.setFitHeight(50);
        avatar.setClip(new Circle(25,25,25));

        nickName.setStyle("-fx-font-weight: bold;-fx-font-size: 20px");

        vipLevel.setStyle("-fx-font-size: 20px");
        vipContainer.setPadding(new Insets(0,10,0,10));
        vipContainer.setStyle("-fx-background-color: rgba(0,0,0,0.3);-fx-background-radius: 15,15,15,15");

        date.setStyle("-fx-font-size: 20px");
        date.setFill(Paint.valueOf("#888888"));

        content.setStyle("-fx-font-size: 15px");

        content.wrappingWidthProperty().bind(maxWidthProperty().subtract(20));
        VBox.setMargin(content,new Insets(0,10,0,10));

        vBox2.setMinHeight(60);
        vBox2.setAlignment(Pos.CENTER_LEFT);

        vipContainer.getChildren().addAll(vipLevel);
        hBox2.getChildren().addAll(nickName,vipContainer);
        hBox3.getChildren().addAll(date);
        vBox2.getChildren().addAll(hBox2,hBox3);
        hBox1.getChildren().addAll(avatarContainer,vBox2);
        vBox1.getChildren().addAll(hBox1,content);
        getChildren().addAll(vBox1);

    }

    public void setUser(UserInfo user)
    {
        avatar.setImage(new Image((user.avatar)));
        nickName.setText(user.nickName);
        vipLevel.setText("vip"+user.vipLevel);
    }

    public void setContent(String content_str)
    {
        content.setText(content_str);
    }

    public void setReplyNum(int num)
    {

    }

    public void setFavNum(int num)
    {

    }

    public void setDate(String date_str)
    {
        date.setText(date_str.replaceAll("T"," "));
    }

}
