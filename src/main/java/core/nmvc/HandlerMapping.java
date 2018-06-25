package core.nmvc;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wyparks2@gmail.com on 2018. 6. 12.
 * Blog : http://WonYoungPark.github.io
 * Github : http://github.com/WonYoungPark
 */
public interface HandlerMapping {
    Object getHandler(HttpServletRequest request);
}
