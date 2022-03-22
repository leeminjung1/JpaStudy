package hellojpa;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
/*
            // 쓰기 지연
            // 비영속 상태
            Member member1 = new Member(150L, "A");
            Member member2 = new Member(160L, "B");

            // 영속 상태
            em.persist(member1);
            em.persist(member2);
            System.out.println("=== 이때까지 db에 저장되지 않음 ===");
*/

/*
            // update - dirty checking
            Member member = em.find(Member.class, 150L);
            member.setName("Updated");
            System.out.println("=== em에 따로 업데이트 하지 않아도됨 ===");
*/

/*
            // flush 강제 호출
            Member member200 = new Member(200L, "member200");
            em.persist(member200);
            System.out.println("=== start insert query");
            em.flush();
            System.out.println("=== end insert query");
*/

/*
            // 준영속
            // 영속 상태
            Member member = em.find(Member.class, 150L);
            member.setName("AAAAA");
            // 준영속 상태
//            em.detach(member);
            em.clear();
            //commit시 update되지 않음

            // 다시 조회시 영속성 컨텍스트에 있지않아서 select쿼리를 다시 날림림
           Member member1 = em.find(Member.class, 150L);
*/


            // commit시 DB에 query날림
             tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
