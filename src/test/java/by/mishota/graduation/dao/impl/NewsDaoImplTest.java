package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.NewsDao;
import by.mishota.graduation.dao.factory.DaoFactory;
import by.mishota.graduation.entity.News;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class NewsDaoImplTest {

    @BeforeClass
    public void setTestDatabase() {
        DaoFactory.setPathDatabase("prop/databaseTest.properties");
    }

    @Test
    public void testAddAll() throws DaoException, DuplicateException {
        NewsDao newsDao = DaoFactory.getInstance().getNewsDao();
        News news1 = new News(4, "name_new_ru_1", "name_new_en_1", "brief_description_new_en_1", "brief_description_new_ru_1", "english_variable_new_1", "russian_variable_new_1", LocalDateTime.of(2020, 8, 04, 13, 19, 17));
        News news2 = new News(5, "name_new_ru_2", "name_new_en_2", "brief_description_new_en_2", "brief_description_new_ru_2", "english_variable_new_2", "russian_variable_new_2", LocalDateTime.of(2020, 8, 04, 13, 19, 17));
        News news3 = new News(6, "name_new_ru_3", "name_new_en_3", "brief_description_new_en_3", "brief_description_new_ru_3", "english_variable_new_3", "russian_variable_new_3", LocalDateTime.of(2020, 8, 04, 13, 19, 17));
        List<News> allNews = List.of(news1, news2, news3);
        assertTrue(newsDao.addAll(allNews));

        for (News news : allNews) {
            newsDao.delete(news.getId());
        }
    }

    @Test
    public void testAddAllOnNull() throws DaoException, DuplicateException {
        NewsDao newsDao = DaoFactory.getInstance().getNewsDao();
        assertFalse(newsDao.addAll(null));
    }

    @Test
    public void testAddAllListEmpty() throws DaoException, DuplicateException {
        NewsDao newsDao = DaoFactory.getInstance().getNewsDao();
        assertFalse(newsDao.addAll(List.of()));
    }

    @Test
    public void testAdd() throws DaoException, DuplicateException {
        NewsDao newsDao = DaoFactory.getInstance().getNewsDao();
        News news = new News(4, "name_new_ru", "name_new_en", "brief_description_new_ru", "brief_description_new_en", "english_variable_new", "russian_variable_new", LocalDateTime.of(2020, 8, 04, 13, 19, 17));
        assertTrue(newsDao.add(news));
        newsDao.delete(news.getId());
    }

    @Test
    public void testAddOnNull() throws DaoException, DuplicateException {
        NewsDao newsDao = DaoFactory.getInstance().getNewsDao();
        assertFalse(newsDao.add(null));
    }

    @Test
    public void testAddNotExist() throws DaoException, DuplicateException {
        NewsDao newsDao = DaoFactory.getInstance().getNewsDao();
        assertFalse(newsDao.add(null));
    }

    @Test(expectedExceptions = DaoException.class)
    public void testAddNotExistUserId() throws DaoException, DuplicateException {
        NewsDao newsDao = DaoFactory.getInstance().getNewsDao();
        News news = new News(400, "name_new_ru", "name_new_en", "brief_description_new_ru", "brief_description_new_en", "english_variable_new", "russian_variable_new", LocalDateTime.of(2020, 8, 04, 13, 19, 17));
        assertTrue(newsDao.add(news));
    }

    @Test
    public void testUpdate() throws DaoException, DuplicateException {
        NewsDao newsDao = DaoFactory.getInstance().getNewsDao();

        News source = new News(4, "name_ru_4","name_en_4", "brief_description_ru_4","brief_description_en_4", "english_variable_4", "russian_variable_4", LocalDateTime.of(2020, 8, 04, 13, 19, 17));
        source.setId(4);

        News updatingNews = new News(4, "name_updating_ru","name_updating_en", "brief_description_updating_en","brief_description_updating_ru", "english_variable_updating", "russian_variable_updating", LocalDateTime.of(2020, 8, 04, 13, 19, 17));
        updatingNews.setId(4);
        assertEquals(1, newsDao.update(updatingNews));
        newsDao.update(source);

    }

    @Test
    public void testUpdateOnNull() throws DaoException, DuplicateException {
        NewsDao newsDao = DaoFactory.getInstance().getNewsDao();
        assertEquals(-1, newsDao.update(null));
    }

    @Test
    public void testUpdateNotExist() throws DaoException, DuplicateException {
        NewsDao newsDao = DaoFactory.getInstance().getNewsDao();
        News updatingNews = new News(4, "name_ru_4","name_en_4", "brief_description_ru_4","brief_description_en_4", "english_variable_4", "russian_variable_4", LocalDateTime.of(2020, 8, 04, 13, 19, 17));
        updatingNews.setId(400);
        assertEquals(0, newsDao.update(updatingNews));
    }

    @Test
    public void testDelete() throws DaoException, DuplicateException {
        NewsDao newsDao = DaoFactory.getInstance().getNewsDao();
        News news = new News(4, "name_ru_4","name_en_4", "brief_description_ru_4","brief_description_en_4", "english_variable_4", "russian_variable_4", LocalDateTime.of(2020, 8, 04, 13, 19, 17));
        newsDao.add(news);
        assertTrue(newsDao.delete(news.getId()));
    }

    @Test
    public void testDeleteWithNegativeId() throws DaoException {
        NewsDao newsDao = DaoFactory.getInstance().getNewsDao();
        assertFalse(newsDao.delete(-1));
    }

    @Test
    public void testDeleteNotExist() throws DaoException {
        NewsDao newsDao = DaoFactory.getInstance().getNewsDao();
        assertFalse(newsDao.delete(10000));
    }

    @Test
    public void testDeleteSome() throws DaoException, DuplicateException {
        NewsDao newsDao = DaoFactory.getInstance().getNewsDao();
        News news1 = new News(4, "name_ru_4","name_en_4", "brief_description_en_4","brief_description_ru_4", "english_variable_4", "russian_variable_4", LocalDateTime.of(2020, 8, 04, 13, 19, 17));
        News news2 = new News(5, "name_ru_5","name_en_5", "brief_description_en_5","brief_description_ru_5", "english_variable_5", "russian_variable_5", LocalDateTime.of(2020, 8, 05, 13, 19, 17));
        News news3 = new News(6, "name_ru_6","name_en_6", "brief_description_en_6","brief_description_ru_6", "english_variable_6", "russian_variable_6", LocalDateTime.of(2020, 8, 06, 13, 19, 17));
        newsDao.add(news1);
        newsDao.add(news2);
        newsDao.add(news3);
        assertTrue(newsDao.deleteSome(List.of(news1.getId(),news2.getId(),news3.getId())));
    }

    @Test
    public void testDeleteSomeOnNull() throws DaoException {
        NewsDao newsDao = DaoFactory.getInstance().getNewsDao();
        assertFalse(newsDao.deleteSome(null));
    }

    @Test
    public void testDeleteSomeNotExist() throws DaoException {
        NewsDao newsDao = DaoFactory.getInstance().getNewsDao();
        assertFalse(newsDao.deleteSome(List.of(1,2,333)));
    }

    @Test
    public void testDeleteSomeWithNegativeId() throws DaoException {
        NewsDao newsDao = DaoFactory.getInstance().getNewsDao();
        assertFalse(newsDao.deleteSome(List.of(1,2,-1)));
    }


    @Test
    public void testFindAll() throws DaoException {
        NewsDao newsDao = DaoFactory.getInstance().getNewsDao();
        List<News> expected = newsDao.findAll();
        assertEquals(14, expected.size());
    }

    @Test
    public void testFindById() throws DaoException {
        NewsDao newsDao = DaoFactory.getInstance().getNewsDao();
        News actual = new News(4, "name_ru_4","name_en_4", "brief_description_ru_4","brief_description_en_4", "english_variable_4", "russian_variable_4", LocalDateTime.of(2020, 8, 04, 13, 19, 17));
        actual.setId(4);
        Optional<News> expected = newsDao.findById(4);
        assertEquals(actual, expected.get());
    }

    @Test
    public void testFindByIdWithNegativeId() throws DaoException {
        NewsDao newsDao = DaoFactory.getInstance().getNewsDao();
        Optional<News> expected = newsDao.findById(-1);
        assertEquals(Optional.empty(), expected);
    }

    @Test
    public void testFindByIdNotExist() throws DaoException {
        NewsDao newsDao = DaoFactory.getInstance().getNewsDao();
        Optional<News> expected = newsDao.findById(1000);
        assertEquals(Optional.empty(), expected);
    }

    @Test
    public void testFindFewSkippingFew() throws DaoException {
        NewsDao newsDao = DaoFactory.getInstance().getNewsDao();
        List<News> expected = newsDao.findFewSkippingFew(4, 2);
        assertEquals(4, expected.size());
    }
}