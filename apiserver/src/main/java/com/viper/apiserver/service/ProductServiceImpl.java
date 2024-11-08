package com.viper.apiserver.service;

import com.viper.apiserver.domain.Product;
import com.viper.apiserver.domain.ProductImage;
import com.viper.apiserver.dto.PageRequestDTO;
import com.viper.apiserver.dto.PageResponseDTO;
import com.viper.apiserver.dto.ProductDTO;
import com.viper.apiserver.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage()-1,
                pageRequestDTO.getSize(),
                Sort.by("pno").descending());

        Page<Object[]> result = productRepository.selectList(pageable);
        //Object[] => 0 product 1 productImage
        //Object[] => 0 product 1 productImage
        List<ProductDTO> dtoList = result.get().map(arr -> {
            ProductDTO productDTO = null;

            Product product = (Product) arr[0];
            ProductImage productImage = (ProductImage) arr[1];

            productDTO = ProductDTO.builder()
                    .pno(product.getPno())
                    .pname(product.getPname())
                    .pdesc(product.getPdesc())
                    .price(product.getPrice())
                    .build();
            String imageStr = productImage.getFileName();
            productDTO.setUploadedFileNames(List.of(imageStr));


            return productDTO;
        }).collect(Collectors.toList());
        long totalCount = result.getTotalElements();
        return PageResponseDTO.<ProductDTO>withAll()
                .dtoList(dtoList)
                .total(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

    @Override
    public Long register(ProductDTO productDTO) {
        Product product = dtoToEntity(productDTO);

        log.info("---------------------------------");
        log.info(product);
        log.info(product.getImageList());
        Long pno = productRepository.save(product).getPno();

        return pno;
    }

    private Product dtoToEntity(ProductDTO productDTO) {
        Product product = Product.builder()
                .pno(productDTO.getPno())
                .pname(productDTO.getPname())
                .pdesc(productDTO.getPdesc())
                .price(productDTO.getPrice())
                .build();

        List<String> uploadFileNames = productDTO.getUploadedFileNames();

        if(uploadFileNames == null || uploadFileNames.size() == 0) {
            return product;
        }

        uploadFileNames.forEach(fileName ->{
            product.addImageString(fileName);
        });

        return product;

    }

}
