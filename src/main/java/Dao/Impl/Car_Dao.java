package Dao.Impl;

import Dao.IDao;
import Document.Car;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Car_Dao implements IDao<Car> {
    //用来储存录像带信息，利用save函数时刻保存到硬盘中
    private static ArrayList<Car> aData;
    private static ObjectMapper aObjectMapper = new ObjectMapper();

    //初始化内存数据库函数，用来导入本地JSON数据
    public void initData() {
        try {
            JavaType type = aObjectMapper.getTypeFactory().
                    constructCollectionType(ArrayList.class, Car.class);
            aData = aObjectMapper.readValue(Paths.get("Car.json").toFile(), type);
        } catch (IOException e) {
            aData = new ArrayList<Car>();
        }
    }

    public void saveData() {
        try {
            aObjectMapper.writeValue(new File("Car.json"), aData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String scan() {
        return aData.stream()
                .map(Car::toString)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    @Override
    public String del(int ID) {
        Optional<Car> t = this.search(ID);
        if (t.isPresent()) {
            aData.remove(t.get());
            saveData();
            return "删除成功";
        }
        return "删除失败";
    }

    @Override
    public Optional<Car> search(int Id) {
        return aData.stream().filter(Car -> Car.getID() == Id).findAny();
    }

    @Override
    public void add(Car item) {
        aData.add(item);
    }

    /*
     * 此函数能返回一个String，用来展示所有不同状态的汽车的数量
     */

    public String stateTable() {
        Map<Car.cState, Long> temp = aData.stream()
                .collect(Collectors.groupingBy(Car::getState, Collectors.counting()));
        StringBuilder temp1 = new StringBuilder();
        for (Map.Entry<Car.cState, Long> qba : temp.entrySet()) {
            temp1.append(qba.getKey().getChinese() + "的单车数目为:" + qba.getValue());
        }
        return temp1.toString();
    }
}
