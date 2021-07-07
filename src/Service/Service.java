package Service;

import DAO.Dao;
import Video.Video;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

/*
 * Service负责业务逻辑
 * 生成Service的时候，调用initData函数,生成aData = Dao作为数据库
 * 增删改查工作会调用aData的add，del，scan等方法直接读取aData中的数据
 */
public class Service implements IService{
    //让这个Service绑定一个Dao
    private static Dao aData;
    //日期格式
    private SimpleDateFormat tempData1 = new SimpleDateFormat("yyyy-MM-dd");

    private void initData() {
        aData = new Dao();
        aData.initData();
    }

    public Service() {
        initData();
    }

    public void addVideo(String nVideo, double renMoney) {
        aData.addVideo(nVideo, renMoney);
    }

    public String delVideo(String nVideo) {
        return aData.delVideo(nVideo);
    }

    public String scanVideo() {
        return aData.scanVideo();
    }

    public String backVideo(String nVideo, String date) {
        Optional<Video> t = aData.searching(nVideo,false);
        if (t.isPresent()) {
            try {
                String tempDate = t.get().getData();
                double tDate = (tempData1.parse(tempDate).getTime() - tempData1.parse(date).getTime());
                if(tDate > 0)throw new ParseException(null,0);
                String d1 = charge(-1*tDate, t.get().getRenMoney());
                t.get().setState(true);
                t.get().setData("");
                aData.saveData();
                return "归还成功\n" + "出借日期：" + tempDate + "\n归还日期：" + date + "\n" + d1;
            } catch (ParseException e) {
                return "您输入的日期有误，请重试";
            }
        }
        return "归还失败.本店没有此录像带或本录像带已经归还";
    }



    public String rentVideo(String nVideo, String date) {
        Optional<Video> t = aData.searching(nVideo,true);
        if (t.isPresent()) {
            t.get().setState(false);
            t.get().setData(date);
            t.get().setRenTime();
            aData.saveData();
            return "借出成功";
        }
        return "借出失败.本店没有此录像带或本录像带已经借出";
    }

    public String rentTime(){
        return aData.rentTime();
    }


    //计算日期和租金函数
    private String charge(double date, double rent) {
        return "您应当给：" + date / (24 * 60 * 1000 * 60) * rent;
    }

}
