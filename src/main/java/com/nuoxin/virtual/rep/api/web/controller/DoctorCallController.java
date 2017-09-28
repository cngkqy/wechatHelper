package com.nuoxin.virtual.rep.api.web.controller;

import com.alibaba.fastjson.JSON;
import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.DoctorCallService;
import com.nuoxin.virtual.rep.api.web.controller.request.CallbackRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.QueryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.call.CallHistoryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.call.CallInfoRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.call.CallRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.DrugUserCallDetaiBean;
import com.nuoxin.virtual.rep.api.web.controller.response.LoginResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.CallHistoryResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.CallResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.CallStatResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by fenggang on 9/11/17.
 */
@Api(value = "",description = "电话页面接口")
@RestController
@RequestMapping(value = "/call")
public class DoctorCallController extends BaseController {

    @Autowired
    private DoctorCallService doctorCallService;

    @RequestMapping("/callback")
    public DefaultResponseBean<Object> callback(HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean<Object> responseBean = new DefaultResponseBean<>();
        response.setContentType("text/html");
        response.setCharacterEncoding("gb2312");
        PrintWriter out = null;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        CallbackRequestBean bean = null;
        try{
            out = response.getWriter();
            request.getInputStream();
            String line="";
            br=new BufferedReader(new InputStreamReader(
                    request.getInputStream()));
            while((line = br.readLine())!=null){
                sb.append(line);
                logger.info("回调数据：【{}】",line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            out.close();
        }

        if(sb!=null&&sb.toString().trim().length()>0){
            try {
                bean = JSON.parseObject(sb.toString(), CallbackRequestBean.class);
            } catch (Exception e) {
                e.printStackTrace();
                logger.debug("获转码回调返回数据失败");
                responseBean.setCode(500);
                responseBean.setData(bean);
                responseBean.setMessage("获转码回调返回数据失败");
                return responseBean;
            }
        }
        responseBean.setData(bean);
        return responseBean;
    }

    @ApiOperation(value = "获取销售信息", notes = "获取销售信息")
    @GetMapping("/druguser/info")
    public DefaultResponseBean<LoginResponseBean> druguser(HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean<LoginResponseBean> responseBean = new DefaultResponseBean();
        DrugUser drugUser = this.getLoginUser(request);
        if (drugUser == null) {
            responseBean.setCode(500);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
        LoginResponseBean result = new LoginResponseBean();
        result.setName(drugUser.getName());
        result.setEmail(drugUser.getEmail());
        result.setCallBean(JSON.parseObject(drugUser.getCallInfo(), DrugUserCallDetaiBean.class));
        responseBean.setData(result);
        return responseBean;
    }

    @ApiOperation(value = "客户电话搜索列表", notes = "客户电话搜索列表")
    @PostMapping("/doctor/page")
    public DefaultResponseBean<PageResponseBean<CallResponseBean>> doctorPage(@RequestBody QueryRequestBean bean,
                                                                              HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        bean.setDrugUserId(super.getLoginId(request));
        responseBean.setData(doctorCallService.doctorPage(bean));
        return responseBean;
    }

    @ApiOperation(value = "客户电话历史记录", notes = "客户电话历史记录")
    @PostMapping("/doctor/history/page")
    public DefaultResponseBean<PageResponseBean<CallHistoryResponseBean>> doctorHistoryPage(@RequestBody CallHistoryRequestBean bean,
                                                                                            HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        bean.setDrugUserId(super.getLoginId(request));
        responseBean.setData(doctorCallService.doctorHistoryPage(bean));
        return responseBean;
    }

    @ApiOperation(value = "电话顶部统计", notes = "电话顶部统计")
    @PostMapping("/stat")
    public DefaultResponseBean<CallStatResponseBean> stat(HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        responseBean.setData(doctorCallService.stat(super.getLoginId(request)));
        return responseBean;
    }

    @ApiOperation(value = "拨号保存电话记录", notes = "拨号保存电话记录")
    @PostMapping("/save")
    public DefaultResponseBean<CallRequestBean> save(@RequestBody CallRequestBean bean,
                                    HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        bean.setDrugUserId(super.getLoginId(request));
        responseBean.setData(doctorCallService.save(bean));
        return responseBean;
    }

    @ApiOperation(value = "拨号电话记录修改", notes = "拨号电话记录修改")
    @PostMapping("/update")
    public DefaultResponseBean<CallRequestBean> update(@RequestBody CallRequestBean bean,
                                                     HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        Long id = bean.getId();
        if(id==null){
            responseBean.setCode(500);
            responseBean.setMessage("请求参数id不能为空");
            return responseBean;
        }
        bean.setDrugUserId(super.getLoginId(request));
        bean = doctorCallService.update(bean);
        if(bean.getId()==null){
            responseBean.setCode(500);
            responseBean.setMessage("状态更新失败");
        }
        responseBean.setData(bean);

        return responseBean;
    }

    @ApiOperation(value = "挂断保存电话记录", notes = "挂断保存电话记录")
    @PostMapping("/stop/update")
    public DefaultResponseBean<Boolean> stopSave(@RequestBody CallInfoRequestBean bean,
                                        HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        if(bean.getId()==null){
            responseBean.setCode(500);
            responseBean.setMessage("请求参数id不能为空");
            return responseBean;
        }
        bean.setDrugUserId(super.getLoginId(request));
        responseBean.setData(doctorCallService.stopUpdate(bean));
        return responseBean;
    }

}
