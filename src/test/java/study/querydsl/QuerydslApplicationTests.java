package study.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static study.querydsl.entity.QMember.*;

@SpringBootTest
@Transactional
class QuerydslApplicationTests {

	@PersistenceContext
	EntityManager em;

	JPAQueryFactory query;

	@BeforeEach
	void setup() {
		query = new JPAQueryFactory(em);
	}

	@Test
	void dynamicQuery_BooleanBuilder() {
		String username = "member1";
		Integer apeParam = 10;

		List<Member> result = searchMember1(username, apeParam);
		System.out.println("result = " + result);
	}

	private List<Member> searchMember1(String username, Integer apeParam) {

		BooleanBuilder builder = new BooleanBuilder();
		if (username != null) {
			builder.and(member.username.eq(username));
		}

		if (apeParam != null) {
			builder.and(member.age.eq(apeParam));
		}

		return query.selectFrom(member)
				.where()
				.fetch();
	}

}
