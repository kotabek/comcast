import com.comcast.utils.ApiResponces;
import com.comcast.utils.IdGenerator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kotabek on 3/22/17.
 */
public class AdApiTest {
    private static String AD_PATH = "/ad";

    @Test
    public void checkList() throws Exception {
        JSONArray list = Api.getArray(Api.doGet(AD_PATH + "/list", null, null));
        Assert.assertNotNull(list);
        for (int i = 0; i < list.size(); i++) {
            JSONObject obj = (JSONObject) list.get(i);
            Assert.assertNotNull(obj.get("partner_id"));
            Assert.assertNotNull(obj.get("duration"));
            Assert.assertNotNull(obj.get("ad_content"));
        }
    }

    @Test
    public void validateEmptyObject() throws Exception {
        //first case object is empty
        Map<String, Object> ad = new HashMap<>();
        JSONObject obj = Api.getObject(Api.doPost(AD_PATH, ad, null, null));
        Assert.assertNotNull(obj);
        Assert.assertNotNull(obj.get("status"));
        Assert.assertNotNull(obj.get("message"));
        Assert.assertEquals(obj.get("status"), ApiResponces.STATUS_ERROR);
    }

    @Test
    public void validateEmptyContent() throws Exception {
        //check content validate
        Map<String, Object> ad = new HashMap<>();
        ad.put("partner_id", "kotabek");
        ad.put("duration", 30);
        ad.put("ad_content", null);
        JSONObject obj = Api.getObject(Api.doPost(AD_PATH, ad, null, null));
        Assert.assertNotNull(obj);
        Assert.assertNotNull(obj.get("status"));
        Assert.assertNotNull(obj.get("message"));
        Assert.assertEquals(obj.get("status"), ApiResponces.STATUS_ERROR);
        Assert.assertTrue(String.valueOf(obj.get("message")).toLowerCase().contains("content"));

    }

    @Test
    public void validateEmptyDuration() throws Exception {
        //check duration validate
        Map<String, Object> ad = new HashMap<>();
        ad.put("partner_id", "kotabek");
        ad.put("duration", 0);
        ad.put("ad_content", "test content");
        JSONObject obj = Api.getObject(Api.doPost(AD_PATH, ad, null, null));
        Assert.assertNotNull(obj);
        Assert.assertNotNull(obj.get("status"));
        Assert.assertNotNull(obj.get("message"));
        Assert.assertEquals(obj.get("status"), ApiResponces.STATUS_ERROR);
        Assert.assertTrue(String.valueOf(obj.get("message")).toLowerCase().contains("duration"));

        ad = new HashMap<>();
        ad.put("partner_id", "kotabek");
        ad.put("duration", -45);
        ad.put("ad_content", "test content");
        obj = Api.getObject(Api.doPost(AD_PATH, ad, null, null));
        Assert.assertNotNull(obj);
        Assert.assertNotNull(obj.get("status"));
        Assert.assertNotNull(obj.get("message"));
        Assert.assertEquals(obj.get("status"), ApiResponces.STATUS_ERROR);
        Assert.assertTrue(String.valueOf(obj.get("message")).toLowerCase().contains("duration"));

    }

    @Test
    public void validateEmptyPartnerID() throws Exception {
        //check partnerId validate
        Map<String, Object> ad = new HashMap<>();
        ad.put("partner_id1", "kotabek");
        ad.put("duration", 30);
        ad.put("ad_content", "test content");
        JSONObject obj = Api.getObject(Api.doPost(AD_PATH, ad, null, null));
        Assert.assertNotNull(obj);
        Assert.assertNotNull(obj.get("status"));
        Assert.assertNotNull(obj.get("message"));
        Assert.assertEquals(obj.get("status"), ApiResponces.STATUS_ERROR);
        Assert.assertTrue(String.valueOf(obj.get("message")).toLowerCase().contains("partner"));
    }

    @Test
    public void createCorrectAd() throws Exception {
        String partnerId = new IdGenerator().nextId();
        int duration = 3;
        //check partnerId validate
        Map<String, Object> ad = new HashMap<>();
        ad.put("partner_id", partnerId);
        ad.put("duration", duration);
        ad.put("ad_content", "test content");
        JSONObject obj = Api.getObject(Api.doPost(AD_PATH, ad, null, null));
        Assert.assertNotNull(obj);
        Assert.assertNotNull(obj.get("status"));
        Assert.assertEquals(obj.get("status"), ApiResponces.STATUS_SUCCEFUL);

        //try again
        //we have to get error, because we have active compaing
        obj = Api.getObject(Api.doPost(AD_PATH, ad, null, null));
        Assert.assertNotNull(obj);
        Assert.assertNotNull(obj.get("status"));
        Assert.assertEquals(obj.get("status"), ApiResponces.STATUS_ERROR);

        //set sleep and wait when we can create again
        try {
            Thread.sleep((duration + 1) * 1000);
            obj = Api.getObject(Api.doPost(AD_PATH, ad, null, null));
            Assert.assertNotNull(obj);
            Assert.assertNotNull(obj.get("status"));
            Assert.assertEquals(obj.get("status"), ApiResponces.STATUS_SUCCEFUL);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //get last active compaing
        obj = Api.getObject(Api.doGet(AD_PATH + "/" + partnerId, null, null));
        Assert.assertNotNull(obj);
        Assert.assertNotNull(obj.get("partner_id"));
        Assert.assertNotNull(obj.get("duration"));
        Assert.assertNotNull(obj.get("ad_content"));
        Assert.assertEquals(obj.get("partner_id"), partnerId);
        Assert.assertEquals((Long) obj.get("duration"), Long.valueOf(duration));


        //get all active compaing by partner
        //active
        JSONArray list = Api.getArray(Api.doGet(AD_PATH + "/" + partnerId, new HashMap<String, Object>() {{
            put("active", false);
        }}, null));
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());
        for (int i = 0; i < list.size(); i++) {
            JSONObject item = (JSONObject) list.get(i);
            Assert.assertNotNull(item.get("partner_id"));
            Assert.assertEquals(item.get("partner_id"), partnerId);
            Assert.assertNotNull(item.get("duration"));
            Assert.assertNotNull(item.get("ad_content"));
        }
    }

}
