package hellojpa;

import org.hibernate.Hibernate;
import org.hibernate.jpa.internal.PersistenceUnitUtilImpl;

import javax.persistence.*;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member1 = new Member();
            member1.setUsername("hello1");
            em.persist(member1);

            em.flush();
            em.clear();

            Member refMember = em.getReference(Member.class, member1.getId());
            System.out.println("refMember.getClass() = " + refMember.getClass()); //Proxy
            refMember.getUsername(); //강제초기화
//            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(refMember));

            Hibernate.initialize(refMember); //강제초기화

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}
