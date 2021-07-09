package Dao.Impl;

import Dao.Inte.IDAOCar;
import DOJO.Car;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class DAOCarImpl implements IDAOCar {
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
            aData = aObjectMapper.readValue(Paths.get("src/main/resources/Car.json").toFile(), type);
            aProcessedData = aData.stream()
                    .collect(Collectors.groupingBy(car -> car.getCooperator().getName()
                            , Collectors.toList()));
        } catch (IOException e) {
            aData = new ArrayList<Car>();
        }
    }

    private static IDAOCar aDao_Car;

    private DAOCarImpl() {
    }

    ;

    public static IDAOCar get() {
        if (aDao_Car == null)
            aDao_Car = new DAOCarImpl();
        return aDao_Car;
    }


    @Override
    public void saveData() {
        try {
            aObjectMapper.writeValue(new File("src/main/resources/Car.json"), aData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String scan() {
        return aData.stream()
                .map(Car::toString)
                .collect(StringBuilder::new
                        , (stringBuilder, s) -> stringBuilder.append(s + "\n")
                        , StringBuilder::append)
                .toString();
    }

    @Override
    public String delete(int ID) {
        Optional<Car> t = this.getById(ID);
        if (t.isPresent()) {
            aData.remove(t.get());
            saveData();
            return "删除成功";
        }
        return "删除失败";
    }

    @Override
    public Optional<Car> getById(int Id) {
        return aData.stream()
                .filter(Car -> Car.getId() == Id)
                .findAny();
    }

    @Override
    public List<Car> listCooperatorCars(String name) {
        return aProcessedData.get(name);
    }

    @Override
    public Optional<Car> getAnyMatchStateCar(Car.cStateEnum state) {
        return aData.stream()
                .filter(e -> e.getCState() == state)
                .findAny();
    }

    @Override
    public List<Car> listAllMatchStateCars(Car.cStateEnum state) {
        return aData.stream()
                .filter(e -> e.getCState() == state)
                .collect(Collectors.toList());
    }

    @Override
    public void add(Car item) {
        aData.add(item);
        saveData();
    }


    @Override
    public String scanTotalStateTable() {
        Map<Car.cStateEnum, Long> temp = aData.stream()
                .collect(Collectors.groupingBy(Car::getCState, Collectors.counting()));
        StringBuilder temp1 = new StringBuilder();
        for (Map.Entry<Car.cStateEnum, Long> qba : temp.entrySet()) {
            temp1.append(qba.getKey().getChinese() + "的单车数目为:" + qba.getValue());
        }
        return temp1.toString();
    }
}
