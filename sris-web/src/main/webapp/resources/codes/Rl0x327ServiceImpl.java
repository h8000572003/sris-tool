package tw.gov.moi.rl.rl0X327.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.gov.moi.ae.common.RisCommon;
import tw.gov.moi.ae.domain.ExecutantType;
import tw.gov.moi.dbo.DBFactory;
import tw.gov.moi.dbo.operator.DBSMain;
import tw.gov.moi.domain.Rldfm02mType;
import tw.gov.moi.domain.Rldfs227Type;
import tw.gov.moi.domain.Rldfv002Type;
import tw.gov.moi.domain.Rldfv003Type;
import tw.gov.moi.exception.RisBusinessException;
import tw.gov.moi.rl.common.AssignComponent;
import tw.gov.moi.rl.common.Rl0xSetComponent;
import tw.gov.moi.rl.common.constant.RlConstant;
import tw.gov.moi.rl.component.rl0x001.Rl0x327Compment;
import tw.gov.moi.rl.domain.Rl0x001DTOResult;
import tw.gov.moi.rl.domain.Rl0x327DTO;
import tw.gov.moi.rl.domain.Rl0xApplyDTO;
import tw.gov.moi.rl.domain.Rl0xSumitDTO;

/**
 * 戶號配賦職權更正處理服務
 * 
 * @author Andy
 * 
 */

@Service("rl0x327Service")
public class Rl0x327ServiceImpl implements Rl0x327Service {

    private static final transient Logger logger = LoggerFactory.getLogger(Rl0x327ServiceImpl.class);

    /** The db factory. */
    @Autowired
    private transient DBFactory dbFactory;

    /** The ris common. */
    @Autowired
    private transient RisCommon risCommon;

    @Autowired
    private transient Rl0xSetComponent rl0xSetComponent;

    @Autowired
    private transient AssignComponent assignComponent;

    final static String TYPE = "type";

    @Autowired
    private transient Rl0x327Compment rl0x327Compent;

    @Override
    public void initWebUI(Rl0x327DTO dto, ExecutantType exe) throws RisBusinessException {
        dto.setSelectData(this.getCommonDto(exe));
        dto.setTitle(this.rl0xSetComponent.getTableName(dto.getMainDTO().getTableCode(), exe));// 加入標頭
        if (dto.getMainDTO().getSource() != null) {
            dto.setReason(((Rldfs227Type) this.rl0xSetComponent.getRecoredData(dto.getMainDTO(), exe))
                    .getUpdateReason());
        } else {
            dto.setReason(StringUtils.EMPTY);
        }

        this.rl0xSetComponent.initWebUI(dto.getMainDTO(), dto.getQuertDto(), dto.getEditResult(),
                this.getCommonDto(exe), exe);

    }

    @Override
    public void doQuery(Rl0x327DTO dto, ExecutantType exe) throws RisBusinessException {

        DBSMain dbi = null;
        List<Rl0x001DTOResult> rl0x001DTOResult = dto.getQuertDto();
        try {
            this.rl0xSetComponent.doCheckQueryConToThrowMessage(dto.getQuertDto());
            dbi = dbFactory.getDB(risCommon.getSiteId(exe));

            dto.getResutlts().clear();
            dto.getResutlts().addAll(
                    rl0xSetComponent.getQueryResult(dto.getMainDTO(), dto.getQuertDto(), Rldfm02mType.class, exe));

            if (dto.getResutlts().isEmpty()) {
                throw new RisBusinessException(tw.gov.moi.rs.common.constant.RsCDMesg.TX6543E);
            }

        } catch (RisBusinessException e) {
            throw e;
        }

        catch (Exception e) {
            logger.debug(e.getMessage());
            throw new RisBusinessException(tw.gov.moi.rs.common.constant.RsCDMesg.TX6397E);
        } finally {
            if (dbi != null) {
                dbi.close();
            }
        }
    }

