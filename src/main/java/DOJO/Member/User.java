package DOJO.Member;

public class User extends AbstractMember {
    //用户ID
    private int id;
    //用户姓名
    private String name;
    //手机号,11位
    private int phoneNumber;
    //密码
    private String password;
    //身份证号
    private int identify;
    //注册事件
    private String date;
    //余额
    private double money;
    //是否正在使用车
    private boolean using;

    public int getId() {
        return id;
    }

    public boolean isUsing() {
        return using;
    }

    public void setUsing(boolean using) {
        this.using = using;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdentify() {
        return identify;
    }

    public void setIdentify(int identify) {
        this.identify = identify;
    }

    public String getDate() {
        return date;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
                .append(id);
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"phoneNumber\":")
                .append(phoneNumber);
        sb.append(",\"password\":\"")
                .append(password).append('\"');
        sb.append(",\"identify\":")
                .append(identify);
        sb.append(",\"date\":\"")
                .append(date).append('\"');
        sb.append(",\"money\":")
                .append(money);
        sb.append(",\"using\":")
                .append(using);
        sb.append('}');
        return sb.toString();
    }

    public User(int id, String name, int phoneNumber, String password, int identify, String date, double money) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.identify = identify;
        this.date = date;
        this.money = money;
    }


}
