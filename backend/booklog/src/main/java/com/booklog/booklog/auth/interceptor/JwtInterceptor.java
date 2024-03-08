package com.booklog.booklog.auth.interceptor;

import com.booklog.booklog.auth.Auth;
import com.booklog.booklog.auth.domain.JwtTokenProvider;
import com.booklog.booklog.common.code.ErrorCode;
import com.booklog.booklog.exception.AuthorizationException;
import io.lettuce.core.output.ScanOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("JWT Interceptor 호출");
        log.info("[요청 URL] : {}", request.getRequestURI());

        System.out.println("**********************************");
        System.out.println(request.getContentType());

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 컨트롤러에 @Auth 어노테이션이 사용되었는지 체크
        if (!checkAuthAnnotation(handler, Auth.class)) {
            return true;
        }

        String token = jwtTokenProvider.resolveToken(request);
        log.info("accessToken: {}", token);

        if (token == null) {
            throw new AuthorizationException(ErrorCode.NOT_EXIST_TOKEN);
        }

        // AccessToken 유효성 검증
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationException(ErrorCode.EXPIRED_TOKEN);
        }

        String userId = jwtTokenProvider.getUserId(token);
        request.setAttribute("userId", userId);
        log.info("userId: {}", userId);

        return true;
    }

    private boolean checkAuthAnnotation(Object handler, Class cls) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (handlerMethod.getMethodAnnotation(cls) != null) {
            log.info("handler: {}", handler);
            return true;
        }
        return false;
    }
}
