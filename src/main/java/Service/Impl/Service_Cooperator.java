package Service.Impl;

import Dao.Impl.Dao_Car;
import Dao.Impl.Dao_Cooperator;
import Dao.Impl.Dao_Manager;
import Dao.Impl.Dao_Record;
import Dao.Inte.IDao;
import Dao.Inte.IDao_Car;
import Dao.Inte.IDao_Record;
import Document.Car;
import Document.Cooperator;
import Document.User;
import Service.Inte.IService_Cooperator;
import Service.Inte.IService_User;

import java.util.*;
import java.util.stream.Collectors;
/*
 * 所有车被建造出来的时候都应该有一个总的承包商,也就是官方
 * 每个承包商可以自己注册,投放车辆
 * 回收车辆,维修车辆
 */

public class Service_Cooperator implements IService_Cooperator {

    //现在正在线上的用户
    private static Cooperator tCoop;
    //储存User的一个对Dao的调用
    private static IDao_Car aCar_List;
    //储存一个对Record的Dao的调用
    private static IDao_Record aRecord_List;
    //储存一个对Cooperator相关Dao的引用变量
    private static IDao<Cooperator> aCooperator_List;

    //储存属于此运营商的单车列表
    private static List<Car> aCarList;
    //作为读取数据使用,这个东西应该在
    private static Scanner aScanner;


    static {
        aScanner = new Scanner(System.in);
        aCar_List = Dao_Car.get();
        aRecord_List = Dao_Record.get();
        aCooperator_List = Dao_Cooperator.get();
    }

    private static IService_Cooperator aService_Cooperator;

    private Service_Cooperator() {
    }

    public static IService_Cooperator get() {
        if (aService_Cooperator == null)
            aService_Cooperator = new Service_Cooperator();
        return aService_Cooperator;
    }

    @Override
    public String res() {
        //初始化用户Id随机指定1-1000
        int tID = (int) (Math.random() * 1000);
        while (aCooperator_List.search(tID).isPresent())
            tID = (int) (Math.random() * 1000);
        try {
            System.out.println("请输入姓名");
            String tName = aScanner.next();
            System.out.println("请输入密码：");
            String tPassWord = aScanner.next();
            tCoop = new Cooperator(tName,tPassWord,tID);
            return "注册成功,服务端登录信息已更改！\n" + tCoop.toString();
        } catch (Exception e) {
            tCoop = null;
            return "用户格式输出错误";
        }
    }

    @Override
    public String log() {
        Optional<Cooperator> e;

        if (tCoop != null) {
            System.out.println("服务器已有名为" + tCoop.getName() + "的用户在线,是否使其强制下线?(Y/N)");
            if (aScanner.next().charAt(0) == 'Y') {
                tCoop = null;
            } else {
                return "登录失败！";
            }
        }

        try {
            System.out.println("请输入您的ID:");
            if ((e = aCooperator_List.search(aScanner.nextInt())).isPresent()) {
                System.out.println("请输入您的密码");
                if (e.get().getPassword().equals(aScanner.next())) {
                    tCoop = e.get();
                    aCarList = aCar_List.searchMatchCooWorker(tCoop.getName());
                    return "登录成功,服务端信息已更改！";
                } else {
                    return "密码错误,请重试.";
                }
            } else {
                return "用户ID不存在";
            }
        } catch (Exception exception) {
            return "输入错误";
        }
    }

    /*
     * 删除指定车辆,但实际上这辆车应当被移交给管理员
     */
    @Override
    public String del() {
        return "您没有权限执行此操作";
    }

    /*
     * 返回名下所有车辆
     */
    @Override
    public String scan() {
        return aCarList.stream()
                .map(Car::toString)
                .collect(StringBuilder::new
                        ,StringBuilder::append
                        ,StringBuilder::append)
                .toString();
    }

    @Override
    public Optional<Car> searchById(int ID) {
        return aCar_List.search(ID);
    }

    @Override
    public Optional<Car> searchByState(Car.cState state) {
        return aCar_List.searchAnyMatchState(state);
    }

    /*
     * 寻找指定ID的,属于此运营商的车辆
     */
    @Override
    public Optional<Car> searchMatchName(int ID) {
        Optional<Car> tCar;
        if(!(tCar = aCar_List.search(ID)).isPresent()){
            //这里，可以用Optional.empty代替null
            return Optional.empty();
        }
        return tCar.get().getCooperate().getName().equals(tCoop.getName()) ? tCar : Optional.empty();
    }

    @Override
    public List<Car> searchAllMatch(Car.cState state) {
        return aCar_List.searchAllMatch(state).stream()
                .filter(e -> e.getCooperate().getName().equals(tCoop.getName()))
                .collect(Collectors.toList());
    }
}
