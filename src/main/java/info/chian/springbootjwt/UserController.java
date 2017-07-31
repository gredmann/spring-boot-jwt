package info.chian.springbootjwt;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @RequestMapping("/auth2")
    public List<String> getListHello(){
        List<String> list=new ArrayList<>();
        char s;
        for (int i = 0; i < 10; i++) {
            s=(char)('A'+i);
            list.add("auth2 Hello-"+s);
        }
        return list;
    }
    @RequestMapping("/list")
    public List<String> getList(){
        List<String> list=new ArrayList<>();
        char s;
        for (int i = 0; i < 10; i++) {
            s=(char)('A'+i);
            list.add("ADMIN-"+s);
        }
        return list;
    }
    @RequestMapping("/user")
    public List<String> getUserList(){
        List<String> list=new ArrayList<>();
        char s;
        for (int i = 0; i < 10; i++) {
            s=(char)('A'+i);
            list.add("USER-"+s);
        }
        return list;
    }


}
