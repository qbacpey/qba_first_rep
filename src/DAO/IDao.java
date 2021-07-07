package DAO;

import Video.Video;

import java.util.Optional;

public interface IDao {
    //初始化内存数据库函数，用来导入本地JSON数据
    void initData();
    //根据给定的录像带名称，删除指定对象
    String delVideo(String nVideo);
    //根据给定的录像带名称及其指定状态，在内存中寻找指定对象
    Optional<Video> searching(String nVideo, boolean rOb);
    //根据给定的录像带名称和录像带租金，添加指定对象
    void addVideo(String nVideo, double renMoney);
    //每次删除，添加，租借，归还录像带都需要调用保存函数
    void saveData();
    //返回字符串，字符串应当有给定的格式
    String scanVideo();
    //返回字符串，字符串用倒序的形式展示租借次数
    String rentTime();


}
