package hellojpa;

import javassist.expr.Instanceof;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            Team team1 = new Team();
            team1.setName("teamA");
            em.persist(team1);

            Member member1 = new Member();
            member1.setUsername("hello1");
            member1.setTeam(team1);
            em.persist(member1);

            Team team2 = new Team();
            team2.setName("team2");
            em.persist(team2);

            Member member2 = new Member();
            member2.setUsername("hello2");
            member2.setTeam(team2);
            em.persist(member2);


            em.flush();
            em.clear();

            // 멤버 조회시 멤버만 가져오기 위해
            // 지연로딩 LAZY 사용시 team은 proxy로 조회
            Member m = em.find(Member.class, member1.getId());
            System.out.println("m.getTeam().getClass() = " + m.getTeam().getClass());
            // 프록시에 없는 값 조회하는 시점에 select문 날림(프록시 초기화 요청)
            System.out.println("m.getTeam().getName() = " + m.getTeam().getName());


            // 즉시로딩 EAGER 사용시 member 조회할때 team도 join해서 같이 조회
            // 문제는 JPQL 쿼리 사용시 N(팀 개수) +1(최초 쿼리)번 쿼리가 나감
            List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();


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
