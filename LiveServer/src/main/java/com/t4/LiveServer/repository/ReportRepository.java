package com.t4.LiveServer.repository;

import com.t4.LiveServer.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT * from report WHERE report.live_id=:liveId AND report.reason=:reason")
    List<Report> getReportedCount(@Param("liveId") int liveId, @Param("reason") String reason);

}
