package com.t4.LiveServer.business.imp;

import com.t4.LiveServer.business.interfaze.ReportOnOwnerStreamBusiness;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ReportOnOwnerStreamBusinessImp implements ReportOnOwnerStreamBusiness {
    @Override
    public long viewCounts(int day, int month, int year, int accountId) {
        return 100;
    }

    @Override
    public long commentCounts(int day, int month, int year, int accountId) {
        return 200;
    }

    @Override
    public long likeCounts(int day, int month, int year, int accountId) {
        return 300;
    }

    @Override
    public long reportCounts(int day, int month, int year, int accountId) {
        return 400;
    }

    @Override
    public double getProfit(int month, int year, int userId) {
        return new Random().nextDouble()*1000;
    }
}
