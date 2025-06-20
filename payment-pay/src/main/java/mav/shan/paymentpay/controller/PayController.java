package mav.shan.paymentpay.controller;

import mav.shan.paymentcommon.entity.Pay;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pay")
public class PayController {

    @GetMapping("pay-1")
    public String pay1() {
        System.out.println("pay-1");
        return "pay";
    }

    @GetMapping("pay-2")
    public String pay2(@RequestParam("id") Long id,
                       @RequestParam("name") String name) {
        System.out.println("GET:" + id);
        return name;
    }

    @PostMapping("pay-3")
    public String pay3(@RequestParam("id") Long id,
                       @RequestParam("name") String name) {
        System.out.println("POST:" + id);
        return name;
    }

    @PostMapping("pay-4")
    public String pay4(@RequestBody Pay pay) {
        System.out.println(pay);
        System.out.println("pay-4");
        return pay.getName();
    }

    @GetMapping("pay-5")
    public String pay5(Pay pay) {
        System.out.println(pay);
        System.out.println("pay-5");
        return pay.getName();
    }
}
