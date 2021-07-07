package Dao.Impl;

import Dao.Inte.IDao;
import Document.Admin;
import Document.Admin;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
/*
 * 这个类兼有管理者和客服
 */

public class Dao_Manager implements IDao<Admin> {
    //用来储存录像带信息，利用save函数时刻保存到硬盘中
    private static ArrayList<Admin> aData;
    private static ObjectMapper aObjectMapper = new ObjectMapper();

    //初始化内存数据库函数，用来导入本地JSON数据
    static {
        try {
            JavaType type = aObjectMapper.getTypeFactory().
                    constructCollectionType(ArrayList.class, Admin.class);
            aData = aObjectMapper.readValue(Paths.get("Admin.json").toFile(), type);
        } catch (IOException e) {
            aData = new ArrayList<Admin>();
        }
    }

    private static IDao<Admin> aDao_Manager;

    private Dao_Manager() {}

    public static IDao<Admin> get() {
        if (aDao_Manager == null)
            aDao_Manager = new Dao_Manager();
        return aDao_Manager;
    }

    @Override
    public void saveData() {
        try {
            aObjectMapper.writeValue(new File("Admin.json"), aData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String scan() {
        return aData.stream()
                .map(Admin::toString)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    @Override
    public String del(int ID) {
        Optional<Admin> t = this.search(ID);
        if (t.isPresent()) {
            aData.remove(t.get());
            saveData();
            return "删除成功";
        }
        return "删除失败";
    }

    /*
     *根据输入的车ID寻找的管理者
     */
    @Override
    public Optional<Admin> search(int ID) {
        return aData.stream().filter(e -> e.getID() == ID).findAny();
    }

    @Override
    public void add(Admin item) {
        aData.add(item);
    }
}
