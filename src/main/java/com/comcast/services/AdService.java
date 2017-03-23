package com.comcast.services;

import com.comcast.to.AdTO;
import com.comcast.utils.AdValidateResult;

import java.util.Collection;

/**
 * Created by kotabek on 3/22/17.
 */
public interface AdService {
    AdValidateResult createAd(AdTO to);

    AdTO getByPartnerId(String partnerId, boolean onlyActive);

    Collection<AdTO> getListByPartnerId(String partnerId);

    Collection<AdTO> getList(boolean onlyActive);
}
