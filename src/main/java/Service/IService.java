package Service;

public interface IService {
    /*
     * 调用Dao中的add函数
     */
    void add(String nVideo, double renMoney);

    /*
     * 调用Dao中的del函数，返回的字符串能直接展示是否删除成功
     */
    String del(String nVideo);

    /*
     * 调用Dao中scan函数，起转接作用
     */
    String scan();
}
