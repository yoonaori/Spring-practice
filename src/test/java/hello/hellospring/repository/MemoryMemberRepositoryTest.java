package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

// 테스트 코드는 다른 곳에서 사용할 목적이 아니므로 public으로 작성하지 않아도 됨
class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    // @AfterEach : 한 메서드의 동작이 끝날 때마다 실행되는 callback 메서드
    // 테스트 순서는 보장되지않음, 따라서 한 테스트가 끝날 때마다 레포지토리를 비워주어야 함
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        // Optional에서 값을 꺼낼때는 get()을 통해 꺼낼 수 있음, 좋은 방법은 아니지만 테스트 코드에서는 가능
        Member res = repository.findById(member.getId()).get();

        // 다음과 같이 출력해서 확인하는 것도 가능하지만 매번 확인할 수 없음
        // System.out.println("res = " + (res == member));

        // jUnit의 Assertions 이용 : Expected(member)와 Actual(res)이 동일한지 확인
        // Assertions.assertEquals(member, res);

        // assertJ 이용 : jUnit보다 직관적이고 사용하기 쉬움, static import를 통해 사용할 수 있음
        assertThat(member).isEqualTo(res);
    }

    @Test
    public void findByName() {
        // given (테스트를 위해 준비하는 과정 / 변수 및 입력값, mock 객체를 정의하는 구문도 given에 포함)
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        // when (실제로 동작하는 테스트를 실행하는 과정)
        Member res = repository.findByName("spring1").get();

        // then (테스트를 검증하는 과정)
        assertThat(res).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        // when
        List<Member> res = repository.findAll();

        // then
        assertThat(res.size()).isEqualTo(2);
    }
}
