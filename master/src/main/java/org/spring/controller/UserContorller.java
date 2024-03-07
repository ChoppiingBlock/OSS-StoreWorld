package org.spring.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.spring.po.User;
import org.spring.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/UserController")
public class UserContorller {

    @Autowired
    UserService userService;
    // 注册用户
    @PostMapping("/RegisterUser")
    public void registerUser() {
        // 实现注册用户的方法
    }

    // 登入
    @PostMapping("/Login")
    @ResponseBody
    public int login(@RequestParam("userName") String userName , @RequestParam("password") String password) throws Exception {
        // 实现登入的方法
        System.out.println(userName + password + "  dsioajfoawef");
        List<User> userList =  userService.getBaseMapper().selectList(new QueryWrapper<User>().eq("username", userName ).eq("password", password));
        if(userList.isEmpty()) {
            throw new Exception();
        }
        System.out.println(userList.get(0).getId());
        return userList.get(0).getId();
    }


    //注册
    @PostMapping("/Register")
    @ResponseBody
    public int register(@RequestParam("userName") String userName , @RequestParam("password") String password) throws Exception {
        // 实现注册的方法
        if (userService.getBaseMapper().insert(User.builder().username(userName).password(password).build()) <= 0){
            throw new Exception();
        }
        return userService.getOne(new QueryWrapper<User>().eq("userName", userName)).getId();
    }

    // 增加文件权限
    @PostMapping("/AddFilePermission")
    public void addFilePermission() {
        // 实现增加文件权限的方法
    }

    // 修改文件权限
    @PostMapping("/ModifyFilePermission")
    public void modifyFilePermission() {
        // 实现修改文件权限的方法
    }

    // 删除文件权限
    @PostMapping("/DeleteFilePermission")
    public void deleteFilePermission() {
        // 实现删除文件权限的方法
    }

    // 查看文件权限
    @GetMapping("/ViewFilePermission")
    public void viewFilePermission() {
        // 实现查看文件权限的方法
    }

}
