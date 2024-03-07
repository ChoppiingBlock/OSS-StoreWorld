package org.spring.flowcontrol;

import org.spring.service.UserService;
import org.spring.dto.FileContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;
@Component
public class LoginStream {
    @Autowired
    UserService userService;

    @Autowired
    FileContext fileContext;

    public static void welcomeMenu(){
        System.out.println("欢迎使用系统，请输入数字选择操作：");
        System.out.println("1. 登录");
        System.out.println("2. 注册");
        System.out.println("0. 退出");
    }

    public static void printfNameTrip(){
        System.out.println("请输入账号：");
    }

    public static void printfPasswordTrip(){
        System.out.println("请输入密码：");
    }

    public static void ErrorTrip(String s){
        System.out.println(s);
    }

    public static void SuccessTrip(){
        System.out.println("成功");
    }

    public static void cleanCMD(){
        for (int i = 0; i < 50; i++) {
            System.out.println(); // 输出空行
        }
    }

    public static void Exit(){
        System.exit(0);
    }

    public void loginStream(Scanner scanner){
        welcomeMenu();
        boolean flag = true;
        while (flag){
            String input = scanner.nextLine();
            switch (input){
                case("1") : {
                    printfNameTrip();
                    String name = scanner.nextLine();
                    printfPasswordTrip();
                    String password = scanner.nextLine();
                    int userId = userService.login(name, password);
                    if(userId > 0) {
                        fileContext.setUserName(name);
                        fileContext.setUserId(userId);
                        fileContext.getPathNameList().add(name);
                        fileContext.getPathIdList().add(0);
                        SuccessTrip();
                        cleanCMD();
                        flag = false;
                    } else {
                        ErrorTrip("账号or密码错误");
                    }
                    break;
                }
                case("2") : {
                    printfNameTrip();
                    String name = scanner.nextLine();
                    printfPasswordTrip();
                    String password = scanner.nextLine();
                    if(userService.register(name, password) > 0){
                        SuccessTrip();
                        cleanCMD();
                    } else {
                        ErrorTrip("注册失败");
                    }
                    break;
                }
                case ("3") : {
                    Exit();
                    break;
                }
                default : {
                    ErrorTrip("输入正确指令");
                    welcomeMenu();
                    break;
                }
            }
        }
    }


}
