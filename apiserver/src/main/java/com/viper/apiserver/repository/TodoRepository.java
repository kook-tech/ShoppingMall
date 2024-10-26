package com.viper.apiserver.repository;

import com.viper.apiserver.domain.Todo;
import com.viper.apiserver.repository.search.TodoSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoSearch {
}
