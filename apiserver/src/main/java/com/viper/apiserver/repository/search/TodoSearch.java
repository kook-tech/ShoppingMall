package com.viper.apiserver.repository.search;

import com.viper.apiserver.domain.Todo;
import org.springframework.data.domain.Page;

public interface TodoSearch {


    Page<Todo> search1();

}
