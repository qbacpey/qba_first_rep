package Dao.Impl;

import DOJO.Member.AbstractMember;
import Dao.Inte.IDAO;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;

public class DAOMemberImpl<T extends AbstractMember> implements IDAO<T> {
    //用来储存录像带信息，利用save函数时刻保存到硬盘中
    private ArrayList<T> aData;
    private ObjectMapper aObjectMapper = new ObjectMapper();
    private static final String config = "src/main/resources/Member.properties";
    private String dataType;
    Properties props;
    Class<T> qba;

   /*
    * 根据传入的
    */
    private void init() {
        try {
            props = new Properties();
            props.load(new java.io.FileInputStream(config));
            System.out.println();
            JavaType type = aObjectMapper.getTypeFactory().
                    constructCollectionType(ArrayList.class, qba);
            aData = aObjectMapper.readValue(Paths.get(props.getProperty(dataType)).toFile(), type);
        } catch (IOException e) {
            aData = new ArrayList<T>();
        }
    }

    public DAOMemberImpl(String dataType,Class<T> qba) {
        this.dataType = dataType;
        this.qba = qba;
        init();
    }

    ;

    @Override
    public void saveData() {
        try {
            aObjectMapper.writeValue(new File(props.getProperty(dataType)), aData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String scan() {
        return aData.stream()
                .map(T::toString)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    @Override
    public String delete(int ID) {
        Optional<T> t = this.getById(ID);
        if (t.isPresent()) {
            aData.remove(t.get());
            saveData();
            return "删除成功";
        }
        return "删除失败";
    }

    @Override
    public Optional<T> getById(int Id) {
        return aData.stream().filter(user -> user.getId() == Id).findAny();
    }

    @Override
    public void add(T item) {
        aData.add(item);
        saveData();
    }
}