package com.tianv.updis.model;

import java.io.Serializable;

/**
 * Created by lm3515 on 14-1-22.
 */
public class ActiveTaskModel implements Serializable {

	private static final long serialVersionUID = 2744582267077933471L;
	private String state;
	private String partner;
	private String partnerType;
	private String customerContact;
	private String guimo;
	private String yaoQiuXingChengWenJian;
	private String shiFouTouBiao;
	private String touBiaoLeiBie;
	private String expressRequirement;
	private String yinHanYaoQiu;
	private String diFangFaGui;
	private String fuJiaYaoQiu;
	private String heTongYiZhi;
	private String ziYuanManZu;
	private String sheBeiManZu;
	private String gongQiManZu;
	private String sheJiFeiManZu;
	private String shiFouWaiBao;
	private String shiZhengPeiTao;
	private String duoFangHeTong;
	private String directorReviewer;
	private String directorReviewerApplyTime;
	private String showButton;

	private String stateId;
	private String pingShenFangShi;
	private String yinFaCuoShi;
	private String renWuYaoQiu;
	private String chengJieBuMen;
	private String jingYingShiReviewer;
	private String jingYingShiReviewApplyTime;
	private String projectCategory;
	private String guanLiJiBie;
	private String projectLead;
	private String zhuGuanZongShi;
	private String zongShiShiReviewer;
	private String zongShiShiReviewApplyTime;
	private String showProjectLeadReviewAndRejectButton;
	private String projectLeadReviewer;
	private String projectLeadReviewApplyTime;
	private String showZongShiReviewButton;

	public String getShowZongShiReviewButton() {
		return showZongShiReviewButton;
	}

	public void setShowZongShiReviewButton(String showZongShiReviewButton) {
		this.showZongShiReviewButton = showZongShiReviewButton;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getPartnerType() {
		return partnerType;
	}

	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}

	public String getCustomerContact() {
		return customerContact;
	}

	public void setCustomerContact(String customerContact) {
		this.customerContact = customerContact;
	}

	public String getGuimo() {
		return guimo;
	}

	public void setGuimo(String guimo) {
		this.guimo = guimo;
	}

	public String getYaoQiuXingChengWenJian() {
		return yaoQiuXingChengWenJian;
	}

	public void setYaoQiuXingChengWenJian(String yaoQiuXingChengWenJian) {
		this.yaoQiuXingChengWenJian = yaoQiuXingChengWenJian;
	}

	public String getShiFouTouBiao() {
		return shiFouTouBiao;
	}

	public void setShiFouTouBiao(String shiFouTouBiao) {
		this.shiFouTouBiao = shiFouTouBiao;
	}

	public String getTouBiaoLeiBie() {
		return touBiaoLeiBie;
	}

	public void setTouBiaoLeiBie(String touBiaoLeiBie) {
		this.touBiaoLeiBie = touBiaoLeiBie;
	}

	public String getExpressRequirement() {
		return expressRequirement;
	}

	public void setExpressRequirement(String expressRequirement) {
		this.expressRequirement = expressRequirement;
	}

	public String getYinHanYaoQiu() {
		return yinHanYaoQiu;
	}

	public void setYinHanYaoQiu(String yinHanYaoQiu) {
		this.yinHanYaoQiu = yinHanYaoQiu;
	}

	public String getDiFangFaGui() {
		return diFangFaGui;
	}

	public void setDiFangFaGui(String diFangFaGui) {
		this.diFangFaGui = diFangFaGui;
	}

	public String getFuJiaYaoQiu() {
		return fuJiaYaoQiu;
	}

	public void setFuJiaYaoQiu(String fuJiaYaoQiu) {
		this.fuJiaYaoQiu = fuJiaYaoQiu;
	}

	public String getHeTongYiZhi() {
		return heTongYiZhi;
	}

	public void setHeTongYiZhi(String heTongYiZhi) {
		this.heTongYiZhi = heTongYiZhi;
	}

	public String getZiYuanManZu() {
		return ziYuanManZu;
	}

	public void setZiYuanManZu(String ziYuanManZu) {
		this.ziYuanManZu = ziYuanManZu;
	}

	public String getSheBeiManZu() {
		return sheBeiManZu;
	}

	public void setSheBeiManZu(String sheBeiManZu) {
		this.sheBeiManZu = sheBeiManZu;
	}

	public String getGongQiManZu() {
		return gongQiManZu;
	}

	public void setGongQiManZu(String gongQiManZu) {
		this.gongQiManZu = gongQiManZu;
	}

	public String getSheJiFeiManZu() {
		return sheJiFeiManZu;
	}

	public void setSheJiFeiManZu(String sheJiFeiManZu) {
		this.sheJiFeiManZu = sheJiFeiManZu;
	}

