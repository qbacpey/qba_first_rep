package Service.Inte;

import Document.Car;
import Document.Record;

import java.util.Optional;

public interface IServiceAdmin extends IService{

    /*
     * 将所有的报修信息打印出来
     */
    void scanAllMessage();

    /*
     * 用户用来反馈的方法
    */
    void userFeedBack(Car fCar);

    /*
     *新建用户乘车记录的方法
     */
    void insertUserRecord(Record record);

    /*
     *更新用户乘车记录的方法
     */
    void updateUserRecord(int UserID);

    /*
     * 寻找给定用户所有的使用记录
     */
    Optional<Record> getRecordByUserId(int UserId);
}
