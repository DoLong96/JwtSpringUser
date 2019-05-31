package com.example.springbootjwt.Service;

import com.example.springbootjwt.Entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    public static List<User> listUser = new ArrayList<User>();

    static {
        User userKai = new User(1,"kai","123456");
        userKai.setRoles(new String[] {"ROLE_ADMIN"});

        User userSena = new User(2,"sena","123456");
        userSena.setRoles(new String[] {"ROLE_USER"});

        listUser.add(userKai);
        listUser.add(userSena);
    }

    public List<User> finAll(){
        return listUser;
    }

    public User findById(int id){
        for (User user : listUser){
            if(user.getId() == id){
                return user;
            }
        }
        return  null;
    }

    public boolean add(User user){
        for (User userExist : listUser){
            if (user.getId() == userExist.getId() || StringUtils.equals(user.getUsername(),userExist.getUsername())){
                return false;
            }
        }
        listUser.add(user);
        return true;
    }

    public void delete(int id){
        listUser.removeIf(user -> user.getId() == id);
    }

    public  User loadUserByUserName(String username){
        for (User user : listUser){
            if (user.getUsername().equals(username)){
                return user;
            }
        }

        return null;
    }

    public User loadUserByUsername(String username) {
        for (User user : listUser) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean checkLogin(User user){
        for (User userExits : listUser){
            if (StringUtils.equals(user.getUsername(),userExits.getUsername()) &&
                    StringUtils.equals(user.getPassword(),userExits.getPassword())){
                return true;
            }
        }
        return false;
    }
}
