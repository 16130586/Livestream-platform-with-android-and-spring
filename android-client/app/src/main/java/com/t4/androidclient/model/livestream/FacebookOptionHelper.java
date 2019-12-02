package com.t4.androidclient.model.livestream;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.t4.androidclient.model.livestream.group.FacebookGroup;
import com.t4.androidclient.model.livestream.group.Group;
import com.t4.androidclient.model.livestream.page.FacebookPage;
import com.t4.androidclient.model.livestream.page.Page;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class FacebookOptionHelper {

    public List<Page> parseFacebookPage(String rs) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            FacebookPage facebookPage = objectMapper.readValue(rs, FacebookPage.class);
            return (facebookPage.data != null && facebookPage.data.size() > 0)
                    ? facebookPage.data : Collections.emptyList();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Group> parseFacebookGroup(String rs) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            FacebookGroup facebookGroup = objectMapper.readValue(rs, FacebookGroup.class);
            return (facebookGroup.data != null && facebookGroup.data.size() > 0)
                    ? facebookGroup.data : Collections.emptyList();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
