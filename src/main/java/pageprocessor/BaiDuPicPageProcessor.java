package pageprocessor;

import httpdownloadutils.ImageDownloadUtils;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.File;
import java.util.List;

import static us.codecraft.webmagic.selector.Selectors.xpath;


/**
 * Created by laowang on 16-8-8.
 */
public class BaiDuPicPageProcessor implements PageProcessor {
    private Site site = Site.me().setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36")
            .setSleepTime(1000).setRetryTimes(3);

    private static Logger logger = Logger.getLogger(BaiDuPicPageProcessor.class);

    @Override
    public void process(Page page) {
        System.out.println("=============爬虫开始工作=============");
        //定义抽取信息，并保存信息
        processPicture(page);
        System.out.println("=============爬虫工作结束=============");
        //因为没有考虑下一页，这里不想着添加下一页
    }

    @Override
    public Site getSite() {
        return site;
    }
    private void processPicture(Page page){
        //获取所有满足匹配的url,已经证明获取的不是空
        List<String> url = page.getHtml().xpath("//*[@id=\"head\"]/div/div[@class=\"s_form\"]/div").all();
        int i=0;
        System.out.println(url.size());
        for(String picUrl:url){
            //从每个url中获取图片路径
            String picU = xpath("//*[@id=\"lg\"]/img").selectElement(picUrl).attr("src");
            picU="http://"+picU.substring(2,picU.length());
            logger.info("图片url:"+picU);
            System.out.println(picU);
            //下载路径
            String downloadDir = "F:/pic";
            //文件保存路径
            String filePath = downloadDir + File.separator+"laowang";
            //文件保存类型
            String picType = picU.substring(picU.length()-3);
            System.out.println(filePath);
            try{
                //调用文件下载方法
                ImageDownloadUtils.downLoadImage(picU,filePath,String.valueOf(i),picType);
            }catch (Exception e){
                e.printStackTrace();
            }
            i++;
        }

    }
}
