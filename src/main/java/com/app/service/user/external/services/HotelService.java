package com.app.service.user.external.services;

import com.app.service.user.entites.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="HOTELSERVICE")
public interface HotelService {
    @GetMapping("/hotel/{hotelId}")
    Hotel hotel(@PathVariable String hotelId);

}
