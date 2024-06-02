package com.nhnacademy.springsecurityhomework.config;

import com.nhnacademy.springsecurityhomework.exception.PageSizeOverDefaultSizeException;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


public class CustomPageableHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static final int DEFAULT_PAGE_SIZE = 5;
    private static final int MAX_PAGE_SIZE = 10;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Pageable.class.equals(parameter.getParameterType());
    }



    @Override
    public Pageable resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                    NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        int page = parsePage(webRequest.getParameter("page"));
        int size = parseSize(webRequest.getParameter("size"));
        return PageRequest.of(page, size);
    }


    private int parsePage(String pageString) {
        if (pageString == null) {
            return 0;
        }
            int page = Integer.parseInt(pageString);
            return Math.max(page, 0);

    }


    private int parseSize(String sizeString) {
        try {
            int size = Integer.parseInt(sizeString);
            return Math.clamp(size, Math.max(size, DEFAULT_PAGE_SIZE), MAX_PAGE_SIZE);
        } catch (IllegalArgumentException e) {
            throw new PageSizeOverDefaultSizeException("페이지 사이즈가 최대크기를 초과합니다");
        }
    }
}

