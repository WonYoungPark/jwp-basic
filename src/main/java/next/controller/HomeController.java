package next.controller;

import core.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wyparks2@gmail.com on 2018. 5. 2.
 * Blog : http://WonYoungPark.github.io
 * Github : http://github.com/WonYoungPark
 */
public class HomeController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "home.jsp";
    }
}