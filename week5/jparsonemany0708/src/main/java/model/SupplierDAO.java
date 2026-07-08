package model;
import java.util.*;

import config.JpaUtil;
import jakarta.persistence.EntityManager;
public class SupplierDAO {
  public static List<Supplier> getAllSuppliers() {
	EntityManager em = JpaUtil.createEntityManager();
	List<Supplier> suppliers = em.createQuery("SELECT s FROM Supplier s", Supplier.class).getResultList();
	em.close();
	return suppliers;
  }
}
