package com.viper.apiserver.repository;

import com.viper.apiserver.domain.Product;
import com.viper.apiserver.dto.PageRequestDTO;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testInsert1(){

        Product product = Product.builder()
                .pname("Test")
                .pdesc("Test Desc")
                .price(1000)
                .build();

        product.addImageString(UUID.randomUUID()+"_"+"IMAGE1.jpg");
        product.addImageString(UUID.randomUUID()+"_"+"IMAGE2.jpg");

        productRepository.save(product);
    }

    @Test
    public void testInsert2(){
        for(int i = 0; i<10; i++){


            Product product = Product.builder()
                    .pname("Test..."+i)
                    .pdesc("Test Desc..."+i)
                    .price(1000*i)
                    .build();

            product.addImageString(UUID.randomUUID() + "_" + "IMAGE"+i+"-1.jpg");
            product.addImageString(UUID.randomUUID() + "_" + "IMAGE"+i+"-2.jpg");
            productRepository.save(product);
        }

    }


    @Transactional
    @Test
    public void testRead1(){
        Long pno = 1L;

        Optional<Product> result = productRepository.findById(pno);

        Product product = result.orElseThrow();

        log.info(product);

        log.info(product.getImageList());
    }

    @Test
    public void testRead2(){
        Long pno = 1L;

        Optional<Product> result = productRepository.selectOne(pno);

        Product product = result.orElseThrow();

        log.info(product);

        log.info(product.getImageList());
    }

    @Commit
    @Transactional
    @Test
    public void testDelete(){
        Long pno = 1L;

        productRepository.updateToDelete(pno, true);

    }

    @Test
    public void updateTest(){
        Product product =productRepository.selectOne(1L).get();

        product.changePrice(2500);

        product.clearList();
        product.addImageString(UUID.randomUUID()+"_"+"PIMAGE1.jpg");
        product.addImageString(UUID.randomUUID()+"_"+"PIMAGE2.jpg");
        product.addImageString(UUID.randomUUID()+"_"+"PIMAGE3.jpg");

        productRepository.save(product);
    }


    @Test
    public void testList(){

        Pageable pageable = PageRequest.of(0,10, Sort.by("pno").descending());
        Page<Object[]> result = productRepository.selectList(pageable);

        result.getContent().forEach(arr -> log.info(Arrays.toString(arr)));

    }

    @Test
    public void testSearch(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();

        productRepository.searchList(pageRequestDTO);

    }


}
