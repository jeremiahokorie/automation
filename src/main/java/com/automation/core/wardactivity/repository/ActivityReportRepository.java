package com.automation.core.wardactivity.repository;

import com.automation.core.wardactivity.enums.ActivityCategory;
import com.automation.core.wardactivity.enums.ActivityStatus;
import com.automation.core.wardactivity.model.ActivityReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ActivityReportRepository extends JpaRepository<ActivityReport, Long> {
    List<ActivityReport> findByWardId(String wardId);

    List<ActivityReport> findByWardIdAndStatus(String wardId, ActivityStatus status);

    List<ActivityReport> findByWardIdAndCategory(String wardId, ActivityCategory category);

    List<ActivityReport> findByWardIdAndDateBetween(String wardId, LocalDate from, LocalDate to);

    long countByWardId(String wardId);

    long countByWardIdAndStatus(String wardId, ActivityStatus status);

    long countByDateBetween(LocalDate from, LocalDate to);

    @Query("""
            SELECT ar FROM ActivityReport ar
            WHERE (:wardId IS NULL OR ar.ward.id = :wardId)
              AND (:status IS NULL OR ar.status = :status)
              AND (:category IS NULL OR ar.category = :category)
              AND (:fromDate IS NULL OR ar.date >= :fromDate)
              AND (:toDate IS NULL OR ar.date <= :toDate)
            """)
    Page<ActivityReport> searchAll(@Param("wardId") String wardId,
                                   @Param("status") ActivityStatus status,
                                   @Param("category") ActivityCategory category,
                                   @Param("fromDate") LocalDate fromDate,
                                   @Param("toDate") LocalDate toDate,
                                   Pageable pageable);

    @Query("""
            SELECT ar FROM ActivityReport ar
            WHERE ar.ward.id = :wardId
              AND (:status IS NULL OR ar.status = :status)
              AND (:category IS NULL OR ar.category = :category)
              AND (:fromDate IS NULL OR ar.date >= :fromDate)
              AND (:toDate IS NULL OR ar.date <= :toDate)
            """)
    Page<ActivityReport> searchByWard(@Param("wardId") String wardId,
                                      @Param("status") ActivityStatus status,
                                      @Param("category") ActivityCategory category,
                                      @Param("fromDate") LocalDate fromDate,
                                      @Param("toDate") LocalDate toDate,
                                      Pageable pageable);

    long countByDateGreaterThanEqual(LocalDate date);

    @Query("""
            SELECT ar.ward.id, ar.ward.name, COUNT(ar)
            FROM ActivityReport ar
            GROUP BY ar.ward.id, ar.ward.name
            """)
    List<Object[]> countReportsPerWard();

    @Query("""
            SELECT ar.ward.id, ar.ward.name, COUNT(ar)
            FROM ActivityReport ar
            WHERE ar.status = :status
            GROUP BY ar.ward.id, ar.ward.name
            """)
    List<Object[]> countNeedsAttentionPerWard(@Param("status") ActivityStatus status);
}
