package com.skytnt.cn;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    static Api mApi;
    public static void main(String[] args) {

        if (!Util.fileExists("settings.txt"))Util.writeFile("settings.txt","");
        if (!Util.fileExists("books.txt"))Util.writeFile("books.txt","");

        Settings settings=new Settings("settings.txt");
        Api my_api=new Api(settings);
        mApi=my_api;

        if (checkOption(args,"-downloadAll"))
        {
            my_api.getAccountApi().signInfo();
            DownloadListener listener= (progress, total) -> System.out.println("chapter:"+progress+"/"+total);
            ArrayList<BookInfo> bookInfos=my_api.getDownloadedBooks();
            int sz=bookInfos.size();
            for (int i=0;i<sz;i++)
            {
                System.out.println("book:"+(i+1)+"/"+sz);
                BookInfo bookInfo=bookInfos.get(i);
                int lastDownloadChap=bookInfo.lastDownloadChap;
                bookInfo.downloadBook(my_api,lastDownloadChap==-1?bookInfo.lastViewChap:lastDownloadChap,listener);
            }
            System.out.println("下载完成");
            System.exit(0);
        }
        else
        {

            launch(args);
        }

    }
    public static boolean checkOption(String[] args,String option)
    {
        for (String arg:args)
        if (arg.equals(option))
            return true;
        return false;
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("sfacg");
        stage.setWidth(750);
        stage.setHeight(800);
        ContentManager.init(stage);
        if (mApi.isLogin())
        ContentManager.showMainPane(mApi);
        else
        {
            ContentManager.showLoginPane(mApi);
            ContentManager.showMessageDialog("说明","手机和电脑不能同时登录");
        }


    }
}
