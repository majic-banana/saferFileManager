package com.project.saferfilemanager.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper; // 引入 Jackson 的 ObjectMapper
import com.project.saferfilemanager.common.Result;
import com.project.saferfilemanager.common.ResultCode2Msg;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper = new ObjectMapper(); // 创建 ObjectMapper 实例

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            // 创建错误结果
            Result result = Result.error(ResultCode2Msg.AUTHENTICATION_ERROR);

            // 设置响应内容类型为 JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK); // 设置状态码为 200

            // 将 Result 对象转换为 JSON 字符串并写入响应
            response.getWriter().write(objectMapper.writeValueAsString(result));
            response.getWriter().flush(); // 刷新输出流

            return false; // 阻止请求继续处理
        }

        return true; // 允许请求继续处理
    }
}
