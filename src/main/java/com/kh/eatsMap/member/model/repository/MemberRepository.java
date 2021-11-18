package com.kh.eatsMap.member.model.repository;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.util.Streamable;

import com.kh.eatsMap.member.model.dto.Member;
import com.kh.eatsMap.timeline.model.dto.Review;

public interface MemberRepository extends MongoRepository<Member, String>{	//Repository, CrudRepository, PagingAndSortiRepository
	

//	Member save(Member member);
//	Optional<Member> findById(String id);
//	List<Member> findAll();
//	long count();
//	void delete(Member member);
//	boolean existsById(String id);

	

	
	@Query("{ 'nickname' : ?0 }")								//select * from member where user_id = ? ----> set(1, "")
	List<Member> findMemberByNickname(String nickname);

	@Query(value = "{ 'nickname' : ?0 }", fields="{ 'email' : 1, 'password' : 1}")
	List<Member> findEmailAndPasswordWithJson(String nickname);

	List<Member> findByPassword(String password, Sort sort);

	List<Member> findDistinctMemberByNicknameOrEmail(String string, String string2);

	Member findByNicknameIgnoreCase(String nickname);
	
	Member findByNicknameOrEmailAllIgnoreCase(String nickname, String email);
	
	List<Member> findByIdOrderByRegDateDesc(String id);
	List<Member> findByPasswordOrderByNicknameAsc(String password);	//중간 ByOO 없으면 오류남

	Page<Review> findByNickname(String nickname, Pageable page);

	Member findFirstByOrderByNicknameAsc();

	Member findTop10ByNickname(String nickname, Sort sort);

	Member findByEmail(String email);

	Streamable<Member> findByNicknameContaining(String string);

	Streamable<Member> findByEmailContaining(String string);

	@Query("select m from Member m")	//안되는 코드임! BsonType에러
	Stream<Member> findMemberByQuery();

	Member findByKakaoId(String kakaoId);




}
