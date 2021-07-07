package Dao;

import java.util.Optional;

public interface IDao<T> {
    //初始化内存数据库函数，用来导入本地JSON数据
    void initData();

    /*
     * 根据给定的用户ID/车辆ID，删除指定对象
     */
    String del(int ID);

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
     *返回字符串，字符串展示相关数据
     */
    String scan();
}
