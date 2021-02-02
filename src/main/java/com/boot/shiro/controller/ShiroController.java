package com.boot.shiro.controller;

import com.boot.shiro.mapper.SysMenuMapper;
import com.boot.shiro.util.ShiroUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Shrio 测试方法控制层
 */
@RestController
public class ShiroController {
    private static Logger LOGGER = LoggerFactory.getLogger(ShiroController.class) ;

    @Resource
    private SysMenuMapper sysMenuMapper ;

    @RequestMapping("/login")
    public String login(ShiroHttpServletRequest request,ShiroHttpServletRequest req){

        return "登录页面" ;
    }
    /**
     * 登录测试
     * http://localhost:7011/userLogin?userName=admin&passWord=admin
     */
    @RequestMapping("/userLogin")
    public String userLogin (
            @RequestParam(value = "userName") String userName,
            @RequestParam(value = "passWord") String passWord){
//        try{
//            Subject subject = ShiroUtils.getSubject();
//            UsernamePasswordToken token = new UsernamePasswordToken(userName, passWord);
//            //进入UserRealm.doGetAuthenticationInfo(启动的时候配置类中UserRealm已经注入SecurityManager)
//            //此login方法最终调用配置的realm
//            //UserRealm继承AuthorizingRealm，重写doGetAuthenticationInfo,查看数据库验证
//            subject.login(token);
//            LOGGER.info("登录成功");
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
        //原先账号密码直接报异常，现改成自定义异常并返回前端，后台不抛异常
        Subject subject = ShiroUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, passWord);
        //进入UserRealm.doGetAuthenticationInfo(启动的时候配置类中UserRealm已经注入SecurityManager)
        //此login方法最终调用配置的realm
        //UserRealm继承AuthorizingRealm，重写doGetAuthenticationInfo,查看数据库验证
        subject.login(token);
        LOGGER.info("登录成功");
        return "登录成功";
    }

    /**
     * 服务器每次重启请求该接口之前必须先请求上面登录接口
     * http://localhost:7011/menu/list 获取所有菜单列表
     * 权限要求：sys:user:shiro
     */
    @RequestMapping("/menu/list")
    @RequiresPermissions("sys:user:shiro")
    public List list(ShiroHttpServletRequest request,ShiroHttpServletRequest req){

        return sysMenuMapper.selectList() ;
    }

    /**
     * 用户没有该权限，无法访问
     * 权限要求：ccc:ddd:bbb
     */
    @RequestMapping("/menu/list2")
    @RequiresPermissions("ccc:ddd:bbb")
    public List list2(HttpServletRequest request){

        return sysMenuMapper.selectList() ;
    }

    /**
     * 退出测试
     */
    @RequestMapping("/userLogOut")
    public String logout (){
        ShiroUtils.logout();
        return "success" ;
    }
}