package moe.fab.sfacgreader.Component;

import moe.fab.sfacgreader.Api;
import moe.fab.sfacgreader.BookInfo;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

public class RecommendPane extends StackPane {
    Api my_api;
    ScrollPane scrollPane;
    FlowPane pane;
    public RecommendPane(Api api)
    {
        super();
        my_api=api;
        pane=new FlowPane();
        scrollPane=new ScrollPane(pane);

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        pane.setAlignment(Pos.CENTER_LEFT);
        pane.setPadding(new Insets(20));
        pane.setHgap(20);
        pane.setVgap(20);

        getChildren().addAll(scrollPane);
        refresh();
    }
    private void addBookCover(BookInfo bookInfo)
    {
        boolean alreadyMarked=false;
        for (BookInfo bookInfo1:my_api.getBooks())
            if (bookInfo.novelId==bookInfo1.novelId)
            {
                alreadyMarked=true;
                break;
            }
        BookCover book=new BookCover(bookInfo,my_api,alreadyMarked?BookCover.NORMAL:BookCover.RECOMMEND);
        Platform.runLater(()->pane.getChildren().add(book));
    }

    public void refresh()
    {
        pane.getChildren().clear();
        new Thread(()->{
            for (BookInfo bookInfo:my_api.getRecommendBooks())
                addBookCover(bookInfo);
            Platform.runLater(()->scrollPane.requestLayout());
        }).start();

    }

}
