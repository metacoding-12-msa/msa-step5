package com.metacoding.delivery.deliveries;

import com.metacoding.delivery.core.handler.ex.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    @Transactional
    public DeliveryResponse createDelivery(int orderId, String address) {
        // 1. 배달 생성
        Delivery createdDelivery = deliveryRepository.save(Delivery.create(orderId, address));
        // 2. 배달 완료
        createdDelivery.complete();
        return DeliveryResponse.from(createdDelivery);
    }

    public DeliveryResponse findById(int deliveryId) {
        Delivery findDelivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new Exception404("배달 정보를 조회할 수 없습니다."));
        return DeliveryResponse.from(findDelivery);
    }

    @Transactional
    public void cancelDelivery(int orderId) {
        Delivery cancelledDelivery = deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() -> new Exception404("배달 정보를 조회할 수 없습니다."));
        cancelledDelivery.cancel();
    }
}