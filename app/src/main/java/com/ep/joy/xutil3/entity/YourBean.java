package com.ep.joy.xutil3.entity;

import com.ep.joy.xutil3.http.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;
import java.util.List;

/**
 * author   Joy
 * Date:  2016/2/19.
 * version:  V1.0
 * Description:
 */
@HttpResponse(parser = JsonResponseParser.class)
public class YourBean implements Serializable {

    /**
     * extension : 0
     * signEndTime : 2016年03月01日23:00
     * macthImg : macth/93/d06e59b9-77b9-4eb9-a30c-2417ee6df2c4.jpg
     * macthId : 93
     * releaseName : 重庆御榜科技有限公司
     * money : 0.01
     * macthName : 竞赛榜2016重庆国际马拉松全程、半程组跑团招募
     * extensionMoney : 0.00
     * appType : 1
     */

    private List<ListEntity> list;

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public List<ListEntity> getList() {
        return list;
    }

    public static class ListEntity {
        private int extension;
        private String signEndTime;
        private String macthImg;
        private int macthId;
        private String releaseName;
        private double money;
        private String macthName;
        private String extensionMoney;
        private int appType;

        public void setExtension(int extension) {
            this.extension = extension;
        }

        public void setSignEndTime(String signEndTime) {
            this.signEndTime = signEndTime;
        }

        public void setMacthImg(String macthImg) {
            this.macthImg = macthImg;
        }

        public void setMacthId(int macthId) {
            this.macthId = macthId;
        }

        public void setReleaseName(String releaseName) {
            this.releaseName = releaseName;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public void setMacthName(String macthName) {
            this.macthName = macthName;
        }

        public void setExtensionMoney(String extensionMoney) {
            this.extensionMoney = extensionMoney;
        }

        public void setAppType(int appType) {
            this.appType = appType;
        }

        public int getExtension() {
            return extension;
        }

        public String getSignEndTime() {
            return signEndTime;
        }

        public String getMacthImg() {
            return macthImg;
        }

        public int getMacthId() {
            return macthId;
        }

        public String getReleaseName() {
            return releaseName;
        }

        public double getMoney() {
            return money;
        }

        public String getMacthName() {
            return macthName;
        }

        public String getExtensionMoney() {
            return extensionMoney;
        }

        public int getAppType() {
            return appType;
        }
    }
}
