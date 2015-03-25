package tw.gov.moi.rl.component.rl0x001;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tw.gov.moi.ae.common.RisCommon;
import tw.gov.moi.ae.domain.ExecutantType;
import tw.gov.moi.ae.domain.RisDateTimeType;
import tw.gov.moi.dbo.DBFactory;
import tw.gov.moi.dbo.operator.DBSMain;
import tw.gov.moi.domain.Rldfm02mType;
import tw.gov.moi.domain.Rldfs227Type;
import tw.gov.moi.exception.RisBusinessException;
import tw.gov.moi.rl.common.Rl0xSetComponent;
import tw.gov.moi.rl.common.constant.RlConstant;
import tw.gov.moi.rl.domain.Rl0x327DTO;
import tw.gov.moi.rl.domain.Rl0xxxxxDTO;
import tw.gov.moi.rl.domain.Rl0xxxxxWhereDTO;
import tw.gov.moi.rl.utils.Rl0xxxxUtils;
import tw.gov.moi.rs.common.NoticeComponent;
import tw.gov.moi.rs.dto.ObnfDTO;
import tw.gov.moi.rs.dto.OtherNoticeObject;
import tw.gov.moi.rs.dto.PairEntry;
import tw.gov.moi.rs.utils.CommonUtils;
import tw.gov.moi.rs.utils.DBUtils.MyDateUtil;

@Component
public class Rl0x327Compment extends AbstractRl0xBean<Rl0x327DTO, Rldfm02mType, Rldfs227Type> {

    private static final transient Logger log = LoggerFactory.getLogger(Rl0x327Compment.class);

    private static final String TABLE_NAME = "RLDFM02M";

    @Autowired
    private transient RisCommon risCommon;

    /** The db factory. */
    @Autowired
    private transient DBFactory dbFactory;

    @Autowired
    private NoticeComponent noticeComponent;

    @Autowired
    private transient Rl0xSetComponent rl0xSetComponent;

    @Override
    protected List<ObnfDTO> doAddAction(DBSMain dbs, Rl0x327DTO dto, Rldfm02mType newType, ExecutantType exe)
            throws RisBusinessException {
        final String userId = this.risCommon.getUserId(exe);
        final String siteId = this.risCommon.getSiteId(exe);
        final String transactionId = this.risCommon.getTransactionId(exe);

        dbs.insert(newType, userId, siteId, transactionId);
        return this.doSendNotice(newType, dto, exe);

    }

    @Override
    protected List<ObnfDTO> doDeleteAction(DBSMain dbs, Rl0x327DTO dto, Rldfm02mType oldType, ExecutantType exe)
            throws RisBusinessException {
        final String userId = this.risCommon.getUserId(exe);
        final String siteId = this.risCommon.getSiteId(exe);
        final String transactionId = this.risCommon.getTransactionId(exe);
        dbs.delete(oldType, userId, siteId, transactionId);
        return this.doSendNotice(oldType, dto, exe);
    }

