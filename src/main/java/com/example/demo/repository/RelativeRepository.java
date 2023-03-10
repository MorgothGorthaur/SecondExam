package com.example.demo.repository;

import com.example.demo.model.Relative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RelativeRepository extends JpaRepository<Relative, Long> {
    @Query(value = """
            SELECT relatives.* FROM relatives
            JOIN children_relatives cr ON relatives.id = cr.relatives_id
            JOIN children c ON c.id = cr.kids_id
            JOIN teachers t ON c.group_id = t.group_id
            WHERE relatives_id = ?1 AND c.id = ?2 AND t.email = ?3""", nativeQuery = true)

    Optional<Relative> getRelative(long relativeId, long kidsId, String email);

    @Query("SELECT r FROM Relative r JOIN r.kids k JOIN k.group g JOIN g.teacher t WHERE k.id = ?1 AND t.email = ?2")
    List<Relative> getAllRelativesByChildIdAndTeacherEmail(Long childId, String teacherEmail);

    Optional<Relative> findByAddress(String address);

}
