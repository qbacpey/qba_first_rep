package Document;

public class AskForFix {
    //报修的车辆
    private int ID_Car;
    //车辆被报修的次数
    private int time;
    //报修的处理结果
    Car.cState isFixing;

    public int getID_Car() {
        return ID_Car;
    }

    public void setID_Car(int ID_Car) {
        this.ID_Car = ID_Car;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Car.cState getIsFixing() {
        return isFixing;
    }

    public void setIsFixing(Car.cState isFixing) {
        this.isFixing = isFixing;
    }
}
