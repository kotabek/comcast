package com.comcast.services.impl;

import com.comcast.services.AdService;
import com.comcast.to.AdTO;
import com.comcast.utils.AdValidateResult;
import com.comcast.utils.DG;
import com.comcast.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by kotabek on 3/22/17.
 */
@Service
public class AdServiceImpl implements AdService {
    private static Map<String, AdTO> ads = new ConcurrentHashMap<>();
    private static Map<String, List<AdTO>> adhistory = new ConcurrentHashMap<>();

    /**
     * Register ne compaing to partner with validation
     *
     * @param to compaing
     * @return result of validation compaing before registered one
     */
    @Override
    public AdValidateResult createAd(AdTO to) {
        AdValidateResult validate = new AdValidateResult();
        if (to == null) {
            validate.regError("Income data is empty");
        } else {
            if (StringUtils.isEmpty(to.getPartnerId())) {
                validate.regError("PartnerId is required");
            }
            if (to.getDuration() <= 0) {
                validate.regError("Duration is required");
            }
            if (StringUtils.isEmpty(to.getContent())) {
                validate.regError("Content is required");
            }
        }
        if (validate.hasError()) {
            return validate;
        }

        if (ads.containsKey(to.getPartnerId())) {
            AdTO currentAd = ads.get(to.getPartnerId());
            if (currentAd.isActive()) {
                validate.regError("Partner has active compaing");
            }
            if (validate.hasError()) {
                return validate;
            }
        }
        to.setStartTime(new Date(System.currentTimeMillis()));
        ads.put(to.getPartnerId(), to);
        adhistory.putIfAbsent(to.getPartnerId(), new CopyOnWriteArrayList<>());
        adhistory.get(to.getPartnerId()).add(to);
        return validate;
    }

    /**
     * Get compaing by Partner Id
     *
     * @param partnerId  Partner Id, return null if id is empty
     * @param onlyActive return only if current compaing is active
     * @return return one compaing
     */
    @Override
    public AdTO getByPartnerId(String partnerId, boolean onlyActive) {
        partnerId = DG.getString(partnerId);
        if (StringUtils.isEmpty(partnerId)) {
            return null;
        }
        AdTO to = ads.get(partnerId);
        if (to != null
            && onlyActive
            && !to.isActive()) {
            return null;
        }
        return to;
    }

    /**
     * Get all compaing by Partner Id
     *
     * @param partnerId Partner Id, return empty collection if id is empty
     * @return return collection of compaings
     */
    @Override
    public Collection<AdTO> getListByPartnerId(String partnerId) {
        partnerId = DG.getString(partnerId);

        if (!StringUtils.isEmpty(partnerId)) {
            List<AdTO> list = adhistory.get(partnerId);
            if (list != null) {
                return list;
            }
        }

        return Collections.emptyList();
    }

    @Override
    public Collection<AdTO> getList(boolean onlyActive) {
        if (onlyActive) {
            List<AdTO> result = new ArrayList<>(ads.size());
            for (AdTO to : ads.values()) {
                if (to.isActive()) {
                    result.add(to);
                }
            }
            return result;
        }
        return ads.values();
    }
}
