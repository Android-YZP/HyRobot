package com.hy.robot.bean;

import java.util.List;

public class CrossTalkBean {

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * _id : 8bfa0048df2375ddab23c316c2fdf9bd
         * actor : 王文玉
         * album : 王文玉相声
         * category : 对口相声
         * chapter : 146
         * description : 王文玉，男，1937年出生，天津人，相声、评书演员。自幼喜爱曲艺。1957年拜相声演员张振圻为师，学说相声。后又拜张力川为师，学说评书。1960年自河北大学肄业后，参加了天津市南开区曲艺团，开始评书表演。从艺几十年来，他在中央电台、中央电视台、天津电台、天津电视台，录制了较多的音像节目。如：《三条石》、《陆文庆上任》、《封神义》、《血祭白狗坟》、《德子外传》、《岳云》、《童侠彪子头》等，并在天津交通台录制了故事百余段。特别还为天津文艺台录制了京韵大鼓《金陵十二钗》的解说词。“文革”后，他被调入天津市实验曲
         * duration : 1287
         * name : 鹑鸟奇闻
         * resourceId :
         * source : qingtingfm
         * type : 1
         * url : http://od.open.qingting.fm/m4a/583e71d97cb8913978625ea5_6391126_64.m4a?u=786&channelId=197972&programId=6012590
         * webUrl : http://m.qingting.fm/vchannels/197972/programs/6012590
         */

        private String _id;
        private String actor;
        private String album;
        private String category;
        private int chapter;
        private String description;
        private String duration;
        private String name;
        private String resourceId;
        private String source;
        private String type;
        private String url;
        private String webUrl;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getActor() {
            return actor;
        }

        public void setActor(String actor) {
            this.actor = actor;
        }

        public String getAlbum() {
            return album;
        }

        public void setAlbum(String album) {
            this.album = album;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getChapter() {
            return chapter;
        }

        public void setChapter(int chapter) {
            this.chapter = chapter;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getResourceId() {
            return resourceId;
        }

        public void setResourceId(String resourceId) {
            this.resourceId = resourceId;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getWebUrl() {
            return webUrl;
        }

        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }
    }
}
