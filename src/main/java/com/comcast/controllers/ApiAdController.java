package com.comcast.controllers;

import com.comcast.services.AdService;
import com.comcast.to.AdTO;
import com.comcast.to.ApiBaseResponse;
import com.comcast.utils.ApiResponces;
import com.comcast.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kotabek on 3/22/17.
 */
@Controller
public class ApiAdController {
    @Autowired
    private AdService adService;

    @RequestMapping(value = "/ad", method = RequestMethod.POST)
    @ResponseBody
    public ApiBaseResponse createAd(@RequestBody Map<String, Object> data) {
        if (data == null) {
            return ApiResponces.dataIsEmpty();
        }

        final AdTO to = AdTO.parse(data);

        if (StringUtils.isEmpty(to.getPartnerId())) {
            return ApiResponces.customError("partner_id is empty");
        } else if (to.getDuration() <= 0) {
            return ApiResponces.customError("duration is empty or incorrect");
        } else if (StringUtils.isEmpty(to.getContent())) {
            return ApiResponces.customError("ad_content is empty");
        }

        to.setStartTime(new Date(System.currentTimeMillis()));

        try {
            adService.createAd(to);
        } catch (Exception ex) {
            return ApiResponces.customError(ex.getMessage());
        }
        return ApiResponces.ok();
    }

    @RequestMapping(value = {"/ad/{partnerId}"}, method = RequestMethod.GET)
    @ResponseBody
    public Object getItem(@PathVariable(value = "partnerId") String partnerId) {
        if (StringUtils.isEmpty(partnerId)) {
            return new HashMap<>();
        }
        return adService.getByPartnerId(partnerId);
    }

    @RequestMapping(value = "/ad/list", method = RequestMethod.GET)
    @ResponseBody
    public Object getList() {
        return adService.getList(null);
    }
}
