//package com.example.assets.dingding.services;
//
//import com.alibaba.fastjson.JSONArray;
//import com.example.assets.dingding.entity.HrDingdingAttendance;
//import com.example.assets.dingding.entity.HrDingdingDept;
//import com.example.assets.dingding.entity.HrDingdingProcess;
//import com.example.assets.dingding.entity.HrDingdingUser;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.time.DateUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * TODO
// *
// * @author GengTianMing
// * @since 2023/02/23 14:48
// **/
//@Slf4j
//@Service
//public class HrDingdingServiceImpl implements HrDingdingService {
//
//    @Autowired
//    private HrDingdingUserMapper hrDingdingUserMapper;
//    @Autowired
//    private HrDingdingDeptMapper hrDingdingDeptMapper;
//    @Autowired
//    private HrDingdingAttendanceMapper hrDingdingAttendanceMapper;
//    @Autowired
//    private HrDingdingProcessMapper hrDingdingProcessMapper;
//    //以下为系统其它业务类，本例不展示其详细代码
//	......
//
//    @Override
//    public void insert(HrDingdingAttendance hrDingdingAttendance) {
//        hrDingdingAttendanceMapper.insert(hrDingdingAttendance);
//    }
//
//    @Override
//    public void insertOrUpdateAttendance(List<HrDingdingAttendance> hrDingdingAttendanceList) {
//        if(hrDingdingAttendanceList != null && hrDingdingAttendanceList.size() > 0) {
//            for(HrDingdingAttendance hrDingdingAttendance:hrDingdingAttendanceList) {
//                HrDingdingAttendanceExample hrDingdingAttendanceExample = new HrDingdingAttendanceExample();
//                hrDingdingAttendanceExample.createCriteria().andAttendanceIdEqualTo(hrDingdingAttendance.getAttendanceId());
//                List<HrDingdingAttendance> list = hrDingdingAttendanceMapper.selectByExample(hrDingdingAttendanceExample);
//                if(list != null && list.size() > 0) {
//                    hrDingdingAttendanceMapper.updateByExampleSelective(hrDingdingAttendance, hrDingdingAttendanceExample);
//                }else {
//                    hrDingdingAttendanceMapper.insert(hrDingdingAttendance);
//                }
//                if(hrDingdingAttendance.getUserCheckTime() != null) {
//                    HrDingdingUser hrDingdingUser = getDingdingUserByUserId(hrDingdingAttendance.getUserId());
//                    if(hrDingdingUser != null) {
//                        //根据手机号获取档案用户
//                        CMUser cmUser = getHrUserByPhone(hrDingdingUser.getMobile());
//                        if(cmUser != null) {
//                            //保存HR系统打卡记录
//                            HrAttendPunchRecord hapr = new HrAttendPunchRecord();
//                            hapr.setHrUserId(cmUser.getHrUserId());
//                            hapr.setUserNumber(cmUser.getUserName());
//                            hapr.setNameCn(cmUser.getRealName());
//                            hapr.setAttendanceTime(String.valueOf(hrDingdingAttendance.getUserCheckTime().getTime()/1000));
//                            ......
//
//                        }else {
//                            log.warn("HR系统不存在手机号["+hrDingdingUser.getMobile()+"]的用户");
//                        }
//                    }
//                }
//
//            }
//        }
//    }
//
//    private CMUser getHrUserByPhone(String phone) {
//        //根据手机号获取HR系统人员信息
//    }
//
//    /**
//     *	根据userid获取钉钉人员详细信息
//     */
//    private HrDingdingUser getDingdingUserByUserId(String userId) {
//        HrDingdingUserExample hrDingdingUserExample = new HrDingdingUserExample();
//        hrDingdingUserExample.createCriteria().andUseridEqualTo(userId);
//        List<HrDingdingUser> hrDingdingUserList = hrDingdingUserMapper.selectByExample(hrDingdingUserExample);
//        if(hrDingdingUserList != null && hrDingdingUserList.size() > 0) {
//            return hrDingdingUserList.get(0);
//        }
//        return null;
//    }
//
//    /**
//     *	根据名称获取钉钉人员详细信息
//     */
//    private HrDingdingUser getDingdingUserByName(String userName) {
//        HrDingdingUserExample hrDingdingUserExample = new HrDingdingUserExample();
//        hrDingdingUserExample.createCriteria().andNameEqualTo(userName);
//        List<HrDingdingUser> hrDingdingUserList = hrDingdingUserMapper.selectByExample(hrDingdingUserExample);
//        if(hrDingdingUserList != null && hrDingdingUserList.size() > 0) {
//            return hrDingdingUserList.get(0);
//        }
//        return null;
//    }
//
//    @Override
//    public void insertOrUpdateUser(List<HrDingdingUser> hrDingdingUserList) {
//        if(hrDingdingUserList != null && hrDingdingUserList.size() > 0) {
//            for(HrDingdingUser hrDingdingUser:hrDingdingUserList) {
//                HrDingdingUserExample hrDingdingUserExample = new HrDingdingUserExample();
//                hrDingdingUserExample.createCriteria().andUseridEqualTo(hrDingdingUser.getUserid());
//                List<HrDingdingUser> list = hrDingdingUserMapper.selectByExample(hrDingdingUserExample);
//                if(list != null && list.size() > 0) {
//                    hrDingdingUserMapper.updateByExampleSelective(hrDingdingUser, hrDingdingUserExample);
//                }else {
//                    hrDingdingUserMapper.insert(hrDingdingUser);
//                }
//            }
//        }
//    }
//
//    @Override
//    public void insertOrUpdateDept(List<HrDingdingDept> dingdingDeptList) {
//        if(dingdingDeptList != null && dingdingDeptList.size() > 0) {
//            for(HrDingdingDept hrDingdingDept:dingdingDeptList) {
//                HrDingdingDeptExample hrDingdingDeptExample = new HrDingdingDeptExample();
//                hrDingdingDeptExample.createCriteria().andDeptIdEqualTo(hrDingdingDept.getDeptId());
//                List<HrDingdingDept> list = hrDingdingDeptMapper.selectByExample(hrDingdingDeptExample);
//                if(list != null && list.size() > 0) {
//                    hrDingdingDeptMapper.updateByExampleSelective(hrDingdingDept, hrDingdingDeptExample);
//                }else {
//                    hrDingdingDeptMapper.insert(hrDingdingDept);
//                }
//            }
//        }
//    }
//
//    @Override
//    public void insertOrUpdateProcessInstance(String type, List<HrDingdingProcessWithBLOBs> processinstanceList) {
//        if(processinstanceList != null && processinstanceList.size() > 0) {
//            for(HrDingdingProcessWithBLOBs hrDingdingProcess:processinstanceList) {
//                HrDingdingProcessExample hrDingdingProcessExample = new HrDingdingProcessExample();
//                hrDingdingProcessExample.createCriteria().andProcessInstanceIdEqualTo(hrDingdingProcess.getProcessInstanceId());
//                List<HrDingdingProcess> list = hrDingdingProcessMapper.selectByExample(hrDingdingProcessExample);
//                if(list != null && list.size() > 0) {
//                    hrDingdingProcessMapper.updateByExampleSelective(hrDingdingProcess, hrDingdingProcessExample);
//                }else {
//                    hrDingdingProcessMapper.insert(hrDingdingProcess);
//                }
//
//                try {
//                    if("leave".equals(type)) {
//                        convertHrLeave(hrDingdingProcess);
//                    }else if("public".equals(type)) {
//                        convertHrPublic(hrDingdingProcess);
//                    }else if("travelwork".equals(type)) {
//                        convertHrTravelwork(hrDingdingProcess);
//                    }else if("transfer".equals(type)) {
//                        convertHrTransfer(hrDingdingProcess);
//                    }
//                } catch (Exception e) {
//                    log.error(ConstantsUtil.SYSTEM_ERROR, e);
//                }
//            }
//        }
//    }
//
//    private void convertHrTransfer(HrDingdingProcessWithBLOBs hrDingdingProcess) {
//        if("COMPLETED".equals(hrDingdingProcess.getStatus()) && "agree".equals(hrDingdingProcess.getResult())) {
//            HrDingdingUser hrDingdingUser = getDingdingUserByUserId(hrDingdingProcess.getOriginatorUserid());
//            if(hrDingdingUser != null) {
//                //根据手机号获取档案用户
//                CMUser cmUser = getHrUserByPhone(hrDingdingUser.getMobile());
//                if (cmUser != null) {
//                    ExtOaPeopleDataModel oaModel = new ExtOaPeopleDataModel();
//                    //保存人员信息
//					......
//                    if(StringUtils.isNotBlank(hrDingdingProcess.getFormComponentValues())) {
//                        JSONArray jsonArray = JSONArray.parseArray(hrDingdingProcess.getFormComponentValues());
//                        if(jsonArray != null && jsonArray.size() > 0) {
//                            JSONObject jsonObject = jsonArray.getJSONObject(0);
//                            String value = jsonObject.getString("value");
//                            JSONArray valueArr = JSONArray.parseArray(value);
//                            for(int i=0;i<valueArr.size();i++) {
//                                JSONObject obj = valueArr.getJSONObject(i);
//                                JSONObject props = obj.getJSONObject("props");
//                                String v = obj.getString("value");
//                                if(StringUtils.isBlank(v)) {
//                                    continue;
//                                }
//                                if("原部门".equals(props.getString("label"))) {
//                                    oaModel.setSsbm(v);
//                                }else if("原职位".equals(props.getString("label"))) {
//                                    oaModel.setGwa(v);
//                                }else if("转入部门".equals(props.getString("label"))) {
//                                    oaModel.setDdbm(v);
//                                }else if("转入职位".equals(props.getString("label"))) {
//                                    oaModel.setGwa(v);
//                                }else if("生效日期".equals(props.getString("label"))) {
//                                    oaModel.setSjdgrq(String.valueOf(DateUtils.stringToInteger(v, EnumDateStyle.YYYY_MM_DD)));
//                                }
//                            }
//                        }
//                    }
//
//                    ServiceResult<HrOaEmployeeTransfer> result = hrUserEditService.updateOaPeopleData(oaModel);
//                    if (result != null && !result.getSuccess()) {
//                        log.warn("保存转岗信息失败："+result.getMessage());
//                    }
//                }else {
//                    log.warn("HR系统不存在手机号["+hrDingdingUser.getMobile()+"]的用户");
//                }
//            }
//        }
//    }
//
//    private void convertHrTravelwork(HrDingdingProcessWithBLOBs hrDingdingProcess) {
//        if("COMPLETED".equals(hrDingdingProcess.getStatus()) && "agree".equals(hrDingdingProcess.getResult())) {
//            HrDingdingUser hrDingdingUser = getDingdingUserByUserId(hrDingdingProcess.getOriginatorUserid());
//            if(hrDingdingUser != null) {
//                //根据手机号获取档案用户
//                CMUser cmUser = getHrUserByPhone(hrDingdingUser.getMobile());
//                if (cmUser != null) {
//                    HrOaAttendTravelwork hrOaAttendTravelwork = new HrOaAttendTravelwork();
//                    List<HrOaAttendTravelwork> hrOaAttendTravelworkList = new ArrayList<>();
//                    //保存人员信息
//					......
//
//                    hrOaAttendTravelwork.setSqrq(DateUtils.DateToString(hrDingdingProcess.getCreateTime(), EnumDateStyle.YYYY_MM_DD));
//                    hrOaAttendTravelwork.setZw(String.valueOf(oaMapTableService.getPositionOaId(cmUser.getHrUserId()).getResult()));
//                    hrOaAttendTravelwork.setSfjk("0");
//
//                    if(StringUtils.isNotBlank(hrDingdingProcess.getFormComponentValues())) {
//                        JSONArray jsonArray = JSONArray.parseArray(hrDingdingProcess.getFormComponentValues());
//                        if(jsonArray != null && jsonArray.size() > 0) {
//                            JSONObject formObj = jsonArray.getJSONObject(0);
//                            JSONArray propsArr = JSONArray.parseArray(formObj.getString("value"));
//                            if(propsArr != null && propsArr.size() > 0) {
//                                if(propsArr != null && propsArr.size() > 0) {
//                                    for(int i=0;i<propsArr.size();i++) {
//                                        JSONObject jsonObject = propsArr.getJSONObject(i);
//                                        JSONObject props = jsonObject.getJSONObject("props");
//                                        String v = jsonObject.getString("value");
//                                        if(StringUtils.isBlank(v)) {
//                                            continue;
//                                        }
//                                        if("出差事由".equals(props.getString("label"))) {
//                                            hrOaAttendTravelwork.setCcsy(v);
//                                        }
//                                        if("同行人".equals(props.getString("label"))) {
//                                            //查询钉钉人员
//                                            HrDingdingUser txDingdingUser = getDingdingUserByName(v);
//                                            if(txDingdingUser != null) {
//                                                CMUser txCmUser = getHrUserByPhone(txDingdingUser.getMobile());
//                                                if(txCmUser != null) {
//                                                    hrOaAttendTravelwork.setSxry(String.valueOf(oaMapTableService.getUserOaIdByUserId(txCmUser.getUserId()).getResult()));
//                                                }
//                                            }
//                                        }
//                                        if("行程".equals(props.getString("label"))) {
//                                            JSONArray rowArray = JSONArray.parseArray(v);
//                                            if(rowArray != null && rowArray.size() > 0) {
//                                                for(int j=0; j<rowArray.size(); j++) {
//                                                    HrOaAttendTravelwork travelwork = new HrOaAttendTravelwork();
//                                                    JSONObject rowObj = rowArray.getJSONObject(j);
//                                                    JSONArray row = rowObj.getJSONArray("rowValue");
//                                                    for(int z=0; z<row.size(); z++) {
//                                                        JSONObject work = row.getJSONObject(z);
//                                                        if("目的城市".equals(work.getString("label"))) {
//                                                            travelwork.setCcmdd(work.getString("value"));
//                                                        }else if("开始时间".equals(work.getString("label"))) {
//                                                            String date = work.getString("value");
//                                                            date = date.substring(0, 10);
//                                                            travelwork.setCcrq(String.valueOf(DateUtils.stringToLong(date, "yyyy-MM-dd")));
//                                                        }else if("结束时间".equals(work.getString("label"))) {
//                                                            String date = work.getString("value");
//                                                            date = date.substring(0, 10);
//                                                            travelwork.setCcrqa(String.valueOf(DateUtils.stringToLong(date, "yyyy-MM-dd")));
//                                                        }
//                                                    }
//                                                    hrOaAttendTravelworkList.add(travelwork);
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//
//                            }
//
//                        }
//                    }
//
//                    if(hrOaAttendTravelworkList != null && hrOaAttendTravelworkList.size() > 0) {
//                        for(HrOaAttendTravelwork travelwork:hrOaAttendTravelworkList) {
//
//                            hrOaAttendTravelwork.setCcmdd(travelwork.getCcmdd());
//                            hrOaAttendTravelwork.setCcrq(travelwork.getCcrq());
//                            hrOaAttendTravelwork.setCcrqa(travelwork.getCcrqa());
//                            ServiceResult<HrOaAttendTravelwork> result = attendTravelworkService.saveTravelworkData(hrOaAttendTravelwork);
//                            if (result != null && !result.getSuccess()) {
//                                log.warn("保存出差信息失败："+result.getMessage());
//                            }
//                        }
//                    }
//
//                }else {
//                    log.warn("HR系统不存在手机号["+hrDingdingUser.getMobile()+"]的用户");
//                }
//            }
//        }
//    }
//
//    private void convertHrPublic(HrDingdingProcessWithBLOBs hrDingdingProcess) {
//        if("COMPLETED".equals(hrDingdingProcess.getStatus()) && "agree".equals(hrDingdingProcess.getResult())) {
//            HrDingdingUser hrDingdingUser = getDingdingUserByUserId(hrDingdingProcess.getOriginatorUserid());
//            if(hrDingdingUser != null) {
//                //根据手机号获取档案用户
//                CMUser cmUser = getHrUserByPhone(hrDingdingUser.getMobile());
//                if (cmUser != null) {
//                    //实体类
//                    HrOaAttendPublicLeave hrOaAttendPublicLeave = new HrOaAttendPublicLeave();
//                    hrOaAttendPublicLeave.setUserName(cmUser.getUserName());
//                    hrOaAttendPublicLeave.setHrUserId(cmUser.getHrUserId());
//                    //保存人员信息
//					......
//
//                    hrOaAttendPublicLeave.setApplyDate(String.valueOf(hrDingdingProcess.getCreateTime().getTime()/1000));
//
//                    hrOaAttendPublicLeave.setBorrowMoney("0");
//
//                    if(StringUtils.isNotBlank(hrDingdingProcess.getFormComponentValues())) {
//                        JSONArray jsonArray = JSONArray.parseArray(hrDingdingProcess.getFormComponentValues());
//                        if(jsonArray != null && jsonArray.size() > 0) {
//                            for(int i = 0; i<jsonArray.size(); i++) {
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                if(jsonObject.get("name") != null && jsonObject.getString("name").contains("事由")) {
//                                    String value = jsonObject.getString("value");
//                                    if(StringUtils.isNotBlank(value)) {
//                                        hrOaAttendPublicLeave.setLeaveReasonDetail(value);
//                                        hrOaAttendPublicLeave.setDetails(value);
//                                    }
//                                }
//                                if(jsonObject.get("name") != null && jsonObject.getString("name").contains("开始时间")) {
//                                    String value = jsonObject.getString("value");
//                                    if(StringUtils.isNotBlank(value)) {
//                                        value = value.replace("[", "");
//                                        value = value.replace("]", "");
//                                        value = value.replace("\"", "");
//                                        System.out.println(value);
//                                        String[] formArr = value.split(",");
//                                        Date beginDate = DateUtils.StringToDate(formArr[0]+":00", EnumDateStyle.YYYY_MM_DD_HH_MM_SS);
//                                        Date endDate = DateUtils.StringToDate(formArr[1]+":00", EnumDateStyle.YYYY_MM_DD_HH_MM_SS);
//                                        hrOaAttendPublicLeave.setLeaveBeginDate(String.valueOf(beginDate.getTime()/1000));
//                                        hrOaAttendPublicLeave.setLeaveEndDate(String.valueOf(endDate.getTime()/1000));
//                                        hrOaAttendPublicLeave.setLeavePlanReturnDate(String.valueOf(endDate.getTime()/1000));
//                                        long l = endDate.getTime() - beginDate.getTime();
//                                        long day = l / (24 * 60 * 60 * 1000);
//                                        long hour = (l / (60 * 60 * 1000) - day * 24);
//                                        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
//                                        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
//                                        hrOaAttendPublicLeave.setZjts(String.valueOf(day));
//                                        hrOaAttendPublicLeave.setZjxss(String.valueOf(hour));
//                                        hrOaAttendPublicLeave.setZjfzs(String.valueOf(s));
//                                    }
//                                }
//
//                            }
//                        }
//                    }
//
//                    hrOaAttendPublicLeave.setDestination("");
//                    hrOaAttendPublicLeave.setFollowUserIds(String.valueOf(oaMapTableService.getUserOaIdByUserId(cmUser.getUserId()).getResult()));
//                    hrOaAttendPublicLeave.setLeaveOnDutyContact("");
//                    hrOaAttendPublicLeave.setIsBeyondCity("");
//                    hrOaAttendPublicLeave.setHaveTravalFee("");
//                    hrOaAttendPublicLeave.setExpireReturnReason("");
//                    hrOaAttendPublicLeave.setIsRelateMoney("");
//
//                    hrOaAttendPublicLeaveMapper.insert(hrOaAttendPublicLeave);
//                }else {
//                    log.warn("HR系统不存在手机号["+hrDingdingUser.getMobile()+"]的用户");
//                }
//            }
//        }
//    }
//
//    private void convertHrLeave(HrDingdingProcessWithBLOBs hrDingdingProcess) {
//        if("COMPLETED".equals(hrDingdingProcess.getStatus()) && "agree".equals(hrDingdingProcess.getResult())) {
//            HrDingdingUser hrDingdingUser = getDingdingUserByUserId(hrDingdingProcess.getOriginatorUserid());
//            if(hrDingdingUser != null) {
//                //根据手机号获取档案用户
//                CMUser cmUser = getHrUserByPhone(hrDingdingUser.getMobile());
//                if (cmUser != null) {
//                    ExtOaHrAttendModel model = new ExtOaHrAttendModel();
//                    model.setFromHr(false);
//                    model.setUserId(oaMapTableService.getUserOaIdByUserId(cmUser.getUserId()).getResult());
//                    if(StringUtils.isNotBlank(hrDingdingProcess.getFormComponentValues())) {
//                        JSONArray jsonArray = JSONArray.parseArray(hrDingdingProcess.getFormComponentValues());
//                        for(int i = 0; i<jsonArray.size(); i++) {
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            if (jsonObject.get("name") != null && jsonObject.getString("name").contains("开始时间")) {
//                                String value = jsonObject.getString("value");
//                                if(StringUtils.isNotBlank(value)) {
//                                    value = value.replace("[", "");
//                                    value = value.replace("]", "");
//                                    value = value.replace("\"", "");
//                                    System.out.println(value);
//                                    String[] formArr = value.split(",");
//
//                                    Date beginDate = DateUtils.StringToDate(formArr[0]+":00", EnumDateStyle.YYYY_MM_DD_HH_MM_SS);
//                                    Date endDate = DateUtils.StringToDate(formArr[1]+":00", EnumDateStyle.YYYY_MM_DD_HH_MM_SS);
//                                    model.setStartTime(String.valueOf(beginDate.getTime()/1000));
//                                    model.setEndTime(String.valueOf(endDate.getTime()/1000));
//
//                                    switch (formArr[4]) {
//                                        case "年假": model.setVocationType("3");break;
//                                        case "事假": model.setVocationType("2");break;
//                                        case "病假": model.setVocationType("1");break;
//                                        case "调休":break;
//                                        case "产假": model.setVocationType("5");break;
//                                        case "陪产假": model.setVocationType("7");break;
//                                        case "婚假": model.setVocationType("6");break;
//                                        case "丧假": model.setVocationType("10");break;
//                                    }
//
//                                }
//                                break;
//                            }
//                        }
//                    }
//
//                    if(StringUtils.isBlank(model.getVocationType())) {
//                        log.warn("请假类型获取失败");
//                        return;
//                    }
//                    ServiceResult<ExtOaHrAttendResultModel> result = attendLeaveListService.insertLeaveDetail(model);
//                    if (result != null && !result.getSuccess()) {
//                        log.warn("保存请假信息失败："+result.getMessage());
//                    }
//                }else {
//                    log.warn("HR系统不存在手机号["+hrDingdingUser.getMobile()+"]的用户");
//                }
//            }
//        }
//    }
//
//}
