package com.comcast.to;

import com.comcast.utils.DG;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;
import java.util.Map;

/**
 * Created by kotabek on 3/22/17.
 */
public class AdTO {
    @JsonProperty(value = "partner_id")
    private String partnerId;
    @JsonIgnore
    private Date startTime;
    @JsonProperty(value = "duration")
    private int duration;
    @JsonProperty(value = "ad_duration")
    private String content;


    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static AdTO parse(Map<String, Object> data) {
        final AdTO to = new AdTO();
        if (data != null) {
            to.setPartnerId(DG.getString(data.get("partner_id")).trim());
            to.setDuration(DG.getInt(data.get("duration")));
            to.setContent(DG.getString(data.get("ad_content")).trim());
        }
        return to;
    }

    @Override
    public String toString() {
        return "AdTO{" +
               "partnerId='" + partnerId + '\'' +
               ", startTime=" + startTime +
               ", duration=" + duration +
               ", content='" + content + '\'' +
               '}';
    }
}
