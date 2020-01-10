package com.t4.LiveServer.business.interfaze;

public interface ReportOnOwnerStreamBusiness {
    long viewCounts(int day , int month , int year, int accountId);
    long commentCounts(int day , int month , int year, int accountId);
    long likeCounts(int day , int month , int year, int accountId);
    long reportCounts(int day , int month , int year, int accountId);
    double getProfit(int month , int year, int userId);
}
