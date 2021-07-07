package Video;

public class Video {
    //DVD租借状态
    private boolean state;
    //DVD名称
    private String name;
    //DVD借出日期，初始化的时候是“无借出记录”
    private String data;
    //DVD借出次数
    private int renTime;
    //DVD租金
    private double renMoney;


    public int getRenTime() {
        return renTime;
    }

    public double getRenMoney() {
        return renMoney;
    }

    public void setRenTime() {
        ++this.renTime;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Video(boolean state, String name, String data, double renMoney) {
        this.state = state;
        this.name = "<<" + name + ">>";
        this.data = data;
        this.renMoney = renMoney;
    }

    public Video() {
    }
}