    @Override
    public void doApply(Rl0x327DTO dto, ExecutantType exe) throws RisBusinessException {

        final String userId = risCommon.getUserId(exe);
        final String siteId = risCommon.getSiteId(exe);
        final String transactionId = risCommon.getTransactionId(exe);

        DBSMain dbi = null;

        final String type = dto.getMainDTO().getBusinessType();

        // /////////////客製化//////////////////
        try {
            this.doCheckCode(dto, exe);// 20130411新增驗證

            dbi = dbFactory.getDB(siteId);

            final Rldfs227Type rec = new Rldfs227Type();
            rec.setUpdateReason(dto.getReason());

            Rl0xApplyDTO<Rl0x327DTO, Rldfs227Type> rl0xApplyDTO = new Rl0xApplyDTO<Rl0x327DTO, Rldfs227Type>();
            rl0xApplyDTO.setDbs(dbi);
            rl0xApplyDTO.setDto(dto);
            rl0xApplyDTO.setRec(rec);
            rl0xApplyDTO.setRl0x001DTO(dto.getMainDTO());

            this.rl0x327Compent.doApplyWork(rl0xApplyDTO, dto.getSelectData(), exe);

        } catch (RisBusinessException e) {
            logger.debug(e.getMessage());
            throw e;
        }

        catch (Exception e) {
            logger.debug(e.getMessage());
            throw new RisBusinessException(tw.gov.moi.rs.common.constant.RsCDMesg.TX6397E, e.getMessage());
        } finally {
            if (dbi != null) {
                dbi.close();
            }
        }
        this.doSumit(dto, exe);
        // ///////////////////////////
    }

    /**
     * 一定有的預設值 ex siteId
     * 
     * @param exe
     * @return
     */
    private Rldfm02mType getCommonDto(ExecutantType exe) {
        // /////////////客製化//////////////////
        Rldfm02mType type = new Rldfm02mType();

        type.setSiteId(risCommon.getSiteId(exe));

        return type;
    }

    @Override
    public List<Rldfv002Type> getRldv002(Rl0x327DTO dto, ExecutantType exe) {

        try {
            return rl0xSetComponent.getRldv002(dto.getMainDTO().getTableCode(), exe);
        } catch (Exception e) {
            logger.debug(e.getMessage());

        }

        return null;
    }

    @Override
    public void setQueryResult(Rl0x327DTO dto, ExecutantType exe) throws RisBusinessException {

        this.rl0xSetComponent.setEditViewbyDomainType(dto.getEditResult(), dto.getSelectData(), exe);
        this.rl0xSetComponent.setEditRightViewbyDomainType(dto.getEditResult(), dto.getSelectData(), exe);
        this.rl0xSetComponent.chechIsChange(dto.getEditResult());

        for (Rl0x001DTOResult ui : dto.getEditResult()) {
            this.rl0xSetComponent.setcodeLabel(ui);
        }

    }

    @Override
    @Transactional(timeout = RlConstant.TIMEOUT)
    public void doSumit(Rl0x327DTO dto, ExecutantType exe) throws RisBusinessException {
        logger.debug("doSumit inii....");

        DBSMain dbs = null;

        // / // ////客製化///////////////////////
        try {
            dbs = dbFactory.getDB(this.risCommon.getSiteId(exe));

            final Rl0xSumitDTO<Rl0x327DTO> rl0xSumitDTO = new Rl0xSumitDTO<Rl0x327DTO>();
            {
                rl0xSumitDTO.setDbs(dbs);
                rl0xSumitDTO.setDto(dto);
                rl0xSumitDTO.setRl0x001DTO(dto.getMainDTO());

            }
            this.rl0x327Compent.doSumitWork(rl0xSumitDTO, exe);

        } catch (RisBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new RisBusinessException(tw.gov.moi.rs.common.constant.RsCDMesg.TX6397E, e.getMessage());
        } finally {
            if (dbs != null) {

                dbs.close();

            }
        }

        // ////////////////////////////////////////////

    }

