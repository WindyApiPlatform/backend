package top.whiteleaf03.api.controller;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.whiteleaf03.api.aop.TokenCheck;
import top.whiteleaf03.api.modal.dto.NewOrderDTO;
import top.whiteleaf03.api.modal.dto.PageNumDTO;
import top.whiteleaf03.api.modal.dto.QuerySelfOrderDTO;
import top.whiteleaf03.api.modal.dto.ReplyOrderDTO;
import top.whiteleaf03.api.service.order.OrderService;
import top.whiteleaf03.api.util.ResponseResult;

/**
 * @author WhiteLeaf03
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @TokenCheck("")
    @PostMapping("")
    public ResponseResult createNewOrder(@RequestBody NewOrderDTO newOrderDTO) {
        return orderService.insertOrder(newOrderDTO);
    }

    @TokenCheck("")
    @GetMapping("")
    public ResponseResult getAllOrder(QuerySelfOrderDTO querySelfOrderDTO) {
        if (ObjectUtil.isNotNull(querySelfOrderDTO.getPageNum()) && querySelfOrderDTO.getPageNum() >= 0) {
            // 携带页号，返回指定页数据
            return orderService.getAllOrder(querySelfOrderDTO);
        }
        // 未携带页号，返回分页总数
        return orderService.getPageSum("self");
    }

    @TokenCheck("admin")
    @GetMapping("all")
    public ResponseResult getAllWaitingOrder(PageNumDTO pageNumDTO) {
        if (ObjectUtil.isNotNull(pageNumDTO.getPageNum()) && pageNumDTO.getPageNum() >= 0) {
            // 携带页号，返回指定页数据
            return orderService.getAllWaitingOrder(pageNumDTO);
        }
        // 未携带页号，返回分页总数
        return orderService.getPageSum("admin");
    }

    @TokenCheck("admin")
    @PutMapping("")
    public ResponseResult replyOrder(@RequestBody ReplyOrderDTO replyOrderDTO) {
        return orderService.replyWaitingOrder(replyOrderDTO);
    }
}
