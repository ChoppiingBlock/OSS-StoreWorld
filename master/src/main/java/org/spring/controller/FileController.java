package org.spring.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/FileController")
public class FileController {


    // 获取文件路径
    @GetMapping("/GetFileBucketPath")
    public void getFileBucketPath(){

    }

    // 新增文件
    @GetMapping("/AddFile")
    public void addFile(){

    }

    // 增加文件夹
    @GetMapping("/AddDirectory")
    public void addDirectory(){

    }

    // 删除文件
    @GetMapping("/DeleteFile")
    public void deleteFile(){

    }

    // 删除文件夹
    @GetMapping("/EditDirectory")
    public void editDirectory(){

    }

    // 修改文件信息
    @GetMapping("/EditFile")
    public void editFile() {
        // 实现修改文件信息的方法
    }

    // 修改文件夹信息
    @GetMapping("/EditDirectoryInfo")
    public void editDirectoryInfo() {
        // 实现修改文件夹信息的方法
    }

    // 查看文件信息
    @GetMapping("/ViewFileInfo")
    public void viewFileInfo() {
        // 实现查看文件信息的方法
    }

    // 查看文件夹信息
    @GetMapping("/ViewDirectoryInfo")
    public void viewDirectoryInfo() {
        // 实现查看文件夹信息的方法
    }

    // 返回文件夹内所有文件信息
    @GetMapping("/AllFilesInDirectory")
    public void getAllFilesInDirectory() {
        // 实现返回文件夹内所有文件信息的方法
    }



}
