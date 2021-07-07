package DAO;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;


import Video.Video;
import com.fasterxml.jackson.databind.*;

/*
 * 增删查改应该在Dao中完成，数据库不应该包含任何业务逻辑
 */
public class Dao implements IDao{
    //用来储存录像带信息，利用save函数时刻保存到硬盘中
    private static ArrayList<Video> aData;
    private static ObjectMapper aObjectMapper = new ObjectMapper();

    //初始化内存数据库函数，用来导入本地JSON数据
    public void initData() {
        try {
            JavaType type = aObjectMapper.getTypeFactory().
                    constructCollectionType(ArrayList.class, Video.class);
            aData = aObjectMapper.readValue(Paths.get("text.json").toFile(), type);
        } catch (IOException e) {
            aData = new ArrayList<Video>();
        }
    }

    public void saveData() {
        try {
            aObjectMapper.writeValue(new File("text.json"), aData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addVideo(String nVideo, double renMoney) {
        aData.add(new Video(true, nVideo, "无出借记录", renMoney));
        saveData();

    }

    public Optional<Video> searching(String nVideo, boolean rOb) {
        //过滤掉所有不可租借，或者名字不相等的，满足其中一个就可以
        //filter是留下满足条件的元素
        //注意Optional里边储存的是引用，所以能直接修改原来的数据
        if (rOb)
            return aData.stream().filter(t -> t.isState() && t.getName().equals("<<" + nVideo + ">>")).findAny();
        else
            return aData.stream().filter(t -> (!t.isState()) && t.getName().equals("<<" + nVideo + ">>")).findAny();
    }

    public String delVideo(String nVideo) {
        Optional<Video> t = this.searching(nVideo, true);
        if (t.isPresent()) {
            aData.remove(t.get());
            saveData();
            return "删除成功";
        }
        return "删除失败";
    }


    public String scanVideo() {
        return IntStream
                //如果要模拟for-loop，就要先生成range
                //再利用box生成Obj Stream而不是基本数据类型的Stream
                //在利用map将需要遍历的集合与Stream中经过Box的Integer结合起来
                .range(0, aData.size())
                .boxed()
                .map(operand -> String.format("|%-10d%-10s%-10s%14s%n"
                        ,operand + 1
                        ,aData.get(operand).isState() ? "可 借" : "已借出"
                        ,aData.get(operand).getName()
                        ,aData.get(operand).getData()))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    public String rentTime() {

        return aData.stream()
                .sorted(Comparator.comparing(Video::getRenTime).reversed())
                .map((operand) -> String.format("|%-10d%-10s%n", operand.getRenTime(), operand.getName()))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}


