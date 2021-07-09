package DOJO;

import java.io.Serializable;

public class AskForFix implements Serializable {
    //报修的车辆
    private int idCar;
    //车辆被报修的次数
    private int time;
    //本车所属的合作方
    private int cooperatorId;
    //报修的处理结果
    Car.cStateEnum isFixing;

    public int getIdCar() {
        return idCar;
    }

    public void setIdCar(int idCar) {
        this.idCar = idCar;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Car.cStateEnum getIsFixing() {
        return isFixing;
    }

    public int getCooperatorId() {
        return cooperatorId;
    }

    public void setCooperatorId(int cooperatorId) {
        this.cooperatorId = cooperatorId;
    }

    public void setIsFixing(Car.cStateEnum isFixing) {
        this.isFixing = isFixing;
    }

    public AskForFix(int ID_Car, int time, int cooperatorId, Car.cStateEnum isFixing) {
        this.idCar = ID_Car;
        this.time = time;
        this.cooperatorId = cooperatorId;
        this.isFixing = isFixing;
    }

    public AskForFix() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"ID_Car\":")
                .append(idCar);
        sb.append(",\"time\":")
                .append(time);
        sb.append(",\"name\":\"")
                .append(cooperatorId).append('\"');
        sb.append(",\"isFixing\":")
                .append(isFixing);
        sb.append('}');
        return sb.toString();
    }

}
