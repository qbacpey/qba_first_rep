package Service.Inte;

import Document.Car;

import java.util.List;
import java.util.Optional;

public interface IService_Cooperator extends IService{

    /*
     * 根据给定ID寻找车辆,不限合作方
     */
    Optional<Car> searchById(int ID);

    /*
     * 根据给定状态寻找车辆,不限运营商
     */
    Optional<Car> searchByState(Car.cState state);
    /*
     * 根据给定ID寻找车辆,如果这个车辆不属于此运营商,那就会返回null
     */
    Optional<Car> searchMatchName(int ID);

    /*
     * 找到所有属于此合作方的车辆
     */
    List<Car> searchAllMatch(Car.cState state);


}
