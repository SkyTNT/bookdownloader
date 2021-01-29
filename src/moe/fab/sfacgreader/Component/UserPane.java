package moe.fab.sfacgreader.Component;

import com.jfoenix.effects.JFXDepthManager;
import moe.fab.sfacgreader.Api;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.Timer;
import java.util.TimerTask;

public class UserPane extends VBox {
    Api my_api;
    ImageView avatar;
    Text name,vipLevel,money;
    public UserPane(Api api)
    {
        super();
        my_api=api;
        avatar=new ImageView();
        name=new Text();
        vipLevel=new Text();
        money=new Text();
        avatar.setClip(new Circle(100,100,100));
        name.setStyle("-fx-font-size: 30px;-fx-font-weight: bold");

        setAlignment(Pos.TOP_CENTER);
        getChildren().addAll(avatar,name,vipLevel,money);

        JFXDepthManager.setDepth(avatar,1);

        setAvatar();
        setVipLevel();
        setName();
        setMoney();

        Timer timer=new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                api.updateMoney();
                setMoney();
            }
        },0,3000);
    }

    private void setAvatar()
    {
        try {
            Image image=new Image(my_api.getUserInfo().avatar);

            avatar.setImage(image);
            avatar.setFitWidth(200);
            avatar.setFitHeight(200*image.getHeight()/image.getWidth());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void  setVipLevel()
    {
        vipLevel.setText("vip:"+my_api.getUserInfo().vipLevel);
    }
    private void setName()
    {
        name.setText(my_api.getUserInfo().nickName);
    }
    public void setMoney()
    {
        money.setText("火劵:"+my_api.getUserInfo().money);
    }

}
