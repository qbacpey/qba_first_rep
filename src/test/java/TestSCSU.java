import Service.Impl.ServiceAdminImpl;
import Service.Impl.ServiceCooperatorImpl;
import Service.Impl.ServiceUserImpl;
import Service.Inte.IServiceAdmin;
import Service.Inte.IServiceCooperator;
import Service.Inte.IServiceUser;

public class TestSCSU {
    static IServiceUser testService_User = ServiceUserImpl.get();
    static IServiceCooperator testService_Cooperator = ServiceCooperatorImpl.get();
    static IServiceAdmin testService_Admin = ServiceAdminImpl.get();

    public static void main(String[] args) {
        TestSCSU TSC = new TestSCSU();
        while (true){
            System.out.println("用户注册");
            System.out.println(testService_User.register());
            testService_Admin.scanAllMessage();
            System.out.println("用户登录");
            System.out.println(testService_User.login());
            System.out.println("用户存钱");
            System.out.println(testService_User.dosMoney());
            System.out.println(testService_User.scan());
            System.out.println("用户借车");
            System.out.println(testService_User.rentCar());
            System.out.println("用户还车");
            System.out.println(testService_User.backCar());
            System.out.println("展示所用用户信息");
            System.out.println(testService_User.scan());
            System.out.println("用户报修");
            System.out.println(testService_User.processFeedBack());
              testService_User = ServiceUserImpl.get();
              testService_Cooperator = ServiceCooperatorImpl.get();
              testService_Admin = ServiceAdminImpl.get();
        }
    }
}
