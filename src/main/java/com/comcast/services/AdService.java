package com.comcast.services;

import com.comcast.to.AdTO;

import java.util.Collection;

/**
 * Created by kotabek on 3/22/17.
 */
public interface AdService {
    void createAd(AdTO to);

    AdTO getByPartnerId(String partnerId);

    Collection<AdTO> getList(String partnerId);
}
