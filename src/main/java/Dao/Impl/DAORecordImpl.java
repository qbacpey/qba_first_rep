package Dao.Impl;

import Dao.Inte.IDAORecord;
import DOJO.Record;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DAORecordImpl implements IDAORecord {
    //用来储存录像带信息，利用save函数时刻保存到硬盘中
    private static ArrayList<Record> aData_Use;
    private static ObjectMapper aObjectMapper = new ObjectMapper();

    //初始化内存数据库函数，用来导入本地JSON数据
    static {
        try {
            JavaType type = aObjectMapper.getTypeFactory().
                    constructCollectionType(ArrayList.class, Record.class);
            aData_Use = aObjectMapper.readValue(Paths.get("src/main/resources/Record.json").toFile(), type);
        } catch (IOException e) {
            aData_Use = new ArrayList<Record>();
        }
    }

    private static IDAORecord aDao_Record;
    private DAORecordImpl(){};

    public static IDAORecord get(){
        if (aDao_Record==null)
            aDao_Record = new DAORecordImpl();
        return aDao_Record;
    }

    @Override
    public void saveData() {
        try {
            aObjectMapper.writeValue(new File("src/main/resources/Record.json"), aData_Use);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String scan() {
        return aData_Use.stream()
                .map(Record::toString)
                .collect(StringBuilder::new
                        , (stringBuilder, s) -> stringBuilder.append(s+"\n")
                        , StringBuilder::append)
                .toString();
    }

    @Override
    public String delete(int ID) {
        Optional<Record> t = this.getById(ID);
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
    public Optional<Record> getById(int UserId) {
        return aData_Use.stream()
                .filter(Record -> Record.getUserID() == UserId)
                .findAny();
    }

    @Override
    public List<Record> listUserRecords(int UserId) {
        return aData_Use.stream()
                .filter(Record -> Record.getUserID() == UserId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Record> listCarRecords(int CarId) {
        return aData_Use.stream()
                .filter(Record -> Record.getCarID() == CarId)
                .collect(Collectors.toList());
    }


    @Override
    public void add(Record item) {
        aData_Use.add(item);
        saveData();
    }
}
