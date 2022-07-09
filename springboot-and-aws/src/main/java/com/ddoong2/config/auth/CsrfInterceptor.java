package com.ddoong2.config.auth;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@Component
public class CsrfInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if (modelAndView != null) {
            modelAndView.addObject("_csrf", new Mustache.Lambda() {
                @Override
                public void execute(Template.Fragment frag, Writer out) throws IOException {
                    if ("token".equals(frag.execute())) {
                        out.write(((CsrfToken) request.getAttribute(CsrfToken.class.getName())).getToken());
                    }
                }
            });
            modelAndView.addObject("_csrf_header", new Mustache.Lambda() {
                @Override
                public void execute(Template.Fragment frag, Writer out) throws IOException {
                    if ("header".equals(frag.execute())) {
                        out.write(((CsrfToken) request.getAttribute(CsrfToken.class.getName())).getHeaderName());
                    }
                }
            });
        }

        super.postHandle(request, response, handler, modelAndView);
    }
}
