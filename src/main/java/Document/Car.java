package Document;

public class Car {
    //车辆ID
    private int ID;
    //投放日期
    private String date;
    //合作方
    private Cooperator Cooperator;
    //日租金
    private double money;
    //车辆状态
    private cState state;
    //枚举类，用来枚举车辆的四种状态
    private boolean askForFix;

    public enum cState {
        //空闲
        FREE ("空闲"){
            @Override
            public boolean isFree() {
                return true;
            }
        },
        //使用中
        USING ("使用中"){
            @Override
            public boolean isUsing() {
                return true;
            }

        },
        //维修中
        FIXING ("维修中"){
            @Override
            public boolean isFixing() {
                return true;
            }

        },
        //报废
        TRASHED ("报废") {
            @Override
            public boolean isTrashed() {
                return true;
            }

        };

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

        cState(String name) {
            chinese = name;
        }

    }

    public int getID() {
        return ID;
    }

    public boolean isAskForFix() {
        return askForFix;
    }

    public void setAskForFix(boolean askForFix) {
        this.askForFix = askForFix;
    }

    public String getDate() {
        return date;
    }

    public void setCooperate(Cooperator cooperate) {
        this.Cooperator = cooperate;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Cooperator getCooperate() {
        return Cooperator;
    }

    public double getMoney() {
        return money;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public cState getState() {
        return state;
    }

    public void setState(cState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"ID\":")
                .append(ID);
        sb.append(",\"date\":\"")
                .append(date).append('\"');
        sb.append(",\"cooperate\":")
                .append(Cooperator);
        sb.append(",\"state\":")
                .append(state);
        sb.append('}');
        return sb.toString();
    }
}
