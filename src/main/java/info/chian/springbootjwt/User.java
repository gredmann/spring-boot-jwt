package info.chian.springbootjwt;



import java.util.ArrayList;
import java.util.List;

public class User {
    public User() {
    }

    public User(long id,String username, String password,String rols) {
        this.id = id;
        if(this.rols==null){
            this.rols=new ArrayList<>();
        }
        this.rols.add("ROLE_USER");
        this.rols.add(rols);
        this.username = username;
        this.password = password;
    }
    public User(long id,String username, String password) {
        this.id = id;
        if(this.rols==null){
            this.rols=new ArrayList<>();
        }
        this.rols.add("ROLE_ADMIN");
        this.username = username;
        this.password = password;
    }

    private long id;
    private List<String> rols;

    public List<String> getRols() {
        return rols;
    }

    public void setRols(List<String> rols) {
        this.rols = rols;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String username,password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
