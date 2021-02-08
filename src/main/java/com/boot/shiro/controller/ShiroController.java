package com.boot.shiro.controller;

import com.boot.shiro.entity.SysUserEntity;
import com.boot.shiro.mapper.SysMenuMapper;
import com.boot.shiro.util.ShiroUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
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
import java.util.ArrayList;
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


     /*   ObjectMapper mapper = new ObjectMapper();
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setUsername("张三丰");
        String s = "";
        try {
            s = mapper.writeValueAsString(sysUserEntity);
            System.out.println("json字符串："+s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }*/
        return "登录成功";
    }

    /**
     * 服务器每次重启请求该接口之前必须先请求上面登录接口
     * http://localhost:7011/menu/list 获取所有菜单列表
     * 权限要求：sys:user:shiro
     */
    @RequestMapping("/menu/list")
//    @RequiresPermissions("sys:user:shiro")
    public List list(ShiroHttpServletRequest request,ShiroHttpServletRequest req){
        Subject subject = ShiroUtils.getSubject();
        boolean authenticated = subject.isAuthenticated();
        if(!authenticated){
            //如果没授权，则抛出未授权异常
            throw new AuthorizationException();
        }
        System.out.println("授权成功");


        return sysMenuMapper.selectList() ;
    }

    /**
     * 用户没有该权限，无法访问
     * 权限要求：ccc:ddd:bbb
     */
    @RequestMapping("/menu/list2")
//    @RequiresPermissions("ccc:ddd:bbb")
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

    public static void main(String[] args) {
//        jackson测试
//对象转json字符串
        ObjectMapper mapper = new ObjectMapper();
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setUsername("张三丰");
        String s = "";
        String s1;
        try {
              s = mapper.writeValueAsString(sysUserEntity);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = JSONObject.fromObject(sysUserEntity);
        s1 = jsonObject.toString();
        System.out.println("jackson对象转json(字符串)：" + s );
        System.out.println("jsob-lib对象转json:"+s1);

        List<SysUserEntity> list = new ArrayList<SysUserEntity>();
        SysUserEntity u1= new SysUserEntity();
        u1.setUsername("张无忌");
        SysUserEntity u2 = new SysUserEntity();
        u2.setUsername("张翠山");
        list.add(u1);
        list.add(u2);
        try {
            String list2Json = mapper.writeValueAsString(list);
            System.out.println("Jackson集合list转json：" + list2Json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

//        JSONObject jsonObject1 = JSONObject.fromObject(list);
        JSONArray jsonArray = JSONArray.fromObject(list);
        String s2 = jsonArray.toString();
        System.out.println("JSON-lib集合list转json:" + s2);
    }
}