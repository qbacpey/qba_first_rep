package Service.Inte;

public interface IService_User extends IService{

    /*
     * 用户向账户中存钱
     */

    String dosMoney();

    /*
     * 用户归还车辆
     */
    String backCar();

    /*
     * 用户借车
     */
    String rentCar();

    /*
     * 用户保修车辆，在进行实际操作之前，要检查一下选项
     * 1.本车是否正在使用中，本车正在使用中就要求用户将此车归还
     * 2.本车是否存在
     */
    String requestForFixing();

}
