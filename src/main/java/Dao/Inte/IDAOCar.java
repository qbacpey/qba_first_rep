package Dao.Inte;

import DOJO.Car;

import java.util.List;
import java.util.Optional;

public interface IDAOCar extends IDAO<Car> {

    /*
     * 找到随机一个指定状态的Car利用List的形式组织他们，并返回,不限合作方
     */

    Optional<Car> getAnyMatchStateCar(Car.cStateEnum state);


    /*
     * 找到所有满足指定状态的Car利用List的形式组织他们，并返回，不限合作方
     */
    List<Car> listAllMatchStateCars(Car.cStateEnum state);


    /*
     * 此函数能返回一个String，用来展示所有不同状态的汽车的数量
     */
    String scanTotalStateTable();

    /*
     * 根据给定的单车的合作方的名字，返回其名下的所有车辆，限定合作方
     */
    List<Car> listCooperatorCars(String name);

}
