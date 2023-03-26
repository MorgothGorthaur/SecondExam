package com.example.demo.repository;

import com.example.demo.model.Child;
import com.example.demo.model.Group;
import com.example.demo.model.Relative;
import com.example.demo.model.Teacher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class RelativeRepositoryTest {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ChildRepository childRepository;
    @Autowired
    private RelativeRepository relativeRepository;

    @AfterEach
    public void remove() {
        childRepository.deleteAll();
        relativeRepository.deleteAll();
        groupRepository.deleteAll();
        teacherRepository.deleteAll();
    }


    @Test
    void testFindRelativeByIdAndChildIdAndTeacherEmail() {
        createTeacherWithGroupAndKidsAndRelatives();
        var email = "john@example.com";
        var childId = childRepository.findAll().get(0).getId();
        var relativeId = relativeRepository.findAll().get(0).getId();
        var result = relativeRepository.findRelativeWithChild(relativeId, childId, email);
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(relativeId);
    }

    @Test
    void testFindRelativesByChildIdAndTeacherEmail() {
        createTeacherWithGroupAndKidsAndRelatives();
        var email = "john@example.com";
        var childId = childRepository.findAll().get(0).getId();
        assertThat(relativeRepository.findRelatives(childId, email).size()).isEqualTo(1);
    }

    @Test
    void testFindEqualRelative() {
        createTeacherWithGroupAndKidsAndRelatives();
        assertThat(relativeRepository.findEqualRelativeWithKids("John's relative", "John's relative address", "+1234567890")).isPresent();
    }

    @Test
    void testFindEqualRelativeWithAnotherId() {
        createTeacherWithGroupAndKidsAndRelatives();
        assertThat(relativeRepository
                .findEqualRelativeWithKidsAndAnotherId("John's relative", "John's relative address", "+1234567890", 3L)).isPresent();
    }

    public void createTeacherWithGroupAndKidsAndRelatives() {
        var relative1 = new Relative("John's relative", "+1234567890", "John's relative address");
        var relative2 = new Relative("Jane's relative", "+0987654321", "Jane's relative address");
        var teacher1 = new Teacher("John Smith", "+1234567890", "john_skype", "john@example.com", "password1");
        var group1 = new Group("Group 1", 3);
        teacher1.addGroup(group1);
        teacherRepository.save(teacher1);
        var child1 = new Child("Child 1", LocalDate.of(2015, 1, 1), null);
        child1.addGroup(group1);
        child1.addRelative(relative1);
        var child2 = new Child("Child 2", LocalDate.of(2016, 2, 2), null);
        child2.addGroup(group1);
        child2.addRelative(relative1);
        childRepository.saveAll(List.of(child1, child2));
        var child3 = new Child("Child 3", LocalDate.of(2017, 3, 3), null);
        child3.addGroup(group1);
        child3.addRelative(relative2);
        childRepository.save(child3);

    }
}