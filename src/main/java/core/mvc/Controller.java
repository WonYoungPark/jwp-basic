package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wyparks2@gmail.com on 2018. 5. 2.
 * Blog : http://WonYoungPark.github.io
 * Github : http://github.com/WonYoungPark
 */
public interface Controller {
    String execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
