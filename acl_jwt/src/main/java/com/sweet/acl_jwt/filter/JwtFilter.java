package com.sweet.acl_jwt.filter;

import com.sweet.acl_jwt.component.CustomUserDetailsService;
import com.sweet.acl_jwt.component.JwtUtil;
import com.sweet.acl_jwt.constant.RequestMatcherConst;
import com.sweet.acl_jwt.entity.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try{
            if(bypassToken(request)) {
                filterChain.doFilter(request, response);
                return;
            }

            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }

            final String token = authHeader.substring(7);
            final String username = jwtUtil.extractUsername(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserEntity userDetails = (UserEntity) customUserDetailsService.loadUserByUsername(username);
                if(jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response); // enable bypass
        }
        catch (Exception ex){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid token");
        }
    }

    private boolean bypassToken(HttpServletRequest request){
        List<Pair<String, String>> bypassRequests = List.of(
                Pair.of(RequestMatcherConst.BypassAPI.LOGIN, RequestMatcherConst.Method.POST),
                Pair.of(RequestMatcherConst.BypassAPI.REGISTER, RequestMatcherConst.Method.POST)
        );

        String requestURI =  request.getRequestURI().toString();
        String method = request.getMethod();
        for(Pair<String, String> item : bypassRequests){
            if(item.getLeft().contains(requestURI) && item.getRight().equals(method)){
                return true;
            }
        }

        return false;
    }
}
