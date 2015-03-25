package tw.gov.moi.rl.rl0x300.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.WindowScoped;
import org.cdisource.springintegration.Spring;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.gov.moi.ae.checker.AdvancePattern;
import tw.gov.moi.ae.checker.DomainCheckerComponent;
import tw.gov.moi.ae.common.RisCommon;
import tw.gov.moi.ae.domain.ExecutantType;
import tw.gov.moi.ae.exception.RisExceptionCatcher;
import tw.gov.moi.ae.web.RisFacesTool;
import tw.gov.moi.domain.Rldfm02mType;
import tw.gov.moi.exception.InvalidException;
import tw.gov.moi.exception.RisBusinessException;
import tw.gov.moi.rl.domain.Rl0x001DTOResult;
import tw.gov.moi.rl.domain.Rl0x327DTO;
import tw.gov.moi.rl.rl0X327.service.Rl0x327Service;
import tw.gov.moi.rl.rl0x000.web.Rl0x001DTOBean;
import tw.gov.moi.rl.rl0x000.web.Rlx001SetBean;
import tw.gov.moi.util.RisUserUtil;

/**
 * 
 * 
 * @author Derek Wang
 * 
 */
@WindowScoped
@RisExceptionCatcher
@Named("rl0x327Controller")
public class Rl0x327Controller implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4507955109815835600L;

    /** The Constant LOGGER. */
    private static final transient Logger LOG = LoggerFactory.getLogger(Rl0x327Controller.class);

    /** The executant type. */
    private transient ExecutantType executantType;

    /** The ris common. */
    @Inject
    @Spring(name = "risCommon")
    private transient RisCommon risCommon;

    @Inject
    @Spring(name = "rl0x327Service")
    private transient Rl0x327Service service;

    private Rl0x327DTO dto;

    @Inject
    private Rl0x001DTOBean rl0x001DTOBean;

    @Inject
    @Spring(name = "risUserUtil")
    private transient RisUserUtil risUserUtil;

    @Inject
    @Spring(name = "risFacesTool")
    private transient RisFacesTool risFacesTool;

    private transient List<SelectItem> radioOnItem = new ArrayList<SelectItem>();

    // 戶籍員畫面
    private final static String TO_MAIN = "../rl0x001.xhtml?faces-redirect=true";

    // 驗證畫面
    private final static String TO_VERFITY = "/pages/func/rl0x002/rl0x002.xhtml?faces-redirect=true";

    private final static String TEMPLATE_URL = ".xhtml?faces-redirect=true";

    private String selectCode = StringUtils.EMPTY;

    private Rl0x001DTOResult selectInfo = null;

    private String categoryCode = StringUtils.EMPTY;

    @Inject
    private Rlx001SetBean rlx001SetBean;

    /** 共用元件:DomainCheckerComponent. */
    @Inject
    @Spring(name = "domainCheckerComponent")
    private transient DomainCheckerComponent domainCheckerComponent;

    public void init() {
        // 設定bean-->每次轉頁前都要寫下面那段
        dto.setMainDTO(rl0x001DTOBean.getMainDTO());

        this.executantType = risUserUtil.getExecutant();
        this.rlx001SetBean.setUpdataId("rl0x327");
        this.rlx001SetBean.setAutoLists(Arrays.asList(new String[] { "acceptSiteId", "acceptAdminOfficeCode" }));// autoComplete
        // 初始化畫面
        this.initMenu();
        this.initValue();// 初始化資料
        // ////////////

    }

    private void initValue() {
        dto.setVerify(false);
        dto.setCheckCode(null);
    }

    public String toVerity() {
        this.dto = null;
        return TO_VERFITY;
    }

    public void openDialog(Rl0x001DTOResult info) {

        rlx001SetBean.openDialog(dto.getMainDTO(), info);
    }

    /**
     * 判斷是否修改
     */
    public void chagneValueSetStart(Rl0x001DTOResult info) {

        this.chagneValueToAction();

    }

    /**
     * 畫面參數變動
     */
    public void chagneValueToAction() {

        /**************************/
        /** 客製化 **/

        /**************************/
        this.service.setCodeLabele(dto, executantType);
        RequestContext.getCurrentInstance().update("rl0x327");

    }

    public void chagneValueToActionForEdit() {

        /**************************/
        /** 客製化 **/

        /**************************/
        this.service.setCodeLabele(dto, executantType);
        RequestContext.getCurrentInstance().update("rl0x327");

    }

    /**
     * 初始化查詢條件
     */
    private void initMenu() {
        this.service.initWebUI(dto, executantType);

    }

    /**
     * 查詢資料
     */
    public void doQuery() {
        LOG.info("====Rl0x327Controller toQueryPage start====");
        try {
            service.doQuery(dto, executantType);

            Collections.sort(dto.getResutlts(), new Comparator<Rldfm02mType>() {

                @Override
                public int compare(Rldfm02mType o1, Rldfm02mType o2) {

                    // TODO Auto-generated method stub
                    return -((o1.getUpdateYyymmdd() + o1.getUpdateHhmmss()).compareTo(o2.getUpdateYyymmdd()
                            + o2.getUpdateHhmmss()));
                }

            });
        } catch (RisBusinessException e) {
            throw e;
        }
        LOG.info("====Rl0x327Controller toQueryPage end====");
    }

    /**
     * 返回查詢頁面
     * 
     * @return
     */
    public String toQuery() {
        dto.setSelectData(dto.getSelectData());

        this.service.unlockData(dto, executantType);

        this.service.refreshUI(dto, executantType);
        dto.setSelectData(null);
        this.initValue();
        // dto.getResutlts().clear();
        // 解鎖】
        return "rl0x327_query.xhtml?faces-redirect=true";
    }

    /**
     * 申請 】
     * 
     * @return
     */
    public String doApply() {

        try {

            this.baseApply();
        } catch (RisBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new RisBusinessException(tw.gov.moi.rs.common.constant.RsCDMesg.TX6881E);
        }
        this.risFacesTool.addMessage(tw.gov.moi.rs.common.constant.RsCDMesg.TX6000S, "");
        return TO_MAIN;
    }

    public String doEditApply() {
        try {
            this.service.doEditApply(dto, executantType);
            dto = null;
        } catch (RisBusinessException e) {
            throw e;
        }
        this.risFacesTool.addMessage(tw.gov.moi.rs.common.constant.RsCDMesg.TX6000S, "");
        return TO_MAIN;
    }

    public void baseApply() {
        service.doApply(dto, executantType);
        dto = null;// 一定要
    }

    public String toMain() {
        this.service.cleanWebUI(dto, executantType);
        this.dto.getResutlts().clear();
        this.initValue();
        dto = null;
        return TO_MAIN;
    }

    /**
     * 取得要執行資料】
     * 
     * @param type
     */
    public String setQueryResult() {

        try {

            dto.setSelectData(dto.getSelectData());

            this.service.lockData(dto, executantType);// 資料鎖定

            this.service.setQueryResult(dto, executantType);
        } catch (RisBusinessException e) {
            throw e;
        }
        return "rl0x327.xhtml?faces-redirect=true";

    }

    /**
     * 畫面驗證
     */
    public void verifyData() {
        LOG.debug("rl0x327Controller verifyData init ");

        this.initValue();
        /************* 客製化 ********************/

        /* 驗證---個別功能驗證自行補 */

        Rldfm02mType leftData = this.service.getUILeftValue(dto, executantType);

        Rldfm02mType rightData = this.service.getUIRightValue(dto, executantType);

        this.service.verifyData(dto, executantType);

        String businessType = dto.getMainDTO().getBusinessType();
        if (StringUtils.equals("A", businessType)) {
            /*********************************************/
            this.blankToShow(leftData.getPersonId(), "統號");
            try {
                domainCheckerComponent.checkByAdvance(leftData.getPersonId(),
                        new AdvancePattern[] { AdvancePattern.PERSON_ID });
            } catch (InvalidException e) {
                throw new RisBusinessException(tw.gov.moi.rs.common.constant.RsCDMesg.CH6001W, "統號");
            }
            this.blankToShow(leftData.getSpouseId(), "配偶統號");
            try {
                domainCheckerComponent.checkByAdvance(leftData.getSpouseId(),
                        new AdvancePattern[] { AdvancePattern.PERSON_ID });
            } catch (InvalidException e) {
                throw new RisBusinessException(tw.gov.moi.rs.common.constant.RsCDMesg.CH6001W, "配偶統號");
            }

            /*********************************************/
        }
        if (StringUtils.equals("D", businessType)) {
            /*********************************************/

            /*********************************************/

        }
        if (StringUtils.equals("M", businessType)) {

        }

        /****************************************/
        this.blankToShow(dto.getReason(), "職權更正原因");
        dto.setVerify(true);
        LOG.debug("rl0x327Controller verifyData init ");

        this.risFacesTool.addMessage(tw.gov.moi.rs.common.constant.RsCDMesg.TX6000S, "驗證成功");
        RequestContext.getCurrentInstance().update("rl0x327");
    }

    /**
     * 畫面驗證
     */
    public void verifyDataEdit() {
        LOG.debug("rl0x327Controller verifyData init ");
        this.initValue();
        /************* 客製化 ********************/

        /* 驗證---個別功能驗證自行補 */

        Rldfm02mType leftData = this.service.getUILeftValue(dto, executantType);

        Rldfm02mType rightData = this.service.getUIRightValue(dto, executantType);

        this.service.verifyDataEdit(dto, executantType);

        String businessType = dto.getMainDTO().getBusinessType();
        if (StringUtils.equals("A", businessType)) {
            /*********************************************/
            this.blankToShow(leftData.getPersonId(), "統號");
            /*********************************************/
        }
        if (StringUtils.equals("D", businessType)) {
            /*********************************************/

            /*********************************************/

        }
        if (StringUtils.equals("M", businessType)) {

            /*********************************************/
            this.blankToShow(rightData.getPersonId(), "統號");

            /*********************************************/
        }
        this.blankToShow(dto.getReason(), "職權更正原因");
        /****************************************/
        dto.setVerify(true);
        LOG.debug("rl0x327Controller verifyData init ");

        this.risFacesTool.addMessage(tw.gov.moi.rs.common.constant.RsCDMesg.TX6000S, "驗證成功");
        RequestContext.getCurrentInstance().update("rl0x327");
    }

    private void blankToShow(String value, String message) {
        if (StringUtils.isBlank(value)) {
            throw new RisBusinessException(tw.gov.moi.rs.common.constant.RsCDMesg.TX6000W, message);
        }

    }

    /**
     * 核准(共用)_需加入導頁網址
     */
    public void doSumit() {
        this.service.doSumit(dto, executantType);
        this.risFacesTool.addMessage(tw.gov.moi.rs.common.constant.RsCDMesg.TX6000S, "");
        dto = null;

    }

    /**
     * 核准+從驗證rl0x002來
     * 
     * @return
     */
    public String doSumitFromVerificationPage() {
        this.doSumit();
        return TO_VERFITY;
    }

    /**
     * 核准從rlx001來的
     * 
     * @return
     */
    public String doSumitFromMainPage() {
        this.doSumit();
        return TO_MAIN;
    }

    /**
     * 核退
     */
    public void doReject() {
        this.service.doReject(dto, executantType);
        this.risFacesTool.addMessage(tw.gov.moi.rs.common.constant.RsCDMesg.TX6000S, "");
        dto = null;

    }

    /**
     * 核退從驗證rl0x002來
     * 
     * @return
     */
    public String doRejectFromVerificationPage() {
        this.doReject();
        return TO_VERFITY;

    }

    /**
     * 核退從rlx001來的
     * 
     * @return
     */
    public String doRejectFromMainPage() {
        this.doReject();
        return TO_MAIN;

    }

    /**
     * 從戶籍員畫面進入編輯被核退的資料
     * 
     * @return
     */
    public String doEditAndApply() {
        this.baseApply();
        return TO_MAIN;
    }

    public Rl0x327DTO getDto() {
        if (dto == null) {
            dto = new Rl0x327DTO();
            this.init();
        }
        return dto;
    }

    public void setDto(Rl0x327DTO dto) {
        this.dto = dto;
    }

    public Rl0x001DTOBean getRl0x001DTOBean() {
        return rl0x001DTOBean;
    }

    public void setRl0x001DTOBean(Rl0x001DTOBean rl0x001dtoBean) {
        rl0x001DTOBean = rl0x001dtoBean;
    }

    public List<SelectItem> getRadioOnItem() {
        return radioOnItem;
    }

    public void setRadioOnItem(List<SelectItem> radioOnItem) {
        this.radioOnItem = radioOnItem;
    }

    public String getSelectCode() {
        return selectCode;
    }

    public void setSelectCode(String selectCode) {
        this.selectCode = selectCode;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public Rl0x001DTOResult getSelectInfo() {
        return selectInfo;
    }

    public void setSelectInfo(Rl0x001DTOResult selectInfo) {
        this.selectInfo = selectInfo;
    }

}
