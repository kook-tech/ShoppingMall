package com.viper.apiserver.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//상속 할 일이 없어서 superbuilder 안씀.
//페이지 결과물을 가져가는 DTO
// E -> DTO 예정
@Data
public class PageResponseDTO<E> {
    //dto목록
    private List<E> dtoList;

    //**데이터를 편하게 전달하려면 -> 페이징 처리를 미리해서 보내준다.
    private List<Integer> pageNumList;

    private PageRequestDTO pageRequestDTO;

    private boolean prev, next;

    private int totalCount, prevPage, nextPage, totalPage, current;
    // 총.이전.다음.현재 페이지

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(List<E> dtoList, PageRequestDTO pageRequestDTO, long total){
        this.dtoList = dtoList;
        this.pageRequestDTO = pageRequestDTO;
        this.totalCount = (int)total;

        //끝페이지부터 계산 end
        int end = (int)(Math.ceil(pageRequestDTO.getPage() / 10.0)) * 10;
        //현재 페이지를 10.0으로 나눈다.
        //그 값을 올림처리하고 10을 곱한다.
        int start = end - 9;

        //구한 end 값이 total페이지 last 값보다 큰지 확인.
        int last = (int)(Math.ceil(totalCount/(double)pageRequestDTO.getSize()));

        end = end > last ? last : end;
        // 삼항 연산자를 통해 end 페이지 재조정.

        this.prev = start > 1;
        //이전페이지
        this.next = totalCount > end * pageRequestDTO.getSize();
        //다음페이지 :  총페이지 > 마지막페이지 * 페이지 사이즈
        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
        //시작부터 끝을 boxed를 통해 Integer로 바꾸고 리스트로 만듬;
        this.prevPage = prev ? start - 1 : 0;
        this.nextPage = next ? end + 1 : 0;
    }


}
