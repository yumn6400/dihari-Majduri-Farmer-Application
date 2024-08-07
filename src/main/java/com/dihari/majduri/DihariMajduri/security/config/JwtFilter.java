package com.dihari.majduri.DihariMajduri.security.config;


import com.dihari.majduri.DihariMajduri.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ApplicationContext applicationContext;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//        System.out.println("********do filter internal*********");
//
//        request.getHeaderNames().asIterator().forEachRemaining(headerName -> {
//            System.out.println("  " + headerName + ": " + request.getHeader(headerName));
//        });
//        String requestPath = request.getRequestURI();
//        if (isWebRequest(requestPath)) {
//            request.setAttribute("divertTo", "WEB");
//        } else if (isMobileRequest(requestPath)) {
//            request.setAttribute("divertTo", "MOBILE");
//        }
//        System.out.println("*************************************");


        setRequestAttribute(request);

        String authHeader=request.getHeader("Authorization");
        String token=null;
        String userName=null;
        if(authHeader!=null && authHeader.startsWith(("Bearer "))){
            token=authHeader.substring(7);
            userName=jwtService.extractUserName(token);
        }
        if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails=applicationContext.getBean(UserDetailsService.class)
                        .loadUserByUsername(userName);

            if(jwtService.validateToken(token,userDetails))
            {
                UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }

    private void setRequestAttribute(HttpServletRequest request){
        String requestPath = request.getRequestURI();
        if (isWebRequest(requestPath)) {
            request.setAttribute("divertTo", "WEB");
        } else if (isMobileRequest(requestPath)) {
            request.setAttribute("divertTo", "MOBILE");
        }
    }
    private boolean isWebRequest(String path) {
        return path.startsWith("/web/");
    }

    private boolean isMobileRequest(String path) {
        return path.startsWith("/mobile/");
    }
}
