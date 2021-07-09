package DOJO;

import DOJO.Member.Cooperator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;

public class Car implements Serializable {
    //车辆ID
    private int id;
    //投放日期
    private String date;
    //合作方
    @JsonDeserialize(as = Cooperator.class)
    private Cooperator cooperator;
    //日租金
    private double money;
    //车辆状态
    @JsonDeserialize(as = cStateEnum.class)
    private cStateEnum cState;
    //枚举类，用来枚举车辆的四种状态
    private boolean askForFix;


    public enum cStateEnum {
        //空闲
        FREE("空闲") {
            @Override
            public boolean isFree() {
                return true;
            }
        },
        //使用中
        USING("使用中") {
            @Override
            public boolean isUsing() {
                return true;
            }

        },
        //维修中
        FIXING("维修中") {
            @Override
            public boolean isFixing() {
                return true;
            }

        },
        //报废
        TRASHED("报废") {
            @Override
            public boolean isTrashed() {
                return true;
            }

        };

        @JsonValue
        public String getValue() {
            return chinese;
        }

        public String getChinese() {
            return chinese;
        }

        String chinese;

        public boolean isFree() {
            return false;
        }

        public boolean isUsing() {
            return false;
        }

        public boolean isFixing() {
            return false;
        }

        public boolean isTrashed() {
            return false;
        }

        cStateEnum(String name) {
            chinese = name;
        }

        public void setChinese(String chinese) {
            this.chinese = chinese;
        }
    }

    public Car() {
    }

    public Car(int id, String date, Cooperator cooperator, double money, cStateEnum cState, boolean askForFix) {
        this.id = id;
        this.date = date;
        this.cooperator = cooperator;
        this.money = money;
        this.cState = cState;
        this.askForFix = askForFix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Cooperator getCooperator() {
        return cooperator;
    }

    public void setCooperator(Cooperator cooperator) {
        this.cooperator = cooperator;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public cStateEnum getCState() {
        return cState;
    }

    public void setCState(cStateEnum cState) {
        this.cState = cState;
    }

    public boolean isAskForFix() {
        return askForFix;
    }

    public void setAskForFix(boolean askForFix) {
        this.askForFix = askForFix;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
                .append(id);
        sb.append(",\"date\":\"")
                .append(date).append('\"');
        sb.append(",\"cooperator\":")
                .append(cooperator);
        sb.append(",\"money\":")
                .append(money);
        sb.append(",\"cState\":")
                .append(cState);
        sb.append(",\"askForFix\":")
                .append(askForFix);
        sb.append('}');
        return sb.toString();
    }
}
