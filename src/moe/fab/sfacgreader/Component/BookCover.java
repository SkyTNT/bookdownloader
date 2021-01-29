package moe.fab.sfacgreader.Component;

import com.jfoenix.controls.JFXRippler;
import com.jfoenix.effects.JFXDepthManager;
import com.jfoenix.svg.SVGGlyph;
import com.jfoenix.svg.SVGGlyphLoader;
import moe.fab.sfacgreader.Api;
import moe.fab.sfacgreader.BookInfo;
import moe.fab.sfacgreader.BookPane;
import moe.fab.sfacgreader.ContentManager;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class BookCover extends StackPane {
    private BookInfo bookInfo;
    private VBox container,container2;
    private HBox options;
    private SVGGlyph read,info,download,delete,mark;
    private JFXRippler read_r,info_r,download_r,delete_r,mark_r;
    public static int NORMAL=0;
    public static int RECOMMEND=1;
    public BookCover(BookInfo bookInfo, Api api, int type)
    {
        super();
        this.bookInfo=bookInfo;
        container=new VBox();
        container2=new VBox();
        ImageView bookIcon=new ImageView();
        Text name=new Text();
        Image image=new Image(bookInfo.novelCover);
        options=new HBox();

        read=SVGGlyphLoader.getGlyph("icomoon.svg.play");
        info= SVGGlyphLoader.getGlyph("icomoon.svg.info");
        download=SVGGlyphLoader.getGlyph("icomoon.svg.download");
        delete=SVGGlyphLoader.getGlyph("icomoon.svg.close, remove, times");
        mark=SVGGlyphLoader.getGlyph("icomoon.svg.heart");
        read_r=new JFXRippler(new StackPane(read), JFXRippler.RipplerMask.CIRCLE);
        info_r=new JFXRippler(new StackPane(info),JFXRippler.RipplerMask.CIRCLE);
        download_r=new JFXRippler(new StackPane(download),JFXRippler.RipplerMask.CIRCLE);
        delete_r=new JFXRippler(new StackPane(delete),JFXRippler.RipplerMask.CIRCLE);
        mark_r=new JFXRippler(new StackPane(mark),JFXRippler.RipplerMask.CIRCLE);
        bookIcon.setImage(image);
        bookIcon.setFitWidth(150);
        bookIcon.setFitHeight(150*image.getHeight()/image.getWidth());
        Rectangle clip=new Rectangle(bookIcon.getFitWidth(),bookIcon.getFitHeight());
        clip.setArcHeight(10);
        clip.setArcWidth(10);
        bookIcon.setClip(clip);
        name.setTextAlignment(TextAlignment.CENTER);
        name.setText(bookInfo.novelName);
        name.wrappingWidthProperty().bind(bookIcon.fitWidthProperty());
        read.setSize(10);
        info.setSize(10);
        delete.setSize(10);
        download.setSize(10);
        mark.setSize(10);
        read.setScaleY(-1);
        info.setScaleY(-1);
        download.setScaleY(-1);
        delete.setScaleY(-1);
        mark.setScaleY(-1);

        options.setAlignment(Pos.CENTER);
        options.setMinHeight(40);
        setType(type);
        container2.prefHeightProperty().bind(container.heightProperty().subtract(40));
        container2.getChildren().addAll(bookIcon,name);
        container.getChildren().addAll(container2,new Separator(),options);
        JFXDepthManager.setDepth(this,1);
        container.setStyle("-fx-background-radius: 5 5 5 5; -fx-background-color: #FFFFFF" );
        getChildren().addAll(container);

        bookIcon.setOnMouseClicked(event -> ContentManager.showReaderPane(api,bookInfo));
        read_r.setOnMouseClicked(event -> ContentManager.showReaderPane(api,bookInfo));
        download_r.setOnMouseClicked(event -> ContentManager.mainPane.addBookToDownload(bookInfo));
        info_r.setOnMouseClicked(event -> ContentManager.setContent(new BookPane(api,bookInfo)));
        mark_r.setOnMouseClicked(event -> {
            setMaxHeight(getHeight());
            setType(NORMAL);
            new Thread(()-> {
                api.addBookToPocket(bookInfo.novelId);
                Platform.runLater(()->{
                    ContentManager.mainPane.refreshAll();
                    ContentManager.showMessageDialog("提示","收藏成功");
                });
            }).start();
        });
    }

    public void  setType(int type)
    {
        options.getChildren().clear();
        if(type==NORMAL)
        {
            read_r.prefWidthProperty().bind(widthProperty().divide(4));
            info_r.prefWidthProperty().bind(widthProperty().divide(4));
            delete_r.prefWidthProperty().bind(widthProperty().divide(4));
            download_r.prefWidthProperty().bind(widthProperty().divide(4));
            options.getChildren().addAll(read_r,info_r,download_r,delete_r);
        }
        else if (type==RECOMMEND)
        {
            read_r.prefWidthProperty().bind(widthProperty().divide(3));
            info_r.prefWidthProperty().bind(widthProperty().divide(3));
            mark_r.prefWidthProperty().bind(widthProperty().divide(3));
            options.getChildren().addAll(read_r,info_r,mark_r);

        }
    }

    public void setDeleteHandler(EventHandler<? super MouseEvent> value)
    {
        delete_r.setOnMouseClicked(value);
    }

}
