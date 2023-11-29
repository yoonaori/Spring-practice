package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    // findBy 뒤의 변수명을 통해 JPQL로 자동 변환 : select m from Member m where m.name = ?
    @Override
    Optional<Member> findByName(String name);
}
