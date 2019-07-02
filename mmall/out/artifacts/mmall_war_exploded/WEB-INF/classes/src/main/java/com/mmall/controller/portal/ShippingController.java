package com.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;
import com.mmall.pojo.User;
import com.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by JiaweiChen on 2019/6/28.
 */
@Controller
@RequestMapping("/shipping/")
public class ShippingController {

    @Autowired
    private IShippingService iShippingService;

    //增加地址,直接是shipping对象，这是springMVC绑定中的一种方式，就是直接绑定对象，用对象来承载里边的字段，它可以接受到，然后springMVC会自动生成这个对象，
    //并把对应的属性赋值上，否则方法声明的参数就特别多
    // 和直接传Shipping类里面的字段是一样的，id和userId不需要前端传，并且id因为是新增，
    //前端也拿不到shipping的id,两个time也不需要前端传，我们只需要前端传这些填充地址的数据。
    @RequestMapping("add.do")
    @ResponseBody
    public ServerResponse add(HttpSession session, Shipping shipping){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            //强制登陆
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.add(user.getId(),shipping);
    }

    //删除地址
    @RequestMapping("del.do")
    @ResponseBody
    public ServerResponse del(HttpSession session, Integer shippingId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            //强制登陆
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.del(user.getId(),shippingId);
    }

    //更新地址，小心越权问题
    @RequestMapping("update.do")
    @ResponseBody
    public ServerResponse update(HttpSession session, Shipping shipping){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            //强制登陆
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.update(user.getId(),shipping);
    }

    //查询地址,也要判断越权问题
    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse<Shipping> select(HttpSession session, Integer shippingId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            //强制登陆
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.select(user.getId(),shippingId);
    }

    //list分页
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                         HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            //强制登陆
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.list(user.getId(),pageNum,pageSize);
    }

}
