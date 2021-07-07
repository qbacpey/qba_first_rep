package Dao.Impl;

import Dao.Inte.IDao;
import Document.Cooperator;
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

public class Dao_Cooperator implements IDao<Cooperator> {
    //用来储存录像带信息，利用save函数时刻保存到硬盘中
    private static ArrayList<Cooperator> aData;
    private static ObjectMapper aObjectMapper = new ObjectMapper();

    //初始化内存数据库函数，用来导入本地JSON数据
    static {
        try {
            JavaType type = aObjectMapper.getTypeFactory().
                    constructCollectionType(ArrayList.class, Cooperator.class);
            aData = aObjectMapper.readValue(Paths.get("Cooperator.json").toFile(), type);
        } catch (IOException e) {
            aData = new ArrayList<Cooperator>();
        }
    }

    private static IDao<Cooperator> aDao_Cooperator;

    private Dao_Cooperator() {}

    public static IDao<Cooperator> get() {
        if (aDao_Cooperator == null)
            aDao_Cooperator = new Dao_Cooperator();
        return aDao_Cooperator;
    }

    @Override
    public void saveData() {
        try {
            aObjectMapper.writeValue(new File("Cooperator.json"), aData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String scan() {
        return aData.stream()
                .map(Cooperator::toString)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    @Override
    public String del(int ID) {
        Optional<Cooperator> t = this.search(ID);
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
    public Optional<Cooperator> search(int ID) {
        return aData.stream()
                .filter(e -> e.getID() == ID)
                .findAny();
    }

    @Override
    public void add(Cooperator item) {
        aData.add(item);
    }
}
