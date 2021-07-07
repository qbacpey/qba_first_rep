package Dao.Impl;

import Dao.Inte.IDao;
import Document.AskForFix;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

public class Dao_Fix implements IDao<AskForFix> {
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
            aData = aObjectMapper.readValue(Paths.get("AskForFix.json").toFile(), type);
        } catch (IOException e) {
            aData = new ArrayList<AskForFix>();
        }
    }

    private static IDao<AskForFix> aDao_Fix;
    private Dao_Fix(){};


    public static IDao<AskForFix> get(){
        if (aDao_Fix==null)
            aDao_Fix = new Dao_Fix();
        return aDao_Fix;
    }

    @Override
    public void saveData() {
        try {
            aObjectMapper.writeValue(new File("AskForFix.json"), aData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String scan() {
        return aData.stream()
                .map(AskForFix::toString)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    @Override
    public String del(int ID) {
        Optional<AskForFix> t = this.search(ID);
        if (t.isPresent()) {
            aData.remove(t.get());
            saveData();
            return "删除成功";
        }
        return "删除失败";
    }

    /*
     *根据输入的车ID寻找指定的报修记录
     */
    @Override
    public Optional<AskForFix> search(int ID) {
        return aData.stream().filter(e -> e.getID_Car() == ID).findAny();
    }

    @Override
    public void add(AskForFix item) {
        Optional<AskForFix> tAsk = aData.stream().filter(e -> e.getID_Car() == item.getID_Car()).findAny();
        //集合API的用法，同样利用Collections的API实现而不是简单的For循环
        tAsk.ifPresent(askForFix -> askForFix.setID_Car(askForFix.getTime() + 1));
        aData.add(item);
    }
}
