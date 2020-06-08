package cn.ibdsr.web.core.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Headers", "Accept,Content-Type,Cache-Control,X-Requested-With,X-Nideshop-Token,Authorization");
        response.addHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
        response.addHeader("Allow", "GET, POST, DELETE, PUT");
        chain.doFilter(request, response);
    }
}
