package com.xcyh.reachmax.model.bean.search;

import android.os.Build;
import android.os.Parcel;

import com.xcyh.reachmax.model.bean.ItemAdvPlan;

import java.io.Serializable;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

/**
 * Created by haojiangfeng on 2024/12/16.
 */
@Keep
public class SearchItemPlan extends SearchItem {


    /**
     * id : 84374
     * ad_account : 520044337278007
     * ad_id : 120215754189050759
     * ad_name : PT_andr_aeo_1U23374_A irmã mais nova perdida dos seis irmãos da máfia-1210
     * adset_id : 120215754189010759
     * campaign_id : 120215754189020759
     * ad_status : ACTIVE
     * effective_status : ACTIVE
     * effective_time : 2006-01-02T15:04:05+08:00
     * create_time : 2024-12-10T13:55:13+08:00
     * update_time : 2024-12-10T13:57:39+08:00
     * grab_time : 2024-12-10T14:03:16+08:00
     * account : {"ad_account":"520044337278007","launch_app_id":1,"ad_account_name":"HTM-ZT-Alphafiction-0914-杜Ying-直投","ad_media":"facebook","launch_id":30,"channel_id":10101,"platform":1,"launch_count":1625,"status":1,"fb_status":1,"create_time":"2024-09-09T15:00:33+08:00","update_time":"2024-12-10T14:04:21+08:00","channel":{"id":0,"name":"","desc":"","created_by":0,"updated_by":0,"status":0,"create_time":"0001-01-01T00:00:00Z","update_time":null,"create_account":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"},"update_account":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}},"launch_user":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}}
     * campaign : {"id":0,"campaign_id":"","campaign_name":"","ad_account":"","status":"","create_time":"0001-01-01T00:00:00Z","update_time":null,"start_time":null,"stop_time":null,"grab_time":"0001-01-01T00:00:00Z","account":{"ad_account":"","launch_app_id":0,"ad_account_name":"","ad_media":"","launch_id":0,"channel_id":0,"platform":0,"launch_count":0,"status":0,"fb_status":0,"create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","channel":{"id":0,"name":"","desc":"","created_by":0,"updated_by":0,"status":0,"create_time":"0001-01-01T00:00:00Z","update_time":null,"create_account":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"},"update_account":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}},"launch_user":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}}}
     * ad_sets : {"id":31887,"ad_account":"520044337278007","adset_id":"120215754189010759","adset_name":"新应用推广广告组","campaign_id":"120215754189020759","status":"ACTIVE","effective_status":"ACTIVE","create_time":"2024-12-10T13:55:12+08:00","update_time":"2024-12-10T13:55:12+08:00","start_time":"2024-12-11T00:00:00+08:00","stop_time":null,"grab_time":"2024-12-10T14:02:23+08:00","account":{"ad_account":"","launch_app_id":0,"ad_account_name":"","ad_media":"","launch_id":0,"channel_id":0,"platform":0,"launch_count":0,"status":0,"fb_status":0,"create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","channel":{"id":0,"name":"","desc":"","created_by":0,"updated_by":0,"status":0,"create_time":"0001-01-01T00:00:00Z","update_time":null,"create_account":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"},"update_account":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}},"launch_user":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}},"campaign":{"id":0,"campaign_id":"","campaign_name":"","ad_account":"","status":"","create_time":"0001-01-01T00:00:00Z","update_time":null,"start_time":null,"stop_time":null,"grab_time":"0001-01-01T00:00:00Z","account":{"ad_account":"","launch_app_id":0,"ad_account_name":"","ad_media":"","launch_id":0,"channel_id":0,"platform":0,"launch_count":0,"status":0,"fb_status":0,"create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","channel":{"id":0,"name":"","desc":"","created_by":0,"updated_by":0,"status":0,"create_time":"0001-01-01T00:00:00Z","update_time":null,"create_account":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"},"update_account":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}},"launch_user":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}}}}
     */

    private int id;
    private String ad_account;
    private String ad_id;
    private String ad_name;
    private String adset_id;
    private String campaign_id;
    private String ad_status;
    private String effective_status;
    private String effective_time;
    private String create_time;
    private String update_time;
    private String grab_time;

    private AccountBean account;
    private CampaignBean campaign;
    private AdSetsBean ad_sets;


    public SearchItemPlan(){

    }

