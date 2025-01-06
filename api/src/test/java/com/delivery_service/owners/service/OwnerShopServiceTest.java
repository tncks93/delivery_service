package com.delivery_service.owners.service;

import com.delivery_service.owners.dto.ShopRegisterDto;
import com.delivery_service.owners.entity.Owner;
import com.delivery_service.owners.entity.Shop;
import com.delivery_service.owners.repository.OwnerRepository;
import com.delivery_service.owners.repository.OwnerShopRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerShopServiceTest {
    @Mock
    OwnerRepository ownerRepository;

    @Mock
    OwnerShopRepository ownerShopRepository;

    @InjectMocks
    OwnerShopService ownerShopService;

    @Test
    @DisplayName("가게 등록 성공")
    void addShop() {
        //given
        ShopRegisterDto registerDto = new ShopRegisterDto();
        registerDto.setName("프랭크 버거");
        registerDto.setDescription("버거 맛있음");
        registerDto.setAddress("서울시 강서구 양천로23길 9");
        registerDto.setCategory("패스트푸드");
        Shop shopToSave = registerDto.convertToEntity();
        Owner owner = new Owner(1);
        when(ownerShopRepository.save(any(Shop.class))).thenAnswer(invocation -> {
            int mockedShopId = 1;
            Shop shop = invocation.getArgument(0);
            shop.setId(mockedShopId);

            return shop;
        });

        //when
        Shop addedShop = ownerShopService.addShop(owner, shopToSave);

        //then
        assertThat(addedShop.getId()).isNotNull();
        assertThat(addedShop.getName()).isEqualTo(shopToSave.getName());
        assertThat(addedShop.getDescription()).isEqualTo(shopToSave.getDescription());
        assertThat(addedShop.getAddress()).isEqualTo(shopToSave.getAddress());
        assertThat(addedShop.getCategory()).isEqualTo(shopToSave.getCategory());

    }

    @Test
    @DisplayName("가게 조회")
    void getShop() {
        //given
        Shop mockedShop = createMockShop();
        Owner mockedOwner = new Owner(1);
        mockedOwner.setShopId(1);

        when(ownerShopRepository.findByShopId(any(Integer.class))).thenReturn(mockedShop);

        //when
        Shop foundShop = ownerShopService.getShop(mockedOwner);

        //then
        assertThat(foundShop.getId()).isEqualTo(mockedShop.getId());
        assertThat(foundShop.getName()).isEqualTo(mockedShop.getName());
        assertThat(foundShop.getDescription()).isEqualTo(mockedShop.getDescription());
        assertThat(foundShop.getAddress()).isEqualTo(mockedShop.getAddress());
        assertThat(foundShop.getCategory()).isEqualTo(mockedShop.getCategory());
        assertThat(foundShop.getIsOpen()).isEqualTo(mockedShop.getIsOpen());
    }

    @Test
    @DisplayName("가게 수정")
    void updateShop() {
        //given
        Shop mockedShop = createMockShop();
        Owner mockedOwner = new Owner(1);
        mockedOwner.setShopId(1);

        when(ownerShopRepository.update(any(Integer.class), any(Shop.class))).thenReturn(mockedShop);

        //when
        Shop updatedShop = ownerShopService.updateShop(mockedOwner, mockedShop);

        //then
        assertThat(updatedShop.getId()).isEqualTo(mockedShop.getId());
        assertThat(updatedShop.getName()).isEqualTo(mockedShop.getName());
        assertThat(updatedShop.getDescription()).isEqualTo(mockedShop.getDescription());
        assertThat(updatedShop.getAddress()).isEqualTo(mockedShop.getAddress());
        assertThat(updatedShop.getCategory()).isEqualTo(mockedShop.getCategory());
        assertThat(updatedShop.getIsOpen()).isEqualTo(mockedShop.getIsOpen());
        assertThat(updatedShop.getImage()).isEqualTo(mockedShop.getImage());

    }

    @Test
    @DisplayName("가게 상태 변경 OPEN->CLOSE")
    void updateShopStatusOpenToClose() {
        //given
        Owner mockedOwner = new Owner(1);
        mockedOwner.setShopId(1);
        Boolean isOpen = false;

        when(ownerShopRepository.updateIsOpen(any(Integer.class),any(Boolean.class))).thenReturn(false);

        //when
        Boolean result = ownerShopService.updateShopStatus(mockedOwner, isOpen);

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("가게 상태 변경 CLOSE->OPEN")
    void updateShopStatusCloseToOpen() {
        //given
        Owner mockedOwner = new Owner(1);
        mockedOwner.setShopId(1);
        Boolean isOpen = true;

        when(ownerShopRepository.updateIsOpen(any(Integer.class),any(Boolean.class))).thenReturn(true);

        //when
        Boolean result = ownerShopService.updateShopStatus(mockedOwner, isOpen);

        //then
        assertThat(result).isTrue();
    }


    private Shop createMockShop() {
        ShopRegisterDto registerDto = new ShopRegisterDto();
        registerDto.setName("프랭크 버거");
        registerDto.setDescription("버거 맛있음");
        registerDto.setAddress("서울시 강서구 양천로23길 9");
        registerDto.setCategory("패스트푸드");
        Shop shop = registerDto.convertToEntity();
        shop.setId(1);
        shop.setIsOpen(false);

        return shop;
    }

}