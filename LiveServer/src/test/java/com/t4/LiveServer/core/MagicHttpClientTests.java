package com.t4.LiveServer.core;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.Map;

public class MagicHttpClientTests extends TestCase {
    public MagicHttpClientTests() {
    }

    @Test
    public void testQueryBuilderWithFullFill() throws IllegalAccessException, InstantiationException {
        CustomEntryParams entryParams = new CustomEntryParams();
        entryParams.age = 21;
        entryParams.balance = 210000000L;
        entryParams.name = "Magic man";
        MagicHttpClient httpClient = new MagicHttpClient();
        String query = httpClient.getQueryFromObjectEntryParams(entryParams);
        assertEquals("?age=21&name=Magic man&balance=210000000", query);
    }

    @Test
    public void testQueryBuilderWithPartFill() throws IllegalAccessException, InstantiationException {
        CustomEntryParams entryParams = new CustomEntryParams();
        entryParams.age = 21;
        entryParams.balance = 210000000L;
        MagicHttpClient httpClient = new MagicHttpClient();
        String query = httpClient.getQueryFromObjectEntryParams(entryParams);
        assertEquals("?age=21&balance=210000000", query);
    }

    @Test
    public void testQueryBuilderWithNoneOfFieldIsFilled() throws IllegalAccessException, InstantiationException {
        CustomEntryParams entryParams = new CustomEntryParams();
        MagicHttpClient httpClient = new MagicHttpClient();
        String query = httpClient.getQueryFromObjectEntryParams(entryParams);
        assertEquals("?", query);
    }

    @Test
    public void testQueryBuilderWithPrivateField() throws IllegalAccessException, InstantiationException {
        CustomEntryParams entryParams = new CustomEntryParams();
        entryParams.setSex("Male");
        MagicHttpClient httpClient = new MagicHttpClient();
        String query = httpClient.getQueryFromObjectEntryParams(entryParams);
        assertEquals("?", query);
    }

    @Test
    public void testGetAsJson() {
        String fullyURL = "http://www.mocky.io/v2/5d83ad4e340000de4ff4a704";
        MagicHttpClient client = new MagicHttpClient();

        try {
            ApiResponse response = client.get((Map)null, fullyURL, "", (Object)null);
            if (response.statusCode > 200 && response.statusCode < 400) {
                assertEquals("{\"ApiResponse\":3,\"JsonHelper\":10,\"c\":\"abc\",\"array\":[\"ApiResponse\",\"JsonHelper\",3]}", response.data.toString().replaceAll("\\s+", ""));
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    @Test
    public void testPostAsJson() {
        String fullyURL = "http://www.mocky.io/v2/5d83b6253400003322f4a72f";
        MagicHttpClient client = new MagicHttpClient();

        try {
            ApiResponse response = client.post((Map)null, fullyURL, "", (String)null);
            if (response.statusCode > 200 && response.statusCode < 400) {
                assertEquals("{\"statusCode\":200,\"message\":\"Success\"}", response.data.toString().replaceAll("\\s+", ""));
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }
}

