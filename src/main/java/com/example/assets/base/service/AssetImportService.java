package com.example.assets.base.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.assets.base.entity.AssetImport;
import com.example.assets.base.entity.AssetImportDetail;
import com.example.assets.base.entity.AssetImportMaster;
import com.example.assets.base.mapper.DaoAssetImport;
import com.example.assets.base.mapper.DaoAssetImportDetail;
import com.example.assets.base.mapper.DaoAssetImportMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author GTM
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AssetImportService {

    @Autowired
    DaoAssetImportMaster daoAssetImportMaster;

    @Autowired
    DaoAssetImportDetail daoAssetImportDetail;

    @Autowired
    DaoAssetImport daoAssetImport;

    @Autowired
    PublicFuncTion publicFuncTion;

    @Autowired
    AssemImportbler assemImportbler;

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    Boolean lbexist = new Boolean(false);

    String tempBill = new String();
    String tempStkid = new String();
    String tempItemno = new String();
    Date tempStartdate = new Date();

    /**
     * 新增或修改资产导入单
     * @param assetImport 资产导入实体类
     * @return
     */
    public void saveAssetImport(AssetImport assetImport) {
        checkAssetImport(assetImport);
        List<AssetImportDetail> assetImportDetail = assetImport.getAssetList();
        AssetImportMaster master = assemImportbler.doToPo(assetImport);
        String asBill = asBill = master.getBill();
        //StringUtils.isEmpty(field)
        /** 主表保存
         * 如果有传入单号，则执行新增操作，否则执行保存动作
         * 新增的时候要先生成单据流水号　
         */
        if (asBill == null || asBill.trim().isEmpty()) {
            lbexist = false;
            try {
                asBill = publicFuncTion.getSerialno("8030", "0", assetImport.getGroup_drpid());
            } catch (Exception e) {
                throw new RuntimeException("流水号生成失败:"+ e.getCause().getCause().getMessage());
            }
            master.setBill(asBill);
            daoAssetImportMaster.insert(master);
        }else{
            lbexist = true;
            UpdateWrapper<AssetImportMaster> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("bill", assetImport.getBill());
            daoAssetImportMaster.update(master, updateWrapper);
        }


        List<AssetImportDetail>  oldDetail = new ArrayList<>();

//        Map<String, Object> dtlMap = new HashMap<>();
//        dtlMap.put("bill",asBill);
//        daoAssetImportDetail.deleteByMap(dtlMap);

        /** 明细删除
         * 修改时如果原单据中已经存在的记录在本次实体类中不存在，则将原单据记录进行删除。　
         *
         */
        if (lbexist == true){
            QueryWrapper<AssetImportDetail> queryDetail = new QueryWrapper<>();
            queryDetail.eq("bill", master.getBill());
            oldDetail = daoAssetImportDetail.selectList(queryDetail);
//            Iterator<AssetImportDetail> iter = oldDetail.iterator();
//            while (iter.hasNext()) {
//            AssetImportDetail oldDtl = (AssetImportDetail)iter.next();
            oldDetail.stream().forEach(oldDtl -> {
                assetImportDetail.stream().forEach(newdtl -> {
                    if (oldDtl.getBill().equalsIgnoreCase(newdtl.getBill())
                            && oldDtl.getBill().equalsIgnoreCase(newdtl.getStkid())
                            && oldDtl.getItemno().equalsIgnoreCase(newdtl.getItemno())
                            && oldDtl.getStartdate() == newdtl.getStartdate()) {
                    }else{
                        QueryWrapper<AssetImportDetail> delDtl = new QueryWrapper<>();
                        delDtl.eq("bill", oldDtl.getBill())
                                .eq("stkid", oldDtl.getStkid())
                                .eq("Department", oldDtl.getDepartment())
                                .eq("itemno", oldDtl.getItemno())
                                .eq("startdate", oldDtl.getStartdate());
                        this.daoAssetImportDetail.delete(delDtl);
                    }
                });
            });
//            for (AssetImportDetail oldDtl : oldDetail) {
//                for (AssetImportDetail newdtl : assetImportDetail){
//                    if (oldDtl.getBill().equalsIgnoreCase(newdtl.getBill())
//                                && oldDtl.getBill().equalsIgnoreCase(newdtl.getStkid())
//                                && oldDtl.getItemno().equalsIgnoreCase(newdtl.getItemno())
//                                && oldDtl.getStartdate() == newdtl.getStartdate()) {
//                    }else{
//                        QueryWrapper<AssetImportDetail> delDtl = new QueryWrapper<>();
//                            delDtl.eq("bill", oldDtl.getBill())
//                                    .eq("stkid", oldDtl.getStkid())
//                                    .eq("Department", oldDtl.getDepartment())
//                                    .eq("itemno", oldDtl.getItemno())
//                                    .eq("startdate", oldDtl.getStartdate());
//                        this.daoAssetImportDetail.delete(delDtl);
//
////                      Map<String, Object> dtlMap = new HashMap<>();
////                      dtlMap.put("stkid",oldDtl.getStkid());
////                      dtlMap.put("Department",oldDtl.getDepartment());
////                      dtlMap.put("itemno",oldDtl.getItemno());
////                      dtlMap.put("startdate",oldDtl.getStartdate());
////                      daoAssetImportDetail.deleteByMap(dtlMap);
//                      break;
//                    }
//                }
//            }
        }

        /** 明细保存
         * 如果有传入单号，则执行新增操作，否则执行保存动作
         *
         */
        assetImportDetail.forEach(rowData -> {
            if (lbexist == false || rowData.getBill() == null || rowData.getBill().isEmpty()){
                rowData.setBill(master.getBill());
                daoAssetImportDetail.insert(rowData);
            }else {
//                List<AssetImportDetail> james = oldDetail.stream()
//                        .filter(dtl -> "stkid".equals(rowData.getStkid()))
//                        .findAny()
//                        .orElse(null);

//                Boolean recoExists = oldDetail.stream().anyMatch(stkid-->rowData.getStkid().equals(stkid));
//                Boolean recoExists = oldDetail.stream().anyMatch(stkid -> rowData.getStkid());

//                Long ifcount = oldDetail.stream()
//                        .collect(Collectors.groupingBy(a -> a.getStkid(), Collectors.counting()))
//                        .entrySet().stream().filter(entry -> entry.getValue() > 1).map(entry -> entry.getKey())
//                        .collect(Collectors.toList()).size();

                rowData.setBill(master.getBill());
                QueryWrapper<AssetImportDetail> queryDtl = new QueryWrapper<>();
                queryDtl.eq("bill",rowData.getBill())
                        .eq("stkid", rowData.getStkid())
                        .eq("Department", rowData.getDepartment())
                        .eq("itemno", rowData.getItemno())
                        .eq("startdate", rowData.getStartdate());
                if (daoAssetImportDetail.exists(queryDtl)){
                    UpdateWrapper<AssetImportDetail> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("bill", rowData.getBill())
                            .eq("stkid", rowData.getStkid())
                            .eq("Department", rowData.getDepartment())
                            .eq("itemno", rowData.getItemno())
                            .eq("startdate", rowData.getStartdate());
                    daoAssetImportDetail.update(rowData, updateWrapper);
                }else{
                    daoAssetImportDetail.insert(rowData);
                };
            }
        });

//        daoAssetImport.saveDetail(assetImportDetail);
//
//        for (AssetImportDetail detail: assetImportDetail){
//            detail.setBill(asBill);
//            daoAssetImportDetail.insert(detail);

//            if (detail.getBill() == null || !asBill.equalsIgnoreCase( detail.getBill()) ){
//                detail.setBill(asBill);
//                daoAssetImportDetail.insert(detail);
//            }else{
//                UpdateWrapper<AssetImportDetail> updateWrapper = new UpdateWrapper<>();
//                updateWrapper.eq("bill", detail.getBill()).
//                        eq("stkid", detail.getStkid()).
//                        eq("Department", detail.getDepartment()).
//                        eq("itemno", detail.getItemno()).
//                        eq("startdate", detail.getStartdate());
//                Integer rows = daoAssetImportDetail.update(detail, updateWrapper);
//            }
//        }


//        daoAssetImportDetail.insertBatch(assetImportDetail);
        System.out.println("BillNo\t" + master.getBill());
    }

    public AssetImportMaster querybill(String bill) {
        QueryWrapper<AssetImportMaster> queryMaster = new QueryWrapper<>();
        queryMaster.eq("bill", bill);
        AssetImportMaster assetImport = daoAssetImportMaster.selectOne(queryMaster);
        if (assetImport != null){
            QueryWrapper<AssetImportDetail> queryDtl = new QueryWrapper<>();
            queryDtl.eq("bill",bill);
            List<AssetImportDetail> assetImportDetail = daoAssetImportDetail.selectList(queryDtl);
            assetImport.setAssetList(assetImportDetail);
        }else{
            throw new RuntimeException("单号[ "+bill+" ]不存在");
        }

//        AssetImportMaster assetImport = daoAssetImport.queryMaster(bill);
//        if (assetImport != null){
//            List<AssetImportDetail> assetImportDetail = daoAssetImport.queryDetail(bill);
//            assetImport.setAssetList(assetImportDetail);
//        }
        return assetImport;
    }
    public void checkAssetImport(AssetImport assetImport) {
        List<AssetImportDetail> assetImportDetail = assetImport.getAssetList();
        if (assetImportDetail == null || assetImportDetail.size() < 1){
            throw new RuntimeException("明细窗口数据不能空");
        }

        assetImportDetail.stream().forEach(x -> {
            String check1 = x.getRowno();
            assetImportDetail.stream().forEach(y -> {
                String check2 = y.getRowno();
                if(check1.equals(check2) && x != y){
                    throw new RuntimeException("行号重复:[ " + check1 + " ]");
                }
            });
        });

        assetImportDetail.stream().forEach(x -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String startDate = dateFormat.format(x.getStartdate());
            String check1 = x.getStkid() + "-->"+ x.getDepartment() + "-->"+ x.getItemno() + "-->"+ startDate;
            assetImportDetail.stream().forEach(y -> {
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                String startDate2 = dateFormat.format(y.getStartdate());
                String check2 = y.getStkid() + "-->"+ y.getDepartment() + "-->"+ y.getItemno() + "-->"+ startDate2;
                if(check1.equals(check2) && x != y){
                    throw new RuntimeException("门店+部门+商品编码+开始日期不能重复:[ " + check1 + " ]");
                }
            });
        });

        if (assetImport.getGroup_drpid() == null || assetImport.getGroup_drpid().isEmpty()) {
            throw new RuntimeException("集团节点不允许为空");
        }
//        @Select("SELECT count(1) FROM t_bp_groupnode where drpid = #{drpid}");
//        Long llcnt = BaseMapper.selectOne(@Param("drpid") String assetImport.getGroup_drpid());
        Integer llCnt = new Integer(0);
        String lsSql = "select count(1) from t_bp_groupnode where drpid = '" + assetImport.getGroup_drpid() + "'";
        try{
            llCnt = jdbcTemplate.queryForObject(lsSql, Integer.class);
        }catch (Exception e){
            throw new RuntimeException("查询集团节点失败:"+ e.getCause().getMessage());
        }

//        lsSql = "select drpname from t_bp_groupnode where drpid = :drpid";         String result = (String) query.getSingleResult();
//        lsSql = "select Count(1) from t_bp_groupnode where drpid = :drpid";     Integer result = (Integer) query.getSingleResult();
//        lsSql = "select drpname, group_bookid, flag_cancel from t_bp_groupnode where drpid = :drpid";
//        Query query = entityManager.createNativeQuery(lsSql);
//        query.setParameter("drpid", assetImport.getGroup_drpid());
//        Object result = (Object) query.getSingleResult();

        if (llCnt < 1){
            throw new RuntimeException("集团节点[ "+assetImport.getGroup_drpid()+" ]不存在");
        }
        Double sumCleanvalue = assetImportDetail.stream().map(AssetImportDetail::getCleanvalue).reduce(Double::sum).orElse(new Double(0L) );
        if (sumCleanvalue <= 0){
            throw new RuntimeException("资产净值合计值不能小于或等于");
        }

//        Double sumNotaxamt = assetImportDetail.stream().map(AssetImportDetail::getNotaxamt).reduce(Double::sum).orElse(new Double(0L) );
//        if (sumNotaxamt <= 0){
//            throw new RuntimeException("资产未税金额合计值不能小于或等于");
//        }

        for (AssetImportDetail dtldata : assetImportDetail) {
            if (dtldata.getDepotqty() <= 0){
                throw new RuntimeException("行[ "+dtldata.getRowno()+" ],数据异常，资产数量不能小于零");
            }
            if (dtldata.getDepotqty()%1 != 0){
                throw new RuntimeException("行[ "+dtldata.getRowno()+" ],数据异常，资产数量不能有小数");
            }
            if (dtldata.getPurprice() <= 0){
                throw new RuntimeException("行[ "+dtldata.getRowno()+" ],数据异常，资产单价不能小于零");
            }

            Map<String, Object> item = new HashMap<>();
            String asSql = "select ItemName,model,vendorid,flag_tax, purprice, tax \n" +
                    "from t_asset_item where itemno =?";
            try{
                item = jdbcTemplate.queryForMap(asSql, dtldata.getItemno());
            }catch (Exception e){
                e.printStackTrace();
                throw new NullPointerException("行[ "+dtldata.getRowno()+" ],资产商品[ "+dtldata.getItemno()+" ]不存在");
            }

            Map<String, Object> defstk = new HashMap<>();
            String getstkidsql = "select name_stk,group_drpid \n" +
                    "from s_defstk where stkid =?";
            try{
                defstk = jdbcTemplate.queryForMap(getstkidsql, dtldata.getStkid());
            }catch (Exception e){
                e.printStackTrace();
                throw new NullPointerException("行[ "+dtldata.getRowno()+" ],门店编码[ "+dtldata.getStkid()+" ]不存在");
            }
            if (!assetImport.getGroup_drpid().equalsIgnoreCase(defstk.get("group_drpid").toString())){
                throw new RuntimeException("行[ "+dtldata.getRowno()+" ]," +
                        "门店编码[ "+dtldata.getStkid()+" ]所在集团节点[ "+ defstk.get("group_drpid").toString()+
                        " ]和表头所在集团节点[ "+assetImport.getGroup_drpid()+" ]不一致");
            }
        }
    }

    public IPage<AssetImportMaster> queryHdr(long currentPage, long pageSize, String group_drpid) {
        QueryWrapper<AssetImportMaster> queryMaster = new QueryWrapper<>();
        queryMaster.eq("group_drpid", group_drpid);
        IPage<AssetImportMaster> masterIPage = new Page(currentPage, pageSize);
        masterIPage=daoAssetImportMaster.selectPage(masterIPage, queryMaster);
        return masterIPage;
    }

    public IPage<AssetImportMaster> selectMaster(long currentPage, long pageSize, QueryWrapper<AssetImportMaster> queryMaster) {
        IPage<AssetImportMaster> masterIPage = new Page<>(currentPage, pageSize);
        return daoAssetImportMaster.selectPage(masterIPage, queryMaster);
    }

}
