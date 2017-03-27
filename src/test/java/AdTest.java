import com.comcast.services.AdService;
import com.comcast.to.AdTO;
import com.comcast.utils.IdGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

/**
 * Created by kotabek on 3/27/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class AdTest {
    @Autowired
    private AdService adService;

    @Test
    public void checkList() throws Exception {
        Assert.assertNotNull(adService.getList(true));
    }

    @Test
    public void validateEmptyObject() throws Exception {
        Assert.assertTrue(adService.createAd(new AdTO()).hasError());
    }

    @Test
    public void validateEmptyContent() throws Exception {
        AdTO to = new AdTO();
        to.setPartnerId("kotabek");
        to.setDuration(30);
        to.setContent(null);
        Assert.assertTrue(adService.createAd(to).hasError());
    }

    @Test
    public void validateEmptyDuration() throws Exception {
        //check duration validate
        AdTO to = new AdTO();
        to.setPartnerId("kotabek");
        to.setDuration(0);
        to.setContent("test context");
        Assert.assertTrue(adService.createAd(to).hasError());

        to = new AdTO();
        to.setPartnerId("kotabek");
        to.setDuration(-45);
        to.setContent("test context");
        Assert.assertTrue(adService.createAd(to).hasError());
    }

    @Test
    public void validateEmptyPartnerID() throws Exception {
        //check partnerId validate
        AdTO to = new AdTO();
        to.setPartnerId(null);
        to.setDuration(20);
        to.setContent("test context");
        Assert.assertTrue(adService.createAd(to).hasError());
    }

    @Test
    public void createCorrectAd() throws Exception {
        String partnerId = new IdGenerator().nextId();
        int duration = 3;

        //check partnerId validate
        AdTO to = new AdTO();
        to.setPartnerId(partnerId);
        to.setDuration(duration);
        to.setContent("test context");
        Assert.assertFalse(adService.createAd(to).hasError());

        //try again
        //we have to get error, because we have active compaing
        to = new AdTO();
        to.setPartnerId(partnerId);
        to.setDuration(duration);
        to.setContent("test context");
        Assert.assertTrue(adService.createAd(to).hasError());

        //set sleep and wait when we can create again
        try {
            Thread.sleep((duration + 1) * 1000);
            to = new AdTO();
            to.setPartnerId(partnerId);
            to.setDuration(duration);
            to.setContent("test context");
            Assert.assertFalse(adService.createAd(to).hasError());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //get last active compaing
        to = adService.getByPartnerId(partnerId, true);
        Assert.assertNotNull(to);
        Assert.assertNotNull(to.getPartnerId());
        Assert.assertNotNull(to.getDuration());
        Assert.assertNotNull(to.getContent());
        Assert.assertEquals(to.getPartnerId(), partnerId);
        Assert.assertEquals(to.getDuration(), duration);


        //get all active compaing by partner
        //active
        Collection<AdTO> list = adService.getListByPartnerId(partnerId);
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());
        for (AdTO item : list) {
            Assert.assertNotNull(item);
            Assert.assertNotNull(item.getPartnerId());
            Assert.assertNotNull(item.getDuration());
            Assert.assertNotNull(item.getContent());
            Assert.assertEquals(item.getPartnerId(), partnerId);
        }
    }
}
