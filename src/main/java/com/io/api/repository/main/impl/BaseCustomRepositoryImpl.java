package com.io.api.repository.main.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

public abstract class BaseCustomRepositoryImpl {
  
  @PersistenceContext
  protected EntityManager entityManager;
  
  @Value("${default.locale:vi,VN}")
  protected String defaultLocale;
  
  <T> T getSingleResult(String query, Map<String, Object> parameters, Class<T> clazz) {
    try {
      Query nativeQuery = entityManager.createNativeQuery(query);
      if (CollectionUtils.isEmpty(parameters)) {
        return clazz.cast(nativeQuery.getSingleResult());
      }
      parameters.forEach(nativeQuery::setParameter);
      return clazz.cast(nativeQuery.getSingleResult());
    } catch (NoResultException e) {
      return null;
    }
  }

  <T> T getSingleResult(String query, String mapping, Map<String, Object> parameters) {
    Query nativeQuery = entityManager.createNativeQuery(query, mapping);
    return executeSingleResultQuery(nativeQuery, parameters);
  }

  <T> T getSingleResult(String query, Class<T> clazz, Map<String, Object> parameters) {
    Query nativeQuery = entityManager.createNativeQuery(query, clazz);
    return executeSingleResultQuery(nativeQuery, parameters);
  }

  @SuppressWarnings("unchecked")
  private <T> T executeSingleResultQuery(Query nativeQuery, Map<String, Object> parameters) {
    try {
      if (CollectionUtils.isEmpty(parameters)) {
        return (T) nativeQuery.getSingleResult();
      }
      parameters.forEach(nativeQuery::setParameter);
      return (T) nativeQuery.getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }
  
  <T> List<T> getResultList(String query, Class<T> clazz) {
    Query nativeQuery = entityManager.createNativeQuery(query, clazz);
    Map<String, Object> parameters = new HashMap<>();
    return executeResultListQuery(nativeQuery, parameters);
  }

  <T> List<T> getResultList(String query, String mapping, Map<String, Object> parameters) {
    Query nativeQuery = entityManager.createNativeQuery(query, mapping);
    return executeResultListQuery(nativeQuery, parameters);
  }

  <T> List<T> getResultList(String query, Class<T> clazz, Map<String, Object> parameters) {
    Query nativeQuery = entityManager.createNativeQuery(query, clazz);
    return executeResultListQuery(nativeQuery, parameters);
  }

  @SuppressWarnings("unchecked")
  private <T> List<T> executeResultListQuery(Query nativeQuery, Map<String, Object> parameters) {
    try {
      if (CollectionUtils.isEmpty(parameters)) {
        return nativeQuery.getResultList();
      }
      parameters.forEach(nativeQuery::setParameter);
      return nativeQuery.getResultList();
    } catch (NoResultException e) {
      return Collections.emptyList();
    }
  }

  <T> List<T> getDistinctResultList(String query, Map<String, Object> parameters) {
    Query nativeQuery = entityManager.createNativeQuery(query);
    return executeResultListQuery(nativeQuery, parameters);
  }

  int executeUpdate(String query, Map<String, Object> parameters) {
    Query nativeQuery = entityManager.createNativeQuery(query);
    if (CollectionUtils.isEmpty(parameters)) {
      return nativeQuery.executeUpdate();
    }
    parameters.forEach(nativeQuery::setParameter);
    return nativeQuery.executeUpdate();
  }
  
}
