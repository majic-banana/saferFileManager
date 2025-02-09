package com.project.saferfilemanager.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.saferfilemanager.common.Result;
import com.project.saferfilemanager.common.ResultCode2Msg;
import com.project.saferfilemanager.common.security.SecurtyUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class FileInterceptor implements HandlerInterceptor {


    private final ObjectMapper objectMapper = new ObjectMapper(); // 创建 ObjectMapper 实例

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        //判断路径合法性
        String userName = (String) session.getAttribute("userName");
        String path = (String) request.getParameter("path");
        String role = (String) session.getAttribute("role");

        if(!role.equals("ADMIN") && !SecurtyUtils.isPathValid(userName, path)){

            //报错
            Result result = Result.error(ResultCode2Msg.BAD_PATH);

            // 设置响应内容类型为 JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK); // 设置状态码为 200

            // 将 Result 对象转换为 JSON 字符串并写入响应
            response.getWriter().write(objectMapper.writeValueAsString(result));
            response.getWriter().flush(); // 刷新输出流

            return false;
        }

        return true;
    }
}
