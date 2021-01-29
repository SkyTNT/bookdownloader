package moe.fab.sfacgreader;

import com.jfoenix.controls.*;
import com.jfoenix.svg.SVGGlyphLoader;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class ContentManager {
    public static MainPane mainPane;
    public static LoginPane loginPane;
    private static Stage stage;
    private static Scene scene;
    private static StackPane content;
    private static Stack<Pane> contentStack;

    public static void init(Stage stg)
    {
        stage=stg;
        content=new StackPane();
        contentStack=new Stack<>();

        JFXDecorator decorator = new JFXDecorator(stage, content);
        decorator.setCustomMaximize(true);
        scene = new Scene(decorator);
        scene.getStylesheets().add(ContentManager.class.getResource("/css/myapp.css").toExternalForm());
        try {
            SVGGlyphLoader.loadGlyphsFont(ContentManager.class.getResourceAsStream("/fonts/icomoon.svg"),"icomoon.svg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        try {
            stage.getIcons().add(new Image("file:launcher.png"));
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        stage.setOnCloseRequest(event -> {
            System.exit(0);
        });
        scene.setOnKeyReleased(event -> {
            if (contentStack!=null)
            {
                Pane pane=contentStack.peek();
                if (pane instanceof BasePane)((BasePane)pane).onKeyRelease(event.getCode());
            }
        });
        stg.show();

    }

    public static ReadOnlyDoubleProperty getWidth()
    {
        return scene.widthProperty();
    }

    public static ReadOnlyDoubleProperty getHeight()
    {
        return scene.heightProperty();
    }

    public static void setContent(Pane pane)
    {
        contentStack.push(pane);
        content.getChildren().clear();
        content.getChildren().add(pane);

    }
    public static void back()
    {
        if (contentStack.size()<=1)
        {
            close();
            return;
        }

        contentStack.pop();
        Pane pane=contentStack.peek();
        content.getChildren().clear();
        content.getChildren().add(pane);
    }
    public static void close()
    {
        stage.close();
    }

    public static void showMainPane(Api api)
    {
        if (mainPane==null)mainPane =new MainPane(api);
        setContent(mainPane);
    }
    public static void showLoginPane(Api api)
    {
        loginPane=new LoginPane(api);
        setContent(loginPane);
    }

    public static void showReaderPane(Api api, BookInfo bookInfo)
    {
        setContent(new ReaderPane(api,bookInfo));
    }
    public static void showTsukkomiPane(Api api,int chapId,int row,int num)
    {
        setContent(new TsukkomiPane(api,chapId,row,num));
    }
    public static void showOptionalDialog(String title, String msg, EventHandler<ActionEvent> yes,EventHandler<ActionEvent> no)
    {
        JFXDialogLayout layout=new JFXDialogLayout();
        layout.setHeading(new Label(title));
        layout.setBody(new Label(msg));
        JFXButton yes_b=new JFXButton("确定");
        JFXButton no_b=new JFXButton("取消");

        yes_b.getStyleClass().add("dialog-accept");
        no_b.getStyleClass().add("dialog-accept");
        layout.setActions(yes_b,no_b);
        JFXDialog dialog=new JFXDialog();
        yes_b.setOnAction(event -> {if (yes!=null)yes.handle(event);dialog.close();});
        no_b.setOnAction(event -> {if (no!=null)no.handle(event);dialog.close();});
        dialog.setContent(layout);
        dialog.show(content);
    }
    public static void showMessageDialog(String title,String msg)
    {
        JFXDialogLayout layout=new JFXDialogLayout();
        layout.setHeading(new Label(title));
        layout.setBody(new Label(msg));
        JFXButton ok=new JFXButton("确定");
        ok.getStyleClass().add("dialog-accept");
        layout.setActions(ok);
        JFXDialog dialog=new JFXDialog();
        ok.setOnAction(event -> {dialog.close();});
        dialog.setContent(layout);
        dialog.show(content);
    }



}
