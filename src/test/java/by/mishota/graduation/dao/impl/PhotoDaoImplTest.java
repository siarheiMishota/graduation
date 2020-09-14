package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.PhotoDao;
import by.mishota.graduation.dao.factory.DaoFactory;
import by.mishota.graduation.entity.Gender;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class PhotoDaoImplTest {

    @BeforeClass
    public void setTestDatabase() {
        DaoFactory.setPathDatabase("prop/databaseTest.properties");
    }

    @Test
    public void testAdd() throws DaoException, DuplicateException {
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        User user = createActualUser();
        assertTrue(photoDao.add(user, "qwerthgfds"));
        photoDao.delete("qwerthgfds");
    }

    @Test
    public void testAddOnNull() throws DaoException, DuplicateException {
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        User user = createActualUser();
        assertFalse(photoDao.add(user, null));
    }

    @Test(expectedExceptions = DaoException.class, expectedExceptionsMessageRegExp = "duplicate")
    public void testAddOnDuplicate() throws DaoException, DuplicateException {
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        User user = createActualUser();
        photoDao.add(user, "aaaa");
    }

    @Test
    public void testAddOnUserNull() throws DaoException, DuplicateException {
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        assertFalse(photoDao.add(null, "aaaaaaa"));
    }

    @Test
    public void testAddAll() throws DaoException, DuplicateException {
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        User user = createActualUser();
        assertTrue(photoDao.addAll(user, List.of("1111111", "2222222", "333333")));
        photoDao.deleteSome(List.of("1111111", "2222222", "333333"));
    }

    @Test(expectedExceptions = DaoException.class, expectedExceptionsMessageRegExp = "duplicate")
    public void testAddAllOnDuplicate() throws DaoException, DuplicateException {
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        User user = createActualUser();
        assertTrue(photoDao.addAll(user, List.of("aaaa", "2222222", "333333")));
    }

    @Test
    public void testAddAllOnNull() throws DaoException, DuplicateException {
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        User user = createActualUser();
        assertFalse(photoDao.addAll(user, null));
    }

    @Test
    public void testAddAllOnEmptyList() throws DaoException, DuplicateException {
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        User user = createActualUser();
        assertFalse(photoDao.addAll(user, List.of()));
    }

    @Test
    public void testUpdate() throws DaoException, DuplicateException {
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        int actual = 1;
        int updating = photoDao.update(3, "updating");
        assertEquals(actual, updating);
        photoDao.update(3, "cccc");
    }

    @Test
    public void testUpdateOnNull() throws DaoException, DuplicateException {
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        int actual = -1;
        int updating = photoDao.update(3, null);
        assertEquals(actual, updating);
    }

    @Test
    public void testUpdateWithNegativeUserId() throws DaoException, DuplicateException {
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        int actual = -1;
        int updating = photoDao.update(-1, "null");
        assertEquals(actual, updating);
    }

    @Test(expectedExceptions = DaoException.class, expectedExceptionsMessageRegExp = "duplicate")
    public void testUpdateOnDuplicate() throws DaoException, DuplicateException {
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        photoDao.update(4, "aaaa");
    }

    @Test
    public void testDelete() throws DaoException, DuplicateException {
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        User user = createActualUser();
        photoDao.add(user, "qwerthgfds");
        assertTrue(photoDao.delete("qwerthgfds"));
    }

    @Test
    public void testDeleteOnNull() throws DaoException {
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        assertFalse(photoDao.delete(null));
    }

    @Test
    public void testAddNotExist() throws DaoException {
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        assertFalse(photoDao.delete("aaaaaaaaaa"));
    }

    @Test
    public void testDeleteSome() throws DaoException, DuplicateException {
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        User user = createActualUser();
        photoDao.addAll(user, List.of("1111111", "2222222", "333333"));
        assertTrue(photoDao.deleteSome(List.of("1111111", "2222222", "333333")));
    }

    @Test
    public void testDeleteSomeOnNull() throws DaoException {
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        assertFalse(photoDao.deleteSome(null));
    }

    @Test
    public void testDeleteSomeNotExist() throws DaoException {
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        assertFalse(photoDao.deleteSome(List.of("1111111", "2222222", "333333")));
    }

    @Test
    public void testFindAll() throws DaoException, DuplicateException {
        List<String> actual = List.of("aaaa", "bbbb", "cccc", "dddd", "eeee", "ffff", "gggg", "hhhh", "qqqq", "wwww",
                "rrrr", "tttt", "yyyy", "uuuu", "iiii", "oooo");
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        List<String> expected = photoDao.findAll();
        assertEquals(actual, expected);
    }

    @Test
    public void testFindAllByUserId() throws DaoException {
        List<String> actual = List.of("cccc");
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        List<String> expected = photoDao.findAllByUserId(3);
        assertEquals(actual, expected);
    }

    @Test
    public void testFindAllByUserIdWithNegativeId() throws DaoException {
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        List<String> expected = photoDao.findAllByUserId(-1);
        assertEquals(List.of(), expected);
    }

    @Test
    public void testFindAllByUserIdNotExist() throws DaoException {
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        List<String> expected = photoDao.findAllByUserId(1000);
        assertEquals(List.of(), expected);
    }

    @Test
    public void testFindIdByName() throws DaoException {
        Integer actual = 2;
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        Optional<Integer> expected = photoDao.findIdByName("bbbb");
        assertEquals(actual, expected.get());
    }

    @Test
    public void testFindIdByNameOnNull() throws DaoException {
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        Optional<Integer> expected = photoDao.findIdByName(null);
        assertEquals(Optional.empty(), expected);
    }

    @Test
    public void testFindIdByNameNotExist() throws DaoException {
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        Optional<Integer> expected = photoDao.findIdByName("dsfsghjgfdsfghn");
        assertEquals(Optional.empty(), expected);
    }

    @Test
    public void testFindById() throws DaoException {
        String actual = "bbbb";
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        Optional<String> expected = photoDao.findById(2);
        assertEquals(actual, expected.get());
    }

    @Test
    public void testFindByWithNotExist() throws DaoException {
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        Optional<String> expected = photoDao.findById(2000);
        assertEquals(Optional.empty(), expected);
    }

    @Test
    public void testFindByWithNegativeId() throws DaoException {
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        Optional<String> expected = photoDao.findById(-1);
        assertEquals(Optional.empty(), expected);
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
        builder.setActivationCode("asdgfhfsdasfghfd");
        builder.setConfirmed(false);
        return builder.build();
    }

}