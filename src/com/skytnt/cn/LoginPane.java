package com.skytnt.cn;

import com.jfoenix.controls.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;


public class LoginPane extends BasePane {
    Api my_api;
    JFXTextField user_field;
    JFXPasswordField pwd_field;
    JFXButton login_btn,register_btn,officialWebsite;
    public LoginPane(Api api)
    {
        super();
        this.my_api=api;

        toolbar.setLeftItems(new Label("登录"));


        HBox line1=new HBox();
        HBox line2=new HBox();
        VBox vBox=new VBox();

        line1.setPadding(new Insets(10));
        line1.setAlignment(Pos.CENTER);
        line2.setPadding(new Insets(10));
        line2.setAlignment(Pos.CENTER);

        user_field=new JFXTextField();
        pwd_field=new JFXPasswordField();

        login_btn=new JFXButton("登录");
        register_btn=new JFXButton("注册");
        officialWebsite=new JFXButton("官网");

        user_field.setPromptText("账号(电话/邮箱)");
        pwd_field.setPromptText("密码");
        login_btn.setButtonType(JFXButton.ButtonType.RAISED);
        login_btn.setStyle("-fx-font-size:14px;-fx-background-color:WHITE;");
        user_field.maxWidthProperty().bind(widthProperty());
        user_field.prefWidthProperty().bind(widthProperty().subtract(100));
        pwd_field.maxWidthProperty().bind(widthProperty());
        pwd_field.prefWidthProperty().bind(widthProperty().subtract(100));
        login_btn.prefWidthProperty().bind(widthProperty().subtract(100));
        line1.getChildren().addAll(user_field);
        line2.getChildren().addAll(pwd_field);

        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.getChildren().addAll(line1,line2,login_btn,register_btn,officialWebsite);
        getChildren().add(vBox);

        login_btn.setOnAction(event -> {
            String user=user_field.getText();
            String pwd=pwd_field.getText();
            if (user.equals("")||pwd.equals(""))
            {
                ContentManager.showMessageDialog("错误","用户名或密码不能为空");
                return;
            }

            ApiResult res=my_api.login(user,pwd);

            if(res.httpCode==200)
            {
                ContentManager.showMainPane(my_api);
                setVisible(false);
            }
            else
                ContentManager.showMessageDialog("错误",res.msg);

        });

        register_btn.setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(URI.create("https://passport.sfacg.com/Register.aspx"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        officialWebsite.setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(URI.create("http://book.sfacg.com/"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
