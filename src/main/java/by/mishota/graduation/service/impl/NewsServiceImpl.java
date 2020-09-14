package by.mishota.graduation.service.impl;

import by.mishota.graduation.dao.factory.DaoFactory;
import by.mishota.graduation.entity.News;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.service.NewsService;
import by.mishota.graduation.validation.NewsValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class NewsServiceImpl implements NewsService {
    @Override
    public boolean addAll(List<News> news) throws ServiceException, DuplicateException {
        try {
            return DaoFactory.getInstance().getNewsDao().addAll(news);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean add(News news) throws ServiceException, DuplicateException {
        try {
            return DaoFactory.getInstance().getNewsDao().add(news);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int update(News news) throws ServiceException, DuplicateException {
        try {
            return DaoFactory.getInstance().getNewsDao().update(news);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        try {
            return DaoFactory.getInstance().getNewsDao().delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteSome(List<Integer> listId) throws ServiceException {
        try {
            return DaoFactory.getInstance().getNewsDao().deleteSome(listId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<News> findAll() throws ServiceException {
        try {
            return DaoFactory.getInstance().getNewsDao().findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<News> findById(int id) throws ServiceException {
        try {
            return DaoFactory.getInstance().getNewsDao().findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<News> findFewSkippingFew(int numberFind, int numberSkipping) throws ServiceException {
        try {
            return DaoFactory.getInstance().getNewsDao().findFewSkippingFew(numberFind, numberSkipping);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int numberNews() throws ServiceException {
        try {
            return DaoFactory.getInstance().getNewsDao().numberNews();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<String, String> validateNews(String nameRu, String nameEn, String briefDescriptionRu, String briefDescriptionEn, String englishVariable, String russianVariable) {
        return NewsValidator.validateNews(nameRu, nameEn, briefDescriptionRu, briefDescriptionEn, englishVariable, russianVariable);
    }
}
