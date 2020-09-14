package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.StudentDao;
import by.mishota.graduation.dao.factory.DaoFactory;
import by.mishota.graduation.entity.Gender;
import by.mishota.graduation.entity.Student;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class StudentDaoImplTest {
    @BeforeClass
    public void setTestDatabase() {
        DaoFactory.setPathDatabase("prop/databaseTest.properties");
    }

    @Test
    public void testFindAll() throws DaoException {
        StudentDao studentDao = DaoFactory.getInstance().getStudentDao();
        List<Student> expected = studentDao.findAll();
        assertEquals(19, expected.size());
    }

    @Test
    public void testFindAllByFacultyId() throws DaoException {
        StudentDao studentDao = DaoFactory.getInstance().getStudentDao();
        List<Student> expected = studentDao.findAllByFacultyId(5);
        assertEquals(2, expected.size());
    }

    @Test
    public void testFindAllByFacultyIdWithNegativeId() throws DaoException {
        StudentDao studentDao = DaoFactory.getInstance().getStudentDao();
        List<Student> expected = studentDao.findAllByFacultyId(-1);
        assertEquals(List.of(), expected);
    }

    @Test
    public void testFindAllByFacultyIdNotExist() throws DaoException {
        StudentDao studentDao = DaoFactory.getInstance().getStudentDao();
        List<Student> expected = studentDao.findAllByFacultyId(1200);
        assertEquals(List.of(), expected);
    }

    @Test
    public void testFindAllIdByFacultyId() throws DaoException {
        StudentDao studentDao = DaoFactory.getInstance().getStudentDao();
        List<Integer> expected = studentDao.findAllIdByFacultyId(5);
        assertEquals(2, expected.size());
    }

    @Test
    public void testFindAllIdByFacultyIdWithNegativeId() throws DaoException {
        StudentDao studentDao = DaoFactory.getInstance().getStudentDao();
        List<Integer> expected = studentDao.findAllIdByFacultyId(-5);
        assertEquals(0, expected.size());
    }

    @Test
    public void testFindAllIdByFacultyIdNotExist() throws DaoException {
        StudentDao studentDao = DaoFactory.getInstance().getStudentDao();
        List<Integer> expected = studentDao.findAllIdByFacultyId(800);
        assertEquals(0, expected.size());
    }

    @Test
    public void testFindAllFree() throws DaoException {
        StudentDao studentDao = DaoFactory.getInstance().getStudentDao();
        List<Student> expected = studentDao.findAllFree();
        assertEquals(10, expected.size());
    }

    @Test
    public void testFindAllPayer() throws DaoException {
        StudentDao studentDao = DaoFactory.getInstance().getStudentDao();
        List<Student> expected = studentDao.findAllPayer();
        assertEquals(9, expected.size());
    }

    @Test
    public void testFindAllFemale() throws DaoException {
        StudentDao studentDao = DaoFactory.getInstance().getStudentDao();
        List<Student> expected = studentDao.findAllFemale();
        assertEquals(9, expected.size());
    }

    @Test
    public void testFindAllMale() throws DaoException {
        StudentDao studentDao = DaoFactory.getInstance().getStudentDao();
        List<Student> expected = studentDao.findAllMale();
        assertEquals(10, expected.size());
    }

    @Test
    public void testFindById() throws DaoException {
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
        builder.setPhotos(List.of("dddd"));
        builder.setConfirmed(true);
        User user = builder.build();

        Student actual = new Student();
        actual.setId(4);
        actual.setUser(user);
        actual.setIdFaculty(4);
        actual.setBudget(true);
        StudentDao studentDao = DaoFactory.getInstance().getStudentDao();
        Optional<Student> expected = studentDao.findById(4);
        assertEquals(actual, expected.get());
    }

    @Test
    public void testFindByIdWithNegativeId() throws DaoException {
        StudentDao studentDao = DaoFactory.getInstance().getStudentDao();
        Optional<Student> expected = studentDao.findById(-5);
        assertEquals(Optional.empty(), expected);
    }

    @Test
    public void testFindByIdNotExist() throws DaoException {
        StudentDao studentDao = DaoFactory.getInstance().getStudentDao();
        Optional<Student> expected = studentDao.findById(4000);
        assertEquals(Optional.empty(), expected);
    }

    @Test
    public void testAdd() throws DaoException, DuplicateException {
        User.Builder builder = new User.Builder();
        builder.setId(20);
        builder.setPassportId("657");
        builder.setBirth(LocalDate.of(1999, 06, 11));
        builder.setLogin("malinovskaya");
        builder.setPassword("98586f8a59ed3e08b81e360e5991035");
        builder.setEmail("malinovskaya@gmail.com");
        builder.setFirstName("Екатерина");
        builder.setSurname("Малиновская");
        builder.setFatherName("Степановна");
        builder.setGender(Gender.FEMALE);
        builder.setConfirmed(true);
        User user = builder.build();

        Student actual = new Student();
        actual.setUser(user);
        actual.setIdFaculty(4);
        actual.setBudget(true);

        StudentDao studentDao = DaoFactory.getInstance().getStudentDao();
        Optional<Student> expected = studentDao.add(actual);
        assertEquals(actual, expected.get());

        studentDao.delete(actual);
    }

    @Test(expectedExceptions = DaoException.class, expectedExceptionsMessageRegExp = "duplicate")
    public void testAddDuplicate() throws DaoException, DuplicateException {
        User.Builder builder = new User.Builder();
        builder.setId(18);
        builder.setPassportId("655");
        builder.setBirth(LocalDate.of(1981, 07, 01));
        builder.setLogin("kryglenya");
        builder.setPassword("b8dddf165c27a5818706ffe5457fc36");
        builder.setEmail("kryglenya@gmail.com");
        builder.setFirstName("Светлана");
        builder.setSurname("Кругленя");
        builder.setFatherName("Николаевна");
        builder.setGender(Gender.FEMALE);
        builder.setConfirmed(true);
        User user = builder.build();

        Student actual = new Student();
        actual.setUser(user);
        actual.setIdFaculty(4);
        actual.setBudget(true);

        StudentDao studentDao = DaoFactory.getInstance().getStudentDao();
        studentDao.add(actual);
    }

    @Test(expectedExceptions = DaoException.class)
    public void testAddNotExist() throws DaoException, DuplicateException {
        User.Builder builder = new User.Builder();
        builder.setId(20);
        builder.setPassportId("657");
        builder.setBirth(LocalDate.of(1999, 06, 11));
        builder.setLogin("malinovskaya");
        builder.setPassword("98586f8a59ed3e08b81e360e5991035");
        builder.setEmail("malinovskaya@gmail.com");
        builder.setFirstName("Екатерина");
        builder.setSurname("Малиновская");
        builder.setFatherName("Степановна");
        builder.setGender(Gender.FEMALE);
        builder.setConfirmed(true);
        User user = builder.build();

        Student actual = new Student();
        actual.setUser(user);
        actual.setIdFaculty(50);
        actual.setBudget(true);

        StudentDao studentDao = DaoFactory.getInstance().getStudentDao();
        studentDao.add(actual);
    }

    @Test
    public void testAddOnNull() throws DaoException, DuplicateException {
        StudentDao studentDao = DaoFactory.getInstance().getStudentDao();
        Optional<Student> expected = studentDao.add(null);
        assertEquals(Optional.empty(), expected);
    }

    @Test
    public void testUpdate() throws DaoException {
        User.Builder builder = new User.Builder();
        builder.setId(18);
        builder.setPassportId("655");
        builder.setBirth(LocalDate.of(1981, 07, 01));
        builder.setLogin("kryglenya");
        builder.setPassword("b8dddf165c27a5818706ffe5457fc36");
        builder.setEmail("kryglenya@gmail.com");
        builder.setFirstName("Светлана");
        builder.setSurname("Кругленя");
        builder.setFatherName("Николаевна");
        builder.setGender(Gender.FEMALE);
        builder.setConfirmed(true);
        User user = builder.build();

        Student source = new Student();
        source.setUser(user);
        source.setId(18);
        source.setIdFaculty(5);
        source.setBudget(true);

        Student actual = new Student();
        actual.setUser(user);
        actual.setId(18);
        actual.setIdFaculty(9);
        actual.setBudget(false);

        StudentDao studentDao = DaoFactory.getInstance().getStudentDao();
        int expected = studentDao.update(actual);
        assertEquals(1, expected);

        studentDao.update(source);
    }

    @Test
    public void testUpdateNotExist() throws DaoException {
        User.Builder builder = new User.Builder();
        builder.setId(18);
        builder.setPassportId("655");
        builder.setBirth(LocalDate.of(1981, 07, 01));
        builder.setLogin("kryglenya");
        builder.setPassword("b8dddf165c27a5818706ffe5457fc36");
        builder.setEmail("kryglenya@gmail.com");
        builder.setFirstName("Светлана");
        builder.setSurname("Кругленя");
        builder.setFatherName("Николаевна");
        builder.setGender(Gender.FEMALE);
        builder.setConfirmed(true);
        User user = builder.build();

        Student actual = new Student();
        actual.setUser(user);
        actual.setId(1800);
        actual.setIdFaculty(9);
        actual.setBudget(false);

        StudentDao studentDao = DaoFactory.getInstance().getStudentDao();
        int expected = studentDao.update(actual);
        assertEquals(0, expected);
    }

    @Test
    public void testUpdateOnNull() throws DaoException {
        StudentDao studentDao = DaoFactory.getInstance().getStudentDao();
        int expected = studentDao.update(null);
        assertEquals(-1, expected);

    }

    @Test
    public void testDelete() throws DaoException, DuplicateException {
        User.Builder builder = new User.Builder();
        builder.setId(20);
        builder.setPassportId("657");
        builder.setBirth(LocalDate.of(1999, 06, 11));
        builder.setLogin("malinovskaya");
        builder.setPassword("98586f8a59ed3e08b81e360e5991035");
        builder.setEmail("malinovskaya@gmail.com");
        builder.setFirstName("Екатерина");
        builder.setSurname("Малиновская");
        builder.setFatherName("Степановна");
        builder.setGender(Gender.FEMALE);
        builder.setConfirmed(true);
        User user = builder.build();

        Student actual = new Student();
        actual.setUser(user);
        actual.setIdFaculty(4);
        actual.setBudget(true);

        StudentDao studentDao = DaoFactory.getInstance().getStudentDao();
        studentDao.add(actual);
        assertTrue(studentDao.delete(actual));
    }

    @Test()
    public void testDeleteNotExist() throws DaoException {
        User.Builder builder = new User.Builder();
        builder.setId(20);
        builder.setPassportId("657");
        builder.setBirth(LocalDate.of(1999, 06, 11));
        builder.setLogin("malinovskaya");
        builder.setPassword("98586f8a59ed3e08b81e360e5991035");
        builder.setEmail("malinovskaya@gmail.com");
        builder.setFirstName("Екатерина");
        builder.setSurname("Малиновская");
        builder.setFatherName("Степановна");
        builder.setGender(Gender.FEMALE);
        builder.setConfirmed(true);
        builder.setPhotos(List.of("dddd"));

        User user = builder.build();

        Student actual = new Student();
        actual.setId(4000);
        actual.setUser(user);
        actual.setIdFaculty(4);
        actual.setBudget(true);

        StudentDao studentDao = DaoFactory.getInstance().getStudentDao();
        assertFalse(studentDao.delete(actual));
    }

    @Test
    public void testDeleteOnNull() throws DaoException {
        StudentDao studentDao = DaoFactory.getInstance().getStudentDao();
        assertFalse(studentDao.delete(null));
    }

}