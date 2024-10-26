package com.viper.apiserver.repository.search;

import com.querydsl.jpa.JPQLQuery;
import com.viper.apiserver.domain.QTodo;
import com.viper.apiserver.domain.Todo;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;


@Log4j2
public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch {
    public TodoSearchImpl(){
        super(Todo.class);
    }


    @Override
    public Page<Todo> search1() {
        log.info("search1.............");
        //search1 메서드 동작 확인.

        QTodo todo = QTodo.todo;
        //쿼리를 날리기 위한 객체

        JPQLQuery<Todo> query = from(todo);
        //Todo엔티티에 대한 jpql 코드 생성.
        //즉 쿼리 객체가 todo 엔티티에 대한 데이터를 조회할 준비를 하는 코드
        query.where(todo.title.contains("1"));

        Pageable pageable = PageRequest.of(1,10, Sort.by("tno").descending());
        this.getQuerydsl().applyPagination(pageable, query);
        //최신 페이징 처리 기술
        query.fetch(); //목록 데이터 가져올 때 씀.

        query.fetchCount(); // Long타입 으로 반환 주의

        return null;
    }
}
