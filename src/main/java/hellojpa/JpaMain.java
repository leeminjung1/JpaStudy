package hellojpa;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Member member = new Member();
            member.setUsername("member");
            member.setHomeAddress(new Address("homeCity", "street", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("엽떡로제");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("파스타");

            member.getAddressHistory().add(new AddressEntity("old1", "street", "10000"));
            member.getAddressHistory().add(new AddressEntity("old2", "street", "10000"));
            member.getAddressHistory().add(new AddressEntity("old3", "street", "10000"));

            // 값 타입 컬렉션의 라이프사이클은 모두 member 에 의존
            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("============== start ==============");
            // member 가져오면 member만 select함 -> 값타임컬렉션은 지연로딩
            Member findMember = em.find(Member.class, member.getId());
            System.out.println("============== lazy ==============");
            List<AddressEntity> addressHistory = findMember.getAddressHistory();
            for (AddressEntity address : addressHistory) {
                System.out.println("address = " + address.getAddress().getCity());
            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for (String favoriteFood : favoriteFoods) {
                System.out.println("favoriteFood = " + favoriteFood);
            }

            // 컬렉션 값만 변경돼도 jpa가 알아서 바꿔줌
            // 치킨 -> 죽
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("죽");

/*
            // old1 -> new1
            // equals 구현되어있어서 가능한 것
            Address a = new Address("old1", "street", "10000");
            // 하나 지우면 모든 값 지우고 다시 삽입
            // insert쿼리가 위에서 생성한거 - 지운거 만큼 다시 생김
            findMember.getAddressHistory().remove(a);
            // 그다음에 새로운거 추가
            findMember.getAddressHistory().add(new AddressEntity("new1", a.getStreet(), a.getZipcode()));
*/

            findMember.getAddressHistory().get(0).setAddress(new Address("new1", "street", "10000"));

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
