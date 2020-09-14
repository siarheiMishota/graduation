package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.EntrantDao;
import by.mishota.graduation.dao.UserDao;
import by.mishota.graduation.dao.factory.DaoFactory;
import by.mishota.graduation.entity.*;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class EntrantDaoImplTest {

    @BeforeClass
    public void setTestDatabase() {
        DaoFactory.setPathDatabase("prop/databaseTest.properties");
    }

    @Test
    public void testFindById() throws DaoException {
        Entrant entrant = createActualEntrant();
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        Optional<Entrant> expected = entrantDao.findById(4);
        assertEquals(entrant, expected.get());
    }
    @Test
    public void testFindByIdNotExist() throws DaoException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        Optional<Entrant> expected = entrantDao.findById(40000);
        assertEquals(Optional.empty(), expected);
    }

    @Test
    public void testFindByIdWithNegativeId() throws DaoException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        Optional<Entrant> expected = entrantDao.findById(-1);
        assertEquals(Optional.empty(), expected);
    }

    @Test
    public void testFindFewSkippingFew() throws DaoException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        List<Entrant> expected = entrantDao.findFewSkippingFew(4,2);
        assertEquals(4, expected.size());
    }

    @Test
    public void testFindByUserId() throws DaoException {
        Entrant entrant = createActualEntrant();
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        Optional<Entrant> expected = entrantDao.findByUserId(4);
        assertEquals(entrant, expected.get());
    }

    @Test
    public void testFindByUserIdNotExist() throws DaoException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        Optional<Entrant> expected = entrantDao.findByUserId(400);
        assertEquals(Optional.empty(), expected);
    }

    @Test
    public void testFindByUserIdWithNegativeId() throws DaoException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        Optional<Entrant> expected = entrantDao.findByUserId(-1);
        assertEquals(Optional.empty(), expected);
    }

    @Test
    public void testFindAll() throws DaoException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        List<Entrant> expected = entrantDao.findAll();
        assertEquals(19, expected.size());
    }

    @Test
    public void testAdd() throws DaoException, DuplicateException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        User user = userDao.findById(20).get();

        Entrant entrant = new Entrant();
        entrant.setUser(user);
        entrant.setCertificate(40);

        assertTrue(entrantDao.add(entrant).isPresent());

        entrantDao.delete(entrant.getId());
    }

    @Test
    public void testAddOnNull() throws DaoException, DuplicateException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        Optional<Entrant> expected = entrantDao.add(null);
        assertEquals(Optional.empty(), expected);
    }

    @Test(expectedExceptions = DaoException.class, expectedExceptionsMessageRegExp = "duplicate")
    public void testAddOnDuplicate() throws DaoException, DuplicateException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        User user = userDao.findById(19).get();

        Entrant entrant = new Entrant();
        entrant.setUser(user);
        entrant.setCertificate(40);
        entrantDao.add(entrant).isPresent();
    }

    @Test
    public void testAddResult() throws DaoException, DuplicateException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        Entrant entrant = createNewEntrant();
        userDao.add(entrant.getUser());
        entrantDao.add(entrant);

        SubjectResult subjectResult = new SubjectResult();
        subjectResult.setSubjectId(4);
        subjectResult.setPoints(29);

        entrant.addResult(subjectResult);
        Optional<Entrant> expected = entrantDao.addResult(entrant);
        assertEquals(3, expected.get().getResults().size());

        DaoFactory.getInstance().getUserDao().deleteById(entrant.getUser().getId());
    }

    @Test(expectedExceptions = DaoException.class, expectedExceptionsMessageRegExp = "duplicate", enabled = false)
    public void testAddResultDuplicate() throws DaoException, DuplicateException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        SubjectResult subjectResult = new SubjectResult();
        subjectResult.setSubjectId(3);
        subjectResult.setPoints(29);

        Entrant entrant = createActualEntrant();
        entrant.addResult(subjectResult);

        entrantDao.addResult(entrant);
    }

    @Test
    public void testAddResultOnNull() throws DaoException, DuplicateException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        Optional<Entrant> expected = entrantDao.addResult(null);
        assertEquals(Optional.empty(), expected);
    }

    @Test
    public void testAddPriorities() throws DaoException, DuplicateException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        FacultyPriority facultyPriority = new FacultyPriority();
        facultyPriority.setFacultyId(7);
        facultyPriority.setPriority(6);
        Entrant entrant = createActualEntrant();
        entrant.addFacultyPriority(facultyPriority);

        entrantDao.addPriorities(entrant);
        assertEquals(7, entrant.getPriorities().size());

        DaoFactory.getInstance().getFacultyPriorityDao().delete(facultyPriority);
    }

    @Test(expectedExceptions = DaoException.class, expectedExceptionsMessageRegExp = "duplicate")
    public void testAddPrioritiesDuplicate() throws DaoException, DuplicateException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        FacultyPriority facultyPriority = new FacultyPriority();
        facultyPriority.setFacultyId(6);
        facultyPriority.setPriority(5);
        Entrant entrant = createActualEntrant();
        entrant.addFacultyPriority(facultyPriority);
        entrantDao.addPriorities(entrant);
    }

    @Test
    public void testAddPrioritiesOnNull() throws DaoException, DuplicateException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        Optional<Entrant> expected = entrantDao.addPriorities(null);
        assertEquals(Optional.empty(), expected);
    }

    @Test
    public void testDeleteResult() throws DaoException, DuplicateException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        Entrant entrant = createNewEntrant();
        userDao.add(entrant.getUser());
        entrantDao.add(entrant);

        SubjectResult subjectResult = new SubjectResult();
        subjectResult.setSubjectId(4);
        subjectResult.setPoints(29);

        entrant.addResult(subjectResult);
        entrantDao.addResult(entrant);
        boolean expected = entrantDao.deleteResult(entrant, subjectResult);
        assertTrue(expected);
        userDao.deleteById(entrant.getUser().getId());
    }

    @Test
    public void testDeleteResultOnNull() throws DaoException, DuplicateException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        Entrant entrant = createActualEntrant();
        assertFalse(entrantDao.deleteResult(entrant, null));
    }

    @Test
    public void testDeleteResultNotExist() throws DaoException, DuplicateException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        Entrant entrant = createActualEntrant();
        SubjectResult subjectResult = new SubjectResult(10, 10);
        subjectResult.setId(500);
        assertFalse(entrantDao.deleteResult(entrant, subjectResult));
    }

    @Test
    public void testDeleteResults() throws DaoException, DuplicateException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        Entrant entrant = createNewEntrant();
        userDao.add(entrant.getUser());
        entrantDao.add(entrant);

        SubjectResult subjectResult1 = new SubjectResult();
        subjectResult1.setSubjectId(4);
        subjectResult1.setPoints(29);
        SubjectResult subjectResult2 = new SubjectResult();
        subjectResult2.setSubjectId(5);
        subjectResult2.setPoints(29);

        entrant.addResult(subjectResult1);
        entrant.addResult(subjectResult2);
        entrantDao.addResult(entrant);
        boolean expected = entrantDao.deleteResults(entrant, List.of(subjectResult1, subjectResult2));
        assertTrue(expected);
        userDao.deleteById(entrant.getUser().getId());
    }

    @Test
    public void testDeleteResultsOnNull() throws DaoException, DuplicateException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        Entrant entrant = createActualEntrant();
        assertFalse(entrantDao.deleteResults(entrant, null));
    }

    @Test
    public void testDeletePriority() throws DaoException, DuplicateException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        Entrant entrant = createNewEntrant();
        userDao.add(entrant.getUser());
        entrantDao.add(entrant);

        FacultyPriority facultyPriority = new FacultyPriority();
        facultyPriority.setFacultyId(7);
        facultyPriority.setPriority(7);
        entrant.addFacultyPriority(facultyPriority);

        entrantDao.addPriorities(entrant);
        assertTrue(entrantDao.deletePriority(entrant, facultyPriority));

        userDao.deleteById(entrant.getUser().getId());
    }

    @Test
    public void testDeletePriorityNotExist() throws DaoException, DuplicateException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        Entrant entrant = createActualEntrant();

        FacultyPriority facultyPriority = new FacultyPriority();
        facultyPriority.setId(50000);
        facultyPriority.setFacultyId(7);
        facultyPriority.setPriority(7);

        assertFalse(entrantDao.deletePriority(entrant, facultyPriority));
    }

    @Test
    public void testDeletePriorityOnNull() throws DaoException, DuplicateException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        Entrant entrant = createActualEntrant();

        assertFalse(entrantDao.deletePriority(entrant, null));
    }

    @Test
    public void testDeletePriorities() throws DaoException, DuplicateException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        Entrant entrant = createNewEntrant();
        userDao.add(entrant.getUser());
        entrantDao.add(entrant);

        FacultyPriority facultyPriority1 = new FacultyPriority();
        facultyPriority1.setFacultyId(7);
        facultyPriority1.setPriority(7);
        FacultyPriority facultyPriority2 = new FacultyPriority();
        facultyPriority2.setFacultyId(8);
        facultyPriority2.setPriority(8);
        entrant.addFacultyPriority(facultyPriority1);
        entrant.addFacultyPriority(facultyPriority2);

        entrantDao.addPriorities(entrant);
        assertTrue(entrantDao.deletePriorities(entrant, List.of(facultyPriority1, facultyPriority2)));

        userDao.deleteById(entrant.getUser().getId());
    }

    @Test
    public void testDeletePrioritiesOnNull() throws DaoException, DuplicateException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        Entrant entrant = createActualEntrant();
        assertFalse(entrantDao.deletePriorities(entrant, null));
    }


    @Test
    public void testUpdate() throws DaoException, DuplicateException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        Entrant entrant = createActualEntrant();
        entrant.setCertificate(23);

        assertEquals(1, entrantDao.update(entrant));

        entrantDao.update(createActualEntrant());
    }

    @Test(expectedExceptions = DaoException.class, expectedExceptionsMessageRegExp = "duplicate")
    public void testUpdateDuplicate() throws DaoException, DuplicateException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        Entrant entrant = createActualEntrant();
        entrant.getPriorities().get(0).setFacultyId(3);

        entrantDao.update(entrant);
    }

    @Test
    public void testUpdateOnNull() throws DaoException, DuplicateException {
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        assertEquals(-1, entrantDao.update(null));
    }

    private Entrant createActualEntrant() {
        User.Builder builder = new User.Builder();
        builder.setId(4);
        builder.setPassportId("503");
        builder.setBirth(LocalDate.of(1971, 01, 06));
        builder.setLogin("makarevich");
        builder.setPassword("f66c993f65d6bcdc1e6e763cb6ea2aa");
        builder.setEmail("makarevich@gmail.com");
        builder.setFirstName("Андрей");
        builder.setSurname("Макаревич");
        builder.setFatherName("Вадимович");
        builder.setGender(Gender.MALE);
        builder.setConfirmed(true);
        builder.setPhotos(List.of("dddd"));
        User user = builder.build();

        FacultyPriority facultyPriority1 = new FacultyPriority(1, 1);
        FacultyPriority facultyPriority2 = new FacultyPriority(3, 2);
        FacultyPriority facultyPriority3 = new FacultyPriority(4, 3);
        FacultyPriority facultyPriority4 = new FacultyPriority(5, 4);
        FacultyPriority facultyPriority5 = new FacultyPriority(6, 5);
        FacultyPriority facultyPriority6 = new FacultyPriority(12, 6);
        facultyPriority1.setId(15);
        facultyPriority2.setId(16);
        facultyPriority3.setId(17);
        facultyPriority4.setId(18);
        facultyPriority5.setId(19);
        facultyPriority6.setId(20);
        List<FacultyPriority> facultyPriorities = new ArrayList<>();
        facultyPriorities.add(facultyPriority1);
        facultyPriorities.add(facultyPriority2);
        facultyPriorities.add(facultyPriority3);
        facultyPriorities.add(facultyPriority4);
        facultyPriorities.add(facultyPriority5);
        facultyPriorities.add(facultyPriority6);

        SubjectResult subjectResult1 = new SubjectResult(1, 19);
        SubjectResult subjectResult2 = new SubjectResult(2, 43);
        SubjectResult subjectResult3 = new SubjectResult(3, 50);
        subjectResult1.setId(10);
        subjectResult2.setId(11);
        subjectResult3.setId(12);
        List<SubjectResult> subjectResults = new ArrayList<>();
        subjectResults.add(subjectResult1);
        subjectResults.add(subjectResult2);
        subjectResults.add(subjectResult3);

        Entrant entrant = new Entrant();
        entrant.setId(4);
        entrant.setUser(user);
        entrant.setCertificate(40);
        entrant.setPriorities(facultyPriorities);
        entrant.setDate(LocalDateTime.of(2020,9,12, 14,03,25));
        entrant.setResults(subjectResults);
        return entrant;
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
        entrant.setDate(LocalDateTime.of(2020,9,12, 12,58,49));
        entrant.setPriorities(facultyPriorities);
        entrant.setResults(subjectResults);
        return entrant;
    }

}