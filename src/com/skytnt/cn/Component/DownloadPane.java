package com.skytnt.cn.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXSpinner;
import com.skytnt.cn.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;

public class DownloadPane extends VBox {
    Api my_api;
    ArrayList<BookInfo> bookInfos;
    ScrollPane scrollPane;
    HashMap<BookInfo,BookCover>bookCoverMap;
    FlowPane flowPane;
    JFXButton downloadAllButton;

    public DownloadPane(Api api)
    {
        super();
        my_api=api;
        downloadAllButton=new JFXButton("一键下载最新");
        scrollPane=new ScrollPane();
        flowPane=new FlowPane();
        bookCoverMap=new HashMap<>();

        downloadAllButton.prefWidthProperty().bind(widthProperty());
        downloadAllButton.setButtonType(JFXButton.ButtonType.RAISED);
        downloadAllButton.setStyle("-fx-font-size:14px;-fx-background-color:WHITE;");

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setContent(flowPane);
        JFXScrollPane.smoothScrolling(scrollPane);

        flowPane.setPadding(new Insets(20));
        flowPane.setHgap(20);
        flowPane.setVgap(20);

        downloadAllButton.setOnAction(event -> {downloadAll();});

        setAlignment(Pos.TOP_CENTER);
        getChildren().addAll(downloadAllButton,scrollPane);

        refresh();
    }
    private void addBookCover(BookInfo bookInfo)
    {
        BookCover book=new BookCover(bookInfo,my_api,BookCover.NORMAL);
        book.setDeleteHandler(event -> {
                    ContentManager.showOptionalDialog("提示", "确认删除？", event1 -> {
                        new Thread(() -> {
                            bookInfo.setIsDownloaded(false);
                            Platform.runLater(this::refresh);
                        }).start();
                    }, null);
                });
        Platform.runLater(()->flowPane.getChildren().add(book));
        bookCoverMap.put(bookInfo,book);

    }

    private void download_noThread(BookInfo bookInfo)
    {
        BookCover bookCover=bookCoverMap.get(bookInfo);
        bookCover.setMaxHeight(bookCover.getHeight());
        JFXSpinner spinner=new JFXSpinner();
        StackPane spinnerStackPane=new StackPane(spinner);
        spinnerStackPane.setStyle("-fx-background-radius: 5 5 5 5; -fx-background-color: rgba(0,0,0,0.4)");
        spinner.getStyleClass().add("materialDesign-blue");
        spinner.setProgress(0);
        spinner.setMaxSize(50,50);
        Platform.runLater(()->{
            bookCover.getChildren().add(spinnerStackPane);
        });

        int lastDownloadChap=bookInfo.lastDownloadChap;

        bookInfo.downloadBook(my_api, lastDownloadChap == -1 ? bookInfo.lastViewChap : lastDownloadChap, new DownloadListener() {
            @Override
            public void onProgressChanged(int progress, int total) {
                System.out.println("正在下载："+progress+"/"+total);
                spinner.setProgress((progress*1.0)/total);
            }
        });
        Platform.runLater(()->{
            bookCover.getChildren().remove(spinnerStackPane);
        });

    }

    private void download(BookInfo bookInfo)
    {
        new Thread(new Runnable() {
        @Override
        public void run() {
            download_noThread(bookInfo);
            System.out.println("下载完成");
            Platform.runLater(()->{ContentManager.showMessageDialog("提示","下载完成");});

        }}).start();
    }

    private void downloadAll()
    {

        Thread thread=new Thread(() -> {
            int sz=bookInfos.size();
            for (int i=0;i<sz;i++)
            {
                System.out.println("book:"+(i+1)+"/"+sz);
                BookInfo bookInfo=bookInfos.get(i);
                download_noThread(bookInfo);
            }
            System.out.println("下载完成");
            Platform.runLater(()->{ContentManager.showMessageDialog("提示","下载完成");});
        });
        thread.start();
    }

    public void addBookToDownload(BookInfo bookInfo)
    {
        bookInfo.setIsDownloaded(true);
        if (bookInfos.indexOf(bookInfo)==-1)
        {
            bookInfos.add(bookInfo);
            bookInfo.saveReadInfo(bookInfo.lastViewChap);
            addBookCover(bookInfo);
        }
        download(bookInfo);

    }

    public void refresh()
    {

        bookCoverMap.clear();
        flowPane.getChildren().clear();
        new Thread(()->{
            bookInfos=my_api.getDownloadedBooks();
            for (BookInfo bookInfo:bookInfos)
                addBookCover(bookInfo);
        }).start();

    }
}
