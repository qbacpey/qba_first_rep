package Service.Impl;

import DOJO.Member.Admin;
import Dao.Impl.*;
import Dao.Inte.IDAO;
import Dao.Inte.IDAOCar;
import Dao.Inte.IDAOFix;
import Dao.Inte.IDAORecord;
import DOJO.Member.Cooperator;
import DOJO.Member.User;
import DOJO.Car;
import DOJO.Record;
import Service.Inte.IServiceAdmin;

import java.util.Optional;

public class ServiceAdminImpl implements IServiceAdmin {

    //引用变量的量级从大到小排序
    private static IDAOFix iDaoFix;
    private static IDAORecord iDao_record;
    private static IDAOCar iDao_car;
    private static IDAO<Cooperator> cooperatorIDAO;
    private static IDAO<User> userIDAO;

    private static Admin admin;

    //静态初始化块
    static {
        iDaoFix = DAOFixImpl.get();
        iDao_record = DAORecordImpl.get();
        iDao_car = DAOCarImpl.get();
        cooperatorIDAO = new DAOMemberImpl<Cooperator>("Cooperator", Cooperator.class);
        userIDAO = new DAOMemberImpl<User>("User", User.class);
    }

    //所有的get,set方法
    public static IDAOFix getIDaoFix() {
        return iDaoFix;
    }

    public static void setIDaoFix(IDAOFix iDaoFix) {
        ServiceAdminImpl.iDaoFix = iDaoFix;
    }

    public static IDAORecord getIDao_record() {
        return iDao_record;
    }

    public static void setIDao_record(IDAORecord iDao_record) {
        ServiceAdminImpl.iDao_record = iDao_record;
    }

    public static IDAOCar getIDao_car() {
        return iDao_car;
    }

    public static void setIDao_car(IDAOCar iDao_car) {
        ServiceAdminImpl.iDao_car = iDao_car;
    }

    public static IDAO<Cooperator> getCooperatorIDaoIDao() {
        return cooperatorIDAO;
    }

    public static void setCooperatorIDaoIDao(IDAO<Cooperator> cooperatorIDAO) {
        ServiceAdminImpl.cooperatorIDAO = cooperatorIDAO;
    }

    public static IDAO<User> getUserIDao() {
        return userIDAO;
    }

    public static void setUserIDao(IDAO<User> userIDAO) {
        ServiceAdminImpl.userIDAO = userIDAO;
    }

    public static IServiceAdmin getAService_Admin() {
        return aService_Admin;
    }

    public static void setAService_Admin(IServiceAdmin aService_Admin) {
        ServiceAdminImpl.aService_Admin = aService_Admin;
    }

    public static Admin getAdmin() {
        return admin;
    }

    public static void setAdmin(Admin admin) {
        ServiceAdminImpl.admin = admin;
    }

    //toString方法

    //单例模式方法
    private static IServiceAdmin aService_Admin;
    private ServiceAdminImpl() {
    }
    public static IServiceAdmin get() {
        if (aService_Admin == null)
            aService_Admin = new ServiceAdminImpl();
        return aService_Admin;
    }


    //自第一层继承而来的方法,按照接口中的顺序排列
    @Override
    public String register() {
        return null;
    }

    @Override
    public String login() {
        return null;
    }

    @Override
    public String delete() {
        return null;
    }

    @Override
    public String scan() {
        return null;
    }

    //自第二层继承而来的方法,按照接口中的顺序排列
    @Override
    public void scanAllMessage() {

        System.out.println("用户");
        System.out.println(userIDAO.scan());
        System.out.println("车辆");
        System.out.println(iDao_car.scan());
        System.out.println("合作方");
        System.out.println(cooperatorIDAO.scan());
        System.out.println("乘车记录");
        System.out.println(iDao_record.scan());
        System.out.println("报修记录");
        System.out.println(iDaoFix.scan());
    }

    /*
     * 用户报修用的函数
     */
    @Override
    public void userFeedBack(Car fCar) {
        iDaoFix.processFeedBack(fCar);
    }

    @Override
    public void insertUserRecord(Record record) {
        iDao_record.add(record);
    }

    @Override
    public void updateUserRecord(int UserID) {
        iDao_record.listUserRecords(UserID).stream()
                .filter(record -> record.getEndTime() == null)
                .findAny()
                .get()
                .setEndTime("Set");
    }

    @Override
    public Optional<Record> getRecordByUserId(int UserId) {
        return iDao_record.listUserRecords(UserId).stream()
                .filter(record -> record.getEndTime() == null)
                .findAny();
    }
}
