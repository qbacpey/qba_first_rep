package Dao.Impl;

import Dao.Inte.IDAOFix;
import DOJO.AskForFix;
import DOJO.Car;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DAOFixImpl implements IDAOFix {
    //用来储存录像带信息，利用save函数时刻保存到硬盘中
    private static ArrayList<AskForFix> aData;
    private static ObjectMapper aObjectMapper = new ObjectMapper();

    /*
     * 以下部分可以说是一个模板了
     * get方法可以放在顶头的接口里面
     */
    static {
        try {
            JavaType type = aObjectMapper.getTypeFactory().
                    constructCollectionType(ArrayList.class, AskForFix.class);
            aData = aObjectMapper.readValue(Paths.get("src/main/resources/AskForFix.json").toFile(), type);
        } catch (IOException e) {
            aData = new ArrayList<AskForFix>();
        }
    }

    private static DAOFixImpl aDao_Fix;

    private DAOFixImpl() {
    }

    ;


    public static DAOFixImpl get() {
        if (aDao_Fix == null)
            aDao_Fix = new DAOFixImpl();
        return aDao_Fix;
    }

    @Override
    public void saveData() {
        try {
            aObjectMapper.writeValue(new File("src/main/resources/AskForFix.json"), aData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String scan() {
        return aData.stream()
                .map(AskForFix::toString)
                .collect(StringBuilder::new
                        , (stringBuilder, s) -> stringBuilder.append(s+"\n")
                        , StringBuilder::append)
                .toString();
    }

    /*
     * 根据给定的车ID,删除指定记录
     */
    @Override
    public String delete(int ID) {
        Optional<AskForFix> t = this.getById(ID);
        if (t.isPresent()) {
            aData.remove(t.get());
            saveData();
            return "删除成功";
        }
        return "删除失败";
    }

    @Override
    public void add(AskForFix ask){
        aData.add(ask);
        saveData();
    }

    /*
     *根据输入的车ID寻找指定的报修记录
     */
    @Override
    public Optional<AskForFix> getById(int ID) {
        return aData.stream().filter(e -> e.getIdCar() == ID).findAny();
    }

    @Override
    public void processFeedBack(Car item) {
        Optional<AskForFix> tAsk = aData.stream().filter(e -> e.getIdCar() == item.getId()).findAny();
        //集合API的用法，同样利用Collections的API实现而不是简单的For循环
        if (tAsk.isPresent())
            tAsk.get().setIdCar(tAsk.get().getTime() + 1);
        else
            aData.add(new AskForFix(item.getId(),1,item.getCooperator().getId(), Car.cStateEnum.FREE));
        saveData();
    }

    @Override
    public List<AskForFix> listCooperatorRecords(int coopId) {
        return aData.stream()
                .filter(askForFix -> askForFix.getCooperatorId()== coopId)
                .filter(askForFix -> !askForFix.getIsFixing().isFixing())
                .collect(Collectors.toList());
    }
}
