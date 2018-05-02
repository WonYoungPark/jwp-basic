package next.web;

import core.db.DataBase;
import next.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by wyparks2@gmail.com on 2018. 4. 23.
 * Blog : http://WonYoungPark.github.io
 * Github : http://github.com/WonYoungPark
 */
@WebServlet("/user/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/user/login.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        if (isLogin(req)) {
            resp.sendRedirect("/user/list");
            return;
        }

        resp.sendRedirect("/user/login");
    }

    private boolean isLogin(HttpServletRequest request) {
        final String userId   = request.getParameter("userId");
        final String password = request.getParameter("password");

        User user = DataBase.findUserById(userId);
        if (user == null)
            return false;
        if (!password.equals(user.getPassword()))
            return false;

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return true;
    }
}
