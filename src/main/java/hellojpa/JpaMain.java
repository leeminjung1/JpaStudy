package hellojpa;

import javassist.expr.Instanceof;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.lang.management.MemoryManagerMXBean;
import java.time.LocalDateTime;
import java.util.List;

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
            // find 호출 후 getReference 초기화
            System.out.println("em.find" + em.find(Member.class, member1.getId()).getClass());
            System.out.println("em.getReference" + em.getReference(Member.class, member1.getId()).getClass());
            // 둘다 hellojpa.Member 클래스




            Member member2 = new Member();
            member1.setUsername("hello2");
            em.persist(member2);
            em.flush();
            em.clear();
            // getReference 초기화 후 find
            System.out.println("em.getReference" + em.getReference(Member.class, member2.getId()).getClass());
            System.out.println("em.find" + em.find(Member.class, member2.getId()).getClass());
            // 둘다 proxy




            Member refMember = em.getReference(Member.class, member1.getId());
            System.out.println("refMember.getClass() = " + refMember.getClass());   // proxy
//            em.detach(refMember);   // 준영속 상태에서
            // em.clear();
            // em.close();
            // 프록시 초기화 하면 LazyInitializationException 터짐
//            System.out.println("refMember = " + refMember.getUsername());     // 초기화

            // 강제 초기화
            Hibernate.initialize(refMember);

            // 프록시 인스턴스 초기화 여부
            System.out.println("emf.getPersistenceUnitUtil().isLoaded(refMember) = " + emf.getPersistenceUnitUtil().isLoaded(refMember));


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void printMemberAndTeam(Member member) {
        String username = member.getUsername();
        System.out.println("username = " + username);

        Team team = member.getTeam();
        System.out.println("team = " + team.getName());
    }
}
