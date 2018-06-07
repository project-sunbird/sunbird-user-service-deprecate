package org.sunbird.user.services.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.sunbird.cassandra.CassandraOperation;
import org.sunbird.common.models.response.Response;
import org.sunbird.helper.ServiceFactory;

/**
 * This Dao class will provide the default implementation of
 * insert/update/delete/readById/readByIndexedProperty methods of CassandraOperation class.
 *
 * @author Amit Kumar
 */
public interface BaseDao {

  String KEY_SPACE = "sunbird";
  CassandraOperation cassandraOperation = ServiceFactory.getInstance();
  ObjectMapper mapper = new ObjectMapper();

  /**
   * default implementation for insertRecord of CassandraOperation.
   *
   * @param keySpace KeySpace name.
   * @param tableName Table name.
   * @param request Map of requested key and their value to insert into DB.
   */
  default void create(String keySpace, String tableName, Map<String, Object> request) {

    cassandraOperation.insertRecord(keySpace, tableName, request);
  }

  /**
   * default implementation for updateRecord of CassandraOperation.
   *
   * @param keySpace KeySpace name.
   * @param tableName Table name.
   * @param request Map of requested key and their value to update into DB.
   */
  default void update(String keySpace, String tableName, Map<String, Object> request) {

    cassandraOperation.updateRecord(keySpace, tableName, request);
  }

  /**
   * default implementation for deleteRecord by identifier of CassandraOperation.
   *
   * @param keySpace KeySpace name.
   * @param tableName Table name.
   * @param identifier Identifier of the record to delete.
   */
  default void deleteById(String keySpace, String tableName, String identifier) {

    cassandraOperation.deleteRecord(keySpace, tableName, identifier);
  }

  /**
   * default implementation for deleteRecord by composite key of CassandraOperation.
   *
   * @param keySpace KeySpace name.
   * @param tableName Table name.
   * @param compositeKeyMap Map of the composite key and its value.
   */
  default void deleteByCompositeKey(
      String keySpace, String tableName, Map<String, String> compositeKeyMap) {

    cassandraOperation.deleteRecord(keySpace, tableName, compositeKeyMap);
  }

  /**
   * default implementation for getRecordById of CassandraOperation.
   *
   * @param keySpace KeySpace name.
   * @param tableName Table name.
   * @param identifier Identifier of the record to fetch.
   * @return Response
   */
  default Response getRecordById(String keySpace, String tableName, String identifier) {

    return cassandraOperation.getRecordById(keySpace, tableName, identifier);
  }

  /**
   * default implementation for getRecordByIndexedProperty of CassandraOperation.
   *
   * @param keySpace KeySpace name.
   * @param tableName Table name.
   * @param indexedProperty Indexed property name.
   * @param propertyValue Indexed property Value.
   * @return Response
   */
  default Response getRecordsByIndexedProperty(
      String keySpace, String tableName, String indexedProperty, String propertyValue) {

    return cassandraOperation.getRecordsByIndexedProperty(
        keySpace, tableName, indexedProperty, propertyValue);
  }
}
