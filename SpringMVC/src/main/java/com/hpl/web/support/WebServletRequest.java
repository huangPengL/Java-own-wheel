package com.hpl.web.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: huangpenglong
 * @Date: 2023/12/16 21:40
 */
public class WebServletRequest {

    final HttpServletRequest request;

    final HttpServletResponse response;


    public WebServletRequest(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public HttpServletRequest getRequest() {
        return request;
    }
}
