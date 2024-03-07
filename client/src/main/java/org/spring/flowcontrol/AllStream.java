package org.spring.flowcontrol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

@Component
@ComponentScan("org.spring.flowcontrol")
public class AllStream {

    private final LoginStream loginStream;
    private final FileSteam fileSteam;

    @Autowired
    public AllStream(LoginStream loginStream, FileSteam fileSteam) {
        this.loginStream = loginStream;
        this.fileSteam = fileSteam;
    }

    public  void  allStream() throws IOException {
        boolean flag = true;
        Scanner scanner = new Scanner(System.in);
        while (true){
            loginStream.loginStream(scanner);
            fileSteam.fileSteam(scanner);
        }
    }
}
