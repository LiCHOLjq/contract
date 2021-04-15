package com.contract.interceptor;

import com.alibaba.fastjson.JSONObject;

import com.contract.annotation.UserAppPass;
import com.contract.annotation.UserLoginToken;
import com.contract.annotation.UserRoleToken;
import com.contract.annotation.UserShareToken;
import com.contract.domain.Admin;
import com.contract.domain.Share;
import com.contract.exception.BaseException;
import com.contract.service.AdminService;
import com.contract.service.ShareService;
import com.contract.util.TokenUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Date;

public class AuthenticationInterceptor implements HandlerInterceptor {


    @Resource
    private AdminService adminService;

    @Resource
    private ShareService shareService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        try {
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            if (!(object instanceof HandlerMethod)) {
                return true;
            }
            HandlerMethod handlerMethod = (HandlerMethod) object;
            Method method = handlerMethod.getMethod();
//            if (method.isAnnotationPresent(UserAppPass.class)) {
//                UserAppPass userAppPass = method.getAnnotation(UserAppPass.class);
//                if (userAppPass.required()) {
//                    String xRequestedWith = httpServletRequest.getHeader("X-Requested-With");
//                    if(xRequestedWith==null){
//                        throw new BaseException("非法的请求",401);
//                    }
//                    if(!xRequestedWith.equals("app")){
//                        throw new BaseException("非法的请求",401);
//                    }
//                }
//            }

            if (method.isAnnotationPresent(UserLoginToken.class)) {
                UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
                if (userLoginToken.required()) {
                    if (token == null||"".equals(token)) {
                        throw new BaseException("无token",401);
                    }
                    String adminId = TokenUtil.getId(token);
                    if(adminId == null||"".equals(adminId)) {
                        throw new BaseException("token解析失败",401);
                    }
                    Admin admin = adminService.getAdminByIdHasPassword(adminId);
                    if(admin == null) {
                        throw new BaseException("用户不存在",401);
                    }
                    if(!TokenUtil.verifyPassword(token,admin.getAdminPassword())){
                        throw new BaseException("token已过期",401);
                    }
                    if(admin.getAdminUseful()){

                    }
                    if (method.isAnnotationPresent(UserRoleToken.class)) {
                        UserRoleToken userRoleToken = method.getAnnotation(UserRoleToken.class);
                        if (userRoleToken.required()) {
                            if(userRoleToken.passRoleList().length>0){
                                boolean isExit = false;
                                for(String role : userRoleToken.passRoleList()){
                                    if(admin.getAdminRole().equals(role)){
                                        isExit = true;
                                        break;
                                    }
                                }
                                if(!isExit){
                                    throw new BaseException("角色非法操作",401);
                                }
                            }
                        }
                    }
                }
            }
            if (method.isAnnotationPresent(UserShareToken.class)) {
                UserShareToken userLoginToken = method.getAnnotation(UserShareToken.class);
                if (userLoginToken.required()) {
                    if (token == null||"".equals(token)) {
                        throw new BaseException("无token",401);
                    }
                    String shareId = TokenUtil.getId(token);
                    if(shareId == null||"".equals(shareId)) {
                        throw new BaseException("token解析失败",401);
                    }
                    Share share = shareService.getShareByIdHasPassword(shareId);
                    if(shareId == null) {
                        throw new BaseException("用户不存在",401);
                    }
                    if(share.getShareBeginDate()!=null&&new Date().before(share.getShareBeginDate())){
                        throw new BaseException("分享不在时效内",401);
                    }
                    if(share.getShareEndDate()!=null&&new Date().after(share.getShareEndDate())){
                        throw new BaseException("分享不在时效内",401);
                    }
                    if(share.getShareDelete()){
                        throw new BaseException("分享已删除",401);
                    }
                    if(!TokenUtil.verifyPassword(token,share.getSharePassword())){
                        throw new BaseException("token已过期",401);
                    }

                }
            }
            return true;

        }catch (Exception e){
            e.printStackTrace();
            httpServletResponse.setHeader("content-type", "application/json");
            httpServletResponse.setCharacterEncoding("UTF-8");      //获取PrintWriter输出流
            JSONObject result = new JSONObject();
            PrintWriter out = httpServletResponse.getWriter();
            result.put("data", e.getMessage());
            result.put("code", 401);
            out.write(result.toJSONString());
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
