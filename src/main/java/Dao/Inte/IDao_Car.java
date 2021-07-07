package Dao.Inte;

import Document.Car;

import java.util.List;
import java.util.Optional;

public interface IDao_Car extends IDao<Car> {

    /*
     * 找到随机一个指定状态的Car利用List的形式组织他们，并返回,不限合作方
     */

    Optional<Car> searchAnyMatchState(Car.cState state);


    /*
     * 找到所有满足指定状态的Car利用List的形式组织他们，并返回，不限合作方
     */
    List<Car> searchAllMatch(Car.cState state);


    /*
     * 此函数能返回一个String，用来展示所有不同状态的汽车的数量
     */
    String stateTable();

    /*
     * 根据给定的单车的合作方的名字，返回其名下的所有车辆，限定合作方
     */
    List<Car> searchMatchCooWorker(String name);

}
