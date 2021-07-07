package Service.Impl;

import Dao.Impl.Dao_User;
import Dao.Inte.IDao;
import Document.Car;
import Document.User;
import Service.Inte.IService_Cooperator;
import Service.Inte.IService_User;

import java.util.Optional;
import java.util.Scanner;

/*
 * 引用变量有接口就应该存接口
 * 如果有多个静态的初始化数据，就应该在初始化块中完成
 */

public class Service_User implements IService_User {

    //储存User的一个对Dao的调用
    private static IDao<User> aData;
    //储存一个对Service_Car的引用，不直接和Car_Dao交互,初始化的时候，所有的车都属于官方
    private static IService_Cooperator aCooperator;
    //作为读取数据使用
    private static Scanner aScanner;
    //现在正在线上的用户
    private static User tUser;

    static {
            aData = Dao_User.get();
            aCooperator = Service_Cooperator.get();
            aScanner = new Scanner(System.in);
    }

    private static IService_User aService_User;
    private Service_User(){}
    public static IService_User get(){
        if(aService_User==null)
            aService_User = new Service_User();
        return aService_User;
    }

    @Override
    public String res() {
        //初始化用户Id随机指定1-1000
        int tID = (int) (Math.random() * 1000);
        while (aData.search(tID).isPresent())
            tID = (int) (Math.random() * 1000);
        try {
            System.out.println("请输入姓名");
            String tName = aScanner.next();
            System.out.println("请输入手机号：");
            int tPhoneNumber = aScanner.nextInt();
            System.out.println("请输入密码：");
            String tPassWord = aScanner.next();
            System.out.println("请输入身份证号：");
            int tIdentity = aScanner.nextInt();
            System.out.println("请输入注册日期：(yyyy-MM-dd)");
            String tData = aScanner.next();
            System.out.println("请输入余额：");
            double tMoney = aScanner.nextDouble();
            tUser = new User(tID, tName, tPhoneNumber, tPassWord, tIdentity, tData, tMoney);
        } catch (Exception e) {
            tUser = null;
            return "用户格式输出错误";
        }
        return "注册成功,服务端登录信息已更改！\n" + tUser.toString();
    }

    @Override
    public String log() {
        Optional<User> e;

        if (tUser != null) {
            System.out.println("服务器已有名为" + tUser.getName() + "的用户在线,是否使其强制下线?(Y/N)");
            if (aScanner.next().charAt(0) == 'Y') {
                tUser = null;
            } else {
                return "登录失败！";
            }
        }

        try {
            System.out.println("请输入您的ID:");
            if ((e = aData.search(aScanner.nextInt())).isPresent()) {
                System.out.println("请输入您的密码");
                if (e.get().getPassword().equals(aScanner.next())) {
                    tUser = e.get();
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

    @Override
    public String del() {
        return "您没有权限进行此操作";
    }

    @Override
    public String scan() {
        if (tUser == null) return "请先登录";
        return tUser.toString();
    }

    @Override
    public String dosMoney() {
        if (tUser == null) return "请先登录";

        try {
            tUser.setMoney(tUser.getMoney() + aScanner.nextDouble());
            return "存钱成功，您现在的账户余额为:" + tUser.getMoney();
        } catch (Exception e) {
            return "用户输入错误!";
        }
    }

    @Override
    public String rentCar() {
        if (tUser == null) return "请先登录";
        Optional<Car> tCar = aCooperator.searchById(aScanner.nextInt());
        try {
            //根据返回的Optional判断本车是否可以租用
            if (!tCar.isPresent()) return "本车Id不存在";
            //根据车辆的状态判断车辆是否可以租用
            if (!tCar.get().getState().isFree()) return "本车不可租用！";
            //根据用户是否有未归还的车辆判断是否可以租用此车
            if (!tUser.isUsing()) {
                System.out.println("本车的ID为:" + tCar.get().getID());
                System.out.println("本车的使用价格为:" + tCar.get().getMoney());
                System.out.println("是否使用?(Y/N)");
                if (aScanner.next().charAt(0) != 'Y') {
                    return "返回主界面";
                }
                tCar.get().setState(Car.cState.USING);
                tUser.setUsing(true);
                return "租用成功,本车信息为:" + tCar.get().toString();
            } else {
                return new StringBuilder().append("发生错误,原因可能为:\n")
                        .append("1.没有空闲中的车辆\n")
                        .append("2.您有使用中的车辆")
                        .toString();
            }
        } catch (Exception e) {
            return "用户输入错误";
        }
    }

    @Override
    public String backCar() {
        if (tUser == null) return "请先登录";
        Optional<Car> tCar;

        try {
            if (!(tCar = aCooperator.searchById(aScanner.nextInt())).isPresent()) return "查无此车";
            if (tCar.get().getState() != Car.cState.USING) return "本车正在使用中,请重试";

            if (tUser.isUsing()) {
                System.out.println("本车的使用费用为" + tCar.get().getMoney());
                if (tUser.getMoney() >= tCar.get().getMoney()) {
                    tUser.setMoney(tUser.getMoney() - tCar.get().getMoney());
                    tCar.get().setState(Car.cState.FREE);
                    tUser.setUsing(false);
                    return "使用费用已从用户账户中扣除,感谢您的使用";
                } else {
                    return "您的账户余额不足，请先充值！";
                }

            } else {
                return "您没有使用中的车辆";
            }
        } catch (Exception e) {
            return "用户输入错误，请重试";
        }
    }

    @Override
    public String requestForFixing() {
        System.out.println("请输入您想要保修的车辆ID:");
        Optional<Car> tCar;

        try {
            if ((tCar = aCooperator.searchById(aScanner.nextInt())).isPresent()) {
                switch (tCar.get().getState()) {
                    case FIXING:
                        return "本车正在等待回收,请换乘其他车辆";
                    case TRASHED:
                        return "本车已报废,请换乘其他车辆";
                    //当且仅当这辆车被使用中或者空闲的时候才能反馈
                    //无论是那种情况，都会返回报修
                    case USING:
                    case FREE:
                        if (!tCar.get().isAskForFix())
                            tCar.get().setAskForFix(true);
                        return "本车已被报修,感谢您的反馈";
                }
            }
            return "指定车辆不存在,请重试！";
        } catch (Exception e) {
            return "用户输入错误";
        }

    }
}
