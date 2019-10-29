package com.t4.LiveServer.core;

import com.t4.LiveServer.model.wowza.WowzaStream;
import com.t4.LiveServer.util.WowzaApiCaller;
import junit.framework.TestCase;
import org.junit.Test;

public class WowzaApiCallerTest extends TestCase {
    public WowzaApiCallerTest() {
    }

    @Test
    public void testVersionsApi() {
        WowzaApiCaller wowzaCaller = new WowzaApiCaller();

        try {
            ApiResponse response = wowzaCaller.get("/versions", (Object)null);
            if (response.statusCode >= 200 && response.statusCode < 400) {
                System.out.println(response.data);
            } else {
                System.out.println(response.statusCode + "| " + response.message);
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    @Test
    public void testGetAllStreams() {
        WowzaApiCaller wowzaCaller = new WowzaApiCaller();

        try {
            ApiResponse response = wowzaCaller.get("/v1.3/live_streams", (Object)null);
            if (response.statusCode >= 200 && response.statusCode < 400) {
                System.out.println(response.data);
            } else {
                System.out.println(response.statusCode + "| " + response.message);
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    @Test
    public void testFetchALiveStreamNotExisted() {
        WowzaApiCaller wowzaCaller = new WowzaApiCaller();

        try {
            String dumpId = "ABCDEF123";
            ApiResponse response = wowzaCaller.get("/v1.3/live_streams/" + dumpId, (Object)null);
            if (response.statusCode >= 200 && response.statusCode < 400) {
                System.out.println(response.data);
            } else {
                System.out.println(response.statusCode + "| " + response.message);
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    @Test
    public void testFetchALiveStreamExisted() {
        WowzaApiCaller wowzaCaller = new WowzaApiCaller();

        try {
            String dumpId = "xlffyjcg";
            ApiResponse response = wowzaCaller.get("/v1.3/live_streams/" + dumpId, (Object)null);
            if (response.statusCode >= 200 && response.statusCode < 400) {
                System.out.println(response.data);
            } else {
                System.out.println(response.statusCode + "| " + response.message);
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    @Test
    public void testCreateALiveStreamWithLimit3StreamsPer3HoursOrMoreHowIKnow() {
        WowzaApiCaller wowzaCaller = new WowzaApiCaller();
        WowzaStream wannaCreateStream = new WowzaStream(1280, 720, "pay_as_you_go", "asia_pacific_singapore", "other_rtmp", "CREATE_" + System.currentTimeMillis(), "transcoded");

        try {
            String jsonData = JsonHelper.serialize(wannaCreateStream);
            jsonData = "{\"live_stream\":" + jsonData + "}";
            System.out.print(jsonData);
            ApiResponse response = wowzaCaller.otherMethodAsJson("POST", "/v1.3/live_streams/", jsonData);
            if (response.statusCode >= 200 && response.statusCode < 400) {
                System.out.println(response.data);
            } else {
                System.out.println(response.statusCode + "| " + response.message + " | \n" + response.data);
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    @Test
    public void testStartExistedLiveStream() {
        WowzaApiCaller wowzaCaller = new WowzaApiCaller();

        try {
            String existedId = "xlffyjcg";
            ApiResponse response = wowzaCaller.otherMethodAsJson("PUT", "/v1.3/live_streams/" + existedId + "/start", (String)null);
            if (response.statusCode >= 200 && response.statusCode < 400) {
                System.out.println(response.data);
            } else {
                System.out.println(response.statusCode + "| " + response.message + "\n" + response.data);
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    @Test
    public void testStopExistedLiveStreamOnStartState() {
        WowzaApiCaller wowzaCaller = new WowzaApiCaller();

        try {
            String existedId = "xlffyjcg";
            ApiResponse response = wowzaCaller.otherMethodAsJson("PUT", "/v1.3/live_streams/" + existedId + "/stop", (String)null);
            if (response.statusCode >= 200 && response.statusCode < 400) {
                System.out.println(response.data);
            } else {
                System.out.println(response.statusCode + "| " + response.message + "\n" + response.data);
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    @Test
    public void testResetExistedLiveStreamOnStartState() {
        WowzaApiCaller wowzaCaller = new WowzaApiCaller();

        try {
            String existedId = "xlffyjcg";
            ApiResponse response = wowzaCaller.otherMethodAsJson("PUT", "/v1.3/live_streams/" + existedId + "/reset", (String)null);
            if (response.statusCode >= 200 && response.statusCode < 400) {
                System.out.println(response.data);
            } else {
                System.out.println(response.statusCode + "| " + response.message + "\n" + response.data);
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    @Test
    public void testRegenrateConectionCode() {
        WowzaApiCaller wowzaCaller = new WowzaApiCaller();

        try {
            String existedId = "xlffyjcg";
            ApiResponse response = wowzaCaller.otherMethodAsJson("PUT", "/v1.3/live_streams/" + existedId + "/regenerate_connection_code", (String)null);
            if (response.statusCode >= 200 && response.statusCode < 400) {
                System.out.println(response.data);
            } else {
                System.out.println(response.statusCode + "| " + response.message + "\n" + response.data);
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    @Test
    public void testFetchStateOfExistedLiveStream() {
        WowzaApiCaller wowzaCaller = new WowzaApiCaller();

        try {
            String existedId = "xlffyjcg";
            ApiResponse response = wowzaCaller.get("/v1.3/live_streams/" + existedId + "/state", (Object)null);
            if (response.statusCode >= 200 && response.statusCode < 400) {
                System.out.println(response.data);
            } else {
                System.out.println(response.statusCode + "| " + response.message + "\n" + response.data);
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }
}
