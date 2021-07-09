package Service.Impl;

import Dao.Impl.*;
import Dao.Inte.IDAO;
import Dao.Inte.IDAOCar;
import Dao.Inte.IDAOFix;
import Dao.Inte.IDAORecord;
import DOJO.AskForFix;
import DOJO.Car;
import DOJO.Member.Cooperator;
import Service.Inte.IServiceCooperator;

import java.util.*;
import java.util.stream.Collectors;
/*
 * 所有车被建造出来的时候都应该有一个总的承包商,也就是官方
 * 每个承包商可以自己注册,投放车辆
 * 回收车辆,维修车辆
 */

public class ServiceCooperatorImpl implements IServiceCooperator {

    //现在正在线上的用户
    private static Cooperator tCoop;
    //储存User的一个对Dao的调用
    private static IDAOCar aCar_List;
    //储存一个对Record的Dao的调用
    private static IDAORecord aRecord_List;
    //储存一个对Cooperator相关Dao的引用变量
    private static IDAO<Cooperator> aCooperator_List;
    //储存一个对IDao_Fix的调用
    private static IDAOFix aFix_List;

    //储存属于此运营商的单车列表
    private static List<Car> aCarList;
    //作为读取数据使用,这个东西应该在
    private static Scanner aScanner;


    static {
        aScanner = new Scanner(System.in);
        aCar_List = DAOCarImpl.get();
        aRecord_List = DAORecordImpl.get();
        aCooperator_List = new DAOMemberImpl<Cooperator>("Cooperator", Cooperator.class);
        aFix_List = DAOFixImpl.get();
    }

    private static IServiceCooperator aService_Cooperator;

    private ServiceCooperatorImpl() {
    }

    public static IServiceCooperator get() {
        if (aService_Cooperator == null)
            aService_Cooperator = new ServiceCooperatorImpl();
        return aService_Cooperator;
    }

    @Override
    public String register() {
        //初始化用户Id随机指定1-1000
        int tID = (int) (Math.random() * 1000);
        while (aCooperator_List.getById(tID).isPresent())
            tID = (int) (Math.random() * 1000);
        try {
            System.out.println("请输入姓名");
            String tName = aScanner.next();
            System.out.println("请输入密码：");
            String tPassWord = aScanner.next();
            tCoop = new Cooperator(tName, tPassWord, tID);
            aCooperator_List.add(tCoop);
            return "注册成功,服务端登录信息已更改！\n" + tCoop.toString();
        } catch (Exception e) {
            tCoop = null;
            return "用户格式输出错误";
        }
    }

