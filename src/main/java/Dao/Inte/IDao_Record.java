package Dao.Inte;

import Document.Record;

import java.util.List;

public interface IDao_Record extends IDao<Record>{
    /*
     * 根据给定的UserId找到这个用户的所有使用记录
     */
    List<Record> searchAllUserRecord(int UserId);

    /*
     * 根据给定的CarId找到这个辆车的所有使用记录
     */
    List<Record> searchAllCarRecord(int CarId);

}
