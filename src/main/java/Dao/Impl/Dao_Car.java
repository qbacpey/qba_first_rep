package Dao.Impl;

import Dao.Inte.IDao_Car;
import Document.Car;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Dao_Car implements IDao_Car {
    //用来储存未经分类的所有车的数据，利用save函数时刻保存到硬盘中
    private static ArrayList<Car> aData;
    //用来储存经过分类的所有车的数据
    private static Map<String, List<Car>> aProcessedData;
    private static ObjectMapper aObjectMapper = new ObjectMapper();

    /*
     * 静态代码块，用来初始化Dao状态
     * 一方面从文件中读取所有数据，将各个不同的车辆按照车辆的合作方进行分类
     */
    static {
        try {
            JavaType type = aObjectMapper.getTypeFactory().
                    constructCollectionType(ArrayList.class, Car.class);
            aData = aObjectMapper.readValue(Paths.get("Car.json").toFile(), type);
            aProcessedData = aData.stream()
                    .collect(Collectors.groupingBy(car -> car.getCooperate().getName()
                            , Collectors.toList()));
        } catch (IOException e) {
            aData = new ArrayList<Car>();
        }
    }

    private static IDao_Car aDao_Car;

    private Dao_Car() {
    }

    ;

    public static IDao_Car get() {
        if (aDao_Car == null)
            aDao_Car = new Dao_Car();
        return aDao_Car;
    }


    @Override
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
                .collect(StringBuilder::new
                        , StringBuilder::append
                        , StringBuilder::append)
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
        return aData.stream()
                .filter(Car -> Car.getID() == Id)
                .findAny();
    }

    @Override
    public List<Car> searchMatchCooWorker(String name) {
        return aProcessedData.get(name);
    }

    @Override
    public Optional<Car> searchAnyMatchState(Car.cState state) {
        return aData.stream()
                .filter(e -> e.getState() == state)
                .findAny();
    }

    @Override
    public List<Car> searchAllMatch(Car.cState state) {
        return aData.stream()
                .filter(e -> e.getState() == state)
                .collect(Collectors.toList());
    }

    @Override
    public void add(Car item) {
        aData.add(item);
    }


    @Override
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
