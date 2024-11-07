package com.viper.apiserver.repository.search;

import com.viper.apiserver.dto.PageRequestDTO;
import com.viper.apiserver.dto.PageResponseDTO;
import com.viper.apiserver.dto.ProductDTO;

public interface ProductSearch {

    PageResponseDTO<ProductDTO> searchList  (PageRequestDTO pageRequestDTO);
}
