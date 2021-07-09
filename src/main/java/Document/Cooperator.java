package Document;

import java.io.Serializable;

public class Cooperator implements Serializable {
    private String name;
    private String password;
    private int id;

    public Cooperator(String name, String password, int ID) {
        this.name = name;
        this.password = password;
        this.id = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"password\":\"")
                .append(password).append('\"');
        sb.append(",\"ID\":")
                .append(id);
        sb.append('}');
        return sb.toString();
    }

    public Cooperator() {
    }
}
