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

            // 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");

            // member.setTeam 메서드에 따로 떼어서 추가하기
//            team.getMembers().add(member); // mappedBy에 값을 주입하면 member의 team_id가 null
//            member.changeTeam(team);   // 연관관계의 주인에 값을 입력해야함

            em.persist(member);


            team.addMember(member);

            /* 만약 team에 add member를 하지 않았을 때 엔티티 매니저를 flush, clear하지 않는다면
            * 이후에 team에서 member를 찾을 때 1차 캐시에 있던 데이터가 업데이트 되지 않은 채로 나감
            * member.setTeam(team) 만으로 연관관계 매핑은 완료되지만 순수한 객체 관계에서는 양쪽에 설정해줘야함*/
//            em.flush();
//            em.clear();

            Team findTeam = em.find(Team.class, team.getId());
            List<Member> members = findTeam.getMembers();
            for (Member m : members) {
                System.out.println("m.getUsername() = " + m.getUsername());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
