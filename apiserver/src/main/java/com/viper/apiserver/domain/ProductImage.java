package com.viper.apiserver.domain;


import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {

    private String fileName;

    private int ord;
    //순번. 대표이미지를 1번으로 하여 목록상에 노출하기 위함

    public void setOrd(int ord) {
        this.ord = ord;
    }
}
