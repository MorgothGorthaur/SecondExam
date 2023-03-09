package com.example.demo.repository;

import com.example.demo.model.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChildRepository extends JpaRepository<Child, Long> {

    @Query(value = """
            SELECT children.* FROM children
            JOIN teachers t ON children.group_id = t.group_id
            WHERE t.email = ?1""", nativeQuery = true)
    List<Child> getKidsByTeacherEmail(String email);

    @Query(value = """
            SELECT children.* FROM children
            JOIN teachers t ON children.group_id = t.group_id
            WHERE children.id = ?1 AND t.email = ?2""", nativeQuery = true)
    Optional<Child> getChildByIdAndTeacherEmail(long id, String email);

    @Query(value = """
            SELECT children.*
            FROM children
            JOIN teachers ON children.group_id = teachers.group_id
            WHERE teachers.email = ?1 and DATE_ADD(birth_year, INTERVAL YEAR(CURRENT_DATE()) - YEAR(birth_year) YEAR) > CURRENT_DATE();""", nativeQuery = true)
    List<Child> getChildThatWaitBirthDay(String email);

    @Query(value = """
            SELECT DISTINCT c.* FROM children c
            JOIN children_relatives cr ON c.id = cr.kids_id
            JOIN relatives r ON cr.relatives_id = r.id
            WHERE r.id = ?1
            AND c.id <> ?1
            """, nativeQuery = true)
    List<Child> getBrothersAndSisters(long id);
}