	public String getShiFouWaiBao() {
		return shiFouWaiBao;
	}

	public void setShiFouWaiBao(String shiFouWaiBao) {
		this.shiFouWaiBao = shiFouWaiBao;
	}

	public String getShiZhengPeiTao() {
		return shiZhengPeiTao;
	}

	public void setShiZhengPeiTao(String shiZhengPeiTao) {
		this.shiZhengPeiTao = shiZhengPeiTao;
	}

	public String getDuoFangHeTong() {
		return duoFangHeTong;
	}

	public void setDuoFangHeTong(String duoFangHeTong) {
		this.duoFangHeTong = duoFangHeTong;
	}

	public String getDirectorReviewer() {
		return directorReviewer;
	}

	public void setDirectorReviewer(String directorReviewer) {
		this.directorReviewer = directorReviewer;
	}

	public String getDirectorReviewerApplyTime() {
		return directorReviewerApplyTime;
	}

	public void setDirectorReviewerApplyTime(String directorReviewerApplyTime) {
		this.directorReviewerApplyTime = directorReviewerApplyTime;
	}

	public String getShowButton() {
		return showButton;
	}

	public void setShowButton(String showButton) {
		this.showButton = showButton;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getPingShenFangShi() {
		return pingShenFangShi;
	}

	public void setPingShenFangShi(String pingShenFangShi) {
		this.pingShenFangShi = pingShenFangShi;
	}

	public String getYinFaCuoShi() {
		return yinFaCuoShi;
	}

	public void setYinFaCuoShi(String yinFaCuoShi) {
		this.yinFaCuoShi = yinFaCuoShi;
	}

	public String getRenWuYaoQiu() {
		return renWuYaoQiu;
	}

	public void setRenWuYaoQiu(String renWuYaoQiu) {
		this.renWuYaoQiu = renWuYaoQiu;
	}

	public String getChengJieBuMen() {
		return chengJieBuMen;
	}

	public void setChengJieBuMen(String chengJieBuMen) {
		this.chengJieBuMen = chengJieBuMen;
	}

	public String getJingYingShiReviewer() {
		return jingYingShiReviewer;
	}

	public void setJingYingShiReviewer(String jingYingShiReviewer) {
		this.jingYingShiReviewer = jingYingShiReviewer;
	}

	public String getJingYingShiReviewApplyTime() {
		return jingYingShiReviewApplyTime;
	}

	public void setJingYingShiReviewApplyTime(String jingYingShiReviewApplyTime) {
		this.jingYingShiReviewApplyTime = jingYingShiReviewApplyTime;
	}

	public String getProjectCategory() {
		return projectCategory;
	}

	public void setProjectCategory(String projectCategory) {
		this.projectCategory = projectCategory;
	}

	public String getGuanLiJiBie() {
		return guanLiJiBie;
	}

	public void setGuanLiJiBie(String guanLiJiBie) {
		this.guanLiJiBie = guanLiJiBie;
	}

	public String getProjectLead() {
		return projectLead;
	}

	public void setProjectLead(String projectLead) {
		this.projectLead = projectLead;
	}

	public String getZhuGuanZongShi() {
		return zhuGuanZongShi;
	}

	public void setZhuGuanZongShi(String zhuGuanZongShi) {
		this.zhuGuanZongShi = zhuGuanZongShi;
	}

	public String getZongShiShiReviewer() {
		return zongShiShiReviewer;
	}

	public void setZongShiShiReviewer(String zongShiShiReviewer) {
		this.zongShiShiReviewer = zongShiShiReviewer;
	}

	public String getZongShiShiReviewApplyTime() {
		return zongShiShiReviewApplyTime;
	}

	public void setZongShiShiReviewApplyTime(String zongShiShiReviewApplyTime) {
		this.zongShiShiReviewApplyTime = zongShiShiReviewApplyTime;
	}

	public String getShowProjectLeadReviewAndRejectButton() {
		return showProjectLeadReviewAndRejectButton;
	}

	public void setShowProjectLeadReviewAndRejectButton(String showProjectLeadReviewAndRejectButton) {
		this.showProjectLeadReviewAndRejectButton = showProjectLeadReviewAndRejectButton;
	}

	public String getProjectLeadReviewer() {
		return projectLeadReviewer;
	}

	public void setProjectLeadReviewer(String projectLeadReviewer) {
		this.projectLeadReviewer = projectLeadReviewer;
	}

	public String getProjectLeadReviewApplyTime() {
		return projectLeadReviewApplyTime;
	}

	public void setProjectLeadReviewApplyTime(String projectLeadReviewApplyTime) {
		this.projectLeadReviewApplyTime = projectLeadReviewApplyTime;
	}

}
