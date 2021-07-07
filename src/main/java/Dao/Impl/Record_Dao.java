package Dao.Impl;

import Dao.IDao;
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

public class Record_Dao implements IDao<Record> {
    //用来储存录像带信息，利用save函数时刻保存到硬盘中
    private static ArrayList<Record> aData;
    private static ObjectMapper aObjectMapper = new ObjectMapper();

    //初始化内存数据库函数，用来导入本地JSON数据
    public void initData() {
        try {
            JavaType type = aObjectMapper.getTypeFactory().
                    constructCollectionType(ArrayList.class, Record.class);
            aData = aObjectMapper.readValue(Paths.get("Record.json").toFile(), type);
        } catch (IOException e) {
            aData = new ArrayList<Record>();
        }
    }

    public void saveData() {
        try {
            aObjectMapper.writeValue(new File("Record.json"), aData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String scan() {
        return aData.stream()
                .map(Record::toString)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    @Override
    public String del(int ID) {
        Optional<Record> t = this.search(ID);
        if (t.isPresent()) {
            aData.remove(t.get());
            saveData();
            return "删除成功";
        }
        return "删除失败";
    }

    @Override
    public Optional<Record> search(int UserId) {
        return aData.stream().filter(Record -> Record.getUserID() == UserId).findAny();
    }

    public List<Record> searchAllUserRecord(int UserId) {
        return aData.stream().filter(Record -> Record.getUserID() == UserId).collect(Collectors.toList());
    }

    @Override
    public void add(Record item) {
        aData.add(item);
    }
}
