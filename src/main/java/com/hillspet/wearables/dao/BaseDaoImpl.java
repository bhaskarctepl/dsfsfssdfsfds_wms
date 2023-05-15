package com.hillspet.wearables.dao;

/**
* @author vvodyaram
*
*/
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.hillspet.wearables.dao.configuration.DataSourceConfig;

public abstract class BaseDaoImpl {

	@Autowired
	protected DataSource dataSource;

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@Autowired
	private DataSourceConfig dataSourceConfig;

	private static final Logger LOGGER = LogManager.getLogger(BaseDaoImpl.class);

	/**
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 *
	 * @return
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	*
	*/
	public BaseDaoImpl() {
	}

	/**
	 * @param sql
	 * @param params
	 * @return get the required from specified table data based on the parameters
	 */
	public List<Map<String, Object>> select(String sql, Object... params) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, (Object[]) params);
		return list;
	}

	/**
	 * @param sql
	 * @return get the required data from specified table based on the parameters
	 */
	public List<Map<String, Object>> select(String sql) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;
	}

	/**
	 * @param sql
	 * @param params
	 * @return delete the data from specified table based on the parameters
	 */
	public int delete(String sql, Object... params) {
		return update(sql, 0, params);
	}

	/**
	 * @param sql
	 * @param params
	 * @return insert the data into specified table based on the parameters
	 */
	public int insert(String sql, Object... params) {
		return jdbcTemplate.update(sql, (Object[]) params);
	}

	/**
	 * @param sql
	 * @param params
	 * @return update the data from specified table based on the parameters
	 */
	public int update(String sql, Object... params) {
		return update(sql, 0, params);
	}

	private int update(String sql, int count, Object... params) {
		int returnVal = 0;
		try {
			returnVal = jdbcTemplate.update(sql, (Object[]) params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (count < 5 && (e instanceof DeadlockLoserDataAccessException)) {
				LOGGER.error("Deadlock Exception throw. Retrying now, Attempt count :" + count);
				LOGGER.error(e);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					LOGGER.warn("Update thread sleep Exception");
				}
				returnVal = update(sql, ++count, params);
			} else {
				throw e;
			}
		}
		return returnVal;
	}

	public int[] batchUpdate(String query, List<Object[]> inputList) throws SQLException {
		try {
			return jdbcTemplate.batchUpdate(query, inputList);
		} catch (Exception exception) {
			throw new SQLException(exception);
		}
	}

	/**
	 * @param procedureName
	 * @return outputParams Map
	 * @throws SQLException
	 */
	public Map<String, Object> callStoredProcedure(String procedureName) throws SQLException {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
		simpleJdbcCall.withCatalogName(dataSourceConfig.getSchema());
		simpleJdbcCall.withProcedureName(procedureName);
		LOGGER.debug("Executing the stored procedureName - " + procedureName);
		Map<String, Object> outParameters = simpleJdbcCall.execute();
		LOGGER.debug("callStoredProcedure procedureName ended outParameters - " + outParameters);
		return outParameters;
	}

	/**
	 * @param procedureName
	 * @param inputParams
	 * @return outputParams Map
	 * @throws SQLException
	 */
	public Map<String, Object> callStoredProcedure(String procedureName, Map<String, Object> inputParams)
			throws SQLException {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
		simpleJdbcCall.withCatalogName(dataSourceConfig.getSchema());
		simpleJdbcCall.withProcedureName(procedureName);
		LOGGER.debug("Executing the stored procedureName - " + procedureName);
		LOGGER.debug("inputParams - " + inputParams);
		Map<String, Object> outParameters = simpleJdbcCall.execute(inputParams);
		LOGGER.debug("callStoredProcedure procedureName ended outParameters - " + outParameters);
		return outParameters;
	}

	/**
	 * @param sql
	 * @param params
	 * @return insert the data into specified table based on the parameters and get
	 *         the primary key
	 */

	public long insertNew(final String sql, final Object... params) {
		final KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			return ps;
		}, keyHolder);
		LOGGER.debug("inserted data keyHolder- " + keyHolder);
		long generatedKey = keyHolder.getKey().longValue();
		LOGGER.debug("inserted data generatedKey- " + generatedKey);
		return generatedKey;
	}

	public void insertBatch(final String sql, final Object... params) {
		final KeyHolder keyHolder = new GeneratedKeyHolder(); //
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			return ps;
		}, keyHolder);
		LOGGER.debug("inserted data keyHolder- " + keyHolder);
	}

	/**
	 * @param sql
	 * @param elementType
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	public <T> List<T> selectForList(String sql, Class<T> elementType, Object[] params) {
		return jdbcTemplate.queryForList(sql, (Object[]) params, elementType);
	}

	/**
	 * @param sql
	 * @param objType
	 * @param params
	 * @return get the data into specified table based on the parameters and get
	 *         primary key
	 */
	public <T> T selectForObject(String sql, Class<T> objType, Object... params) {
		T object = jdbcTemplate.queryForObject(sql, (Object[]) params, objType);
		return object;
	}

	/**
	 * this method will be used to close all the connection related objects
	 *
	 * @param connection
	 * @param callableStatement
	 * @param preparedStatement
	 * @param statement
	 * @param resultSet
	 */
	protected void closeAll(Connection connection, CallableStatement callableStatement,
			PreparedStatement preparedStatement, Statement statement, ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (callableStatement != null) {
				callableStatement.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			LOGGER.error("Error while closing the onnection: " + e.getMessage());
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (Exception e) {
					LOGGER.error("Error while closing the resultSet: " + e.getMessage());
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception e) {
					LOGGER.error("Error while closing the statement: " + e.getMessage());
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					LOGGER.error("Error while closing the preparedStatement: " + e.getMessage());
				}
			}
			if (callableStatement != null) {
				try {
					callableStatement.close();
				} catch (Exception e) {
					LOGGER.error("Error while closing the callableStatement: " + e.getMessage());
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					LOGGER.error("Error while closing the connection: " + e.getMessage());
				}
			}
		}
	}

	public void executeBatch(String query, List<Object[]> inputList) throws SQLException {
		executeBatch(query, 0, inputList);
	}

	public void executeBatch(String query, int cnt, List<Object[]> inputList) throws SQLException {
		try { //
			int[] batchResult = jdbcTemplate.batchUpdate(query, inputList);

			for (int result : batchResult) {
				System.out.println((++cnt) + " result : " + result);
			}
			LOGGER.debug("Total Updated Records count: " + batchResult.length);
		} catch (Exception exception) {
			if (cnt < 5 && (exception instanceof DeadlockLoserDataAccessException)) {
				LOGGER.error("Deadlock Exception throw. Retrying now, Attempt count :" + cnt);
				LOGGER.error("Error while closing the connection: ", exception);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					LOGGER.warn("Update thread sleep Exception");
				}
				executeBatch(query, ++cnt, inputList);
			} else {
				throw exception;
			}
		}
	}

}
