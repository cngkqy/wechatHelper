package com.nuoxin.virtual.rep.api.web.controller.request.hcp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author tiancun
 */
@ApiModel(value = "医生基本信息修改每个字段的请求参数")
public class HcpBasicFieldRequestBean implements Serializable{
    private static final long serialVersionUID = 4655248582939056676L;



    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "每个字段的id")
    private Long fieldId;

    @ApiModelProperty(value = "每个字段的修改前id")
    private Long oldFieldId;

    @ApiModelProperty(value = "前端不用传")
    private Long ddfvId;


    @ApiModelProperty(value = "字段的名称")
    private String key;

    @ApiModelProperty(value = "字段修改前的名称")
    private String oldKey;

    @ApiModelProperty(value = "每个字段的值")
    private String value;

    @ApiModelProperty(value = "每个字段修改前的值")
    private String oldValue;

    @ApiModelProperty(value = "正确错误的使用的字段")
    private String correct;

    @ApiModelProperty(value = "前端不用传")
    private Long doctorId;

    @ApiModelProperty(value = "前端不用传")
    private Long drugUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getDrugUserId() {
        return drugUserId;
    }

    public void setDrugUserId(Long drugUserId) {
        this.drugUserId = drugUserId;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }


    public Long getDdfvId() {
        return ddfvId;
    }

    public void setDdfvId(Long ddfvId) {
        this.ddfvId = ddfvId;
    }


    public Long getOldFieldId() {
        return oldFieldId;
    }

    public void setOldFieldId(Long oldFieldId) {
        this.oldFieldId = oldFieldId;
    }

    public String getOldKey() {
        return oldKey;
    }

    public void setOldKey(String oldKey) {
        this.oldKey = oldKey;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }
}
