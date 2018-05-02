package next.web;

import core.db.DataBase;
import next.model.User;
import org.h2.engine.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by wyparks2@gmail.com on 2018. 4. 23.
 * Blog : http://WonYoungPark.github.io
 * Github : http://github.com/WonYoungPark
 */
@WebServlet("/user/update")
public class UpdateUserServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        req.setAttribute("user", DataBase.findUserById("wyparks2"));

        RequestDispatcher rd = req.getRequestDispatcher("/user/update.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        User user = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
            req.getParameter("email"));
        log.debug("user : {}", user);

        HttpSession session = req.getSession();
        Object loginedUser = session.getAttribute("user");

        // 자기 자신만 수정 가능
        if (loginedUser != null && ((User)loginedUser).getUserId().equals(user.getUserId())) {
            DataBase.addUser(user);
        }

        resp.sendRedirect("/user/list");
    }

}
