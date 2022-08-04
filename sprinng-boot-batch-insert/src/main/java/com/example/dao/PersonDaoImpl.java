package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.entity.Person;

@Repository
@Transactional
public class PersonDaoImpl implements PersonDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private DataSource dataSource;

	private static final int BATCH_SIZE = 500;

	@Override
	public void saveByEntityManager(List<Person> persons) {
		int i = 0;
//		entityManager.unwrap(Session.class).setJdbcBatchSize(500);
		for (Person person : persons) {
//			persist(person);
			entityManager.persist(person);
			i++;
			if (i % BATCH_SIZE == 0) {
				entityManager.flush();
				entityManager.clear();
			}
		}

	}

	/**
	 * Hinderence of this is can not able to flush and clear 
	 * @param person
	 */
	private void persist(Person person) {
		String nativeQuery = "INSERT INTO PERSON (JOBTITILE, FIRSTNAME, LASTNAME , EMAIL) VALUES (?,?,?,?)";
		Query query = entityManager.createNativeQuery(nativeQuery);
		query.setParameter(1, person.getJobTitle());
		query.setParameter(2, person.getFirstName());
		query.setParameter(3, person.getLastName());
		query.setParameter(4, person.getEmailAddress());
		query.executeUpdate();
	}

	@Override
	public void saveAllByJDBCBatch(List<Person> persons) {

		System.out.println("insert using jdbc batch");
		String sql = String.format("INSERT INTO %s (JOBTITILE, FIRSTNAME, LASTNAME , EMAIL) " + "VALUES (?, ?, ?, ?)",
				Person.class.getAnnotation(Table.class).name());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			connection.setAutoCommit(false);
			int counter = 0;
			for (Person person : persons) {
				System.out.println(person.toString());
				statement.clearParameters();
				statement.setString(1, person.getJobTitle());
				statement.setString(2, person.getFirstName());
				statement.setString(3, person.getLastName());
				statement.setString(4, person.getEmailAddress());
				statement.addBatch();
				/**
				 * Lets say need to insert 1200 records with batch size = 500
				 * 0 -> 500 (counter + 1) % BATCH_SIZE == 0
				 * 501 -> 1000 (counter + 1) % BATCH_SIZE == 0
				 * 1001 - 1200 (counter + 1) == persons.size()
				 */
				if ((counter + 1) % BATCH_SIZE == 0 || (counter + 1) == persons.size()) {
					statement.executeBatch();
					statement.clearBatch();
				}
				counter++;
			}
			connection.commit();
			connection.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Exited");

	}

//	public void persist(Person person) {
//		entityManager.persist(person);
//	}
	
	@Override
	public void updateAllByJDBCBatch(List<Person> persons) {

		System.out.println("insert using jdbc batch");
		String sql = String.format("UPDATE %s SET EMAIL = ? WHERE ID = ?",
				Person.class.getAnnotation(Table.class).name());
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			connection.setAutoCommit(false);
			int counter = 0;
			for (Person person : persons) {
				System.out.println(person.toString());
				statement.clearParameters();
				statement.setString(1, person.getEmailAddress());
				statement.setLong(2, person.getId());
				statement.addBatch();
				/**
				 * Lets say need to insert 1200 records with batch size = 500
				 * 0 -> 500 (counter + 1) % BATCH_SIZE == 0
				 * 501 -> 1000 (counter + 1) % BATCH_SIZE == 0
				 * 1001 - 1200 (counter + 1) == persons.size()
				 */
				if ((counter + 1) % BATCH_SIZE == 0 || (counter + 1) == persons.size()) {
					statement.executeBatch();
					statement.clearBatch();
				}
				counter++;
			}
			connection.commit();
			connection.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Exited");

	}

}
