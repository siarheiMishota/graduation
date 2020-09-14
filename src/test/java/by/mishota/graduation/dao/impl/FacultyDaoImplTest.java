package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.FacultyDao;
import by.mishota.graduation.dao.factory.DaoFactory;
import by.mishota.graduation.entity.Faculty;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class FacultyDaoImplTest {

    @BeforeClass
    public void setTestDatabase() {
        DaoFactory.setPathDatabase("prop/databaseTest.properties");
    }

    @Test(dependsOnMethods = "testAdd")
    public void deleteAddingRow() throws DaoException {
        DaoFactory.getInstance().getFacultyDao().deleteByName("test");
    }

    @Test(dependsOnMethods = "testUpdate")
    public void updateRow() throws DaoException, DuplicateException {
        Faculty faculty = new Faculty();
        faculty.setId(4);
        faculty.setName("МСФ");
        faculty.setNumberFreePlaces(6);
        faculty.setNumberPayPlaces(6);
        faculty.setIdStudents(List.of());
        faculty.setIdNeedSubjects(List.of(1, 2, 3));

        DaoFactory.getInstance().getFacultyDao().update(faculty);
    }

    @Test(groups = "updating", priority = 1)
    public void testUpdate() throws DaoException, DuplicateException {
        Faculty actual = new Faculty();
        actual.setId(4);
        actual.setName("test");
        actual.setNumberFreePlaces(60);
        actual.setNumberPayPlaces(60);
        actual.setIdStudents(List.of(4,17));
        actual.setIdNeedSubjects(List.of(1, 2, 3));

        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        facultyDao.update(actual);
        Faculty expected = facultyDao.findById(4).get();
        assertEquals(actual, expected);
    }

    @Test(groups = "adding")
    public void testAdd() throws DaoException, DuplicateException {
        Faculty actual = new Faculty();
        actual.setName("test");
        actual.setNumberFreePlaces(5);
        actual.setNumberPayPlaces(1);
        actual.setIdStudents(List.of());
        actual.setIdNeedSubjects(List.of());

        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        facultyDao.add(actual);
        Faculty expected = facultyDao.findByName("test").get();
        assertEquals(actual, expected);
    }

    @Test
    public void testAddOnNull() throws DaoException, DuplicateException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        Optional<Faculty> expected = facultyDao.add(null);
        assertEquals(Optional.empty(), expected);
    }

    @Test(expectedExceptions = DaoException.class, expectedExceptionsMessageRegExp = "duplicate")
    public void testAddDuplicate() throws DaoException, DuplicateException {
        Faculty actual = new Faculty();
        actual.setName("ФИТР");
        actual.setNumberFreePlaces(5);
        actual.setNumberPayPlaces(1);
        actual.setIdStudents(List.of());
        actual.setIdNeedSubjects(List.of());

        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        facultyDao.add(actual);
    }

    @Test
    public void testDelete() throws DaoException, DuplicateException {
        Faculty actual = new Faculty();
        actual.setName("test");
        actual.setNumberFreePlaces(5);
        actual.setNumberPayPlaces(1);
        actual.setIdStudents(List.of());
        actual.setIdNeedSubjects(List.of());

        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        facultyDao.add(actual);
        assertTrue(facultyDao.deleteById(actual.getId()));
    }

    @Test
    public void testDeleteWithNegativeId() throws DaoException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        assertFalse(facultyDao.deleteById(-1));
    }

    @Test
    public void testDeleteNotExist() throws DaoException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        assertFalse(facultyDao.deleteById(1000));
    }

    @Test
    public void testDeleteByName() throws DaoException, DuplicateException {
        Faculty actual = new Faculty();
        actual.setName("test");
        actual.setNumberFreePlaces(5);
        actual.setNumberPayPlaces(1);
        actual.setIdStudents(List.of());
        actual.setIdNeedSubjects(List.of());

        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        facultyDao.add(actual);
        assertTrue(facultyDao.deleteByName("test"));
    }

    @Test
    public void testDeleteByNameNotExist() throws DaoException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        assertFalse(facultyDao.deleteByName("asdfsgdfhgbfasdf"));
    }

    @Test
    public void testDeleteByNameOnNull() throws DaoException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        assertFalse(facultyDao.deleteByName(null));
    }

    @Test
    public void testNumberFaculties() throws DaoException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        int actual = 13;
        int expected = facultyDao.numberFaculties();
        assertEquals(actual, expected);
    }

    @Test
    public void testFindAll() throws DaoException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        int expected = facultyDao.findAll().size();
        int actual = 13;
        assertEquals(actual, expected);
    }

    @Test
    public void testFindAllByNeedSubjects() throws DaoException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        int expected = facultyDao.findAllByNeedSubjects(List.of(1, 2, 3)).size();
        int actual = 6;
        assertEquals(actual, expected);
    }

    @Test
    public void testFindAllByNeedSubjectsNotExist() throws DaoException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        List<Faculty> expected = facultyDao.findAllByNeedSubjects(List.of(10, 2, 3));
        assertEquals(List.of(), expected);
    }

    @Test
    public void testFindAllByNeedSubjectsOnNull() throws DaoException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        int expected = facultyDao.findAllByNeedSubjects(null).size();
        int actual = 0;
        assertEquals(actual, expected);
    }

    @Test
    public void testFindFewSkippingFew() throws DaoException {
        Faculty faculty1 = new Faculty();
        Faculty faculty2 = new Faculty();
        Faculty faculty3 = new Faculty();
        faculty1.setId(4);
        faculty2.setId(5);
        faculty3.setId(6);
        faculty1.setName("МСФ");
        faculty2.setName("МТФ");
        faculty3.setName("ФММП");
        faculty1.setNumberFreePlaces(6);
        faculty2.setNumberFreePlaces(2);
        faculty3.setNumberFreePlaces(6);
        faculty1.setNumberPayPlaces(6);
        faculty2.setNumberPayPlaces(3);
        faculty3.setNumberPayPlaces(4);
        faculty1.setIdNeedSubjects(List.of(1, 2, 3));
        faculty2.setIdNeedSubjects(List.of(1, 2, 3));
        faculty3.setIdNeedSubjects(List.of(1, 2, 3));
        faculty1.setIdStudents(List.of(4,17));
        faculty2.setIdStudents(List.of(5,18));
        faculty3.setIdStudents(List.of(6,19));
        List<Faculty> actualFaculties = List.of(faculty1, faculty2, faculty3);

        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        List<Faculty> expectedFaculties = facultyDao.findFewSkippingFew(3, 3);
        assertEquals(actualFaculties, expectedFaculties);
    }

    @Test
    public void testFindFewSkippingFewNotExist() throws DaoException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        List<Faculty> expectedFaculties = facultyDao.findFewSkippingFew(3, 30);
        assertEquals(List.of(), expectedFaculties);
    }

    @Test
    public void testFindById() throws DaoException {
        Faculty actualFaculty = new Faculty();
        actualFaculty.setId(4);
        actualFaculty.setName("МСФ");
        actualFaculty.setNumberFreePlaces(6);
        actualFaculty.setNumberPayPlaces(6);
        actualFaculty.setIdNeedSubjects(List.of(1, 2, 3));
        actualFaculty.setIdStudents(List.of(4,17));

        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        Faculty expectedFaculty = facultyDao.findById(4).get();
        assertEquals(actualFaculty, expectedFaculty);
    }

    @Test
    public void testFindByIdNotExist() throws DaoException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        Optional<Faculty> expectedFaculty = facultyDao.findById(400);
        assertEquals(Optional.empty(), expectedFaculty);
    }

    @Test
    public void testFindWhereFreePlacesMoreSpecify() throws DaoException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        int expectedSize = facultyDao.findWhereFreePlacesMoreSpecify(5).size();
        assertEquals(4, expectedSize);
    }

    @Test
    public void testFindWhereFreePlacesMoreSpecifyNotExist() throws DaoException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        List<Faculty> expectedSize = facultyDao.findWhereFreePlacesMoreSpecify(5000);
        assertEquals(List.of(), expectedSize);
    }

    @Test
    public void testFindWhereFreePlacesLessSpecify() throws DaoException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        int expectedSize = facultyDao.findWhereFreePlacesLessSpecify(5).size();
        assertEquals(7, expectedSize);
    }

    @Test
    public void testFindWhereFreePlacesLessSpecifyNotExist() throws DaoException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        List<Faculty> expectedSize = facultyDao.findWhereFreePlacesLessSpecify(-5);
        assertEquals(List.of(), expectedSize);
    }

    @Test
    public void testIsExistId() throws DaoException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        assertTrue(facultyDao.isExistId(4));
    }

    @Test
    public void testIsExistIdNotExist() throws DaoException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        assertFalse(facultyDao.isExistId(400));
    }
}