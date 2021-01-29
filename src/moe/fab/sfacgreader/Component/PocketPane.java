package moe.fab.sfacgreader.Component;

import com.jfoenix.controls.JFXScrollPane;
import moe.fab.sfacgreader.Api;
import moe.fab.sfacgreader.BookInfo;
import moe.fab.sfacgreader.ContentManager;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

public class PocketPane extends StackPane {
    Api my_api;
    ScrollPane scrollPane;
    FlowPane pane;
    public PocketPane(Api api)
    {
        super();
        my_api=api;
        scrollPane=new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        pane=new FlowPane();

        pane.setAlignment(Pos.CENTER_LEFT);
        pane.setPadding(new Insets(20));
        pane.setHgap(20);
        pane.setVgap(20);
        scrollPane.setContent(pane);
        JFXScrollPane.smoothScrolling(scrollPane);

        getChildren().add(scrollPane);

        refresh();

    }

    public void refresh()
    {
        pane.getChildren().clear();
        new Thread(()->{
            for (BookInfo bookInfo:my_api.getBooks())
                addBookCover(bookInfo);
            Platform.runLater(()->scrollPane.requestLayout());
        }).start();

    }
    private void addBookCover(BookInfo bookInfo)
    {
        BookCover book=new BookCover(bookInfo,my_api,BookCover.NORMAL);
        book.setDeleteHandler(event -> {
            ContentManager.showOptionalDialog("提示","确认删除？", event1 -> {
                new Thread(()->{
                    my_api.deleteBookFromPocket(bookInfo.novelId);
                    my_api.updateBooks();
                    Platform.runLater(()->ContentManager.mainPane.refreshAll());
                }).start();
            },null);


        });
        Platform.runLater(()->pane.getChildren().add(book));

    }

}
