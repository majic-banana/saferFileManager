package com.project.saferfilemanager.service;

import com.project.saferfilemanager.common.Result;
import com.project.saferfilemanager.common.ResultCode2Msg;
import com.project.saferfilemanager.entity.DiskFile;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

@Service
public class FileService {
    public static final String rootPath = "E:/Project/saferFileManager/disk";
    private int idCounter = 0; // 用于生成唯一ID

    public Result selectAll(String childPath) {
        Path startPath = Paths.get(rootPath + childPath);
        DiskFile rootFile = new DiskFile();
        rootFile.setId(++idCounter); // 设置唯一 ID
        rootFile.setName(childPath);
        rootFile.setPath("");
        rootFile.setType("folder");
        rootFile.setSize(0);
        rootFile.setOwner(childPath);
        rootFile.setChildren(new ArrayList<>());

        try {
            Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    // 获取文件信息并添加到树结构
                    if (startPath.relativize(file).getNameCount() == 1) {
                        // 获取文件信息并添加到树结构
                        addFileToTree(rootFile, file);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    // 只处理子目录的添加
                    if (!dir.equals(startPath) && startPath.relativize(dir).getNameCount() == 1) { // 不添加根目录
                        addDirectoryToTree(rootFile, dir);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });

            return Result.success(rootFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Result.error();
    }

    private void addFileToTree(DiskFile parent, Path file) {
        // 只在这里添加文件
        try {
            DiskFile diskFile = new DiskFile();
            diskFile.setId(++idCounter); // 设置唯一 ID
            diskFile.setName(file.getFileName().toString());
            diskFile.setPath(file.toString().substring(rootPath.length() + 1));
            diskFile.setType("file");
            diskFile.setSize((int) (Files.size(file) / 1024)); // 转换为 KB
            diskFile.setOwner("defaultOwner"); // 设置文件拥有者（可以根据需要更改）

            // 将文件添加到父目录
            parent.getChildren().add(diskFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addDirectoryToTree(DiskFile parent, Path dir) {
        DiskFile diskFile = new DiskFile();
        diskFile.setId(++idCounter); // 设置唯一 ID
        diskFile.setName(dir.getFileName().toString());
        diskFile.setPath(dir.toString().substring(rootPath.length() + 1));
        diskFile.setType("folder");
        diskFile.setSize(0); // 文件夹大小为 0
        diskFile.setOwner("defaultOwner"); // 设置文件拥有者（可以根据需要更改）

        // 初始化子文件列表
        diskFile.setChildren(new ArrayList<>());

        // 将目录添加到父节点的子节点中
        parent.getChildren().add(diskFile);

        // 遍历当前目录，递归添加子目录和文件
        try {
            Files.list(dir).forEach(path -> {
                if (Files.isDirectory(path)) {
                    addDirectoryToTree(diskFile, path); // 递归添加子目录
                } else {
                    addFileToTree(diskFile, path); // 添加文件
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void upload(String path, MultipartFile file) throws IOException {
        // 获取上传文件的原始名称
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            throw new IOException("上传的文件名无效");
        }

        // 定义文件的存储路径，包括文件名
        String fullPath = rootPath + path + fileName; // 确保包含文件名

        // 创建目标文件
        File targetFile = new File(fullPath);

        // 确保父目录存在
        if (!targetFile.getParentFile().exists()) {
            if (!targetFile.getParentFile().mkdirs()) {
                throw new IOException("无法创建目录: " + targetFile.getParent());
            }
        }

        // 将上传的文件写入目标文件
        file.transferTo(targetFile);
    }

    public void download(String fullPath, HttpServletResponse response, String fileName){


        File file = new File(fullPath);
        if (file.exists()) {
            response.setContentType("application/octet-stream"); // 设置内容类型为二进制流
            if(fileName == null || fileName.isEmpty()){
                // 设置下载时的文件名
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");
            }else{
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
            }


            response.setContentLength((int) file.length()); // 设置文件长度

            try (FileInputStream in = new FileInputStream(file);
                 OutputStream out = response.getOutputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                out.flush();
            } catch (IOException e) {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                e.getMessage(); // 处理异常
            }
        } else {
            response.setStatus(HttpStatus.NOT_FOUND.value()); // 文件未找到
        }
    }

    public Result delete(String fullPath){
        File file = new File(fullPath);
        if (file.exists()) {
            file.delete();

            return Result.success();
        }else{
            return Result.error(ResultCode2Msg.FILE_NOT_FOUND);
        }

    }


}
