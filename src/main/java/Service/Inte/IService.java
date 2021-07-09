package Service.Inte;

public interface IService {
    /*
     * 调用Dao中的add函数
     */
    String register();

    /*
     * 登录函数
     */

    String login();

    /*
     * 调用Dao中的del函数，返回的字符串能直接展示是否删除成功
     */
    String delete();

    /*
     * 调用Dao中scan函数，起转接作用
     */
    String scan();



}
