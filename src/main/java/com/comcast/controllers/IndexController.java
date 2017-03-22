package com.comcast.controllers;

import com.comcast.services.AdService;
import com.comcast.to.AdTO;
import com.comcast.utils.IdGenerator;
import com.comcast.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * Created by kotabek on 3/22/17.
 */
@Controller
public class IndexController {

    @Autowired
    private AdService adService;

    @RequestMapping(value = {"/", "/**"}, method = RequestMethod.GET)
    public ModelAndView indexPage() {
        AdTO to = new AdTO();
        to.setPartnerId(new IdGenerator().nextId());
        return new ModelAndView("index")
                .addObject("list", adService.getList(null))
                .addObject("item", to);
    }

    @RequestMapping(value = {"/", "/**"}, method = RequestMethod.POST)
    public ModelAndView indexPage(@ModelAttribute(value = "item") AdTO to) {

        ModelAndView mv = new ModelAndView("index");

        String errorMessage = null;
        if (StringUtils.isEmpty(to.getPartnerId())) {
            errorMessage = "Partner Id is empty";
        } else if (to.getDuration() <= 0) {
            errorMessage = "Duration is empty or incorrect";
        } else if (StringUtils.isEmpty(to.getContent())) {
            errorMessage = "Content is empty";
        }
        if (StringUtils.isEmpty(errorMessage)) {
            to.setStartTime(new Date(System.currentTimeMillis()));
            adService.createAd(to);
            to = new AdTO();
            to.setPartnerId(new IdGenerator().nextId());

        } else {
            mv.addObject("errorMessage", errorMessage);

        }

        return mv.addObject("list", adService.getList(null))
                 .addObject("item", to);
    }
}
