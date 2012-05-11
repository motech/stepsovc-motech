package org.wv.stepsovc.dmis.migration;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CareGiverUploader {

    @Autowired


    //username,password,name,phone_no
    public void uploadCareGiver() throws IOException {
        final String path = getClass().getResource("/caregiver.csv").getPath();
        List<String> caregiverRows = FileUtils.readLines(new File(path), "UTF-8");
        for (String caregiverRow : caregiverRows) {
            String[] arr = caregiverRow.split(",");


        }
    }
}
