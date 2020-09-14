package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.EntrantDao;
import by.mishota.graduation.dao.FacultyPriorityDao;
import by.mishota.graduation.dao.UserDao;
import by.mishota.graduation.dao.factory.DaoFactory;
import by.mishota.graduation.entity.*;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class FacultyPriorityDaoImplTest {

    @BeforeClass
    public void setTestDatabase() {
        DaoFactory.setPathDatabase("prop/databaseTest.properties");
    }

    @Test
    public void testDelete() throws DaoException, DuplicateException {
        FacultyPriority actual = new FacultyPriority();
        actual.setPriority(2);
        actual.setFacultyId(1);

        FacultyPriorityDao facultyPriorityDao = DaoFactory.getInstance().getFacultyPriorityDao();
        facultyPriorityDao.add(actual, 19);
        assertTrue(facultyPriorityDao.delete(actual));
    }

    @Test
    public void testDeleteAllByEntrant() throws DaoException, DuplicateException {
        FacultyPriority actual = new FacultyPriority();
        actual.setPriority(1);
        actual.setFacultyId(10);

        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        UserDao userDao=DaoFactory.getInstance().getUserDao();
        FacultyPriorityDao facultyPriorityDao = DaoFactory.getInstance().getFacultyPriorityDao();

        Entrant entrant = createNewEntrant();
        User user = userDao.add(entrant.getUser()).get();
        entrantDao.add(entrant);
        facultyPriorityDao.add(actual, entrant.getId());
        assertTrue(facultyPriorityDao.deleteAllByEntrant(entrant.getId()));

        userDao.deleteById(user.getId());
    }

    @Test
    public void testAdd() throws DaoException, DuplicateException {
        FacultyPriority actual = new FacultyPriority();
        actual.setPriority(2);
        actual.setFacultyId(1);

        FacultyPriorityDao facultyPriorityDao = DaoFactory.getInstance().getFacultyPriorityDao();
        facultyPriorityDao.add(actual, 19);
        Optional<FacultyPriority> expected = facultyPriorityDao.findById(actual.getId());
        assertEquals(actual, expected.get());

        facultyPriorityDao.delete(expected.get());
    }

    @Test
    public void testAddOnNull() throws DaoException, DuplicateException {
        FacultyPriorityDao facultyPriorityDao = DaoFactory.getInstance().getFacultyPriorityDao();
        Optional<FacultyPriority> expected = facultyPriorityDao.add(null, 19);
        assertEquals(Optional.empty(), expected);
    }

    @Test(expectedExceptions = DaoException.class,expectedExceptionsMessageRegExp = "duplicate")
    public void testAddDuplicate() throws DaoException, DuplicateException {
        FacultyPriority actual = new FacultyPriority();
        actual.setPriority(1);
        actual.setFacultyId(10);
        FacultyPriorityDao facultyPriorityDao = DaoFactory.getInstance().getFacultyPriorityDao();
        Optional<FacultyPriority> expected = facultyPriorityDao.add(actual, 19);
        assertEquals(Optional.empty(), expected);
    }

    @Test(expectedExceptions = DaoException.class)
    public void testAddNotExistEntrant() throws DaoException, DuplicateException {
        FacultyPriority actual = new FacultyPriority();
        actual.setPriority(2);
        actual.setFacultyId(10);

        FacultyPriorityDao facultyPriorityDao = DaoFactory.getInstance().getFacultyPriorityDao();
        Optional<FacultyPriority> expected = facultyPriorityDao.add(actual, 200);
        assertEquals(Optional.empty(),expected);
    }

    @Test
    public void testAddAll() throws DaoException, DuplicateException {
        FacultyPriority actual1 = new FacultyPriority();
        FacultyPriority actual2 = new FacultyPriority();
        FacultyPriority actual3 = new FacultyPriority();
        actual1.setPriority(2);
        actual2.setPriority(3);
        actual3.setPriority(4);
        actual1.setFacultyId(7);
        actual2.setFacultyId(8);
        actual3.setFacultyId(9);

        FacultyPriorityDao facultyPriorityDao = DaoFactory.getInstance().getFacultyPriorityDao();
        List<FacultyPriority> delete = facultyPriorityDao.addAll(List.of(actual1, actual2, actual3), 19);
        List<FacultyPriority> expected = facultyPriorityDao.findAllByEntrantId(19);
        int actual = 4;
        assertEquals(actual, expected.size());

        for (FacultyPriority facultyPriority : delete) {
            facultyPriorityDao.delete(facultyPriority);
        }
    }

    @Test
    public void testAddAllOnNull() throws DaoException, DuplicateException {
        FacultyPriorityDao facultyPriorityDao = DaoFactory.getInstance().getFacultyPriorityDao();
        List<FacultyPriority> expected = facultyPriorityDao.addAll(null, 20);
        assertEquals(List.of(),expected);
    }

    @Test(expectedExceptions = DaoException.class, expectedExceptionsMessageRegExp = "Error getting id of the faculty priority")
    public void testAddAllNotExist() throws DaoException, DuplicateException {
        FacultyPriority actual1 = new FacultyPriority();
        FacultyPriority actual2 = new FacultyPriority();
        FacultyPriority actual3 = new FacultyPriority();
        actual1.setPriority(2);
        actual2.setPriority(3);
        actual3.setPriority(4);
        actual1.setFacultyId(7);
        actual2.setFacultyId(8);
        actual3.setFacultyId(9);

        FacultyPriorityDao facultyPriorityDao = DaoFactory.getInstance().getFacultyPriorityDao();
        facultyPriorityDao.addAll(List.of(actual1,actual2,actual3), 200);
    }

    @Test
    public void testUpdate() throws DaoException, DuplicateException {

        FacultyPriority actual = new FacultyPriority();
        actual.setId(62);
        actual.setPriority(2);
        actual.setFacultyId(7);

        FacultyPriority source = new FacultyPriority();
        source.setId(62);
        source.setPriority(1);
        source.setFacultyId(10);

        FacultyPriorityDao facultyPriorityDao = DaoFactory.getInstance().getFacultyPriorityDao();
        facultyPriorityDao.update(actual);
        Optional<FacultyPriority> expected = facultyPriorityDao.findById(actual.getId());
        assertEquals(actual, expected.get());

        facultyPriorityDao.update(source);
    }

    @Test
    public void testUpdateNotExist() throws DaoException, DuplicateException {
        FacultyPriority actual = new FacultyPriority();
        actual.setId(69);
        actual.setPriority(2);
        actual.setFacultyId(7);

        FacultyPriorityDao facultyPriorityDao = DaoFactory.getInstance().getFacultyPriorityDao();
        facultyPriorityDao.update(actual);
        Optional<FacultyPriority> expected = facultyPriorityDao.findById(actual.getId());
        assertEquals(Optional.empty(), expected);
    }

    @Test
    public void testUpdateOnNull() throws DaoException, DuplicateException {
        FacultyPriorityDao facultyPriorityDao = DaoFactory.getInstance().getFacultyPriorityDao();
        int update = facultyPriorityDao.update(null);
        assertEquals(-1,update);
    }

    @Test
    public void testFindAll() throws DaoException {
        int actual = 62;
        FacultyPriorityDao facultyPriorityDao = DaoFactory.getInstance().getFacultyPriorityDao();
        List<FacultyPriority> expectedFacultyPriorities = facultyPriorityDao.findAll();
        assertEquals(actual, expectedFacultyPriorities.size());
    }

    @Test
    public void testFindByEntrantId() throws DaoException {
        int actual = 6;
        FacultyPriorityDao facultyPriorityDao = DaoFactory.getInstance().getFacultyPriorityDao();
        List<FacultyPriority> expectedFacultyPriorities = facultyPriorityDao.findAllByEntrantId(5);
        assertEquals(actual, expectedFacultyPriorities.size());
    }

    @Test
    public void testFindByEntrantIdNotExist() throws DaoException {
        int actual = 0;
        FacultyPriorityDao facultyPriorityDao = DaoFactory.getInstance().getFacultyPriorityDao();
        List<FacultyPriority> expectedFacultyPriorities = facultyPriorityDao.findAllByEntrantId(500);
        assertEquals(actual, expectedFacultyPriorities.size());
    }

    @Test
    public void testFindByFacultyId() throws DaoException {
        int actual = 2;
        FacultyPriorityDao facultyPriorityDao = DaoFactory.getInstance().getFacultyPriorityDao();
        List<FacultyPriority> expectedFacultyPriorities = facultyPriorityDao.findByFacultyId(10);
        assertEquals(actual, expectedFacultyPriorities.size());
    }

    @Test
    public void testFindByFacultyIdNotExist() throws DaoException {
        FacultyPriorityDao facultyPriorityDao = DaoFactory.getInstance().getFacultyPriorityDao();
        List<FacultyPriority> expectedFacultyPriorities = facultyPriorityDao.findByFacultyId(100);
        assertEquals(List.of(), expectedFacultyPriorities);
    }

    @Test
    public void testFindById() throws DaoException {

        FacultyPriority actual = new FacultyPriority();
        actual.setId(10);
        actual.setFacultyId(5);
        actual.setPriority(4);
        FacultyPriorityDao facultyPriorityDao = DaoFactory.getInstance().getFacultyPriorityDao();
        Optional<FacultyPriority> expectedFacultyPriority = facultyPriorityDao.findById(10);
        assertEquals(actual, expectedFacultyPriority.get());
    }

    @Test
    public void testFindByIdNotExist() throws DaoException {
        FacultyPriority actual = new FacultyPriority();
        actual.setId(10);
        actual.setFacultyId(5);
        actual.setPriority(4);
        FacultyPriorityDao facultyPriorityDao = DaoFactory.getInstance().getFacultyPriorityDao();
        Optional<FacultyPriority> expectedFacultyPriority = facultyPriorityDao.findById(1000);
        assertEquals(Optional.empty(), expectedFacultyPriority);
    }

    private Entrant createNewEntrant() {
        User.Builder builder = new User.Builder();
        builder.setPassportId("66699");
        builder.setBirth(LocalDate.of(1971, 01, 06));
        builder.setLogin("ocipov");
        builder.setPassword("f66c993f65d6bcdc1e6e763cb6ea2aa");
        builder.setEmail("ocipov@gmail.com");
        builder.setFirstName("Андрей");
        builder.setSurname("Осипов");
        builder.setFatherName("Вадимович");
        builder.setGender(Gender.MALE);
        builder.setActivationCode("asdgfhfsdasfghfd");
        builder.setConfirmed(false);
        User user = builder.build();

        FacultyPriority facultyPriority1 = new FacultyPriority(1, 1);
        FacultyPriority facultyPriority2 = new FacultyPriority(3, 2);
        FacultyPriority facultyPriority3 = new FacultyPriority(4, 3);
        FacultyPriority facultyPriority4 = new FacultyPriority(5, 4);
        FacultyPriority facultyPriority5 = new FacultyPriority(6, 5);
        FacultyPriority facultyPriority6 = new FacultyPriority(12, 6);
        List<FacultyPriority> facultyPriorities = new ArrayList<>();
        facultyPriorities.add(facultyPriority1);
        facultyPriorities.add(facultyPriority2);
        facultyPriorities.add(facultyPriority3);
        facultyPriorities.add(facultyPriority4);
        facultyPriorities.add(facultyPriority5);
        facultyPriorities.add(facultyPriority6);

        SubjectResult subjectResult1 = new SubjectResult(1, 19);
        SubjectResult subjectResult2 = new SubjectResult(2, 43);
        List<SubjectResult> subjectResults = new ArrayList<>();
        subjectResults.add(subjectResult1);
        subjectResults.add(subjectResult2);

        Entrant entrant = new Entrant();
        entrant.setUser(user);
        entrant.setCertificate(99);
        entrant.setPriorities(facultyPriorities);
        entrant.setResults(subjectResults);
        return entrant;
    }
}