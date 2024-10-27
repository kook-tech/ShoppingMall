package com.viper.apiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder //상속 시 필요
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {
    //페이지 번호, 사이즈
    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

}
