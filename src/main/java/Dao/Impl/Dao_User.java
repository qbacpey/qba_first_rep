package Dao.Impl;

import Dao.Inte.IDao;
import Document.User;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

public class Dao_User implements IDao<User> {
    //用来储存录像带信息，利用save函数时刻保存到硬盘中
    private static ArrayList<User> aData;
    private static ObjectMapper aObjectMapper = new ObjectMapper();

    //初始化内存数据库函数，用来导入本地JSON数据
    static {
        try {
            JavaType type = aObjectMapper.getTypeFactory().
                    constructCollectionType(ArrayList.class, User.class);
            aData = aObjectMapper.readValue(Paths.get("User.json").toFile(), type);
        } catch (IOException e) {
            aData = new ArrayList<User>();
        }
    }

    private static IDao<User> aDao_User;

    private Dao_User() {
    }

    ;

    public static IDao<User> get() {
        if (aDao_User == null)
            aDao_User = new Dao_User();
        return aDao_User;
    }

    @Override
    public void saveData() {
        try {
            aObjectMapper.writeValue(new File("User.json"), aData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String scan() {
        return aData.stream()
                .map(User::toString)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    @Override
    public String del(int ID) {
        Optional<User> t = this.search(ID);
        if (t.isPresent()) {
            aData.remove(t.get());
            saveData();
            return "删除成功";
        }
        return "删除失败";
    }

    @Override
    public Optional<User> search(int Id) {
        return aData.stream().filter(user -> user.getId() == Id).findAny();
    }

    @Override
    public void add(User item) {
        aData.add(item);
    }
}