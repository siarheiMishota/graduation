package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.SubjectResultDao;
import by.mishota.graduation.dao.factory.DaoFactory;
import by.mishota.graduation.entity.SubjectResult;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class SubjectResultDaoImplTest {

    @BeforeClass
    public void setTestDatabase() {
        DaoFactory.setPathDatabase("prop/databaseTest.properties");
    }

    @Test
    public void testFindById() throws DaoException {
        SubjectResult actual = new SubjectResult();
        actual.setId(5);
        actual.setPoints(29);
        actual.setSubjectId(2);

        SubjectResultDao subjectResultDao = DaoFactory.getInstance().getSubjectResultDao();
        Optional<SubjectResult> expected = subjectResultDao.findById(5);
        assertEquals(actual, expected.get());
    }

    @Test
    public void testFindByIdNotExist() throws DaoException {
        SubjectResultDao subjectResultDao = DaoFactory.getInstance().getSubjectResultDao();
        Optional<SubjectResult> expected = subjectResultDao.findById(5000);
        assertEquals(Optional.empty(), expected);
    }

    @Test
    public void testFindByIdWithNegativeId() throws DaoException {
        SubjectResultDao subjectResultDao = DaoFactory.getInstance().getSubjectResultDao();
        Optional<SubjectResult> expected = subjectResultDao.findById(-1);
        assertEquals(Optional.empty(), expected);
    }

    @Test
    public void testFindAllByEntrantId() throws DaoException {
        SubjectResultDao subjectResultDao = DaoFactory.getInstance().getSubjectResultDao();
        List<SubjectResult> expected = subjectResultDao.findAllByEntrantId(5);
        assertEquals(3, expected.size());
    }

    @Test
    public void testFindAllByEntrantIdNotExist() throws DaoException {
        SubjectResultDao subjectResultDao = DaoFactory.getInstance().getSubjectResultDao();
        List<SubjectResult> expected = subjectResultDao.findAllByEntrantId(50000);
        assertEquals(List.of(), expected);
    }

    @Test
    public void testFindAllByEntrantIdWithNegativeId() throws DaoException {
        SubjectResultDao subjectResultDao = DaoFactory.getInstance().getSubjectResultDao();
        List<SubjectResult> expected = subjectResultDao.findAllByEntrantId(-1);
        assertEquals(List.of(), expected);
    }

    @Test
    public void testDelete() throws DaoException, DuplicateException {
        SubjectResult actual = new SubjectResult();
        actual.setPoints(29);
        actual.setSubjectId(2);

        SubjectResultDao subjectResultDao = DaoFactory.getInstance().getSubjectResultDao();
        subjectResultDao.add(actual, 19);

        assertTrue(subjectResultDao.delete(actual));
    }

    @Test
    public void testDeleteOnNull() throws DaoException, DuplicateException {
        SubjectResultDao subjectResultDao = DaoFactory.getInstance().getSubjectResultDao();
        assertFalse(subjectResultDao.delete(null));
    }

    @Test
    public void testAdd() throws DaoException, DuplicateException {
        SubjectResult actual = new SubjectResult();
        actual.setPoints(29);
        actual.setSubjectId(2);

        SubjectResultDao subjectResultDao = DaoFactory.getInstance().getSubjectResultDao();
        Optional<SubjectResult> expected = subjectResultDao.add(actual, 19);
        assertTrue(expected.isPresent());

        subjectResultDao.delete(actual);
    }

    @Test(expectedExceptions = DaoException.class, expectedExceptionsMessageRegExp = "duplicate")
    public void testAddDuplicate() throws DaoException, DuplicateException {
        SubjectResult actual = new SubjectResult();
        actual.setPoints(29);
        actual.setSubjectId(7);

        SubjectResultDao subjectResultDao = DaoFactory.getInstance().getSubjectResultDao();
        Optional<SubjectResult> expected = subjectResultDao.add(actual, 19);
    }

    @Test
    public void testAddOnNull() throws DaoException, DuplicateException {
        SubjectResultDao subjectResultDao = DaoFactory.getInstance().getSubjectResultDao();
        Optional<SubjectResult> expected = subjectResultDao.add(null, 20);
        assertTrue(expected.isEmpty());
    }

    @Test
    public void testUpdate() throws DaoException, DuplicateException {
        SubjectResult actual = new SubjectResult();
        actual.setId(57);
        actual.setPoints(29);
        actual.setSubjectId(2);

        SubjectResult source = new SubjectResult();
        source.setId(57);
        source.setPoints(60);
        source.setSubjectId(9);

        SubjectResultDao subjectResultDao = DaoFactory.getInstance().getSubjectResultDao();
        int expected = subjectResultDao.update(actual,19);
        assertEquals(1,expected);

        subjectResultDao.update(source,19);
    }

    @Test(expectedExceptions = DaoException.class,expectedExceptionsMessageRegExp = "duplicate")
    public void testUpdateDuplicate() throws DaoException, DuplicateException {
        SubjectResult actual = new SubjectResult();
        actual.setId(57);
        actual.setPoints(29);
        actual.setSubjectId(8);

        SubjectResultDao subjectResultDao = DaoFactory.getInstance().getSubjectResultDao();
        subjectResultDao.update(actual,19);
    }

    @Test
    public void testUpdateOnNull() throws DaoException, DuplicateException {
        SubjectResultDao subjectResultDao = DaoFactory.getInstance().getSubjectResultDao();
        int expected = subjectResultDao.update(null,19);
        assertEquals(-1,expected);
    }

}