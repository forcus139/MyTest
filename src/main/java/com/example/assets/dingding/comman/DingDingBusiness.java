//package com.example.assets.dingding.comman;
//
//import com.alibaba.fastjson.JSON;
//import com.example.assets.dingding.entity.HrDingdingAttendance;
//import com.example.assets.dingding.entity.HrDingdingDept;
//import com.example.assets.dingding.entity.HrDingdingUser;
//import com.google.common.collect.Lists;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.time.DateUtils;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * TODO
// *
// * @author GengTianMing
// * @since 2023/02/23 14:44
// **/
//@Slf4j
//public class DingDingBusiness {
//    @Test
//    public void doBaseTask() {
//        log.info("--------------------------钉钉人员部门基本任务开始-----------------------");
//        Date now = new Date();
//
//        try {
//            List<OapiV2DepartmentListsubResponse.DeptBaseResponse> departmentList = DingDingUtil.getDepartment();
//            if(departmentList != null && departmentList.size() > 0) {
//                List<String> userIdList = new ArrayList<>();
//                List<HrDingdingDept> dingdingDeptList = new ArrayList<>();
//                List<OapiV2UserListResponse.ListUserResponse> userList = new ArrayList();
//                for(OapiV2DepartmentListsubResponse.DeptBaseResponse department:departmentList) {
//                    long cursor = 0L;
//                    long size = 50L;
//                    OapiV2UserListResponse.PageResult userPageResult = DingDingUtil.getDepartmentUser(department.getDeptId(), cursor, size);
//                    while (userPageResult != null && userPageResult.getHasMore()) {
//                        userList.addAll(userPageResult.getList());
//                        cursor = userPageResult.getNextCursor();
//                        userPageResult = DingDingUtil.getDepartmentUser(department.getDeptId(), cursor, size);
//                    }
//                    if(userPageResult != null && userPageResult.getList() != null && userPageResult.getList().size() > 0) {
//                        userList.addAll(userPageResult.getList());
//                    }
//
//                    HrDingdingDept hrDingdingDept = new HrDingdingDept();
//                    hrDingdingDept.setDeptId(department.getDeptId());
//                    hrDingdingDept.setName(department.getName());
//                    hrDingdingDept.setParentId(department.getParentId());
//                    hrDingdingDept.setUpdateTime(now);
//                    dingdingDeptList.add(hrDingdingDept);
//                }
//
//                hrDingdingService.insertOrUpdateDept(dingdingDeptList);
//
//                if(userList != null && userList.size() > 0) {
//                    List<HrDingdingUser> hrDingdingUserList = new ArrayList<>();
//                    for(OapiV2UserListResponse.ListUserResponse user:userList) {
//                        userIdList.add(user.getUserid());
//
//                        HrDingdingUser hrDingdingUser = new HrDingdingUser();
//                        hrDingdingUser.setUserid(user.getUserid());
//                        hrDingdingUser.setUnionid(user.getUnionid());
//                        hrDingdingUser.setName(user.getName());
//                        hrDingdingUser.setAvatar(user.getAvatar());
//                        hrDingdingUser.setStateCode(user.getStateCode());
//                        hrDingdingUser.setMobile(user.getMobile());
//                        hrDingdingUser.setHideMobile(user.getHideMobile()?"true":"false");
//                        hrDingdingUser.setTelephone(user.getTelephone());
//                        hrDingdingUser.setJobNumber(user.getJobNumber());
//                        hrDingdingUser.setTitle(user.getTitle());
//                        hrDingdingUser.setEmail(user.getEmail());
//                        hrDingdingUser.setOrgEmail(user.getOrgEmail());
//                        hrDingdingUser.setWorkPlace(user.getWorkPlace());
//                        hrDingdingUser.setRemark(user.getRemark());
//                        hrDingdingUser.setDeptIdList(JSON.toJSONString(user.getDeptIdList()));
//                        hrDingdingUser.setExtension(user.getExtension());
//                        hrDingdingUser.setAdmin(user.getAdmin()?"true":"false");
//                        hrDingdingUser.setBoss(user.getBoss()?"true":"false");
//                        hrDingdingUser.setLeader(user.getLeader()?"true":"false");
//                        if(user.getHiredDate() != null) {
//                            hrDingdingUser.setHiredDate(new Date(Long.valueOf(user.getHiredDate())));
//                        }
//                        hrDingdingUser.setExclusiveAccountType(user.getExclusiveAccountType());
//                        hrDingdingUser.setExclusiveAccount(user.getExclusiveAccount()?"true":"false");
//                        hrDingdingUser.setLoginId(user.getLoginId());
//                        hrDingdingUser.setUpdateTime(now);
//                        hrDingdingUserList.add(hrDingdingUser);
//                    }
//
//                    hrDingdingService.insertOrUpdateUser(hrDingdingUserList);
//                }
//            }
//        } catch (Exception e) {
//            log.error(ConstantsUtil.SYSTEM_ERROR, e);
//        }
//
//        log.info("--------------------------钉钉人员部门基本任务结束-----------------------");
//    }
//
//
//    @Test
//    public void doAttendTest() {
//        System.out.println(DingDingUtil.getToken());
//        Date now = new Date();
//
//        String workDate = DateUtils.getDate(DateUtils.addDay(new Date(), -3));
//        String nowDate = DateUtils.getDate(new Date());
//        String startWorkDate = workDate + " 00:00:00";
//        String endWorkDate = nowDate + " 23:59:59";
//
//        List<OapiAttendanceListResponse.Recordresult> attendanceList = new ArrayList<>();
//
//        List<OapiV2DepartmentListsubResponse.DeptBaseResponse> departmentList = DingDingUtil.getDepartment();
//        if(departmentList != null && departmentList.size() > 0) {
//            List<String> userIdList = new ArrayList<>();
//            for(OapiV2DepartmentListsubResponse.DeptBaseResponse department:departmentList) {
//                List<String> userIdListTmp = DingDingUtil.getDepartmentUserId(department.getDeptId());
//                if(userIdListTmp != null && userIdListTmp.size() > 0) {
//                    userIdList.addAll(userIdListTmp);
//                }
//            }
//
//            if(userIdList != null && userIdList.size() > 0) {
//                List<List<String>> userIds = Lists.partition(userIdList, 50);
//                for(List<String> users:userIds) {
//                    int count = 1;
//                    long offset = 0L;
//                    long limit = 50L;
//                    List<OapiAttendanceListResponse.Recordresult> attendanceListTmp = DingDingUtil.getAttendanceList(startWorkDate, endWorkDate, users, offset, limit);
//                    if(attendanceListTmp != null && attendanceListTmp.size() > 0) {
//                        attendanceList.addAll(attendanceListTmp);
//                        while (attendanceListTmp.size() <= 50) {
//                            count++;
//                            offset = (count - 1) * limit;
//                            attendanceListTmp = DingDingUtil.getAttendanceList(startWorkDate, endWorkDate, users, offset, limit);
//                            if(attendanceListTmp == null || attendanceListTmp.size() == 0) {
//                                break;
//                            }
//                            attendanceList.addAll(attendanceListTmp);
//                        }
//                    }
//
//                }
//            }
//
//            if(attendanceList != null && attendanceList.size() > 0) {
//                List<HrDingdingAttendance> hrDingdingAttendanceList = new ArrayList<>();
//                for(OapiAttendanceListResponse.Recordresult attendance: attendanceList) {
//                    HrDingdingAttendance hrDingdingAttendance = new HrDingdingAttendance();
//                    hrDingdingAttendance.setAttendanceId(attendance.getId());
//                    hrDingdingAttendance.setSourceType(attendance.getSourceType());
//                    hrDingdingAttendance.setBaseCheckTime(attendance.getBaseCheckTime());
//                    hrDingdingAttendance.setUserCheckTime(attendance.getUserCheckTime());
//                    hrDingdingAttendance.setProcInstId(attendance.getProcInstId());
//                    hrDingdingAttendance.setApproveId(attendance.getApproveId());
//                    hrDingdingAttendance.setLocationResult(attendance.getLocationResult());
//                    hrDingdingAttendance.setTimeResult(attendance.getTimeResult());
//                    hrDingdingAttendance.setCheckType(attendance.getCheckType());
//                    hrDingdingAttendance.setUserId(attendance.getUserId());
//                    hrDingdingAttendance.setWorkDate(attendance.getWorkDate());
//                    hrDingdingAttendance.setRecordId(attendance.getRecordId());
//                    hrDingdingAttendance.setPlanId(attendance.getPlanId());
//                    hrDingdingAttendance.setGroupId(attendance.getGroupId());
//                    hrDingdingAttendance.setUpdateTime(now);
//                    hrDingdingAttendanceList.add(hrDingdingAttendance);
//                }
//
//                hrDingdingService.insertOrUpdateAttendance(hrDingdingAttendanceList);
//            }
//        }
//    }
//}
