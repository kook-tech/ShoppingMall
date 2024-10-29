package com.viper.apiserver.service;

import com.viper.apiserver.domain.Todo;
import com.viper.apiserver.dto.PageRequestDTO;
import com.viper.apiserver.dto.PageResponseDTO;
import com.viper.apiserver.dto.TodoDTO;

public interface TodoService {

    TodoDTO get(Long tno);

    Long register(TodoDTO dto);
    //등록// pk 값 반환
    void modify(TodoDTO dto);
    //수정//
    void remove(Long tno);
    //삭제//


    PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO);


    default TodoDTO entityToDTO(Todo todo){
        return TodoDTO.builder()
                        .tno(todo.getTno())
                        .writer(todo.getWriter())
                        .title(todo.getTitle())
                        .content(todo.getContent())
                        .complete(todo.isComplete())
                        .dueDate(todo.getDueDate())
                        .build();
    }

    default Todo dtoToEntity(TodoDTO todoDTO){
        return Todo.builder()
                .tno(todoDTO.getTno())
                .writer(todoDTO.getWriter())
                .title(todoDTO.getTitle())
                .content(todoDTO.getContent())
                .complete(todoDTO.isComplete())
                .dueDate(todoDTO.getDueDate())
                .build();
    }
}
