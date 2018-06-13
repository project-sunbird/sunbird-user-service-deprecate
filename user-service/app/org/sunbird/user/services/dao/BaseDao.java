package org.sunbird.user.services.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import org.sunbird.cassandra.CassandraOperation;
import org.sunbird.common.models.response.Response;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.helper.ServiceFactory;

/**
 * BaseDao is an interface with default implementation for accessing objects in database.
 *
 * @author Amit Kumar
 */
public interface BaseDao {

  String KEY_SPACE = JsonKey.SUNBIRD;
  CassandraOperation cassandraOperation = ServiceFactory.getInstance();
  ObjectMapper mapper = new ObjectMapper();

  /**
   * Default implementation for creating an entity in database.
   *
   * @param keyspace Keyspace name.
   * @param tableName Table name.
   * @param request Entity details.
   */
  default void create(String keyspace, String tableName, Object request) {

    cassandraOperation.insertRecord(keyspace, tableName, mapper.convertValue(request, Map.class));
  }

  /**
   * Default implementation for updating an entity in database.
   *
   * @param keyspace Keyspace name.
   * @param tableName Table name.
   * @param request Entity details.
   */
  default void update(String keyspace, String tableName, Object request) {

    cassandraOperation.updateRecord(keyspace, tableName, mapper.convertValue(request, Map.class));
  }

  /**
   * Default implementation for deleting an entity from database.
   *
   * @param keyspace Keyspace name.
   * @param tableName Table name.
   * @param identifier Entity primary key.
   */
  default void deleteById(String keyspace, String tableName, String identifier) {

    cassandraOperation.deleteRecord(keyspace, tableName, identifier);
  }

  /**
   * Default implementation for deleting an entity from database using a composite primary key.
   *
   * @param keyspace Keyspace name.
   * @param tableName Table name.
   * @param compositeKeyMap Entity composite primary key.
   */
  default void deleteByCompositeKey(
      String keySpace, String tableName, Map<String, String> compositeKeyMap) {

    cassandraOperation.deleteRecord(keySpace, tableName, compositeKeyMap);
  }

  /**
   * Default implementation for retrieving an entity from database using a primary key.
   *
   * @param keyspace Keyspace name.
   * @param tableName Table name.
   * @param identifier Identifier of the record to fetch.
   * @return Response containing entity information
   */
  default Response getRecordById(String keyspace, String tableName, String identifier) {

    return cassandraOperation.getRecordById(keyspace, tableName, identifier);
  }

  /**
   * Default implementation for retrieving an entity from database using an indexed key.
   *
   * @param keyspace Keyspace name.
   * @param tableName Table name.
   * @param indexedColumn Indexed column name.
   * @param indexedValue Indexed column value.
   * @return Response containing entity information
   */
  default Response getRecordsByIndexedProperty(
      String keyspace, String tableName, String indexedColumn, String indexedValue) {

    return cassandraOperation.getRecordsByIndexedProperty(
        keyspace, tableName, indexedColumn, indexedValue);
  }

  /**
   * @param keyspace Keyspace name.
   * @param tableName Table name.
   * @return containing entity information
   */
  static Response getAllRecords(String keyspace, String tableName) {

    return cassandraOperation.getAllRecords(keyspace, tableName);
  }

  static Response getRecordsByProperty(
      String keyspace, String tableName, String paramName, List<Object> paramValue) {

    return cassandraOperation.getRecordsByProperty(keyspace, tableName, paramName, paramValue);
  }
}
