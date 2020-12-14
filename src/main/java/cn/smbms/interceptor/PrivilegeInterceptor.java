package cn.smbms.interceptor;


import cn.smbms.pojo.User;
import cn.smbms.tools.Constants;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author knn
 * @create 2020-11-23 23:29
 */
public class PrivilegeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User userSession = (User) request.getSession().getAttribute(Constants.USER_SESSION);
        if (userSession == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return false;
        } else {
            return true;
        }
    }
}
