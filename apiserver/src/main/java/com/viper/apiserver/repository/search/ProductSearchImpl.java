package com.viper.apiserver.repository.search;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import com.viper.apiserver.domain.Product;
import com.viper.apiserver.domain.QProduct;
import com.viper.apiserver.domain.QProductImage;
import com.viper.apiserver.dto.PageRequestDTO;
import com.viper.apiserver.dto.PageResponseDTO;
import com.viper.apiserver.dto.ProductDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Objects;


@Log4j2
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {
    public ProductSearchImpl() {
        super(Product.class);
    }

    @Override
    public PageResponseDTO<ProductDTO> searchList(PageRequestDTO pageRequestDTO) {

        log.info("------------searchList-------------");

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage()-1,
                pageRequestDTO.getSize(),
                Sort.by("pno").descending());

        QProduct product = QProduct.product;
        QProductImage productImage = QProductImage.productImage;

        JPQLQuery<Product> query = from(product);

        //product엔티티에 있는 imageList를 productImage로 left join한다.
        query.leftJoin(product.imageList, productImage);
        //이미지는 대표이미지로 0번째만 가져올 것이다.
        query.where(productImage.ord.eq(0));

        Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query);

        List<Tuple> productList = query.select(product, productImage).fetch();

        long count = query.fetchCount();

        log.info("=================================");
        log.info(productList);

        return null;
    }
}
