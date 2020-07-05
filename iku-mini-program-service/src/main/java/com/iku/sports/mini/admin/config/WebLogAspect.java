package com.iku.sports.mini.admin.config;

import com.google.common.collect.Lists;
import com.iku.sports.mini.admin.annotation.WebLog;
import com.iku.sports.mini.admin.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Aspect
@Slf4j
public class WebLogAspect {
    private static final String UNKNOWN = "unknown";
    private static final String LOCALHOST = "127.0.0.1";
    private static final String SEPARATOR = ",";

    @Around("@annotation(com.iku.sports.mini.admin.annotation.WebLog)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        HttpServletRequest request = RequestContextHolder.getRequestAttributes() != null ?
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest() : null;
        String url = request != null ? request.getRequestURI() : "";

        log.info("client ip: {}, request url: {}, params:{}", getIpAddr(request), url, getArgs(proceedingJoinPoint));

        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        WebLog webLog = signature.getMethod().getAnnotation(WebLog.class);
        boolean outputResponse = webLog.response();

        Object result = proceedingJoinPoint.proceed();

        long endTime = System.currentTimeMillis();

        log.info("request url: {}, response:{}, cost time: {} ms", url, outputResponse ? JsonUtil.toJSONString(result) : "", endTime - startTime);

        return result;
    }

    private static List<Object> getArgs(ProceedingJoinPoint pjp) {
        Object[] argValues = pjp.getArgs();
        if (argValues != null && argValues.length > 0) {
            return Arrays.stream(argValues).map(v -> {
                if (v == null) {
                    return "null";
                }
                if (v instanceof HttpServletRequest) {
                    return "{httpReq}";
                } else if ( v instanceof HttpServletResponse){
                    return "{httpResp}";
                } else if (v instanceof MultipartFile){
                    return ((MultipartFile) v).getOriginalFilename();
                }

                return JsonUtil.toJSONString(v);
            }).collect(Collectors.toList());
        }

        return Lists.newArrayList();
    }

    private static String getIpAddr(HttpServletRequest request) {
        if (null == request) {
            return "";
        }

        String ipAddress;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (LOCALHOST.equals(ipAddress)) {
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                       // ignore
                    }

                    ipAddress = inet != null ? inet.getHostAddress() : "";
                }
            }

            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            // "***.***.***.***".length()
            if (ipAddress != null && ipAddress.length() > 15) {
                if (ipAddress.indexOf(SEPARATOR) > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }

        return ipAddress;
    }

}
