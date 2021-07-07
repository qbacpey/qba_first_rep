package Service;

public interface IService {
    //调用Dao中的add函数
    void addVideo(String nVideo, double renMoney);
    //调用Dao中的del函数，返回的字符串能直接展示是否删除成功
    String delVideo(String nVideo);
    //调用Dao中scan函数，起转接作用
    String scanVideo();
    //归还DVD函数，根据给定的DVD名称和归还日期，计算能否成功归还DVD;
    //若能成功归还，就会直接返回字符串，展示相关信息
    String backVideo(String nVideo, String date);
    //同上
    String rentVideo(String nVideo, String date);
    //调用DAO中的rentTime函数，起转接作用
    String rentTime();

}
