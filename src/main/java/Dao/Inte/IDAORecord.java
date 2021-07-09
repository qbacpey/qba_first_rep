package Dao.Inte;

import Document.Record;

import java.util.List;

public interface IDAORecord extends IDAO<Record> {
    /*
     * 根据给定的UserId找到这个用户的所有使用记录
     */
    List<Record> listUserRecords(int UserId);

    /*
     * 根据给定的CarId找到这个辆车的所有使用记录
     */
    List<Record> listCarRecords(int CarId);

}
