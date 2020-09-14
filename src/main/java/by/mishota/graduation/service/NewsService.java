package by.mishota.graduation.service;

import by.mishota.graduation.entity.News;
import by.mishota.graduation.exception.DuplicateException;
import by.mishota.graduation.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface NewsService {
    boolean addAll(List<News> news) throws ServiceException, DuplicateException;

    boolean add(News news) throws ServiceException, DuplicateException;

    int update(News news) throws ServiceException, DuplicateException;

    boolean delete(int id) throws ServiceException;

    boolean deleteSome(List<Integer> listId) throws ServiceException;

    List<News> findAll() throws ServiceException;

    Optional<News> findById(int id) throws ServiceException;

    List<News> findFewSkippingFew(int numberFind, int numberSkipping) throws ServiceException;

    int numberNews() throws ServiceException;

    Map<String, String> validateNews(String nameRu, String nameEn, String briefDescriptionRu, String briefDescriptionEn, String englishVariable, String russianVariable);
}
