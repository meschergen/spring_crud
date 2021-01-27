package web.config.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import web.dao.UserDao;
import web.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * 07.12.2020
 *
 * @author MescheRGen
 */

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler{

    private UserDao userDao; // для возможности вытащить user.id, через username
                            // чтобы сформировать с помощью id ссылку на страницу пользователя
                            // или добавить UserService.class?

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        User user =  userDao.getByUsername(authentication.getName());
        if (roles.contains("ROLE_ADMIN")) {
            httpServletResponse.sendRedirect("/users"); // /admin
        } else if (roles.contains("ROLE_USER")) {
            httpServletResponse.sendRedirect("users/" + user.getId());
        } else {
            httpServletResponse.sendRedirect("/");
        }
    }
}
