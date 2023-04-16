package com.project.ecommerce.helper;

import com.project.ecommerce.dto.ListingResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class CommonUtil {

    public static <S,D>  ListingResponse<D> convertListingResponse(Page<S> entity,Class<D> destClass){
        List<D> dtoList = entity.getContent().stream()
                .map(t -> new ModelMapper().map(t, destClass)).collect(Collectors.toList());
        ListingResponse listingResponse =new ListingResponse<>();
        listingResponse.setData(dtoList);
        listingResponse.setTotalSize(entity.getSize());
       return listingResponse;
    }

}
