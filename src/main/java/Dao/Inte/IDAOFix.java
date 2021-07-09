package Dao.Inte;

import DOJO.AskForFix;
import DOJO.Car;

import java.util.List;

public interface IDAOFix extends IDAO<AskForFix> {

    /*
     * 处理用户反馈,为反馈次数添加1
     */
    void processFeedBack(Car item);

    /*
     * 找到所有属于这个合作方的记录
     */
    List<AskForFix> listCooperatorRecords(int coopId);
}
