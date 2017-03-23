package com.comcast.controllers;

import com.comcast.services.AdService;
import com.comcast.to.AdTO;
import com.comcast.to.ApiBaseResponse;
import com.comcast.utils.ApiResponces;
import com.comcast.utils.StringUtils;
import com.comcast.utils.AdValidateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;

/**
 * Created by kotabek on 3/22/17.
 */
@Controller
@RequestMapping(value = "/ad")
public class ApiAdController {
    @Autowired
    private AdService adService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ApiBaseResponse createAd(@RequestBody AdTO to) {

        try {
            AdValidateResult validateResult = adService.createAd(to);

            if (validateResult.hasError()) {
                return ApiResponces.customError(validateResult.generateMessage());
            }
        } catch (Exception ex) {
            return ApiResponces.customError(ex.getMessage());
        }
        return ApiResponces.ok();
    }

    /**
     * Returm currect active compang by partnerId
     *
     * @return whole list of compaings
     */
    @RequestMapping(value = "/{partnerId}", method = RequestMethod.GET)
    @ResponseBody
    public Object getItem(
            @PathVariable(value = "partnerId") String partnerId,
            @RequestParam(value = "active", required = false) Boolean active
                         ) {
        if (StringUtils.isEmpty(partnerId)) {
            return new HashMap<>();
        }
        if (active != null && !active) {
            return adService.getListByPartnerId(partnerId);
        }
        AdTO to = adService.getByPartnerId(partnerId, true);
        if (to == null) {
            return Collections.emptyMap();
        }
        return to;
    }

    /**
     * Temp method for incorrect requests
     *
     * @return empty object
     */
    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    @ResponseBody
    public Object getEmptyItem() {
        return Collections.emptyMap();
    }

    /**
     * Returm all compaings
     *
     * @return whole list of compaings
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object getList() {
        return adService.getList(true);
    }
}
