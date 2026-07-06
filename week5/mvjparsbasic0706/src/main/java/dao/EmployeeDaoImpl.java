package dao;

import java.util.*;

import config.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Employee;

public class EmployeeDaoImpl implements EmployeeDao<Employee, Long> {
	@Override
	public Employee save(Employee emp) {
	    EntityManager em = JpaUtil.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    try {
	        tx.begin();
	        em.persist(emp);      // INSERT INTO employees ...
	        tx.commit();
	        return emp;           // emp 的 id 會被自動填入
	    } catch (Exception e) {
	        if (tx.isActive()) tx.rollback();
	        throw e;
	    } finally {
	        em.close();
	    }
	}
	@Override
	public Optional<Employee> findById(Long id) {
	    EntityManager em = JpaUtil.createEntityManager();
	    try {
	        return Optional.ofNullable(em.find(Employee.class, id));
	    } finally {
	        em.close();
	    }
	}

	@Override
	public List<Employee> findAll() {
		return null;
	}

	@Override
	public Employee update(Employee entity) {
		return null;
	}

	@Override
	public void deleteById(Long id) {

	}

	@Override
	public boolean existsById(Long id) {
		return false;
	}

}
