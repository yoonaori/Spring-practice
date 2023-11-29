package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member){
        long start = System.currentTimeMillis();
        try {
            // 시나리오 : 동일한 이름을 가진 회원은 중복 가입 불가
            validateDuplicatedMember(member); // 중복 회원 검증
            memberRepository.save(member); // 중복 확인 후 회원가입
            return member.getId();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("join " + timeMs + "ms");
        }
    }

    private void validateDuplicatedMember(Member member) {
        // 변수 추출(새로운 변수로 선언) 단축키 : Ctrl + Alt + V
        Optional<Member> res = memberRepository.findByName(member.getName());
        // ifPresent() : Optional 객체가 null 값을 가지는지 확인, isPresent()와는 다름
        // null이 아닌 특정 값을 가지고 있으면 실행, null값이면 넘어감
        res.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });

        // 위에 처럼 Optional 변수 선언하는 것보다 아래처럼 한번에 처리하는 것을 권장 !
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
        long start = System.currentTimeMillis();
        try {
            return memberRepository.findAll();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("findMembers " + timeMs + "ms");
        }
    }

    /**
     * 특정 회원 조회
     */
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
