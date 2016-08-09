package test;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import pageprocessor.BaiDuPicPageProcessor;
import us.codecraft.webmagic.Spider;
/**
 * Created by laowang on 16-8-8.
 */
public class BaiduPicTest {
    private static Logger logger = Logger.getLogger(BaiduPicTest.class);
    public static void main(String args[]){
        PropertyConfigurator.configure(ClassLoader.getSystemResourceAsStream("log4j.properties"));
        Spider.create(new BaiDuPicPageProcessor())
                .addUrl("https://www.baidu.com/")
                .thread(3)
                .run();
    }
}
