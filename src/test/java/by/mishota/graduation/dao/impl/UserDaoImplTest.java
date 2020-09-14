package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.UserDao;
import by.mishota.graduation.dao.factory.DaoFactory;
import by.mishota.graduation.entity.Gender;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class UserDaoImplTest {

    @BeforeClass
    public void setTestDatabase() {
        DaoFactory.setPathDatabase("prop/databaseTest.properties");
    }

    @AfterMethod(groups = "adding")
    public void deleteAddingRow() throws DaoException {
        DaoFactory.getInstance().getUserDao().deleteByLogin("mara");
    }

    @AfterMethod(groups = "updating")
    public void updateRow() throws DaoException, DuplicateException {
        User actualUser = createActualUser();
        DaoFactory.getInstance().getUserDao().update(actualUser);
    }

    @Test
    public void testFindCountByEmail() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        int expected = userDao.findCountByEmail("malinovskaya@gmail.com");
        int actual = 1;
        assertEquals(actual, expected);
    }

    @Test
    public void testFindCountByEmailOnNull() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        int expected = userDao.findCountByEmail(null);
        int actual = -1;
        assertEquals(actual, expected);
    }

    @Test
    public void testFindCountByEmailNotExist() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        int expected = userDao.findCountByEmail("solo@gmail.com");
        int actual = 0;
        assertEquals(actual, expected);
    }

    @Test
    public void testFindCountByLogin() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        int expected = userDao.findCountByLogin("makarevich");
        int actual = 1;
        assertEquals(actual, expected);
    }

    @Test
    public void testFindCountByLoginOnNull() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        int expected = userDao.findCountByLogin(null);
        int actual = -1;
        assertEquals(actual, expected);
    }

    @Test
    public void testFindCountByLoginNotExist() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        int expected = userDao.findCountByLogin("maka");
        int actual = 0;
        assertEquals(actual, expected);
    }

    @Test
    public void testFindCountByPassportId() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        int expected = userDao.findCountByPassportId("548");
        int actual = 1;
        assertEquals(actual, expected);
    }

    @Test
    public void testFindCountByPassportIdOnNull() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        int expected = userDao.findCountByPassportId(null);
        int actual = -1;
        assertEquals(actual, expected);
    }

    @Test
    public void testFindCountByPassportNotExist() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        int expected = userDao.findCountByPassportId("34431");
        int actual = 0;
        assertEquals(actual, expected);
    }

    @Test
    public void testFindAll() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        int expected = userDao.findAll().size();
        int actual = 20;
        assertEquals(actual, expected);
    }

    @Test
    public void testFindAllMales() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        int expected = userDao.findAllMales().size();
        int actual = 10;
        assertEquals(actual, expected);
    }

    @Test
    public void testFindAllFemales() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        int expected = userDao.findAllFemales().size();
        int actual = 10;
        assertEquals(actual, expected);
    }

    @Test
    public void testFindAllAdults() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        int expected = userDao.findAllAdults().size();
        int actual = 19;
        assertEquals(actual, expected);
    }

    @Test
    public void testFindById() throws DaoException {
        User actualUser = createActualUser();

        UserDao userDao = DaoFactory.getInstance().getUserDao();
        User expectedUser = userDao.findById(4).get();
        assertEquals(actualUser, expectedUser);
    }

    private User createActualUser() {
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
        return builder.build();
    }

    @Test
    public void testFindByIdNotExist() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        Optional<User> expectedUser = userDao.findById(400);
        assertEquals(Optional.empty(), expectedUser);
    }

    @Test
    public void testFindByLogin() throws DaoException {
        User actualUser = createActualUser();

        UserDao userDao = DaoFactory.getInstance().getUserDao();
        User expectedUser = userDao.findByLogin("makarevich").get();
        assertEquals(actualUser, expectedUser);
    }

    @Test
    public void testFindByLoginOnNull() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        Optional<User> expectedUser = userDao.findByLogin(null);
        assertEquals(Optional.empty(), expectedUser);
    }

    @Test
    public void testFindByLoginNotExist() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        Optional<User> expectedUser = userDao.findByLogin("asdfghjk");
        assertEquals(Optional.empty(), expectedUser);
    }

    @Test
    public void testFindByEmail() throws DaoException {
                User actualUser = createActualUser();

        UserDao userDao = DaoFactory.getInstance().getUserDao();
        User expectedUser = userDao.findByEmail("makarevich@gmail.com").get();
        assertEquals(actualUser, expectedUser);
    }

    @Test
    public void testFindByEmailOnNull() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        Optional<User> expectedUser = userDao.findByEmail(null);
        assertEquals(Optional.empty(), expectedUser);
    }

    @Test
    public void testFindByEmailNotExist() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        Optional<User> expectedUser = userDao.findByEmail("asdfgkjhgjfd@mail.ru");
        assertEquals(Optional.empty(), expectedUser);
    }

    @Test
    public void testFindByActivationCode() throws DaoException,DuplicateException {
        User actualUser = createActualUser();
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        actualUser.setConfirmed(false);
        actualUser.setActivationCode("asdgfhfsdasfghfd");
        userDao.update(actualUser);
        User expectedUser = userDao.findByActivationCode("asdgfhfsdasfghfd").get();
        assertEquals(actualUser, expectedUser);

        actualUser.setConfirmed(true);
        actualUser.setActivationCode(null);
        userDao.update(actualUser);
    }

    @Test
    public void testFindByActivationCodeOnNull() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        Optional<User> expectedUser = userDao.findByActivationCode(null);
        assertEquals(Optional.empty(), expectedUser);
    }

    @Test
    public void testFindByActivationCodeNotExist() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        Optional<User> expectedUser = userDao.findByActivationCode("asdfghjklf234r5t6y7u8ikmnhbgvfcds345ty");
        assertEquals(Optional.empty(), expectedUser);
    }

    @Test(groups = "adding")
    public void testAdd() throws DaoException, DuplicateException {
        User.Builder builder = new User.Builder();

        builder.setId(4);
        builder.setPassportId("50113");
        builder.setBirth(LocalDate.of(1971, 01, 06));
        builder.setLogin("mara");
        builder.setPassword("f66c993f65d6bcdc1e6e763cb6ea2aa");
        builder.setEmail("mara@gmail.com");
        builder.setFirstName("Андрей");
        builder.setSurname("Макаревич");
        builder.setFatherName("Вадимович");
        builder.setGender(Gender.MALE);
        builder.setConfirmed(true);
        User actualUser = builder.build();

        UserDao userDao = DaoFactory.getInstance().getUserDao();
        userDao.add(actualUser);
        User expectedUser = userDao.findById(actualUser.getId()).get();
        assertEquals(actualUser, expectedUser);
    }

    @Test()
    public void testAddOnNull() throws DaoException, DuplicateException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        Optional<User> expectedUser = userDao.add(null);
        assertEquals(Optional.empty(), expectedUser);
    }

    @Test(expectedExceptions = DuplicateException.class,
            groups = "adding")
    public void testAddDuplicateThrowsDaoException() throws DaoException, DuplicateException {
        User.Builder builder = new User.Builder();

        builder.setId(4);
        builder.setPassportId("50113");
        builder.setBirth(LocalDate.of(1971, 01, 06));
        builder.setLogin("mara");
        builder.setPassword("f66c993f65d6bcdc1e6e763cb6ea2aa");
        builder.setEmail("mara@gmail.com");
        builder.setFirstName("Андрей");
        builder.setSurname("Макаревич");
        builder.setFatherName("Вадимович");
        builder.setGender(Gender.MALE);
        builder.setConfirmed(true);
        User actualUser = builder.build();

        UserDao userDao = DaoFactory.getInstance().getUserDao();
        userDao.add(actualUser);
        userDao.add(actualUser);
    }

    @Test(groups = "updating")
    public void testUpdate() throws DaoException, DuplicateException {
        User.Builder builder = new User.Builder();

        builder.setId(4);
        builder.setPassportId("503");
        builder.setBirth(LocalDate.of(1971, 01, 06));
        builder.setLogin("makaka");
        builder.setPassword("f66c993f65d6bcdc1e6e763cb6ea2aa");
        builder.setEmail("makaka@gmail.com");
        builder.setFirstName("Андрей");
        builder.setSurname("Макаревич");
        builder.setFatherName("Вадимович");
        builder.setGender(Gender.MALE);
        builder.setActivationCode("asdgfhfsdasfghfd");
        builder.setPhotos(List.of("dddd"));
        builder.setConfirmed(false);
        User actualUser = builder.build();

        UserDao userDao = DaoFactory.getInstance().getUserDao();
        userDao.update(actualUser);
        User expectedUser = userDao.findById(actualUser.getId()).get();
        assertEquals(actualUser, expectedUser);
    }

    @Test()
    public void testUpdateOnNull() throws DaoException, DuplicateException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        int expectedUpdate = userDao.update(null);
        assertEquals(-1, expectedUpdate);
    }

    @Test(expectedExceptions = DaoException.class, expectedExceptionsMessageRegExp = "duplicate")
    public void testUpdateToDuplicate() throws DaoException, DuplicateException {
        User.Builder builder = new User.Builder();

        builder.setId(4);
        builder.setPassportId("530");
        builder.setBirth(LocalDate.of(1971, 01, 06));
        builder.setLogin("makaka");
        builder.setPassword("f66c993f65d6bcdc1e6e763cb6ea2aa");
        builder.setEmail("makaka@gmail.com");
        builder.setFirstName("Андрей");
        builder.setSurname("Макаревич");
        builder.setFatherName("Вадимович");
        builder.setGender(Gender.MALE);
        builder.setActivationCode("asdgfhfsdasfghfd");
        builder.setConfirmed(false);
        User actualUser = builder.build();

        UserDao userDao = DaoFactory.getInstance().getUserDao();
        userDao.update(actualUser);
        User expectedUser = userDao.findById(actualUser.getId()).get();
        assertEquals(actualUser, expectedUser);
    }

    @Test
    public void testDeleteById() throws DaoException, DuplicateException {
        User.Builder builder = new User.Builder();
        builder.setPassportId("50113");
        builder.setBirth(LocalDate.of(1971, 01, 06));
        builder.setLogin("mara");
        builder.setPassword("f66c993f65d6bcdc1e6e763cb6ea2aa");
        builder.setEmail("mara@gmail.com");
        builder.setFirstName("Андрей");
        builder.setSurname("Макаревич");
        builder.setFatherName("Вадимович");
        builder.setGender(Gender.MALE);
        builder.setConfirmed(true);
        User actualUser = builder.build();

        UserDao userDao = DaoFactory.getInstance().getUserDao();
        userDao.add(actualUser);
        assertTrue(userDao.deleteById(actualUser.getId()));
    }

    @Test
    public void testDeleteByIdWithNegativeId() throws DaoException, DuplicateException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        assertFalse(userDao.deleteById(-1));
    }

    @Test
    public void testDeleteByIdNotExist() throws DaoException, DuplicateException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        assertFalse(userDao.deleteById(1000));
    }

    @Test
    public void deleteByLogin() throws DaoException, DuplicateException {
        User.Builder builder = new User.Builder();
        builder.setPassportId("50113");
        builder.setBirth(LocalDate.of(1971, 01, 06));
        builder.setLogin("mara");
        builder.setPassword("f66c993f65d6bcdc1e6e763cb6ea2aa");
        builder.setEmail("mara@gmail.com");
        builder.setFirstName("Андрей");
        builder.setSurname("Макаревич");
        builder.setFatherName("Вадимович");
        builder.setGender(Gender.MALE);
        builder.setConfirmed(true);
        User actualUser = builder.build();

        UserDao userDao = DaoFactory.getInstance().getUserDao();
        userDao.add(actualUser);
        assertTrue(userDao.deleteByLogin(actualUser.getLogin()));
    }
}