    public SearchItemPlan(Parcel in) {
        this.id = in.readInt();
        this.ad_account = in.readString();
        this.ad_id = in.readString();
        this.ad_name = in.readString();
        this.adset_id = in.readString();
        this.campaign_id = in.readString();
        this.ad_status = in.readString();
        this.effective_status = in.readString();
        this.effective_time = in.readString();
        this.create_time = in.readString();
        this.update_time = in.readString();
        this.grab_time = in.readString();
        this.type = in.readInt();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.isChecked = in.readBoolean();
        } else {
            this.isChecked = in.readInt() == 1;
        }
    }

    @Override
    public String getSearchName() {
        return ad_name;
    }

    @Override
    public String getSearchId() {
        return ad_id;
    }

    @NonNull
    @Override
    public SearchItem toSelectedItem() {
        SearchItemPlan item = new SearchItemPlan();
        item.id = id;
        item.ad_account = ad_account;
        item.ad_id = ad_id;
        item.ad_name = ad_name;
        item.adset_id = adset_id;
        item.campaign_id = campaign_id;
        item.ad_status = ad_status;
        item.effective_status = effective_status;
        item.effective_time = effective_time;
        item.create_time = create_time;
        item.update_time = update_time;
        item.grab_time = grab_time;
        item.setItemType(SearchItem.TYPE_SELECTED);
        item.setChecked(true);
        return item;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAd_account() {
        return ad_account;
    }

    public void setAd_account(String ad_account) {
        this.ad_account = ad_account;
    }

    public String getAd_id() {
        return ad_id;
    }

    public void setAd_id(String ad_id) {
        this.ad_id = ad_id;
    }

    public String getAd_name() {
        return ad_name;
    }

    public void setAd_name(String ad_name) {
        this.ad_name = ad_name;
    }


    public String getAdset_id() {
        return adset_id;
    }

    public void setAdset_id(String adset_id) {
        this.adset_id = adset_id;
    }

    public String getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(String campaign_id) {
        this.campaign_id = campaign_id;
    }

    public String getAd_status() {
        return ad_status;
    }

    public void setAd_status(String ad_status) {
        this.ad_status = ad_status;
    }

    public String getEffective_status() {
        return effective_status;
    }

    public void setEffective_status(String effective_status) {
        this.effective_status = effective_status;
    }

    public String getEffective_time() {
        return effective_time;
    }

    public void setEffective_time(String effective_time) {
        this.effective_time = effective_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getGrab_time() {
        return grab_time;
    }

    public void setGrab_time(String grab_time) {
        this.grab_time = grab_time;
    }

    public AccountBean getAccount() {
        return account;
    }

    public void setAccount(AccountBean account) {
        this.account = account;
    }

    public CampaignBean getCampaign() {
        return campaign;
    }

    public void setCampaign(CampaignBean campaign) {
        this.campaign = campaign;
    }

    public AdSetsBean getAd_sets() {
        return ad_sets;
    }

    public void setAd_sets(AdSetsBean ad_sets) {
        this.ad_sets = ad_sets;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.ad_account);
        dest.writeString(this.ad_id);
        dest.writeString(this.ad_name);
        dest.writeString(this.adset_id);
        dest.writeString(this.campaign_id);
        dest.writeString(this.ad_status);
        dest.writeString(this.effective_status);
        dest.writeString(this.effective_time);
        dest.writeString(this.create_time);
        dest.writeString(this.update_time);
        dest.writeString(this.grab_time);
        dest.writeInt(this.type);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeBoolean(this.isChecked);
        } else {
            dest.writeInt(this.isChecked ? 1 : 0);
        }
    }

    public static final Creator<SearchItemPlan> CREATOR = new Creator<SearchItemPlan>() {
        @Override
        public SearchItemPlan createFromParcel(Parcel source) {
            return new SearchItemPlan(source);
        }

        @Override
        public SearchItemPlan[] newArray(int size) {
            return new SearchItemPlan[size];
        }
    };




    @Keep
    public static class AccountBean implements Serializable {
        /**
         * ad_account : 520044337278007
         * launch_app_id : 1
         * ad_account_name : HTM-ZT-Alphafiction-0914-杜Ying-直投
         * ad_media : facebook
         * launch_id : 30
         * channel_id : 10101
         * platform : 1
         * launch_count : 1625
         * status : 1
         * fb_status : 1
         * create_time : 2024-09-09T15:00:33+08:00
         * update_time : 2024-12-10T14:04:21+08:00
         * channel : {"id":0,"name":"","desc":"","created_by":0,"updated_by":0,"status":0,"create_time":"0001-01-01T00:00:00Z","update_time":null,"create_account":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"},"update_account":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}}
         * launch_user : {"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}
         */

        private String ad_account;
        private int launch_app_id;
        private String ad_account_name;
        private String ad_media;
        private int launch_id;
        private int channel_id;
        private int platform;
        private int launch_count;
        private int status;
        private int fb_status;
        private String create_time;
        private String update_time;
        private ItemAdvPlan.AccountBean.ChannelBean channel;
        private ItemAdvPlan.AccountBean.LaunchUserBean launch_user;

        public String getAd_account() {
            return ad_account;
        }

        public void setAd_account(String ad_account) {
            this.ad_account = ad_account;
        }

        public int getLaunch_app_id() {
            return launch_app_id;
        }

        public void setLaunch_app_id(int launch_app_id) {
            this.launch_app_id = launch_app_id;
        }

        public String getAd_account_name() {
            return ad_account_name;
        }

        public void setAd_account_name(String ad_account_name) {
            this.ad_account_name = ad_account_name;
        }

        public String getAd_media() {
            return ad_media;
        }

        public void setAd_media(String ad_media) {
            this.ad_media = ad_media;
        }

        public int getLaunch_id() {
            return launch_id;
        }

        public void setLaunch_id(int launch_id) {
            this.launch_id = launch_id;
        }

        public int getChannel_id() {
            return channel_id;
        }

        public void setChannel_id(int channel_id) {
            this.channel_id = channel_id;
        }

        public int getPlatform() {
            return platform;
        }

        public void setPlatform(int platform) {
            this.platform = platform;
        }

        public int getLaunch_count() {
            return launch_count;
        }

        public void setLaunch_count(int launch_count) {
            this.launch_count = launch_count;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getFb_status() {
            return fb_status;
        }

        public void setFb_status(int fb_status) {
            this.fb_status = fb_status;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public ItemAdvPlan.AccountBean.ChannelBean getChannel() {
            return channel;
        }

        public void setChannel(ItemAdvPlan.AccountBean.ChannelBean channel) {
            this.channel = channel;
        }

        public ItemAdvPlan.AccountBean.LaunchUserBean getLaunch_user() {
            return launch_user;
        }

        public void setLaunch_user(ItemAdvPlan.AccountBean.LaunchUserBean launch_user) {
            this.launch_user = launch_user;
        }

        @Keep
        public static class ChannelBean implements Serializable{
            /**
             * id : 0
             * name :
             * desc :
             * created_by : 0
             * updated_by : 0
             * status : 0
             * create_time : 0001-01-01T00:00:00Z
             * update_time : null
             * create_account : {"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}
             * update_account : {"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}
             */

            private int id;
            private String name;
            private String desc;
            private int created_by;
            private int updated_by;
            private int status;
            private String create_time;
            private Object update_time;
            private ItemAdvPlan.AccountBean.ChannelBean.CreateAccountBean create_account;
            private ItemAdvPlan.AccountBean.ChannelBean.UpdateAccountBean update_account;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public int getCreated_by() {
                return created_by;
            }

            public void setCreated_by(int created_by) {
                this.created_by = created_by;
            }

            public int getUpdated_by() {
                return updated_by;
            }

            public void setUpdated_by(int updated_by) {
                this.updated_by = updated_by;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public Object getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(Object update_time) {
                this.update_time = update_time;
            }

            public ItemAdvPlan.AccountBean.ChannelBean.CreateAccountBean getCreate_account() {
                return create_account;
            }

            public void setCreate_account(ItemAdvPlan.AccountBean.ChannelBean.CreateAccountBean create_account) {
                this.create_account = create_account;
            }

            public ItemAdvPlan.AccountBean.ChannelBean.UpdateAccountBean getUpdate_account() {
                return update_account;
            }

            public void setUpdate_account(ItemAdvPlan.AccountBean.ChannelBean.UpdateAccountBean update_account) {
                this.update_account = update_account;
            }

            @Keep
            public static class CreateAccountBean implements Serializable{
                /**
                 * id : 0
                 * name :
                 * description :
                 * roles :
                 * organization :
                 * status : 0
                 * mobile_phone :
                 * dingtalk_robot :
                 * create_time : 0001-01-01T00:00:00Z
                 * update_time : 0001-01-01T00:00:00Z
                 * login_time : 0001-01-01T00:00:00Z
                 */

                private int id;
                private String name;
                private String description;
                private String roles;
                private String organization;
                private int status;
                private String mobile_phone;
                private String dingtalk_robot;
                private String create_time;
                private String update_time;
                private String login_time;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getRoles() {
                    return roles;
                }

                public void setRoles(String roles) {
                    this.roles = roles;
                }

                public String getOrganization() {
                    return organization;
                }

                public void setOrganization(String organization) {
                    this.organization = organization;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getMobile_phone() {
                    return mobile_phone;
                }

                public void setMobile_phone(String mobile_phone) {
                    this.mobile_phone = mobile_phone;
                }

                public String getDingtalk_robot() {
                    return dingtalk_robot;
                }

                public void setDingtalk_robot(String dingtalk_robot) {
                    this.dingtalk_robot = dingtalk_robot;
                }

                public String getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(String create_time) {
                    this.create_time = create_time;
                }

                public String getUpdate_time() {
                    return update_time;
                }

                public void setUpdate_time(String update_time) {
                    this.update_time = update_time;
                }

                public String getLogin_time() {
                    return login_time;
                }

                public void setLogin_time(String login_time) {
                    this.login_time = login_time;
                }
            }

            @Keep
            public static class UpdateAccountBean implements Serializable{
                /**
                 * id : 0
                 * name :
                 * description :
                 * roles :
                 * organization :
                 * status : 0
                 * mobile_phone :
                 * dingtalk_robot :
                 * create_time : 0001-01-01T00:00:00Z
                 * update_time : 0001-01-01T00:00:00Z
                 * login_time : 0001-01-01T00:00:00Z
                 */

                private int id;
                private String name;
                private String description;
                private String roles;
                private String organization;
                private int status;
                private String mobile_phone;
                private String dingtalk_robot;
                private String create_time;
                private String update_time;
                private String login_time;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getRoles() {
                    return roles;
                }

                public void setRoles(String roles) {
                    this.roles = roles;
                }

                public String getOrganization() {
                    return organization;
                }

                public void setOrganization(String organization) {
                    this.organization = organization;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getMobile_phone() {
                    return mobile_phone;
                }

                public void setMobile_phone(String mobile_phone) {
                    this.mobile_phone = mobile_phone;
                }

                public String getDingtalk_robot() {
                    return dingtalk_robot;
                }

                public void setDingtalk_robot(String dingtalk_robot) {
                    this.dingtalk_robot = dingtalk_robot;
                }

                public String getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(String create_time) {
                    this.create_time = create_time;
                }

                public String getUpdate_time() {
                    return update_time;
                }

                public void setUpdate_time(String update_time) {
                    this.update_time = update_time;
                }

                public String getLogin_time() {
                    return login_time;
                }

                public void setLogin_time(String login_time) {
                    this.login_time = login_time;
                }
            }
        }

        @Keep
        public static class LaunchUserBean implements Serializable{
            /**
             * id : 0
             * name :
             * description :
             * roles :
             * organization :
             * status : 0
             * mobile_phone :
             * dingtalk_robot :
             * create_time : 0001-01-01T00:00:00Z
             * update_time : 0001-01-01T00:00:00Z
             * login_time : 0001-01-01T00:00:00Z
             */

            private int id;
            private String name;
            private String description;
            private String roles;
            private String organization;
            private int status;
            private String mobile_phone;
            private String dingtalk_robot;
            private String create_time;
            private String update_time;
            private String login_time;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getRoles() {
                return roles;
            }

            public void setRoles(String roles) {
                this.roles = roles;
            }

            public String getOrganization() {
                return organization;
            }

            public void setOrganization(String organization) {
                this.organization = organization;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getMobile_phone() {
                return mobile_phone;
            }

            public void setMobile_phone(String mobile_phone) {
                this.mobile_phone = mobile_phone;
            }

            public String getDingtalk_robot() {
                return dingtalk_robot;
            }

            public void setDingtalk_robot(String dingtalk_robot) {
                this.dingtalk_robot = dingtalk_robot;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getLogin_time() {
                return login_time;
            }

            public void setLogin_time(String login_time) {
                this.login_time = login_time;
            }
        }
    }

    @Keep
    public static class CampaignBean implements Serializable{
        /**
         * id : 0
         * campaign_id :
         * campaign_name :
         * ad_account :
         * status :
         * create_time : 0001-01-01T00:00:00Z
         * update_time : null
         * start_time : null
         * stop_time : null
         * grab_time : 0001-01-01T00:00:00Z
         * account : {"ad_account":"","launch_app_id":0,"ad_account_name":"","ad_media":"","launch_id":0,"channel_id":0,"platform":0,"launch_count":0,"status":0,"fb_status":0,"create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","channel":{"id":0,"name":"","desc":"","created_by":0,"updated_by":0,"status":0,"create_time":"0001-01-01T00:00:00Z","update_time":null,"create_account":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"},"update_account":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}},"launch_user":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}}
         */

        private int id;
        private String campaign_id;
        private String campaign_name;
        private String ad_account;
        private String status;
        private String create_time;
        private Object update_time;
        private Object start_time;
        private Object stop_time;
        private String grab_time;
        private ItemAdvPlan.CampaignBean.AccountBeanX account;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCampaign_id() {
            return campaign_id;
        }

        public void setCampaign_id(String campaign_id) {
            this.campaign_id = campaign_id;
        }

        public String getCampaign_name() {
            return campaign_name;
        }

        public void setCampaign_name(String campaign_name) {
            this.campaign_name = campaign_name;
        }

        public String getAd_account() {
            return ad_account;
        }

        public void setAd_account(String ad_account) {
            this.ad_account = ad_account;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public Object getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(Object update_time) {
            this.update_time = update_time;
        }

        public Object getStart_time() {
            return start_time;
        }

        public void setStart_time(Object start_time) {
            this.start_time = start_time;
        }

        public Object getStop_time() {
            return stop_time;
        }

        public void setStop_time(Object stop_time) {
            this.stop_time = stop_time;
        }

        public String getGrab_time() {
            return grab_time;
        }

        public void setGrab_time(String grab_time) {
            this.grab_time = grab_time;
        }

        public ItemAdvPlan.CampaignBean.AccountBeanX getAccount() {
            return account;
        }

        public void setAccount(ItemAdvPlan.CampaignBean.AccountBeanX account) {
            this.account = account;
        }

        @Keep
        public static class AccountBeanX implements Serializable{
            /**
             * ad_account :
             * launch_app_id : 0
             * ad_account_name :
             * ad_media :
             * launch_id : 0
             * channel_id : 0
             * platform : 0
             * launch_count : 0
             * status : 0
             * fb_status : 0
             * create_time : 0001-01-01T00:00:00Z
             * update_time : 0001-01-01T00:00:00Z
             * channel : {"id":0,"name":"","desc":"","created_by":0,"updated_by":0,"status":0,"create_time":"0001-01-01T00:00:00Z","update_time":null,"create_account":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"},"update_account":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}}
             * launch_user : {"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}
             */

            private String ad_account;
            private int launch_app_id;
            private String ad_account_name;
            private String ad_media;
            private int launch_id;
            private int channel_id;
            private int platform;
            private int launch_count;
            private int status;
            private int fb_status;
            private String create_time;
            private String update_time;
            private ItemAdvPlan.CampaignBean.AccountBeanX.ChannelBeanX channel;
            private ItemAdvPlan.CampaignBean.AccountBeanX.LaunchUserBeanX launch_user;

            public String getAd_account() {
                return ad_account;
            }

            public void setAd_account(String ad_account) {
                this.ad_account = ad_account;
            }

            public int getLaunch_app_id() {
                return launch_app_id;
            }

            public void setLaunch_app_id(int launch_app_id) {
                this.launch_app_id = launch_app_id;
            }

            public String getAd_account_name() {
                return ad_account_name;
            }

            public void setAd_account_name(String ad_account_name) {
                this.ad_account_name = ad_account_name;
            }

            public String getAd_media() {
                return ad_media;
            }

            public void setAd_media(String ad_media) {
                this.ad_media = ad_media;
            }

            public int getLaunch_id() {
                return launch_id;
            }

            public void setLaunch_id(int launch_id) {
                this.launch_id = launch_id;
            }

            public int getChannel_id() {
                return channel_id;
            }

            public void setChannel_id(int channel_id) {
                this.channel_id = channel_id;
            }

            public int getPlatform() {
                return platform;
            }

            public void setPlatform(int platform) {
                this.platform = platform;
            }

            public int getLaunch_count() {
                return launch_count;
            }

            public void setLaunch_count(int launch_count) {
                this.launch_count = launch_count;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getFb_status() {
                return fb_status;
            }

            public void setFb_status(int fb_status) {
                this.fb_status = fb_status;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public ItemAdvPlan.CampaignBean.AccountBeanX.ChannelBeanX getChannel() {
                return channel;
            }

            public void setChannel(ItemAdvPlan.CampaignBean.AccountBeanX.ChannelBeanX channel) {
                this.channel = channel;
            }

            public ItemAdvPlan.CampaignBean.AccountBeanX.LaunchUserBeanX getLaunch_user() {
                return launch_user;
            }

            public void setLaunch_user(ItemAdvPlan.CampaignBean.AccountBeanX.LaunchUserBeanX launch_user) {
                this.launch_user = launch_user;
            }

            @Keep
            public static class ChannelBeanX implements Serializable{
                /**
                 * id : 0
                 * name :
                 * desc :
                 * created_by : 0
                 * updated_by : 0
                 * status : 0
                 * create_time : 0001-01-01T00:00:00Z
                 * update_time : null
                 * create_account : {"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}
                 * update_account : {"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}
                 */

                private int id;
                private String name;
                private String desc;
                private int created_by;
                private int updated_by;
                private int status;
                private String create_time;
                private Object update_time;
                private ItemAdvPlan.CampaignBean.AccountBeanX.ChannelBeanX.CreateAccountBeanX create_account;
                private ItemAdvPlan.CampaignBean.AccountBeanX.ChannelBeanX.UpdateAccountBeanX update_account;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public int getCreated_by() {
                    return created_by;
                }

                public void setCreated_by(int created_by) {
                    this.created_by = created_by;
                }

                public int getUpdated_by() {
                    return updated_by;
                }

                public void setUpdated_by(int updated_by) {
                    this.updated_by = updated_by;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(String create_time) {
                    this.create_time = create_time;
                }

                public Object getUpdate_time() {
                    return update_time;
                }

                public void setUpdate_time(Object update_time) {
                    this.update_time = update_time;
                }

                public ItemAdvPlan.CampaignBean.AccountBeanX.ChannelBeanX.CreateAccountBeanX getCreate_account() {
                    return create_account;
                }

                public void setCreate_account(ItemAdvPlan.CampaignBean.AccountBeanX.ChannelBeanX.CreateAccountBeanX create_account) {
                    this.create_account = create_account;
                }

                public ItemAdvPlan.CampaignBean.AccountBeanX.ChannelBeanX.UpdateAccountBeanX getUpdate_account() {
                    return update_account;
                }

                public void setUpdate_account(ItemAdvPlan.CampaignBean.AccountBeanX.ChannelBeanX.UpdateAccountBeanX update_account) {
                    this.update_account = update_account;
                }

                @Keep
                public static class CreateAccountBeanX implements Serializable{
                    /**
                     * id : 0
                     * name :
                     * description :
                     * roles :
                     * organization :
                     * status : 0
                     * mobile_phone :
                     * dingtalk_robot :
                     * create_time : 0001-01-01T00:00:00Z
                     * update_time : 0001-01-01T00:00:00Z
                     * login_time : 0001-01-01T00:00:00Z
                     */

                    private int id;
                    private String name;
                    private String description;
                    private String roles;
                    private String organization;
                    private int status;
                    private String mobile_phone;
                    private String dingtalk_robot;
                    private String create_time;
                    private String update_time;
                    private String login_time;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getDescription() {
                        return description;
                    }

                    public void setDescription(String description) {
                        this.description = description;
                    }

                    public String getRoles() {
                        return roles;
                    }

                    public void setRoles(String roles) {
                        this.roles = roles;
                    }

                    public String getOrganization() {
                        return organization;
                    }

                    public void setOrganization(String organization) {
                        this.organization = organization;
                    }

                    public int getStatus() {
                        return status;
                    }

                    public void setStatus(int status) {
                        this.status = status;
                    }

                    public String getMobile_phone() {
                        return mobile_phone;
                    }

                    public void setMobile_phone(String mobile_phone) {
                        this.mobile_phone = mobile_phone;
                    }

                    public String getDingtalk_robot() {
                        return dingtalk_robot;
                    }

                    public void setDingtalk_robot(String dingtalk_robot) {
                        this.dingtalk_robot = dingtalk_robot;
                    }

                    public String getCreate_time() {
                        return create_time;
                    }

                    public void setCreate_time(String create_time) {
                        this.create_time = create_time;
                    }

                    public String getUpdate_time() {
                        return update_time;
                    }

                    public void setUpdate_time(String update_time) {
                        this.update_time = update_time;
                    }

                    public String getLogin_time() {
                        return login_time;
                    }

                    public void setLogin_time(String login_time) {
                        this.login_time = login_time;
                    }
                }

                @Keep
                public static class UpdateAccountBeanX implements Serializable{
                    /**
                     * id : 0
                     * name :
                     * description :
                     * roles :
                     * organization :
                     * status : 0
                     * mobile_phone :
                     * dingtalk_robot :
                     * create_time : 0001-01-01T00:00:00Z
                     * update_time : 0001-01-01T00:00:00Z
                     * login_time : 0001-01-01T00:00:00Z
                     */

                    private int id;
                    private String name;
                    private String description;
                    private String roles;
                    private String organization;
                    private int status;
                    private String mobile_phone;
                    private String dingtalk_robot;
                    private String create_time;
                    private String update_time;
                    private String login_time;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getDescription() {
                        return description;
                    }

                    public void setDescription(String description) {
                        this.description = description;
                    }

                    public String getRoles() {
                        return roles;
                    }

                    public void setRoles(String roles) {
                        this.roles = roles;
                    }

                    public String getOrganization() {
                        return organization;
                    }

                    public void setOrganization(String organization) {
                        this.organization = organization;
                    }

                    public int getStatus() {
                        return status;
                    }

                    public void setStatus(int status) {
                        this.status = status;
                    }

                    public String getMobile_phone() {
                        return mobile_phone;
                    }

                    public void setMobile_phone(String mobile_phone) {
                        this.mobile_phone = mobile_phone;
                    }

                    public String getDingtalk_robot() {
                        return dingtalk_robot;
                    }

                    public void setDingtalk_robot(String dingtalk_robot) {
                        this.dingtalk_robot = dingtalk_robot;
                    }

                    public String getCreate_time() {
                        return create_time;
                    }

                    public void setCreate_time(String create_time) {
                        this.create_time = create_time;
                    }

                    public String getUpdate_time() {
                        return update_time;
                    }

                    public void setUpdate_time(String update_time) {
                        this.update_time = update_time;
                    }

                    public String getLogin_time() {
                        return login_time;
                    }

                    public void setLogin_time(String login_time) {
                        this.login_time = login_time;
                    }
                }
            }

            @Keep
            public static class LaunchUserBeanX implements Serializable{
                /**
                 * id : 0
                 * name :
                 * description :
                 * roles :
                 * organization :
                 * status : 0
                 * mobile_phone :
                 * dingtalk_robot :
                 * create_time : 0001-01-01T00:00:00Z
                 * update_time : 0001-01-01T00:00:00Z
                 * login_time : 0001-01-01T00:00:00Z
                 */

                private int id;
                private String name;
                private String description;
                private String roles;
                private String organization;
                private int status;
                private String mobile_phone;
                private String dingtalk_robot;
                private String create_time;
                private String update_time;
                private String login_time;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getRoles() {
                    return roles;
                }

                public void setRoles(String roles) {
                    this.roles = roles;
                }

                public String getOrganization() {
                    return organization;
                }

                public void setOrganization(String organization) {
                    this.organization = organization;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getMobile_phone() {
                    return mobile_phone;
                }

                public void setMobile_phone(String mobile_phone) {
                    this.mobile_phone = mobile_phone;
                }

                public String getDingtalk_robot() {
                    return dingtalk_robot;
                }

                public void setDingtalk_robot(String dingtalk_robot) {
                    this.dingtalk_robot = dingtalk_robot;
                }

                public String getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(String create_time) {
                    this.create_time = create_time;
                }

                public String getUpdate_time() {
                    return update_time;
                }

                public void setUpdate_time(String update_time) {
                    this.update_time = update_time;
                }

                public String getLogin_time() {
                    return login_time;
                }

                public void setLogin_time(String login_time) {
                    this.login_time = login_time;
                }
            }
        }
    }

    @Keep
    public static class AdSetsBean implements Serializable{
        /**
         * id : 31887
         * ad_account : 520044337278007
         * adset_id : 120215754189010759
         * adset_name : 新应用推广广告组
         * campaign_id : 120215754189020759
         * status : ACTIVE
         * effective_status : ACTIVE
         * create_time : 2024-12-10T13:55:12+08:00
         * update_time : 2024-12-10T13:55:12+08:00
         * start_time : 2024-12-11T00:00:00+08:00
         * stop_time : null
         * grab_time : 2024-12-10T14:02:23+08:00
         * account : {"ad_account":"","launch_app_id":0,"ad_account_name":"","ad_media":"","launch_id":0,"channel_id":0,"platform":0,"launch_count":0,"status":0,"fb_status":0,"create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","channel":{"id":0,"name":"","desc":"","created_by":0,"updated_by":0,"status":0,"create_time":"0001-01-01T00:00:00Z","update_time":null,"create_account":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"},"update_account":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}},"launch_user":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}}
         * campaign : {"id":0,"campaign_id":"","campaign_name":"","ad_account":"","status":"","create_time":"0001-01-01T00:00:00Z","update_time":null,"start_time":null,"stop_time":null,"grab_time":"0001-01-01T00:00:00Z","account":{"ad_account":"","launch_app_id":0,"ad_account_name":"","ad_media":"","launch_id":0,"channel_id":0,"platform":0,"launch_count":0,"status":0,"fb_status":0,"create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","channel":{"id":0,"name":"","desc":"","created_by":0,"updated_by":0,"status":0,"create_time":"0001-01-01T00:00:00Z","update_time":null,"create_account":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"},"update_account":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}},"launch_user":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}}}
         */

        private int id;
        private String ad_account;
        private String adset_id;
        private String adset_name;
        private String campaign_id;
        private String status;
        private String effective_status;
        private String create_time;
        private String update_time;
        private String start_time;
        private Object stop_time;
        private String grab_time;
        private ItemAdvPlan.AdSetsBean.AccountBeanXX account;
        private ItemAdvPlan.AdSetsBean.CampaignBeanX campaign;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAd_account() {
            return ad_account;
        }

        public void setAd_account(String ad_account) {
            this.ad_account = ad_account;
        }

        public String getAdset_id() {
            return adset_id;
        }

        public void setAdset_id(String adset_id) {
            this.adset_id = adset_id;
        }

        public String getAdset_name() {
            return adset_name;
        }

        public void setAdset_name(String adset_name) {
            this.adset_name = adset_name;
        }

        public String getCampaign_id() {
            return campaign_id;
        }

        public void setCampaign_id(String campaign_id) {
            this.campaign_id = campaign_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getEffective_status() {
            return effective_status;
        }

        public void setEffective_status(String effective_status) {
            this.effective_status = effective_status;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public Object getStop_time() {
            return stop_time;
        }

        public void setStop_time(Object stop_time) {
            this.stop_time = stop_time;
        }

        public String getGrab_time() {
            return grab_time;
        }

        public void setGrab_time(String grab_time) {
            this.grab_time = grab_time;
        }

        public ItemAdvPlan.AdSetsBean.AccountBeanXX getAccount() {
            return account;
        }

        public void setAccount(ItemAdvPlan.AdSetsBean.AccountBeanXX account) {
            this.account = account;
        }

        public ItemAdvPlan.AdSetsBean.CampaignBeanX getCampaign() {
            return campaign;
        }

        public void setCampaign(ItemAdvPlan.AdSetsBean.CampaignBeanX campaign) {
            this.campaign = campaign;
        }

        @Keep
        public static class AccountBeanXX implements Serializable{
            /**
             * ad_account :
             * launch_app_id : 0
             * ad_account_name :
             * ad_media :
             * launch_id : 0
             * channel_id : 0
             * platform : 0
             * launch_count : 0
             * status : 0
             * fb_status : 0
             * create_time : 0001-01-01T00:00:00Z
             * update_time : 0001-01-01T00:00:00Z
             * channel : {"id":0,"name":"","desc":"","created_by":0,"updated_by":0,"status":0,"create_time":"0001-01-01T00:00:00Z","update_time":null,"create_account":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"},"update_account":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}}
             * launch_user : {"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}
             */

            private String ad_account;
            private int launch_app_id;
            private String ad_account_name;
            private String ad_media;
            private int launch_id;
            private int channel_id;
            private int platform;
            private int launch_count;
            private int status;
            private int fb_status;
            private String create_time;
            private String update_time;
            private ItemAdvPlan.AdSetsBean.AccountBeanXX.ChannelBeanXX channel;
            private ItemAdvPlan.AdSetsBean.AccountBeanXX.LaunchUserBeanXX launch_user;

            public String getAd_account() {
                return ad_account;
            }

            public void setAd_account(String ad_account) {
                this.ad_account = ad_account;
            }

            public int getLaunch_app_id() {
                return launch_app_id;
            }

            public void setLaunch_app_id(int launch_app_id) {
                this.launch_app_id = launch_app_id;
            }

            public String getAd_account_name() {
                return ad_account_name;
            }

            public void setAd_account_name(String ad_account_name) {
                this.ad_account_name = ad_account_name;
            }

            public String getAd_media() {
                return ad_media;
            }

            public void setAd_media(String ad_media) {
                this.ad_media = ad_media;
            }

            public int getLaunch_id() {
                return launch_id;
            }

            public void setLaunch_id(int launch_id) {
                this.launch_id = launch_id;
            }

            public int getChannel_id() {
                return channel_id;
            }

            public void setChannel_id(int channel_id) {
                this.channel_id = channel_id;
            }

            public int getPlatform() {
                return platform;
            }

            public void setPlatform(int platform) {
                this.platform = platform;
            }

            public int getLaunch_count() {
                return launch_count;
            }

            public void setLaunch_count(int launch_count) {
                this.launch_count = launch_count;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getFb_status() {
                return fb_status;
            }

            public void setFb_status(int fb_status) {
                this.fb_status = fb_status;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public ItemAdvPlan.AdSetsBean.AccountBeanXX.ChannelBeanXX getChannel() {
                return channel;
            }

            public void setChannel(ItemAdvPlan.AdSetsBean.AccountBeanXX.ChannelBeanXX channel) {
                this.channel = channel;
            }

            public ItemAdvPlan.AdSetsBean.AccountBeanXX.LaunchUserBeanXX getLaunch_user() {
                return launch_user;
            }

            public void setLaunch_user(ItemAdvPlan.AdSetsBean.AccountBeanXX.LaunchUserBeanXX launch_user) {
                this.launch_user = launch_user;
            }

            @Keep
            public static class ChannelBeanXX implements Serializable{
                /**
                 * id : 0
                 * name :
                 * desc :
                 * created_by : 0
                 * updated_by : 0
                 * status : 0
                 * create_time : 0001-01-01T00:00:00Z
                 * update_time : null
                 * create_account : {"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}
                 * update_account : {"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}
                 */

                private int id;
                private String name;
                private String desc;
                private int created_by;
                private int updated_by;
                private int status;
                private String create_time;
                private Object update_time;
                private ItemAdvPlan.AdSetsBean.AccountBeanXX.ChannelBeanXX.CreateAccountBeanXX create_account;
                private ItemAdvPlan.AdSetsBean.AccountBeanXX.ChannelBeanXX.UpdateAccountBeanXX update_account;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public int getCreated_by() {
                    return created_by;
                }

                public void setCreated_by(int created_by) {
                    this.created_by = created_by;
                }

                public int getUpdated_by() {
                    return updated_by;
                }

                public void setUpdated_by(int updated_by) {
                    this.updated_by = updated_by;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(String create_time) {
                    this.create_time = create_time;
                }

                public Object getUpdate_time() {
                    return update_time;
                }

                public void setUpdate_time(Object update_time) {
                    this.update_time = update_time;
                }

                public ItemAdvPlan.AdSetsBean.AccountBeanXX.ChannelBeanXX.CreateAccountBeanXX getCreate_account() {
                    return create_account;
                }

                public void setCreate_account(ItemAdvPlan.AdSetsBean.AccountBeanXX.ChannelBeanXX.CreateAccountBeanXX create_account) {
                    this.create_account = create_account;
                }

                public ItemAdvPlan.AdSetsBean.AccountBeanXX.ChannelBeanXX.UpdateAccountBeanXX getUpdate_account() {
                    return update_account;
                }

                public void setUpdate_account(ItemAdvPlan.AdSetsBean.AccountBeanXX.ChannelBeanXX.UpdateAccountBeanXX update_account) {
                    this.update_account = update_account;
                }

                @Keep
                public static class CreateAccountBeanXX implements Serializable{
                    /**
                     * id : 0
                     * name :
                     * description :
                     * roles :
                     * organization :
                     * status : 0
                     * mobile_phone :
                     * dingtalk_robot :
                     * create_time : 0001-01-01T00:00:00Z
                     * update_time : 0001-01-01T00:00:00Z
                     * login_time : 0001-01-01T00:00:00Z
                     */

                    private int id;
                    private String name;
                    private String description;
                    private String roles;
                    private String organization;
                    private int status;
                    private String mobile_phone;
                    private String dingtalk_robot;
                    private String create_time;
                    private String update_time;
                    private String login_time;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getDescription() {
                        return description;
                    }

                    public void setDescription(String description) {
                        this.description = description;
                    }

                    public String getRoles() {
                        return roles;
                    }

                    public void setRoles(String roles) {
                        this.roles = roles;
                    }

                    public String getOrganization() {
                        return organization;
                    }

                    public void setOrganization(String organization) {
                        this.organization = organization;
                    }

                    public int getStatus() {
                        return status;
                    }

                    public void setStatus(int status) {
                        this.status = status;
                    }

                    public String getMobile_phone() {
                        return mobile_phone;
                    }

                    public void setMobile_phone(String mobile_phone) {
                        this.mobile_phone = mobile_phone;
                    }

                    public String getDingtalk_robot() {
                        return dingtalk_robot;
                    }

                    public void setDingtalk_robot(String dingtalk_robot) {
                        this.dingtalk_robot = dingtalk_robot;
                    }

                    public String getCreate_time() {
                        return create_time;
                    }

                    public void setCreate_time(String create_time) {
                        this.create_time = create_time;
                    }

                    public String getUpdate_time() {
                        return update_time;
                    }

                    public void setUpdate_time(String update_time) {
                        this.update_time = update_time;
                    }

                    public String getLogin_time() {
                        return login_time;
                    }

                    public void setLogin_time(String login_time) {
                        this.login_time = login_time;
                    }
                }

                @Keep
                public static class UpdateAccountBeanXX implements Serializable{
                    /**
                     * id : 0
                     * name :
                     * description :
                     * roles :
                     * organization :
                     * status : 0
                     * mobile_phone :
                     * dingtalk_robot :
                     * create_time : 0001-01-01T00:00:00Z
                     * update_time : 0001-01-01T00:00:00Z
                     * login_time : 0001-01-01T00:00:00Z
                     */

                    private int id;
                    private String name;
                    private String description;
                    private String roles;
                    private String organization;
                    private int status;
                    private String mobile_phone;
                    private String dingtalk_robot;
                    private String create_time;
                    private String update_time;
                    private String login_time;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getDescription() {
                        return description;
                    }

                    public void setDescription(String description) {
                        this.description = description;
                    }

                    public String getRoles() {
                        return roles;
                    }

                    public void setRoles(String roles) {
                        this.roles = roles;
                    }

                    public String getOrganization() {
                        return organization;
                    }

                    public void setOrganization(String organization) {
                        this.organization = organization;
                    }

                    public int getStatus() {
                        return status;
                    }

                    public void setStatus(int status) {
                        this.status = status;
                    }

                    public String getMobile_phone() {
                        return mobile_phone;
                    }

                    public void setMobile_phone(String mobile_phone) {
                        this.mobile_phone = mobile_phone;
                    }

                    public String getDingtalk_robot() {
                        return dingtalk_robot;
                    }

                    public void setDingtalk_robot(String dingtalk_robot) {
                        this.dingtalk_robot = dingtalk_robot;
                    }

                    public String getCreate_time() {
                        return create_time;
                    }

                    public void setCreate_time(String create_time) {
                        this.create_time = create_time;
                    }

                    public String getUpdate_time() {
                        return update_time;
                    }

                    public void setUpdate_time(String update_time) {
                        this.update_time = update_time;
                    }

                    public String getLogin_time() {
                        return login_time;
                    }

                    public void setLogin_time(String login_time) {
                        this.login_time = login_time;
                    }
                }
            }

            @Keep
            public static class LaunchUserBeanXX implements Serializable{
                /**
                 * id : 0
                 * name :
                 * description :
                 * roles :
                 * organization :
                 * status : 0
                 * mobile_phone :
                 * dingtalk_robot :
                 * create_time : 0001-01-01T00:00:00Z
                 * update_time : 0001-01-01T00:00:00Z
                 * login_time : 0001-01-01T00:00:00Z
                 */

                private int id;
                private String name;
                private String description;
                private String roles;
                private String organization;
                private int status;
                private String mobile_phone;
                private String dingtalk_robot;
                private String create_time;
                private String update_time;
                private String login_time;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getRoles() {
                    return roles;
                }

                public void setRoles(String roles) {
                    this.roles = roles;
                }

                public String getOrganization() {
                    return organization;
                }

                public void setOrganization(String organization) {
                    this.organization = organization;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getMobile_phone() {
                    return mobile_phone;
                }

                public void setMobile_phone(String mobile_phone) {
                    this.mobile_phone = mobile_phone;
                }

                public String getDingtalk_robot() {
                    return dingtalk_robot;
                }

                public void setDingtalk_robot(String dingtalk_robot) {
                    this.dingtalk_robot = dingtalk_robot;
                }

                public String getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(String create_time) {
                    this.create_time = create_time;
                }

                public String getUpdate_time() {
                    return update_time;
                }

                public void setUpdate_time(String update_time) {
                    this.update_time = update_time;
                }

                public String getLogin_time() {
                    return login_time;
                }

                public void setLogin_time(String login_time) {
                    this.login_time = login_time;
                }
            }
        }

        @Keep
        public static class CampaignBeanX implements Serializable{
            /**
             * id : 0
             * campaign_id :
             * campaign_name :
             * ad_account :
             * status :
             * create_time : 0001-01-01T00:00:00Z
             * update_time : null
             * start_time : null
             * stop_time : null
             * grab_time : 0001-01-01T00:00:00Z
             * account : {"ad_account":"","launch_app_id":0,"ad_account_name":"","ad_media":"","launch_id":0,"channel_id":0,"platform":0,"launch_count":0,"status":0,"fb_status":0,"create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","channel":{"id":0,"name":"","desc":"","created_by":0,"updated_by":0,"status":0,"create_time":"0001-01-01T00:00:00Z","update_time":null,"create_account":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"},"update_account":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}},"launch_user":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}}
             */

            private int id;
            private String campaign_id;
            private String campaign_name;
            private String ad_account;
            private String status;
            private String create_time;
            private Object update_time;
            private Object start_time;
            private Object stop_time;
            private String grab_time;
            private ItemAdvPlan.AdSetsBean.CampaignBeanX.AccountBeanXXX account;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCampaign_id() {
                return campaign_id;
            }

            public void setCampaign_id(String campaign_id) {
                this.campaign_id = campaign_id;
            }

            public String getCampaign_name() {
                return campaign_name;
            }

            public void setCampaign_name(String campaign_name) {
                this.campaign_name = campaign_name;
            }

            public String getAd_account() {
                return ad_account;
            }

            public void setAd_account(String ad_account) {
                this.ad_account = ad_account;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public Object getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(Object update_time) {
                this.update_time = update_time;
            }

            public Object getStart_time() {
                return start_time;
            }

            public void setStart_time(Object start_time) {
                this.start_time = start_time;
            }

            public Object getStop_time() {
                return stop_time;
            }

            public void setStop_time(Object stop_time) {
                this.stop_time = stop_time;
            }

            public String getGrab_time() {
                return grab_time;
            }

            public void setGrab_time(String grab_time) {
                this.grab_time = grab_time;
            }

            public ItemAdvPlan.AdSetsBean.CampaignBeanX.AccountBeanXXX getAccount() {
                return account;
            }

            public void setAccount(ItemAdvPlan.AdSetsBean.CampaignBeanX.AccountBeanXXX account) {
                this.account = account;
            }

            @Keep
            public static class AccountBeanXXX implements Serializable{
                /**
                 * ad_account :
                 * launch_app_id : 0
                 * ad_account_name :
                 * ad_media :
                 * launch_id : 0
                 * channel_id : 0
                 * platform : 0
                 * launch_count : 0
                 * status : 0
                 * fb_status : 0
                 * create_time : 0001-01-01T00:00:00Z
                 * update_time : 0001-01-01T00:00:00Z
                 * channel : {"id":0,"name":"","desc":"","created_by":0,"updated_by":0,"status":0,"create_time":"0001-01-01T00:00:00Z","update_time":null,"create_account":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"},"update_account":{"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}}
                 * launch_user : {"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}
                 */

                private String ad_account;
                private int launch_app_id;
                private String ad_account_name;
                private String ad_media;
                private int launch_id;
                private int channel_id;
                private int platform;
                private int launch_count;
                private int status;
                private int fb_status;
                private String create_time;
                private String update_time;
                private ItemAdvPlan.AdSetsBean.CampaignBeanX.AccountBeanXXX.ChannelBeanXXX channel;
                private ItemAdvPlan.AdSetsBean.CampaignBeanX.AccountBeanXXX.LaunchUserBeanXXX launch_user;

                public String getAd_account() {
                    return ad_account;
                }

                public void setAd_account(String ad_account) {
                    this.ad_account = ad_account;
                }

                public int getLaunch_app_id() {
                    return launch_app_id;
                }

                public void setLaunch_app_id(int launch_app_id) {
                    this.launch_app_id = launch_app_id;
                }

                public String getAd_account_name() {
                    return ad_account_name;
                }

                public void setAd_account_name(String ad_account_name) {
                    this.ad_account_name = ad_account_name;
                }

                public String getAd_media() {
                    return ad_media;
                }

                public void setAd_media(String ad_media) {
                    this.ad_media = ad_media;
                }

                public int getLaunch_id() {
                    return launch_id;
                }

                public void setLaunch_id(int launch_id) {
                    this.launch_id = launch_id;
                }

                public int getChannel_id() {
                    return channel_id;
                }

                public void setChannel_id(int channel_id) {
                    this.channel_id = channel_id;
                }

                public int getPlatform() {
                    return platform;
                }

                public void setPlatform(int platform) {
                    this.platform = platform;
                }

                public int getLaunch_count() {
                    return launch_count;
                }

                public void setLaunch_count(int launch_count) {
                    this.launch_count = launch_count;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public int getFb_status() {
                    return fb_status;
                }

                public void setFb_status(int fb_status) {
                    this.fb_status = fb_status;
                }

                public String getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(String create_time) {
                    this.create_time = create_time;
                }

                public String getUpdate_time() {
                    return update_time;
                }

                public void setUpdate_time(String update_time) {
                    this.update_time = update_time;
                }

                public ItemAdvPlan.AdSetsBean.CampaignBeanX.AccountBeanXXX.ChannelBeanXXX getChannel() {
                    return channel;
                }

                public void setChannel(ItemAdvPlan.AdSetsBean.CampaignBeanX.AccountBeanXXX.ChannelBeanXXX channel) {
                    this.channel = channel;
                }

                public ItemAdvPlan.AdSetsBean.CampaignBeanX.AccountBeanXXX.LaunchUserBeanXXX getLaunch_user() {
                    return launch_user;
                }

                public void setLaunch_user(ItemAdvPlan.AdSetsBean.CampaignBeanX.AccountBeanXXX.LaunchUserBeanXXX launch_user) {
                    this.launch_user = launch_user;
                }

                @Keep
                public static class ChannelBeanXXX implements Serializable{
                    /**
                     * id : 0
                     * name :
                     * desc :
                     * created_by : 0
                     * updated_by : 0
                     * status : 0
                     * create_time : 0001-01-01T00:00:00Z
                     * update_time : null
                     * create_account : {"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}
                     * update_account : {"id":0,"name":"","description":"","roles":"","organization":"","status":0,"mobile_phone":"","dingtalk_robot":"","create_time":"0001-01-01T00:00:00Z","update_time":"0001-01-01T00:00:00Z","login_time":"0001-01-01T00:00:00Z"}
                     */

                    private int id;
                    private String name;
                    private String desc;
                    private int created_by;
                    private int updated_by;
                    private int status;
                    private String create_time;
                    private Object update_time;
                    private ItemAdvPlan.AdSetsBean.CampaignBeanX.AccountBeanXXX.ChannelBeanXXX.CreateAccountBeanXXX create_account;
                    private ItemAdvPlan.AdSetsBean.CampaignBeanX.AccountBeanXXX.ChannelBeanXXX.UpdateAccountBeanXXX update_account;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getDesc() {
                        return desc;
                    }

                    public void setDesc(String desc) {
                        this.desc = desc;
                    }

                    public int getCreated_by() {
                        return created_by;
                    }

                    public void setCreated_by(int created_by) {
                        this.created_by = created_by;
                    }

                    public int getUpdated_by() {
                        return updated_by;
                    }

                    public void setUpdated_by(int updated_by) {
                        this.updated_by = updated_by;
                    }

                    public int getStatus() {
                        return status;
                    }

                    public void setStatus(int status) {
                        this.status = status;
                    }

                    public String getCreate_time() {
                        return create_time;
                    }

                    public void setCreate_time(String create_time) {
                        this.create_time = create_time;
                    }

                    public Object getUpdate_time() {
                        return update_time;
                    }

                    public void setUpdate_time(Object update_time) {
                        this.update_time = update_time;
                    }

                    public ItemAdvPlan.AdSetsBean.CampaignBeanX.AccountBeanXXX.ChannelBeanXXX.CreateAccountBeanXXX getCreate_account() {
                        return create_account;
                    }

                    public void setCreate_account(ItemAdvPlan.AdSetsBean.CampaignBeanX.AccountBeanXXX.ChannelBeanXXX.CreateAccountBeanXXX create_account) {
                        this.create_account = create_account;
                    }

                    public ItemAdvPlan.AdSetsBean.CampaignBeanX.AccountBeanXXX.ChannelBeanXXX.UpdateAccountBeanXXX getUpdate_account() {
                        return update_account;
                    }

                    public void setUpdate_account(ItemAdvPlan.AdSetsBean.CampaignBeanX.AccountBeanXXX.ChannelBeanXXX.UpdateAccountBeanXXX update_account) {
                        this.update_account = update_account;
                    }

                    @Keep
                    public static class CreateAccountBeanXXX implements Serializable{
                        /**
                         * id : 0
                         * name :
                         * description :
                         * roles :
                         * organization :
                         * status : 0
                         * mobile_phone :
                         * dingtalk_robot :
                         * create_time : 0001-01-01T00:00:00Z
                         * update_time : 0001-01-01T00:00:00Z
                         * login_time : 0001-01-01T00:00:00Z
                         */

                        private int id;
                        private String name;
                        private String description;
                        private String roles;
                        private String organization;
                        private int status;
                        private String mobile_phone;
                        private String dingtalk_robot;
                        private String create_time;
                        private String update_time;
                        private String login_time;

                        public int getId() {
                            return id;
                        }

                        public void setId(int id) {
                            this.id = id;
                        }

                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }

                        public String getDescription() {
                            return description;
                        }

                        public void setDescription(String description) {
                            this.description = description;
                        }

                        public String getRoles() {
                            return roles;
                        }

                        public void setRoles(String roles) {
                            this.roles = roles;
                        }

                        public String getOrganization() {
                            return organization;
                        }

                        public void setOrganization(String organization) {
                            this.organization = organization;
                        }

                        public int getStatus() {
                            return status;
                        }

                        public void setStatus(int status) {
                            this.status = status;
                        }

                        public String getMobile_phone() {
                            return mobile_phone;
                        }

                        public void setMobile_phone(String mobile_phone) {
                            this.mobile_phone = mobile_phone;
                        }

                        public String getDingtalk_robot() {
                            return dingtalk_robot;
                        }

                        public void setDingtalk_robot(String dingtalk_robot) {
                            this.dingtalk_robot = dingtalk_robot;
                        }

                        public String getCreate_time() {
                            return create_time;
                        }

                        public void setCreate_time(String create_time) {
                            this.create_time = create_time;
                        }

                        public String getUpdate_time() {
                            return update_time;
                        }

                        public void setUpdate_time(String update_time) {
                            this.update_time = update_time;
                        }

                        public String getLogin_time() {
                            return login_time;
                        }

                        public void setLogin_time(String login_time) {
                            this.login_time = login_time;
                        }
                    }

                    @Keep
                    public static class UpdateAccountBeanXXX implements Serializable{
                        /**
                         * id : 0
                         * name :
                         * description :
                         * roles :
                         * organization :
                         * status : 0
                         * mobile_phone :
                         * dingtalk_robot :
                         * create_time : 0001-01-01T00:00:00Z
                         * update_time : 0001-01-01T00:00:00Z
                         * login_time : 0001-01-01T00:00:00Z
                         */

                        private int id;
                        private String name;
                        private String description;
                        private String roles;
                        private String organization;
                        private int status;
                        private String mobile_phone;
                        private String dingtalk_robot;
                        private String create_time;
                        private String update_time;
                        private String login_time;

                        public int getId() {
                            return id;
                        }

                        public void setId(int id) {
                            this.id = id;
                        }

                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }

                        public String getDescription() {
                            return description;
                        }

                        public void setDescription(String description) {
                            this.description = description;
                        }

                        public String getRoles() {
                            return roles;
                        }

                        public void setRoles(String roles) {
                            this.roles = roles;
                        }

                        public String getOrganization() {
                            return organization;
                        }

                        public void setOrganization(String organization) {
                            this.organization = organization;
                        }

                        public int getStatus() {
                            return status;
                        }

                        public void setStatus(int status) {
                            this.status = status;
                        }

                        public String getMobile_phone() {
                            return mobile_phone;
                        }

                        public void setMobile_phone(String mobile_phone) {
                            this.mobile_phone = mobile_phone;
                        }

                        public String getDingtalk_robot() {
                            return dingtalk_robot;
                        }

                        public void setDingtalk_robot(String dingtalk_robot) {
                            this.dingtalk_robot = dingtalk_robot;
                        }

                        public String getCreate_time() {
                            return create_time;
                        }

                        public void setCreate_time(String create_time) {
                            this.create_time = create_time;
                        }

                        public String getUpdate_time() {
                            return update_time;
                        }

                        public void setUpdate_time(String update_time) {
                            this.update_time = update_time;
                        }

                        public String getLogin_time() {
                            return login_time;
                        }

                        public void setLogin_time(String login_time) {
                            this.login_time = login_time;
                        }
                    }
                }

                @Keep
                public static class LaunchUserBeanXXX implements Serializable{
                    /**
                     * id : 0
                     * name :
                     * description :
                     * roles :
                     * organization :
                     * status : 0
                     * mobile_phone :
                     * dingtalk_robot :
                     * create_time : 0001-01-01T00:00:00Z
                     * update_time : 0001-01-01T00:00:00Z
                     * login_time : 0001-01-01T00:00:00Z
                     */

                    private int id;
                    private String name;
                    private String description;
                    private String roles;
                    private String organization;
                    private int status;
                    private String mobile_phone;
                    private String dingtalk_robot;
                    private String create_time;
                    private String update_time;
                    private String login_time;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getDescription() {
                        return description;
                    }

                    public void setDescription(String description) {
                        this.description = description;
                    }

                    public String getRoles() {
                        return roles;
                    }

                    public void setRoles(String roles) {
                        this.roles = roles;
                    }

                    public String getOrganization() {
                        return organization;
                    }

                    public void setOrganization(String organization) {
                        this.organization = organization;
                    }

                    public int getStatus() {
                        return status;
                    }

                    public void setStatus(int status) {
                        this.status = status;
                    }

                    public String getMobile_phone() {
                        return mobile_phone;
                    }

                    public void setMobile_phone(String mobile_phone) {
                        this.mobile_phone = mobile_phone;
                    }

                    public String getDingtalk_robot() {
                        return dingtalk_robot;
                    }

                    public void setDingtalk_robot(String dingtalk_robot) {
                        this.dingtalk_robot = dingtalk_robot;
                    }

                    public String getCreate_time() {
                        return create_time;
                    }

                    public void setCreate_time(String create_time) {
                        this.create_time = create_time;
                    }

                    public String getUpdate_time() {
                        return update_time;
                    }

                    public void setUpdate_time(String update_time) {
                        this.update_time = update_time;
                    }

                    public String getLogin_time() {
                        return login_time;
                    }

                    public void setLogin_time(String login_time) {
                        this.login_time = login_time;
                    }
                }
            }
        }
    }
}
