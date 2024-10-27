package com.viper.apiserver.repository.search;

import com.querydsl.jpa.JPQLQuery;
import com.viper.apiserver.domain.QTodo;
import com.viper.apiserver.domain.Todo;
import com.viper.apiserver.dto.PageRequestDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;


@Log4j2
public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch {
    public TodoSearchImpl(){
        super(Todo.class);
    }


    @Override
    public Page<Todo> search1(PageRequestDTO pageRequestDTO) {
        log.info("search1.............");
        //search1 메서드 동작 확인.

        QTodo todo = QTodo.todo;
        //쿼리를 날리기 위한 객체

        JPQLQuery<Todo> query = from(todo);
        //Todo엔티티에 대한 jpql 코드 생성.
        //즉 쿼리 객체가 todo 엔티티에 대한 데이터를 조회할 준비를 하는 코드

        //**** 검색 조건 추가시 이자리에 구성하면 됨 ***//

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("tno").descending());
        this.getQuerydsl().applyPagination(pageable, query);
        //최신 페이징 처리 기술
        List<Todo> list = query.fetch(); //목록 데이터 가져올 때 씀.
        long total = query.fetchCount(); // long타입 으로 반환 주의
        return new PageImpl<>(list,pageable,total);
    }
}
