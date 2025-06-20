package mav.shan.paymentservice.apiclient;

import mav.shan.paymentcommon.entity.Pay;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "payment-pay")
public interface PayClient {

    @GetMapping("/pay/pay-1")
    String pay1();

    @GetMapping("/pay/pay-2")
    String pay2(@RequestParam("id") Long id,
                @RequestParam("name") String name);

    @PostMapping("/pay/pay-3")
    String pay3(@RequestParam("id") Long id,
                @RequestParam("name") String name);

    @PostMapping("/pay/pay-4")
    String pay4(@RequestBody Pay pay);

    @GetMapping("/pay/pay-5")
    String pay5(@RequestParam("id") Long id,
                @RequestParam("name") String name);
}