    @Override
    @Transactional(timeout = RlConstant.TIMEOUT)
    public void doReject(Rl0x327DTO dto, ExecutantType exe) throws RisBusinessException {

        final String userId = risCommon.getUserId(exe);
        final String siteId = risCommon.getSiteId(exe);
        final String transactionId = risCommon.getTransactionId(exe);
        DBSMain dbs = null;
        try {
            dbs = dbFactory.getDB(siteId);
            logger.debug("doReject inii....");
            Rldfv003Type rldfv003Type = rl0xSetComponent.getRldfv003(dto.getMainDTO(), exe);
            rldfv003Type.setStatus(rl0xSetComponent.REJECT);

            Rldfs227Type recode = (Rldfs227Type) rl0xSetComponent.getRecoredData(dto.getMainDTO(), exe);// 紀錄檔
            recode.setApproverId(risCommon.getUserId(exe));
            recode.setApproverName(risCommon.getUserName(exe));
            recode.setStatus(rl0xSetComponent.REJECT);
            dbs.modify(rldfv003Type, userId, siteId, transactionId);
            dbs.modify(recode, userId, siteId, transactionId);
        } catch (RisBusinessException e) {
            throw e;
        } catch (Exception e) {
            if (dbs != null) {
                dbs.close();
            }
        }

    }

    @Override
    @Transactional(timeout = RlConstant.TIMEOUT)
    public void lockData(Rl0x327DTO dto, ExecutantType exe) throws RisBusinessException {
        try {
            this.rl0xSetComponent.lockData(dto.getMainDTO(), dto.getSelectData(), exe);
        } catch (RisBusinessException e) {
            throw e;
        }
    }

    @Override
    @Transactional(timeout = RlConstant.TIMEOUT)
    public void unlockData(Rl0x327DTO dto, ExecutantType exe) throws RisBusinessException {
        this.rl0xSetComponent.unlockDataToQuery(dto.getMainDTO(), dto.getSelectData(), exe);
    }

    @Override
    public void cleanWebUI(Rl0x327DTO dto, ExecutantType exe) throws RisBusinessException {
        this.rl0xSetComponent.cleanWebUI(dto.getEditResult(), exe);
        this.rl0xSetComponent.cleanWebUI(dto.getQuertDto(), exe);
    }

    @Override
    public void refreshUI(Rl0x327DTO dto, ExecutantType exe) throws RisBusinessException {
        this.rl0xSetComponent.refreshUI(dto.getQuertDto(), dto.getEditResult(), this.getCommonDto(exe), exe);

    }

    @Override
    public void setCodeLabele(Rl0x327DTO dto, ExecutantType exe) throws RisBusinessException {
        for (Rl0x001DTOResult ui : dto.getEditResult()) {
            this.rl0xSetComponent.setcodeLabel(ui);
        }

    }

    @Override
    @Transactional(timeout = RlConstant.TIMEOUT)
    public void doEditApply(Rl0x327DTO dto, ExecutantType exe) throws RisBusinessException {

        Rldfs227Type recode = (Rldfs227Type) rl0xSetComponent.getRecoredData(dto.getMainDTO(), exe);// 紀錄檔
        Rldfm02mType oldData = (Rldfm02mType) this.rl0xSetComponent.getDomainTypeOneByEditView(dto.getEditResult(),
                Rldfm02mType.class, exe);// 從畫面取出來的舊資料
        Rldfm02mType newData = (Rldfm02mType) this.rl0xSetComponent.getDomainTypeTwoByEditView(dto.getEditResult(),
                Rldfm02mType.class, exe);// 從畫面取出來的資料

        Rldfm02mType oldObject = this.rl0xSetComponent.getOldRecored(recode, Rldfm02mType.class);// 從記錄檔取出來的舊資料
        Rldfm02mType newObject = this.rl0xSetComponent.getNewRecored(recode, Rldfm02mType.class);// 從記錄檔取出來的新資料

        final String type = dto.getMainDTO().getBusinessType();

        this.doCheckCode(dto, exe);// 20130411新增驗證

        if (StringUtils.equals("A", type)) {

            final Rldfm02mType data = this.getMerger(newObject, oldData, dto.getEditResult());

            this.rl0xSetComponent.setNewRecored(recode, data);
        }
        if (StringUtils.equals("D", type)) {
            final Rldfm02mType data = this.getMerger(oldObject, oldData, dto.getEditResult());

            this.rl0xSetComponent.setOldRecored(recode, data);
        }
        if (StringUtils.equals("M", type)) {
            final Rldfm02mType mergerNewData = this.getMerger(newObject, newData, dto.getEditResult());
            this.rl0xSetComponent.setNewRecored(recode, mergerNewData);
        }

        final String userId = risCommon.getUserId(exe);
        final String siteId = risCommon.getSiteId(exe);
        final String transactionId = risCommon.getTransactionId(exe);

        DBSMain dbi = null;

        try {
            Rldfv003Type rldfv003Type = this.rl0xSetComponent.getRldfv003(dto.getMainDTO(), exe);
            dbi = dbFactory.getDB(siteId);
            recode.setStatus(Rl0xSetComponent.ACTION);
            recode.setUpdateReason(dto.getReason());
            rldfv003Type.setStatus(Rl0xSetComponent.ACTION);
            dbi.modify(recode, userId, siteId, transactionId);
            dbi.modify(rldfv003Type, userId, siteId, transactionId);

        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            throw new RisBusinessException(tw.gov.moi.rs.common.constant.RsCDMesg.TX6397E);
        } finally {
            if (dbi != null) {
                dbi.close();
            }
        }

    }

