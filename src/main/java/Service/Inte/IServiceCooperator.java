package Service.Inte;

import Document.Car;

import java.util.List;
import java.util.Optional;

public interface IServiceCooperator extends IService{

    /*
     * 根据给定ID寻找车辆,不限合作方
     */
    Optional<Car> getByIdCar(int ID);

    /*
     * 根据给定状态寻找车辆,不限运营商
     */
    Optional<Car> getByStateCar(Car.cStateEnum state);
    /*
     * 根据给定ID寻找车辆,如果这个车辆不属于此运营商,那就会返回null
     */
    Optional<Car> getMatchNameCar(int ID);

    /*
     * 找到所有属于此合作方的车辆
     */
    List<Car> listAllMatchCooperatorCars(Car.cStateEnum state);

    /*
     * 投放车辆，用户指定要投放多少车辆，在添加车辆之前会提示用户，是否要将FIXING状态的车辆添加到投放的车辆中
     */
    String pushCar();

    /*
     * 处理维修中的车辆,会处理所有关于车辆维修的推送,并依次决定要将他们重新投入使用还是报废
     */
    void processFixing();

    /*
     * 处理报修,决定是忽略报修还是修理
     */
    void processFeedBack();


    /*
     * 返回所有空闲车辆
     */
    String scanAllFree();

}
