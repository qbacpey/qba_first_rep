package Dao.Impl;

import Dao.Inte.IDao_Record;
import Document.AskForFix;
import Document.Record;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Dao_Record implements IDao_Record {
    //用来储存录像带信息，利用save函数时刻保存到硬盘中
    private static ArrayList<Record> aData_Use;
    private static ObjectMapper aObjectMapper = new ObjectMapper();

    //初始化内存数据库函数，用来导入本地JSON数据
    static {
        try {
            JavaType type = aObjectMapper.getTypeFactory().
                    constructCollectionType(ArrayList.class, Record.class);
            aData_Use = aObjectMapper.readValue(Paths.get("Record.json").toFile(), type);
        } catch (IOException e) {
            aData_Use = new ArrayList<Record>();
        }
    }

    private static IDao_Record aDao_Record;
    private Dao_Record(){};

    public static IDao_Record get(){
        if (aDao_Record==null)
            aDao_Record = new Dao_Record();
        return aDao_Record;
    }

    @Override
    public void saveData() {
        try {
            aObjectMapper.writeValue(new File("Record.json"), aData_Use);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String scan() {
        return aData_Use.stream()
                .map(Record::toString)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    @Override
    public String del(int ID) {
        Optional<Record> t = this.search(ID);
        if (t.isPresent()) {
            aData_Use.remove(t.get());
            saveData();
            return "删除成功";
        }
        return "删除失败";
    }

    /*
     * 相比于找到全部用户，这个search找到一个记录就行了
     */
    @Override
    public Optional<Record> search(int UserId) {
        return aData_Use.stream()
                .filter(Record -> Record.getUserID() == UserId)
                .findAny();
    }

    @Override
    public List<Record> searchAllUserRecord(int UserId) {
        return aData_Use.stream()
                .filter(Record -> Record.getUserID() == UserId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Record> searchAllCarRecord(int CarId) {
        return aData_Use.stream()
                .filter(Record -> Record.getCarID() == CarId)
                .collect(Collectors.toList());
    }


    @Override
    public void add(Record item) {
        aData_Use.add(item);
    }
}
