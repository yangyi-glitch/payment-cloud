package mav.shan.paymentservice.controller;

import mav.shan.paymentcommon.utils.exception.ServiceException;
import mav.shan.paymentservice.apiclient.PayClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/pay")
@RestController
public class PayController {

    @Resource
    private PayClient payClient;

    @GetMapping("pay-1")
    public String pay1() {
        String s1 = payClient.pay1();
//        System.out.println("pay-1：" + s1);
//        String s2 = payClient.pay2(1L, "杨溢");
//        System.out.println("pay-2：" + s2);
//        String s3 = payClient.pay3(1L, "杨溢");
//        System.out.println("pay-3：" + s3);
//        Pay pay = new Pay();
//        pay.setId(1L);
//        pay.setName("杨溢");
//        String s4 = payClient.pay4(pay);
//        System.out.println("pay-4：" + s4);
//        String s5 = payClient.pay5(1L, "杨溢");
//        System.out.println("pay-5：" + s5);
        if (true){
            throw new ServiceException("异常~~~~");
        }
        return "yes";
    }
}