    @Override
    public String login() {
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
            if ((e = aCooperator_List.getById(aScanner.nextInt())).isPresent()) {
                System.out.println("请输入您的密码");
                if (e.get().getPassword().equals(aScanner.next())) {
                    tCoop = e.get();
                    aCarList = aCar_List.listCooperatorCars(tCoop.getName());
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
    public String delete() {
        return "您没有权限执行此操作";
    }

    /*
     * 返回名下所有车辆
     */
    @Override
    public String scan() {
        if (tCoop == null) return "请先登录";
        if (aCarList == null) return "名下没有车辆";
        aCarList = aCar_List.listCooperatorCars(tCoop.getName());

        return aCarList.stream()
                .map(Car::toString)
                .collect(StringBuilder::new
                        , StringBuilder::append
                        , StringBuilder::append)
                .toString();
    }

    @Override
    public String scanAllFree() {
        return aCar_List.listAllMatchStateCars(Car.cStateEnum.FREE)
                .stream()
                .map(Car::toString)
                .collect(StringBuilder::new
                        , StringBuilder::append
                        , StringBuilder::append)
                .toString();
    }

    @Override
    public Optional<Car> getByIdCar(int ID) {
        return aCar_List.getById(ID);
    }

    @Override
    public Optional<Car> getByStateCar(Car.cStateEnum state) {
        return aCar_List.getAnyMatchStateCar(state);
    }
    /*
     * 寻找指定ID的,属于此运营商的车辆
     */

    @Override
    public Optional<Car> getMatchNameCar(int ID) {
        Optional<Car> tCar;
        if (!(tCar = aCar_List.getById(ID)).isPresent()) {
            //这里，可以用Optional.empty代替null
            return Optional.empty();
        }
        return tCar.get().getCooperator().getName().equals(tCoop.getName()) ? tCar : Optional.empty();
    }

    @Override
    public List<Car> listAllMatchCooperatorCars(Car.cStateEnum state) {
        return aCar_List.listAllMatchStateCars(state).stream()
                .filter(e -> e.getCooperator().getName().equals(tCoop.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String pushCar() {
        if (tCoop == null) System.out.println("请先登录");
        System.out.println("请更新您要推送的车的数目:");
        try {
            int tChoice = aScanner.nextInt();
            List<Car> tFreeCarList = aCar_List.listAllMatchStateCars(Car.cStateEnum.FREE);
            int freeCar = tFreeCarList.size();
            if (freeCar >= tChoice) {
                System.out.println("您现在已经投放了" + freeCar + "辆车");
                System.out.println("请问你要继续增加投放的车辆吗?(N代表不添加)");
                if (aScanner.next().charAt(0) == 'N')
                    return "您现在已经投放了" + freeCar + "辆车";
                System.out.println("请输入您要增加投放的车辆数目");
                tChoice = aScanner.nextInt();
            }
            for (int i = 0; i < tChoice; i++) {
                int tID = (int) (Math.random() * 1000);
                while (aCar_List.getById(tID).isPresent())
                    tID = (int) (Math.random() * 1000);
                System.out.print("请输入本车租金:");
                Car tCat = new Car(tID, null, tCoop, aScanner.nextDouble(), Car.cStateEnum.FREE,false);
                System.out.println(tCat);
                aCar_List.add(tCat);
            }
            freeCar = freeCar + tChoice;
            return "您现在已经投放了" + freeCar + "辆车";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public void processFeedBack() {
        if (tCoop == null) {
            System.out.println("请先登录");
            return;
        }

        aCarList = aCar_List.listCooperatorCars(tCoop.getName());
        if(aCarList == null){
            System.out.println("名下没有车辆");
            return;
        }

        List<AskForFix> lFix = aFix_List.listCooperatorRecords(tCoop.getId());

        Iterator<AskForFix> tIte = lFix.iterator();
        AskForFix tFix;
        Car tCar;
        System.out.println("下面是车辆信息,及其被报修的次数,选择是否投放使用或者报废车辆");
        try {
            while (tIte.hasNext()) {
                tFix = tIte.next();
                tCar = aCar_List.getById(tFix.getIdCar()).get();
                System.out.println(tCar);
                System.out.println("报修次数:" + tFix.getTime());
                System.out.println("请选择" +
                        "1.忽略报修" +
                        "2.返厂修理" +
                        "3.报废车辆");
                int tChoice = aScanner.nextInt();
                while (tChoice != 1 && tChoice != 2 && tChoice != 3) {
                    System.out.println("输入错误,请重新输入");
                    tChoice = aScanner.nextInt();
                }
                switch (tChoice) {
                    case 1:
                        tCar.setAskForFix(false);
                        break;
                    case 2:
                        tCar.setCState(Car.cStateEnum.FIXING);
                        tCar.setAskForFix(false);
                        tFix.setIsFixing(Car.cStateEnum.FIXING);
                        break;
                    case 3:
                        tCar.setCState(Car.cStateEnum.TRASHED);
                        tCar.setAskForFix(false);
                        tFix.setIsFixing(Car.cStateEnum.TRASHED);
                        break;
                }
            }
            System.out.println("所有用户报修已被处理!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processFixing() {
        if (tCoop == null){
            System.out.println("请先登录");
            return;
        }
        aCarList = aCar_List.listCooperatorCars(tCoop.getName());
        if(aCarList == null){
            System.out.println("名下没有车辆");
            return;
        }
        List<Car> tCarList = aCarList.stream()
                .filter(Car::isAskForFix)
                .filter(car -> car.getCooperator().getId()==tCoop.getId())
                .collect(Collectors.toList());

        Iterator<Car> tIte = tCarList.iterator();
        Car tCar;
        AskForFix tAsk;
        try {
            System.out.println("下面正在修理的车辆,请根据车辆信息,以及被报修的次数,选择是否投放使用或者报废车辆");
            while (tIte.hasNext()) {
                tCar = tIte.next();
                tAsk = aFix_List.getById(tCar.getId()).get();
                System.out.println(tAsk);
                System.out.println("请选择" +
                        "1.投放使用" +
                        "2.继续修理" +
                        "3.报废车辆");
                int tChoice = aScanner.nextInt();
                while (tChoice != 1 && tChoice != 2 && tChoice != 3) {
                    System.out.println("输入错误,请重新输入");
                    tChoice = aScanner.nextInt();
                }
                switch (tChoice) {
                    case 1:
                        tCar.setAskForFix(false);
                        tCar.setCState(Car.cStateEnum.FREE);
                        tAsk.setIsFixing(Car.cStateEnum.FREE);
                        break;
                    case 2:
                        break;
                    case 3:
                        tCar.setCState(Car.cStateEnum.TRASHED);
                        tCar.setAskForFix(false);
                        tAsk.setIsFixing(Car.cStateEnum.TRASHED);
                        break;
                }
            }
            System.out.println("所有正在修理中的车辆已被处理!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
