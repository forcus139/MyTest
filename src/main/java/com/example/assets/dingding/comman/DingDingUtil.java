//package com.example.assets.dingding.comman;
//
///**
// * TODO
// *
// * @author GengTianMing
// * @since 2023/02/23 14:28
// **/
//import com.dingtalk.api.DefaultDingTalkClient;
//import com.dingtalk.api.DingTalkClient;
//import com.dingtalk.api.request.*;
//import com.dingtalk.api.response.*;
//import com.taobao.api.ApiException;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.List;
//
///**
// * 钉钉工具类
// */
//@Slf4j
//public class DingDingUtil {
//
//    private final static String SYSTEM_ERROR ="SYSTEM_ERROR";
//
//    private final static String APPKEY ="";
//
//    private final static String APPSECRET="";
//
//    private final static Long AGENTID = 0L;
//
//    public final static String LEAVE_PROCESS_CODE = "PROC-70A9A9C2-BF877C09E3FE";
//    public final static String PUBLIC_PROCESS_CODE = "PROC-C6B6F367-BE545F51D9E0";
//    public final static String TRAVELWORK_PROCESS_CODE = "PROC-8763D6A9-A788E7CEC119";
//    public final static String TRANSFER_PROCESS_CODE = "PROC-E05FD7DE-5A2B9D7CD6AC";
//
//
//    //获取token
//    public static String getToken (){
//        Object object = LocalCacheClient.get("access_token");
//        if(object != null){
//            return object.toString();
//        }
//        DefaultDingTalkClient client = new
//                DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
//        OapiGettokenRequest request = new OapiGettokenRequest();
//        request.setAppkey(DingDingUtil.APPKEY);
//        request.setAppsecret(DingDingUtil.APPSECRET);
//        request.setHttpMethod("GET");
//        try {
//            OapiGettokenResponse response = client.execute(request);
//            LocalCacheClient.set("access_token", response.getAccessToken(),7200*1000);
//            if(!response.isSuccess()) {
//                log.error("调用钉钉接口失败："+response.getMessage());
//            }
//            return response.getAccessToken();
//        } catch (ApiException e) {
//            log.error(DingDingUtil.SYSTEM_ERROR, e);
//        }
//        return null;
//    }
//
//    //获取部门列表
//    public static List<OapiV2DepartmentListsubResponse.DeptBaseResponse> getDepartment(){
//        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/listsub");
//        OapiV2DepartmentListsubRequest request = new OapiV2DepartmentListsubRequest();
//        //获取根部门下所有部门列表  根部门的部门id为1
////        request.setDeptId(1L);
//        request.setHttpMethod("GET");
//        try {
//            OapiV2DepartmentListsubResponse response = client.execute(request, DingDingUtil.getToken());
//            if(!response.isSuccess()) {
//                log.error("调用钉钉接口失败："+response.getMessage());
//            }
//            return response.isSuccess() ? response.getResult():null;
//        } catch (ApiException e) {
//            log.error(DingDingUtil.SYSTEM_ERROR, e);
//        }
//        return null;
//    }
//
//    //获取部门下的所有用户列表
//    public static List<String> getDepartmentUserId(Long departmentId){
//        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/user/listid");
//        OapiUserListidRequest req = new OapiUserListidRequest();
//        req.setDeptId(departmentId);
//
//        try {
//            OapiUserListidResponse response = client.execute(req, DingDingUtil.getToken());
//            if(!response.isSuccess()) {
//                log.error("调用钉钉接口失败："+response.getMessage());
//            }
//            return response.isSuccess()?response.getResult().getUseridList():null;
//        } catch (ApiException e) {
//            log.error(DingDingUtil.SYSTEM_ERROR, e);
//        }
//        return null;
//    }
//
//    //获取部门下的所有用户列表
//    public static OapiV2UserListResponse.PageResult getDepartmentUser(Long departmentId, long cursor, long size){
//        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/list");
//        OapiV2UserListRequest request = new OapiV2UserListRequest();
//        request.setDeptId(departmentId);
//        request.setCursor(cursor);
//        request.setSize(size);
//        request.setOrderField("modify_desc");
//        request.setHttpMethod("GET");
//
//        try {
//            OapiV2UserListResponse  response = client.execute(request, DingDingUtil.getToken());
//            if(!response.isSuccess()) {
//                log.error("调用钉钉接口失败："+response.getMessage());
//            }
//            return response.isSuccess()?response.getResult():null;
//        } catch (ApiException e) {
//            log.error(DingDingUtil.SYSTEM_ERROR, e);
//        }
//        return null;
//    }
//
//    //获取钉钉考勤记录
//    public static List<OapiAttendanceListResponse.Recordresult> getAttendanceList(String startWorkDate, String endWorkDate, List<String> userIdList, long offset, long limit) {
//        // 通过调用接口获取考勤打卡结果
//        DingTalkClient clientDingTalkClient = new DefaultDingTalkClient("https://oapi.dingtalk.com/attendance/list");
//        OapiAttendanceListRequest requestAttendanceListRequest = new OapiAttendanceListRequest();
//        // 查询考勤打卡记录的起始工作日
//        requestAttendanceListRequest.setWorkDateFrom(startWorkDate);
//        // 查询考勤打卡记录的结束工作日
//        requestAttendanceListRequest.setWorkDateTo(endWorkDate);
//        // 员工在企业内的userid列表，最多不能超过50个。
//        requestAttendanceListRequest.setUserIdList(userIdList);
//        // 表示获取考勤数据的起始点
//        requestAttendanceListRequest.setOffset(offset);
//        // 表示获取考勤数据的条数，最大不能超过50条。
//        requestAttendanceListRequest.setLimit(limit);
//        OapiAttendanceListResponse response = null;
//        try {
//            response = clientDingTalkClient.execute(requestAttendanceListRequest,DingDingUtil.getToken());
//            if(!response.isSuccess()) {
//                log.error("调用钉钉接口失败："+response.getMessage());
//            }
//            return response.isSuccess()?response.getRecordresult():null;
//        } catch (ApiException e) {
//            log.error(DingDingUtil.SYSTEM_ERROR, e);
//        }
//        return null;
//    }
//
//    //给用户推送消息（文字消息）
//    public static Object pushUser(String userIds,String content){
//        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
//
//        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
//        request.setUseridList(userIds);
//        request.setAgentId(AGENTID);
//        request.setToAllUser(false);
//
//        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
//        msg.setMsgtype("text");
//        msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
//        msg.getText().setContent(content);
//        request.setMsg(msg);
//
//        try {
//            OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, DingDingUtil.getToken());
//            if(!response.isSuccess()) {
//                log.error("调用钉钉接口失败："+response.getMessage());
//            }
//            return response;
//        } catch (ApiException e) {
//            log.error(DingDingUtil.SYSTEM_ERROR, e);
//        }
//        return null;
//    }
//
//    //获取审批实例ID列表
//    public static OapiProcessinstanceListidsResponse.PageResult getProcessinstanceListid(String processCode, Long startTime, Long endTime, long cursor, long size) {
//        try {
//            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/listids");
//            OapiProcessinstanceListidsRequest request = new OapiProcessinstanceListidsRequest();
//            request.setProcessCode(processCode);
//            request.setStartTime(startTime);
//            if(endTime != null) {
//                request.setEndTime(endTime);
//            }
//            request.setSize(size);
//            request.setCursor(cursor);
//            OapiProcessinstanceListidsResponse response = client.execute(request, DingDingUtil.getToken());
//            if(!response.isSuccess()) {
//                log.error("调用钉钉接口失败："+response.getMessage());
//            }
//            return response.isSuccess()?response.getResult():null;
//        } catch (ApiException e) {
//            log.error(DingDingUtil.SYSTEM_ERROR, e);
//        }
//        return null;
//    }
//
//    //获取审批实例详情
//    public static OapiProcessinstanceGetResponse.ProcessInstanceTopVo getProcessinstanceInfo(String processInstanceId) {
//        try {
//            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/get");
//            OapiProcessinstanceGetRequest request = new OapiProcessinstanceGetRequest();
//            request.setProcessInstanceId(processInstanceId);
//            OapiProcessinstanceGetResponse response = client.execute(request, DingDingUtil.getToken());
//            if(!response.isSuccess()) {
//                log.error("调用钉钉接口失败："+response.getMessage());
//            }
//            return response.isSuccess()?response.getProcessInstance():null;
//        } catch (ApiException e) {
//            log.error(DingDingUtil.SYSTEM_ERROR, e);
//        }
//        return null;
//    }
//
//
//}