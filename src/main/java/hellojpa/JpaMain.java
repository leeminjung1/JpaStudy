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
            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);     // parent는 영속성 컨테이너에서 생명주기 관리. child는 parent가 관리
            // 엔티티 옵션 cascade = CascadeType.ALL 로 주면 parent persist 시 child 모두 자동 persist
//            em.persist(child1);
//            em.persist(child2);

            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent.getId());

            // orphanRemoval = true: 컬렉션에서 빠진 객체는 DB에서 삭제됨
//            findParent.getChildList().remove(0);
            // 부모 삭제되면 자식도 삭제됨
            em.remove(parent);


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
