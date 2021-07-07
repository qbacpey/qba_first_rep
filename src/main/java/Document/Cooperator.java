package Document;

public class Cooperator {
    private String name;
    private String password;
    private int ID;

    public Cooperator(String name, String password, int ID) {
        this.name = name;
        this.password = password;
        this.ID = ID;
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