    private Rldfm02mType getMerger(Rldfm02mType source, Rldfm02mType tar, List<Rl0x001DTOResult> resultUI) {
        return this.rl0xSetComponent.secondMerger(source, tar, resultUI);
    }

    @Override
    public void verifyData(Rl0x327DTO dto, ExecutantType exe) throws RisBusinessException {
        this.rl0xSetComponent.doCheckNeeedValue(dto.getEditResult(), dto.getMainDTO(), exe);
        this.rl0xSetComponent.doCheckTheSameRecord(dto.getMainDTO(), dto.getEditResult(), exe);
        this.verifyDataEdit(dto, exe);
    }

    @Override
    public void verifyDataEdit(Rl0x327DTO dto, ExecutantType exe) throws RisBusinessException {
        this.rl0xSetComponent.doCheckNeeedValue(dto.getEditResult(), dto.getMainDTO(), exe);
        this.rl0xSetComponent.doCheckPkToThrowMessage(dto.getEditResult());
        this.doCheckDataAndVerifyData(dto, exe);

        dto.setCheckCode(this.rl0xSetComponent.getCheckData(dto.getEditResult()));// 取得檢查碼

    }

    /**
     * 驗證共用部分
     * 
     * @param dto
     * @param exe
     */
    private void doCheckDataAndVerifyData(Rl0x327DTO dto, ExecutantType exe) {

        final String businessType = dto.getMainDTO().getBusinessType();

        if (StringUtils.equals("A", businessType)) {
            /*********************************************/
            this.rl0xSetComponent.checkThePkValue(this.rl0xSetComponent.secondMerger(dto.getSelectData(),
                    this.rl0xSetComponent.getDomainTypeOneByEditView(dto.getEditResult(), dto.getMainDTO()
                            .getTarClass(), exe), dto.getEditResult()), dto.getMainDTO(), exe);
            /*********************************************/
        }
        if (StringUtils.equals("D", businessType)) {
            /*********************************************/

            /*********************************************/

        }
        if (StringUtils.equals("M", businessType)) {

            /*********************************************/

            /*********************************************/
        }

    }

    /**
     * 檢查碼檢查
     * 
     * @param dto
     * @param exe
     */
    private void doCheckCode(Rl0x327DTO dto, ExecutantType exe) {

        this.rl0xSetComponent.doCheckCode(dto.getCheckCode(), dto.getEditResult(), exe);
    }

    @Override
    public Rldfm02mType getUILeftValue(Rl0x327DTO dto, ExecutantType exe) throws RisBusinessException {
        // TODO Auto-generated method stub
        return this.rl0xSetComponent.getDomainTypeOneByEditView(dto.getEditResult(), Rldfm02mType.class, exe);
    }

    @Override
    public Rldfm02mType getUIRightValue(Rl0x327DTO dto, ExecutantType exe) throws RisBusinessException {
        // TODO Auto-generated method stub
        return this.rl0xSetComponent.getDomainTypeTwoByEditView(dto.getEditResult(), Rldfm02mType.class, exe);
    }

}
