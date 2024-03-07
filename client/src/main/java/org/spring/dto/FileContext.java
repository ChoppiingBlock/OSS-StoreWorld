package org.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.spring.po.DirectoryMetadata;
import org.spring.po.FileMetadata;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Builder
@Component
@AllArgsConstructor
public class FileContext {
    private Integer userId;
    private String userName;

    //当前文件夹路径
    private List<String> pathNameList;
    //当前文件夹路径对应dirid
    private List<Integer> pathIdList;

    private List<DirectoryMetadata> nowDirList;
    private List<FileMetadata> nowFileList;

    public FileContext() {
        this.userId = 0;
        this.userName = "";
        this.pathNameList = new ArrayList<>();
        this.pathIdList = new ArrayList<>();
        this.nowDirList = new ArrayList<>();
        this.nowFileList = new ArrayList<>();
    }

    public int getParentId(){
        int length = this.pathIdList.size();
        return this.pathIdList.get(length - 1) ;
    }

    public String getNowPath(){
        StringBuilder stringBuffer = new StringBuilder();
        for (String s : this.pathNameList) {
            stringBuffer.append("/");
            stringBuffer.append(s);
        }
        return stringBuffer.toString();
    }


    public List<DirectoryMetadata> getNowDirList() {
        return nowDirList;
    }

    public void setNowDirList(List<DirectoryMetadata> nowDirList) {
        this.nowDirList = nowDirList;
    }

    public List<FileMetadata> getNowFileList() {
        return nowFileList;
    }

    public void setNowFileList(List<FileMetadata> nowFileList) {
        this.nowFileList = nowFileList;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getPathNameList() {
        return pathNameList;
    }

    public void setPathNameList(List<String> pathNameList) {
        this.pathNameList = pathNameList;
    }

    public List<Integer> getPathIdList() {
        return pathIdList;
    }

    public void setPathIdList(List<Integer> pathIdList) {
        this.pathIdList = pathIdList;
    }
}
