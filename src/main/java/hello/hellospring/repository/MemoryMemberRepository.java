package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // 값이 null이더라도 반환할 수 있도록 Optional로 감싸줌
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        // Stream API는 자바8부터 람다식과 함께 제공된 함수형 프로그래밍을 위한 기능
        // 컬렉션, 배열 안에 있는 요소들을 하나씩 참조하며 반복적인 처리를 할 수 있는 것
        return store.values().stream()  // stream 객체 생성
                .filter(member -> member.getName().equals(name))  // filter로 가공
                .findAny();  // 어떤 것이든(any) 찾아서 반환, 없으면 null이 Optional로 반환됨
    }

    @Override
    public List<Member> findAll() {
        // store의 값(value)들만 list에 담아 반환, 전체 member 객체가 list에 담김
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
