package Dao.Inte;

import java.util.Optional;

/*
 * 还是说应该在切实被实现的接口上在加注释？
 */

public interface IDao<T> {

    /*
     * 根据给定的用户ID/车辆ID，删除指定对象
     */
    String del(int ID);

    /*
     *返回字符串，字符串所有车辆的数据
     */

    String scan();

    /*
     * 根据给定的用户ID/车辆ID，寻找指定对象
     * 利用Optional加泛型主要为了排除Null的情况
     */
    Optional<T> search(int Id);

    /*
     * 将指定对象添加到Dao中
     */
    void add(T item);

    /*
     * 每次数据发生改动都会调用此函数保存
     */
    void saveData();

    /*
     * 展示所有Car的信息，不限运营商
     */

}
