package Document;

import java.io.Serializable;

public class Record implements Serializable {
    //用户ID
    private int userID;
    //车ID
    private int carID;
    //使用起始时间
    private String startTime;
    //使用结束时间
    private String endTime;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"userID\":")
                .append(userID);
        sb.append(",\"carID\":")
                .append(carID);
        sb.append(",\"startTime\":\"")
                .append(startTime).append('\"');
        sb.append(",\"endTime\":\"")
                .append(endTime).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public Record(int userID, int carID, String startTime, String endTime) {
        this.userID = userID;
        this.carID = carID;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Record() {
    }
}
