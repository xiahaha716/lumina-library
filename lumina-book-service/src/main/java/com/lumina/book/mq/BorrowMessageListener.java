package com.lumina.book.mq;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class BorrowMessageListener {

    /**
     * @RabbitListener：这是一个极度强大的注解。
     * queuesToDeclare = @Queue("borrow.queue") 的意思是：
     * 如果 RabbitMQ 里没有叫 "borrow.queue" 的队列，Spring 会自动帮你建一个，然后死死盯着它。
     */
    @RabbitListener(queuesToDeclare = @Queue("borrow.queue"))
    public void handleBorrowMessage(String message) throws InterruptedException {
        System.out.println("📨 [MQ消费者] 后台系统收到了一条新消息: " + message);

        // 模拟耗时的发短信和加积分操作（比如耗时 2 秒）
        Thread.sleep(2000);

        System.out.println("📱 [发送短信] " + message + "，已成功借阅！");
        System.out.println("🎁 [增加积分] 会员积分 +10 分！");
        System.out.println("-------------------------------------------------");
    }
}
