package com.comcast.services.impl;

import com.comcast.services.AdService;
import com.comcast.to.AdTO;
import com.comcast.utils.DG;
import com.comcast.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kotabek on 3/22/17.
 */
@Service
public class AdServiceImpl implements AdService {
    private static Map<String, AdTO> ads = new ConcurrentHashMap<>();

    @Override
    public void createAd(AdTO to) {
        System.out.println("Start add to=" + to.toString());
        ads.put(to.getPartnerId(), to);
    }

    @Override
    public AdTO getByPartnerId(String partnerId) {
        partnerId = DG.getString(partnerId);
        if (StringUtils.isEmpty(partnerId)) {
            return null;
        }
        return ads.get(partnerId);
    }

    @Override
    public Collection<AdTO> getList(String partnerId) {

        partnerId = DG.getString(partnerId);

        if (StringUtils.isEmpty(partnerId)) {
            return ads.values();

        } else if (ads.containsKey(partnerId)) {
            return Collections.singletonList(ads.get(partnerId));
        }
        return Collections.emptyList();
    }
}
