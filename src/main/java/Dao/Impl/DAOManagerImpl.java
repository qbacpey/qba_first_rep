package Dao.Impl;

import Document.Admin;
import Dao.Inte.IDAO;
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

public class DAOManagerImpl implements IDAO<Admin> {
    //用来储存录像带信息，利用save函数时刻保存到硬盘中
    private static ArrayList<Admin> aData;
    private static ObjectMapper aObjectMapper = new ObjectMapper();
    private static Admin admin;

    //初始化内存数据库函数，用来导入本地JSON数据
    static {
        try {
            JavaType type = aObjectMapper.getTypeFactory().
                    constructCollectionType(ArrayList.class, Admin.class);
            aData = aObjectMapper.readValue(Paths.get("D:\\Note-for-computer-technology\\Java\\JAVAfx_Myself\\Bike\\src\\main\\resources\\Admin.json").toFile(), type);
        } catch (IOException e) {
            aData = new ArrayList<Admin>();
        }
    }

    private static IDAO<Admin> aDao_Manager;

    private DAOManagerImpl() {}

    public static IDAO<Admin> get() {
        if (aDao_Manager == null)
            aDao_Manager = new DAOManagerImpl();
        return aDao_Manager;
    }

    @Override
    public void saveData() {
        try {
            aObjectMapper.writeValue(new File("D:\\Note-for-computer-technology\\Java\\JAVAfx_Myself\\Bike\\src\\main\\resources\\Admin.json"), aData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String scan() {
        return aData.stream()
                .map(Admin::toString)
                .collect(StringBuilder::new
                        , (stringBuilder, s) -> stringBuilder.append(s+"\n")
                        , StringBuilder::append)
                .toString();
    }

    @Override
    public String delete(int ID) {
        Optional<Admin> t = this.getById(ID);
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
    public Optional<Admin> getById(int ID) {
        return aData.stream().filter(e -> e.getID() == ID).findAny();
    }

    @Override
    public void add(Admin item) {
        aData.add(item);
        saveData();
    }
}
