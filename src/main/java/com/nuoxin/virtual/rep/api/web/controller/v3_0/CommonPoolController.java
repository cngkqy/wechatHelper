package com.nuoxin.virtual.rep.api.web.controller.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.service.v2_5.CommonService;
import com.nuoxin.virtual.rep.api.service.v3_0.CommonPoolService;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.CommonPoolRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.CommonPoolDoctorResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DoctorImportErrorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 我的客户相关接口
 * @author tiancun
 * @date 2019-05-06
 */
@RestController
@Api(value = "V3.0.1客户公共池")
@RequestMapping(value = "/common/pool")
public class CommonPoolController {

    @Resource
    private CommonPoolService commonPoolService;

    @Resource
    private CommonService commonService;

    @ApiOperation(value = "列表", notes = "列表")
    @PostMapping(value = "/doctor/list")
    public DefaultResponseBean<PageResponseBean<CommonPoolDoctorResponse>> getDoctorPage(HttpServletRequest request, @RequestBody CommonPoolRequest bean) {

        PageResponseBean<CommonPoolDoctorResponse> doctorPage = commonPoolService.getDoctorPage(bean);
        DefaultResponseBean<PageResponseBean<CommonPoolDoctorResponse>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(doctorPage);
        return responseBean;
    }



    @ApiOperation(value = "导入医生", notes = "导入医生")
    @PostMapping(value = "/doctor/import")
    public DefaultResponseBean<Map<String, DoctorImportErrorResponse>> doctorImport(MultipartFile file) {

        Map<String, DoctorImportErrorResponse> map = commonService.doctorImport(file);
        DefaultResponseBean<Map<String, DoctorImportErrorResponse>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(map);
        return responseBean;
    }

}
