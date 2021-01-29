package moe.fab.sfacgreader;

import com.jfoenix.controls.*;
import com.jfoenix.svg.SVGGlyph;
import com.jfoenix.svg.SVGGlyphLoader;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReaderPane extends BasePane {
    private Api my_api;
    private BookInfo bookInfo;
    private JFXDrawer drawer;
    private int chapOrderNow;
    private JFXHamburger menu,back;
    private ScrollPane contentScroller, chapsScroller;
    private BorderPane contentContainer;
    private StackPane loadPane;
    private Label title,volumeName;
    private VBox content;
    private VBox chapsList;
    private PageController pageController;
    private SVGGlyph location;
    Vector<JFXRippler> chapterRipplers;
    public ReaderPane(Api api, BookInfo bookInfo)
    {
        super();
        my_api=api;
        this.bookInfo=bookInfo;

        drawer=new JFXDrawer();
        title=new Label("");
        menu=new JFXHamburger();
        back=new JFXHamburger();
        JFXRippler menuR=new JFXRippler(menu);
        JFXRippler backR=new JFXRippler(back);
        chapsList=new VBox();
        contentScroller =new ScrollPane();
        chapsScroller =new ScrollPane();
        contentContainer=new BorderPane();
        content=new VBox();
        loadPane=new StackPane();
        JFXSpinner loadSpinner=new JFXSpinner();
        loadSpinner.setMaxSize(100,100);
        loadPane.getChildren().add(loadSpinner);


        pageController=new PageController();
        pageController.prefWidthProperty().bind(widthProperty());

        chapsScroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        chapsScroller.setFitToWidth(true);
        chapsScroller.setFitToHeight(true);
        chapsScroller.setContent(chapsList);

        contentScroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        contentScroller.setFitToWidth(true);
        contentScroller.setFitToHeight(true);

        content.setPadding(new Insets(20));
        contentScroller.setContent(content);
        contentContainer.setCenter(contentScroller);
        contentContainer.setBottom(pageController);

        location=SVGGlyphLoader.getGlyph("icomoon.svg.location");
        StackPane.setAlignment(location, Pos.CENTER_RIGHT);
        location.setScaleY(-1);
        location.setSize(10);

        getChildren().add(drawer);
        setVgrow(drawer,Priority.ALWAYS);
        drawer.setDirection(JFXDrawer.DrawerDirection.RIGHT);
        drawer.setDefaultDrawerSize(300);
        drawer.setSidePane(chapsScroller);
        drawer.setContent(contentContainer);
        drawer.maxHeightProperty().bind(heightProperty().subtract(toolbar.heightProperty()));
        JFXScrollPane.smoothScrolling(contentScroller);
        JFXScrollPane.smoothScrolling(chapsScroller);

        backR.setMaskType(JFXRippler.RipplerMask.CIRCLE);
        menuR.setMaskType(JFXRippler.RipplerMask.CIRCLE);
        toolbar.setLeftItems(backR,title);
        toolbar.setRightItems(menuR);

        HamburgerBasicCloseTransition closeTransition=new HamburgerBasicCloseTransition(menu);

        drawer.setOnDrawerOpening(event -> {
            closeTransition.setRate(1);
            closeTransition.play();
        });
        drawer.setOnDrawerClosing(event -> {
            closeTransition.setRate(-1);
            closeTransition.play();
        });
        menu.setOnMouseClicked(event -> {
            if(drawer.isClosed())drawer.open();
            else drawer.close();
            });
        back.setOnMouseClicked(event -> {
            ContentManager.back();
            if (drawer.isOpened())drawer.close();
        });

        drawer.setContent(loadPane);
        chapterRipplers=new Vector<>();
        new Thread(() -> {
            if (bookInfo.volumes==null)api.updateBookChapters(bookInfo);
            Platform.runLater(()->{
                updateCatalogue();
                loadChap(bookInfo.lastViewChap==-1?1:bookInfo.chaptersOrder.get(bookInfo.lastViewChap));
                HamburgerBackArrowBasicTransition ht1 = new HamburgerBackArrowBasicTransition(back);
                ht1.setRate(1);
                ht1.play();
            });
        }).start();
    }

    private void updateCatalogue(){
        int volumeIdChange=-1;
        chapsList.getChildren().clear();
        chapterRipplers.clear();
        for (BookInfo.ChapterInfo c:bookInfo.chapters)
        {
            if (volumeIdChange!=c.volumeId)
            {
                Text volumeTitle=new Text(bookInfo.volumes.get(bookInfo.volumesOrder.get(c.volumeId)-1).title);
                volumeTitle.setFill(Paint.valueOf("#999999"));
                chapsList.getChildren().add(volumeTitle);
                volumeIdChange=c.volumeId;
            }
            JFXRippler rippler=new JFXRippler();
            StackPane container=new StackPane();
            HBox container2=new HBox();
            container2.prefWidthProperty().bind(container.widthProperty());
            container.setMaxWidth(270);
            container.setPadding(new Insets(5));
            Text text=new Text(c.title);
            text.setWrappingWidth(260);

            rippler.setControl(container);
            rippler.setOnMouseClicked(event -> {loadChap(c.chapOrder);drawer.close();});

            if (c.isVip)
            {
                SVGGlyph svgGlyph= SVGGlyphLoader.getGlyph(c.needFireMoney>0?"icomoon.svg.lock":"icomoon.svg.unlock");
                svgGlyph.setScaleY(-1);
                svgGlyph.setSize(10);
                container2.getChildren().add(svgGlyph);
                text.setFill(Paint.valueOf("#FF0000"));
            }
            container2.getChildren().add(text);
            container.getChildren().add(container2);
            chapsList.getChildren().add(rippler);
            chapterRipplers.add(rippler);
        }
    }

    private void setChapsListPos()
    {
        double h=chapsList.getHeight()-chapsScroller.getHeight();
        double y=chapterRipplers.get(chapOrderNow-1).getLayoutY();
        double pos=y/h;
        if (Double.isNaN(pos))pos=0;
        if (pos>1)pos=1;
        chapsScroller.setVvalue(pos);
    }

    private void setChapterUnlock(int chapOrder)
    {
        if (!bookInfo.chapters.get(chapOrder-1).isVip)
            return;
        HBox container=(HBox) getChapterRipplerControl(chapOrder).getChildren().get(0);
        SVGGlyph svgGlyph= SVGGlyphLoader.getGlyph("icomoon.svg.unlock");
        svgGlyph.setScaleY(-1);
        svgGlyph.setSize(10);
        container.getChildren().set(0,svgGlyph);
    }

    private StackPane getChapterRipplerControl(int chapOrder)
    {
        return (StackPane) chapterRipplers.get(chapOrder-1).getControl();
    }

    private void setRow(String cnt,int tsukkomiCount,int rowCount)
    {
        cnt+=" ";
        VBox vBox1=new VBox();
        Label tsL=new Label("...");
        StackPane badge=new StackPane(tsL);
        JFXRippler tsR=new JFXRippler(badge);
        StackPane rowContainer=new StackPane();
        tsR.setAlignment(Pos.TOP_RIGHT);
        rowContainer.prefWidthProperty().bind(widthProperty().subtract(30));
        vBox1.prefWidthProperty().bind(rowContainer.widthProperty());
        badge.setMaxSize(25,25);
        if (tsukkomiCount!=0)badge.setStyle("-fx-background-color: #92F8FF;-fx-background-radius: 12px");
        tsR.setMaskType(JFXRippler.RipplerMask.CIRCLE);
        rowContainer.getChildren().addAll(vBox1,tsR);

        Vector<String> imgs=new Vector<>();
        Pattern pattern=Pattern.compile("\\[img=.*?\\].*?\\[/img\\]");
        Matcher matcher= pattern.matcher(cnt);
        while (matcher.find())
        {
            int start,end;
            start=matcher.start();
            end=matcher.end();
            imgs.add(cnt.substring(start,end));
        }
        int p=0;
        for (String sp1:cnt.split("\\[img=.*?\\].*?\\[/img\\]")){
            Text text=new Text(sp1);
            text.setStyle("-fx-font-size: 20px");
            text.wrappingWidthProperty().bind(widthProperty().subtract(80));
            vBox1.getChildren().add(text);
            if (p<imgs.size())
            {
                String str=imgs.elementAt(p);
                int w,h;
                Matcher m=Pattern.compile("(?<=img=).*?(?=,)").matcher(str);
                m.find();
                w=Integer.parseInt(str.substring(m.start(),m.end()));
                m=Pattern.compile("(?<=,).*?(?=])").matcher(str);
                m.find();
                h=Integer.parseInt(str.substring(m.start(),m.end()));
                m=Pattern.compile("(?<=\\])(.*)?(?=\\[)").matcher(str);
                m.find();
                String s=str.substring(m.start(),m.end());
                if (w>getWidth())
                {
                    double scale=(getWidth()-50)/w;
                    w= (int) (w*scale);
                    h= (int) (h*scale);
                }
                ImageView imageView=new ImageView();
                Image image=new Image(s);
                imageView.setImage(image);
                imageView.setFitWidth(w);
                imageView.setFitHeight(h);
                vBox1.getChildren().add(imageView);
            }
            p++;
        }

        tsR.setOnMouseClicked(event -> ContentManager.showTsukkomiPane(my_api,bookInfo.chapters.get(chapOrderNow-1).chapId,rowCount,tsukkomiCount));
        if (tsukkomiCount!=0)tsL.setText(tsukkomiCount+"");
        content.getChildren().add(rowContainer);
    }

    private void setContent(String cnt, HashMap<Integer,Integer> tsukkomiCount)
    {
        cnt=cnt.replaceAll("\r\r","").replaceAll("\r","");
        content.getChildren().clear();
        int rowCount=0;
        for (String row:cnt.split("\n"))
        {
            if (row.equals(""))
                continue;
            rowCount++;
            Integer tsukkomi=tsukkomiCount.get(rowCount);
            //if (tsukkomi!=null)
            //    System.out.println(my_api.getTsukkomiApi().getTsukkomiListByChap(0,bookInfo.chapters.get(chapOrderNow-1).chapId,"vipLevel,avatar,roleName","hot",0,20,rowCount).data);
            setRow(row,tsukkomi==null?0:tsukkomi,rowCount);
        }

    }


    private void loadChap(int chapOrder)
    {

        drawer.setContent(loadPane);
        new Thread(() -> {
            int chapId=bookInfo.chapters.get(chapOrder-1).chapId;
            BookInfo.ChapterInfo chapterInfo=my_api.getChapterDetail(chapId);
            Platform.runLater(()->{
                title.setText(chapterInfo.title);
                drawer.setContent(contentContainer);
                contentScroller.setVvalue(0);
                if (chapOrderNow!=0)
                {
                    getChapterRipplerControl(chapOrderNow).getChildren().remove(location);

                }
                chapOrderNow=chapOrder;
                setContent(chapterInfo.content,chapterInfo.tsukkomiCount);
                setChapterUnlock(chapOrderNow);
                getChapterRipplerControl(chapOrderNow).getChildren().add(location);
                setChapsListPos();
            });
            bookInfo.saveReadInfo(chapId);
            bookInfo.writeChapToFile(chapterInfo);
            my_api.updateNovelVisits(bookInfo.novelId,chapId);
        }).start();


    }

    private void nextChap()
    {
        if (chapOrderNow<bookInfo.chapters.size())
            loadChap(chapOrderNow+1);
        else ContentManager.showMessageDialog("提示","没有更多章节了");
    }

    private void preChap()
    {
        if(chapOrderNow>1)
            loadChap(chapOrderNow-1);
        else ContentManager.showMessageDialog("提示","已经到头了");
    }

    class PageController extends HBox{
        JFXButton pageUP;
        JFXButton pageDown;
        PageController()
        {
            super();
            pageUP=new JFXButton("<");
            pageDown=new JFXButton(">");
            getChildren().addAll(pageUP,pageDown);
            pageUP.setPrefHeight(30);
            pageDown.setPrefHeight(30);
            pageUP.prefWidthProperty().bind(widthProperty().divide(2));
            pageDown.prefWidthProperty().bind(widthProperty().divide(2));
            pageUP.setOnAction(event -> {preChap();});
            pageDown.setOnAction(event -> {nextChap();});
        }
    }

}
