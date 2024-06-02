package com.nhnacademy.springsecurityhomework.config;

import com.nhnacademy.springsecurityhomework.exception.PageSizeOverDefaultSizeException;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.context.request.NativeWebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomPageableHandlerMethodArgumentResolverTest {

    private final CustomPageableHandlerMethodArgumentResolver resolver = new CustomPageableHandlerMethodArgumentResolver();

    @Test
    public void resolveArgument_MaxPageSize_ExceptionThrown() {
        NativeWebRequest webRequest = mock(NativeWebRequest.class);
        when(webRequest.getParameter("page")).thenReturn("0");
        when(webRequest.getParameter("size")).thenReturn("11");

        assertThrows(PageSizeOverDefaultSizeException.class,
                () -> resolver.resolveArgument(null, null, webRequest, null));
    }

    @Test
    public void resolveArgument_ValidPageSizeAndNumber_ReturnsPageable() {
        NativeWebRequest webRequest = mock(NativeWebRequest.class);
        when(webRequest.getParameter("page")).thenReturn("1");
        when(webRequest.getParameter("size")).thenReturn("7");

        Pageable pageable = resolver.resolveArgument(null, null, webRequest, null);

        assertEquals(PageRequest.of(1, 7), pageable);
    }




    @Test
    public void resolveArgument_NegativePageNumber_ReturnsZero() {
        NativeWebRequest webRequest = mock(NativeWebRequest.class);
        when(webRequest.getParameter("page")).thenReturn("-3");
        when(webRequest.getParameter("size")).thenReturn("5");

        Pageable pageable = resolver.resolveArgument(null, null, webRequest, null);

        assertEquals(PageRequest.of(0, 5), pageable);
    }

    @Test
    public void resolveArgument_PageSizeBelowMinSize_ReturnsMinPageSize() {
        NativeWebRequest webRequest = mock(NativeWebRequest.class);
        when(webRequest.getParameter("page")).thenReturn("0");
        when(webRequest.getParameter("size")).thenReturn("2");

        Pageable pageable = resolver.resolveArgument(null, null, webRequest, null);

        assertEquals(PageRequest.of(0, 5), pageable);
    }
}
