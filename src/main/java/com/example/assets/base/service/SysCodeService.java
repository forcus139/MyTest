package com.example.assets.base.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.assets.base.entity.SysCode;
import com.example.assets.base.entity.TempSysCode;
import com.example.assets.base.mapper.CommonDao;
import com.example.assets.base.mapper.DaoCardQrCodeNew;
import com.example.assets.base.mapper.DaoSysCode;
import com.example.assets.base.mapper.DaoTempSysCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author GTM
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysCodeService {
    @Autowired
    DaoSysCode daosyscode;

    @Autowired
    DaoCardQrCodeNew cardqrcodenew;

    @Autowired
    CommonDao commonDao;

    @Resource
    private DaoTempSysCode daoTempSysCode;

    public int insert(SysCode syscode) {
        return daosyscode.insert(syscode);
    }

//    public int modify(SysCode syscode) {
//        return daosyscode.updateById(syscode);
//    }

    public int modify(SysCode syscode) {
        UpdateWrapper<SysCode> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("Parentcode", syscode.getParentcode()).eq("Syscode", syscode.getSyscode());
        Integer rows = daosyscode.update(syscode, updateWrapper);
        return rows;
    }

    public int delete(HashMap<String, Object> map) {
        return daosyscode.deleteByMap(map);
    }

    public List<SysCode> query(String parentcode, String syscode) {
        HashMap<String ,Object> map=new HashMap<>();
        map.put("parentcode",parentcode);
        map.put("syscode",syscode);
        return daosyscode.selectByMap(map);
    }

    public List<SysCode> queryParent(String parentcode) {
        HashMap<String ,Object> map=new HashMap<>();
        map.put("parentcode",parentcode);
        return daosyscode.selectByMap(map);
    }

    public Integer insertBatchSomeColumn(Collection<TempSysCode> entityList){
        return daoTempSysCode.insertBatchSomeColumn(entityList);
    }

    public List<SysCode> queryAll() {return daosyscode.selectList(null);}

    public List<Map<String, Object>> querySysCode(String parentcode) {
        return commonDao.selectSysCode(parentcode);
    }

    public Map<String, Object> selectGroupNode(String drpid) {
        return commonDao.selectGroupNode(drpid);
    }

    public Map<String, Object> selectDefstk(String stkid) {
        return commonDao.selectDefstk(stkid);
    }

    public void createCheckLog(Map<String, Object> intputMap) {
        commonDao.createCheckLog(intputMap);
    }

    public Integer transferData(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String startDate = dateFormat.format(new Date());

        Integer resultrow = 0;
        List<TempSysCode> tempSysCodes = new ArrayList<>();
        List<SysCode> sysCodeList = queryAll();

//        List<SysCode> codeList_1 = getPageInfo(1, 100, sysCodeList);
//        List<SysCode> codeList_2 = getPageInfo(2, 100, sysCodeList);
//        List<SysCode> codeList_3 = getPageInfo(3, 100, sysCodeList);

        System.out.println("rowcnt1:\t" + sysCodeList.size());

        tempSysCodes = sysCodeList.stream().map(x -> {
            TempSysCode tempSingle = new TempSysCode();
            BeanUtils.copyProperties(x, tempSingle);
            tempSingle.setCreateuser("SysTem");
            tempSingle.setCreatetime(new Date());
            return  tempSingle;
        }).collect(Collectors.toList());

        System.out.println("rowcnt2:\t" + tempSysCodes.size());


        int num=tempSysCodes.size() / 100;
        if(tempSysCodes.size() % 100 >= 0){
            num = num + 1;
        }
        int lastIndex=0;
        for (int i=0;i<num;i++){
            if(100*(i+1) > tempSysCodes.size()){
                lastIndex = tempSysCodes.size();
            }else{
                lastIndex= 100*(i+1);
            }
            //多线程批量添加
//            executorService.submit(new BatchInsertHzjjCodeThread(list.subList(i*100,lastIndex),bindCardInsertLogMapper));
            Integer row = insertBatchSomeColumn(tempSysCodes.subList(i*100,lastIndex));
            resultrow = resultrow + row;
        }

        String endDate = dateFormat.format(new Date());
        System.out.println("startDate:\t" + startDate);
        System.out.println("endDate:\t" + endDate);

        return resultrow;
    }

    public List<SysCode> sysCodeselectByMyWrapper() {
        QueryWrapper<SysCode> wrapper = new QueryWrapper();
        wrapper.eq("parentcode", "211").orderByAsc("syscode");
        //需要查询出来的字段
//        wrapper .select("syscode","descr");
        List<SysCode> sysCodeList = daosyscode.selectByMyWrapper(wrapper);
//        Wrapper<SysCode> wrapper = new WrapperAdapter();
        return sysCodeList;
    }


    /**
     * 处理List集合数据进行分页
     *
     * @param currentPage 当前页
     * @param pageSize    每页数据个数
     * @param list        进行分页的数据
     * @param <T>
     * @return
     */
    public static <T> List<T> getPageInfo(int currentPage, int pageSize, List<T> list) {
        List<T> newList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            int currIdx = (currentPage > 1 ? (currentPage - 1) * pageSize : 0);
            for (int i = 0; i < pageSize && i < list.size() - currIdx; i++) {
                newList.add(list.get(currIdx + i));
            }
        }
        return newList;
    }

}