    List<ObnfDTO> doSendNotice(Rldfm02mType rldfm02mType, Rl0x327DTO dto, ExecutantType exe)
            throws RisBusinessException {
        log.debug("#. doSend .s");
        final String changeType = dto.getMainDTO().getBusinessType();

        List<ObnfDTO> obnfList = new ArrayList<ObnfDTO>();
        List<PairEntry> wkKey = new ArrayList<PairEntry>();// (更新值Map)
        wkKey.add(noticeComponent.getPairEntry("personId", rldfm02mType.getPersonId()));
        wkKey.add(noticeComponent.getPairEntry("siteId", rldfm02mType.getSiteId()));
        wkKey.add(noticeComponent.getPairEntry("spouseId", rldfm02mType.getSpouseId()));
        wkKey.add(noticeComponent.getPairEntry("updateYyymmdd", rldfm02mType.getUpdateYyymmdd()));
        wkKey.add(noticeComponent.getPairEntry("updateHhmmss", rldfm02mType.getUpdateHhmmss()));

        List<PairEntry> wkDataObject = CommonUtils.makeObjectMap(rldfm02mType);// (更新資料Map)
        {
            String wkProcessSeqNo = RlConstant.STRING_ONE;// (作業順序)
            String wkNoticeType = RlConstant.NOTICE_TYPE_L006;// (通報類別)
            String wkChgType = changeType;// (異動模式)
            List<OtherNoticeObject> wkNoticeObjectMap = null;//
            String wkSenderSiteId = this.risCommon.getSiteId(exe);// (發送端作業點代碼)
            String wkReceiverSiteId = this.risCommon.getSiteId(exe);// (目的地作業點代碼)
            Class<?> wkEntityClass = Rldfm02mType.class;// (欲更新或新增的DomainObject的Class名稱)
            RisDateTimeType wkNoticeDateTime = MyDateUtil.getRisDateTimeTypeNow();// 系統日期+系統時間
                                                                                  // (通報日期
                                                                                  // +時間String)
            String wkSystemCode = RlConstant.SYSTEM_CODE_RL;// (目的地系統代碼)

            ObnfDTO obnfDto = noticeComponent.createObnfDTO(//
                    wkProcessSeqNo, wkNoticeType, wkChgType, wkDataObject, wkKey, wkSenderSiteId, wkReceiverSiteId, //
                    wkSystemCode, wkEntityClass, wkNoticeDateTime, wkNoticeObjectMap);
            obnfDto.setWkOperationCode("0X327");
            log.debug("!. obnfDto = {}", ReflectionToStringBuilder.toString(obnfDto));
            obnfList.add(obnfDto);
        }

        return obnfList;

    }

    @Override
    protected List<ObnfDTO> doModifyAction(DBSMain dbs, Rl0x327DTO dto, Rldfm02mType oldType, Rldfm02mType newType,
            ExecutantType exe) throws RisBusinessException {
        final String userId = this.risCommon.getUserId(exe);
        final String siteId = this.risCommon.getSiteId(exe);
        final String transactionId = this.risCommon.getTransactionId(exe);

        final Rl0xxxxxDTO rl0xxxxxDTO = Rl0xxxxUtils.getModifyColumnsAndValue(newType, dto.getMainDTO());
        final List<String> names = rl0xxxxxDTO.getNames();
        final List<String> values = rl0xxxxxDTO.getValues();

        final Rl0xxxxxWhereDTO rl0xxxxxWhereDTO = Rl0xxxxUtils.getWhereAndValue(newType, dto.getMainDTO());
        final String whereCondition = rl0xxxxxWhereDTO.getWhere();
        final List<String> conditionValues = rl0xxxxxWhereDTO.getConditionValues();

        dbs.modifyByColumn(TABLE_NAME, names, values, whereCondition, conditionValues, userId, siteId, transactionId);

        this.rl0xSetComponent.unlockData(dto.getMainDTO(), oldType, exe);
        return this.doSendNotice(newType, dto, exe);

    }

    @Override
    protected void doModifyActionForApply(DBSMain dbs, Rl0x327DTO dto, Rldfm02mType oldType, Rldfm02mType newType,
            ExecutantType exe) throws RisBusinessException {

        // TODO Auto-generated method stub

    }

    @Override
    protected void doAddActionForApply(DBSMain dbs, Rl0x327DTO dto, Rldfm02mType newType, ExecutantType exe)
            throws RisBusinessException {
        if (newType.getSpouseId().matches("^[0-9]{10}")) {
            newType.setSpouseIdFlag(RlConstant.STRING_Y);
        } else {
            newType.setSpouseIdFlag(StringUtils.EMPTY);
        }

    }

    @Override
    protected void doDeleteActionForApply(DBSMain dbs, Rl0x327DTO dto, Rldfm02mType oldType, ExecutantType exe)
            throws RisBusinessException {
        // TODO Auto-generated method stub

    }

}
