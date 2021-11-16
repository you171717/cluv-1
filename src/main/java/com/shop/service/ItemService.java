package com.shop.service;

import com.shop.dto.ItemFormDto;
import com.shop.dto.ItemImgDto;
import com.shop.dto.ItemSerachDto;
import com.shop.dto.MainItemDto;
import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemFormDto itemFormDto,
                         List<MultipartFile> itemImgFileList) throws Exception{

        // 상품 등록
        Item item = itemFormDto.createItem();               // fom으로 부터 item 객체 생성
        itemRepository.save(item);                          // 상품 데이터 저장

        // 이미지 등록
        for(int i=0; i<itemImgFileList.size(); i++){
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            if(i==0)                                        // 첫번째 이미지일 경우 대표 상품 이미지 여부 값 "Y"
                itemImg.setRepimgYn("Y");
            else
                itemImg.setRepimgYn("N");
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));      // 상품의 이미지 정보 저장
        }

        return item.getId();
    }

    // 상품 수정을 위해 읽어오는 메소드
    @Transactional(readOnly = true)         // 상품 데이터를 읽어오는 트랜잭션 (읽기전용)
    public ItemFormDto getItemDtl(Long itemId){

        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);    // 등록 순으로 조회
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for(ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        Item item = itemRepository.findById(itemId)               // 상품의 아이디를 통해 엔티티 조회
                .orElseThrow(EntityNotFoundException::new);
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        return itemFormDto;
    }

    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception{

        //상품 수정
        Item item = itemRepository.findById(itemFormDto.getId())    // 상품 아이디를 이용해 상품 엔티티 조회
                .orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemFormDto);                               // itemFormDto를 통해 상품 엔티티 업데이트

        List<Long> itemImgIds = itemFormDto.getItemImgIds();        // 상품 아이디로 리스트 조회

        // 이미지 등록
        for(int i =0; i<itemImgFileList.size(); i++){
            itemImgService.updateItemImg(itemImgIds.get(i),
                    itemImgFileList.get(i));                // 상품 이미지 아이디, 상품 이미지 파일 정보를 파라미터로 전달
        }
        return item.getId();
    }

    // 상품 관리 페이지 목록 불러옴 (읽기 전용)
    @Transactional(readOnly= true)
    public Page<Item> getAdminItemPage(ItemSerachDto itemSerachDto, Pageable pageable){
        return itemRepository.getAdminItemPage(itemSerachDto, pageable);
    }

    // 메인 페이지 상품 데이터
    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSerachDto itemSerachDto, Pageable pageable){
        return itemRepository.getMainItemPage(itemSerachDto, pageable);
    }
}
