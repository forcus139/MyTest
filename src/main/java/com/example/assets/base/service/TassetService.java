package com.example.assets.base.service;

import com.example.assets.base.entity.TassetHjxc;
import com.example.assets.base.entity.TempTassetHJxc;
import com.example.assets.base.mapper.DaoTassetHjxc;
import com.example.assets.base.mapper.DaoTempTassetHJxc;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author GengTianMing
 * @since 2023/02/15 14:17
 **/
@Service
//@SneakyThrows(Exception.class)
//@Transactional
@Transactional(rollbackFor = Exception.class)
public class TassetService {
    @Resource
    private DaoTassetHjxc daoTassetHJxc;

    @Resource
    private DaoTempTassetHJxc daoTempTassetHJxc;

    public static final Integer BATCH = 50;

    public Integer transferData(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String startDate = dateFormat.format(new Date());

        Integer resultrow = 0;
        List<TempTassetHJxc> tempTassetHJxcs = new ArrayList<>();
        List<TassetHjxc> tassetHJxcs = daoTassetHJxc.selectList(null);

        System.out.println("rowcnt1:\t" + tassetHJxcs.size());

        tempTassetHJxcs = tassetHJxcs.stream().map(x -> {
            TempTassetHJxc tempSingle = new TempTassetHJxc();
            BeanUtils.copyProperties(x, tempSingle);
            tempSingle.setCreateuser("SysTem");
            tempSingle.setCreatetime(new Date());
            return  tempSingle;
        }).collect(Collectors.toList());

        System.out.println("rowcnt2:\t" + tassetHJxcs.size());

        int num=tempTassetHJxcs.size() / BATCH;
        if(tempTassetHJxcs.size() % BATCH >= 0){
            num = num + 1;
        }
        int lastIndex=0;
        for (int i=0;i<num;i++){
            if(BATCH*(i+1) > tempTassetHJxcs.size()){
                lastIndex = tempTassetHJxcs.size();
            }else{
                lastIndex= BATCH*(i+1);
            }
            //多线程批量添加
//            executorService.submit(new BatchInsertHzjjCodeThread(list.subList(i*100,lastIndex),bindCardInsertLogMapper));
            Integer row = daoTempTassetHJxc.insertBatchSomeColumn(tempTassetHJxcs.subList(i*BATCH,lastIndex));
            resultrow = resultrow + row;
        }
        tempTassetHJxcs.clear();

        String endDate = dateFormat.format(new Date());
        System.out.println("startDate:\t" + startDate);
        System.out.println("endDate:\t" + endDate);

        return resultrow;
    }
}
