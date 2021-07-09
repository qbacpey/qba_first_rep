package DOJO.Member;

import java.io.Serializable;

public class Cooperator extends AbstractMember implements Serializable {
    private String name;
    private String password;
    private int id;

    public Cooperator() {
    }

    public Cooperator(String name, String password, int id) {
        this.name = name;
        this.password = password;
        this.id = id;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"password\":\"")
                .append(password).append('\"');
        sb.append(",\"id\":")
                .append(id);
        sb.append('}');
        return sb.toString();
    }
}
