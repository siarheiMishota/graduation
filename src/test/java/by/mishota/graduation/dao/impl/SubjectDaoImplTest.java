package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.SubjectDao;
import by.mishota.graduation.dao.factory.DaoFactory;
import by.mishota.graduation.entity.Subject;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class SubjectDaoImplTest {

    @BeforeClass
    public void setTestDatabase() {
        DaoFactory.setPathDatabase("prop/databaseTest.properties");
    }

    @Test
    public void testAdd() throws DaoException, DuplicateException {
        Subject subject = new Subject();
        subject.setName("testSubject");

        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        Optional<Subject> expected = subjectDao.add(subject);
        assertTrue(expected.isPresent());
        subjectDao.delete(subject);
    }

    @Test
    public void testAddOnNull() throws DaoException, DuplicateException {
        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        Optional<Subject> expected = subjectDao.add(null);
        assertTrue(expected.isEmpty());
    }

    @Test(expectedExceptions = DaoException.class,expectedExceptionsMessageRegExp = "duplicate")
    public void testAddDuplicate() throws DaoException, DuplicateException {
        Subject subject = new Subject();
        subject.setName("maths");

        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        subjectDao.add(subject);
    }

    @Test
    public void testUpdate() throws DaoException, DuplicateException {
        Subject subject = new Subject();
        subject.setId(8);
        subject.setName("updateSubject");

        Subject source = new Subject();
        source.setId(8);
        source.setName("social.science");

        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        assertEquals(1, subjectDao.update(subject));
        subjectDao.update(source);
    }

    @Test
    public void testUpdateNotExist() throws DaoException, DuplicateException {
        Subject subject = new Subject();
        subject.setId(8000);
        subject.setName("updateSubject");

        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        assertEquals(0, subjectDao.update(subject));
    }

    @Test
    public void testUpdateOnNull() throws DaoException, DuplicateException {
        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        assertEquals(-1, subjectDao.update(null));
    }

    @Test
    public void testDelete() throws DaoException, DuplicateException {
        Subject subject = new Subject();
        subject.setName("testSubject");

        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        subjectDao.add(subject);
        assertTrue(subjectDao.delete(subject));
    }

    @Test
    public void testDeleteOnNull() throws DaoException {
        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        assertFalse(subjectDao.delete(null));
    }

    @Test
    public void testDeleteNotExist() throws DaoException {
        Subject subject = new Subject();
        subject.setId(10000);
        subject.setName("testSubject");
        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        assertFalse(subjectDao.delete(subject));
    }

    @Test
    public void testFindById() throws DaoException {
        Subject actual = new Subject();
        actual.setId(4);
        actual.setName("chemistry");

        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        Subject expected = subjectDao.findById(4).get();
        assertEquals(actual, expected);
    }

    @Test
    public void testFindByIdNotExist() throws DaoException {
        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        Optional<Subject> expected = subjectDao.findById(400);
        assertEquals(Optional.empty(), expected);
    }

    @Test
    public void testFindByIdWithNegativeId() throws DaoException {
        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        Optional<Subject> expected = subjectDao.findById(-1);
        assertEquals(Optional.empty(), expected);
    }

    @Test
    public void testFindAllByFacultyId() throws DaoException {
        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        List<Subject> expected = subjectDao.findAllByFacultyId(4);
        assertEquals(3, expected.size());
    }

    @Test
    public void testFindAllByFacultyIdWithNegativeId() throws DaoException {
        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        List<Subject> expected = subjectDao.findAllByFacultyId(-4);
        assertEquals(0, expected.size());
    }

    @Test
    public void testFindAllByFacultyIdNotExist() throws DaoException {
        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        List<Subject> expected = subjectDao.findAllByFacultyId(4000);
        assertEquals(0, expected.size());
    }

    @Test
    public void testFindAll() throws DaoException {
        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        List<Subject> expected = subjectDao.findAll();
        assertEquals(10, expected.size());
    }

    @Test
    public void testFindAllIdByFacultyId() throws DaoException {
        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        List<Integer> expected = subjectDao.findAllIdByFacultyId(4);
        assertEquals(3, expected.size());
    }

    @Test
    public void testFindIdAllByFacultyIdWithNegativeId() throws DaoException {
        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        List<Integer> expected = subjectDao.findAllIdByFacultyId(-4);
        assertEquals(0, expected.size());
    }

    @Test
    public void testFindAllIdByFacultyIdNotExist() throws DaoException {
        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        List<Integer> expected = subjectDao.findAllIdByFacultyId(4000);
        assertEquals(0, expected.size());
    }
}