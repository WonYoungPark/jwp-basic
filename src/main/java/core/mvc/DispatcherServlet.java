package core.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wyparks2@gmail.com on 2018. 5. 2.
 * Blog : http://WonYoungPark.github.io
 * Github : http://github.com/WonYoungPark
 */
@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private ReqeustMapping reqeustMapping;

    @Override
    public void init() throws ServletException {
        reqeustMapping = new ReqeustMapping();
        reqeustMapping.initMapping();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        log.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        Controller controller = reqeustMapping.findController(requestUri);

        try {
            String viewName = controller.execute(req, resp);
            move(viewName, req, resp);
        } catch (Throwable e) {
            log.error(e.getMessage());
            throw new ServletException(e.getMessage());
        }
    }

    private void move(String viewName, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            resp.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));

            return;
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher(viewName);
        requestDispatcher.forward(req, resp);
    }
}
