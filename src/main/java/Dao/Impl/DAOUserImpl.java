package Dao.Impl;

import Dao.Inte.IDAO;
import Document.User;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

public class DAOUserImpl implements IDAO<User> {
    //用来储存录像带信息，利用save函数时刻保存到硬盘中
    private static ArrayList<User> aData;
    private static ObjectMapper aObjectMapper = new ObjectMapper();

    //初始化内存数据库函数，用来导入本地JSON数据
    static {
        try {
            JavaType type = aObjectMapper.getTypeFactory().
                    constructCollectionType(ArrayList.class, User.class);
            aData = aObjectMapper.readValue(Paths.get("D:\\Note-for-computer-technology\\Java\\JAVAfx_Myself\\Bike\\src\\main\\resources\\User.json").toFile(), type);
        } catch (IOException e) {
            aData = new ArrayList<User>();
        }
    }

    private static IDAO<User> aDAOUserImpl;

    private DAOUserImpl(){}

    ;

    public static IDAO<User> get() {
        if (aDAOUserImpl == null)
            aDAOUserImpl = new DAOUserImpl();
        return aDAOUserImpl;
    }

    @Override
    public void saveData() {
        try {
            aObjectMapper.writeValue(new File("D:\\Note-for-computer-technology\\Java\\JAVAfx_Myself\\Bike\\src\\main\\resources\\User.json"), aData);
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
    public String delete(int ID) {
        Optional<User> t = this.getById(ID);
        if (t.isPresent()) {
            aData.remove(t.get());
            saveData();
            return "删除成功";
        }
        return "删除失败";
    }

    @Override
    public Optional<User> getById(int Id) {
        return aData.stream().filter(user -> user.getId() == Id).findAny();
    }

    @Override
    public void add(User item) {
        aData.add(item);
        saveData();
    }
}