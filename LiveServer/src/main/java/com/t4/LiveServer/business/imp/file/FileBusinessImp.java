package com.t4.LiveServer.business.imp.file;

import com.t4.LiveServer.business.interfaze.file.FileBusiness;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class FileBusinessImp implements FileBusiness {

    @Override
    public boolean base64ToImage(String data, String path) {
        try {
            byte[] decodeBytes = Base64.getMimeDecoder().decode(data.getBytes(StandardCharsets.UTF_8));
            FileUtils.writeByteArrayToFile(new File(path), decodeBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
