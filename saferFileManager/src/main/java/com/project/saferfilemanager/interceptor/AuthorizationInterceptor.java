package com.project.saferfilemanager.interceptor;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.saferfilemanager.common.Result;
import com.project.saferfilemanager.common.ResultCode2Msg;
import com.project.saferfilemanager.common.Roles;
import com.project.saferfilemanager.common.config.AuthorizationConfig;
import com.project.saferfilemanager.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;



@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final AuthorizationConfig authorizationConfig;

    private final ObjectMapper objectMapper = new ObjectMapper(); // 创建 ObjectMapper 实例

    @Autowired
    public AuthorizationInterceptor(AuthorizationConfig authorizationConfig) {
        this.authorizationConfig = authorizationConfig;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        String userRole = session.getAttribute("role").toString();

        String urlRemovedEscape = request.getRequestURI().replace("/","");
        String requiredRole = authorizationConfig.getPath2role().get(urlRemovedEscape);


        //用户角色序列优先于所需序列
        if (Roles.valueOf(userRole).seq <= Roles.valueOf(requiredRole).seq) {
            return true;
        }




        // 创建错误结果
        Result result = Result.error(ResultCode2Msg.AUTHORIZATION_ERROR);

        // 设置响应内容类型为 JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK); // 设置状态码为 200

        // 将 Result 对象转换为 JSON 字符串并写入响应
        response.getWriter().write(objectMapper.writeValueAsString(result));
        response.getWriter().flush(); // 刷新输出流
        return false;
    }
}
